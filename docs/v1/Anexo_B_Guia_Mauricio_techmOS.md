# Anexo B. Registro técnico de la contribución de Mauricio López

**Proyecto:** Huertos Medicinales Comunitarios  
**Equipo:** techmOS  
**Integrantes:** Mauricio López · Dhalin Avila · Oscar Gualotuña · Christian Yanchatipan · Jean Espinosa · Gabriel Vinueza  
**Versión documental:** 2.2.1  
**Fecha de edición:** 12 de noviembre de 2026  
**Base de integración:** `develop`; candidato local de corrección  
**Commit base del candidato:** `78d87974152092c2d07125060021d0afdae9dac9`  
**Institución:** Escuela Politécnica Nacional · Ingeniería de Software · Calidad de Software (ISWD652) · GR2SW · Período 2026-B

## 1. Aporte integrado

Mauricio López integró la base de datos V3, los contratos de error y el entorno de CI. Su commit de contribución es f7555a2221abf73915ca1d4bb3049e2aaaad5f98. La revisión conjunta de este paquete es 78d87974152092c2d07125060021d0afdae9dac9.

## 2. Archivos del aporte

- .github/workflows/ci.yml.
- README.md y SIGUIENTES_PASOS.md.
- backend/mvnw, con modo Git 100755.
- backend/src/main/java/ec/epn/huertos/comun/Mensaje.java.
- backend/src/main/java/ec/epn/huertos/comun/ManejadorErrores.java.
- backend/src/main/resources/db/migration/V3__integridad_envios.sql.
- backend/src/test/java/ec/epn/huertos/ContenedoresPrueba.java.
- backend/src/test/java/ec/epn/huertos/comun/MensajeTest.java.
- backend/src/test/resources/application-test.yml.

## 3. Resultado registrado

La comprobación individual del aporte ejecutó las tres pruebas de MensajeTest. La migración de una base V2 a V3 conservó actividad, versión y fotografía; los cinco indicadores de conservación fueron verdaderos y las dos restricciones nuevas estaban presentes. La revisión integrada posterior ejecutó las 89 pruebas Java y los 14 recorridos de navegador de la suite.

## 4. Comprobación reproducible

Los comandos se ejecutan desde la raíz del repositorio.

```powershell
git rev-parse HEAD
git status --short
git ls-files --stage backend/mvnw

Push-Location backend
.\mvnw.cmd '-Dtest=MensajeTest' test
.\mvnw.cmd verify
Pop-Location
```

El modo esperado del wrapper es 100755. La validación de migración sobre datos existentes requiere una base de ensayo independiente; la ejecución de la aplicación de demostración no acredita por sí sola una migración desde una base anterior.

## 5. Alcance del cierre

INC-06 se resuelve mediante el contrato de selección institucional: los nombres libres se rechazan con MSG-28. El workflow conserva sus cuatro trabajos y añade push a develop. La ejecución remota de este cambio queda pendiente de integrar el candidato.

## 6. Evidencia de integración

La evidencia común está en el documento 10 y en el directorio evidencias. Los comandos de instalación, aislamiento de la demostración y detención están en el documento 12. Este anexo describe un aporte ya integrado y no exige volver a aplicar el parche histórico.

[Índice de documentación](README.md)
