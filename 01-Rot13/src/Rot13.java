public class Rot13 {

    private static final char[] minuscules = {
        'a', 'à', 'á', 'b', 'c', 'ç', 'd', 'e', 'è', 'é', 'f', 'g', 'h', 'i', 'í', 'ì', 'ï', 
        'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'ó', 'ò', 'p', 'q', 'r', 's', 't', 'u', 'ú', 'ù', 'ü', 'v', 'w', 'x', 'y', 'z'
    };

    private static final char[] majuscules = {
        'A', 'À', 'Á', 'B', 'C', 'Ç', 'D', 'E', 'È', 'É', 'F', 'G', 'H', 'I', 'Í', 'Ì', 'Ï', 
        'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'Ó', 'Ò', 'P', 'Q', 'R', 'S', 'T', 'U', 'Ú', 'Ù', 'Ü', 'V', 'W', 'X', 'Y', 'Z'
    };

    public static String xifraRot13(String cadena) {
        return transformar(cadena, 13);
    }

    public static String desxifraRot13(String cadena) {
        return transformar(cadena, -13);
    }

    private static String transformar(String cadena, int desplaçament) {
        StringBuilder resultat = new StringBuilder();

        for (char c : cadena.toCharArray()) {
            int posMinus = trobarPosicio(minuscules, c);
            int posMajus = trobarPosicio(majuscules, c);

            if (posMinus != -1) {
                int novaPos = calcularNovaPosicio(posMinus, desplaçament, minuscules.length);
                resultat.append(minuscules[novaPos]);
            } else if (posMajus != -1) {
                int novaPos = calcularNovaPosicio(posMajus, desplaçament, majuscules.length);
                resultat.append(majuscules[novaPos]);
            } else {
                resultat.append(c);
            }
        }

        return resultat.toString();
    }
    private static int trobarPosicio(char[] array, char c) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == c) return i;
        }
        return -1;
    }

    private static int calcularNovaPosicio(int posActual, int desplaçament, int mida) {
        int novaPos = (posActual + desplaçament) % mida;
        if (novaPos < 0) {
            novaPos += mida;
        }
        return novaPos;
    }
    public static void main(String[] args) {
        String[] proves = {
            "ABC",
            "XYZ",
            "Hola, Mr. calçot",
            "Perdó, per tu què és?"
        };

        System.out.println("Xifrat");
        System.out.println("---------");
        for (String text : proves) {
            System.out.println(text + " => " + xifraRot13(text));
        }

        System.out.println("\nDesxifrat");
        System.out.println("---------");
        for (String text : proves) {
            String xifrat = xifraRot13(text);
            System.out.println(xifrat + " => " + desxifraRot13(xifrat));
        }
    }
}