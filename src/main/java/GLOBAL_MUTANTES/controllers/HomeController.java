package GLOBAL_MUTANTES.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    private static final String ESTADO_SERVICIO = "online";
    private static final String MENSAJE_BIENVENIDA = "Servicio de análisis genético de mutantes";

    @GetMapping("/")
    @Hidden
    public Map<String, Object> home() {
        Map<String, Object> respuesta = construirRespuestaInicial();
        respuesta.put("endpoints", construirMapaEndpoints());
        return respuesta;
    }

    private Map<String, Object> construirRespuestaInicial() {
        Map<String, Object> informacionBase = new HashMap<>();
        informacionBase.put("status", ESTADO_SERVICIO);
        informacionBase.put("message", MENSAJE_BIENVENIDA);
        return informacionBase;
    }

    private Map<String, String> construirMapaEndpoints() {
        Map<String, String> puntosAcceso = new HashMap<>();

        puntosAcceso.put("verificar_mutante",
                "POST /mutant — Verifica si el ADN corresponde a un mutante");

        puntosAcceso.put("estadisticas",
                "GET /stats — Muestra estadísticas de validaciones");

        puntosAcceso.put("documentacion",
                "GET /swagger-ui/index.html — Documentación interactiva");

        return puntosAcceso;
    }
}