package org.example;

import java.util.ArrayList;
import java.util.List;

public class Cardapio {
    private List<ItemCardapio> itens;

    public Cardapio() {
        this.itens = new ArrayList<>();
    }

    public List<ItemCardapio> getItens() {
        return itens;
    }

    public void addItem(ItemCardapio item) {
        this.itens.add(item);
    }

    public ItemCardapio buscarItemNome(String nome) {
        for (ItemCardapio item : itens) {
            if (item.getNome().equalsIgnoreCase(nome)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Cardapio{" +
                "itens=" + itens +
                '}';
    }
}