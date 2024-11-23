package main;

//Notificacao.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import database.*;

public class Notificacao {
 private int notificacaoID;
 private int destinatarioID;
 private String mensagem;
 private String dataEnvio;
 private String status;



 public int getNotificacaoID() {
	return notificacaoID;
}

public void setNotificacaoID(int notificacaoID) {
	this.notificacaoID = notificacaoID;
}

public int getDestinatarioID() {
	return destinatarioID;
}

public void setDestinatarioID(int destinatarioID) {
	this.destinatarioID = destinatarioID;
}

public String getMensagem() {
	return mensagem;
}

public void setMensagem(String mensagem) {
	this.mensagem = mensagem;
}

public String getDataEnvio() {
	return dataEnvio;
}

public void setDataEnvio(String dataEnvio) {
	this.dataEnvio = dataEnvio;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}



public Notificacao(int notificacaoID, int destinatarioID, String mensagem, String dataEnvio, String status) {
	super();
	this.notificacaoID = notificacaoID;
	this.destinatarioID = destinatarioID;
	this.mensagem = mensagem;
	this.dataEnvio = dataEnvio;
	this.status = status;
}

public Notificacao() {
	
}

public boolean enviarNotificacao() {
    String insert = "INSERT INTO Notificacoes (destinatarioID, mensagem, dataEnvio, status) " +
                    "VALUES (?, ?, datetime('now'), 'Não Lida')";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(insert)) {
        pstmt.setInt(1, this.destinatarioID);
        pstmt.setString(2, this.mensagem);
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("Notificação enviada com sucesso.");
            return true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public boolean marcarComoLida(int notificacaoID) {
    String update = "UPDATE Notificacoes SET status = 'Lida' WHERE notificacaoID = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(update)) {
        pstmt.setInt(1, notificacaoID);
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("Notificação marcada como lida.");
            return true;
        } else {
            System.out.println("Notificação não encontrada.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public List<Notificacao> listarNotificacoes(int destinatarioID) {
    List<Notificacao> lista = new ArrayList<>();
    String query = "SELECT * FROM Notificacoes WHERE destinatarioID = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, destinatarioID);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Notificacao notif = new Notificacao();
            notif.setNotificacaoID(rs.getInt("notificacaoID"));
            notif.setDestinatarioID(rs.getInt("destinatarioID"));
            notif.setMensagem(rs.getString("mensagem"));
            notif.setDataEnvio(rs.getString("dataEnvio"));
            notif.setStatus(rs.getString("status"));
            lista.add(notif);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}
}