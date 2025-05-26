package hse.studying.filestoringservice.service;

import java.io.InputStream;
import java.security.MessageDigest;
import org.apache.commons.codec.binary.Hex;

/**
 * Утилита для быстрого подсчёта SHA-256
 */
public final class HashUtils {
    private HashUtils() {}

    public static String sha256Hex(InputStream in) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] buf = new byte[8192];
            int n;
            while ((n = in.read(buf)) > 0) {
                digest.update(buf, 0, n);
            }
            return Hex.encodeHexString(digest.digest());
        } catch (Exception e) {
            throw new IllegalStateException("Cannot calculate SHA-256", e);
        }
    }
}