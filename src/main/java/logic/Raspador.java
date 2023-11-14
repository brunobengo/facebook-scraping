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
import java.util.Collections;
import java.util.List;

public class Raspador {

    public void execute(String url){
        System.out.println("Iniciando a busca por comentários...");
        WebDriver driver = new ChromeDriver(getChromeOptions());

        driver.get(url);
        waitForIt(Configuracao.getWaitForRenderUrl());

//        processaLinkComentario(driver);
        processaLinkVerMais(driver);
        processaLinkDeRespostas(driver);

        capturaComentarios(driver);

        driver.quit();
    }

    private void processaLinkComentario(WebDriver driver) {
        JavascriptExecutor js;
        WebElement webElementMaisRespostas = null;
        try {
            webElementMaisRespostas = driver.findElement(By.xpath("//span[contains(text(), ' comentário')]"));
        }catch (Exception e){}
        js = (JavascriptExecutor) driver;
        while(webElementMaisRespostas != null){
            js.executeScript("arguments[0].click();", webElementMaisRespostas);
            waitForIt(Configuracao.getWaitForRenderUrl());
            webElementMaisRespostas = null;
            try {
                webElementMaisRespostas = driver.findElement(By.xpath("//span[contains(text(), ' comentário')]"));
            }catch (Exception e){}
        }
    }

    private static void capturaComentarios(WebDriver driver) {
        List<WebElement> webElementsComentarios = driver.findElements(By.xpath(Configuracao.getXPathExpressionComentarios()));
        for(int i = 0; i < webElementsComentarios.size(); i++){
            System.out.println(webElementsComentarios.get(i).getText());
        }
        System.out.println(webElementsComentarios.size() + " comentários ");
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
