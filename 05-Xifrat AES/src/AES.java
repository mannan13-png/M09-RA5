import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;
    private static final String CLAU = "LaClauSecretaQueVulguis";

    public static byte[] xifraAES(String msg, String clau)
            throws Exception {

        byte[] missatgeBytes = msg.getBytes(StandardCharsets.UTF_8);

        byte[] iv = new byte[MIDA_IV];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        byte[] hash = generaHash(clau);
        SecretKeySpec secretKey =
                new SecretKeySpec(hash, ALGORISME_XIFRAT);

        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

        byte[] missatgeXifrat = cipher.doFinal(missatgeBytes);

        byte[] resultat = new byte[iv.length + missatgeXifrat.length];

        System.arraycopy(iv, 0, resultat, 0, iv.length);
        System.arraycopy(
                missatgeXifrat, 0, resultat, iv.length,
                missatgeXifrat.length
        );

        return resultat;
    }

    public static String desxifraAES(byte[] bIvMsgXifrat, String clau)
            throws Exception {

        byte[] iv = Arrays.copyOfRange(
                bIvMsgXifrat, 0, MIDA_IV
        );

        byte[] missatgeXifrat = Arrays.copyOfRange(
                bIvMsgXifrat, MIDA_IV, bIvMsgXifrat.length
        );

        byte[] hash = generaHash(clau);
        SecretKeySpec secretKey =
                new SecretKeySpec(hash, ALGORISME_XIFRAT);

        
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

        byte[] missatgeBytes = cipher.doFinal(missatgeXifrat);

        return new String(missatgeBytes, StandardCharsets.UTF_8);
    }

    private static byte[] generaHash(String password)
            throws Exception {

        MessageDigest digest =
                MessageDigest.getInstance(ALGORISME_HASH);

        return digest.digest(
                password.getBytes(StandardCharsets.UTF_8)
        );
    }

    public static void main(String[] args) {

        String[] msgs = {
            "Lorem ipsum dicet",
            "Hola Andrés cómo está tu cuñado",
            "Àgora illa Òtto"
        };

        for (int i = 0; i < msgs.length; i++) {

            String msg = msgs[i];
            byte[] bXifrats = null;
            String desxifrat = "";

            try {
                bXifrats = xifraAES(msg, CLAU);
                desxifrat = desxifraAES(bXifrats, CLAU);

            } catch (Exception e) {
                System.err.println(
                        "Error de xifrat: "
                        + e.getLocalizedMessage()
                );
            }

            System.out.println("--------------------");
            System.out.println("Msg: " + msg);
            System.out.println("Enc: " + new String(bXifrats));
            System.out.println("DEC: " + desxifrat);
        }
    }
}