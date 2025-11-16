package inutil;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    private By campoUsuario = By.id("username");
    private By campoSenha = By.id("password");
    private By botaoLogin = By.cssSelector("button[type='submit']");
    private By mensagem = By.id("flash");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void acessarPaginaLogin() {
        driver.get("https://the-internet.herokuapp.com/login");
    }

    public void preencherUsuario(String usuario) {
        driver.findElement(campoUsuario).sendKeys(usuario);
    }

    public void preencherSenha(String senha) {
        driver.findElement(campoSenha).sendKeys(senha);
    }

    public void clicarLogin() {
        driver.findElement(botaoLogin).click();
    }

    public String obterMensagem() {
        return driver.findElement(mensagem).getText();
    }
}
