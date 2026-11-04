-- Datos de demostración (doc 02 §7, doc 06 §7). Ficticios e identificados como tales: no son huertos reales
-- ni contenido medicinal validado. La contraseña de los líderes llega por la variable de entorno
-- DEMO_PASSWORD (placeholder de Flyway) y nunca se guarda en el repositorio; si falta, nadie puede ingresar.

CREATE EXTENSION IF NOT EXISTS pgcrypto;

INSERT INTO usuario (usuario, hash_contrasena, nombre, activo) VALUES
    ('lider.demo1', crypt('${demo_password}', gen_salt('bf', 10)), 'Líder de demostración 1', TRUE),
    ('lider.demo2', crypt('${demo_password}', gen_salt('bf', 10)), 'Líder de demostración 2', TRUE),
    ('lider.demo3', crypt('${demo_password}', gen_salt('bf', 10)), 'Líder de demostración 3', TRUE);

INSERT INTO comunidad (nombre, contacto_responsable) VALUES
    ('Comunidad demostrativa Sumak Kawsay', 'contacto-demo-1@ejemplo.ec'),
    ('Familia demostrativa Hampi', 'contacto-demo-2@ejemplo.ec'),
    ('Barrio demostrativo Jambi Muyu', 'contacto-demo-3@ejemplo.ec');

INSERT INTO huerto (comunidad_id, lider_id, nombre, descripcion, provincia, parroquia, direccion, latitud,
                    longitud, superficie_m2, diseno, tipo_cerca, tipo_suelo, fuente_agua, tecnica_riego,
                    tipo_abono, publicado)
SELECT c.id, u.id, d.nombre, d.descripcion, d.provincia, d.parroquia, d.direccion, d.latitud, d.longitud,
       d.superficie, d.diseno, d.cerca, d.suelo, d.agua, d.riego, d.abono, TRUE
  FROM (VALUES
    ('Comunidad demostrativa Sumak Kawsay', 'lider.demo1', 'Huerto demostrativo Sumak Kawsay',
     'Huerto de demostración preparado por el equipo a partir de un croquis provisional.',
     'Pichincha', 'Tumbaco', 'Dirección de demostración', -0.210100, -78.402200, 80.0,
     'en tierra: camellones', 'cerca viva', 'margoso', 'canal', 'regadera', 'compost'),
    ('Familia demostrativa Hampi', 'lider.demo2', 'Huerto familiar demostrativo Hampi',
     'Huerto familiar de demostración en maceteros y llantas.',
     'Imbabura', 'Otavalo', 'Dirección de demostración', 0.234300, -78.261100, 12.0,
     'en llantas', 'malla', 'humífero', 'red pública', 'regadera', 'humus de lombriz'),
    ('Barrio demostrativo Jambi Muyu', 'lider.demo3', 'Huerto barrial demostrativo Jambi Muyu',
     'Huerto barrial de demostración en terrazas.',
     'Azuay', 'Cuenca', 'Dirección de demostración', -2.900600, -79.004500, 45.0,
     'en tierra: terrazas', 'madera', 'arcilloso', 'lluvia', 'goteo', 'bocashi')
  ) AS d(comunidad, lider, nombre, descripcion, provincia, parroquia, direccion, latitud, longitud, superficie,
         diseno, cerca, suelo, agua, riego, abono)
  JOIN comunidad c ON c.nombre = d.comunidad
  JOIN usuario u ON u.usuario = d.lider;

-- Croquis PROVISIONAL del huerto Sumak Kawsay (sustituye a CR1 hasta que el cliente apruebe el real).
-- Geometría en metros: (x, z) es el centro de la zona sobre el terreno; ancho en x, largo en z.
INSERT INTO zona (huerto_id, codigo, nombre, tipo, geometria)
SELECT h.id, z.codigo, z.nombre, z.tipo, z.geometria::jsonb
  FROM huerto h,
       (VALUES
           ('Z1', 'Camellón 1', 'CULTIVO', '{"x": -2.5, "z": -2.5, "ancho": 4.0, "largo": 1.2, "alto": 0.3}'),
           ('Z2', 'Camellón 2', 'CULTIVO', '{"x": -2.5, "z": -0.5, "ancho": 4.0, "largo": 1.2, "alto": 0.3}'),
           ('Z3', 'Camellón 3', 'CULTIVO', '{"x": -2.5, "z": 1.5, "ancho": 4.0, "largo": 1.2, "alto": 0.3}'),
           ('Z4', 'Terraza de aromáticas', 'CULTIVO', '{"x": 2.5, "z": -1.5, "ancho": 3.0, "largo": 3.0, "alto": 0.5}'),
           ('C1', 'Camino central', 'CAMINO', '{"x": 0.0, "z": 0.0, "ancho": 1.0, "largo": 7.0, "alto": 0.05}'),
           ('CE1', 'Cerca norte', 'CERCA', '{"x": 0.0, "z": -4.0, "ancho": 10.0, "largo": 0.15, "alto": 1.0}'),
           ('CE2', 'Cerca sur', 'CERCA', '{"x": 0.0, "z": 4.0, "ancho": 10.0, "largo": 0.15, "alto": 1.0}'),
           ('CE3', 'Cerca oeste', 'CERCA', '{"x": -5.0, "z": 0.0, "ancho": 0.15, "largo": 8.0, "alto": 1.0}'),
           ('CE4', 'Cerca este', 'CERCA', '{"x": 5.0, "z": 0.0, "ancho": 0.15, "largo": 8.0, "alto": 1.0}'),
           ('B1', 'Bodega', 'BODEGA', '{"x": 3.0, "z": 2.5, "ancho": 2.0, "largo": 2.0, "alto": 2.0}')
       ) AS z(codigo, nombre, tipo, geometria)
 WHERE h.nombre = 'Huerto demostrativo Sumak Kawsay';

INSERT INTO zona (huerto_id, codigo, nombre, tipo, geometria)
SELECT h.id, 'Z1', 'Zona de llantas', 'CULTIVO', '{"x": 0, "z": 0, "ancho": 3.0, "largo": 2.0, "alto": 0.4}'::jsonb
  FROM huerto h WHERE h.nombre IN ('Huerto familiar demostrativo Hampi', 'Huerto barrial demostrativo Jambi Muyu');

-- Fichas de demostración: nombres comunes reales, contenido sin validar y sin usos ni dosis.
INSERT INTO planta (huerto_id, nombre_comun, nombre_cientifico, familia, fuente, descripcion, clasificacion)
SELECT h.id, p.comun, p.cientifico, p.familia, 'Datos de demostración del equipo (sin validar)',
       'Ficha de demostración: el contenido y la clasificación deben ser validados por el líder del huerto.',
       p.clasificacion
  FROM huerto h,
       (VALUES
           ('Manzanilla', 'Matricaria chamomilla', 'Asteraceae', 'CALIENTE'),
           ('Toronjil', 'Melissa officinalis', 'Lamiaceae', 'FRIA'),
           ('Ruda', 'Ruta graveolens', 'Rutaceae', 'CALIENTE'),
           ('Hierba luisa', 'Cymbopogon citratus', 'Poaceae', NULL)
       ) AS p(comun, cientifico, familia, clasificacion)
 WHERE h.nombre = 'Huerto demostrativo Sumak Kawsay';

INSERT INTO cultivo (huerto_id, planta_id, zona_id, estado, registrado_en, actualizado_en)
SELECT h.id, p.id, z.id, c.estado, now(), now()
  FROM huerto h
  JOIN (VALUES ('Manzanilla', 'Z1', 'EN_CRECIMIENTO'),
               ('Toronjil', 'Z2', 'SEMBRADO'),
               ('Ruda', 'Z2', 'EN_CRECIMIENTO'),
               ('Hierba luisa', 'Z4', 'SEMBRADO')) AS c(planta, zona, estado) ON TRUE
  JOIN planta p ON p.huerto_id = h.id AND p.nombre_comun = c.planta
  JOIN zona z ON z.huerto_id = h.id AND z.codigo = c.zona
 WHERE h.nombre = 'Huerto demostrativo Sumak Kawsay';
