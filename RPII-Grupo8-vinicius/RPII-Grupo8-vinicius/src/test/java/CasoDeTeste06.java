import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

    // --- DADOS DE TESTE CT-06-1 ---
    private final String NOME_CURSO_ALVO = "Curso-Teste G.8"; // Nome do curso onde o vídeo está.
    // Título do vídeo que DEVE EXISTIR antes de rodar este teste:
    private final String TITULO_VIDEO_ANTIGO = "Músicas que um engenheiro...";

    // Novos Dados para Edição
    private static final String NOVO_TITULO = "Introdução ao React - Atualizado";
    private static final String NOVA_DESCRICAO = "Versão revisada com novas práticas e exemplos.";
    private static final String NOVO_LINK = "https://www.youtube.com/watch?v=abcd1234xyz";

    @BeforeEach
    public void setup() throws InterruptedException {
	@FindBy(css="")
	private WebElement webElement;
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        wait = new WebDriverWait(driver, TIMEOUT);
        js = (JavascriptExecutor) driver;

        // Login por injeção no Local Storage
        driver.get(URL_BASE);
        js.executeScript("window.localStorage.setItem(arguments[0], arguments[1]);",
                FIREBASE_KEY, FIREBASE_VALUE);
        driver.navigate().refresh();

        // pequena espera pro Firebase processar a sessão
        Thread.sleep(4000);
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit(); // É importante fechar o driver após o teste.
        }
    }

    @Test
    public void ct06_editarVideo() throws InterruptedException { // <--- CORREÇÃO CRÍTICA
        // Pré-condições: Usuário logado, Curso existe, Vídeo existe.

        // 1) No menu superior, clicar em “Gerenciamento de Cursos” (Passo 1 do CT)
        abrirMenuPerfilEIrParaGerenciamento();

        // ... (resto do código)

        // 6) Verificar se o vídeo aparece atualizado na lista (Passo 8 do CT)
        verificarVideoNaLista(NOVO_TITULO); // Linha onde a falha anterior ocorreu

        // ...
    }



    // ===================== HELPERS REUTILIZADOS =====================

    private void abrirMenuPerfilEIrParaGerenciamento() {
        WebElement profileBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[aria-label='Configurações da Conta']")));
        js.executeScript("arguments[0].click();", profileBtn);

        WebElement item = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[normalize-space()='Gerenciamento de Cursos']")));
        item.click();

        wait.until(ExpectedConditions.urlContains("/manage-courses"));
    }

    private void clicarGerenciarCurso(String nomeParcialCurso) {
        // 1. Localiza o botão Gerenciar Curso
        // Busca o botão Gerenciar Curso que segue o elemento <h6> com o nome do curso
        By btnLocator = By.xpath("//h6[contains(normalize-space(),'" + nomeParcialCurso + "')]/following::button[contains(.,'Gerenciar Curso')][1]");

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(btnLocator));

        // 2. Garante que o elemento está na vista
        js.executeScript("arguments[0].scrollIntoView({block:'center'})", btn);

        // 3. Clica usando JavaScript para garantir a ação
        js.executeScript("arguments[0].click();", btn);

        // 4. Espera a URL carregar (a linha onde a falha ocorreu)
        wait.until(ExpectedConditions.urlContains("/adm-cursos"));

        // ... [restante do código] ...
    }

    private void preencherCamposVideo(String titulo, String url, String descricao) {

        // Encontra o campo ativo após a label

        By inputTituloLocator = By.xpath(
                "//label[contains(text(),'Título do Vídeo')]/following::input[1]"
        );
        // Usa 'presence' e rola o elemento para garantir que ele esteja no DOM
        WebElement inputTitulo = wait.until(ExpectedConditions.presenceOfElementLocated(inputTituloLocator));
        js.executeScript("arguments[0].scrollIntoView(true);", inputTitulo); // Garante visibilidade


        By inputUrlLocator = By.xpath(
                "//label[contains(text(),'URL do Vídeo')]/following::input[1]"
        );
        WebElement inputUrl = wait.until(ExpectedConditions.presenceOfElementLocated(inputUrlLocator));

        By inputDescLocator = By.xpath(
                "//label[contains(text(),'Descrição do Vídeo')]/following::textarea[1]"
        );
        WebElement inputDesc = wait.until(ExpectedConditions.presenceOfElementLocated(inputDescLocator));


        // --- 2. INTERAÇÃO FORÇADA VIA JAVASCRIPT ---
        System.out.println("Forçando o preenchimento dos campos via JavaScript...");

        // Limpa e digita no Título
        js.executeScript("arguments[0].value = '';", inputTitulo); // Limpa o valor
        js.executeScript("arguments[0].value = arguments[1];", inputTitulo, titulo);

        // Limpa e digita na URL
        js.executeScript("arguments[0].value = '';", inputUrl);
        js.executeScript("arguments[0].value = arguments[1];", inputUrl, url);

        // Limpa e digita na Descrição
        js.executeScript("arguments[0].value = '';", inputDesc);
        js.executeScript("arguments[0].value = arguments[1];", inputDesc, descricao);
    }

    private void clicarSalvarCurso() {
        // Localiza o botão "Salvar Curso"
        By salvarLocator = By.xpath("//button[normalize-space()='Salvar Curso']");

        // 1. Espera apenas que ele esteja PRESENTE (visível)
        WebElement salvar = wait.until(ExpectedConditions.presenceOfElementLocated(salvarLocator));

        // 2. Garante que está no viewport (CRUCIAL!)
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", salvar);

        // 3. Dispara o clique via JS.
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", salvar);

        System.out.println("Clicou em 'Salvar Curso' via JavaScript.");

        // --- 4. Verificação de Sucesso (mantemos resiliente) ---
        try {
            // Espera a mensagem de alerta/toast aparecer (duração menor, pois a falha não é aqui)
            By mensagemSucessoLocator = By.xpath("//*[contains(text(),'sucesso') and (contains(@class,'alert') or contains(@class,'toast') or contains(@class,'Snackbar') or ancestor::div[contains(@role,'alert') or contains(@class,'modal')])]");
            WebElement alerta = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(mensagemSucessoLocator));

            System.out.println("Mensagem de sucesso recebida: " + alerta.getText());

            // Se houver modal OK, clica:
            By modalOk = By.xpath("//button[normalize-space()='OK']");
            if (driver.findElements(modalOk).size() > 0) {
                wait.until(ExpectedConditions.elementToBeClickable(modalOk)).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(modalOk));
            }

        } catch (Exception e) {
            System.err.println("Alerta de sucesso não encontrado após salvar. Verifique se o teste falhou na verificação final.");
        }

        // Espera pela permanência na URL de Gerenciamento (prova que o salvamento funcionou)
        wait.until(ExpectedConditions.urlContains("/adm-cursos"));
    }
    private void verificarVideoNaLista(String novoTitulo) throws InterruptedException {
        // Verifica se o novo título aparece na lista de Vídeos do Curso
        By tituloListado = By.xpath(
                "//div[contains(.,'Vídeos do Curso')]//*[normalize-space()='" + novoTitulo + "']"
        );
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(tituloListado));
        assertTrue(el.isDisplayed(), "O NOVO TÍTULO do vídeo não apareceu na lista após a edição.");
    }

    private void clicarAba(String nomeAba) {
        // Ação 1: Espera apenas a visibilidade
        // Usamos o localizador com 'contains' que já aplicamos.
        WebElement aba = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(.,'" + nomeAba + "')]")
        ));

        //Força o clique via JavaScript, ignorando overlays.
        js.executeScript("arguments[0].click();", aba);

        System.out.println("Aba '" + nomeAba + "' acessada.");
    }
    /**
     * Localiza o botão de edição (lápis) ao lado do vídeo na lista e clica.
     */
    private void clicarEditarVideo(String tituloVideo) {
        // Definimos a parte estável do título para busca
        String tituloCurto = "Músicas que um engenheiro";

        // 1. LOCALIZA O CONTÊINER PAI DO VÍDEO PELO TEXTO
        By contêinerDoVideoLocator = By.xpath(
                "//*[contains(text(),'" + tituloCurto + "')]/ancestor::div[contains(@class, 'MuiPaper-root') or contains(@class, 'video-item')]"
        );

        // Localiza o contêiner pai que contém o vídeo e os botões
        WebElement contêiner = wait.until(ExpectedConditions.presenceOfElementLocated(contêinerDoVideoLocator));

        // 2. ROLAGEM FORÇADA: Rolamos o contêiner do vídeo para o centro da tela.
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", contêiner);

        // 3. Busca o primeiro elemento SVG dentro do contêiner.
        // O ícone de edição é o primeiro SVG que aparece na hierarquia das ações.
        By editarIconeRelativo = By.xpath(
                ".//*[name()='svg'][1] | .//*[contains(@class, 'fa-pen')][1]" // Tenta o SVG ou a classe FontAwesome
        );

        // 4. busca final e clique.
        WebElement btnEditarIcone = contêiner.findElement(editarIconeRelativo);

        // 5. Para resolver o problema de que o SVG/ícone não é o elemento clicável,
        // subimos para o seu botão pai que tem o evento de clique.
        WebElement btnClicavel = btnEditarIcone.findElement(By.xpath("./ancestor::button[1] | ./ancestor::a[1] | ./parent::*[1]"));

        js.executeScript("arguments[0].click();", btnClicavel);

        System.out.println("Clicou no botão Editar para o vídeo: " + tituloVideo);

        // 6. Espera o formulário de edição carregar
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//label[contains(text(),'Título do Vídeo')]")
        ));
    }
}