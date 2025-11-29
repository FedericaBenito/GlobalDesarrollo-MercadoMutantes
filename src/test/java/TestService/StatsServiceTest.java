package TestService;

import global_mutantes.Repositorio.DNARecordRepositorio;
import global_mutantes.Servicios.StatsService;
import global_mutantes.dtos.StatsResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatsServiceTest {

    @Mock
    private DNARecordRepositorio repository;

    @InjectMocks
    private StatsService statsService;

    @Test
    void testGetStatsWithMutantsAndHumans() {
        // Arrange
        when(repository.countMutants()).thenReturn(40L);
        when(repository.countHumans()).thenReturn(100L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertEquals(40L, stats.getCountMutantDna());
        assertEquals(100L, stats.getCountHumanDna());
        assertEquals(0.4, stats.getRatio(), 0.01);
    }

    @Test
    void testGetStatsWithNoHumans() {
        // Arrange
        when(repository.countMutants()).thenReturn(10L);
        when(repository.countHumans()).thenReturn(0L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertEquals(10L, stats.getCountMutantDna());
        assertEquals(0L, stats.getCountHumanDna());
        assertEquals(1.0, stats.getRatio());
    }

    @Test
    void testGetStatsWithNoMutantsAndNoHumans() {
        // Arrange
        when(repository.countMutants()).thenReturn(0L);
        when(repository.countHumans()).thenReturn(0L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertEquals(0L, stats.getCountMutantDna());
        assertEquals(0L, stats.getCountHumanDna());
        assertEquals(0.0, stats.getRatio());
    }

    @Test
    void testGetStatsRatioRounding() {
        // Arrange
        when(repository.countMutants()).thenReturn(1L);
        when(repository.countHumans()).thenReturn(3L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertEquals(0.33, stats.getRatio(), 0.01);
    }

    @Test
    void testGetStatsCallsRepositoryMethods() {
        // Arrange
        when(repository.countMutants()).thenReturn(5L);
        when(repository.countHumans()).thenReturn(10L);

        // Act
        statsService.getStats();

        // Assert
        verify(repository, times(1)).countMutants();
        verify(repository, times(1)).countHumans();
    }

    @Test
    void testGetStatsWithOnlyMutants() {
        // Arrange
        when(repository.countMutants()).thenReturn(50L);
        when(repository.countHumans()).thenReturn(0L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertEquals(1.0, stats.getRatio());
    }

    @Test
    void testGetStatsWithOnlyHumans() {
        // Arrange
        when(repository.countMutants()).thenReturn(0L);
        when(repository.countHumans()).thenReturn(100L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertEquals(0.0, stats.getRatio());
    }

    @Test
    void testGetStatsWithEqualCounts() {
        // Arrange
        when(repository.countMutants()).thenReturn(50L);
        when(repository.countHumans()).thenReturn(50L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertEquals(1.0, stats.getRatio());
    }

    @Test
    void testGetStatsRatioPrecision() {
        // Arrange
        when(repository.countMutants()).thenReturn(2L);
        when(repository.countHumans()).thenReturn(7L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertEquals(0.29, stats.getRatio(), 0.01);
    }
}