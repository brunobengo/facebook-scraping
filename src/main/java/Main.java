import logic.Raspador;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Raspador raspador = new Raspador();
//        raspador.execute("https://www.facebook.com/groups/1509648409266249/search/?q=colombo");
        raspador.execute("https://www.facebook.com/photo/?fbid=1947116122119678");
//        raspador.execute("https://www.facebook.com/photo/?fbid=1947116122119678");
        //"https://www.facebook.com/photo/?fbid=6448664415224061"
    }
}