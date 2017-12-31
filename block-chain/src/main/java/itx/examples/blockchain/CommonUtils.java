package itx.examples.blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class CommonUtils {

    private CommonUtils() {
    }

    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvxywz";
    private static MessageDigest digest;

    static {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            digest = null;
        }
    }

    public static final String GENESIS_BLOCK_ID = "0";
    public static final String GENESIS_BLOCK_PREVIOUS_HASH = "0000000000000000000000000000000000000000000000000000000000000000";

    public static String getNextBlockId(String lastBlockId) {
        Long id = Long.parseLong(lastBlockId) + 1;
        return id.toString();
    }

    public static String getNextNonce(String lastNonce) {
        if (lastNonce == null) return String.valueOf(ALPHABET.charAt(0));
        int maxIndex = ALPHABET.length() - 1;
        StringBuilder nextNonce = new StringBuilder(lastNonce);
        int remainder = 0;
        for (int i=(lastNonce.length()-1); i>=0; i--) {
            int nextIndex = ALPHABET.indexOf(lastNonce.charAt(i)) + 1;
            if (nextIndex <= maxIndex) {
                nextNonce.setCharAt(i, ALPHABET.charAt(nextIndex));
                remainder = 0;
                break;
            } else {
                nextNonce.setCharAt(i, ALPHABET.charAt(0));
                remainder = 1;
            }
        }
        if (remainder != 0) {
            return ALPHABET.charAt(0) + nextNonce.toString();
        } else {
            return nextNonce.toString();
        }
    }

    public static String createSHA256Hash(String data) {
        byte[] encodedHash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedHash);
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }

}
