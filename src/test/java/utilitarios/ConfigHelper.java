package utilitarios;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigHelper {

    private static Properties props = new Properties();

    static {
        try (InputStream input = ConfigHelper.class.getResourceAsStream("/test-config.properties")) {
            if (input == null) {
                throw new RuntimeException("Arquivo de configuração não encontrado!");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar configurações de teste", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
