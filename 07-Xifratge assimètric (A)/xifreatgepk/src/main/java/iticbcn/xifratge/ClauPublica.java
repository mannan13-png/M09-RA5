
package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.MGF1ParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

public class ClauPublica {

    private static final OAEPParameterSpec OAEP_PARAMS =
            new OAEPParameterSpec(
                    "SHA-256",
                    "MGF1",
                    MGF1ParameterSpec.SHA256,
                    PSource.PSpecified.DEFAULT
            );

    public KeyPair generaParellClausRSA() throws Exception {
        KeyPairGenerator generador =
                KeyPairGenerator.getInstance("RSA");

        generador.initialize(2048);

        return generador.generateKeyPair();
    }

    public byte[] xifraRSA(
            String msg,
            PublicKey clauPublica) throws Exception {

        Cipher cipher = Cipher.getInstance(
                "RSA/ECB/OAEPWithSHA-256AndMGF1Padding");

        cipher.init(
                Cipher.ENCRYPT_MODE,
                clauPublica,
                OAEP_PARAMS
        );

        return cipher.doFinal(
                msg.getBytes(StandardCharsets.UTF_8));
    }

    public String desxifraRSA(
            byte[] msgXifrat,
            PrivateKey clauPrivada) throws Exception {

        Cipher cipher = Cipher.getInstance(
                "RSA/ECB/OAEPWithSHA-256AndMGF1Padding");

        cipher.init(
                Cipher.DECRYPT_MODE,
                clauPrivada,
                OAEP_PARAMS
        );

        byte[] resultat = cipher.doFinal(msgXifrat);

        return new String(resultat, StandardCharsets.UTF_8);
    }
}
