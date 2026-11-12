-- Los registros previos se conservan. Solo los envíos nuevos llevan huella.
ALTER TABLE actividad ADD COLUMN huella_envio VARCHAR(64);

ALTER TABLE actividad_version
    ADD COLUMN clave_correccion UUID,
    ADD COLUMN huella_correccion VARCHAR(64),
    ADD CONSTRAINT actividad_version_clave_correccion_unica
        UNIQUE (actividad_id, clave_correccion),
    ADD CONSTRAINT actividad_version_correccion_completa CHECK (
        (clave_correccion IS NULL AND huella_correccion IS NULL)
        OR (
            numero > 1
            AND clave_correccion IS NOT NULL
            AND huella_correccion IS NOT NULL
        )
    );