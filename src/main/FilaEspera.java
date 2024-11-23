package main;

//FilaEspera.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import database.*;

public class FilaEspera {
 private int filaID;
 private int livroID;
 private int clienteID;
 private String dataSolicitacao;
 private String status;

 public int getFilaID() {
	return filaID;
}

public void setFilaID(int filaID) {
	this.filaID = filaID;
}

public int getLivroID() {
	return livroID;
}

public void setLivroID(int livroID) {
	this.livroID = livroID;
}

public int getClienteID() {
	return clienteID;
}

public void setClienteID(int clienteID) {
	this.clienteID = clienteID;
}

public String getDataSolicitacao() {
	return dataSolicitacao;
}

public void setDataSolicitacao(String dataSolicitacao) {
	this.dataSolicitacao = dataSolicitacao;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}



public FilaEspera(int filaID, int livroID, int clienteID, String dataSolicitacao, String status) {
	super();
	this.filaID = filaID;
	this.livroID = livroID;
	this.clienteID = clienteID;
	this.dataSolicitacao = dataSolicitacao;
	this.status = status;
}

public FilaEspera() {
	
}

public boolean adicionarFila() {
    String insert = "INSERT INTO FilaEspera (livroID, clienteID, dataSolicitacao, status) " +
                    "VALUES (?, ?, datetime('now'), 'Ativo')";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(insert)) {
        pstmt.setInt(1, this.livroID);
        pstmt.setInt(2, this.clienteID);
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("Cliente adicionado à fila de espera com sucesso.");
            return true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public boolean removerFila(int filaID) {
    String delete = "DELETE FROM FilaEspera WHERE filaID = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(delete)) {
        pstmt.setInt(1, filaID);
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("Cliente removido da fila de espera.");
            return true;
        } else {
            System.out.println("Fila de espera não encontrada.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public List<FilaEspera> listarFila(int livroID) {
    List<FilaEspera> filaList = new ArrayList<>();
    String query = "SELECT * FROM FilaEspera WHERE livroID = ? AND status = 'Ativo' ORDER BY dataSolicitacao";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, livroID);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            FilaEspera fila = new FilaEspera();
            fila.setFilaID(rs.getInt("filaID"));
            fila.setLivroID(rs.getInt("livroID"));
            fila.setClienteID(rs.getInt("clienteID"));
            fila.setDataSolicitacao(rs.getString("dataSolicitacao"));
            fila.setStatus(rs.getString("status"));
            filaList.add(fila);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return filaList;
}
}