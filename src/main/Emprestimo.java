package main;

//Emprestimo.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.*;

public class Emprestimo {
 private int emprestID;
 private Cliente cliente;
 private Livro livro;
 private String dataEmprestimo;
 private String dataDevolucaoPrevista;
 private String dataDevolucaoEfetiva;
 private String status;
 private Multa multa;

 public int getEmprestID() {
	return emprestID;
}

public void setEmprestID(int emprestID) {
	this.emprestID = emprestID;
}

public Cliente getCliente() {
	return cliente;
}

public void setCliente(Cliente cliente) {
	this.cliente = cliente;
}

public Livro getLivro() {
	return livro;
}

public void setLivro(Livro livro) {
	this.livro = livro;
}

public String getDataEmprestimo() {
	return dataEmprestimo;
}

public void setDataEmprestimo(String dataEmprestimo) {
	this.dataEmprestimo = dataEmprestimo;
}

public String getDataDevolucaoPrevista() {
	return dataDevolucaoPrevista;
}

public void setDataDevolucaoPrevista(String dataDevolucaoPrevista) {
	this.dataDevolucaoPrevista = dataDevolucaoPrevista;
}

public String getDataDevolucaoEfetiva() {
	return dataDevolucaoEfetiva;
}

public void setDataDevolucaoEfetiva(String dataDevolucaoEfetiva) {
	this.dataDevolucaoEfetiva = dataDevolucaoEfetiva;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public Multa getMulta() {
	return multa;
}

public void setMulta(Multa multa) {
	this.multa = multa;
}

public Emprestimo() {
	
}

public Emprestimo(int emprestID, Cliente cliente, Livro livro, String dataEmprestimo, String dataDevolucaoPrevista,
		String dataDevolucaoEfetiva, String status, Multa multa) {
	super();
	this.emprestID = emprestID;
	this.cliente = cliente;
	this.livro = livro;
	this.dataEmprestimo = dataEmprestimo;
	this.dataDevolucaoPrevista = dataDevolucaoPrevista;
	this.dataDevolucaoEfetiva = dataDevolucaoEfetiva;
	this.status = status;
	this.multa = multa;
}

public boolean registrarEmprestimo() {
    String insert = "INSERT INTO Emprestimo (clienteID, livroID, dataEmprestimo, dataDevolucaoPrevista, status) " +
                    "VALUES (?, ?, datetime('now'), datetime('now', '+14 day'), 'Ativo')";
    String updateLivro = "UPDATE Livro SET quantidadeDisponivel = quantidadeDisponivel - 1 WHERE livroID = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
         PreparedStatement pstmtUpdate = conn.prepareStatement(updateLivro)) {
        pstmt.setInt(1, cliente.getUserID());
        pstmt.setInt(2, livro.getLivroID());
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows == 0) return false;
        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            this.emprestID = rs.getInt(1);
        }
        pstmtUpdate.setInt(1, livro.getLivroID());
        pstmtUpdate.executeUpdate();
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public boolean registrarDevolucao() {
    String update = "UPDATE Emprestimo SET dataDevolucaoEfetiva = datetime('now'), status = 'Devolvido' WHERE emprestID = ?";
    String updateLivro = "UPDATE Livro SET quantidadeDisponivel = quantidadeDisponivel + 1 WHERE livroID = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(update);
         PreparedStatement pstmtUpdate = conn.prepareStatement(updateLivro)) {
        pstmt.setInt(1, this.emprestID);
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows == 0) return false;
        pstmtUpdate.setInt(1, this.livro.getLivroID());
        pstmtUpdate.executeUpdate();
        calcularMulta();
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public void calcularMulta() {
    int diasAtraso = 0; 

    if (diasAtraso > 0) {
        Multa novaMulta = new Multa();
        novaMulta.setEmprestimoID(this.emprestID);
        novaMulta.setValor(diasAtraso * 1.0);
        novaMulta.aplicarMulta();
        this.multa = novaMulta;
        System.out.println("Multa de R$" + novaMulta.getValor() + " aplicada por " + diasAtraso + " dias de atraso.");
    } else {
        System.out.println("Nenhuma multa aplicada.");
    }
}

public boolean renovarEmprestimo() {
    String update = "UPDATE Emprestimo SET dataDevolucaoPrevista = datetime(dataDevolucaoPrevista, '+14 day') WHERE emprestID = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(update)) {
        pstmt.setInt(1, this.emprestID);
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("Empréstimo renovado com sucesso.");
            return true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

 public String consultarStatus() {
     return this.status;
 }
}