package GLOBAL_MUTANTES.dtos;

import GLOBAL_MUTANTES.Validaciones.ValidacionDNASecuencia;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Para verificar si un ADN es mutante")
public class DnaRequest {
    @Schema(
            description = "Secuencia de ADN representada como matriz cuadrada de Strings",
            example = "[\"GAGTGA\",\"CAGTGC\",\"TTTTGT\",\"AGBAGG\",\"CCCCTA\",\"TCAATG\"]",
            required = true
    )

    @ValidacionDNASecuencia
    private String[] dna;
}
