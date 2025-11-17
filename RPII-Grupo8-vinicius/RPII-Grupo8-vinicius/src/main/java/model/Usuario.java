package model;

import java.util.HashMap;
import java.util.Map;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private PapelUsuario papel;
    private Map<String, String> redesSociais; // Ex: {"github": "link", "linkedin": "link"}

    public Usuario(String id, String nome, String email, PapelUsuario papel) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.papel = papel;
        this.redesSociais = new HashMap<>();
    }

    // Getters
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public PapelUsuario getPapel() { return papel; }
    public Map<String, String> getRedesSociais() { return redesSociais; }

    // Setters (essenciais para os requisitos)
    public void setNome(String nome) { this.nome = nome; }
    public void setPapel(PapelUsuario papel) { this.papel = papel; }
    public void setRedesSociais(Map<String, String> redesSociais) { this.redesSociais = redesSociais; }

    // Método para simplificar a conversão para JSON (simples)
    @Override
    public String toString() {
        return "{"
            + "\"id\":\"" + id + "\","
            + "\"nome\":\"" + nome + "\","
            + "\"email\":\"" + email + "\","
            + "\"papel\":\"" + papel.name() + "\","
            + "\"redesSociais\":" + mapToJson(redesSociais)
            + "}";
    }

    private String mapToJson(Map<String, String> map) {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!first) sb.append(",");
            sb.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\"");
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }
}