import utilitarios.LoginFirebase;
import utilitarios.ConfigHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * @Name: CT-09 Cadastro de Curso.
 * @Requirement: RF-09 O sistema deve permitir editar informações ou substituir slides já cadastrados
 * @author Guilherme Jaques
 */
public class CasoDeTeste09 {
    private String slide = ConfigHelper.get("slide.guilherme");

    private WebDriver driver;
    private ChromeOptions options;
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

        loginFirebase = new LoginFirebase(driver);
    }

    @Test
    public void testEditarSlide(){
        loginFirebase.login();

        //esperarSegundos(5);
    }

    @Test
    public void testSubstituirSlide(){

    }

    @After
    public void tearDown(){
        driver.quit();
    }
}