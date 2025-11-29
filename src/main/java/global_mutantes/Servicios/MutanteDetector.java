package global_mutantes.Servicios;

import global_mutantes.Entidades.DNARecord;
import global_mutantes.Repositorio.DNARecordRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MutanteDetector {

    private final DNARecordRepositorio dnaRecordRepository;
    private static final int LARGO_SECUENCIA = 4;

    @Autowired
    public MutanteDetector(DNARecordRepositorio dnaRecordRepository) {
        this.dnaRecordRepository = dnaRecordRepository;
    }

    /**
     * Determina si el genoma proporcionado pertenece a un mutante.
     */
    public static boolean isMutant(String[] dna) {
        int dimensionMatriz = dna.length;
        int contadorSecuencias = 0;

        contadorSecuencias += verificarFilas(dna, dimensionMatriz);
        if (contadorSecuencias > 1) return true;

        contadorSecuencias += verificarColumnas(dna, dimensionMatriz);
        if (contadorSecuencias > 1) return true;

        contadorSecuencias += verificarDiagonales(dna, dimensionMatriz);
        return contadorSecuencias > 1;
    }

    /**
     * Verifica secuencias genéticas en las filas del genoma.
     */
    private static int verificarFilas(String[] dna, int dimension) {
        int secuenciasEncontradas = 0;

        for (int fila = 0; fila < dimension; fila++) {
            int contadorConsecutivos = 1;

            for (int columna = 1; columna < dimension; columna++) {
                if (dna[fila].charAt(columna) == dna[fila].charAt(columna - 1)) {
                    contadorConsecutivos++;

                    if (contadorConsecutivos == LARGO_SECUENCIA) {
                        secuenciasEncontradas++;
                        if (secuenciasEncontradas > 1) return secuenciasEncontradas;
                    }
                } else {
                    contadorConsecutivos = 1;
                }
            }
        }
        return secuenciasEncontradas;
    }

    private static int verificarColumnas(String[] dna, int dimension) {
        int secuenciasEncontradas = 0;

        for (int columna = 0; columna < dimension; columna++) {
            int contadorConsecutivos = 1;

            for (int fila = 1; fila < dimension; fila++) {
                if (dna[fila].charAt(columna) == dna[fila - 1].charAt(columna)) {
                    contadorConsecutivos++;

                    if (contadorConsecutivos == LARGO_SECUENCIA) {
                        secuenciasEncontradas++;
                        if (secuenciasEncontradas > 1) return secuenciasEncontradas;
                    }
                } else {
                    contadorConsecutivos = 1;
                }
            }
        }
        return secuenciasEncontradas;
    }

    private static int verificarDiagonales(String[] dna, int dimension) {
        int secuenciasEncontradas = 0;

        // Diagonales descendentes (\)
        for (int fila = 0; fila <= dimension - LARGO_SECUENCIA; fila++) {
            for (int columna = 0; columna <= dimension - LARGO_SECUENCIA; columna++) {
                if (verificarDiagonalEspecifica(dna, fila, columna, 1, 1, dimension)) {
                    secuenciasEncontradas++;
                    if (secuenciasEncontradas > 1) return secuenciasEncontradas;
                }
            }
        }

        // Diagonales ascendentes (/)
        for (int fila = 0; fila <= dimension - LARGO_SECUENCIA; fila++) {
            for (int columna = LARGO_SECUENCIA - 1; columna < dimension; columna++) {
                if (verificarDiagonalEspecifica(dna, fila, columna, 1, -1, dimension)) {
                    secuenciasEncontradas++;
                    if (secuenciasEncontradas > 1) return secuenciasEncontradas;
                }
            }
        }

        return secuenciasEncontradas;
    }

    private static boolean verificarDiagonalEspecifica(String[] dna, int posX, int posY,
                                                       int incrementoX, int incrementoY, int dimension) {
        char caracterInicial = dna[posX].charAt(posY);

        for (int paso = 1; paso < LARGO_SECUENCIA; paso++) {
            int nuevaX = posX + paso * incrementoX;
            int nuevaY = posY + paso * incrementoY;

            if (nuevaX >= dimension || nuevaY >= dimension || nuevaY < 0 ||
                    dna[nuevaX].charAt(nuevaY) != caracterInicial) {
                return false;
            }
        }

        return true;
    }

    public boolean analyzeDna(String[] dna) {
        String secuenciaAdn = String.join(",", dna);

        Optional<DNARecord> registroExistente = dnaRecordRepository.findByDna(secuenciaAdn);

        if (registroExistente.isPresent()) {
            return registroExistente.get().isMutant();
        }

        boolean esMutante = isMutant(dna);

        DNARecord nuevoRegistro = DNARecord.builder()
                .dna(secuenciaAdn)
                .isMutant(esMutante)
                .build();

        dnaRecordRepository.save(nuevoRegistro);

        return esMutante;
    }
}