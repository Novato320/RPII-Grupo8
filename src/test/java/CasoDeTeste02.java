import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

/**
 * CT-02 Cadastro de Curso
 * @author Guilherme Jaques
 * RF-02 O sistema deve permitir que o usuário adicione novos cursos,
 * informando título, descrição e PIN (opcional).
 *
 */
public class CasoDeTeste02 {
    public String nomeCurso = "Curso-Teste G.8";
    public String descricaoCurso = "Esse curso foi criado para testes automatizados.";
    public String pin = "1234abc";

    WebDriver driver;

    @Before
    public void setUp() throws Exception{
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://testes.codefolio.com.br/");
    }

    @Test
    public void cadastroSemPin(){

    }

    @Test
    public void cadastroComPin(){

    }

    @After
    public void tearDown() throws Exception{
        driver.quit();
    }
}
