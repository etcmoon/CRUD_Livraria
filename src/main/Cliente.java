package main;
import database.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Cliente extends Usuario {
    private List<Emprestimo> emprestimosAtivos;
    private List<FilaEspera> filaEspera;
 
public Cliente(int userID, String CPF, String nome, String email, String telefone, String endereco,
            String dataCadastro, String statusConta, String funcao, String senha,
            List<Emprestimo> emprestimosAtivos, List<FilaEspera> filaEspera) {
super();
}

public Cliente() {
	super();
}

//Getters and Setters
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
public List<Emprestimo> getEmprestimosAtivos() {
	return emprestimosAtivos;
}

public void setEmprestimosAtivos(List<Emprestimo> emprestimosAtivos) {
	this.emprestimosAtivos = emprestimosAtivos;
}

public List<FilaEspera> getFilaEspera() {
	return filaEspera;
}

public void setFilaEspera(List<FilaEspera> filaEspera) {
	this.filaEspera = filaEspera;
}

public List<Emprestimo> visualizarHistorico() {
    List<Emprestimo> historico = new ArrayList<>();
    String query = "SELECT * FROM Emprestimo WHERE clienteID = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, this.getUserID());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Emprestimo emp = new Emprestimo();
            emp.setEmprestID(rs.getInt("emprestID"));
            emp.setCliente(this);
            Livro livro = new Livro();
            livro.setLivroID(rs.getInt("livroID"));
            emp.setLivro(livro);
            emp.setDataEmprestimo(rs.getString("dataEmprestimo"));
            emp.setDataDevolucaoPrevista(rs.getString("dataDevolucaoPrevista"));
            emp.setDataDevolucaoEfetiva(rs.getString("dataDevolucaoEfetiva"));
            emp.setStatus(rs.getString("status"));
            historico.add(emp);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return historico;
}

public List<Multa> visualizarMultas() {
    List<Multa> multasList = new ArrayList<>();
    String query = "SELECT m.* FROM Multas m JOIN Emprestimo e ON m.emprestimoID = e.emprestimoID WHERE e.clienteID = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, this.getUserID());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Multa multa = new Multa();
            multa.setMultaID(rs.getInt("multaID"));
            multa.setEmprestimoID(rs.getInt("emprestimoID"));
            multa.setValor(rs.getDouble("valor"));
            multa.setDataAplicacao(rs.getString("dataAplicacao"));
            multa.setStatusPagamento(rs.getString("statusPagamento"));
            multasList.add(multa);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return multasList;
}
}