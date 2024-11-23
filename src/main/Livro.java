package main;

//Livro.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.*;

public class Livro {
 private int livroID;
 private String titulo;
 private String autor;
 private String editora;
 private String categoria;
 private int anoPublicacao;
 private int quantidadeDisponivel;
 private String isbn;
 private boolean disponivel;


 public int getLivroID() {
	return livroID;
}

public void setLivroID(int livroID) {
	this.livroID = livroID;
}

public String getTitulo() {
	return titulo;
}

public void setTitulo(String titulo) {
	this.titulo = titulo;
}

public String getAutor() {
	return autor;
}

public void setAutor(String autor) {
	this.autor = autor;
}

public String getEditora() {
	return editora;
}

public void setEditora(String editora) {
	this.editora = editora;
}

public String getCategoria() {
	return categoria;
}

public void setCategoria(String categoria) {
	this.categoria = categoria;
}

public int getAnoPublicacao() {
	return anoPublicacao;
}

public void setAnoPublicacao(int anoPublicacao) {
	this.anoPublicacao = anoPublicacao;
}

public int getQuantidadeDisponivel() {
	return quantidadeDisponivel;
}

public void setQuantidadeDisponivel(int quantidadeDisponivel) {
	this.quantidadeDisponivel = quantidadeDisponivel;
}

public String getIsbn() {
	return isbn;
}

public void setIsbn(String isbn) {
	this.isbn = isbn;
}

public boolean isDisponivel() {
	return disponivel;
}

public void setDisponivel(boolean disponivel) {
	this.disponivel = disponivel;
}




public Livro(int livroID, String titulo, String autor, String editora, String categoria, int anoPublicacao,
		int quantidadeDisponivel, String isbn, boolean disponivel) {
	super();
	this.livroID = livroID;
	this.titulo = titulo;
	this.autor = autor;
	this.editora = editora;
	this.categoria = categoria;
	this.anoPublicacao = anoPublicacao;
	this.quantidadeDisponivel = quantidadeDisponivel;
	this.isbn = isbn;
	this.disponivel = disponivel;
}

public Livro() {
	
}

public boolean adicionarLivro() {
    String insert = "INSERT INTO Livro (titulo, autor, editora, categoria, anoPublicacao, quantidadeDisponivel, isbn, disponivel) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(insert)) {
        pstmt.setString(1, this.titulo);
        pstmt.setString(2, this.autor);
        pstmt.setString(3, this.editora);
        pstmt.setString(4, this.categoria);
        pstmt.setInt(5, this.anoPublicacao);
        pstmt.setInt(6, this.quantidadeDisponivel);
        pstmt.setString(7, this.isbn);
        pstmt.setBoolean(8, this.disponivel);
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("Livro '" + this.titulo + "' cadastrado com sucesso.");
            return true;
        }
    } catch (SQLException e) {
        if(e.getMessage().contains("UNIQUE constraint failed")) {
            System.out.println("Erro: Um livro com o ISBN '" + this.isbn + "' já está cadastrado.");
        } else {
            e.printStackTrace();
        }
    }
    return false;
}

public boolean verificarDisponibilidade() {
    String query = "SELECT quantidadeDisponivel FROM Livro WHERE livroID = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, this.livroID);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            this.quantidadeDisponivel = rs.getInt("quantidadeDisponivel");
            this.disponivel = this.quantidadeDisponivel > 0;
            return this.disponivel;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public boolean editarLivro() {
    String update = "UPDATE Livro SET titulo = ?, autor = ?, editora = ?, categoria = ?, anoPublicacao = ?, " +
                    "quantidadeDisponivel = ?, isbn = ?, disponivel = ? WHERE livroID = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(update)) {
        pstmt.setString(1, this.titulo);
        pstmt.setString(2, this.autor);
        pstmt.setString(3, this.editora);
        pstmt.setString(4, this.categoria);
        pstmt.setInt(5, this.anoPublicacao);
        pstmt.setInt(6, this.quantidadeDisponivel);
        pstmt.setString(7, this.isbn);
        pstmt.setBoolean(8, this.disponivel);
        pstmt.setInt(9, this.livroID);
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("Livro '" + this.titulo + "' atualizado com sucesso.");
            return true;
        }
    } catch (SQLException e) {
        if(e.getMessage().contains("UNIQUE constraint failed")) {
            System.out.println("Erro: Um livro com o ISBN '" + this.isbn + "' já está cadastrado.");
        } else {
            e.printStackTrace();
        }
    }
    return false;
}

public boolean removerLivro() {
    String delete = "DELETE FROM Livro WHERE livroID = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(delete)) {
        pstmt.setInt(1, this.livroID);
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("Livro removido com sucesso.");
            return true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

public Livro buscarLivro(int livroID) {
    String query = "SELECT * FROM Livro WHERE livroID = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, livroID);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            Livro livro = new Livro();
            livro.setLivroID(rs.getInt("livroID"));
            livro.setTitulo(rs.getString("titulo"));
            livro.setAutor(rs.getString("autor"));
            livro.setEditora(rs.getString("editora"));
            livro.setCategoria(rs.getString("categoria"));
            livro.setAnoPublicacao(rs.getInt("anoPublicacao"));
            livro.setQuantidadeDisponivel(rs.getInt("quantidadeDisponivel"));
            livro.setIsbn(rs.getString("isbn"));
            livro.setDisponivel(rs.getBoolean("disponivel"));
            return livro;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

public void atualizarQuantidade(int livroID, int novaQuantidade) {
    String update = "UPDATE Livro SET quantidadeDisponivel = ?, disponivel = ? WHERE livroID = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(update)) {
        pstmt.setInt(1, novaQuantidade);
        pstmt.setBoolean(2, novaQuantidade > 0);
        pstmt.setInt(3, livroID);
        int rows = pstmt.executeUpdate();
        if (rows > 0) {
            System.out.println("Quantidade atualizada com sucesso.");
        } else {
            System.out.println("Erro ao atualizar a quantidade.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}