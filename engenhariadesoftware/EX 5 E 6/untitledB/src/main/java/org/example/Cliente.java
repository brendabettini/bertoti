package org.example;

public class Cliente {
    private String nome;
    private String tipoPagamento;

    public Cliente(String nome, String tipoPagamento) {
        this.nome = nome;
        this.tipoPagamento = tipoPagamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", tipoPagamento='" + tipoPagamento + '\'' +
                '}';
    }
}