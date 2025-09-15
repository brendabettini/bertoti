package com.seunome.biblioteca;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Conexao.inicializar();
        Biblioteca biblioteca = new Biblioteca();        
        try (Scanner sc = new Scanner(System.in)) {
            OUTER:
            while (true) {
                System.out.println("\n1 - Adicionar Usuário");
                System.out.println("2 - Adicionar Livro");
                System.out.println("3 - Registrar Empréstimo");
                System.out.println("4 - Listar Usuários");
                System.out.println("5 - Listar Livros");
                System.out.println("0 - Sair");
                System.out.print("Escolha: ");
                int op = sc.nextInt();
                sc.nextLine();
                switch (op) {
                    case 1 -> {
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Email: ");
                        String email = sc.nextLine();
                        biblioteca.adicionarUsuario(new Usuario(nome, email));
                    }
                    case 2 -> {
                        System.out.print("Título: ");
                        String titulo = sc.nextLine();
                        System.out.print("Autor: ");
                        String autor = sc.nextLine();
                        biblioteca.adicionarLivro(new Livro(titulo, autor));
                    }
                    case 3 -> {
                        System.out.print("ID Usuário: ");
                        int usuarioId = sc.nextInt();
                        System.out.print("ID Livro: ");
                        int livroId = sc.nextInt();
                        biblioteca.registrarEmprestimo(new Emprestimo(usuarioId, livroId));
                    }
                    case 4 -> biblioteca.listarUsuarios().forEach(u ->
                                System.out.println(u.getId() + " - " + u.getNome() + " (" + u.getEmail() + ")"));
                    case 5 -> biblioteca.listarLivros().forEach(l ->
                                System.out.println(l.getId() + " - " + l.getTitulo() + " - " + l.getAutor() +
                                        " | Disponível: " + (l.isDisponivel() ? "Sim" : "Não")));
                    case 0 -> {
                        break OUTER;
                    }
                    default -> {
                    }
                }
            }
        }
    }
}
