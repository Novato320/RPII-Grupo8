import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * @Name: CT-02 Cadastro de Curso.
 * @Requirement: RF-02 O sistema deve permitir que o usuário adicione novos cursos,
 * informando título, descrição e PIN (opcional).
 * @author Guilherme Jaques
 */
public class CasoDeTeste02 {
    public String nomeCurso = "Curso-Teste G.8";
    public String descricaoCurso = "Esse curso foi criado para testes automatizados.";
    public String pin = "1234abc";

    WebDriver driver;
    ChromeOptions options;
    WebDriverWait wait;
    JavascriptExecutor js;

    // Dados para login FIREBASE
    private final String FIREBASE_KEY = "firebase:authUser:AIzaSyARn2qVrSSndFu9JSo5mexrQCMxmORZzCg:[DEFAULT]";
    private final String FIREBASE_VALUE = "{" +
            "   \"apiKey\": \"AIzaSyARn2qVrSSndFu9JSo5mexrQCMxmORZzCg\"," +
            "   \"appName\": \"[DEFAULT]\"," +
            "   \"createdAt\": \"1760400803670\"," +
            "   \"displayName\": \"Guilherme Jaques\"," +
            "   \"email\": \"guilhermejaques.aluno@unipampa.edu.br\"," +
            "   \"emailVerified\": true," +
            "   \"isAnonymous\": false," +
            "   \"lastLoginAt\": \"1762216871936\"," +
            "   \"phoneNumber\": null," +
            "   \"photoURL\": \"https://lh3.googleusercontent.com/a/ACg8ocL5V-nPkkV2Unsr5DbpNvyd3eBv7WiDbmQK7AtVcKPFzoe2oxg=s96-c\"," +
            "   \"providerData\": [" +
            "       {" +
            "       \"providerId\": \"google.com\"," +
            "       \"uid\": \"106189421015729953258\"," +
            "       \"displayName\": \"Guilherme Jaques\"," +
            "       \"email\": \"guilhermejaques.aluno@unipampa.edu.br\"," +
            "       \"phoneNumber\": null," +
            "       \"photoURL\": \"https://lh3.googleusercontent.com/a/ACg8ocL5V-nPkkV2Unsr5DbpNvyd3eBv7WiDbmQK7AtVcKPFzoe2oxg=s96-c\"" +
            "       }" +
            "   ]," +
            "   \"stsTokenManager\": {" +
            "     \"accessToken\": \"eyJhbGciOiJSUzI1NiIsImtpZCI6IjU0NTEzMjA5OWFkNmJmNjEzODJiNmI0Y2RlOWEyZGZlZDhjYjMwZjAiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiR3VpbGhlcm1lIEphcXVlcyIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NMNVYtblBra1YyVW5zcjVEYnBOdnlkM2VCdjdXaURibVFLN0F0VmNLUEZ6b2Uyb3hnPXM5Ni1jIiwiaXNzIjoiaHR0cHM6Ly9zZWN1cmV0b2tlbi5nb29nbGUuY29tL3JlYWN0LW5hLXByYXRpY2EiLCJhdWQiOiJyZWFjdC1uYS1wcmF0aWNhIiwiYXV0aF90aW1lIjoxNzYyMjE2ODcxLCJ1c2VyX2lkIjoibm8yWUszUW1xd2VjTFVLSTJ5ZHNJOUhMcm0wMyIsInN1YiI6Im5vMllLM1FtcXdlY0xVS0kyeWRzSTlITHJtMDMiLCJpYXQiOjE3NjI3MzAyOTUsImV4cCI6MTc2MjczMzg5NSwiZW1haWwiOiJndWlsaGVybWVqYXF1ZXMuYWx1bm9AdW5pcGFtcGEuZWR1LmJyIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZ29vZ2xlLmNvbSI6WyIxMDYxODk0MjEwMTU3Mjk5NTMyNTgiXSwiZW1haWwiOlsiZ3VpbGhlcm1lamFxdWVzLmFsdW5vQHVuaXBhbXBhLmVkdS5iciJdfSwic2lnbl9pbl9wcm92aWRlciI6Imdvb2dsZS5jb20ifX0.D0Gh5s43ywbdqh2qLAko1Ol2CIt6pnbxvaIsrvfa5yreDmgurmQdKr3IcGpv3WGd11C-_C2BvHnRqqMaZEMzvbw8jrPHN1dlqXVr2aLzHxr21xdXpYc952KMUdiwHAbtwMwAZtwWZDYzQZts8HzBFXPTDs-FXdUfQy0AnEa4ZMaWIbYoSWagumBkymZm8gvfi-U7Cs6HTxBC_bZVrOBcb1ufP0Rml5rAdlEglmBmGNmWav89NvbIN8cBjaaSAZZ8n7UbVzqN0G-qyOBbK9E-VF9UBzAFMuXdpZVpSUwMi0XT5090TCxuf2AVbMSuMHbqRL4L2NlHQ7OKwDhy4oH0SA\"," +
            "     \"expirationTime\": 1762733895478," +
            "     \"refreshToken\": \"AMf-vBwb0vAjq0flNRAhZIM770TL1mSmzEJUpPmwIU1dFPSEVazKINIpI009o3sRNR7ikvpBtoD7oNzPbNWkrhHyC_zc3o-wScuXEeRDFecvmJngGuu6j_FT6xg5KP-yrsgBRu5Df_8VUoiE2lQ89way6XforwiGConZMKMREg1ysrRfbxLudGXn8CChRjQ4wjWtNFPeGcNbBKxjiOWPzIxTGFbpIhseEJU3aNbYBKD3h6823lvfe1ZalPLfdlHgDt7DIQggv_4zGGiYfB5depoGSkCQ5qzq8Kz6e8-xiqlse5uAvvAiYwH5220G-mzqpg2kruw0MdK0lnbPnt-FtC6I4mRPFBcfUUMDZLE4ZTPkxxRl_DnF7XcuEd-7E4eVWGX67HqEKPlbJFg3xDJtEwtNGG__WqO7nVLPoxPJ0bWoJKT6-qi8PFqD7wx0w4HSCJkSBp4yAQRbB_l48XvKPPMyck8t4DJEFg\"," +
            "     \"tenantId\": null" +
            "   }," +
            "   \"uid\": \"no2YK3QmqwecLUKI2ydsI9HLrm03\"," +
            "   \"_redirectEventId\": null" +
            "}";
    // logica para o login firebase
    public void login(){
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(50));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver; // inicializa JS Executor

        driver.get("https://testes.codefolio.com.br/");

        //injeção de dados
        System.out.println("Injetando dados de autenticação no Local Storage...");
        try{
            js.executeScript("window.localStorage.setItem(arguments[0], arguments[1]);",
                    FIREBASE_KEY,
                    FIREBASE_VALUE);
            System.out.println("Injeção bem sucedida!");
        } catch (Exception e) {
            System.out.println("Falha critica ao injetar no local storage:" + e.getMessage());
            driver.quit();
            throw  new RuntimeException("Falha no setup do local storage", e);
        }

        System.out.println("Recarregando pagina...");
        driver.navigate().refresh();
    }

    public void esperarSegundos(int segundos){
        try {
            Thread.sleep(Duration.ofSeconds(segundos).toMillis());
        }catch (InterruptedException e){
            System.out.println("Erro au pausar a Thread");
            Thread.currentThread().interrupt();
        }
    }

    @Before
    public void setUp() throws Exception{
        options = new ChromeOptions(); // parametro para o driver
        //options.addArguments("--headless"); // executa sem interface
        //options.addArguments("--disable-gpu");// recomendado no Windows
        //options.addArguments("--window-size=1920,1080"); // define tamanho da tela virtual

        driver = new ChromeDriver(options);
        driver.manage().window().maximize(); // tela cheia

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20)); // tempo para carregar a pagina
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // tempo para procurar um elemento
    }

    @Test
    public void cadastroSemPin(){
        login();

        WebElement botaoPerfil = driver.findElement(By.xpath("/html/body/div/section[2]/div[2]/div/div[1]/div/div[2]/button[2]"));
        botaoPerfil.click();

        WebElement botaoGerenCursos = driver.findElement(By.xpath("/html/body/div[2]/div[3]/ul/li[2]"));
        botaoGerenCursos.click();

        WebElement botaoNovoCurso = driver.findElement(By.xpath("/html/body/div/div/div[2]/button"));
        botaoNovoCurso.click();

        WebElement campoTitulo = driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div[2]/div[1]/div/div/input"));
        campoTitulo.sendKeys(nomeCurso);

        WebElement campoDescricao = driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div[2]/div[2]/div/div/textarea[1]"));
        campoDescricao.sendKeys(descricaoCurso);

        WebElement botaoSalvar = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/button[2]"));
        botaoSalvar.click();

        WebElement mensagem = driver.findElement(By.xpath("/html/body/div[2]/div[3]/h6"));
        String textoMensagem = mensagem.getText();

        assertEquals("Curso criado com sucesso!", textoMensagem);

        //esperarSegundos(25);
    }

    @Test
    public void cadastroComPin(){
        login();

        WebElement botaoPerfil = driver.findElement(By.xpath("/html/body/div/section[2]/div[2]/div/div[1]/div/div[2]/button[2]"));
        botaoPerfil.click();

        WebElement botaoGerenCursos = driver.findElement(By.xpath("/html/body/div[2]/div[3]/ul/li[2]"));
        botaoGerenCursos.click();

        WebElement botaoNovoCurso = driver.findElement(By.xpath("/html/body/div/div/div[2]/button"));
        botaoNovoCurso.click();

        WebElement campoTitulo = driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div[2]/div[1]/div/div/input"));
        campoTitulo.sendKeys(nomeCurso);

        WebElement campoDescricao = driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div[2]/div[2]/div/div/textarea[1]"));
        campoDescricao.sendKeys(descricaoCurso);

        WebElement checkPin = driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div[2]/div[3]/label/span[1]/span[1]/input"));
        checkPin.click();
        WebElement campoPin = driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div[2]/div[4]/div/div/input"));
        campoPin.sendKeys(pin);

        WebElement botaoSalvar = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/button[2]"));
        botaoSalvar.click();

        WebElement mensagem = driver.findElement(By.xpath("/html/body/div[2]/div[3]/h6"));
        String textoMensagem = mensagem.getText();

        assertEquals("Curso criado com sucesso!", textoMensagem);

        //esperarSegundos(25);
    }

    @After
    public void tearDown() throws Exception{
        driver.quit();
    }
}
