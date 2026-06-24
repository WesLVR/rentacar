# Rent a Car - Arquitectura de Microservicios

Sistema de gestión de arriendo de vehículos basado en arquitectura de microservicios con Spring Boot, comunicación REST mediante OpenFeign, descubrimiento de servicios con Eureka y enrutamiento centralizado mediante API Gateway.

## Contexto del proyecto

El sistema permite gestionar el arriendo de vehículos de una empresa de Rent a Car, cubriendo el ciclo completo: registro de vehículos y clientes, reservas con cálculo automático de costo, pagos, disponibilidad, mantenimientos, entregas/devoluciones y generación de reportes.

## Integrantes

- Gonzalo Gallardo
- Bastián González
- Lucas Valderrama

## Asignatura

DSY1103 - Desarrollo FullStack 1 - Duoc UC

## Arquitectura

El sistema está compuesto por 10 microservicios de negocio, 1 servidor de descubrimiento (Eureka) y 1 API Gateway.

### Microservicios

| Microservicio | Puerto | Base de datos | Descripción |
|---|---|---|---|
| vehiculoMS | 8081 | vehiculos_db | Vehículos y categorías de la flota |
| clienteMS | 8082 | clientes_db | Clientes y tipos de licencia |
| empleadoMS | 8083 | empleados_db | Empleados y cargos |
| categoriaVehiculoMS | 8084 | categorias_vehiculo_db | Categorías de vehículo y tarifas |
| reservaMS | 8085 | reservas_db | Reservas con cálculo automático de costo |
| pagoMS | 8086 | pagos_db | Pagos de reservas |
| disponibilidadMS | 8087 | disponibilidad_db | Disponibilidad de vehículos |
| mantenimientoMS | 8088 | mantenimiento_db | Mantenciones de vehículos |
| entregaMS | 8089 | entrega_db | Entregas y devoluciones |
| reporteMS | 8090 | reportes_db | Reportes del sistema |

### Infraestructura

| Componente | Puerto | Descripción |
|---|---|---|
| eureka-server | 8761 | Servidor de descubrimiento de servicios |
| gateway | 8080 | API Gateway - punto único de entrada |

## Rutas principales del Gateway

Todas las peticiones pueden hacerse a través del Gateway en el puerto 8080:

```
http://localhost:8080/api/v1/vehiculos
http://localhost:8080/api/v1/clientes
http://localhost:8080/api/v1/empleados
http://localhost:8080/api/v1/categorias
http://localhost:8080/api/v1/reservas
http://localhost:8080/api/v1/pagos
http://localhost:8080/api/v1/disponibilidad
http://localhost:8080/api/v1/mantenimientos
http://localhost:8080/api/v1/entregas
http://localhost:8080/api/v1/reportes
```

## Documentación Swagger

Cada microservicio expone su documentación OpenAPI en:

```
http://localhost:8081/swagger-ui.html   (vehiculoMS)
http://localhost:8082/swagger-ui.html   (clienteMS)
http://localhost:8083/swagger-ui.html   (empleadoMS)
http://localhost:8084/swagger-ui.html   (categoriaVehiculoMS)
http://localhost:8085/swagger-ui.html   (reservaMS)
http://localhost:8086/swagger-ui.html   (pagoMS)
http://localhost:8087/swagger-ui.html   (disponibilidadMS)
http://localhost:8088/swagger-ui.html   (mantenimientoMS)
http://localhost:8089/swagger-ui.html   (entregaMS)
http://localhost:8090/swagger-ui.html   (reporteMS)
```

## Tecnologías

- Java 17
- Spring Boot 3.2.5
- Spring Cloud 2023.0.1 (Eureka, OpenFeign, Gateway)
- MySQL (XAMPP)
- JUnit 5 + Mockito (pruebas unitarias)
- springdoc-openapi (Swagger)
- Maven
- Configuración mediante archivos YAML (application.yml en cada microservicio)

## Instrucciones de ejecución local

### Requisitos previos

1. Tener instalado XAMPP con MySQL activo
2. Tener instalado JDK 17
3. Crear las 10 bases de datos en phpMyAdmin:

```sql
CREATE DATABASE vehiculos_db;
CREATE DATABASE clientes_db;
CREATE DATABASE empleados_db;
CREATE DATABASE categorias_vehiculo_db;
CREATE DATABASE reservas_db;
CREATE DATABASE pagos_db;
CREATE DATABASE disponibilidad_db;
CREATE DATABASE mantenimiento_db;
CREATE DATABASE entrega_db;
CREATE DATABASE reportes_db;
```

### Orden de arranque

El orden es importante para que el descubrimiento de servicios funcione correctamente:

1. **eureka-server** (primero siempre) - puerto 8761
2. Microservicios base: vehiculoMS, clienteMS, empleadoMS, categoriaVehiculoMS
3. Microservicios orquestadores: reservaMS, pagoMS, disponibilidadMS, mantenimientoMS, entregaMS, reporteMS
4. **gateway** (último) - puerto 8080

Las tablas se crean automáticamente al levantar cada microservicio gracias a `spring.jpa.hibernate.ddl-auto=update`. Los datos de ejemplo se cargan mediante un `DataLoader` en cada microservicio.

### Verificación

- Panel de Eureka: `http://localhost:8761` (deben aparecer los 10 microservicios registrados)
- Documentación Swagger de cada MS en las rutas indicadas arriba

## Pruebas unitarias

Las pruebas unitarias están desarrolladas con JUnit 5 y Mockito, ubicadas en `src/test/java` de cada microservicio. Para ejecutarlas:

```
mvn test
```

Las pruebas cubren la lógica de negocio de la capa de servicio, incluyendo el cálculo automático del costo de las reservas y la combinación de datos mediante Feign.
