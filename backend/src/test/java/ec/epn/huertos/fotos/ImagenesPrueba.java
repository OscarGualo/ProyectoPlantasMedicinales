package ec.epn.huertos.fotos;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Fotografías del doc 05 §7, generadas en memoria para no versionar binarios:
 * F1 JPEG válido de 1 MB; F2 texto con extensión .jpg; F3 PNG válido de 6 MB; F4 JPEG válido de exactamente
 * 5 MB (5 242 880 bytes). El relleno se agrega después del marcador final, así la imagen sigue siendo válida.
 */
public final class ImagenesPrueba {

    public static final int MB = 1024 * 1024;

    private ImagenesPrueba() {
    }

    public static byte[] f1() {
        return rellenar(imagen("jpg"), MB);
    }

    public static byte[] f2() {
        return "Archivo de texto con extensión .jpg".getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] f3() {
        return rellenar(imagen("png"), 6 * MB);
    }

    public static byte[] f4() {
        return rellenar(imagen("jpg"), 5 * MB);
    }

    private static byte[] imagen(String formato) {
        BufferedImage img = new BufferedImage(64, 48, BufferedImage.TYPE_INT_RGB);
        var g = img.createGraphics();
        g.setColor(new Color(46, 125, 50));
        g.fillRect(0, 0, 64, 48);
        g.dispose();
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ImageIO.write(img, formato, out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static byte[] rellenar(byte[] imagen, int tamano) {
        return Arrays.copyOf(imagen, tamano);
    }
}
