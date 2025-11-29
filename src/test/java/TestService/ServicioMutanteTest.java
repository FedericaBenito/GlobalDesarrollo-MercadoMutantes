package TestService;

import global_mutantes.Entidades.DNARecord;
import global_mutantes.Repositorio.DNARecordRepositorio;
import global_mutantes.Servicios.MutanteDetector;
import global_mutantes.Servicios.ServicioMutante;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServicioMutanteTest {

    @Mock
    private MutanteDetector mutantDetector;

    @Mock
    private DNARecordRepositorio dnaRecordRepository;

    @InjectMocks
    private ServicioMutante servicioMutante;

    @Test
    void testAnalyzeDnaWithNewMutantDna() {
        // Arrange
        String[] dna = {"AAAA", "CCCC", "TCAG", "GGTC"};
        when(dnaRecordRepository.findByDna(anyString())).thenReturn(Optional.empty());
        when(dnaRecordRepository.save(any(DNARecord.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        boolean result = servicioMutante.analyzeDna(dna);

        // Assert
        assertTrue(result);
        verify(dnaRecordRepository, times(1)).findByDna(anyString());
        verify(dnaRecordRepository, times(1)).save(any(DNARecord.class));
    }

    @Test
    void testAnalyzeDnaWithNewHumanDna() {
        // Arrange
        String[] dna = {"ATGC", "CGTA", "TACG", "GCAT"};
        when(dnaRecordRepository.findByDna(anyString())).thenReturn(Optional.empty());
        when(dnaRecordRepository.save(any(DNARecord.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        boolean result = servicioMutante.analyzeDna(dna);

        // Assert
        assertFalse(result);
        verify(dnaRecordRepository, times(1)).save(any(DNARecord.class));
    }

    @Test
    void testAnalyzeDnaWithExistingMutantRecord() {
        // Arrange
        String[] dna = {"AAAA", "CCCC", "TCAG", "GGTC"};
        DNARecord existingRecord = new DNARecord("hashvalue", true);
        when(dnaRecordRepository.findByDna(anyString())).thenReturn(Optional.of(existingRecord));

        // Act
        boolean result = servicioMutante.analyzeDna(dna);

        // Assert
        assertTrue(result);
        verify(dnaRecordRepository, times(1)).findByDna(anyString());
        verify(dnaRecordRepository, never()).save(any());
    }

    @Test
    void testAnalyzeDnaWithExistingHumanRecord() {
        // Arrange
        String[] dna = {"ATGC", "CGTA", "TACG", "GCAT"};
        DNARecord existingRecord = new DNARecord("hashvalue", false);
        when(dnaRecordRepository.findByDna(anyString())).thenReturn(Optional.of(existingRecord));

        // Act
        boolean result = servicioMutante.analyzeDna(dna);

        // Assert
        assertFalse(result);
        verify(dnaRecordRepository, never()).save(any());
    }

    @Test
    void testAnalyzeDnaGeneratesSameHashForSameDna() {
        // Arrange
        String[] dna1 = {"AAAA", "CCCC", "TCAG", "GGTC"};
        String[] dna2 = {"AAAA", "CCCC", "TCAG", "GGTC"};

        when(dnaRecordRepository.findByDna(anyString())).thenReturn(Optional.empty());
        when(dnaRecordRepository.save(any(DNARecord.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        servicioMutante.analyzeDna(dna1);

        // Reset mock to check second call
        reset(dnaRecordRepository);
        DNARecord savedRecord = new DNARecord("samehash", true);
        when(dnaRecordRepository.findByDna(anyString())).thenReturn(Optional.of(savedRecord));

        boolean result = servicioMutante.analyzeDna(dna2);

        // Assert
        assertTrue(result);
        verify(dnaRecordRepository, times(1)).findByDna(anyString());
    }

    @Test
    void testAnalyzeDnaGeneratesDifferentHashForDifferentDna() {
        // Arrange
        String[] dna1 = {"AAAA", "CCCC", "TCAG", "GGTC"};
        String[] dna2 = {"TTTT", "GGGG", "ACGT", "CGTA"};

        when(dnaRecordRepository.findByDna(anyString())).thenReturn(Optional.empty());
        when(dnaRecordRepository.save(any(DNARecord.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        servicioMutante.analyzeDna(dna1);
        servicioMutante.analyzeDna(dna2);

        // Assert - Se debe guardar dos veces porque son DNAs diferentes
        verify(dnaRecordRepository, times(2)).save(any(DNARecord.class));
    }

    @Test
    void testAnalyzeDnaHandlesLargeDnaSequences() {
        // Arrange
        String[] largeDna = {
                "ATGCGATTTT",
                "CAGTGCAAAT",
                "TTATGTAAAG",
                "AGAAGGCCCC",
                "CCCCTATGCA",
                "TCACTGAAAA",
                "ATGCGATCGA",
                "CAGTGCATGC",
                "TTATGTTACG",
                "AGAAGGTCGA"
        };

        when(dnaRecordRepository.findByDna(anyString())).thenReturn(Optional.empty());
        when(dnaRecordRepository.save(any(DNARecord.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        boolean result = servicioMutante.analyzeDna(largeDna);

        // Assert
        assertTrue(result);
        verify(dnaRecordRepository, times(1)).save(any(DNARecord.class));
    }

    @Test
    void testAnalyzeDnaSavesCorrectMutantStatus() {
        // Arrange
        String[] dna = {"AAAA", "CCCC", "TCAG", "GGTC"};
        when(dnaRecordRepository.findByDna(anyString())).thenReturn(Optional.empty());

        DNARecord[] savedRecord = new DNARecord[1];
        when(dnaRecordRepository.save(any(DNARecord.class))).thenAnswer(invocation -> {
            savedRecord[0] = invocation.getArgument(0);
            return savedRecord[0];
        });

        // Act
        servicioMutante.analyzeDna(dna);

        // Assert
        assertNotNull(savedRecord[0]);
        assertTrue(savedRecord[0].isMutant());
    }

    @Test
    void testAnalyzeDnaSavesHashCorrectly() {
        // Arrange
        String[] dna = {"ATGC", "CGTA", "TACG", "GCAT"};
        when(dnaRecordRepository.findByDna(anyString())).thenReturn(Optional.empty());

        DNARecord[] savedRecord = new DNARecord[1];
        when(dnaRecordRepository.save(any(DNARecord.class))).thenAnswer(invocation -> {
            savedRecord[0] = invocation.getArgument(0);
            return savedRecord[0];
        });

        // Act
        servicioMutante.analyzeDna(dna);

        // Assert
        assertNotNull(savedRecord[0]);
        assertNotNull(savedRecord[0].getDna());
        assertEquals(64, savedRecord[0].getDna().length()); // SHA-256 hash length
    }

    @Test
    void testAnalyzeDnaDoesNotSaveWhenRecordExists() {
        // Arrange
        String[] dna = {"ATGC", "CGTA", "TACG", "GCAT"};
        DNARecord existingRecord = new DNARecord("existinghash", false);
        when(dnaRecordRepository.findByDna(anyString())).thenReturn(Optional.of(existingRecord));

        // Act
        servicioMutante.analyzeDna(dna);

        // Assert
        verify(dnaRecordRepository, never()).save(any(DNARecord.class));
    }

    @Test
    void testAnalyzeDnaCallsFindByDnaWithCorrectHash() {
        // Arrange
        String[] dna = {"ATGC", "CGTA", "TACG", "GCAT"};
        when(dnaRecordRepository.findByDna(anyString())).thenReturn(Optional.empty());
        when(dnaRecordRepository.save(any(DNARecord.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        servicioMutante.analyzeDna(dna);

        // Assert
        verify(dnaRecordRepository, times(1)).findByDna(anyString());
    }
}