# 🧬 Detector de Mutantes - Proyecto Magneto

## 👤 Datos del Alumno

- **Alumna:** Federica Benito
- **Legajo:** 50801
- **Comisión:** 3K10
- **Materia:** Desarrollo de Software
- **Repositorio:** [GitHub](https://github.com/FedericaBenito/GlobalDesarrollo-MercadoMutantes.git)
- **API en Render:** [https://api-mutantes-zn0u.onrender.com](https://api-mutantes-zn0u.onrender.com)
- **Swagger UI:** [https://api-mutantes-zn0u.onrender.com/swagger-ui/index.html](https://api-mutantes-zn0u.onrender.com/swagger-ui/index.html)

---

## 📋 Descripción del Proyecto

API REST desarrollada en **Spring Boot** que detecta si un humano es mutante basándose en su secuencia de ADN.

Un humano es **mutante** si se encuentran **más de una secuencia de cuatro letras iguales** (A, T, C, G) de forma horizontal, vertical u oblicua en su matriz de ADN NxN.

### Ejemplo de ADN Mutante:
```
A T G C G A
C A G T G C
T T A T G T
A G A A G G  ← Secuencia horizontal
C C C C T A  ← Secuencia horizontal
T C A C T G
```

---

## ✨ Características del Proyecto

### Nivel 1: Algoritmo ✅
- Implementación del método `isMutant(String[] dna)`
- Detección de secuencias horizontales, verticales y diagonales
- Optimización con **early termination** (se detiene al encontrar más de 1 secuencia)
- Validación completa de entrada

### Nivel 2: API REST ✅
- Endpoint `POST /mutant` para verificar ADN
- Respuestas HTTP: `200 OK` (mutante), `403 Forbidden` (humano), `400 Bad Request` (inválido)
- Desplegado en **Render**
- Documentación con **Swagger/OpenAPI**

### Nivel 3: Persistencia y Estadísticas ✅
- Base de datos **H2** embebida
- Hash **SHA-256** para evitar duplicados
- Endpoint `GET /stats` con estadísticas
- **87% de cobertura de tests** (117 tests unitarios e integración)

---

## 🛠️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database**
- **Gradle 8.5**
- **Lombok**
- **Swagger/OpenAPI 3.0**
- **JUnit 5 & Mockito**
- **JaCoCo** (cobertura de código)
- **Render** (deployment)

---

## 🚀 Comandos Importantes

### Compilar el proyecto
```bash
./gradlew clean build
```

### Ejecutar tests
```bash
./gradlew test
```

### Generar reporte de cobertura
```bash
./gradlew test jacocoTestReport
```
📊 El reporte se genera en: `build/reports/jacoco/test/html/index.html`

### Ejecutar la aplicación localmente
```bash
./gradlew bootRun
```
🌐 La app estará en: `http://localhost:8080`

### Ver Swagger UI (local)
```
http://localhost:8080/swagger-ui/index.html
```

---

## 📊 Cobertura de Tests - 87%

<img width="1366" height="284" alt="Cover Corage mayor a 80%" src="https://github.com/user-attachments/assets/161d1887-7923-4524-8b13-7eaea44649f6" />


### Resumen de Cobertura:

| Package | Cobertura | Detalles |
|---------|-----------|----------|
| **Controllers** | 100% | HomeController, MutantController, StatsController |
| **Servicios** | 88% | MutanteDetector, ServicioMutante, StatsService |
| **DTOs** | 100% | DnaRequest, DnaResponse, StatsResponse, ErrorResponse |
| **Entidades** | 100% | DNARecord |
| **Validaciones** | 98% | ValidacionDNASecuencia |
| **Excepcion** | 100% | GlobalExceptionHandler |
| **TOTAL** | **87%** | **117 tests** |

---

## 🌐 Endpoints de la API

### Base URL (Producción)
```
https://api-mutantes-zn0u.onrender.com
```

### 1. POST /mutant - Verificar ADN

**Request:**
```json
POST /mutant
Content-Type: application/json

{
  "dna": [
    "ATGCGA",
    "CAGTGC",
    "TTATGT",
    "AGAAGG",
    "CCCCTA",
    "TCACTG"
  ]
}
```

**Respuestas:**
- `200 OK` → Es mutante: `{"mutant": true}`
- `403 Forbidden` → Es humano: `{"mutant": false}`
- `400 Bad Request` → Datos inválidos

**Ejemplo con cURL:**
```bash
curl -X POST https://api-mutantes-zn0u.onrender.com/mutant \
  -H "Content-Type: application/json" \
  -d '{"dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]}'
```

---

### 2. GET /stats - Obtener Estadísticas

**Request:**
```
GET /stats
```

**Response:**
```json
{
  "count_mutant_dna": 40,
  "count_human_dna": 100,
  "ratio": 0.4
}
```

**Ejemplo con cURL:**
```bash
curl -X GET https://api-mutantes-zn0u.onrender.com/stats
```

---

## 📐 Diagramas de Secuencia

Los diagramas de secuencia completos se encuentran en el archivo:
📄 **[Diagramas de Secuencia - Federica Benito.html](docs/Diagramas%20de%20Secuencia%20-%20Federica%20Benito.html)**

### Diagrama 1: Método isMutant()
Flujo de detección del algoritmo que verifica secuencias horizontales, verticales y diagonales con early termination.

### Diagrama 2: GET /stats
Flujo de consulta de estadísticas desde la base de datos H2.

---

## 🏗️ Arquitectura del Proyecto

```
src/main/java/global_mutantes/
├── controllers/          # Controladores REST
├── dtos/                 # Data Transfer Objects
├── Servicios/            # Lógica de negocio
├── Repositorio/          # Acceso a datos (JPA)
├── Entidades/            # Entidades JPA
├── Validaciones/         # Validadores custom
├── Excepcion/            # Manejo de excepciones
└── apiConfig/            # Configuración Swagger
```

**Patrones implementados:**
- ✅ Dependency Injection
- ✅ DTO Pattern
- ✅ Repository Pattern
- ✅ Service Layer
- ✅ Global Exception Handler

---

## 🧪 Testing

### Ejecutar todos los tests:
```bash
./gradlew test
```

### Tests por categoría:
```bash
# Tests del algoritmo
./gradlew test --tests MutanteDetectorTest

# Tests de controladores
./gradlew test --tests MutantControllerTest
./gradlew test --tests StatsControllerTest

# Tests de servicios
./gradlew test --tests ServicioMutanteTest
./gradlew test --tests StatsServiceTest
```

### Estructura de Tests (117 tests totales):

- **MutanteDetectorTest:** 19 tests
- **ServicioMutanteTest:** 11 tests
- **MutantControllerTest:** 3 tests
- **StatsControllerTest:** 6 tests
- **HomeControllerTest:** 6 tests
- **ValidacionTest:** 16 tests
- **GlobalExceptionHandlerTest:** 11 tests
- **DTOTests:** 16 tests
- **DNARecordTest:** 15 tests
- Otros tests de integración

---

## 💾 Base de Datos H2

### Configuración
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
```

### Acceso a la consola H2 (local)
```
http://localhost:8080/h2-console
```
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **User:** `sa`
- **Password:** *(vacío)*

### Tabla: dna_records
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | Long | Primary Key (autoincremental) |
| dna | String(64) | Hash SHA-256 del ADN (UNIQUE) |
| is_mutant | Boolean | true = mutante, false = humano |

---

## 📦 Instalación y Ejecución Local

### Prerrequisitos
- Java 17+
- Gradle 8.5+

### Pasos:

1. **Clonar el repositorio:**
```bash
git clone https://github.com/FedericaBenito/GlobalDesarrollo-MercadoMutantes.git
cd GlobalDesarrollo-MercadoMutantes
```

2. **Compilar:**
```bash
./gradlew clean build
```

3. **Ejecutar:**
```bash
./gradlew bootRun
```

4. **Acceder:**
- API: `http://localhost:8080`
- Swagger: `http://localhost:8080/swagger-ui/index.html`
- H2 Console: `http://localhost:8080/h2-console`

---

## 📝 Ejemplos de Uso

### Ejemplo 1: ADN Mutante
```bash
curl -X POST http://localhost:8080/mutant \
  -H "Content-Type: application/json" \
  -d '{
    "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
  }'
```
**Respuesta:** `200 OK` `{"mutant": true}`

### Ejemplo 2: ADN Humano
```bash
curl -X POST http://localhost:8080/mutant \
  -H "Content-Type: application/json" \
  -d '{
    "dna": ["ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"]
  }'
```
**Respuesta:** `403 Forbidden` `{"mutant": false}`

### Ejemplo 3: Consultar Estadísticas
```bash
curl -X GET http://localhost:8080/stats
```
**Respuesta:** `200 OK`
```json
{
  "count_mutant_dna": 40,
  "count_human_dna": 100,
  "ratio": 0.4
}
```

---

## 🔒 Validaciones

El sistema valida automáticamente:
- ✅ Matriz cuadrada NxN
- ✅ Solo caracteres válidos: A, T, C, G
- ✅ No acepta valores nulos o vacíos
- ✅ Todas las filas deben tener la misma longitud

---

## 📚 Documentación Adicional

- **Swagger UI:** [https://api-mutantes-zn0u.onrender.com/swagger-ui/index.html](https://api-mutantes-zn0u.onrender.com/swagger-ui/index.html)
- **Diagramas de Secuencia:** Ver archivo HTML en `/docs`
- **Reporte de Cobertura:** Ejecutar `./gradlew jacocoTestReport`

---

## 📞 Contacto

**Federica Benito**
- 📧 Email: federica.benito@alumnos.frm.utn.edu.ar
- 💼 GitHub: [@federica-benito](https://github.com/federica-benito)
- 🔗 Repositorio: [GlobalDesarrollo-MercadoMutantes](https://github.com/FedericaBenito/GlobalDesarrollo-MercadoMutantes)

---

## 📄 Licencia

Proyecto académico - Universidad Tecnológica Nacional (UTN)  
Desarrollo de Software - Comisión 3K10

---

<div align="center">

**Hecho con ❤️ por Federica Benito**

</div>
