package org.example;

public class ItemCardapio {
    private String nome;
    private double valor;
    private String ingredientes;

    public ItemCardapio(String nome, double valor, String ingredientes) {
        this.nome = nome;
        this.valor = valor;
        this.ingredientes = ingredientes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    @Override
    public String toString() {
        return "ItemCardapio{" +
                "nome='" + nome + '\'' +
                ", valor=" + valor +
                ", ingredientes='" + ingredientes + '\'' +
                '}';
    }
}