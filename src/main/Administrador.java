package main;

//Administrador.java
//Administrador.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import database.*;

public class Administrador extends Usuario {

 public Administrador(int userID, String CPF, String nome, String email, String telefone, String endereco,
                     String dataCadastro, String statusConta, String funcao, String senha,
                     List<Emprestimo> emprestimosAtivos, List<FilaEspera> filaEspera) {
     super();
 }

 public Administrador() {
	super();
}

// Getters and Setters
 public int getUserID() {
     return super.getUserID();
 }

 public void setUserID(int userID) {
     super.setUserID(userID);
 }

 public String getCPF() {
     return super.getCPF();
 }

 public void setCPF(String CPF) {
     super.setCPF(CPF);
 }

 public String getNome() {
     return super.getNome();
 }

 public void setNome(String nome) {
     super.setNome(nome);
 }

 public String getEmail() {
     return super.getEmail();
 }

 public void setEmail(String email) {
     super.setEmail(email);
 }

 public String getTelefone() {
     return super.getTelefone();
 }

 public void setTelefone(String telefone) {
     super.setTelefone(telefone);
 }

 public String getEndereco() {
     return super.getEndereco();
 }

 public void setEndereco(String endereco) {
     super.setEndereco(endereco);
 }

 public String getDataCadastro() {
     return super.getDataCadastro();
 }

 public void setDataCadastro(String dataCadastro) {
     super.setDataCadastro(dataCadastro);
 }

 public String getStatusConta() {
     return super.getStatusConta();
 }

 public void setStatusConta(String statusConta) {
     super.setStatusConta(statusConta);
 }

 public String getFuncao() {
     return super.getFuncao();
 }

 public void setFuncao(String funcao) {
     super.setFuncao(funcao);
 }

 public String getSenha() {
     return super.getSenha();
 }

 public void setSenha(String senha) {
     super.setSenha(senha);
 }

 // Methods

 public boolean cadastrarCliente(Cliente cliente) {
     return cliente.realizarCadastro();
 }

 public boolean atualizarCliente(Cliente cliente) {
     return cliente.atualizarCadastro();
 }

 public boolean gerenciarUsuarios(int userID, String acao) {
     // acao pode ser "ativar", "desativar", ou "listar"
     switch (acao.toLowerCase()) {
         case "ativar":
             return alterarStatusConta(userID, "Ativa");
         case "desativar":
             return alterarStatusConta(userID, "Inativa");
         case "listar":
             listarUsuarios();
             return true;
         default:
             System.out.println("Ação desconhecida. Use 'ativar', 'desativar' ou 'listar'.");
             return false;
     }
 }

 private boolean alterarStatusConta(int userID, String novoStatus) {
     String update = "UPDATE Usuarios SET statusConta = ? WHERE userID = ?";
     try (Connection conn = DBConnection.getConnection();
          PreparedStatement pstmt = conn.prepareStatement(update)) {
         pstmt.setString(1, novoStatus);
         pstmt.setInt(2, userID);
         int affectedRows = pstmt.executeUpdate();
         if (affectedRows > 0) {
             System.out.println("Usuário " + userID + " atualizado para status: " + novoStatus);
             return true;
         } else {
             System.out.println("Usuário não encontrado.");
             return false;
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
     return false;
 }

 private void listarUsuarios() {
     String query = "SELECT userID, nome, CPF, email, statusConta, funcao FROM Usuarios";
     try (Connection conn = DBConnection.getConnection();
          PreparedStatement pstmt = conn.prepareStatement(query);
          ResultSet rs = pstmt.executeQuery()) {

         System.out.println("----- Lista de Usuários -----");
         while (rs.next()) {
             int id = rs.getInt("userID");
             String nome = rs.getString("nome");
             String CPF = rs.getString("CPF");
             String email = rs.getString("email");
             String status = rs.getString("statusConta");
             String funcao = rs.getString("funcao");
             System.out.println("ID: " + id + ", Nome: " + nome + ", CPF: " + CPF +", Email: " + email +
                                ", Status: " + status + ", Função: " + funcao);
         }
         System.out.println("------------------------------");
     } catch (SQLException e) {
         e.printStackTrace();
     }
 }

 
 public boolean cadastrarLivro(Livro livro) {
     return livro.adicionarLivro();
 }

 public boolean gerenciarUsuarios() {
     // Método para listar todos usuários
     listarUsuarios();
     return true;
 }

}
