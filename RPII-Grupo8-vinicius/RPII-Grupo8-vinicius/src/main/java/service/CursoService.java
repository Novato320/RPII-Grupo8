package service;


import model.Curso;
import model.StatusCurso;

import java.util.List;
import java.util.stream.Collectors;

public class CursoService {
    private final JsonDataManager dataManager;

    public CursoService(JsonDataManager dataManager) {
        this.dataManager = dataManager;
    }

    // Requisito: Criar novos cursos (Professor)
    public Curso criarCurso(String titulo, String descricao, String professorId) {
        String newId = "c" + (dataManager.getCursos().size() + 1);
        Curso novoCurso = new Curso(newId, titulo, descricao, professorId);
        dataManager.getCursos().add(novoCurso);
        return novoCurso;
    }

    // Requisito: Editar cursos existentes (Professor/Administrador)
    public boolean editarCurso(String cursoId, String novoTitulo, String novaDescricao) {
        return dataManager.getCursos().stream()
            .filter(c -> c.getId().equals(cursoId))
            .findFirst()
            .map(c -> {
                c.setTitulo(novoTitulo);
                c.setDescricao(novaDescricao);
                return true;
            })
            .orElse(false);
    }

    // Requisito: Configurar proteção por PIN de acesso (Professor)
    public boolean configurarPin(String cursoId, String pin) {
         return dataManager.getCursos().stream()
            .filter(c -> c.getId().equals(cursoId))
            .findFirst()
            .map(c -> {
                c.setPinAcesso(pin);
                return true;
            })
            .orElse(false);
    }

    // Requisito: Aprovar/rejeitar cursos (Administrador)
    public boolean aprovarCurso(String cursoId) {
        return setStatus(cursoId, StatusCurso.ATIVO);
    }

    public boolean rejeitarCurso(String cursoId) {
        return setStatus(cursoId, StatusCurso.INATIVO);
    }

    private boolean setStatus(String cursoId, StatusCurso status) {
        return dataManager.getCursos().stream()
            .filter(c -> c.getId().equals(cursoId))
            .findFirst()
            .map(c -> {
                c.setStatus(status);
                return true;
            })
            .orElse(false);
    }

    // Requisito: Visualizar catálogo de cursos disponíveis (Estudante/Comum)
    public List<Curso> visualizarCatalogo() {
        return dataManager.getCursos().stream()
            .filter(c -> c.getStatus() == StatusCurso.ATIVO)
            .collect(Collectors.toList());
    }

    // Requisito: Ingressar em cursos (simulação de lógica de PIN)
    public boolean ingressarCurso(String cursoId, String pinFornecido) {
        return dataManager.getCursos().stream()
            .filter(c -> c.getId().equals(cursoId) && c.getStatus() == StatusCurso.ATIVO)
            .findFirst()
            .map(c -> {
                if (c.getPinAcesso() == null || c.getPinAcesso().equals(pinFornecido)) {
                    // Lógica real: Adicionar estudante à lista de alunos do curso
                    System.out.println("Usuário ingressou no curso " + c.getTitulo());
                    return true;
                }
                return false; // PIN incorreto
            })
            .orElse(false);
    }
}