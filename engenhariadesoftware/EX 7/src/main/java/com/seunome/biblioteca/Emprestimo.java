package com.seunome.biblioteca;

public class Emprestimo {
    private int id;
    private int usuarioId;
    private int livroId;

    public Emprestimo(int id, int usuarioId, int livroId) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.livroId = livroId;
    }

    public Emprestimo(int usuarioId, int livroId) {
        this(0, usuarioId, livroId);
    }

    public int getId() {
        return id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public int getLivroId() {
        return livroId;
    }
}