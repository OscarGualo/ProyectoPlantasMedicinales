-- Catálogo institucional de lectura. No se incorporan dosis ni indicaciones terapéuticas.
CREATE TABLE catalogo_planta_msp (
 codigo VARCHAR(40) PRIMARY KEY, nombre_comun VARCHAR(120) NOT NULL,
 nombre_cientifico VARCHAR(150), familia VARCHAR(120), fuente VARCHAR(300) NOT NULL,
 descripcion VARCHAR(2000), clasificacion VARCHAR(10) CHECK (clasificacion IN ('CALIENTE','FRIA')),
 usos VARCHAR(2000), version VARCHAR(60) NOT NULL, activo BOOLEAN NOT NULL DEFAULT TRUE
);
INSERT INTO catalogo_planta_msp
 (codigo,nombre_comun,nombre_cientifico,familia,fuente,descripcion,clasificacion,version)
VALUES
 ('MSP-MANZANILLA','Manzanilla',NULL,NULL,
  'MSP (2017), Lineamientos de huertos medicinales, anexos 3 y 5; páginas impresas 66 y 74.',
  'Incluida en el anexo 3. El anexo 5 la clasifica como planta caliente según la medicina andina.',
  'CALIENTE','MSP-2017-I1-1'),
 ('MSP-TORONJIL','Toronjil',NULL,NULL,
  'MSP (2017), Lineamientos de huertos medicinales, anexos 3 y 5; páginas impresas 66 y 74.',
  'Incluida en el anexo 3. El anexo 5 la clasifica como planta caliente según la medicina andina.',
  'CALIENTE','MSP-2017-I1-1'),
 ('MSP-RUDA','Ruda',NULL,NULL,
  'MSP (2017), Lineamientos de huertos medicinales, anexo 5; página impresa 74.',
  'Incluida en el listado de plantas calientes del anexo 5. La clasificación se atribuye a esa fuente.',
  'CALIENTE','MSP-2017-I1-1'),
 ('MSP-HIERBA-LUISA','Hierba luisa','Cymbopogon citratus','Poaceae',
  'MSP (2017), Lineamientos de huertos medicinales, anexo 2.1; página impresa 63.',
  'Incluida en el anexo 2.1. Esta ficha incorpora su identificación y referencia documental.',
  NULL,'MSP-2017-I1-1');
ALTER TABLE planta ADD COLUMN catalogo_codigo VARCHAR(40) REFERENCES catalogo_planta_msp(codigo);
ALTER TABLE planta ALTER COLUMN nombre_cientifico DROP NOT NULL;
ALTER TABLE planta ALTER COLUMN familia DROP NOT NULL;
-- Conserva los identificadores de plantas, cultivos, siembras, fotos e historial.
UPDATE planta p SET catalogo_codigo=c.codigo,nombre_comun=c.nombre_comun,
 nombre_cientifico=c.nombre_cientifico,familia=c.familia,fuente=c.fuente,
 descripcion=c.descripcion,clasificacion=c.clasificacion,usos=c.usos,
 otros_nombres=NULL,altitud=NULL,distribucion=NULL,ciclo=NULL
FROM catalogo_planta_msp c WHERE lower(trim(p.nombre_comun))=lower(c.nombre_comun);
CREATE INDEX planta_catalogo_idx ON planta(catalogo_codigo);
