package ec.epn.huertos.fotos;

import ec.epn.huertos.comun.ErrorNegocio;
import ec.epn.huertos.comun.Mensaje;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

/**
 * RN-05: la fotografía es JPEG o PNG, con contenido coherente con su formato y un tamaño máximo de
 * 5 MB (5 242 880 bytes). Se valida por la firma del archivo y decodificándolo, no por la extensión.
 */
@Component
public class ValidadorImagen {

    public static final int TAMANO_MAXIMO = 5 * 1024 * 1024;

    private static final byte[] FIRMA_JPEG = {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF};
    private static final byte[] FIRMA_PNG = {(byte) 0x89, 'P', 'N', 'G', 0x0D, 0x0A, 0x1A, 0x0A};

    /** Devuelve la imagen validada o lanza MSG-07. */
    public ImagenValidada validar(byte[] contenido) {
        if (contenido == null || contenido.length == 0 || contenido.length > TAMANO_MAXIMO) {
            throw ErrorNegocio.enCampo(Mensaje.MSG_07, "foto");
        }
        String tipo;
        if (empiezaCon(contenido, FIRMA_JPEG)) {
            tipo = "image/jpeg";
        } else if (empiezaCon(contenido, FIRMA_PNG)) {
            tipo = "image/png";
        } else {
            throw ErrorNegocio.enCampo(Mensaje.MSG_07, "foto");
        }
        if (!decodificable(contenido)) {
            throw ErrorNegocio.enCampo(Mensaje.MSG_07, "foto");
        }
        return new ImagenValidada(contenido, tipo, sha256(contenido));
    }

    private static boolean empiezaCon(byte[] datos, byte[] firma) {
        if (datos.length < firma.length) {
            return false;
        }
        for (int i = 0; i < firma.length; i++) {
            if (datos[i] != firma[i]) {
                return false;
            }
        }
        return true;
    }

    private static boolean decodificable(byte[] datos) {
        try {
            return ImageIO.read(new ByteArrayInputStream(datos)) != null;
        } catch (IOException | RuntimeException e) {
            return false;
        }
    }

    private static String sha256(byte[] datos) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(datos));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
