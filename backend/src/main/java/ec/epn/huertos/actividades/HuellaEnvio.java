package ec.epn.huertos.actividades;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

final class HuellaEnvio {
    private HuellaEnvio() {
    }

    static String de(String... campos) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // La longitud distingue campos contiguos y separa null de un valor vacío.
            for (String campo : campos) {
                byte[] bytes = campo == null ? null : campo.getBytes(StandardCharsets.UTF_8);
                digest.update(ByteBuffer.allocate(Integer.BYTES).putInt(bytes == null ? -1 : bytes.length).array());
                if (bytes != null) {
                    digest.update(bytes);
                }
            }
            return HexFormat.of().formatHex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 no disponible", e);
        }
    }
}
