package utilitarios;

import org.junit.Test;
import static org.junit.Assert.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TesteSeleniumJunit {

    @Test
    public void testandoSelenium(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://testes.codefolio.com.br/");

        try{
            Thread.sleep(5000);
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }

        driver.quit();
    }

    @Test
    public void testConfigHelper() {
        String url = ConfigHelper.get("base.url");
        String browser = ConfigHelper.get("browser");

        System.out.println("Executando testes no navegador: " + browser);
        System.out.println("URL base: " + url);

        assertNotNull(url);
        assertEquals("chrome", browser);
    }

}
