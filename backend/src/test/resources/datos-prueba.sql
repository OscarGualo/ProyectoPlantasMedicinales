-- Datos de prueba del documento 05, sección 7. Ficticios y exclusivos del entorno de prueba.
-- Se restauran antes de cada caso de prueba. Contraseña de todas las cuentas: clave-prueba.

CREATE EXTENSION IF NOT EXISTS pgcrypto;

TRUNCATE actividad_version, actividad, cultivo, planta, foto, zona, huerto, comunidad, usuario
    RESTART IDENTITY CASCADE;

-- Líderes: L1→H1, L2→H2, L3→H4, L4→H5 (todas las cuentas activas). L5 gestiona H3 (no definido en doc 05).
INSERT INTO usuario (id, usuario, hash_contrasena, nombre, activo) VALUES
    (1, 'l1', crypt('clave-prueba', gen_salt('bf', 4)), 'Líder 1', TRUE),
    (2, 'l2', crypt('clave-prueba', gen_salt('bf', 4)), 'Líder 2', TRUE),
    (3, 'l3', crypt('clave-prueba', gen_salt('bf', 4)), 'Líder 3', TRUE),
    (4, 'l4', crypt('clave-prueba', gen_salt('bf', 4)), 'Líder 4', TRUE),
    (5, 'l5', crypt('clave-prueba', gen_salt('bf', 4)), 'Líder 5', TRUE);

INSERT INTO comunidad (id, nombre, contacto_responsable) VALUES
    (1, 'Comunidad de prueba 1', 'contacto-privado-1@ejemplo.ec'),
    (2, 'Comunidad de prueba 2', 'contacto-privado-2@ejemplo.ec'),
    (3, 'Comunidad de prueba 3', 'contacto-privado-3@ejemplo.ec'),
    (4, 'Comunidad de prueba 4', 'contacto-privado-4@ejemplo.ec'),
    (5, 'Comunidad de prueba 5', 'contacto-privado-5@ejemplo.ec');

-- H1 y H2 publicados con coordenadas. H3 no publicado. H4 no publicado y sin coordenadas.
-- H5 no publicado, con nombre y coordenadas.
INSERT INTO huerto (id, comunidad_id, lider_id, nombre, provincia, latitud, longitud, superficie_m2, publicado) VALUES
    (1, 1, 1, 'Huerto de prueba H1', 'Pichincha', -0.200000, -78.500000, 25, TRUE),
    (2, 2, 2, 'Huerto de prueba H2', 'Imbabura', 0.350000, -78.120000, 40, TRUE),
    (3, 3, 5, 'Huerto de prueba H3', 'Cotopaxi', -0.930000, -78.610000, 15, FALSE),
    (4, 4, 3, 'Huerto de prueba H4', NULL, NULL, NULL, 10, FALSE),
    (5, 5, 4, 'Huerto de prueba H5', 'Azuay', -2.900000, -79.000000, 30, FALSE);

-- Zonas: Z1, Z2 y Z4 (sin cultivos) en H1; Z3 en H2.
INSERT INTO zona (id, huerto_id, codigo, nombre, tipo, geometria) VALUES
    (1, 1, 'Z1', 'Camellón 1', 'CULTIVO', '{"x": 0, "z": 0, "ancho": 4.0, "largo": 1.2, "alto": 0.3}'),
    (2, 1, 'Z2', 'Camellón 2', 'CULTIVO', '{"x": 0, "z": 2, "ancho": 4.0, "largo": 1.2, "alto": 0.3}'),
    (3, 2, 'Z3', 'Terraza 1', 'CULTIVO', '{"x": 0, "z": 0, "ancho": 3.0, "largo": 2.0, "alto": 0.3}'),
    (4, 1, 'Z4', 'Camellón 3', 'CULTIVO', '{"x": 0, "z": 4, "ancho": 4.0, "largo": 1.2, "alto": 0.3}');

-- Plantas (datos sintéticos sin uso medicinal).
INSERT INTO planta (id, huerto_id, nombre_comun, nombre_cientifico, familia, fuente, descripcion, clasificacion) VALUES
    (1, 1, 'Planta de prueba', 'Plantae probatoria', 'Familia de prueba', 'Catálogo de prueba del equipo',
        'Ficha sintética sin uso medicinal', 'FRIA'),
    (2, 2, 'Planta de prueba', 'Plantae probatoria', 'Familia de prueba', 'Catálogo de prueba del equipo',
        'Ficha sintética sin uso medicinal', 'FRIA'),
    (3, 1, 'Planta caliente de prueba', 'Planta calida', 'Familia de prueba', 'Catálogo de prueba del equipo',
        'Ficha sintética sin uso medicinal', 'CALIENTE'),
    (4, 1, 'Planta sin clasificar', 'Planta neutra', 'Familia de prueba', 'Catálogo de prueba del equipo',
        'Ficha sintética sin uso medicinal', NULL);

-- Cultivos: K1 (E1 en Z1) y K2 (E1 en Z2); K4 (E3 en Z2) y K5 (E4 en Z2); K3 en Z3 de H2.
-- Registrados el 30/10 para que un cambio de estado con el reloj fijo (31/10 12:00) muestre una fecha nueva.
INSERT INTO cultivo (id, huerto_id, planta_id, zona_id, estado, registrado_en, actualizado_en) VALUES
    (1, 1, 1, 1, 'SEMBRADO', '2026-10-30 09:00:00-05', '2026-10-30 09:00:00-05'),
    (2, 1, 1, 2, 'SEMBRADO', '2026-10-30 09:00:00-05', '2026-10-30 09:00:00-05'),
    (3, 2, 2, 3, 'SEMBRADO', '2026-10-30 09:00:00-05', '2026-10-30 09:00:00-05'),
    (4, 1, 3, 2, 'SEMBRADO', '2026-10-30 09:00:00-05', '2026-10-30 09:00:00-05'),
    (5, 1, 4, 2, 'SEMBRADO', '2026-10-30 09:00:00-05', '2026-10-30 09:00:00-05');

SELECT setval('usuario_id_seq', 100);
SELECT setval('comunidad_id_seq', 100);
SELECT setval('huerto_id_seq', 100);
SELECT setval('zona_id_seq', 100);
SELECT setval('planta_id_seq', 100);
SELECT setval('cultivo_id_seq', 100);
