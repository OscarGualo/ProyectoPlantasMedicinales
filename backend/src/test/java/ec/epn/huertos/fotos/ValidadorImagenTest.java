package ec.epn.huertos.fotos;

import ec.epn.huertos.comun.ErrorNegocio;
import ec.epn.huertos.comun.Mensaje;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** RN-05: formato real JPEG o PNG y tamaño máximo de 5 242 880 bytes (lógica de P-015). */
class ValidadorImagenTest {

    private final ValidadorImagen validador = new ValidadorImagen();

    @Test
    @DisplayName("F1: JPEG válido de 1 MB se acepta")
    void jpegValido() {
        assertThat(validador.validar(ImagenesPrueba.f1()).tipoMime()).isEqualTo("image/jpeg");
    }

    @Test
    @DisplayName("F4: JPEG de exactamente 5 MB se acepta (límite incluido)")
    void limiteExacto() {
        byte[] f4 = ImagenesPrueba.f4();
        assertThat(f4).hasSize(5_242_880);
        assertThat(validador.validar(f4).sha256()).hasSize(64);
    }

    @Test
    @DisplayName("Un byte por encima del límite se rechaza con MSG-07")
    void unBytePorEncima() {
        byte[] grande = Arrays.copyOf(ImagenesPrueba.f4(), 5_242_881);
        assertMsg07(grande);
    }

    @Test
    @DisplayName("F2: texto con extensión .jpg se rechaza con MSG-07")
    void textoDisfrazado() {
        assertMsg07(ImagenesPrueba.f2());
    }

    @Test
    @DisplayName("F3: PNG de 6 MB se rechaza con MSG-07")
    void pngGrande() {
        assertMsg07(ImagenesPrueba.f3());
    }

    @Test
    @DisplayName("Firma JPEG con contenido corrupto se rechaza con MSG-07")
    void firmaSinImagen() {
        byte[] falso = Arrays.copyOf(new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF, 0x00}, 2048);
        assertMsg07(falso);
    }

    private void assertMsg07(byte[] datos) {
        assertThatThrownBy(() -> validador.validar(datos))
                .isInstanceOf(ErrorNegocio.class)
                .extracting(e -> ((ErrorNegocio) e).mensaje())
                .isEqualTo(Mensaje.MSG_07);
    }
}
