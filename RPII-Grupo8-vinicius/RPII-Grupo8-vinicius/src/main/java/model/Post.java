package model;

import java.time.LocalDateTime;

public class Post {
    private String id;
    private String titulo;
    private String conteudo;
    private String autorId;
    private int curtidas;
    private LocalDateTime dataCriacao;
    private String[] tags;

    public Post(String id, String titulo, String conteudo, String autorId, String[] tags) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.autorId = autorId;
        this.curtidas = 0;
        this.dataCriacao = LocalDateTime.now();
        this.tags = tags;
    }

    // Getters
    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getConteudo() { return conteudo; }
    public String getAutorId() { return autorId; }
    public int getCurtidas() { return curtidas; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public String[] getTags() { return tags; }

    // Setters (essenciais para os requisitos)
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
    public void incrementarCurtidas() { this.curtidas++; }
    public void decrementarCurtidas() { this.curtidas = Math.max(0, this.curtidas - 1); }

    @Override
    public String toString() {
        return "{"
            + "\"id\":\"" + id + "\","
            + "\"titulo\":\"" + titulo + "\","
            + "\"conteudo\":\"" + conteudo.substring(0, Math.min(20, conteudo.length())) + "...\","
            + "\"autorId\":\"" + autorId + "\","
            + "\"curtidas\":" + curtidas + ","
            + "\"dataCriacao\":\"" + dataCriacao.toString() + "\","
            + "\"tags\":[\"" + String.join("\",\"", tags) + "\"]"
            + "}";
    }
}