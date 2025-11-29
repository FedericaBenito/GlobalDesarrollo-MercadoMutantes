package TestDTO;

import global_mutantes.dtos.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DTOTests {

    // DnaRequest Tests
    @Test
    void testDnaRequestGetterSetter() {
        DnaRequest request = new DnaRequest();
        String[] dna = {"ATGC", "CGTA", "TACG", "GCAT"};

        request.setDna(dna);

        assertArrayEquals(dna, request.getDna());
    }

    @Test
    void testDnaRequestWithNull() {
        DnaRequest request = new DnaRequest();
        request.setDna(null);

        assertNull(request.getDna());
    }

    // DnaResponse Tests
    @Test
    void testDnaResponseConstructorMutant() {
        DnaResponse response = new DnaResponse(true);
        assertTrue(response.isMutant());
    }

    @Test
    void testDnaResponseConstructorHuman() {
        DnaResponse response = new DnaResponse(false);
        assertFalse(response.isMutant());
    }

    @Test
    void testDnaResponseSetter() {
        DnaResponse response = new DnaResponse(false);
        response.setMutant(true);

        assertTrue(response.isMutant());
    }

    // StatsResponse Tests
    @Test
    void testStatsResponseConstructor() {
        StatsResponse stats = new StatsResponse(40, 100, 0.4);

        assertEquals(40, stats.getCountMutantDna());
        assertEquals(100, stats.getCountHumanDna());
        assertEquals(0.4, stats.getRatio());
    }

    @Test
    void testStatsResponseWithZeroValues() {
        StatsResponse stats = new StatsResponse(0, 0, 0.0);

        assertEquals(0, stats.getCountMutantDna());
        assertEquals(0, stats.getCountHumanDna());
        assertEquals(0.0, stats.getRatio());
    }

    @Test
    void testStatsResponseSetters() {
        StatsResponse stats = new StatsResponse(0, 0, 0.0);

        stats.setCountMutantDna(50);
        stats.setCountHumanDna(100);
        stats.setRatio(0.5);

        assertEquals(50, stats.getCountMutantDna());
        assertEquals(100, stats.getCountHumanDna());
        assertEquals(0.5, stats.getRatio());
    }

    // ErrorResponse Tests
    @Test
    void testErrorResponseFullConstructor() {
        LocalDateTime now = LocalDateTime.now();
        ErrorResponse error = new ErrorResponse(now, 400, "Bad Request",
                "Invalid DNA", "/mutant");

        assertEquals(now, error.getTimestamp());
        assertEquals(400, error.getStatus());
        assertEquals("Bad Request", error.getError());
        assertEquals("Invalid DNA", error.getMessage());
        assertEquals("/mutant", error.getPath());
    }

    @Test
    void testErrorResponseBuilder() {
        LocalDateTime now = LocalDateTime.now();
        Map<String, String> errors = new HashMap<>();
        errors.put("field", "error message");

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(now)
                .status(400)
                .error("Bad Request")
                .message("Validation failed")
                .path("/mutant")
                .errors(errors)
                .build();

        assertEquals(400, error.getStatus());
        assertNotNull(error.getErrors());
        assertEquals(1, error.getErrors().size());
    }

    @Test
    void testErrorResponseSetters() {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(), 400, "Bad Request", "message", "/path");

        error.setStatus(500);
        error.setError("Internal Server Error");
        error.setMessage("New message");
        error.setPath("/new-path");

        assertEquals(500, error.getStatus());
        assertEquals("Internal Server Error", error.getError());
        assertEquals("New message", error.getMessage());
        assertEquals("/new-path", error.getPath());
    }

    @Test
    void testErrorResponseWithNullErrors() {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(), 400, "Bad Request", "message", "/path");

        assertNull(error.getErrors());
    }

    @Test
    void testErrorResponseBuilderWithNullValues() {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(400)
                .error("Bad Request")
                .message("message")
                .path("/path")
                .build();

        assertNull(error.getErrors());
    }

    @Test
    void testStatsResponseRatioCalculation() {
        StatsResponse stats1 = new StatsResponse(1, 1, 1.0);
        assertEquals(1.0, stats1.getRatio());

        StatsResponse stats2 = new StatsResponse(40, 100, 0.4);
        assertEquals(0.4, stats2.getRatio());
    }

    @Test
    void testDnaResponseMultipleToggle() {
        DnaResponse response = new DnaResponse(true);

        response.setMutant(false);
        assertFalse(response.isMutant());

        response.setMutant(true);
        assertTrue(response.isMutant());
    }

    @Test
    void testErrorResponseTimestampFormat() {
        LocalDateTime specificTime = LocalDateTime.of(2024, 1, 1, 12, 0, 0);
        ErrorResponse error = new ErrorResponse(
                specificTime, 400, "Bad Request", "message", "/path");

        assertEquals(specificTime, error.getTimestamp());
    }

    @Test
    void testStatsResponseLargeNumbers() {
        StatsResponse stats = new StatsResponse(1000000, 5000000, 0.2);

        assertEquals(1000000, stats.getCountMutantDna());
        assertEquals(5000000, stats.getCountHumanDna());
        assertEquals(0.2, stats.getRatio());
    }

    @Test
    void testErrorResponseWithComplexErrors() {
        Map<String, String> errors = new HashMap<>();
        errors.put("dna", "Invalid DNA sequence");
        errors.put("length", "Must be NxN matrix");

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(400)
                .error("Validation Error")
                .message("Multiple validation errors")
                .path("/mutant")
                .errors(errors)
                .build();

        assertEquals(2, error.getErrors().size());
        assertTrue(error.getErrors().containsKey("dna"));
        assertTrue(error.getErrors().containsKey("length"));
    }
}