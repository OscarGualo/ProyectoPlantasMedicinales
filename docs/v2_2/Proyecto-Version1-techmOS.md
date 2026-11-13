# Informe de la versión 1 de Huertos Medicinales Comunitarios

**Proyecto:** Huertos Medicinales Comunitarios  
**Equipo:** techmOS  
**Integrantes:** Mauricio López · Dhalin Avila · Oscar Gualotuña · Christian Yanchatipan · Jean Espinosa · Gabriel Vinueza  
**Versión documental:** 2.2.1  
**Fecha de edición:** 12 de noviembre de 2026  
**Base de integración:** `develop`; candidato local de corrección  
**Commit base del candidato:** `78d87974152092c2d07125060021d0afdae9dac9`  
**Institución:** Escuela Politécnica Nacional · Ingeniería de Software · Calidad de Software (ISWD652) · GR2SW · Período 2026-B

## 1. Producto y equipo

techmOS presenta el primer incremento de Huertos Medicinales Comunitarios, aplicación educativa web en español. Integra consulta por mapa, representación esquemática 3D, fichas educativas, cultivos por zona, gestión por líder, siembras con fotografía, condición lunar, corrección con historial y reto comunitario.

## 2. Identificación de la entrega

Repositorio: [https://github.com/OscarGualo/ProyectoPlantasMedicinales](https://github.com/OscarGualo/ProyectoPlantasMedicinales). Rama de referencia: develop. Commit auditado: `78d87974152092c2d07125060021d0afdae9dac9`. Fecha documental: 12/11/2026. Presentación académica prevista: 13/11/2026 a las 09:00.

## 3. Entregables documentales

| Documento | Contenido |
|---|---|
| 01 | Visión y alcance del proyecto |
| 02 | Plan y seguimiento de la primera iteración |
| 03 | Historias de usuario de la primera iteración |
| 04 | Casos de prueba y resultados de verificación |
| 05 | Reglas de negocio, glosario, datos y trazabilidad |
| 06 | Arquitectura y diseño del producto |
| 07 | Especificación de requisitos de software |
| 08 | Revisión técnica y control de cambios |
| 09 | Referencia astronómica para la verificación lunar |
| 10 | Resultados de verificación de la primera iteración |
| 11 | Distribución del trabajo y responsabilidades |
| 12 | Manual de instalación, pruebas y demostración |

Los anexos A y B describen el reparto del código integrado y la contribución de Mauricio López. El paquete incluye los Markdown completos y las evidencias de la revisión.

## 4. Construcción y resultados

Esta edición 2.2.1 documenta el candidato de corrección local preparado sobre develop `78d87974152092c2d07125060021d0afdae9dac9`. Incorpora la aclaración del cliente del 12/11/2026: la comunidad escoge al líder y el MSP lo autoriza; la información educativa de las plantas procede del catálogo institucional y el líder la selecciona para su huerto, sin editar contenido medicinal. Los resultados nuevos se registran en el documento 10. La integración en GitHub y su CI todavía no se han ejecutado para este candidato.

La [ejecución de CI](https://github.com/OscarGualo/ProyectoPlantasMedicinales/actions/runs/43140337802) aprobó los cuatro trabajos, incluida la imagen Docker. El JAR normal permitió consultar el producto y rechazó el endpoint de restauración e2e. La auditoría complementaria reprodujo siete incidencias y aprobó nueve combinaciones de acceso a fotos.

## 5. Alcance y estado de aceptación

Las 20 historias conservan sus 64 criterios, con HU-006 y HU-007 actualizadas por el cliente. El catálogo contiene 82 protocolos identificados. Las siete incidencias reproducidas se verificaron como corregidas en el candidato local. La integración remota, su CI, la aceptación del cliente y las comprobaciones de entorno indicadas en el documento 10 permanecen pendientes.

P-027 no cuenta con ejecución en Android físico y contraste con un croquis real aprobado. P-066, P-067 y P-068 carecen de medición formal de rendimiento y recuperación. P-063 conserva cobertura parcial del entorno HTTPS de entrega. No se dispone de evidencia de validación comunitaria de los intervalos lunares ni de alojamiento externo operativo. La construcción de la imagen Docker sí fue satisfactoria en GitHub Actions del último PR integrado.

## 6. Continuidad del proyecto

Almacén, miembros, foro, gestión gubernamental, editor de geometría, ciclo agrícola completo y gamificación ampliada permanecen en el alcance global. Las siguientes entregas se priorizan por valor y capacidad; no se atribuye su implementación al primer incremento.

[Índice de documentación](README.md)
