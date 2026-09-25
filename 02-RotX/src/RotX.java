public class RotX {

    private static final char[] minuscules = {
        'a', 'à', 'á', 'b', 'c', 'ç', 'd', 'e', 'è', 'é',
        'f', 'g', 'h', 'i', 'í', 'ì', 'ï', 'j', 'k', 'l',
        'm', 'n', 'ñ', 'o', 'ó', 'ò', 'p', 'q', 'r', 's',
        't', 'u', 'ú', 'ù', 'ü', 'v', 'w', 'x', 'y', 'z'
    };

    private static final char[] majuscules = {
        'A', 'À', 'Á', 'B', 'C', 'Ç', 'D', 'E', 'È', 'É',
        'F', 'G', 'H', 'I', 'Í', 'Ì', 'Ï', 'J', 'K', 'L',
        'M', 'N', 'Ñ', 'O', 'Ó', 'Ò', 'P', 'Q', 'R', 'S',
        'T', 'U', 'Ú', 'Ù', 'Ü', 'V', 'W', 'X', 'Y', 'Z'
    };

    private static int trobarPosicio(char[] array, char c) {

        for (int i = 0; i < array.length; i++) {

            if (array[i] == c) {
                return i;
            }
        }

        return -1;
    }

    private static int calcularNovaPosicio(
            int posActual,
            int desplaçament,
            int mida) {

        int novaPos = (posActual + desplaçament) % mida;

        if (novaPos < 0) {
            novaPos += mida;
        }

        return novaPos;
    }

    public static String xifraRotX(
            String cadena,
            int desplaçament) {

        return transformar(cadena, desplaçament);
    }

    public static String desxifraRotX(
            String cadena,
            int desplaçament) {

        return transformar(cadena, -desplaçament);
    }

    private static String transformar(
            String cadena,
            int desplaçament) {

        StringBuilder resultat = new StringBuilder();

        for (char c : cadena.toCharArray()) {

            int posMinus =
                trobarPosicio(minuscules, c);

            int posMajus =
                trobarPosicio(majuscules, c);

            if (posMinus != -1) {

                int novaPos =
                    calcularNovaPosicio(
                        posMinus,
                        desplaçament,
                        minuscules.length
                    );

                resultat.append(minuscules[novaPos]);

            } else if (posMajus != -1) {

                int novaPos =
                    calcularNovaPosicio(
                        posMajus,
                        desplaçament,
                        majuscules.length
                    );

                resultat.append(majuscules[novaPos]);

            } else {

                resultat.append(c);
            }
        }

        return resultat.toString();
    }

    public static void forçaBrutaRotX(
            String cadenaXifrada) {

        for (int desplaçament = 0;
             desplaçament < minuscules.length;
             desplaçament++) {

            System.out.println(
                "(" + desplaçament + ")->"
                + desxifraRotX(
                    cadenaXifrada,
                    desplaçament
                )
            );
        }
    }

    public static void main(String[] args) {

        String missatge = "Hola, Mr. calçot";

        System.out.println("Xifrat");
        System.out.println("---------");

        System.out.println(
            "(0) " + missatge
            + " => "
            + xifraRotX(missatge, 0)
        );

        System.out.println(
            "(2) " + missatge
            + " => "
            + xifraRotX(missatge, 2)
        );

        System.out.println(
            "(4) " + missatge
            + " => "
            + xifraRotX(missatge, 4)
        );

        System.out.println();
        System.out.println("Desxifrat");
        System.out.println("---------");

        String xifrat2 =
            xifraRotX(missatge, 2);

        String xifrat4 =
            xifraRotX(missatge, 4);

        System.out.println(
            "(2) " + xifrat2
            + " => "
            + desxifraRotX(xifrat2, 2)
        );

        System.out.println(
            "(4) " + xifrat4
            + " => "
            + desxifraRotX(xifrat4, 4)
        );

        System.out.println();
        System.out.println("Força bruta");
        System.out.println("-----------");

        String missatgeXifrat =
            xifraRotX(
                "Perdó, per tu què és?",
                6
            );

        System.out.println(
            "Missatge xifrat: "
            + missatgeXifrat
        );

        System.out.println();

        forçaBrutaRotX(missatgeXifrat);
    }
}