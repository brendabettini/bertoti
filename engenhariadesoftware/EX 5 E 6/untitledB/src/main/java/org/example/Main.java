package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        Lanchonete lanchonete = new Lanchonete("Lanchonete da Ana");

        ItemCardapio xBurger = new ItemCardapio("X-Burger", 15.00, "Pão, carne, queijo");
        ItemCardapio xSalada = new ItemCardapio("X-Salada", 18.00, "Pão, carne, queijo, alface, tomate");

        lanchonete.getCardapio().addItem(xBurger);
        lanchonete.getCardapio().addItem(xSalada);

        Cliente cliente1 = new Cliente("Joao", "PIX");
        Cliente cliente2 = new Cliente("Maria", "Crédito");
        Cliente cliente3 = new Cliente("Joao", "Débito");

        lanchonete.addCliente(cliente1);
        lanchonete.addCliente(cliente2);
        lanchonete.addCliente(cliente3);

        System.out.println("--- DADOS DA LANCHONETE ---");
        System.out.println(lanchonete);


        System.out.println("\n--- BUSCANDO ITEM 'X-Burger' ---");
        ItemCardapio itemBuscado = lanchonete.getCardapio().buscarItemNome("X-Burger");
        if (itemBuscado != null) {
            System.out.println("Resultado: " + itemBuscado);
        } else {
            System.out.println("Item não encontrado.");
        }


        System.out.println("\n--- BUSCANDO CLIENTE 'Joao' ---");
        List<Cliente> clientesBuscados = lanchonete.buscarClienteNome("Joao");
        if (clientesBuscados.isEmpty()) {
            System.out.println("Nenhum cliente com nome 'Joao' encontrado.");
        } else {
            System.out.println("Resultados: " + clientesBuscados);
        }

        System.out.println("\n--- BUSCANDO ITEM 'Suco' (não existe) ---");
        ItemCardapio itemNaoExiste = lanchonete.getCardapio().buscarItemNome("Suco");
        if (itemNaoExiste == null) {
            System.out.println("Item 'Suco' não encontrado.");
        }
    }
}