-- Esquema de la versión 1 (doc 06, §7 y figura 4).
-- Los instantes se guardan en UTC (timestamptz) y se interpretan con la zona horaria del huerto (AD-10).

CREATE TABLE usuario (
    id              BIGSERIAL PRIMARY KEY,
    usuario         VARCHAR(60)  NOT NULL UNIQUE,
    hash_contrasena VARCHAR(100) NOT NULL,
    nombre          VARCHAR(120),
    activo          BOOLEAN      NOT NULL DEFAULT TRUE
);

-- Comunidad separada del huerto para no fijar cardinalidades pendientes (D-03).
CREATE TABLE comunidad (
    id                   BIGSERIAL PRIMARY KEY,
    nombre               VARCHAR(150) NOT NULL,
    contacto_responsable VARCHAR(200)          -- privado (RN-02)
);

CREATE TABLE huerto (
    id            BIGSERIAL PRIMARY KEY,
    comunidad_id  BIGINT       NOT NULL REFERENCES comunidad (id),
    lider_id      BIGINT       NOT NULL REFERENCES usuario (id),
    nombre        VARCHAR(150),
    descripcion   VARCHAR(2000),
    provincia     VARCHAR(60),
    parroquia     VARCHAR(120),
    direccion     VARCHAR(250),
    latitud       NUMERIC(9, 6),
    longitud      NUMERIC(9, 6),
    zona_horaria  VARCHAR(40)  NOT NULL DEFAULT 'America/Guayaquil',
    superficie_m2 NUMERIC(10, 2),
    diseno        VARCHAR(80),
    tipo_cerca    VARCHAR(80),
    tipo_suelo    VARCHAR(80),
    fuente_agua   VARCHAR(80),
    tecnica_riego VARCHAR(80),
    tipo_abono    VARCHAR(80),
    publicado     BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT huerto_latitud_rango CHECK (latitud BETWEEN -90 AND 90),
    CONSTRAINT huerto_longitud_rango CHECK (longitud BETWEEN -180 AND 180),
    CONSTRAINT huerto_superficie_no_negativa CHECK (superficie_m2 >= 0),
    CONSTRAINT huerto_zona_horaria CHECK (zona_horaria IN ('America/Guayaquil', 'Pacific/Galapagos')),
    CONSTRAINT huerto_publicado_con_ubicacion
        CHECK (NOT publicado OR (nombre IS NOT NULL AND latitud IS NOT NULL AND longitud IS NOT NULL))
);

CREATE TABLE zona (
    id        BIGSERIAL PRIMARY KEY,
    huerto_id BIGINT      NOT NULL REFERENCES huerto (id),
    codigo    VARCHAR(20) NOT NULL,
    nombre    VARCHAR(120),
    tipo      VARCHAR(20) NOT NULL,
    geometria JSONB       NOT NULL,      -- {x, z, ancho, largo, alto} en metros
    CONSTRAINT zona_tipo CHECK (tipo IN ('CULTIVO', 'CAMINO', 'CERCA', 'BODEGA')),
    CONSTRAINT zona_codigo_unico UNIQUE (huerto_id, codigo),
    CONSTRAINT zona_id_huerto UNIQUE (id, huerto_id)
);

CREATE TABLE foto (
    id           BIGSERIAL PRIMARY KEY,
    contenido    BYTEA       NOT NULL,
    tipo_mime    VARCHAR(20) NOT NULL,
    tamano_bytes INTEGER     NOT NULL,
    sha256       VARCHAR(64) NOT NULL,
    creada_en    TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT foto_tipo CHECK (tipo_mime IN ('image/jpeg', 'image/png')),
    CONSTRAINT foto_tamano CHECK (tamano_bytes > 0 AND tamano_bytes <= 5242880)
);

CREATE TABLE planta (
    id                BIGSERIAL PRIMARY KEY,
    huerto_id         BIGINT       NOT NULL REFERENCES huerto (id),
    nombre_comun      VARCHAR(120) NOT NULL,
    nombre_cientifico VARCHAR(150) NOT NULL,
    familia           VARCHAR(120) NOT NULL,
    fuente            VARCHAR(300) NOT NULL,
    otros_nombres     VARCHAR(300),
    descripcion       VARCHAR(2000),
    altitud           VARCHAR(120),
    distribucion      VARCHAR(500),
    ciclo             VARCHAR(10),
    clasificacion     VARCHAR(10),
    usos              VARCHAR(2000),
    foto_id           BIGINT REFERENCES foto (id),
    CONSTRAINT planta_clasificacion CHECK (clasificacion IN ('CALIENTE', 'FRIA')),
    CONSTRAINT planta_ciclo CHECK (ciclo IN ('CORTO', 'LARGO')),
    CONSTRAINT planta_id_huerto UNIQUE (id, huerto_id)
);

-- Un cultivo es una planta en una zona del MISMO huerto (RN-03): las FK compuestas lo garantizan.
CREATE TABLE cultivo (
    id             BIGSERIAL PRIMARY KEY,
    huerto_id      BIGINT      NOT NULL REFERENCES huerto (id),
    planta_id      BIGINT      NOT NULL,
    zona_id        BIGINT      NOT NULL,
    estado         VARCHAR(20) NOT NULL DEFAULT 'SEMBRADO',
    registrado_en  TIMESTAMPTZ NOT NULL,
    actualizado_en TIMESTAMPTZ NOT NULL,
    CONSTRAINT cultivo_estado CHECK (estado IN ('SEMBRADO', 'EN_CRECIMIENTO', 'COSECHADO', 'PERDIDO')),
    CONSTRAINT cultivo_planta_mismo_huerto FOREIGN KEY (planta_id, huerto_id) REFERENCES planta (id, huerto_id),
    CONSTRAINT cultivo_zona_mismo_huerto FOREIGN KEY (zona_id, huerto_id) REFERENCES zona (id, huerto_id)
);

-- Actividad: identidad estable; sus datos viven en versiones de solo inserción (RN-07, AD-08).
CREATE TABLE actividad (
    id            BIGSERIAL PRIMARY KEY,
    cultivo_id    BIGINT      NOT NULL REFERENCES cultivo (id),
    tipo          VARCHAR(20) NOT NULL DEFAULT 'SIEMBRA',
    clave_envio   UUID        NOT NULL,
    autor_id      BIGINT      NOT NULL REFERENCES usuario (id),
    registrada_en TIMESTAMPTZ NOT NULL,
    CONSTRAINT actividad_tipo CHECK (tipo IN ('SIEMBRA')),
    CONSTRAINT actividad_clave_envio_unica UNIQUE (clave_envio)
);

CREATE TABLE actividad_version (
    id           BIGSERIAL PRIMARY KEY,
    actividad_id BIGINT       NOT NULL REFERENCES actividad (id),
    numero       INTEGER      NOT NULL,
    fecha_hecho  TIMESTAMPTZ  NOT NULL,
    tecnica      VARCHAR(20)  NOT NULL,
    descripcion  VARCHAR(2000),
    foto_id      BIGINT       NOT NULL REFERENCES foto (id),
    motivo       VARCHAR(500),
    autor_id     BIGINT       NOT NULL REFERENCES usuario (id),
    creada_en    TIMESTAMPTZ  NOT NULL,
    CONSTRAINT actividad_version_numero_unico UNIQUE (actividad_id, numero),
    CONSTRAINT actividad_version_tecnica CHECK (tecnica IN ('SEMILLAS', 'ESTACAS', 'ESQUEJES', 'PLANTULAS')),
    CONSTRAINT actividad_version_motivo CHECK (numero = 1 OR (motivo IS NOT NULL AND length(trim(motivo)) > 0))
);

-- Las versiones nunca se actualizan ni se borran: la base es la última barrera.
CREATE FUNCTION impedir_cambio_version() RETURNS trigger AS
$$
BEGIN
    RAISE EXCEPTION 'Las versiones de una actividad son de solo inserción';
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER actividad_version_solo_insercion
    BEFORE UPDATE OR DELETE ON actividad_version
    FOR EACH ROW EXECUTE FUNCTION impedir_cambio_version();

CREATE INDEX huerto_lider_idx ON huerto (lider_id);
CREATE INDEX huerto_publicado_idx ON huerto (publicado);
CREATE INDEX planta_huerto_idx ON planta (huerto_id);
CREATE INDEX cultivo_huerto_idx ON cultivo (huerto_id);
CREATE INDEX cultivo_zona_idx ON cultivo (zona_id);
CREATE INDEX actividad_cultivo_idx ON actividad (cultivo_id);
