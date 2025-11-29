package global_mutantes.controllers;

import global_mutantes.Servicios.ServicioMutante;
import global_mutantes.dtos.DnaRequest;
import global_mutantes.dtos.DnaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/mutant")
public class MutantController {

    private final ServicioMutante servicioMutante;

    public MutantController(ServicioMutante servicioMutante) {
        this.servicioMutante = servicioMutante;
    }

    @PostMapping
    @Operation(
            summary = "Detectar si un ADN corresponde a un mutante",
            description = """
                    Analiza una secuencia de ADN y determina si pertenece a un mutante. 
                    Un individuo es considerado mutante cuando se encuentran más de una 
                    secuencia de cuatro letras idénticas de forma consecutiva en sentido 
                    horizontal, vertical u oblicuo (diagonal).
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Es mutante",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DnaResponse.class),
                            examples = @ExampleObject(value = "{\"mutant\": true}")
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Es humano",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DnaResponse.class),
                            examples = @ExampleObject(value = "{\"mutant\": false}")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            )
    })
    public ResponseEntity<DnaResponse> checkMutant(@Valid @RequestBody DnaRequest dnaRequest) {
        boolean esMutante = servicioMutante.analyzeDna(dnaRequest.getDna());

        DnaResponse respuesta = construirRespuesta(esMutante);

        return determinarEstadoRespuesta(esMutante, respuesta);
    }

    private DnaResponse construirRespuesta(boolean esMutante) {
        return new DnaResponse(esMutante);
    }

    private ResponseEntity<DnaResponse> determinarEstadoRespuesta(boolean esMutante, DnaResponse respuesta) {
        if (esMutante) {
            return ResponseEntity.ok(respuesta);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(respuesta);
    }
}