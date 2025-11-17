import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

import static org.apache.hc.core5.http.HttpHeaders.TIMEOUT;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CasoDeTeste25 {

    private WebDriver driver;
    private WebDriverWait wait;
    private final Duration TIMEOUT = Duration.ofSeconds(30);
    private JavascriptExecutor js;

    // --- DADOS DE PRÉ-CONDIÇÃO E ENTRADA ---
    private final String URL_BASE = "https://testes.codefolio.com.br/";
    private final String NOME_CURSO_ALVO = "Curso-Teste G.8"; // Deve ser o curso do professor
    private final String NOME_AVALIACAO = "Prova Final";      // Nome da avaliação já cadastrada
    private final String EMAIL_ALUNO = "aluno_teste@unipampa.edu.br"; // E-mail do aluno alvo
    private final String NOTA_A_ATRIBUIR = "85";               // Nota de entrada (Exemplo: 85%)

    // ⚠️ SUBSTITUA O TOKEN COM SUA CHAVE FIREBASE ATUALIZADA ⚠️
    private final String FIREBASE_KEY = "firebase:authUser:AIzaSyARn2qVrSSndFu9JSo5mexrQCMxmORZzCg:[DEFAULT]";
    private final String FIREBASE_VALUE = "{\"apiKey\":\"AIzaSyARn2qVrSSndFu9JSo5mexrQCMxmORZzCg\",\"appName\":\"[DEFAULT]\",\"createdAt\":\"1762153090304\",\"displayName\":\"Vinicius Dorneles Pereira\",\"email\":\"viniciusdp.aluno@unipampa.edu.br\",\"emailVerified\":true,\"isAnonymous\":false,\"lastLoginAt\":\"1762747397844\",\"phoneNumber\":null,\"photoURL\":\"https://lh3.googleusercontent.com/a/ACg8ocLm6MriEN15JkhEa7QtYDbZ-u-_f8legSguOpEOIsPH4dEn58s=s96-c\",\"providerData\":[{\"providerId\":\"google.com\",\"uid\":\"116711811499464874741\",\"displayName\":\"Vinicius Dorneles Pereira\",\"email\":\"viniciusdp.aluno@unipampa.edu.br\",\"phoneNumber\":null,\"photoURL\":\"https://lh3.googleusercontent.com/a/ACg8ocLm6MriEN15JkhEa7QtYDbZ-u-_f8legSguOpEOIsPH4dEn58s=s96-c\"}],\"stsTokenManager\":{\"accessToken\":\"eyJhbGciOiJSUzI1NiIsImtpZCI6IjU0NTEzMjA5OWFkNmJmNjEzODJiNmI0Y2RlOWEyZGZlZDhjYjMwZjAiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiVmluaWNpdXMgRG9ybmVsZXMgUGVyZWlyYSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NMbTZNcmlFTjE1SmtoRWE3UXRZRGJaLXUtX2Y4bGVnU2d1T3BFT0lzUEg0ZEVuNThzPXM5Ni1jIiwiaXNzIjoiaHR0cHM6Ly9zZWN1cmV0b2tlbi5nb29nbGUuY29tL3JlYWN0LW5hLXByYXRpY2EiLCJhdWQiOiJyZWFjdC1uYS1wcmF0aWNhIiwiYXV0aF90aW1lIjoxNzYyNzQ3Mzk3LCJ1c2VyX2lkIjoiOUk4MUJ5VTM3b2J5endGVG11aTdJVHBqZGpnMSIsInN1YiI6IjlJODFCeVUzN29ieXp3RlRtdWk3SVRwamRqZzEiLCJpYXQiOjE3NjI3NDczOTcsImV4cCI6MTc2Mjc1MDk5NywiZW1haWwiOiJ2aW5pY2l1c2RwLmFsdW5vQHVuaXBhbXBhLmVkdS5iciIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7Imdvb2dsZS5jb20iOlsiMTE2NzExODExNDk5NDY0ODc0NzQxIl0sImVtYWlsIjpbInZpbmljaXVzZHAuYWx1bm9AdW5pcGFtcGEuZWR1LmJyIl19LCJzaWduX2luX3Byb3ZpZGVyIjoiZ29vZ2xlLmNvbSJ9fQ.jY9sr1eqMZOXJYLAF6aIHxa4tQKfrNfO1AblRtTvufBAPt58ZT69dqxzj5yAImR8-9lf8XlC3gsRE2EX0qJi8rCq70DLYhG5GL5-LfG0u2MJ2sUrKQI3QcNdjUfNITGQeZ6hpRmRYlK8VdqsU5nDf76DWXt3fPliHENV4Tvu86Qms5aFNcxL86ryn_PCMvV7ZjreOWY3Ddu9WkN8pOT3Pcw0MgQ0bDZxqSIuRbFcSTM0LCCK0jbfXLEzql2s5s3FhmydFCsOaBWXtJk0DX93zc7_cHr3SH4Ji5rAoUx9oX_i_bBRWqHvO2mB8y3D1ow-hhhipoWTGFD6o_feejDtDQ\",\"expirationTime\":1762750997791,\"refreshToken\":\"AMf-vByy4_WtjzjDe9Dm2-gEw57rUVMzEnbFo8Na_VQTLWkoxPjPYfhdFWQ9ocoG4XtPbn7vvlB_UBGQK3oPMnkwoH7fo4UWFEJUOFvfqcRZLN_zz26fi1cgH72YapR92vW2PLMrW2IFFBPasWxbDDnyH8ZvOG05leQ5E16e32Jq48dx0qGxNIS1Je20oUgVbIvMMBexuv8sgV2gK2r0eKjeEZz6ZfvVvYRsNPtKe0TdrLNXjxHmE4DcaLIQ0zlZGQRlNRTov5pRFBm6MLKv5RwaZNtlBmC0bgE8BPn2L2FcGdYS9oGOdLKVUBDoP4r1dmtGc58grZfvNpTrZxoFHrD60nBRHiceP6GGCPNI9ALs5x3APvwPjj_KP-9GCZlYFigz3Um3hA8PYC-zbbflSYNNbgXPpokW7xGDWbuRnpJRuCwA9Fpk94hIKesNmpVf_QtLwUoNBX6aEP06sGdDy33hu5bhfADdQ"},"tenantId":null,"uid":"9I81ByU37obyzwFTmui7ITpjdjg1","_redirectEventId":null}";


    @BeforeEach
    public void setup() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, TIMEOUT);
        js = (JavascriptExecutor) driver;

        // Login por injeção (usando a estratégia já validada)
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
    public void ct25_atribuirNotaComSucesso() {
        // Pré-condição: Professor logado, Curso e Avaliação existem.

        // 1) Acessar a página de gerenciamento do curso
        abrirMenuPerfilEIrParaGerenciamento();
        clicarGerenciarCurso(NOME_CURSO_ALVO);

        // 2) Acessar a aba "AVALIAÇÕES"
        clicarAba("AVALIAÇÕES");

        // 3) Localizar a avaliação e clicar em "Atribuir Nota"
        clicarAtribuirNota(NOME_AVALIACAO);

        // 4) Localizar o campo do aluno e atribuir a nota (85)
        atribuirNotaAoAluno(EMAIL_ALUNO, NOTA_A_ATRIBUIR);

        // 5) Salvar e Verificar a mensagem de sucesso
        salvarNotas();

        // Verificação final
        assertTrue(verificarNotaSalva(EMAIL_ALUNO, NOTA_A_ATRIBUIR),
                "Falha: A nota atribuída não foi salva corretamente.");
    }

    // ===================== MÉTODOS DE AÇÃO ESPECÍFICOS =====================

    private void clicarAtribuirNota(String nomeAvaliacao) {
        // Localiza a linha da avaliação e clica no botão "Atribuir Nota" (ou ícone similar)
        By atribuirBtnLocator = By.xpath(
                "//*[contains(text(),'" + nomeAvaliacao + "')]/ancestor::div[contains(@class,'item') or contains(@class,'row')]"
                        + "//*[contains(text(),'ATRIBUIR NOTA')]"
        );
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(atribuirBtnLocator));
        js.executeScript("arguments[0].click();", btn);

        // Espera a tela de atribuição de notas carregar (ex: a URL ou o título)
        wait.until(ExpectedConditions.urlContains("assign-grades") | ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Atribuição de Notas')]")));
        System.out.println("Entrou na tela de atribuição de notas para: " + nomeAvaliacao);
    }

    private void atribuirNotaAoAluno(String emailAluno, String nota) {
        // Localiza a linha do aluno pelo e-mail
        By inputNotaLocator = By.xpath(
                "//*[contains(text(),'" + emailAluno + "')]/ancestor::div[contains(@class,'aluno-row') or contains(@class,'item')]"
                        + "//input[contains(@type,'text') or contains(@type,'number')]" // Busca o campo de input dentro da linha do aluno
        );

        WebElement inputNota = wait.until(ExpectedConditions.elementToBeClickable(inputNotaLocator));
        js.executeScript("arguments[0].scrollIntoView({block:'center'})", inputNota);

        // Preenche o campo de nota (usando JS para robustez)
        js.executeScript("arguments[0].value = '';", inputNota);
        js.executeScript("arguments[0].value = arguments[1];", inputNota, nota);

        System.out.println("Nota " + nota + " atribuída ao aluno " + emailAluno);
    }

    private void salvarNotas() {
        By salvarBtnLocator = By.xpath("//button[contains(text(),'SALVAR') or contains(text(),'ATUALIZAR NOTAS')]");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(salvarBtnLocator));
        js.executeScript("arguments[0].click();", btn);

        // Espera a mensagem de sucesso aparecer
        By sucessoLocator = By.xpath("//*[contains(text(),'sucesso') or contains(text(),'atualizada')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(sucessoLocator));

        System.out.println("Notas salvas com sucesso.");
    }

    private boolean verificarNotaSalva(String emailAluno, String notaEsperada) {
        // Após salvar, o sistema geralmente retorna à lista ou atualiza a tela.
        // Voltamos à lista principal de avaliações para verificar.

        // Navegação de volta (opcional, dependendo do fluxo da aplicação)
        // driver.navigate().refresh();

        // Localiza a célula ou elemento que exibe a nota do aluno
        By notaExibidaLocator = By.xpath(
                "//*[contains(text(),'" + emailAluno + "')]/ancestor::div[contains(@class,'aluno-row')]"
                        + "//span[contains(text(),'" + notaEsperada + "%')]" // Busca o texto da nota na linha do aluno
        );

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(notaExibidaLocator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }


    // ===================== HELPERS REUTILIZADOS (Da sua biblioteca) =====================

    // Estes métodos (abrirMenuPerfilEIrParaGerenciamento, clicarGerenciarCurso, clicarAba)
    // devem ser copiados da sua classe CasoDeTeste06/13, garantindo que o Java reconheça os métodos.
    // (Abaixo está um placeholder, use sua versão mais recente e estável)

    private void abrirMenuPerfilEIrParaGerenciamento() {
        // ... (código de abertura do menu de perfil) ...
    }

    private void clicarGerenciarCurso(String nomeParcialCurso) {
        // ... (código para clicar em Gerenciar Curso) ...
    }

    private void clicarAba(String nomeAba) {
        // ... (código para clicar na aba usando translate()) ...
    }
}