Repo para el curso DSY1103 Fullstack I


# Guia 1: Conexión a PostgreSQL (NeonTech) con Hibernate/JPA

Esta guía documenta el proceso completo para conectar la aplicación BibliotecaDUOC a una base de datos PostgreSQL en NeonTech usando Hibernate/JPA.

---

## 📋 Tabla de Contenidos

1. [Prerequisitos](#prerequisitos)
2. [Configuración de NeonTech](#configuración-de-neontech)
3. [Configuración del Proyecto](#configuración-del-proyecto)
4. [Migración del Repository](#migración-del-repository)
5. [Verificación](#verificación)
6. [Troubleshooting](#troubleshooting)

---

## 1. Prerequisitos

### Dependencias Maven

Ya agregadas en `pom.xml`:

```xml
<!-- Spring Data JPA + Hibernate -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- PostgreSQL Driver -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

### Software Necesario

- ✅ Java 21+
- ✅ Maven 3.9+
- ✅ Cuenta en NeonTech (https://neon.tech)

---

## 2. Configuración de NeonTech

### Paso 1: Crear Proyecto en Neon

1. Ingresa a https://console.neon.tech
2. Click en **"New Project"**
3. Configura:
   - **Project name**: `bibliotecaduoc`
   - **Region**: Selecciona el más cercano (ej: `US East`)
   - **PostgreSQL version**: `16` (recomendado)
4. Click en **"Create Project"**

### Paso 2: Obtener Credenciales

Neon te mostrará una pantalla con la cadena de conexión:

![asd](doc/Captura%20de%20pantalla%202026-04-13%20104434.png)

![asd](doc/Captura%20de%20pantalla%202026-04-13%20104531.png)


```
postgresql://username:password@ep-cool-silence-123456.us-east-2.aws.neon.tech/neondb?sslmode=require
```

**Descompón la URL**:
- **Endpoint**: `ep-cool-silence-123456.us-east-2.aws.neon.tech`
- **Database**: `neondb` (puedes crear una nueva base de datos si prefieres, pero este es el nombre por defecto)
- **Username**: `username` (nombre de usuario)
- **Password**: Copia el password (¡guárdalo!, no se muestra de nuevo)


## 3. Configuración del Proyecto

### Paso 1: Configurar `application.properties`

Edita `src/main/resources/application.properties`:


```properties
# ===================================
# PostgreSQL + Hibernate (JPA) - NeonTech
# ===================================

spring.datasource.url=jdbc:postgresql://ep-cool-silence-123456.us-east-2.aws.neon.tech/neondb?sslmode=require
spring.datasource.username=tu_username_real
spring.datasource.password=tu_password_real
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuración de Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

#### Explicación de `ddl-auto`

| Valor | Comportamiento |
|-------|----------------|
| `update` | Actualiza schema sin borrar datos |
| `create` | Borra y recrea tabla en cada inicio (pierde datos) |
| `create-drop` | Borra tabla al cerrar aplicación |
| `validate` | Solo valida que schema coincida (producción) |
| `none` | No hace nada automáticamente |

### Paso 2: Verificar Entidad `Libro`

La clase `Libro` ya está configurada como entidad JPA:

```java
@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "isbn", nullable = false, length = 20)
    private String isbn;

    // ... otros campos
}
```

**Anotaciones clave**:
- `@Entity`: Marca la clase como entidad JPA
- `@Table(name = "libros")`: Nombre de la tabla en la BD
- `@Id`: Clave primaria
- `@GeneratedValue`: PostgreSQL genera el ID automáticamente
- `@Column`: Configuración de cada columna

---

## 4. Cambios al Repository

**Elimina** la implementación manual y crea una **interface**:

```java
package com.example.bibliotecaduoc.repository;

import com.example.bibliotecaduoc.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {

    // Métodos automáticos heredados de JpaRepository:
    // - findAll()
    // - findById(int id)
    // - save(Libro libro)
    // - deleteById(int id)
    // - count()

    // Métodos custom (Spring Data JPA los implementa automáticamente)
    Optional<Libro> findByIsbn(String isbn);

    List<Libro> findByAutorContainingIgnoreCase(String autor);
}
```


## 5. Cambios al Service
Necesitas actualizar `LibroService` para usar los métodos JPA:

```java
// Antes (con ArrayList)
libroRepository.obtenerLibros();

// Después (con JPA)
libroRepository.findAll();
```

## 6. Verificación

### Paso 1: Compilar

```bash
./mvnw clean compile
```

Debe completar sin errores.

### Paso 2: Ejecutar Aplicación

```bash
./mvnw spring-boot:run
```

**Busca en los logs**:

```
Hibernate: create table if not exists libros (
    id int4 generated by default as identity,
    isbn varchar(20) not null,
    titulo varchar(200) not null,
    editorial varchar(100) not null,
    fecha_publicacion int4 not null,
    autor varchar(150) not null,
    primary key (id)
)
```

Si ves esto, **¡la tabla se creó correctamente!** 

### Paso 3: Verificar en Neon SQL Editor

1. Ve a https://console.neon.tech
2. Click en **SQL Editor**
3. Ejecuta:
   ```sql
   SELECT * FROM libros;
   ```

Debería mostrar la tabla vacía pero existente.

### Paso 4: Probar API con Swagger

1. Abre http://localhost:8080/swagger-ui.html
2. Prueba **POST /api/v1/libros**:
   ```json
   {
     "isbn": "978-0-13-468599-1",
     "titulo": "Clean Code",
     "editorial": "Prentice Hall",
     "fechaPublicacion": 2008,
     "autor": "Robert C. Martin"
   }
   ```
3. Verifica con **GET /api/v1/libros**

