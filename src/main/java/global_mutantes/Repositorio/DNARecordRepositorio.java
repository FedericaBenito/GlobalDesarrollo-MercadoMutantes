package GLOBAL_MUTANTES.Repositorio;


import GLOBAL_MUTANTES.Entidades.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DNARecordRepositorio extends JpaRepository<DNARecord, Long>{
    Optional<DNARecord> findByDna(String dnaSequence);

    long countByIsMutant(boolean isMutant);
}