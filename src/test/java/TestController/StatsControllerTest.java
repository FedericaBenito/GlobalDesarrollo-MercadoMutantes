package TestController;

import global_mutantes.Servicios.StatsService;
import global_mutantes.controllers.StatsController;
import global_mutantes.dtos.StatsResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatsControllerTest {

    @Mock
    private StatsService statsService;

    @InjectMocks
    private StatsController statsController;

    @Test
    void testGetStatsReturnsOkStatus() {
        // Arrange
        StatsResponse mockStats = new StatsResponse(40, 100, 0.4);
        when(statsService.getStats()).thenReturn(mockStats);

        // Act
        ResponseEntity<StatsResponse> response = statsController.getStats();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetStatsReturnsCorrectData() {
        // Arrange
        StatsResponse mockStats = new StatsResponse(40, 100, 0.4);
        when(statsService.getStats()).thenReturn(mockStats);

        // Act
        ResponseEntity<StatsResponse> response = statsController.getStats();

        // Assert
        assertEquals(40, response.getBody().getCountMutantDna());
        assertEquals(100, response.getBody().getCountHumanDna());
        assertEquals(0.4, response.getBody().getRatio());
    }

    @Test
    void testGetStatsCallsServiceOnce() {
        // Arrange
        StatsResponse mockStats = new StatsResponse(40, 100, 0.4);
        when(statsService.getStats()).thenReturn(mockStats);

        // Act
        statsController.getStats();

        // Assert
        verify(statsService, times(1)).getStats();
    }

    @Test
    void testGetStatsWithNoMutants() {
        // Arrange
        StatsResponse mockStats = new StatsResponse(0, 100, 0.0);
        when(statsService.getStats()).thenReturn(mockStats);

        // Act
        ResponseEntity<StatsResponse> response = statsController.getStats();

        // Assert
        assertEquals(0, response.getBody().getCountMutantDna());
        assertEquals(0.0, response.getBody().getRatio());
    }

    @Test
    void testGetStatsWithNoHumans() {
        // Arrange
        StatsResponse mockStats = new StatsResponse(10, 0, 1.0);
        when(statsService.getStats()).thenReturn(mockStats);

        // Act
        ResponseEntity<StatsResponse> response = statsController.getStats();

        // Assert
        assertEquals(0, response.getBody().getCountHumanDna());
        assertEquals(1.0, response.getBody().getRatio());
    }

    @Test
    void testGetStatsWithEmptyDatabase() {
        // Arrange
        StatsResponse mockStats = new StatsResponse(0, 0, 0.0);
        when(statsService.getStats()).thenReturn(mockStats);

        // Act
        ResponseEntity<StatsResponse> response = statsController.getStats();

        // Assert
        assertEquals(0, response.getBody().getCountMutantDna());
        assertEquals(0, response.getBody().getCountHumanDna());
        assertEquals(0.0, response.getBody().getRatio());
    }
}
