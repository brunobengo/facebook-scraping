package configuration;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.Map;

public class Configuration {
    private static Map<String, Object> configuracao;

    static {
        try (InputStream input = Configuration.class.getClassLoader().getResourceAsStream("application.yml")) {
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
    public static String getXPathExpressionLinkVerMais() {
        return (String) ((Map<String, Object>) configuracao.get("facebook")).get("xpathExpression-link-ver-mais");
    }
    public static String getXPathExpressionLinkRespostas() {
        return (String) ((Map<String, Object>) configuracao.get("facebook")).get("xpathExpression-link-respostas");
    }
    public static int getWaitForClick() {
        return (int) ((Map<String, Object>) configuracao.get("facebook")).get("wait-for-click");
    }
    public static int getWaitForRenderUrl() {
        return (int) ((Map<String, Object>) configuracao.get("facebook")).get("wait-for-render-url");
    }
    public static String getXPathExpressionDivComentarioCompleto() {
        return (String) ((Map<String, Object>) configuracao.get("facebook")).get("xpathExpression-div-comentarioCompleto");
    }
    public static int getNroExecucoesVerMais() {
        return (int) ((Map<String, Object>) configuracao.get("facebook")).get("nro-execucoes-ver-mais");
    }
}
