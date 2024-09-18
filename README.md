# Sistema de Registro de Productos con Spring WebFlux y MongoDB

Este proyecto es un servicio API para el registro y administración de productos. La aplicación está construida utilizando **Spring WebFlux**, lo que permite un manejo asíncrono y no bloqueante de las solicitudes HTTP. Los datos de los productos se almacenan en una base de datos NoSQL **MongoDB**, aprovechando su flexibilidad para gestionar los productos.

## Arquitectura y Patrones Utilizados

### 1. **Arquitectura Reactiva**
Este proyecto utiliza Spring WebFlux, lo que implementa un enfoque reactivo basado en **Reactive Streams**. La aplicación está diseñada para manejar un gran número de solicitudes simultáneamente, sin bloquear los recursos de servidor, lo que mejora la eficiencia y escalabilidad del sistema.

### 2. **Patrón de Capas (Layered Architecture)**
El proyecto sigue el patrón de capas, separando la lógica de negocio, la capa de persistencia y la de presentación (controladores). Las capas clave son:

- **Controlador (`ProductController`)**: Gestiona las solicitudes HTTP y coordina con los servicios.
- **Servicios (`ProductService`, `DiscountService`, `ProductStatusService`)**: Contienen la lógica de negocio y las interacciones con otras partes del sistema, como caché y servicios externos.
- **Persistencia (`ProductRepository`)**: Interactúa con la base de datos para realizar las operaciones CRUD.

### 3. **Uso de Caché**
El sistema implementa un caché en memoria para almacenar los estados de los productos (Activo/Inactivo). Esto reduce la latencia al no tener que recalcular o recuperar los estados constantemente desde la base de datos.

### 4. **WebClient para Servicios Externos**
El patrón **WebClient** se utiliza para consumir servicios externos, como el de descuentos para los productos. Este cliente reactivo permite integraciones no bloqueantes con APIs externas.

### 5. **Logging del Tiempo de Respuesta**
Se implementa un filtro para registrar el tiempo de respuesta de cada solicitud en un archivo de texto plano. Este registro permite medir el rendimiento de la API y analizar posibles cuellos de botella.

## Requisitos Previos

Asegúrate de tener instalados los siguientes requisitos en tu entorno local:

- **Java 17** o superior
- **Maven** (para gestionar las dependencias)
- **Docker** (opcional, para ejecutar MongoDB en un contenedor)
- **MongoDB** (si no se usa Docker, instala MongoDB localmente)

## Consideraciones

- ya que Utiliza un servicio externo para crear los descuentos se deben crear segun la id generada por mongo
