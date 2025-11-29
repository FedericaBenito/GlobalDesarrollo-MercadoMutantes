package global_mutantes.Servicios;

import global_mutantes.Repositorio.DNARecordRepositorio;
import global_mutantes.dtos.StatsResponse;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private final DNARecordRepositorio repository;

    public StatsService(DNARecordRepositorio repository) {
        this.repository = repository;
    }

    public StatsResponse getStats() {
        long countMutant = repository.countMutants();
        long countHuman = repository.countHumans();

        double ratio = countHuman == 0 ?
                (countMutant > 0 ? 1.0 : 0.0) :
                Math.round((double) countMutant / countHuman * 100.0) / 100.0;  // Redondear a 2 decimales

        return new StatsResponse(countMutant, countHuman, ratio);
    }
}