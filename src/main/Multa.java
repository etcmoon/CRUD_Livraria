package main;

//Multa.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.*;

public class Multa {
 private int multaID;
 private int emprestimoID;
 private double valor;
 private String dataAplicacao;
 private String statusPagamento;

 

 public int getMultaID() {
	return multaID;
}

public void setMultaID(int multaID) {
	this.multaID = multaID;
}

public int getEmprestimoID() {
	return emprestimoID;
}

public void setEmprestimoID(int emprestimoID) {
	this.emprestimoID = emprestimoID;
}

public double getValor() {
	return valor;
}

public void setValor(double valor) {
	this.valor = valor;
}

public String getDataAplicacao() {
	return dataAplicacao;
}

public void setDataAplicacao(String dataAplicacao) {
	this.dataAplicacao = dataAplicacao;
}

public String getStatusPagamento() {
	return statusPagamento;
}

public void setStatusPagamento(String statusPagamento) {
	this.statusPagamento = statusPagamento;
}



public Multa(int multaID, int emprestimoID, double valor, String dataAplicacao, String statusPagamento) {
	super();
	this.multaID = multaID;
	this.emprestimoID = emprestimoID;
	this.valor = valor;
	this.dataAplicacao = dataAplicacao;
	this.statusPagamento = statusPagamento;
}

public Multa() {
	
}

public boolean aplicarMulta() {
    String insert = "INSERT INTO Multas (emprestimoID, valor, dataAplicacao, statusPagamento) " +
                    "VALUES (?, ?, datetime('now'), 'Pendente')";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(insert)) {
        pstmt.setInt(1, this.emprestimoID);
        pstmt.setDouble(2, this.valor);
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("Multa aplicada com sucesso.");
            return true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public boolean pagarMulta(int multaID) {
    String update = "UPDATE Multas SET statusPagamento = 'Pago' WHERE multaID = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(update)) {
        pstmt.setInt(1, multaID);
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("Multa paga com sucesso.");
            return true;
        } else {
            System.out.println("Multa não encontrada.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public Multa consultarMultaPorCliente(int clienteID) {
    String query = "SELECT m.* FROM Multas m JOIN Emprestimo e ON m.emprestimoID = e.emprestimoID WHERE e.clienteID = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, clienteID);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            Multa multa = new Multa();
            multa.setMultaID(rs.getInt("multaID"));
            multa.setEmprestimoID(rs.getInt("emprestimoID"));
            multa.setValor(rs.getDouble("valor"));
            multa.setDataAplicacao(rs.getString("dataAplicacao"));
            multa.setStatusPagamento(rs.getString("statusPagamento"));
            return multa;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

public boolean isentarMulta(int multaID) {
    String update = "UPDATE Multas SET statusPagamento = 'Isenta' WHERE multaID = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(update)) {
        pstmt.setInt(1, multaID);
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("Multa isentada com sucesso.");
            return true;
        } else {
            System.out.println("Multa não encontrada.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
}