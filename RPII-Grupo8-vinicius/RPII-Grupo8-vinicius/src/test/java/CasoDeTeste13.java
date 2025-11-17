import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CasoDeTeste13 {

    private WebDriver driver;
    private WebDriverWait wait;
    private final Duration TIMEOUT = Duration.ofSeconds(30);
    private final String URL_BASE = "https://testes.codefolio.com.br/";
    private JavascriptExecutor js;

    // ====== CHAVES ( VERIFICAR SE O TOKEN NÃO EXPIROU!) ======
    private final String FIREBASE_KEY = "firebase:authUser:AIzaSyARn2qVrSSndFu9JSo5mexrQCMxmORZzCg:[DEFAULT]";
    // Token reformatado para a linha única, baseado no seu código anterior:
    private final String FIREBASE_VALUE = "{\"apiKey\":\"AIzaSyARn2qVrSSndFu9JSo5mexrQCMxmORZzCg\",\"appName\":\"[DEFAULT]\",\"createdAt\":\"1762153090304\",\"displayName\":\"Vinicius Dorneles Pereira\",\"email\":\"viniciusdp.aluno@unipampa.edu.br\",\"emailVerified\":true,\"isAnonymous\":false,\"lastLoginAt\":\"1762747397844\",\"phoneNumber\":null,\"photoURL\":\"https://lh3.googleusercontent.com/a/ACg8ocLm6MriEN15JkhEa7QtYDbZ-u-_f8legSguOpEOIsPH4dEn58s=s96-c\",\"providerData\":[{\"providerId\":\"google.com\",\"uid\":\"116711811499464874741\",\"displayName\":\"Vinicius Dorneles Pereira\",\"email\":\"viniciusdp.aluno@unipampa.edu.br\",\"phoneNumber\":null,\"photoURL\":\"https://lh3.googleusercontent.com/a/ACg8ocLm6MriEN15JkhEa7QtYDbZ-u-_f8legSguOpEOIsPH4dEn58s=s96-c\"}],\"stsTokenManager\":{\"accessToken\":\"eyJhbGciOiJSUzI1NiIsImtpZCI6IjU0NTEzMjA5OWFkNmJmNjEzODJiNmI0Y2RlOWEyZGZlZDhjYjMwZjAiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiVmluaWNpdXMgRG9ybmVsZXMgUGVyZWlyYSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NMbTZNcmlFTjE1SmtoRWE3UXRZRGJaLXUtX2Y4bGVnU2d1T3BFT0lzUEg0ZEVuNThzPXM5Ni1jIiwiaXNzIjoiaHR0cHM6Ly9zZWN1cmV0b2tlbi5nb29nbGUuY29tL3JlYWN0LW5hLXByYXRpY2EiLCJhdWQiOiJyZWFjdC1uYS1wcmF0aWNhIiwiYXV0aF90aW1lIjoxNzYyNzQ3Mzk3LCJ1c2VyX2lkIjoiOUk4MUJ5VTM3b2J5endGVG11aTdJVHBqZGpnMSIsInN1YiI6IjlJODFCeVUzN29ieXp3RlRtdWk3SVRwamRqZzEiLCJpYXQiOjE3NjI3NDczOTcsImV4cCI6MTc2Mjc1MDk5NywiZW1haWwiOiJ2aW5pY2l1c2RwLmFsdW5vQHVuaXBhbXBhLmVkdS5iciIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7Imdvb2dsZS5jb20iOlsiMTE2NzExODExNDk5NDY0ODc0NzQxIl0sImVtYWlsIjpbInZpbmljaXVzZHAuYWx1bm9AdW5pcGFtcGEuZWR1LmJyIl19LCJzaWduX2luX3Byb3ZpZGVyIjoiZ29vZ2xlLmNvbSJ9fQ.jY9sr1eqMZOXJYLAF6aIHxa4tQKfrNfO1AblRtTvufBAPt58ZT69dqxzj5yAImR8-9lf8XlC3gsRE2EX0qJi8rCq70DLYhG5GL5-LfG0u2MJ2sUrKQI3QcNdjUfNITGQeZ6hpRmRYlK8VdqsU5nDf76DWXt3fPliHENV4Tvu86Qms5aFNcxL86ryn_PCMvV7ZjreOWY3Ddu9WkN8pOT3Pcw0MgQ0bDZxqSIuRbFcSTM0LCCK0jbfXLEzql2s5s3FhmydFCsOaBWXtJk0DX93zc7_cHr3SH4Ji5rAoUx9oX_i_bBRWqHvO2mB8y3D1ow-hhhipoWTGFD6o_feejDtDQ\",\"expirationTime\":1762750997791,\"refreshToken\":\"AMf-vByy4_WtjzjDe9Dm2-gEw57rUVMzEnbFo8Na_VQTLWkoxPjPYfhdFWQ9ocoG4XtPbn7vvlB_UBGQK3oPMnkwoH7fo4UWFEJUOFvfqcRZLN_zz26fi1cgH72YapR92vW2PLMrW2IFFBPasWxbDDnyH8ZvOG05leQ5E16e32Jq48dx0qGxNIS1Je20oUgVbIvMMBexuv8sgV2gK2r0eKjeEZz6ZfvVvYRsNPtKe0TdrLNXjxHmE4DcaLIQ0zlZGQRlNRTov5pRFBm6MLKv5RwaQZNtlBmC0bgE8BPn2L2FcGdYS9oGOdLKVUBDoP4r1dmtGc58grZfvNpTrZxoFHrD60nBRHiceP6GGCPNI9ALs5x3APvwPjj_KP-9GCZlYFigz3Um3hA8PYC-zbbflSYNNbgXPpokW7xGDWbuRnpJRuCwA9Fpk94hIKesNmpVf_QtLwUoNBX6aEP06sGdDy33hu5bhfADdQ\"},\"tenantId\":null,\"uid\":\"9I81ByU37obyzwFTmui7ITpjdjg1\",\"_redirectEventId\":null}";

    // Dados de Pré-Condição do CT-13-1
    private final String NOME_CURSO_ALVO = "Curso-Teste G.8"; // Onde o material está
    // Título do material extra que DEVE EXISTIR antes de rodar este teste:
    private final String TITULO_MATERIAL_EXTRA = "Material de Referência CT-13";


    @BeforeEach
    public void setup() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        wait = new WebDriverWait(driver, TIMEOUT);
        js = (JavascriptExecutor) driver;

        // Pré-condição 1: Usuário logado com perfil de professor (via injeção de Local Storage)
        driver.get(URL_BASE);
        js.executeScript("window.localStorage.setItem(arguments[0], arguments[1]);",
                FIREBASE_KEY, FIREBASE_VALUE);
        driver.navigate().refresh();
        Thread.sleep(4000);
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void ct13_excluirMaterialExtra() {
        // Pré-condição: Material Extra ativo vinculado (Vídeo ou Slide)

        // 1) Ir para "Gerenciamento de Cursos"
        abrirMenuPerfilEIrParaGerenciamento();

        // 2) Clicar no card -> Gerenciar Curso
        clicarGerenciarCurso(NOME_CURSO_ALVO);

        // 3) Acessar a aba “Materiais Extras” (Passo de Pré-condição)
        clicarAba("MATERIAIS EXTRAS");

        // 4) Localizar o material extra e clicar no ícone de Excluir (lixeira)
        clicarExcluirMaterialExtra(TITULO_MATERIAL_EXTRA);

        // 5) Janela de confirmação: confirmar a exclusão
        confirmarExclusao();

        // 6) Verificação Final (Resultado Esperado)
        verificarExclusaoNaLista(TITULO_MATERIAL_EXTRA);
    }

    // ===================== MÉTODOS DE AÇÃO E VERIFICAÇÃO =====================

    private void clicarExcluirMaterialExtra(String tituloMaterial) {
        // ... (código de rolagem e busca do texto, que agora DEVE funcionar) ...
        // busca a LIXEIRA (ícone trash) que o segue.
        // A lixeira é o ÍCONE de trash, buscamos o elemento SVG/Ícone que contém 'trash'
        By lixeiraFinalLocator = By.xpath(
                "//*[contains(text(),'" + tituloMaterial + "')]" // Encontra o elemento de texto
                        + "/following::*[contains(@data-icon, 'trash') or contains(@class, 'fa-trash') or contains(@aria-label, 'Excluir') or name()='svg']" // Busca o ícone/botão de lixeira
                        + "/ancestor::*[self::button or self::a or self::div][1]" // Sobe para o primeiro pai clicável (botão/link)
        );

        WebElement btnLixeira = wait.until(ExpectedConditions.presenceOfElementLocated(lixeiraFinalLocator));

        // Clica no botão final clicável via JS
        js.executeScript("arguments[0].click();", btnLixeira);
    }

    private void rolarParaOElemento(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    private void confirmarExclusao() {
        // --- 1. Espera o MODAL de Confirmação ---
        By modalTexto = By.xpath("//*[contains(text(),'Tem certeza que deseja excluir')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalTexto));
        // --- 2. Localiza e Clica no botão SIM, EXCLUIR ---
        // Busca pelo texto 'Sim, Excluir' (com S e E maiúsculos)
        // E/OU busca pelo botão que tem a classe de erro (vermelho).
        By btnConfirmarFinal = By.xpath(
                "//button[contains(@class, 'Error') and contains(.,'Sim, Excluir')] | //*[contains(text(),'Sim, Excluir')]/ancestor::button[1]"
        );

        // Espera e localiza o botão
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(btnConfirmarFinal));

        // 3. Clica no botão
        js.executeScript("arguments[0].click();", btn);

        // 4. Espera o modal desaparecer
        wait.until(ExpectedConditions.invisibilityOfElementLocated(modalTexto));

        System.out.println("Confirmação de exclusão clicada com sucesso.");
    }

    private void verificarExclusaoNaLista(String tituloExcluido) {
        // Espera pela mensagem de sucesso (Resultado Esperado)
        By mensagemSucessoLocator = By.xpath("//*[contains(text(),'excluído com sucesso') and (contains(@class,'alert') or contains(@class,'toast'))]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(mensagemSucessoLocator));

        // Verifica se o título do material NÃO está mais presente na lista
        By tituloListado = By.xpath("//*[contains(text(),'Materiais Extras')]/following::*[contains(text(),'" + tituloExcluido + "')]");

        boolean materialNaoEstaPresente = wait.until(ExpectedConditions.invisibilityOfElementLocated(tituloListado));

        assertTrue(materialNaoEstaPresente, "Falha na exclusão: O material extra ainda está visível na lista.");
    }

    // ===================== HELPERS REUTILIZADOS (Do CT06Teste) =====================

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
        By btnLocator = By.xpath("//h6[contains(normalize-space(),'" + nomeParcialCurso + "')]/following::button[contains(.,'Gerenciar Curso')][1]");

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(btnLocator));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        js.executeScript("arguments[0].click();", btn);

        wait.until(ExpectedConditions.urlContains("/adm-cursos"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[contains(text(),'Gerenciar Curso')] | //h1[contains(text(),'Gerenciar Curso')]")
        ));
    }

    private void clicarAba(String nomeAba) {
        // O XPath busca o nome da aba convertendo ambos (DOM e busca) para CAIXA BAIXA.
        String xpathAba = String.format(
                "//button[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]",
                nomeAba.toLowerCase()
        );

        WebElement aba = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(xpathAba)
        ));

        // A aba "VÍDEOS" é a padrão, mas para materiais extras o clique é necessário.
        js.executeScript("arguments[0].click();", aba);
        System.out.println("Aba '" + nomeAba + "' acessada.");
    }
}