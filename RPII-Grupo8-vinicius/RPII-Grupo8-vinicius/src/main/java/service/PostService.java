package service;

import model.Post;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PostService {
    private final JsonDataManager dataManager;

    public PostService(JsonDataManager dataManager) {
        this.dataManager = dataManager;
    }

    // Requisito: Visualizar posts/artigos (Usuário Comum)
    public List<Post> visualizarTodos() {
        return dataManager.getPosts();
    }

    // Requisito: Remover posts inadequados (Administrador)
    public boolean removerPost(String postId) {
        return dataManager.getPosts().removeIf(p -> p.getId().equals(postId));
    }

    // Requisito: Curtir/descurtir posts (Usuário Comum) - Simulação de curtida
    public boolean curtirPost(String postId) {
        return dataManager.getPosts().stream()
            .filter(p -> p.getId().equals(postId))
            .findFirst()
            .map(p -> {
                p.incrementarCurtidas();
                return true;
            })
            .orElse(false);
    }

    // Requisito: Filtrar artigos por temas/tags (Usuário Comum)
    public List<Post> filtrarPorTag(String tag) {
        if (tag == null || tag.trim().isEmpty()) {
            return visualizarTodos();
        }

        String tagLower = tag.toLowerCase();

        return dataManager.getPosts().stream()
            .filter(p -> Arrays.stream(p.getTags())
                    .map(String::toLowerCase)
                    .anyMatch(t -> t.equals(tagLower)))
            .collect(Collectors.toList());
    }

    // Requisito: Visualizar métricas de engajamento (Administrador)
    public int getCurtidasTotais() {
        return dataManager.getPosts().stream().mapToInt(Post::getCurtidas).sum();
    }
}