import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Polialfabetic {

    public static final String ALFABET = "aàáäbcçdeèéëfghiìíïjklmnñoòóöpqrstuùúüvwxyz" +
                                         "AÀÁÄBCÇDEÈÉËFGHIÌÍÏJKLMNÑOÒÓÖPQRSTUÙÚÜVWXYZ" +
                                         "0123456789 ,.:;!?¡¿\"'()-";

    private static String clauSecreta = "laSevaContrasenya123";
    public static Random random;
    public static List<Character> alfabetPermutat = new ArrayList<>();

    public static void initRandom(String clau) {
        random = new Random(clau.hashCode());
    }

    public static void permutaAlfabet() {
        alfabetPermutat.clear();
        for (char c : ALFABET.toCharArray()) {
            alfabetPermutat.add(c);
        }
        Collections.shuffle(alfabetPermutat, random); 
    }

    public static String xifraPoliAlfa(String msg) {
        StringBuilder resultat = new StringBuilder();

        for (int i = 0; i < msg.length(); i++) {
            char lletraOriginal = msg.charAt(i);
            permutaAlfabet();

            int posicio = ALFABET.indexOf(lletraOriginal);
            if (posicio != -1) {
                resultat.append(alfabetPermutat.get(posicio));
            } else {
                resultat.append(lletraOriginal);
            }
        }

        return resultat.toString();
    }

    public static String desxifraPoliAlfa(String msgXifrat) {
        StringBuilder resultat = new StringBuilder();

        for (int i = 0; i < msgXifrat.length(); i++) {
            char lletraXifrada = msgXifrat.charAt(i);
            permutaAlfabet();

            int posicio = alfabetPermutat.indexOf(lletraXifrada);
            if (posicio != -1) {
                resultat.append(ALFABET.charAt(posicio));
            } else {
                resultat.append(lletraXifrada);
            }
        }

        return resultat.toString();
    }

    public static void main(String[] args) {
        String msgs[] = {"Test 01 àrbitre, coixí, Perímetre",
                         "Test 02 Taüll, DÍA, año",
                         "Test 03 Peça, Òrrius, Bòvila"};
        String msgsXifrats[] = new String[msgs.length];

        System.out.println("Xifratge:\n---------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            msgsXifrats[i] = xifraPoliAlfa(msgs[i]);
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]);
        }

        System.out.println("\nDesxifratge:\n------------");
        for (int i = 0; i < msgs.length; i++) {
            initRandom(clauSecreta);
            String msg = desxifraPoliAlfa(msgsXifrats[i]);
            System.out.printf("%-34s -> %s%n", msgsXifrats[i], msg);
        }
    }
}