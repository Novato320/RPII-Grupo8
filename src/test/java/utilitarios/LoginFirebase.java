package utilitarios;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;

public class LoginFirebase {
    // Dados para login FIREBASE
    private final String FIREBASE_KEY;
    private final String FIREBASE_VALUE;

    private WebDriver driver;
    private JavascriptExecutor js;

    public LoginFirebase(WebDriver driver, String key, String value) {
        this.driver = driver;
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(50));
        js = (JavascriptExecutor) driver; // inicializa JS Executor
        FIREBASE_KEY = ConfigHelper.get(key); // key.guilherme
        FIREBASE_VALUE = ConfigHelper.get(value); // value.guilherme
    }

    // logica do login
    public void login(){
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
}
