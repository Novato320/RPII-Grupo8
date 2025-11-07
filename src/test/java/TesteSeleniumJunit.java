import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.*;

public class TesteSeleniumJunit {

    @Test
    public void testandoSelenium(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://testes.codefolio.com.br/");

    }
}
