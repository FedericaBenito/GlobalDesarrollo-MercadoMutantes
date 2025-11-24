package com.example.GlobalMutantes.service;

import com.example.GlobalMutantes.model.Stats;
import com.example.GlobalMutantes.repository.ADNRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StatsServiceTest {

    @Mock
    private ADNRepository adnRepository; // Mock the repository

    @InjectMocks
    private StatsService statsService; // Inject the mock into the service

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    /*
    TEST 1: VERIFICAR QUE GETSTATS() FUNCIONA CON HUMANOS Y MUTANTES EN LA BD
     */
    @Test
    public void testGetStatsWithHumanAndMutantADN() {
        long expectedMutants = 40;
        long expectedHumans = 100;
        double expectedRatio = 0.4;
        when(adnRepository.countByEsMutante(true)).thenReturn(expectedMutants);
        when(adnRepository.countByEsMutante(false)).thenReturn(expectedHumans);
        Stats result = statsService.getStats();
        assertEquals(expectedMutants, result.getContadorADNMutante(), "El contador de ADN mutante debería coincidir");
        assertEquals(expectedHumans, result.getContadorADNHumano(), "El contador de ADN humano debería coincidir");
        assertEquals(expectedRatio, result.getRatio(), 0.001, "El ratio debería ser correcto");
    }

    /*
    TEST 2: VERIFICAR QUE GETSTATS() FUNCIONA SIN HUMANOS NI MUTANTES EN LA BD
     */
    @Test
    public void testGetStatsWithNoHumans() {
        long expectedMutants = 10;
        long expectedHumans = 0;
        double expectedRatio = 0.0;
        when(adnRepository.countByEsMutante(true)).thenReturn(expectedMutants);
        when(adnRepository.countByEsMutante(false)).thenReturn(expectedHumans);
        Stats result = statsService.getStats();
        assertEquals(expectedMutants, result.getContadorADNMutante(), "El contador de ADN mutante debería coincidir");
        assertEquals(expectedHumans, result.getContadorADNHumano(), "El contador de ADN humano debería coincidir");
        assertEquals(expectedRatio, result.getRatio(), "Ratio debería ser 0 porque no hay humanos");
    }
}