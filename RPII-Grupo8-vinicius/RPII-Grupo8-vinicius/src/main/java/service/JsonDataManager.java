package service;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonDataManager {
    private List<Usuario> usuarios;
    private List<Curso> cursos;
    private List<Post> posts;

    public JsonDataManager() {
        // Dados simulados para testes
        this.usuarios = createSimulatedUsers();
        this.cursos = createSimulatedCursos();
        this.posts = createSimulatedPosts();
    }

    // Métodos para acesso aos dados
    public List<Usuario> getUsuarios() { return usuarios; }
    public List<Curso> getCursos() { return cursos; }
    public List<Post> getPosts() { return posts; }

    // Métodos que simulam a escrita em JSON (para referência em testes)
    public String exportUsuariosToJson() {
        return "[\n" + String.join(",\n", usuarios.stream().map(Usuario::toString).toArray(String[]::new)) + "\n]";
    }

    public String exportCursosToJson() {
        return "[\n" + String.join(",\n", cursos.stream().map(Curso::toString).toArray(String[]::new)) + "\n]";
    }

    // --- Métodos de Criação de Dados Simulados ---

    private List<Usuario> createSimulatedUsers() {
        List<Usuario> list = new ArrayList<>();

        // Administrador
        Usuario admin = new Usuario("u1", "Ana Admin", "ana@codefolio.com", PapelUsuario.ADMINISTRADOR);
        list.add(admin);

        // Professor
        Usuario prof = new Usuario("u2", "Bruno Professor", "bruno@codefolio.com", PapelUsuario.PROFESSOR);
        Map<String, String> profSocial = new HashMap<>();
        profSocial.put("github", "github.com/bruno");
        prof.setRedesSociais(profSocial);
        list.add(prof);

        // Estudante
        Usuario estudante = new Usuario("u3", "Carla Estudante", "carla@codefolio.com", PapelUsuario.ESTUDANTE);
        list.add(estudante);

        // Usuário Comum
        Usuario comum = new Usuario("u4", "Daniel Comum", "daniel@codefolio.com", PapelUsuario.USUARIO_COMUM);
        list.add(comum);

        return list;
    }

    private List<Curso> createSimulatedCursos() {
        List<Curso> list = new ArrayList<>();

        // Curso Aprovado
        Curso c1 = new Curso("c1", "Java para Iniciantes", "Curso completo de Java básico", "u2");
        c1.setStatus(StatusCurso.ATIVO);
        list.add(c1);

        // Curso Pendente
        Curso c2 = new Curso("c2", "Desenvolvimento Frontend", "Introdução a HTML, CSS, JS", "u2");
        c2.setPinAcesso("1234");
        list.add(c2);

        // Outro curso aprovado
        Curso c3 = new Curso("c3", "Design Patterns", "Padrões de projeto essenciais", "u2");
        c3.setStatus(StatusCurso.ATIVO);
        list.add(c3);

        return list;
    }

    private List<Post> createSimulatedPosts() {
        List<Post> list = new ArrayList<>();

        Post p1 = new Post("p1", "Novidades do Java 21", "O Java 21 trouxe muitas melhorias...", "u2", new String[]{"Java", "Backend"});
        p1.incrementarCurtidas();
        p1.incrementarCurtidas();
        list.add(p1);

        Post p2 = new Post("p2", "Dicas de Produtividade", "Use a técnica Pomodoro para ser mais produtivo.", "u4", new String[]{"Carreira", "Dicas"});
        list.add(p2);

        return list;
    }
}