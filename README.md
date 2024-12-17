Biblioteca Demo es una aplicación desarrollada con Spring Boot que permite gestionar préstamos de libros en una biblioteca. La aplicación facilita la creación, seguimiento y devolución de préstamos, así como la gestión de usuarios y libros disponibles. Con una interfaz RESTful, permite una fácil integración con clientes front-end o aplicaciones móviles.
⭐ Características

    Gestión de Libros: Agregar, listar y actualizar el estado de los libros.
    Gestión de Usuarios: Registrar y gestionar usuarios que pueden realizar préstamos.
    Préstamos de Libros: Permite a los usuarios prestar y devolver libros, con validaciones para garantizar que un usuario no tenga más de 3 préstamos activos.
    Validaciones y Manejo de Errores: Implementación de validaciones para entradas de datos y manejo global de excepciones para respuestas consistentes.
    Documentación de API: Endpoints bien definidos para facilitar la interacción con la API.

🛠️ Tecnologías Utilizadas

    Lenguaje: Java 17
    Framework: Spring Boot
    Gestor de Dependencias: Maven
    Base de Datos: H2 (para pruebas) / PostgreSQL (puedes cambiar según tus necesidades)
    ORM: Hibernate
    Validación: Hibernate Validator y Jakarta Validation
    Herramientas de Desarrollo: IntelliJ IDEA / Eclipse

🚀 Instalación y Configuración
📋 Requisitos Previos

    Java 17 o superior
    Maven 3.6.3 o superior
    Git (opcional, para clonar el repositorio)

🔧 Pasos de Instalación

    Clonar el Repositorio

git clone https://github.com/Vexturion/library.git
cd library-demo

Configurar la Base de Datos

Por defecto, la aplicación está configurada para usar la base de datos en memoria H2 para pruebas. Si deseas usar PostgreSQL u otra base de datos, actualiza el archivo application.properties o application.yml con las credenciales correspondientes.

Ejemplo para PostgreSQL:

# src/main/resources/application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/biblioteca_demo
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

Construir el Proyecto

Utiliza Maven para construir el proyecto y descargar todas las dependencias necesarias.

mvn clean install

Ejecutar la Aplicación

Puedes ejecutar la aplicación utilizando el siguiente comando:

mvn spring-boot:run

O ejecutando el archivo JAR generado:

java -jar target/biblioteca-demo-0.0.1-SNAPSHOT.jar

Acceder a la Aplicación

Una vez que la aplicación esté en ejecución, podrás acceder a ella en:

    http://localhost:8080

    Nota: Si cambiaste el puerto por defecto, ajusta la URL en consecuencia.

📝 Uso
📥 Insertar Datos de Prueba

Para probar la aplicación, puedes insertar algunos datos de prueba en la base de datos utilizando los siguientes scripts SQL:

-- Libros de J.K. Rowling (3)
INSERT INTO BOOK (ISBN, TITLE, AUTHOR, AVAILABLE) VALUES ('ISBNROW001', 'Harry Potter y la Piedra Filosofal', 'J.K. Rowling', TRUE);
INSERT INTO BOOK (ISBN, TITLE, AUTHOR, AVAILABLE) VALUES ('ISBNROW002', 'Harry Potter y la Cámara Secreta', 'J.K. Rowling', TRUE);
INSERT INTO BOOK (ISBN, TITLE, AUTHOR, AVAILABLE) VALUES ('ISBNROW003', 'Harry Potter y el Prisionero de Azkaban', 'J.K. Rowling', TRUE);

-- Libros de George R. R. Martin (4)
INSERT INTO BOOK (ISBN, TITLE, AUTHOR, AVAILABLE) VALUES ('ISBNGRRM001', 'Juego de tronos', 'George R. R. Martin', TRUE);
INSERT INTO BOOK (ISBN, TITLE, AUTHOR, AVAILABLE) VALUES ('ISBNGRRM002', 'Choque de reyes', 'George R. R. Martin', TRUE);
INSERT INTO BOOK (ISBN, TITLE, AUTHOR, AVAILABLE) VALUES ('ISBNGRRM003', 'Tormenta de espadas', 'George R. R. Martin', TRUE);
INSERT INTO BOOK (ISBN, TITLE, AUTHOR, AVAILABLE) VALUES ('ISBNGRRM004', 'Festín de cuervos', 'George R. R. Martin', TRUE);

-- Libros de J.R.R. Tolkien (5)
INSERT INTO BOOK (ISBN, TITLE, AUTHOR, AVAILABLE) VALUES ('ISBNTOL001', 'El hobbit', 'J.R.R. Tolkien', TRUE);
INSERT INTO BOOK (ISBN, TITLE, AUTHOR, AVAILABLE) VALUES ('ISBNTOL002', 'La comunidad del anillo', 'J.R.R. Tolkien', TRUE);
INSERT INTO BOOK (ISBN, TITLE, AUTHOR, AVAILABLE) VALUES ('ISBNTOL003', 'Las dos torres', 'J.R.R. Tolkien', TRUE);
INSERT INTO BOOK (ISBN, TITLE, AUTHOR, AVAILABLE) VALUES ('ISBNTOL004', 'El retorno del rey', 'J.R.R. Tolkien', TRUE);
INSERT INTO BOOK (ISBN, TITLE, AUTHOR, AVAILABLE) VALUES ('ISBNTOL005', 'El Silmarillion', 'J.R.R. Tolkien', TRUE);

-- Usuarios
INSERT INTO USERS (ID, NAME, EMAIL) VALUES ('USER001', 'Juan Pérez', 'juan@example.com');
INSERT INTO USERS (ID, NAME, EMAIL) VALUES ('USER002', 'María López', 'maria@example.com');

Puedes ejecutar estos scripts utilizando la consola de tu base de datos o una herramienta como DBeaver o pgAdmin.
🛠️ Realizar Préstamos

Una vez que los datos de prueba están insertados, puedes realizar solicitudes al endpoint /library/loan para prestar libros. A continuación, se muestran ejemplos de cómo hacerlo usando cURL y Postman.
Ejemplo 1: Préstamo de un libro de J.K. Rowling por Juan Pérez

Solicitud JSON:

{
    "isbn": "ISBNROW001",
    "userId": "USER001"
}

Comando cURL:

curl -X POST http://localhost:8080/library/loan \
     -H "Content-Type: application/json" \
     -d '{
           "isbn": "ISBNROW001",
           "userId": "USER001"
         }'

Respuesta Esperada:

{
  "id": 1,
  "isbn": "ISBNROW001",
  "userId": "USER001",
  "loanDate": "2024-12-16",
  "dueDate": "2024-12-30",
  "returned": false
}



🛣️ Endpoints de la API

A continuación, se detallan los endpoints disponibles en la API, sus métodos HTTP, descripciones y ejemplos de uso.
1. Crear un Préstamo

    URL: /library/loan

    Método HTTP: POST

    Descripción: Crea un nuevo préstamo de un libro por parte de un usuario.

    Cuerpo de la Solicitud:

{
    "isbn": "ISBNGRRM001",
    "userId": "USER002"
}

Respuesta Exitosa:

{
  "id": 3,
  "isbn": "ISBNGRRM001",
  "userId": "USER002",
  "loanDate": "2024-12-16",
  "dueDate": "2024-12-30",
  "returned": false
}

Respuesta de Error (Libro No Disponible):

    {
      "message": "El libro no está disponible para préstamo."
    }

2.- Buscar libros por Titulo
   Método HTTP: GET
    URL: /library/authors?author={nombreDelAuthor}

    Parámetros:
        author: El nombre del autor (o parte del nombre).

Ejemplo de uso:

    Solicitud:

GET http://localhost:8080/library/authors?author=Martin

Respuesta exitosa:

[{"isbn":"ISBNGRRM001","title":"Juego de tronos","author":"George R. R. Martin","available":true},
{"isbn":"ISBNGRRM002","title":"Choque de reyes","author":"George R. R. Martin","available":true},
{"isbn":"ISBNGRRM003","title":"Tormenta de espadas","author":"George R. R. Martin","available":true},
{"isbn":"ISBNGRRM004","title":"Festín de cuervos","author":"George R. R. Martin","available":true}]

3.- Buscar libros por Titulo
   Método HTTP: GET
    URL: /library/authors?author={nombreDelLibro}

    Parámetros:
        titulo del libro: Titulo parcial o completo del libro.

Ejemplo de uso:

    Solicitud:

GET http://localhost:8080/library/books?title=Harry

Respuesta exitosa:

[{"isbn":"ISBNROW001","title":"Harry Potter y la Piedra Filosofal","author":"J.K. Rowling","available":false},
{"isbn":"ISBNROW002","title":"Harry Potter y la Cámara Secreta","author":"J.K. Rowling","available":true},
{"isbn":"ISBNROW003","title":"Harry Potter y el Prisionero de Azkaban","author":"J.K. Rowling","available":true}]


🗃️ Esquema de la Base de Datos

A continuación, se muestra una descripción de las tablas principales y sus relaciones.
1. Tabla BOOK
Columna	Tipo	Descripción
ISBN	VARCHAR	Identificador único del libro (PK).
TITLE	VARCHAR	Título del libro.
AUTHOR	VARCHAR	Autor del libro.
AVAILABLE	BOOLEAN	Indica si el libro está disponible.
2. Tabla USERS
Columna	Tipo	Descripción
ID	VARCHAR	Identificador único del usuario (PK).
NAME	VARCHAR	Nombre del usuario.
EMAIL	VARCHAR	Correo electrónico del usuario.
3. Tabla LOAN
Columna	Tipo	Descripción
ID	BIGINT	Identificador único del préstamo (PK).
BOOK_ISBN	VARCHAR	ISBN del libro prestado (FK a BOOK.ISBN).
USER_ID	VARCHAR	ID del usuario que realiza el préstamo (FK a USERS.ID).
LOAN_DATE	DATE	Fecha en que se realizó el préstamo.
DUE_DATE	DATE	Fecha límite para la devolución del libro.
RETURNED	BOOLEAN	Indica si el libro ha sido devuelto.

Relaciones:

    LOAN a BOOK: Muchos a Uno.
    LOAN a USERS: Muchos a Uno.

🧪 Pruebas
📝 Pruebas Unitarias

Implementa pruebas unitarias para cada componente de tu aplicación, asegurándote de que cada método funcione según lo esperado.

Ejemplo de Prueba para LibraryService:

// src/test/java/com/library/demo/service/LibraryServiceTest.java
package com.library.demo.service;

import com.library.demo.exception.LibraryException;
import com.library.demo.model.Book;
import com.library.demo.model.Loan;
import com.library.demo.model.User;
import com.library.demo.repository.BookRepository;
import com.library.demo.repository.LoanRepository;
import com.library.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LibraryServiceTest {

    package com.library.demo.service;

import com.library.demo.exception.LibraryException;
import com.library.demo.model.Book;
import com.library.demo.model.Loan;
import com.library.demo.model.User;
import com.library.demo.repository.BookRepository;
import com.library.demo.repository.LoanRepository;
import com.library.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LibraryServiceTest {

    private LibraryService service;
    private BookRepository bookRepository;
    private LoanRepository loanRepository;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        loanRepository = mock(LoanRepository.class);
        userRepository = mock(UserRepository.class);

        service = new LibraryService(bookRepository, loanRepository, userRepository);

        // Crear libros simulados
        Book book1 = new Book("ISBN001", "Java Programming", "Author A", true);
        Book book2 = new Book("ISBN002", "Advanced Java", "Author B", true);

        // Configurar comportamiento de los mocks
        when(bookRepository.findByTitleContainingIgnoreCase("java"))
                .thenReturn(Arrays.asList(book1, book2));
        when(bookRepository.findByAuthorContainingIgnoreCase("Author A"))
                .thenReturn(Collections.singletonList(book1));
        when(bookRepository.findByAuthorContainingIgnoreCase("Non Existing"))
                .thenReturn(Collections.emptyList());
        when(bookRepository.findById("ISBN001")).thenReturn(Optional.of(book1));
        when(bookRepository.findById("ISBN002")).thenReturn(Optional.of(book2));

        // Crear usuario simulado
        User user1 = new User("USER001", "Juan Pérez", "juan@example.com", new ArrayList<>());
        User user2 = new User("USER002", "María López", "maria@example.com", new ArrayList<>());

        // Configurar usuario con 3 préstamos activos
        Book loanedBook1 = new Book("ISBN010", "C++ Guide", "Author D", false);
        Book loanedBook2 = new Book("ISBN011", "JavaScript Essentials", "Author E", false);
        Book loanedBook3 = new Book("ISBN012", "Kotlin In-Depth", "Author F", false);

        Loan l1 = new Loan(loanedBook1, user2, LocalDate.now().minusDays(2), LocalDate.now().plusDays(12), false);
        Loan l2 = new Loan(loanedBook2, user2, LocalDate.now().minusDays(5), LocalDate.now().plusDays(9), false);
        Loan l3 = new Loan(loanedBook3, user2, LocalDate.now().minusDays(1), LocalDate.now().plusDays(13), false);

        user2.setActiveLoans(Arrays.asList(l1, l2, l3));

        when(userRepository.findById("USER001")).thenReturn(Optional.of(user1));
        when(userRepository.findById("USER002")).thenReturn(Optional.of(user2));
    }

    @Test
    void shouldFindBooksByTitle() {
        var foundBooks = service.findBooksByTitle("java");
        assertEquals(2, foundBooks.size());
        assertTrue(foundBooks.stream().anyMatch(b -> b.getTitle().equals("Java Programming")));
        assertTrue(foundBooks.stream().anyMatch(b -> b.getTitle().equals("Advanced Java")));
    }

    @Test
    void shouldFindBooksByAuthor() {
        var foundBooks = service.findBooksByAuthor("Author A");
        assertEquals(1, foundBooks.size());
        assertEquals("Java Programming", foundBooks.get(0).getTitle());

        var noBooks = service.findBooksByAuthor("Non Existing");
        assertTrue(noBooks.isEmpty());
    }

    @Test
    void shouldLoanAvailableBook() throws LibraryException {
        Loan loan = service.loanBook("ISBN001", "USER001");
        assertNotNull(loan);
        assertEquals("ISBN001", loan.getBook().getIsbn());
        assertEquals("USER001", loan.getUser().getId());
        assertFalse(loan.getBook().isAvailable());

        verify(bookRepository, times(1)).save(Mockito.any(Book.class));
        verify(loanRepository, times(1)).save(Mockito.any(Loan.class));
    }

    @Test
    void shouldNotLoanUnavailableBook() {
        Book unavailableBook = new Book("ISBN002", "Advanced Java", "Author B", false);
        when(bookRepository.findById("ISBN002")).thenReturn(Optional.of(unavailableBook));

        assertThrows(LibraryException.class, () -> service.loanBook("ISBN002", "USER001"));
    }

    @Test
    void shouldNotExceedMaxLoans() {
        assertThrows(LibraryException.class, () -> service.loanBook("ISBN001", "USER002"));
    }
}

}




