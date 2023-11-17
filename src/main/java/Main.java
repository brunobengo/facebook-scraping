import logic.Raspador;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Raspador raspador = new Raspador();
        raspador.execute("https://www.facebook.com/photo/?fbid=6448664415224061");
//        raspador.execute("https://www.facebook.com/photo/?fbid=1947116122119678");
    }
}