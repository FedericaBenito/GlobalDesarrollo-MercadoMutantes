# **Proyecto: Detector de Mutantes - Mercadolibre**

**Alumna:** Federica Benito
**Legajo:** 50801
**Comisión:** 3K10
**Año:** 2025

---

## **Descripción del Proyecto**
Este proyecto fue desarrollado como parte del examen de **Mercadolibre** para detectar si un humano es mutante basándose en su secuencia de ADN. El sistema consta de una **API REST** construida con **Spring Boot**, una base de datos **H2** para almacenar los ADN verificados, y endpoints para:
- Verificar si un ADN es mutante (`/mutant`).
- Obtener estadísticas de las verificaciones (`/stats`).

---

## **Tecnologías Utilizadas**
- **Java 17**
- **Spring Boot 3.x**
- **H2 Database** (Base de datos embebida)
- **Lombok** (Reducción de boilerplate)
- **JUnit 5** (Pruebas unitarias)
- **Maven** (Gestor de dependencias)

---

## **Requisitos Previos**
- **Java JDK 17** o superior.
- **Maven** instalado.
- **Git** para clonar el repositorio.

---

## **Instalación y Ejecución**

### **1. Clonar el repositorio**
```bash
git clone https://github.com/tu-usuario/nombre-del-repositorio.git
cd GlobalDesarrollo-MercadoMutantes
```

### **2. Compilar y ejecutar el proyecto**
```bash
mvn clean install
mvn spring-boot:run
```

### **3. Acceder a la API**
- La API estará disponible en: [http://localhost:8080](http://localhost:8080)
- **Endpoints disponibles:**
  - **POST** `/mutant`: Verifica si un ADN es mutante.
    - **Body (JSON):**
      ```json
      {
        "dna": ["ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"]
      }
      ```
    - **Respuestas:**
      - `200 OK`: ADN mutante.
      - `403 Forbidden`: ADN humano.
  - **GET** `/stats`: Devuelve estadísticas de las verificaciones.
    - **Respuesta (JSON):**
      ```json
      {
        "count_mutant_dna": 40,
        "count_human_dna": 100,
        "ratio": 0.4
      }
      ```

---

## **Pruebas**
El proyecto incluye pruebas unitarias para:
- La función `isMutant`.
- Los servicios `ADNService` y `StatsService`.
- Los controladores `ADNController` y `StatsController`.

**Ejecución de pruebas:**
```bash
mvn test
```

**Code Coverage:**
- El proyecto tiene un **coverage > 80%**
---

## **Diagrama de Secuencia**
**Diagrama de secuencia: IsMutante**
![SECUENCIA ISMUTANT](https://github.com/user-attachments/assets/a7eb7bec-7d3f-4340-980a-5e3ea7f2ae50)

**Diagrama de secuencia: GetStats**
<img width="1173" height="560" alt="SECUENCIA GETSTATS" src="https://github.com/user-attachments/assets/b8278931-11d1-43e6-8689-e9573afd0a77" />

---

## **Estructura del Proyecto**
```
src/
├── main/
│   ├── java/com/example/GlobalMutantes/
│   │   ├── controller/       # Controladores REST
│   │   ├── model/             # Entidades y DTOs
│   │   ├── repository/        # Repositorios JPA
│   │   ├── service/           # Lógica de negocio
│   │   ├── utils/             # Clases utilitarias
│   │   └── GlobalApplication.java  # Clase principal
│   └── resources/
│       ├── application.properties  # Configuración de Spring Boot
│       └── data.sql                 # Script SQL para H2
└── test/                        # Pruebas unitarias
```

---
