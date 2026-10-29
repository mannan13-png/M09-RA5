
import java.security.MessageDigest;
import java.util.HexFormat;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {

    private int npass = 0;

    private static final String CHARSET =
            "abcdefABCDEF1234567890!";

    public String getSHA512AmbSalt(String pw, String salt)
            throws Exception {

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] bytes = md.digest(
                (pw + salt).getBytes("UTF-8"));

        return HexFormat.of().formatHex(bytes);
    }

    public String getPBKDF2AmbSalt(String pw, String salt)
            throws Exception {

        PBEKeySpec spec = new PBEKeySpec(
                pw.toCharArray(),
                salt.getBytes("UTF-8"),
                10000,
                160
        );

        try {
            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] bytes = factory.generateSecret(spec).getEncoded();
            return HexFormat.of().formatHex(bytes);
        } finally {
            spec.clearPassword();
        }
    }

    
public String forcaBruta(String alg, String hash, String salt)
        throws Exception {

    npass = 0;

    char[] pw = new char[6];

    return provar(alg, hash, salt, pw, 0);
}


    private String provar(
            String alg, String hash, String salt,
            char[] pw, int posicio) throws Exception {

        if (posicio == pw.length) {
            npass++;

            String candidat = new String(pw);
            String hashCandidat;

            if (alg.equalsIgnoreCase("SHA-512")) {
                hashCandidat = getSHA512AmbSalt(candidat, salt);
            } else if (alg.equalsIgnoreCase("PBKDF2")) {
                hashCandidat = getPBKDF2AmbSalt(candidat, salt);
            } else {
                throw new IllegalArgumentException(
                        "Algorisme desconegut: " + alg);
            }

            if (hashCandidat.equalsIgnoreCase(hash)) {
                return candidat;
            }

            return null;
        }

        for (int i = 0; i < CHARSET.length(); i++) {
            pw[posicio] = CHARSET.charAt(i);

            String resultat = provar(
                    alg, hash, salt, pw, posicio + 1);

            if (resultat != null) {
                return resultat;
            }
        }

        return null;
    }

    public String getInterval(long t1, long t2) {
        long total = Math.max(0, t2 - t1);

        long dies = total / 86400000;
        total %= 86400000;

        long hores = total / 3600000;
        total %= 3600000;

        long minuts = total / 60000;
        total %= 60000;

        long segons = total / 1000;
        long millis = total % 1000;

        return dies + " dies / " + hores + " hores / "
                + minuts + " minuts / " + segons
                + " segons / " + millis + " millis";
    }

    public static void main(String[] args) throws Exception {

        String salt = "qpoweiurañskldfjz";
        String pw = "aaabF!";

        Hashes h = new Hashes();

        String[] aHashes = {
            h.getSHA512AmbSalt(pw, salt),
            h.getPBKDF2AmbSalt(pw, salt)
        };

        String pwTrobat;
        String[] algoritmes = {"SHA-512", "PBKDF2"};

        for (int i = 0; i < aHashes.length; i++) {
            System.out.println("============================");
            System.out.printf("Algorisme: %s%n", algoritmes[i]);
            System.out.printf("Hash: %s%n", aHashes[i]);
            System.out.println("----------------------------");
            System.out.println("-- Inici de força bruta --");

            long t1 = System.currentTimeMillis();
            pwTrobat = h.forcaBruta(
                    algoritmes[i], aHashes[i], salt);
            long t2 = System.currentTimeMillis();

            System.out.printf("Pass : %s%n", pwTrobat);
            System.out.printf("Proves: %d%n", h.npass);
            System.out.printf("Temps : %s%n", h.getInterval(t1, t2));
            System.out.println("----------------------------\n");
        }
    }
}
