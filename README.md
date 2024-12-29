# Hexagonal Architecture Shop API

Este proyecto implementa un sistema de gestión de un carrito de compras, sus líneas de productos y productos utilizando la Arquitectura Hexagonal. Está diseñado para separar de manera clara las responsabilidades y promover la mantenibilidad y escalabilidad.

## Características principales

- **Arquitectura Hexagonal**: Implementa puertos y adaptadores para garantizar la independencia del dominio central.
- **Controladores REST**: el carrito, líneas de productos del carrito y productos
- **Base de datos**: Utiliza MySQL como almacenamiento persistente.
- **Autenticación y Autorización**: Integración con Keycloak para manejar seguridad.

## Estructura del proyecto

1. **Dominio**: Contiene la lógica de negocio central.
2. **Aplicación**: Servicios y casos de uso que orquestan las operaciones del dominio.
3. **Infraestructura**: Implementaciones técnicas como persistencia y controladores REST.
4. **Bootstrap**: Configuración y ejecución inicial de la aplicación, incluyendo la integración con servicios externos y preparación del entorno.

## Requisitos previos

- Docker
- JDK 17 o superior
- Maven 3.8 o superior

## Configuración inicial

Ejecute los siguientes comandos para iniciar los contenedores necesarios:

### MySQL

```bash
docker run --name hexagonal-mysql -d -p3306:3306 -e MYSQL_DATABASE=shop -e MYSQL_ROOT_PASSWORD=test mysql:8.1
```

### Keycloak

```bash
docker run -- name keycloak -d -p 8383:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:25.0.6 start-dev
```
### Configurar Keycloak
[Manual de Configuración de Keycloak](docs/manual-configuracion-keycloak.pdf)


## Controladores disponibles

- **Carrito**

  - `GET /carts/{customerId}`: consulta un carrito.
  - `POST /carts/{customerId}/line-items?productId={productId}&quantity={quantity}`: Agrega productos al carrito.
  - `DELETE /carts/{customerId}`:  vacia el carrito.

- **Productos**

  - `GET /products?query=monitor`: Busca los productos que cosidan con query.

## Autenticación con Keycloak

Para obtener un token de acceso desde Keycloak, use el siguiente comando `curl`:

```bash
curl --location 'http://localhost:8383/realms/spring-keycloak-realm/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Cookie: JSESSIONID=5BF7287ED2E76928F23D0F6DDF94688A' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'client_id=spring-keycloak-client' \
--data-urlencode 'client_secret=iKCL0NjcJKRUUriAlxNYRmZBlyQsj6f8' \
--data-urlencode 'username=admin' \
--data-urlencode 'password=admin'
```

Este comando devuelve un token JWT que se puede usar para autenticarse en las API protegidas.

## Perfiles de Spring

El proyecto utiliza perfiles de Spring para diferenciar entre el uso de una base de datos en memoria y MySQL. Puede especificar el perfil deseado al iniciar la aplicación:

## Perfil `inmemory`

```bash
java -Dspring.profiles.active=inmemory -jar bootstrap/target/bootstrap-1.0.0.jar
```

## Perfil `mysql`

```bash
java -Dspring.profiles.active=mysql -jar bootstrap/target/bootstrap-1.0.0.jar
```

## Ejecución

1. Asegúrese de que los contenedores de Docker estén en ejecución.
2. Compile y ejecute el proyecto utilizando Maven:

```bash
mvn clean install
```

3. Ejecute la aplicación con el perfil deseado (por ejemplo, `mysql` o `inmemory`):

```bash
java -Dspring.profiles.active=mysql -jar bootstrap/target/bootstrap-1.0.0.jar
```

5. Acceda a la aplicación en `http://localhost:8080`.
---

