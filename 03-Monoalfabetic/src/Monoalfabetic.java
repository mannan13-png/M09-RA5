import java.util.Random;

public class Monoalfabetic {

    private static final char[] alfabet = {
        'A', 'À', 'Á', 'B', 'C', 'Ç', 'D', 'E', 'È', 'É',
        'F', 'G', 'H', 'I', 'Í', 'Ï', 'J', 'K', 'L', 'M',
        'N', 'Ñ', 'O', 'Ó', 'Ò', 'P', 'Q', 'R', 'S', 'T',
        'U', 'Ú', 'Ù', 'Ü', 'V', 'W', 'X', 'Y', 'Z'
    };

    private static final char[] alfabetPermutat = crearPermutacio();

    private static char[] crearPermutacio() {
        return permutarAlfabet(alfabet);
    }

    public static char[] permutarAlfabet(char[] alfabet) {
        char[] resultat = alfabet.clone();
        Random random = new Random();

        for (int i = resultat.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);

            char temporal = resultat[i];
            resultat[i] = resultat[j];
            resultat[j] = temporal;
        }

        return resultat;
    }

    public static String xifraMonoAlfa(String cadena) {
        StringBuilder resultat = new StringBuilder();

        for (char c : cadena.toCharArray()) {
            char majuscula = Character.toUpperCase(c);
            int posicio = trobarPosicio(alfabet, majuscula);

            if (posicio != -1) {
                char xifrat = alfabetPermutat[posicio];

                if (Character.isLowerCase(c)) {
                    resultat.append(Character.toLowerCase(xifrat));
                } else {
                    resultat.append(xifrat);
                }
            } else {
                resultat.append(c);
            }
        }

        return resultat.toString();
    }

    public static String desxifraMonoAlfa(String cadena) {
        StringBuilder resultat = new StringBuilder();

        for (char c : cadena.toCharArray()) {
            char majuscula = Character.toUpperCase(c);
            int posicio = trobarPosicio(alfabetPermutat, majuscula);

            if (posicio != -1) {
                char desxifrat = alfabet[posicio];

                if (Character.isLowerCase(c)) {
                    resultat.append(Character.toLowerCase(desxifrat));
                } else {
                    resultat.append(desxifrat);
                }
            } else {
                resultat.append(c);
            }
        }

        return resultat.toString();
    }

    private static int trobarPosicio(char[] array, char c) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == c) {
                return i;
            }
        }

        return -1;
    }

    public static void main(String[] args) {

        String[] proves = {
            "Test 01 àrbitre, coixí, Perímetre",
            "Test 02 Taüll, DÍA, año",
            "Test 03 Peça, Òrrius, Bòvila"
        };

        System.out.println("Alfabet permutat:");

        for (char c : alfabetPermutat) {
            System.out.print(c + " ");
        }

        System.out.println();
        System.out.println();

        System.out.println("Xifratge:");

        for (String prova : proves) {
            String xifrat = xifraMonoAlfa(prova);
            System.out.println(prova + " -> " + xifrat);
        }

        System.out.println();
        System.out.println("Desxifratge:");

        for (String prova : proves) {
            String xifrat = xifraMonoAlfa(prova);
            String desxifrat = desxifraMonoAlfa(xifrat);
            System.out.println(xifrat + " -> " + desxifrat);
        }
    }
}