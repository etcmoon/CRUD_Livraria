package main;

import database.*;
//Main.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
 public static void main(String[] args) {
     // Inicializa o banco de dados
     DatabaseInitialization.initializeDatabase();

     Scanner scanner = new Scanner(System.in);
     Administrador admin = new Administrador();

     System.out.println("=== Projeto Piloto Inicial - Sistema de Biblioteca ===");
     
     // Menu principal
     boolean exit = false;
     while (!exit) {
         System.out.println("\n=== Tela Inicial ===");
         System.out.println("1. Cliente");
         System.out.println("2. Administrador");
         System.out.println("3. Criar Conta");
         System.out.println("4. Sair");
         System.out.print("Escolha uma opção: ");
         String opcao = scanner.nextLine();

         switch (opcao) {
             case "1":
                 loginCliente(scanner);
                 break;
             case "2":
                 loginAdministrador(scanner, admin);
                 break;
             case "3":
                 criarContaCliente(scanner);
                 break;
             case "4":
                 exit = true;
                 System.out.println("Encerrando o sistema. Até logo!");
                 break;
             default:
                 System.out.println("Opção inválida. Tente novamente.");
         }
     }

     scanner.close();
 }


 /**
  * Processo de login para Cliente.
  *
  * @param scanner Scanner para entrada de dados
  */
 private static void loginCliente(Scanner scanner) {
     System.out.println("\n--- Login Cliente ---");
     System.out.print("Email: ");
     String email = scanner.nextLine();
     System.out.print("Senha: ");
     String senha = scanner.nextLine();

     Cliente cliente = new Cliente();
     if (cliente.login(email, senha)) {
         System.out.println("Login bem-sucedido. Bem-vindo, " + cliente.getNome() + "!");
         menuCliente(scanner, cliente);
     } else {
         System.out.println("Email ou senha incorretos. Retornando ao menu inicial.");
     }
 }

 /**
  * Menu específico para Cliente após login.
  *
  * @param scanner  Scanner para entrada de dados
  * @param cliente  Objeto Cliente logado
  */
 private static void menuCliente(Scanner scanner, Cliente cliente) {
     boolean logout = false;
     while (!logout) {
         System.out.println("\n--- Menu Cliente ---");
         System.out.println("1. Solicitar Empréstimo de Livro");
         System.out.println("2. Devolver Livro");
         System.out.println("3. Renovar Empréstimo");
         System.out.println("4. Visualizar Histórico de Empréstimos");
         System.out.println("5. Visualizar Multas");
         System.out.println("6. Logout");
         System.out.print("Escolha uma opção: ");
         String opcao = scanner.nextLine();

         switch (opcao) {
             case "1":
                 solicitarEmprestimo(scanner, cliente);
                 break;
             case "2":
                 devolverLivro(scanner, cliente);
                 break;
             case "3":
                 renovarEmprestimo(scanner, cliente);
                 break;
             case "4":
                 visualizarHistorico(cliente);
                 break;
             case "5":
                 visualizarMultas(cliente);
                 break;
             case "6":
                 logout = true;
                 cliente.logout();
                 System.out.println("Logout realizado. Retornando ao menu inicial.");
                 break;
             default:
                 System.out.println("Opção inválida. Tente novamente.");
         }
     }
 }

 /**
  * Processo de login para Administrador.
  *
  * @param scanner Scanner para entrada de dados
  * @param admin   Objeto Administrador
  */
 private static void loginAdministrador(Scanner scanner, Administrador admin) {
     System.out.println("\n--- Login Administrador ---");
     System.out.print("Email: ");
     String email = scanner.nextLine();
     System.out.print("Senha: ");
     String senha = scanner.nextLine();

     if (admin.login(email, senha)) {
         System.out.println("Login bem-sucedido. Bem-vindo, " + admin.getNome() + "!");
         menuAdministrador(scanner, admin);
     } else {
         System.out.println("Email ou senha incorretos. Retornando ao menu inicial.");
     }
 }

 /**
  * Menu específico para Administrador após login.
  *
  * @param scanner Scanner para entrada de dados
  * @param admin   Objeto Administrador logado
  */
 private static void menuAdministrador(Scanner scanner, Administrador admin) {
     boolean logout = false;
     while (!logout) {
         System.out.println("\n--- Menu Administrador ---");
         System.out.println("1. Cadastrar Cliente");
         System.out.println("2. Atualizar Cadastro de Cliente");
         System.out.println("3. Gerenciar Usuários");
         System.out.println("4. Cadastrar Livro");
         System.out.println("5. Logout");
         System.out.print("Escolha uma opção: ");
         String opcao = scanner.nextLine();

         switch (opcao) {
             case "1":
                 cadastrarCliente(admin, scanner);
                 break;
             case "2":
                 atualizarCliente(admin, scanner);
                 break;
             case "3":
                 gerenciarUsuarios(admin, scanner);
                 break;
             case "4":
                 cadastrarLivro(admin, scanner);
                 break;
             case "5":
                 logout = true;
                 admin.logout();
                 System.out.println("Logout realizado. Retornando ao menu inicial.");
                 break;
             default:
                 System.out.println("Opção inválida. Tente novamente.");
         }
     }
 }

 // Processo de criação de uma nova conta Cliente.
 
 private static void criarContaCliente(Scanner scanner) {
     System.out.println("\n--- Criar Nova Conta Cliente ---");
     Cliente cliente = new Cliente();

     System.out.print("CPF: ");
     cliente.setCPF(scanner.nextLine());

     System.out.print("Nome: ");
     cliente.setNome(scanner.nextLine());

     System.out.print("Email: ");
     cliente.setEmail(scanner.nextLine());

     System.out.print("Telefone: ");
     cliente.setTelefone(scanner.nextLine());

     System.out.print("Endereço: ");
     cliente.setEndereco(scanner.nextLine());

     System.out.print("Senha: ");
     cliente.setSenha(scanner.nextLine());

     cliente.setFuncao("Cliente");

     if (cliente.realizarCadastro()) {
         System.out.println("Conta criada com sucesso. Você pode agora fazer login.");
     } else {
         System.out.println("Falha ao criar conta. Verifique se o email já está em uso.");
     }
 }

 // Cadastra um novo Cliente através do Administrador.
 
 private static void cadastrarCliente(Administrador admin, Scanner scanner) {
     System.out.println("\n--- Cadastrar Novo Cliente ---");
     Cliente cliente = new Cliente();

     System.out.print("CPF: ");
     cliente.setCPF(scanner.nextLine());

     System.out.print("Nome: ");
     cliente.setNome(scanner.nextLine());

     System.out.print("Email: ");
     cliente.setEmail(scanner.nextLine());

     System.out.print("Telefone: ");
     cliente.setTelefone(scanner.nextLine());

     System.out.print("Endereço: ");
     cliente.setEndereco(scanner.nextLine());

     System.out.print("Senha: ");
     cliente.setSenha(scanner.nextLine());

     cliente.setFuncao("Cliente");

     if (admin.cadastrarCliente(cliente)) {
         System.out.println("Cliente cadastrado com sucesso.");
     } else {
         System.out.println("Falha ao cadastrar cliente. Verifique se o email já está em uso.");
     }
 }

 /**
  * Atualiza o cadastro de um Cliente existente através do Administrador.
  *
  * @param admin   Objeto Administrador
  * @param scanner Scanner para entrada de dados
  */
 private static void atualizarCliente(Administrador admin, Scanner scanner) {
     System.out.println("\n--- Atualizar Cadastro de Cliente ---");
     System.out.print("Digite o ID do cliente a ser atualizado: ");
     int userID;
     try {
         userID = Integer.parseInt(scanner.nextLine());
     } catch (NumberFormatException e) {
         System.out.println("ID inválido.");
         return;
     }

     // Buscar cliente existente
     Cliente cliente = buscarClientePorID(userID);
     if (cliente == null) {
         System.out.println("Cliente não encontrado.");
         return;
     }

     System.out.println("Deixe em branco para manter o valor atual.");

     System.out.print("CPF (" + cliente.getCPF() + "): ");
     String cpf = scanner.nextLine();
     if (!cpf.isEmpty()) {
         cliente.setCPF(cpf);
     }

     System.out.print("Nome (" + cliente.getNome() + "): ");
     String nome = scanner.nextLine();
     if (!nome.isEmpty()) {
         cliente.setNome(nome);
     }

     System.out.print("Email (" + cliente.getEmail() + "): ");
     String email = scanner.nextLine();
     if (!email.isEmpty()) {
         cliente.setEmail(email);
     }

     System.out.print("Telefone (" + cliente.getTelefone() + "): ");
     String telefone = scanner.nextLine();
     if (!telefone.isEmpty()) {
         cliente.setTelefone(telefone);
     }

     System.out.print("Endereço (" + cliente.getEndereco() + "): ");
     String endereco = scanner.nextLine();
     if (!endereco.isEmpty()) {
         cliente.setEndereco(endereco);
     }

     System.out.print("Senha: ");
     String senha = scanner.nextLine();
     if (!senha.isEmpty()) {
         cliente.setSenha(senha);
     }

     if (admin.atualizarCliente(cliente)) {
         System.out.println("Cliente atualizado com sucesso.");
     } else {
         System.out.println("Falha ao atualizar cliente.");
     }
 }

 /**
  * Busca um Cliente pelo seu ID.
  *
  * @param userID ID do Cliente
  * @return Objeto Cliente se encontrado, null caso contrário
  */
 private static Cliente buscarClientePorID(int userID) {
     String query = "SELECT * FROM Usuarios WHERE userID = ? AND funcao = 'Cliente'";
     try (Connection conn = DBConnection.getConnection();
          PreparedStatement pstmt = conn.prepareStatement(query)) {
         pstmt.setInt(1, userID);
         ResultSet rs = pstmt.executeQuery();
         if (rs.next()) {
             Cliente cliente = new Cliente();
             cliente.setUserID(rs.getInt("userID"));
             cliente.setCPF(rs.getString("CPF"));
             cliente.setNome(rs.getString("nome"));
             cliente.setEmail(rs.getString("email"));
             cliente.setTelefone(rs.getString("telefone"));
             cliente.setEndereco(rs.getString("endereco"));
             cliente.setDataCadastro(rs.getString("dataCadastro"));
             cliente.setStatusConta(rs.getString("statusConta"));
             cliente.setFuncao(rs.getString("funcao"));
             cliente.setSenha(rs.getString("senha"));
             return cliente;
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
     return null;
 }

 /**
  * Gerencia Usuários através do menu interativo do Administrador.
  *
  * @param admin   Objeto Administrador
  * @param scanner Scanner para entrada de dados
  */
 private static void gerenciarUsuarios(Administrador admin, Scanner scanner) {
     System.out.println("\n--- Gerenciar Usuários ---");
     System.out.println("1. Ativar Usuário");
     System.out.println("2. Desativar Usuário");
     System.out.println("3. Listar Todos os Usuários");
     System.out.print("Escolha uma opção: ");
     String opcao = scanner.nextLine();

     switch (opcao) {
         case "1":
             System.out.print("Digite o ID do usuário a ativar: ");
             int ativarID;
             try {
                 ativarID = Integer.parseInt(scanner.nextLine());
             } catch (NumberFormatException e) {
                 System.out.println("ID inválido.");
                 return;
             }
             if (admin.gerenciarUsuarios(ativarID, "ativar")) {
                 System.out.println("Usuário ativado com sucesso.");
             } else {
                 System.out.println("Falha ao ativar usuário.");
             }
             break;
         case "2":
             System.out.print("Digite o ID do usuário a desativar: ");
             int desativarID;
             try {
                 desativarID = Integer.parseInt(scanner.nextLine());
             } catch (NumberFormatException e) {
                 System.out.println("ID inválido.");
                 return;
             }
             if (admin.gerenciarUsuarios(desativarID, "desativar")) {
                 System.out.println("Usuário desativado com sucesso.");
             } else {
                 System.out.println("Falha ao desativar usuário.");
             }
             break;
         case "3":
             admin.gerenciarUsuarios(Integer.MIN_VALUE, "listar"); // Usa um valor inválido para listar todos
             break;
         default:
             System.out.println("Opção inválida.");
     }
 }

 /**
  * Cadastra um novo Livro através do Administrador.
  *
  * @param admin   Objeto Administrador
  * @param scanner Scanner para entrada de dados
  */
 private static void cadastrarLivro(Administrador admin, Scanner scanner) {
     System.out.println("\n--- Cadastrar Novo Livro ---");
     Livro livro = new Livro();

     System.out.print("Título: ");
     livro.setTitulo(scanner.nextLine());

     System.out.print("Autor: ");
     livro.setAutor(scanner.nextLine());

     System.out.print("Editora: ");
     livro.setEditora(scanner.nextLine());

     System.out.print("Categoria: ");
     livro.setCategoria(scanner.nextLine());

     System.out.print("Ano de Publicação: ");
     String anoInput = scanner.nextLine();
     try {
         livro.setAnoPublicacao(Integer.parseInt(anoInput));
     } catch (NumberFormatException e) {
         System.out.println("Ano de publicação inválido. Definindo como 2024.");
         livro.setAnoPublicacao(2024);
     }

     System.out.print("Quantidade Disponível: ");
     String quantidadeInput = scanner.nextLine();
     try {
         livro.setQuantidadeDisponivel(Integer.parseInt(quantidadeInput));
     } catch (NumberFormatException e) {
         System.out.println("Quantidade inválida. Definindo como 1.");
         livro.setQuantidadeDisponivel(1);
     }

     System.out.print("ISBN: ");
     livro.setIsbn(scanner.nextLine());

     livro.setDisponivel(livro.getQuantidadeDisponivel() > 0);

     if (admin.cadastrarLivro(livro)) {
         System.out.println("Livro cadastrado com sucesso.");
     } else {
         System.out.println("Falha ao cadastrar livro. Verifique se o ISBN já está em uso.");
     }
 }

 /**
  * Solicita o empréstimo de um livro pelo Cliente.
  *
  * @param scanner Scanner para entrada de dados
  * @param cliente Objeto Cliente logado
  */
 private static void solicitarEmprestimo(Scanner scanner, Cliente cliente) {
     System.out.println("\n--- Solicitar Empréstimo de Livro ---");
     System.out.print("Digite o ID do livro que deseja emprestar: ");
     int livroID;
     try {
         livroID = Integer.parseInt(scanner.nextLine());
     } catch (NumberFormatException e) {
         System.out.println("ID inválido.");
         return;
     }

     Livro livro = new Livro();
     livro = livro.buscarLivro(livroID);
     if (livro == null) {
         System.out.println("Livro não encontrado.");
         return;
     }

     if (!livro.verificarDisponibilidade()) {
         System.out.println("Livro indisponível no momento.");
         // Opcional: Adicionar Cliente à Fila de Espera
         System.out.print("Deseja ser adicionado à fila de espera? (s/n): ");
         String resposta = scanner.nextLine();
         if (resposta.equalsIgnoreCase("s")) {
             FilaEspera fila = new FilaEspera();
             fila.setLivroID(livroID);
             fila.setClienteID(cliente.getUserID());
             if (fila.adicionarFila()) {
                 System.out.println("Adicionado à fila de espera com sucesso.");
             } else {
                 System.out.println("Falha ao adicionar à fila de espera.");
             }
         }
         return;
     }

     // Registrar Empréstimo
     Emprestimo emprestimo = new Emprestimo();
     emprestimo.setCliente(cliente);
     emprestimo.setLivro(livro);
     if (emprestimo.registrarEmprestimo()) {
         System.out.println("Empréstimo realizado com sucesso.");
     } else {
         System.out.println("Falha ao realizar empréstimo.");
     }
 }

 /**
  * Processo de devolução de um livro pelo Cliente.
  *
  * @param scanner Scanner para entrada de dados
  * @param cliente Objeto Cliente logado
  */
 private static void devolverLivro(Scanner scanner, Cliente cliente) {
     System.out.println("\n--- Devolver Livro ---");
     System.out.print("Digite o ID do empréstimo a ser devolvido: ");
     int emprestID;
     try {
         emprestID = Integer.parseInt(scanner.nextLine());
     } catch (NumberFormatException e) {
         System.out.println("ID inválido.");
         return;
     }

     // Buscar empréstimo
     Emprestimo emprestimo = buscarEmprestimoPorID(emprestID, cliente.getUserID());
     if (emprestimo == null) {
         System.out.println("Empréstimo não encontrado ou não pertence a você.");
         return;
     }

     if (emprestimo.getStatus().equalsIgnoreCase("Devolvido")) {
         System.out.println("Este empréstimo já foi devolvido.");
         return;
     }

     if (emprestimo.registrarDevolucao()) {
         System.out.println("Livro devolvido com sucesso.");
         // Verificar se há fila de espera e notificar o próximo cliente
         verificarFilaEspera(emprestimo.getLivro().getLivroID());
     } else {
         System.out.println("Falha ao registrar devolução.");
     }
 }

 /**
  * Busca um Empréstimo pelo seu ID e verifica se pertence ao Cliente.
  *
  * @param emprestID ID do Empréstimo
  * @param clienteID ID do Cliente
  * @return Objeto Emprestimo se encontrado, null caso contrário
  */
 private static Emprestimo buscarEmprestimoPorID(int emprestID, int clienteID) {
     String query = "SELECT * FROM Emprestimo WHERE emprestID = ? AND clienteID = ?";
     try (Connection conn = DBConnection.getConnection();
          PreparedStatement pstmt = conn.prepareStatement(query)) {
         pstmt.setInt(1, emprestID);
         pstmt.setInt(2, clienteID);
         ResultSet rs = pstmt.executeQuery();
         if (rs.next()) {
             Emprestimo emprestimo = new Emprestimo();
             emprestimo.setEmprestID(rs.getInt("emprestID"));
             emprestimo.setStatus(rs.getString("status"));
             // Configurar outros atributos conforme necessário
             Livro livro = new Livro();
             livro.setLivroID(rs.getInt("livroID"));
             emprestimo.setLivro(livro);
             return emprestimo;
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
     return null;
 }

 /**
  * Renova um Empréstimo pelo Cliente.
  *
  * @param scanner Scanner para entrada de dados
  * @param cliente Objeto Cliente logado
  */
 private static void renovarEmprestimo(Scanner scanner, Cliente cliente) {
     System.out.println("\n--- Renovar Empréstimo ---");
     System.out.print("Digite o ID do empréstimo a ser renovado: ");
     int emprestID;
     try {
         emprestID = Integer.parseInt(scanner.nextLine());
     } catch (NumberFormatException e) {
         System.out.println("ID inválido.");
         return;
     }

     // Buscar empréstimo
     Emprestimo emprestimo = buscarEmprestimoPorID(emprestID, cliente.getUserID());
     if (emprestimo == null) {
         System.out.println("Empréstimo não encontrado ou não pertence a você.");
         return;
     }

     if (!emprestimo.getStatus().equalsIgnoreCase("Ativo")) {
         System.out.println("Este empréstimo não está ativo e não pode ser renovado.");
         return;
     }

     if (emprestimo.renovarEmprestimo()) {
         System.out.println("Empréstimo renovado com sucesso.");
     } else {
         System.out.println("Falha ao renovar empréstimo.");
     }
 }

 /**
  * Visualiza o histórico de Empréstimos do Cliente.
  *
  * @param cliente Objeto Cliente logado
  */
 private static void visualizarHistorico(Cliente cliente) {
     System.out.println("\n--- Histórico de Empréstimos ---");
     List<Emprestimo> historico = cliente.visualizarHistorico();
     if (historico == null || historico.isEmpty()) {
         System.out.println("Nenhum empréstimo encontrado.");
         return;
     }

     for (Emprestimo emp : historico) {
         System.out.println("ID Empréstimo: " + emp.getEmprestID() +
                            ", Livro ID: " + emp.getLivro().getLivroID() +
                            ", Data Empréstimo: " + emp.getDataEmprestimo() +
                            ", Data Devolução Prevista: " + emp.getDataDevolucaoPrevista() +
                            ", Status: " + emp.getStatus());
     }
 }

 /**
  * Visualiza as Multas do Cliente.
  *
  * @param cliente Objeto Cliente logado
  */
 private static void visualizarMultas(Cliente cliente) {
     System.out.println("\n--- Multas ---");
     List<Multa> multas = cliente.visualizarMultas();
     if (multas == null || multas.isEmpty()) {
         System.out.println("Nenhuma multa encontrada.");
         return;
     }

     for (Multa multa : multas) {
         System.out.println("ID Multa: " + multa.getMultaID() +
                            ", Valor: R$" + multa.getValor() +
                            ", Data Aplicação: " + multa.getDataAplicacao() +
                            ", Status Pagamento: " + multa.getStatusPagamento());
     }
 }

 /**
  * Verifica se há fila de espera para um livro e notifica o próximo cliente se aplicável.
  *
  * @param livroID ID do Livro
  */
 private static void verificarFilaEspera(int livroID) {
     FilaEspera fila = new FilaEspera();
     List<FilaEspera> filaList = fila.listarFila(livroID);

     if (filaList.isEmpty()) {
         System.out.println("Não há clientes na fila de espera para este livro.");
         return;
     }

     // Notificar o próximo cliente na fila
     FilaEspera proximo = filaList.get(0);
     Notificacao notificacao = new Notificacao();
     notificacao.setDestinatarioID(proximo.getClienteID());
     notificacao.setMensagem("O livro com ID " + livroID + " está disponível para empréstimo.");
     if (notificacao.enviarNotificacao()) {
         System.out.println("Notificação enviada para o próximo cliente na fila de espera.");
         // Remover o cliente da fila após notificação
         fila.removerFila(proximo.getFilaID());
     } else {
         System.out.println("Falha ao enviar notificação.");
     }
 }
}