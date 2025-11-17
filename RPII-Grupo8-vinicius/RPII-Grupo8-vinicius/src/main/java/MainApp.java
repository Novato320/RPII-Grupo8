import model.PapelUsuario;
import service.CursoService;
import service.JsonDataManager;
import service.PostService;
import service.UsuarioService;

void main() {
    // 1. Inicializa o Data Manager com dados simulados
    JsonDataManager dataManager = new JsonDataManager();

    // 2. Inicializa os Serviços
    UsuarioService usuarioService = new UsuarioService(dataManager);
    CursoService cursoService = new CursoService(dataManager);
    PostService postService = new PostService(dataManager);

    IO.println("--- DADOS INICIAIS (SIMULAÇÃO JSON) ---");
    IO.println("Usuários:\n" + dataManager.exportUsuariosToJson());
    IO.println("\nCursos:\n" + dataManager.exportCursosToJson());
    IO.println("\n----------------------------------------\n");

    // Exemplo de Requisito 1: Gerenciamento de Usuários (ADMINISTRADOR)
    IO.println("ADMIN: Alterando papel de Carla (u3) para PROFESSOR...");
    usuarioService.alterarNivelAcesso("u3", PapelUsuario.PROFESSOR);
    IO.println("Busca por Professores:");
    usuarioService.buscarUsuarios(null, PapelUsuario.PROFESSOR)
            .forEach(u -> IO.println("- " + u.getNome() + " | " + u.getPapel()));

    // Exemplo de Requisito 2: Gerenciamento de Cursos (ADMINISTRADOR)
    IO.println("\nADMIN: Aprovando curso c2...");
    cursoService.aprovarCurso("c2");
    IO.println("Catálogo de Cursos Ativos:");
    cursoService.visualizarCatalogo()
            .forEach(c -> IO.println("- " + c.getTitulo() + " (Status: " + c.getStatus() + ")"));

    // Exemplo de Requisito 3: Acesso a Cursos (ESTUDANTE)
    IO.println("\nESTUDANTE: Tentando ingressar no curso c2 (com PIN 1234)...");
    boolean ingressou = cursoService.ingressarCurso("c2", "1234");
    IO.println("Ingresso bem-sucedido? " + ingressou);

    // Exemplo de Requisito 4: Interação com Posts (USUÁRIO COMUM)
    IO.println("\nCOMUM: Filtrando posts por tag 'Java'...");
    postService.filtrarPorTag("Java")
            .forEach(p -> IO.println("- " + p.getTitulo() + " (Curtidas: " + p.getCurtidas() + ")"));

    IO.println("\n--- SIMULAÇÃO JSON APÓS ALTERAÇÕES ---");
    IO.println("Usuários:\n" + dataManager.exportUsuariosToJson());
}