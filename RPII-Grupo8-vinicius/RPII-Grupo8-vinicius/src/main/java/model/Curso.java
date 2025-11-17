package model;

public class Curso {
    private String id;
    private String titulo;
    private String descricao;
    private String professorId;
    private StatusCurso status;
    private String pinAcesso; // Proteção por PIN

    public Curso(String id, String titulo, String descricao, String professorId) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.professorId = professorId;
        this.status = StatusCurso.PENDENTE_APROVACAO;
        this.pinAcesso = null;
    }

    // Getters
    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public String getProfessorId() { return professorId; }
    public StatusCurso getStatus() { return status; }
    public String getPinAcesso() { return pinAcesso; }

    // Setters (essenciais para os requisitos)
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setStatus(StatusCurso status) { this.status = status; }
    public void setPinAcesso(String pinAcesso) { this.pinAcesso = pinAcesso; }

    @Override
    public String toString() {
        return "{"
            + "\"id\":\"" + id + "\","
            + "\"titulo\":\"" + titulo + "\","
            + "\"descricao\":\"" + descricao + "\","
            + "\"professorId\":\"" + professorId + "\","
            + "\"status\":\"" + status.name() + "\","
            + "\"pinAcesso\":" + (pinAcesso == null ? "null" : "\"" + pinAcesso + "\"")
            + "}";
    }
}