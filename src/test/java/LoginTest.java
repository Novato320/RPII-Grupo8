import org.junit.*;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.*;

public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;

    @Before
    public void setUp() {
        driver = DriverFactory.getDriver();
        loginPage = new LoginPage(driver);
        loginPage.acessarPaginaLogin();
    }

    @After
    public void tearDown() {
        DriverFactory.killDriver();
    }

    @Test
    public void testLoginComSucesso() {
        loginPage.preencherUsuario("tomsmith");
        loginPage.preencherSenha("SuperSecretPassword!");
        loginPage.clicarLogin();

        String mensagem = loginPage.obterMensagem();
        assertTrue("Mensagem de sucesso não encontrada!", mensagem.contains("You logged into a secure area!"));
    }

    @Test
    public void testLoginInvalido() {
        loginPage.preencherUsuario("usuario_invalido");
        loginPage.preencherSenha("senha_errada");
        loginPage.clicarLogin();

        String mensagem = loginPage.obterMensagem();
        assertTrue("Mensagem de erro não encontrada!", mensagem.contains("Your username is invalid!"));
    }
}
