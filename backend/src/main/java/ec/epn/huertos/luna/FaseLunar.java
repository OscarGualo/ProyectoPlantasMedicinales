package ec.epn.huertos.luna;

/** Las cuatro fases que usan las prácticas lunares de las comunidades (doc 05 §2.1). */
public enum FaseLunar {
    LUNA_OSCURA("luna oscura"),
    LUNA_CRECIENTE("luna creciente"),
    LUNA_LLENA("luna llena"),
    LUNA_MENGUANTE("luna menguante");

    private final String etiqueta;

    FaseLunar(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String etiqueta() {
        return etiqueta;
    }
}
