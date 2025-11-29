package TestController;

import global_mutantes.Servicios.ServicioMutante;  // ⬅️ Cambio importante
import global_mutantes.controllers.MutantController;
import global_mutantes.dtos.DnaRequest;
import global_mutantes.dtos.DnaResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class MutanteControllerTest {

    @Mock
    private ServicioMutante servicioMutante;  // ⬅️ Cambio importante

    @InjectMocks
    private MutantController mutantController;

    @Test
    void whenDnaIsMutant_returnsOkStatus() {
        // Arrange
        String[] secuenciaMutante = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        DnaRequest solicitud = crearSolicitudAdn(secuenciaMutante);

        configurarDetectorMutante(true);

        // Act
        ResponseEntity<DnaResponse> respuesta = mutantController.checkMutant(solicitud);

        // Assert
        verificarRespuestaMutante(respuesta);
        verificarLlamadaDetector(secuenciaMutante, 1);
    }

    @Test
    void whenDnaIsHuman_returnsForbiddenStatus() {
        // Arrange
        String[] secuenciaHumana = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        DnaRequest solicitud = crearSolicitudAdn(secuenciaHumana);

        configurarDetectorMutante(false);

        // Act
        ResponseEntity<DnaResponse> respuesta = mutantController.checkMutant(solicitud);

        // Assert
        verificarRespuestaHumana(respuesta);
        verificarLlamadaDetector(secuenciaHumana, 1);
    }

    @Test
    void whenMultipleMutantChecks_callsServiceMultipleTimes() {
        // Arrange
        String[] primerAdn = {"AAAA", "CCCC", "TCAG", "GGTC"};
        String[] segundoAdn = {"ATGC", "ATGC", "ATGC", "ATGC"};

        DnaRequest primeraSolicitud = crearSolicitudAdn(primerAdn);
        DnaRequest segundaSolicitud = crearSolicitudAdn(segundoAdn);

        configurarDetectorParaSecuencias(primerAdn, segundoAdn);

        // Act
        mutantController.checkMutant(primeraSolicitud);
        mutantController.checkMutant(segundaSolicitud);

        // Assert
        verificarLlamadasMultiples(primerAdn, segundoAdn);
    }

    private DnaRequest crearSolicitudAdn(String[] secuencia) {
        DnaRequest solicitud = new DnaRequest();
        solicitud.setDna(secuencia);
        return solicitud;
    }

    private void configurarDetectorMutante(boolean esMutante) {
        when(servicioMutante.analyzeDna(any(String[].class))).thenReturn(esMutante);  // ⬅️ Cambio
    }

    private void configurarDetectorParaSecuencias(String[] primerAdn, String[] segundoAdn) {
        when(servicioMutante.analyzeDna(primerAdn)).thenReturn(true);  // ⬅️ Cambio
        when(servicioMutante.analyzeDna(segundoAdn)).thenReturn(true);  // ⬅️ Cambio
    }

    private void verificarRespuestaMutante(ResponseEntity<DnaResponse> respuesta) {
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertTrue(respuesta.getBody().isMutant());
    }

    private void verificarRespuestaHumana(ResponseEntity<DnaResponse> respuesta) {
        assertEquals(HttpStatus.FORBIDDEN, respuesta.getStatusCode());
        assertFalse(respuesta.getBody().isMutant());
    }

    private void verificarLlamadaDetector(String[] secuencia, int vecesEsperadas) {
        verify(servicioMutante, times(vecesEsperadas)).analyzeDna(secuencia);  // ⬅️ Cambio
    }

    private void verificarLlamadasMultiples(String[] primerAdn, String[] segundoAdn) {
        verify(servicioMutante, times(1)).analyzeDna(primerAdn);  // ⬅️ Cambio
        verify(servicioMutante, times(1)).analyzeDna(segundoAdn);  // ⬅️ Cambio
    }
}