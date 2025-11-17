import org.openqa.selenium.support.ui.ExpectedConditions;
import utilitarios.LoginFirebase;
import utilitarios.ConfigHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * @Name: CT-09 Cadastro de Curso.
 * @Requirement: RF-09 O sistema deve permitir editar informações ou substituir slides já cadastrados
 * @author Guilherme Jaques
 */
public class CasoDeTeste09 {
    private String linkSite = ConfigHelper.get("site");

    private String tituloSlide = "Slide-Teste G.8";
    private String slide = ConfigHelper.get("slide1");
    private String descricaoSlide = "Esse slide foi criado para testes.";

    private WebDriver driver;
    private ChromeOptions options;
    private WebDriverWait wait;

    private LoginFirebase loginFirebase;

    public void esperarSegundos(int segundos){
        try {
            Thread.sleep(Duration.ofSeconds(segundos).toMillis());
        }catch (InterruptedException e){
            System.out.println("Erro au pausar a Thread");
            Thread.currentThread().interrupt();
        }
    }

    @Before
    public void setUp() {
        options = new ChromeOptions(); // parametro para o driver
        //options.addArguments("--headless"); // executa sem interface
        //options.addArguments("--disable-gpu");// recomendado no Windows
        //options.addArguments("--window-size=1920,1080"); // define tamanho da tela virtual

        driver = new ChromeDriver(options);
        driver.manage().window().maximize(); // tela cheia

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20)); // tempo para carregar a pagina
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // tempo para procurar um elemento

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginFirebase = new LoginFirebase(driver);
    }

    @Test
    public void testEditarSlide(){
        driver.get(linkSite);
        loginFirebase.login();

        WebElement botaoPerfil = driver.findElement(By.xpath("/html/body/div/section[2]/div[2]/div/div[1]/div/div[2]/button[2]"));
        botaoPerfil.click();

        WebElement botaoGerenCursos = driver.findElement(By.xpath("/html/body/div[2]/div[3]/ul/li[2]"));
        botaoGerenCursos.click();

        WebElement botaoGenrenCurso = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div/div[3]/div/div/div[1]/div/div[2]/button[1]")));
        botaoGenrenCurso.click();

        WebElement botaoSlides = driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div[3]/div/div/div/button[2]"));
        botaoSlides.click();

        WebElement campoTituloSlide = driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div[5]/div[1]/div/div[1]/div/div/input"));
        campoTituloSlide.sendKeys(tituloSlide);

        WebElement campoSlide = driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div[5]/div[1]/div/div[2]/div/div/input"));
        campoSlide.sendKeys(slide);

        WebElement campoDescricaoSlide = driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div[5]/div[1]/div/div[3]/div/div/textarea[1]"));
        campoDescricaoSlide.sendKeys(descricaoSlide);

        WebElement botaoAdiconarSlide = driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div[5]/div[1]/div/div[4]/button"));
        botaoAdiconarSlide.click();

        WebElement mensagem = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/div[1]/p"));
        String textoMensagem = mensagem.getText();

        assertEquals("O slide foi adicionado com sucesso!", textoMensagem);

        esperarSegundos(5);
    }

    @Test
    public void testSubstituirSlide(){
        driver.get(linkSite);
        loginFirebase.login();

        WebElement botaoPerfil = driver.findElement(By.xpath("/html/body/div/section[2]/div[2]/div/div[1]/div/div[2]/button[2]"));
        botaoPerfil.click();

        WebElement botaoGerenCursos = driver.findElement(By.xpath("/html/body/div[2]/div[3]/ul/li[2]"));
        botaoGerenCursos.click();

        WebElement botaoGenrenCurso = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div/div[3]/div/div/div[1]/div/div[2]/button[1]")));
        botaoGenrenCurso.click();

        WebElement botaoSlides = driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div[3]/div/div/div/button[2]"));
        botaoSlides.click();

        esperarSegundos(50);
    }

    @After
    public void tearDown(){
        driver.quit();
    }
}