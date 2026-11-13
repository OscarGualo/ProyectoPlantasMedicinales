# Documentación de Huertos Medicinales Comunitarios

**Proyecto:** Huertos Medicinales Comunitarios  
**Equipo:** techmOS  
**Integrantes:** Mauricio López · Dhalin Avila · Oscar Gualotuña · Christian Yanchatipan · Jean Espinosa · Gabriel Vinueza  
**Versión documental:** 2.2.1  
**Fecha de edición:** 12 de noviembre de 2026  
**Base de integración:** `develop`; candidato local de corrección  
**Commit base del candidato:** `78d87974152092c2d07125060021d0afdae9dac9`  
**Institución:** Escuela Politécnica Nacional · Ingeniería de Software · Calidad de Software (ISWD652) · GR2SW · Período 2026-B

Esta edición 2.2.1 documenta el candidato de corrección local preparado sobre develop `78d87974152092c2d07125060021d0afdae9dac9`. Incorpora la aclaración del cliente del 12/11/2026: la comunidad escoge al líder y el MSP lo autoriza; la información educativa de las plantas procede del catálogo institucional y el líder la selecciona para su huerto, sin editar contenido medicinal. Los resultados nuevos se registran en el documento 10. La integración en GitHub y su CI todavía no se han ejecutado para este candidato.

## Índice

- [Visión y alcance del proyecto](01_Vision_y_alcance_techmOS.md)
- [Plan y seguimiento de la primera iteración](02_Plan_de_iteracion_techmOS.md)
- [Historias de usuario de la primera iteración](03_Historias_de_usuario_techmOS.md)
- [Casos de prueba y resultados de verificación](04_Casos_de_prueba_y_resultados_techmOS.md)
- [Reglas de negocio, glosario, datos y trazabilidad](05_Reglas_datos_y_trazabilidad_techmOS.md)
- [Arquitectura y diseño del producto](06_Arquitectura_y_diseno_techmOS.md)
- [Especificación de requisitos de software](07_ERS_consolidada_techmOS.md)
- [Revisión técnica y control de cambios](08_Revision_tecnica_y_cambios_techmOS.md)
- [Referencia astronómica para la verificación lunar](09_Referencia_lunar_techmOS.md)
- [Resultados de verificación de la primera iteración](10_Resultados_de_verificacion_techmOS.md)
- [Distribución del trabajo y responsabilidades](11_Distribucion_del_trabajo_techmOS.md)
- [Manual de instalación, pruebas y demostración](12_Manual_de_ejecucion_techmOS.md)
- [Anexo A. Distribución técnica del código integrado](Anexo_A_Reparto_tecnico_techmOS.md)
- [Anexo B. Registro técnico de la contribución de Mauricio López](Anexo_B_Guia_Mauricio_techmOS.md)
- [Informe de la versión 1 de Huertos Medicinales Comunitarios](Proyecto-Version1-techmOS.md)

## Identidad y lectura del paquete

Los documentos corresponden a una misma edición y revisión de código. Los nombres del equipo están completos y no existen campos de identidad por rellenar. Los estados de ejecución y cobertura distinguen las comprobaciones locales del candidato, los antecedentes del commit base y las validaciones de entorno todavía pendientes.

La línea base contiene 20 historias, 64 criterios y 86 requisitos funcionales, de los cuales 40 se relacionan con I1. El catálogo de pruebas conserva P-001 a P-077 y agrega P-078 a P-082 para la información institucional. P-013 a P-018 se actualizan por el cambio de contrato del cliente. Las 94 pruebas Java y los 21 recorridos automatizados no equivalen a 82 protocolos manuales completos.

## Alcance del paquete

Esta entrega contiene documentación, evidencia y un candidato de corrección local en el paquete que la acompaña. Las siete incidencias se verifican en la copia local; la integración remota del candidato está pendiente. Las instrucciones actuales están en la guía PASO_A_PASO y el catálogo institucional se explica en el documento 13.

Los diagramas de arquitectura están incluidos como bloques Mermaid, acompañados por texto. Los enlaces entre documentos y evidencias son relativos para conservar su funcionamiento al extraer el ZIP.

- [Autoridad y procedencia de las fichas institucionales](13_Autoridad_y_catalogo_MSP_techmOS.md).

## Aclaración de la edición 2.2.1

Las siembras se planifican tomando como referencia el calendario lunar. Se precisa esa relación en visión y alcance, plan, HU-019, glosario, arquitectura y RF-084. El calendario relaciona fechas, fases y actividades; el ciclo agrícola describe etapas de cultivo. La edición solo actualiza documentación: conserva las 20 HU, 64 criterios, 86 RF y 82 protocolos, y la evidencia de 94 pruebas Java y 21 recorridos del candidato de código ya comprobado. No añade agenda persistente ni modifica las condiciones de la insignia.
