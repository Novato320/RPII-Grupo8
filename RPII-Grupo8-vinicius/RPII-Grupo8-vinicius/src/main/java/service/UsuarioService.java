package service;

import model.PapelUsuario;
import model.Usuario;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioService {
    private final JsonDataManager dataManager;

    public UsuarioService(JsonDataManager dataManager) {
        this.dataManager = dataManager;
    }

    // Requisito: Visualizar todos os usuários cadastrados
    public List<Usuario> visualizarTodos() {
        return dataManager.getUsuarios();
    }

    // Requisito: Alterar níveis de acesso (Administrador)
    public boolean alterarNivelAcesso(String userId, PapelUsuario novoPapel) {
        return dataManager.getUsuarios().stream()
            .filter(u -> u.getId().equals(userId))
            .findFirst()
            .map(u -> {
                u.setPapel(novoPapel);
                return true;
            })
            .orElse(false);
    }

    // Requisito: Filtrar e buscar usuários por nome, email ou papel (Administrador)
    public List<Usuario> buscarUsuarios(String termo, PapelUsuario papel) {
        if (termo == null && papel == null) {
            return visualizarTodos();
        }

        String termoLower = (termo != null) ? termo.toLowerCase() : null;

        return dataManager.getUsuarios().stream()
            .filter(u -> {
                boolean matchTermo = (termoLower == null) || 
                                     u.getNome().toLowerCase().contains(termoLower) || 
                                     u.getEmail().toLowerCase().contains(termoLower);
                
                boolean matchPapel = (papel == null) || u.getPapel() == papel;
                
                return matchTermo && matchPapel;
            })
            .collect(Collectors.toList());
    }

    // Requisito: Ordenar usuários por diferentes critérios (Administrador)
    public List<Usuario> ordenarUsuarios(String criterio) {
        Comparator<Usuario> comparator;
        switch (criterio.toLowerCase()) {
            case "nome":
                comparator = Comparator.comparing(Usuario::getNome);
                break;
            case "email":
                comparator = Comparator.comparing(Usuario::getEmail);
                break;
            case "papel":
                // Ordenação por ordem de enum/definição
                comparator = Comparator.comparing(u -> u.getPapel().ordinal()); 
                break;
            default:
                // Critério padrão (ex: ID)
                comparator = Comparator.comparing(Usuario::getId);
                break;
        }
        return dataManager.getUsuarios().stream().sorted(comparator).collect(Collectors.toList());
    }

    // Requisito: Editar informações pessoais (Usuário Comum)
    public boolean editarPerfil(String userId, String novoNome) {
        return dataManager.getUsuarios().stream()
            .filter(u -> u.getId().equals(userId))
            .findFirst()
            .map(u -> {
                u.setNome(novoNome);
                // Adicione lógica para alterar sobrenome/foto/redes sociais aqui, se necessário
                return true;
            })
            .orElse(false);
    }
}