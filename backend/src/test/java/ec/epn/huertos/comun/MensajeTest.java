package ec.epn.huertos.comun;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class MensajeTest {

    @Test
    void elCatalogoTieneLos29MensajesConCodigoDelDocumento() {
        assertThat(Mensaje.values()).hasSize(29);
        assertThat(Arrays.stream(Mensaje.values()).map(Mensaje::codigo))
                .startsWith("MSG-01", "MSG-02")
                .endsWith("MSG-23", "MSG-24", "MSG-25", "MSG-26", "MSG-27", "MSG-28", "MSG-29");
    }

    @Test
    void reemplazaLosMarcadoresEnOrden() {
        assertThat(Mensaje.MSG_04.texto("Nombre"))
                .isEqualTo("Completa el campo obligatorio: Nombre.");

        assertThat(Mensaje.MSG_20.texto("31/10/2025", "31/10/2027"))
                .isEqualTo(
                        "El calendario lunar está disponible desde 31/10/2025 hasta 31/10/2027."
                );

        assertThat(Mensaje.MSG_15.texto("luna llena"))
                .isEqualTo(
                        "Conforme con la condición lunar: la siembra se realizó en luna llena."
                );
    }

    @Test
    void sinArgumentosConservaElTextoLiteral() {
        assertThat(Mensaje.MSG_01.texto())
                .isEqualTo("Usuario o contraseña incorrectos.");
    }
}