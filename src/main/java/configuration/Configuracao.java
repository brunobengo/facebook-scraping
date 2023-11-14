package configuration;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.Map;

public class Configuracao {
    private static Map<String, Object> configuracao;

    static {
        try (InputStream input = Configuracao.class.getClassLoader().getResourceAsStream("application.yml")) {
            if (input == null) {
                System.out.println("Desculpe, não foi possível encontrar o arquivo de configuração.");
            }
            // Carregar as configurações do arquivo YAML
            Yaml yaml = new Yaml();
            configuracao = yaml.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Exemplo de como obter uma configuração
    public static String getXPathExpressionComentarios() {
        return (String) ((Map<String, Object>) configuracao.get("facebook")).get("xpathExpression-comentarios");
    }
    public static String getXPathExpressionVerMais() {
        return (String) ((Map<String, Object>) configuracao.get("facebook")).get("xpathExpression-ver-mais");
    }
    public static String getXPathExpressionRespostas() {
        return (String) ((Map<String, Object>) configuracao.get("facebook")).get("xpathExpression-respostas");
    }
    public static int getWaitForClick() {
        return (int) ((Map<String, Object>) configuracao.get("facebook")).get("wait-for-click");
    }
    public static int getWaitForRenderUrl() {
        return (int) ((Map<String, Object>) configuracao.get("facebook")).get("wait-for-render-url");
    }
}
