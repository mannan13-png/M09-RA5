
package iticbcn.xifratge;

import java.security.KeyPair;
import java.util.HexFormat;

public class Main {

    public static void main(String[] args) throws Exception {

        ClauPublica cp = new ClauPublica();

        String msg = "Missatge de prova per xifrar àéióù àèìòù";

        KeyPair parellClaus = cp.generaParellClausRSA();

        byte[] msgXifrat =
                cp.xifraRSA(msg, parellClaus.getPublic());

        System.out.println("==============================");
        System.out.println("Text xifrat:");
        System.out.println(HexFormat.of().formatHex(msgXifrat));

        String msgDesxifrat =
                cp.desxifraRSA(
                        msgXifrat,
                        parellClaus.getPrivate());

        System.out.println("==============================");
        System.out.println("Text desxifrat: " + msgDesxifrat);

        if (msg.equals(msgDesxifrat)) {
            System.out.println("Resultat: CORRECTE");
        } else {
            throw new IllegalStateException(
                    "El missatge desxifrat no coincideix!");
        }

        String clauPublica = HexFormat.of().formatHex(
                parellClaus.getPublic().getEncoded());

        System.out.println("==============================");
        System.out.println("Clau pública: " + clauPublica);
    }
}
