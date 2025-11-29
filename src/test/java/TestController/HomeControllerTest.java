package TestController;

import global_mutantes.controllers.HomeController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class HomeControllerTest {

    private HomeController homeController;

    @BeforeEach
    void setUp() {
        homeController = new HomeController();
    }

    @Test
    void testHomeEndpointReturnsCorrectStructure() {
        // Act
        Map<String, Object> response = homeController.home();

        // Assert
        assertNotNull(response);
        assertTrue(response.containsKey("status"));
        assertTrue(response.containsKey("message"));
        assertTrue(response.containsKey("endpoints"));
    }

    @Test
    void testHomeEndpointReturnsCorrectStatus() {
        // Act
        Map<String, Object> response = homeController.home();

        // Assert
        assertEquals("online", response.get("status"));
    }

    @Test
    void testHomeEndpointReturnsCorrectMessage() {
        // Act
        Map<String, Object> response = homeController.home();

        // Assert
        assertEquals("Servicio de análisis genético de mutantes", response.get("message"));
    }

    @Test
    void testHomeEndpointReturnsAllEndpoints() {
        // Act
        Map<String, Object> response = homeController.home();

        @SuppressWarnings("unchecked")
        Map<String, String> endpoints = (Map<String, String>) response.get("endpoints");

        // Assert
        assertNotNull(endpoints);
        assertTrue(endpoints.containsKey("verificar_mutante"));
        assertTrue(endpoints.containsKey("estadisticas"));
        assertTrue(endpoints.containsKey("documentacion"));
    }

    @Test
    void testEndpointsContainCorrectDescriptions() {
        // Act
        Map<String, Object> response = homeController.home();

        @SuppressWarnings("unchecked")
        Map<String, String> endpoints = (Map<String, String>) response.get("endpoints");

        // Assert
        assertTrue(endpoints.get("verificar_mutante").contains("POST /mutant"));
        assertTrue(endpoints.get("estadisticas").contains("GET /stats"));
        assertTrue(endpoints.get("documentacion").contains("GET /swagger-ui"));
    }

    @Test
    void testHomeEndpointIsIdempotent() {
        // Act
        Map<String, Object> response1 = homeController.home();
        Map<String, Object> response2 = homeController.home();

        // Assert
        assertEquals(response1.get("status"), response2.get("status"));
        assertEquals(response1.get("message"), response2.get("message"));
    }
}
