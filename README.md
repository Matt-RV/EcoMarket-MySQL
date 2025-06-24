# 📦 Sistema de Gestión de Pedidos
   Una API Rest desarrollada con Spring Boot para la gestión de pedidos y clientes, con documentación usando Swagger y soporte para HATEOAS.

## 🚀 Características

- **CRUD Completo** para Pedidos y Clientes
- **API REST** con endpoints bien estructurados
- **Documentación** con Swagger
- **Soporte HATEOAS** (v2 de la API)
- **Base de datos MySQL** con configuraciones para desarrollo y testing
- **Datos de prueba** generados automáticamente con Faker.
- **Testing unitario** con JUnit y Mockito

## 🛠️ Tecnologías Utilizadas
- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring HATEOAS**
- **MySQL**
- **Swagger/OpenAPI**
- **Lombok**
- **DataFaker** (generación de datos de prueba)
- **JUnit 5** y **Mockito** (testing)

## 📋 Prerrequisitos
- Java 17 o superior
- Maven 3.6+
- MySql 8.0
- IDE de preferencia (IntelliJ IDEA - Eclipse - VS Code)

## ⚙️ Configuración

### 1.- Base de Datos

Se necesatia crear las base de datos necesarias en MySQL:

´´´sql
CREATE DATABASE ecomarket_pedidos_dev;
CREATE DATABASE ecomarket_pedidos_test;
´´´

### 2.- Configuración de perfiles

El proyecto incluye tres perfiles de configuración:
  - **Desarrollo** ('dev'): Configuración para desarrollo local.
  - **Testing** ('test'): Configuración para pruebas.
  - **Producción** (por defecto): Configuración base.

### 3.- Variables de entorno
-- Falta


### Importante: La aplicación estará disponible en: 'http://localhost:8081'

## 📚 Documentación de la API.

### Swagger UI
Una vez que la aplicación esté en ejecución, puedes acceder a la documentación interactiva:
**URL:** 'http://localhost:8081/doc/swagger-ui/index.html'

### Endpoints Principales

#### 👥 Clientes (`/api/v1/clientes`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/clientes` | Listar todos los clientes |
| POST | `/api/v1/clientes` | Crear nuevo cliente |
| GET | `/api/v1/clientes/{id}` | Obtener cliente por ID |
| PUT | `/api/v1/clientes/{id}` | Actualizar cliente |
| DELETE | `/api/v1/clientes/{id}` | Eliminar cliente |
| GET | `/api/v1/clientes/count` | Contar clientes |
| GET | `/api/v1/clientes/nombre/{nombre}` | Buscar por nombre |
| GET | `/api/v1/clientes/email/{email}` | Buscar por email |

#### 📦 Pedidos (`/api/v1/pedidos`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/pedidos` | Listar todos los pedidos |
| POST | `/api/v1/pedidos` | Crear nuevo pedido |
| GET | `/api/v1/pedidos/{id}` | Obtener pedido por ID |
| PUT | `/api/v1/pedidos/{id}` | Actualizar pedido |
| DELETE | `/api/v1/pedidos/{id}` | Eliminar pedido |
| GET | `/api/v1/pedidos/count` | Contar pedidos |
| GET | `/api/v1/pedidos/estado/{estado}` | Buscar por estado |
| PUT | `/api/v1/pedidos/{id}/estado/{estado}` | Actualizar estado |

#### 🔗 API v2 con HATEOAS (`/api/v2/pedidos`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v2/pedidos` | Listar pedidos con enlaces HATEOAS |
| GET | `/api/v2/pedidos/{id}` | Obtener pedido con enlaces HATEOAS |

## 📝 Ejemplos de Uso

### Crear un Cliente

En POST http://localhost:8081/api/v1/clientes
'{
    "idCliente": 1,
    "nombreCliente": "Juan",
    "apellidosCliente": "Pérez García",
    "emailCliente": "juan.perez@email.com",
    "direccionCliente": "Calle Principal 123"
}'

### Crear un Pedido

En POST http://localhost:8081/api/v1/pedidos
'{
    "fechaCreacion": "2024-01-15T10:30:00",
    "estado": "Pendiente",
    "total": 150.50,
    "cliente": {
      "idCliente": 1
    }
}'

### Buscar Pedidos por Estado

En GET http://localhost:8081/api/v1/pedidos/estado/Pendiente

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/gestionPedidos/GestionPedidos/
│   │   ├── Assemblers/          # Assemblers para HATEOAS
│   │   ├── Config/              # Configuraciones (Swagger)
│   │   ├── Controller/          # Controllers REST
│   │   ├── Model/               # Entidades JPA
│   │   ├── Repository/          # Repositorios JPA
│   │   ├── Service/             # Servicios de negocio
│   │   ├── DataLoader.java      # Carga de datos de prueba
│   │   └── GestionPedidosApplication.java
│   └── resources/
│       ├── application.properties
│       ├── application-dev.properties
│       └── application-test.properties
└── test/
    └── java/com/gestionPedidos/GestionPedidos/
        ├── ClienteServiceTest.java
        ├── PedidoServiceTest.java
        └── GestionPedidosApplicationTests.java
```

## 🎯 Estados de Pedidos

Los pedidos pueden tener los siguientes estados:
- **Pendiente**: Pedido recién creado
- **Entregado**: Pedido completado exitosamente
- **Cancelado**: Pedido cancelado

## 📊 Datos de Prueba

En el perfil de desarrollo (`dev`), la aplicación genera automáticamente:
- 25 clientes con datos ficticios
- 25 pedidos con estados aleatorios

## 📞 Contacto

- **Autor**: [Matias Ramos Valenzuela]
- **Email**: [matiasramos.iv@gmail.com]
- **Proyecto**: [https://github.com/Matt-RV/EcoMarket-MySQL]


