package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Lanchonete {
    private Cardapio cardapio;
    private String nome;
    private List<Cliente> clientes;

    public Lanchonete(String nome) {
        this.nome = nome;
        this.cardapio = new Cardapio();
        this.clientes = new ArrayList<>();
    }

    public void addCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }

    public List<Cliente> buscarClienteNome(String nome) {
        List<Cliente> clientesEncontrados = new LinkedList<>();
        for (Cliente cliente : clientes) {
            if (cliente.getNome().equalsIgnoreCase(nome)) {
                clientesEncontrados.add(cliente);
            }
        }
        return clientesEncontrados;
    }

    public Cardapio getCardapio() {
        return cardapio;
    }

    public void setCardapio(Cardapio cardapio) {
        this.cardapio = cardapio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    @Override
    public String toString() {
        return "Lanchonete{" +
                "nome='" + nome + '\'' +
                ", clientes=" + clientes +
                ", cardapio=" + cardapio +
                '}';
    }
}