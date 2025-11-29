package global_mutantes.Repositorio;

import global_mutantes.Entidades.DNARecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DNARecordRepositorio extends JpaRepository<DNARecord, Long> {

    Optional<DNARecord> findByDna(String dnaHash);

    @Query("SELECT COUNT(d) FROM DNARecord d WHERE d.isMutant = true")
    long countMutants();

    @Query("SELECT COUNT(d) FROM DNARecord d WHERE d.isMutant = false")
    long countHumans();
}