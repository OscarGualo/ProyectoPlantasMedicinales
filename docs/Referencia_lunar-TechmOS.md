# Referencia astronómica para la verificación lunar

<div align="justify" style="text-align: justify;">

**Proyecto:** Huertos Medicinales Comunitarios  
**Equipo:** techmOS  
**Integrantes:** Mauricio López · Dhalin Avila · Oscar Gualotuña · Christian Yanchatipan · Jean Espinosa · Gabriel Vinueza  
**Versión documental:** 2.2.1  
**Fecha de edición:** 12 de noviembre de 2026  
**Base de integración:** `develop`; candidato local de corrección  
**Commit base del candidato:** `78d87974152092c2d07125060021d0afdae9dac9`  
**Institución:** Escuela Politécnica Nacional · Ingeniería de Software · Calidad de Software (ISWD652) · GR2SW · Período 2026-B

Fuente de referencia para P-050, P-055 y P-062. Los instantes se transcriben en Tiempo Universal con precisión de minuto. Se comparan como instantes absolutos. La zona de Ecuador continental se usa para mostrar el calendario y convertir las fechas de los ejemplos de H1. [U.S. Naval Observatory, fases principales de la Luna en 2026](https://aa.usno.navy.mil/calculated/moon/phases?year=2026).

| Fase astronómica | Instante de referencia UT | Ecuador continental | Uso |
|---|---|---|---|
| Luna llena | 2026-09-26 16:49 | 26/09 11:49 | Evento exterior para octubre |
| Cuarto menguante | 2026-10-03 13:25 | 03/10 08:25 | Instante cercano al caso no conforme P-055 |
| Luna nueva | 2026-10-10 15:50 | 10/10 10:50 | Evento vecino para octubre |
| Cuarto creciente | 2026-10-18 16:12 | 18/10 11:12 | Instante cercano al caso conforme P-055 |
| Luna llena | 2026-10-26 04:12 | 25/10 23:12 | Evento exterior para noviembre |
| Cuarto menguante | 2026-11-01 20:28 | 01/11 15:28 | P-050 |
| Luna nueva | 2026-11-09 07:02 | 09/11 02:02 | P-050 |
| Cuarto creciente | 2026-11-17 11:48 | 17/11 06:48 | P-050 |
| Luna llena | 2026-11-24 14:53 | 24/11 09:53 | P-050 |
| Cuarto menguante | 2026-12-01 06:08 | 01/12 01:08 | Evento exterior para noviembre |
| Luna nueva | 2026-12-09 00:52 | 08/12 19:52 | Evento exterior para noviembre |

**Tabla 1.** Fases astronómicas de referencia: instante en UT, hora de Ecuador continental y uso en las pruebas.

## P-050 · Calendario real

Con el reloj de pruebas del 31/10/2026 a las 12:00 de Ecuador continental, el rango predeterminado es del 31/10 al 29/11, ambos inclusive. Debe contener los cuatro eventos de noviembre indicados para P-050. Comparar por tipo de fase e instante, con una diferencia absoluta máxima de una hora por evento y sin duplicados. Para la clasificación diaria, derivar la fase del instante 12:00 de cada fecha continental según RN-09. Si el mediodía cae cerca de un punto medio, contrastar la regla de frontera por separado con datos de precisión conocida. Registrar la diferencia observada, la versión de la biblioteca y el resultado. La comparación automatizada con estos cuatro eventos se ejecutó satisfactoriamente el 10/11/2026.

## P-055 · Recorrido con cálculo real

Preparar H1 publicado en `America/Guayaquil`, sin siembras iniciales y sin otra siembra elegible en su comunidad. Usar el cálculo real de fases y el reloj del 31/10. Registrar el hecho del **18/10/2026 a las 08:00** (13:00 UT). Está dentro del intervalo creciente bajo RN-09. Con RN-06 y foto válida, se espera condición conforme e insignia. Corregir el hecho al **03/10/2026 a las 08:00** (13:00 UT). Está dentro del intervalo menguante. Se espera no conforme, historial de dos versiones y desaparición de la insignia. Los instantes están lejos de los puntos medios y fuera del tramo de CAL-T del perfil `e2e` existente. El recorrido Playwright original se conserva como prueba controlada con sus propios valores.

## P-062 · Cobertura y límite exacto

Para una prueba unitaria determinista, usar eventos fijos con los instantes de la tabla y fijar la precisión de comparación. El punto medio entre 01/11 20:28 UT y 09/11 07:02 UT es 05/11 13:45 UT. Un segundo antes debe corresponder al intervalo menguante. El punto medio y un segundo después deben corresponder al intervalo oscuro. Con la biblioteca real, verificar cobertura alrededor de varias transiciones y comparar eventos con la tolerancia de P-050. Las fronteras exactas de la biblioteca se comprueban a partir de sus propios instantes calculados.

La fuente documenta los eventos astronómicos. La división del ciclo por puntos medios y su relación con prácticas de siembra pertenecen a las reglas del proyecto y quedan sujetas a validación comunitaria.
## Relación con la revisión integrada

La comparación de efemérides de la suite volvió a finalizar aprobada en la revisión de `78d8797`. La referencia comprueba eventos astronómicos; la convención de intervalos por puntos medios y las recomendaciones agrícolas conservan su naturaleza de regla del piloto. No hay evidencia de validación comunitaria de esa convención.

[Índice de documentación](README.md)

</div>
