package logic;

import configuration.Configuracao;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.manager.SeleniumManagerOutput;
import org.openqa.selenium.remote.service.DriverFinder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class Raspador {

    private FileWriter fileWriter;

    public void execute(String url) throws IOException {
        System.out.println("Iniciando a busca por comentários...");
        WebDriver driver = new ChromeDriver(getChromeOptions());

        driver.get(url);
        waitForIt(5000);//Configuracao.getWaitForClick());

        processaLinkVerMais(driver);
        processaLinkDeRespostas(driver);

        fileWriter = new FileWriter("resultados/raspagem-"+getLocalDateTimeFormatado()+".csv");
        capturaComentarioTotal(driver);

        fileWriter.close();
        driver.quit();
    }
    private void capturaComentarioTotal(WebDriver driver) throws IOException {
        List<WebElement> webElement = driver.findElements(By.xpath(Configuracao.getXPathExpressionComentarioTotal()));
        for(int i = 0; i < webElement.size(); i++){
            String[] vetorNomeComentarioAno = webElement.get(i).getText().split("\n");
            if(vetorNomeComentarioAno.length >= 3){
                String nome = vetorNomeComentarioAno[0];

                int posicaoAno = vetorNomeComentarioAno.length-1;
                boolean comentarioContemLikes = vetorNomeComentarioAno[posicaoAno].trim().split(" ").length == 1; //quando o comentario tem like, o numero de likes compoem o webElement
                if(comentarioContemLikes) posicaoAno--;
                String distanciaDoDiaDaRaspagem = vetorNomeComentarioAno[posicaoAno];

                String comentario = "";
                for(int a = 1; a < posicaoAno; comentario += vetorNomeComentarioAno[a] + " ", a++);

                String registroComentario = getLocalDateTimeFormatado() + ";" + nome + ";" + distanciaDoDiaDaRaspagem + ";" + comentario + ";" + driver.getCurrentUrl();
                fileWriter.write(registroComentario + "\n");
                System.out.println(registroComentario);
            }
        }
    }

    private String getLocalDateTimeFormatado(){
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-H-mm-ss"));
    }


    private void processaLinkDeRespostas(WebDriver driver) {
        JavascriptExecutor js;
        WebElement webElementMaisRespostas = null;
        try {
            webElementMaisRespostas = driver.findElement(By.xpath(Configuracao.getXPathExpressionRespostas()));
        }catch (Exception e){}
        js = (JavascriptExecutor) driver;
        while(webElementMaisRespostas != null){
            js.executeScript("arguments[0].click();", webElementMaisRespostas);
            waitForIt(Configuracao.getWaitForRenderUrl());
            webElementMaisRespostas = null;
            try {
                webElementMaisRespostas = driver.findElement(By.xpath(Configuracao.getXPathExpressionRespostas()));
            }catch (Exception e){}
        }
    }

    private void processaLinkVerMais(WebDriver driver) {
        WebElement webElementVerMais = null;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            webElementVerMais = driver.findElement(By.xpath(Configuracao.getXPathExpressionVerMais()));
        }catch (Exception e){}
        while(webElementVerMais != null){
            js.executeScript("arguments[0].click();", webElementVerMais);
            waitForIt(Configuracao.getWaitForRenderUrl());
            webElementVerMais = null;
            try {
                webElementVerMais = driver.findElement(By.xpath(Configuracao.getXPathExpressionVerMais()));
            }catch (Exception e){}
        }
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setBinary(getChromeLocation());
        //nao exibe o navegador
        options.addArguments("--headless=new");
        //para corrigir possiveis erros na execução
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        //configurações uteis para prevenção de  bloqueio das buscas
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", null);
        options.addArguments("window-size=1600,800");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
        return options;
    }

    private File getChromeLocation() {
        ChromeOptions options = new ChromeOptions();
        options.setBrowserVersion("stable");
        SeleniumManagerOutput.Result output =
                DriverFinder.getPath(ChromeDriverService.createDefaultService(), options);
        return new File(output.getBrowserPath());
    }

    private void waitForIt(long tempo) {
        try {
            new Thread().sleep(tempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
