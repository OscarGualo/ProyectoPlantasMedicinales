package ec.epn.huertos.fotos;

/** Imagen que superó la validación de RN-05: contenido real JPEG o PNG de hasta 5 MB. */
public record ImagenValidada(byte[] contenido, String tipoMime, String sha256) {
}
