package TestException;

import global_mutantes.Excepcion.DnaHashCalculationException;
import global_mutantes.Excepcion.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
        webRequest = mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("uri=/test");
    }

    @Test
    void testHandleValidationExceptions() {
        // Arrange
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("dnaRequest", "dna", "Secuencia DNA ingresada invalida");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleValidationExceptions(ex, webRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().get("status"));
        assertEquals("Bad Request", response.getBody().get("error"));
        assertTrue(response.getBody().containsKey("errors"));
    }

    @Test
    void testHandleDnaHashCalculationException() {
        // Arrange
        DnaHashCalculationException ex = new DnaHashCalculationException("Error al calcular hash");

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleDnaHashCalculationException(ex, webRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().get("status"));
        assertEquals("Internal Server Error", response.getBody().get("error"));
        assertEquals("Error al calcular hash", response.getBody().get("message"));
    }

    @Test
    void testHandleIllegalArgumentException() {
        // Arrange
        IllegalArgumentException ex = new IllegalArgumentException("Argumento invalido");

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleIllegalArgumentException(ex, webRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().get("status"));
        assertEquals("Bad Request", response.getBody().get("error"));
        assertEquals("Argumento invalido", response.getBody().get("message"));
    }

    @Test
    void testHandleGlobalException() {
        // Arrange
        Exception ex = new Exception("Error inesperado");

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleGlobalException(ex, webRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().get("status"));
        assertEquals("Internal Server Error", response.getBody().get("error"));
        assertEquals("Ocurrió un error inesperado en el servidor", response.getBody().get("message"));
    }

    @Test
    void testValidationExceptionContainsTimestamp() {
        // Arrange
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of());

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleValidationExceptions(ex, webRequest);

        // Assert
        assertTrue(response.getBody().containsKey("timestamp"));
    }

    @Test
    void testValidationExceptionContainsPath() {
        // Arrange
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of());

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleValidationExceptions(ex, webRequest);

        // Assert
        assertEquals("/test", response.getBody().get("path"));
    }

    @Test
    void testMultipleValidationErrors() {
        // Arrange
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError error1 = new FieldError("obj", "field1", "Error 1");
        FieldError error2 = new FieldError("obj", "field2", "Error 2");

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(error1, error2));

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleValidationExceptions(ex, webRequest);

        // Assert
        @SuppressWarnings("unchecked")
        Map<String, String> errors = (Map<String, String>) response.getBody().get("errors");
        assertEquals(2, errors.size());
    }

    @Test
    void testDnaHashExceptionWithCause() {
        // Arrange
        Exception cause = new Exception("Causa original");
        DnaHashCalculationException ex = new DnaHashCalculationException("Error hash", cause);

        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleDnaHashCalculationException(ex, webRequest);

        // Assert
        assertEquals("Error hash", response.getBody().get("message"));
    }

    @Test
    void testExceptionHandlersReturnCorrectHttpStatus() {
        // Arrange
        IllegalArgumentException illegalArg = new IllegalArgumentException("test");
        DnaHashCalculationException hashEx = new DnaHashCalculationException("test");
        Exception generalEx = new Exception("test");

        // Act & Assert
        assertEquals(HttpStatus.BAD_REQUEST,
                exceptionHandler.handleIllegalArgumentException(illegalArg, webRequest).getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                exceptionHandler.handleDnaHashCalculationException(hashEx, webRequest).getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                exceptionHandler.handleGlobalException(generalEx, webRequest).getStatusCode());
    }
}
