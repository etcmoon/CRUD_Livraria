package database;

//DatabaseInitialization.java
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitialization {
 public static void initializeDatabase() {
     String createUsuarios = "CREATE TABLE IF NOT EXISTS Usuarios (" +
             "userID INTEGER PRIMARY KEY AUTOINCREMENT," +
             "CPF TEXT NOT NULL," +
             "nome TEXT NOT NULL," +
             "email TEXT UNIQUE NOT NULL," +
             "telefone TEXT," +
             "endereco TEXT," +
             "dataCadastro TEXT," +
             "statusConta TEXT," +
             "funcao TEXT," +
             "senha TEXT NOT NULL" +
             ");";

     String createLivro = "CREATE TABLE IF NOT EXISTS Livro (" +
             "livroID INTEGER PRIMARY KEY AUTOINCREMENT," +
             "titulo TEXT NOT NULL," +
             "autor TEXT," +
             "editora TEXT," +
             "categoria TEXT," +
             "anoPublicacao INTEGER," +
             "quantidadeDisponivel INTEGER," +
             "isbn TEXT UNIQUE," +
             "disponivel BOOLEAN" +
             ");";

     String createEmprestimo = "CREATE TABLE IF NOT EXISTS Emprestimo (" +
             "emprestID INTEGER PRIMARY KEY AUTOINCREMENT," +
             "clienteID INTEGER," +
             "livroID INTEGER," +
             "dataEmprestimo TEXT," +
             "dataDevolucaoPrevista TEXT," +
             "dataDevolucaoEfetiva TEXT," +
             "status TEXT," +
             "multa INTEGER," +
             "FOREIGN KEY(clienteID) REFERENCES Usuarios(userID)," +
             "FOREIGN KEY(livroID) REFERENCES Livro(livroID)" +
             ");";

     String createFilaEspera = "CREATE TABLE IF NOT EXISTS FilaEspera (" +
             "filaID INTEGER PRIMARY KEY AUTOINCREMENT," +
             "livroID INTEGER," +
             "clienteID INTEGER," +
             "dataSolicitacao TEXT," +
             "status TEXT," +
             "FOREIGN KEY(livroID) REFERENCES Livro(livroID)," +
             "FOREIGN KEY(clienteID) REFERENCES Usuarios(userID)" +
             ");";

     String createMultas = "CREATE TABLE IF NOT EXISTS Multas (" +
             "multaID INTEGER PRIMARY KEY AUTOINCREMENT," +
             "emprestimoID INTEGER," +
             "valor REAL," +
             "dataAplicacao TEXT," +
             "statusPagamento TEXT," +
             "FOREIGN KEY(emprestimoID) REFERENCES Emprestimo(emprestID)" +
             ");";

     String createNotificacoes = "CREATE TABLE IF NOT EXISTS Notificacoes (" +
             "notificacaoID INTEGER PRIMARY KEY AUTOINCREMENT," +
             "destinatarioID INTEGER," +
             "mensagem TEXT," +
             "dataEnvio TEXT," +
             "status TEXT," +
             "FOREIGN KEY(destinatarioID) REFERENCES Usuarios(userID)" +
             ");";

     try (Connection conn = DBConnection.getConnection();
          Statement stmt = conn.createStatement()) {
         stmt.execute(createUsuarios);
         stmt.execute(createLivro);
         stmt.execute(createEmprestimo);
         stmt.execute(createFilaEspera);
         stmt.execute(createMultas);
         stmt.execute(createNotificacoes);
     } catch (SQLException e) {
         e.printStackTrace();
     }
 }
}