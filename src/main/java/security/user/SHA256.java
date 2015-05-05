package security.user;

import java.security.MessageDigest;

public class SHA256 {

    public static String encrypt(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(input.getBytes());
            byte[] dataBytes = md.digest();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < dataBytes.length; i++) {
                buffer.append(Integer.toString((dataBytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return buffer.toString();
        }
        catch (Exception e) {
            return null;
        }
    }
}