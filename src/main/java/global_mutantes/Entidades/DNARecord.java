package global_mutantes.Entidades;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "dna_records", indexes = {
        @Index(name = "idx_dna_hash", columnList = "dna", unique = true)
})
public class DNARecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 64)
    private String dna;  // Este es el hash del ADN

    @Column(nullable = false)
    private boolean isMutant;

    public DNARecord(String dnaHash, boolean isMutant) {
        this.dna = dnaHash;
        this.isMutant = isMutant;
    }
}
