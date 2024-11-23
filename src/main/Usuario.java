package main;

//Usuario.java
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.*;
import java.security.MessageDigest;


public class Usuario {

protected int userID;
protected String CPF;
protected String nome;
protected String email;
protected String telefone;
protected String endereco;
protected String dataCadastro;
protected String statusConta;
protected String funcao;
protected String senha;
	
 public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getStatusConta() {
		return statusConta;
	}

	public void setStatusConta(String statusConta) {
		this.statusConta = statusConta;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


public Usuario() {
	
}

 public Usuario(int userID, String CPF, String nome, String email, String telefone, String endereco,
			String dataCadastro, String statusConta, String funcao, String senha) {
		super();
		this.userID = userID;
		this.CPF = CPF;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.endereco = endereco;
		this.dataCadastro = dataCadastro;
		this.statusConta = statusConta;
		this.funcao = funcao;
		this.senha = senha;
	}
 
 
 
 protected String hashSenha(String senha) {
     try {
         MessageDigest digest = MessageDigest.getInstance("SHA-256");
         byte[] hash = digest.digest(senha.getBytes("UTF-8"));
         StringBuilder hexString = new StringBuilder();
         for (byte b : hash) {
             String hex = Integer.toHexString(0xff & b);
             if(hex.length() == 1) hexString.append('0');
             hexString.append(hex);
         }
         return hexString.toString();
     } catch(Exception ex){
        throw new RuntimeException(ex);
     }
 }
 

 public boolean login(String email, String senha) {
     String sql = "SELECT * FROM Usuarios WHERE email = ?";
     try (Connection conn = DBConnection.getConnection();
          PreparedStatement pstmt = conn.prepareStatement(sql)) {
          
         pstmt.setString(1, email);
         ResultSet rs = pstmt.executeQuery();
         if (rs.next()) {
             String hashSenhaBanco = rs.getString("senha");
             String hashSenhaInput = hashSenha(senha);
             if (hashSenhaBanco.equals(hashSenhaInput)) {
                 // Inicializar atributos do usuário
                 this.userID = rs.getInt("userID");
                 this.CPF = rs.getString("CPF");
                 this.nome = rs.getString("nome");
                 this.email = rs.getString("email");
                 this.telefone = rs.getString("telefone");
                 this.endereco = rs.getString("endereco");
                 this.dataCadastro = rs.getString("dataCadastro");
                 this.statusConta = rs.getString("statusConta");
                 this.funcao = rs.getString("funcao");
                 this.senha = hashSenhaBanco;
                 return true;
             }
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
     return false;
 }


 public void logout() {
     System.out.println("Usuário deslogado.");
 }
 
 public boolean alterarSenha(String novaSenha) {
     String sql = "UPDATE Usuarios SET senha = ? WHERE userID = ?";
     try (Connection conn = DBConnection.getConnection();
          PreparedStatement pstmt = conn.prepareStatement(sql)) {
          
         String hashNovaSenha = hashSenha(novaSenha);
         pstmt.setString(1, hashNovaSenha);
         pstmt.setInt(2, this.userID);
         int affected = pstmt.executeUpdate();
         if (affected > 0) {
             this.senha = hashNovaSenha;
             return true;
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
     return false;
 }

 public List<Livro> consultarLivro(String criterio) {
     // Implement book search based on criterio
     // Example:
     // SELECT * FROM Livro WHERE titulo LIKE %criterio% OR autor LIKE %criterio%
     return null;
 }

 public boolean atualizarCadastro() {
     String update = "UPDATE Usuarios SET CPF = ?, nome = ?, email = ?, telefone = ?, endereco = ?, senha = ? WHERE userID = ?";
     try (Connection conn = DBConnection.getConnection();
          PreparedStatement pstmt = conn.prepareStatement(update)) {
         pstmt.setString(1, this.CPF);
         pstmt.setString(2, this.nome);
         pstmt.setString(3, this.email);
         pstmt.setString(4, this.telefone);
         pstmt.setString(5, this.endereco);
         pstmt.setString(6, this.senha);
         pstmt.setInt(7, this.userID);
         int affectedRows = pstmt.executeUpdate();
         if (affectedRows > 0) {
             System.out.println("Cadastro atualizado com sucesso.");
             return true;
         }
     } catch (SQLException e) {
         if(e.getMessage().contains("UNIQUE constraint failed")) {
             System.out.println("Erro: Um usuário com o email '" + this.email + "' já está cadastrado.");
         } else {
             e.printStackTrace();
         }
     }
     return false;
 }


public boolean realizarCadastro() {
     String insert = "INSERT INTO Usuarios (CPF, nome, email, telefone, endereco, dataCadastro, statusConta, funcao, senha) " +
                     "VALUES (?, ?, ?, ?, ?, datetime('now'), 'Ativa', ?, ?)";
     try (Connection conn = DBConnection.getConnection();
          PreparedStatement pstmt = conn.prepareStatement(insert)) {
         pstmt.setString(1, this.CPF);
         pstmt.setString(2, this.nome);
         pstmt.setString(3, this.email);
         pstmt.setString(4, this.telefone);
         pstmt.setString(5, this.endereco);
         pstmt.setString(6, this.funcao);
         pstmt.setString(7, this.senha);
         int affectedRows = pstmt.executeUpdate();
         if (affectedRows > 0) {
             System.out.println("Usuário '" + this.nome + "' cadastrado com sucesso.");
             return true;
         }
     } catch (SQLException e) {
         if(e.getMessage().contains("UNIQUE constraint failed")) {
             System.out.println("Erro: Um usuário com o email '" + this.email + "' já está cadastrado.");
         } else {
             e.printStackTrace();
         }
     }
     return false;
 }
}