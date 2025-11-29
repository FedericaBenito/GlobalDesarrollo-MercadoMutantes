package global_mutantes.Servicios;

import global_mutantes.Entidades.DNARecord;
import global_mutantes.Excepcion.DnaHashCalculationException;
import global_mutantes.Repositorio.DNARecordRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicioMutante {

    private final MutanteDetector mutantDetector;
    private final DNARecordRepositorio dnaRecordRepository;

    public boolean analyzeDna(String[] dna) {
        String hashedDna = generarHashAdn(dna);

        Optional<DNARecord> registroExistente = dnaRecordRepository.findByDna(hashedDna);

        if (registroExistente.isPresent()) {
            return registroExistente.get().isMutant();
        }

        boolean esMutante = mutantDetector.isMutant(dna);

        DNARecord nuevoRegistro = new DNARecord(hashedDna, esMutante);
        dnaRecordRepository.save(nuevoRegistro);

        return esMutante;
    }

    private String generarHashAdn(String[] dna) {
        try {
            MessageDigest algoritmoHash = MessageDigest.getInstance("SHA-256");

            String secuenciaCompleta = String.join("", dna);

            byte[] bytesHash = algoritmoHash.digest(secuenciaCompleta.getBytes(StandardCharsets.UTF_8));

            return convertirBytesAHexadecimal(bytesHash);
        } catch (NoSuchAlgorithmException ex) {
            throw new DnaHashCalculationException("Error al calcular hash SHA-256", ex);
        }
    }

    private String convertirBytesAHexadecimal(byte[] bytes) {
        StringBuilder cadenaHex = new StringBuilder();

        for (byte byteActual : bytes) {
            String valorHex = Integer.toHexString(0xff & byteActual);

            if (valorHex.length() == 1) {
                cadenaHex.append('0');
            }

            cadenaHex.append(valorHex);
        }

        return cadenaHex.toString();
    }
}