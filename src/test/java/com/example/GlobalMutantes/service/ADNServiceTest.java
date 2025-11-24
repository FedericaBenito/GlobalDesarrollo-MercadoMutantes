package com.example.GlobalMutantes.service;

import com.example.GlobalMutantes.model.ADN;
import com.example.GlobalMutantes.repository.ADNRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

public class ADNServiceTest {
    @Mock
    private ADNRepository adnRepository;

    @InjectMocks
    private ADNService adnService;

    // antes de cada test! :)
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    /*--------------------
    TEST 1:
    Comprobamos que la función isMutant del servicio ADNService devuelve true
    al pasarle una secuencia de ADN mutante
    --------------------*/
    @Test
    public void testIsMutant_ADNMutante() {
        String[] adn = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        boolean resultado = adnService.isMutant(adn);
        assertEquals(true, resultado, "Debería detectar ADN mutante (true)");
    }

    /*--------------------
    TEST 2:
    Comprobamos que la función isMutant del servicio ADNService devuelve false
    al pasarle una secuencia de ADN humano
    --------------------*/
    @Test
    public void testIsMutant_ADNHumano() {
        String[] adn = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGACGG",
                "CCTCTA",
                "TCACTG"
        };
        boolean resultado = adnService.isMutant(adn);
        assertEquals(false, resultado, "Debería detectar ADN humano (false)");
    }

    /*--------------------
    TEST 3:
    Comprobamos que la función isMutant puede detectar secuencias de ADN Mutante horizontalmente
    --------------------*/
    @Test
    public void testIsMutant_SecuenciaHorizontal() {
        String[] adn = {
                "AAAAAA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        boolean resultado = adnService.isMutant(adn);
        assertEquals(true, resultado, "Debería detectar ADN mutante (true) de forma horizontal");
    }

    /*--------------------
    TEST 4:
    Comprobamos que la función isMutant puede detectar secuencias de ADN Mutante verticalmente
    --------------------*/
    @Test
    public void testIsMutant_SecuenciaVertical() {
        String[] adn = {
                "ATGCGA",
                "AAGTGC",
                "ATATGT",
                "AGAAGG",
                "ACCCCT",
                "ATCACT"
        };
        boolean resultado = adnService.isMutant(adn);
        assertEquals(true, resultado, "Debería detectar ADN mutante (true) de forma vertical");
    }

    /*--------------------
    TEST 5:
    Comprobamos que la función isMutant puede detectar secuencias de ADN Mutante diagonalmente
    de izquierda a derecha
    --------------------*/
    @Test
    public void testIsMutant_SecuenciaDiagonalIzqDer() {
        String[] adn = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        boolean resultado = adnService.isMutant(adn);
        assertEquals(true, resultado, "Debería detectar ADN mutante (true) de forma diagonal de izq a derecha");
    }

    /*--------------------
    TEST 6:
    idem test 5 pero de derecha a izquierda (me cansé de escribir comentarios)
    --------------------*/
    @Test
    public void testIsMutant_SecuenciaDiagonalDerIzq() {
        String[] adn = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        boolean resultado = adnService.isMutant(adn);
        assertEquals(true, resultado, "Debería detectar ADN mutante (true) de forma diagonal de derecha a izq");
    }

    /*--------------------
    TEST 7:
    Comprobamos que funciona la función saveADN para guardar ADN en la base de datos
    --------------------*/
    @Test
    public void testSaveADN() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        boolean isMutant = false;
        adnService.saveADN(String.join(",", dna), isMutant);
        ArgumentCaptor<ADN> adnCaptor = ArgumentCaptor.forClass(ADN.class);
        verify(adnRepository).save(adnCaptor.capture());
        ADN capturedADN = adnCaptor.getValue();
        assertEquals(String.join(",", dna), capturedADN.getSecuencia());
        assertEquals(isMutant, capturedADN.isEsMutante());
    }

    /*--------------------
    TEST 8:
    Comprobamos que la función isValid del ADNValidator funciona correctamente pasandole un ADN incorrecto
    (adn matriz NxM en lugar de NxN)
    --------------------*/
    @Test
    public void testIsMutant_InvalidDNA() {
        String[] dna = {"ATGCGA","CAGTGC"};
        assertThrows(IllegalArgumentException.class, () -> adnService.isMutant(dna));
    }

    /*--------------------
    TEST 9:
    Comprobamos que la función isValid del ADNValidator funciona correctamente pasandole un ADN incorrecto
    (símbolos erroneos)
    --------------------*/
    @Test
    public void testIsMutant_InvalidCharacters() {
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTGX"};
        assertThrows(IllegalArgumentException.class, () -> adnService.isMutant(dna));
    }
}
