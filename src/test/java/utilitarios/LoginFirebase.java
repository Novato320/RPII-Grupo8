package utilitarios;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;

public class LoginFirebase {
    // Dados para login FIREBASE
    private final String FIREBASE_KEY = "firebase:authUser:AIzaSyARn2qVrSSndFu9JSo5mexrQCMxmORZzCg:[DEFAULT]";
    private final String FIREBASE_VALUE = ConfigHelper.get("login.guilherme");

    private WebDriver driver;
    private JavascriptExecutor js;

    public LoginFirebase(WebDriver driver) {
        this.driver = driver;
        js = (JavascriptExecutor) driver;
    }

    // logica do login
    public static boolean login(){
        return false;
    }
}
