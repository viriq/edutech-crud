# Paths y peticiones REST

### 👤 Usuarios

#### localhost:8080/api/usuarios

Cuerpo:
```json
{
    "nombreCompleto": "Nombre Usuario",
    "email": "emailsuario@example.com",
    "contrasena": "ContraseñaUsuario",
    "tipoUsuario": "Cliente/Docente/Soporte/..."
}
```

**GET** - Muestra todos los usuarios y sus atributos
<br>
**POST** - Ingresa un usuario (Requiere cuerpo)


#### localhost:8080/api/usuarios/{id}
**GET** - Muestra los atributos del usuario especificado con {id}
<br>
**PUT** - Modifica los atributos del usuario especificado (Requiere cuerpo)
<br>
**DELETE** - Elimina al usuario especificado del sistema

### 🏫 Cursos

Cuerpo:
```json
{
    "titulo": "TítuloCurso",
    "descripcion": "Descripción del curso",
    "nombreInstructor": "Nombre Instructor"
}
```

#### localhost:8080/api/cursos
**GET** - Muestra todos los cursos y sus atributos
<br>
**POST** - Ingresa un nuevo curso (Requiere cuerpo)

#### localhost:8080/api/cursos/{id}
**GET** - Muestra los atributos del curso especificado con {id}.
<br>
**PUT** - Modifica los atributos del curso especificado (Requiere cuerpo)
<br>
**DELETE** - Elimina el curso especificado del sistema

### 💰 Proveedores

Cuerpo:
```json
{
    "rut": "RutProveedor",
    "razonSocial": "Razón Social del proveedor",
    "email": "emailproveedor@example.com",
    "giro": "Descripción del servicio"
}
```

#### localhost:8080/api/proveedores
**GET** - Muestra todos los proveedores y sus atributos
<br>
**POST** - Ingresa un proveedor (Requiere cuerpo)

#### localhost:8080/api/proveedores/{id}
**GET** - Muestra los atributos del proveedor especificado con {id}.
<br>
**PUT** - Modifica los atributos del proveedor especificado (Requiere cuerpo)
<br>
**DELETE** - Elimina al proveedor especificado del sistema
