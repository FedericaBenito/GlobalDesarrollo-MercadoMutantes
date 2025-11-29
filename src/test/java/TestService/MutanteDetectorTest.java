package TestService;


import GLOBAL_MUTANTES.Servicios.MutanteDetector;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MutanteDetectorTest {

    @Test
    public void testMutantWithHorizontalSequence() {
        String[] dna = {
                "AAAAGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(MutanteDetector.isMutant(dna));
    }

    @Test
    public void testMutantWithVerticalSequence() {
        String[] dna = {
                "AGAATG",
                "AGCAGT",
                "AGTTCC",
                "AGTCTC",
                "GTAGTC",
                "GGTCAC"
        };
        assertTrue(MutanteDetector.isMutant(dna));
    }

    @Test
    public void testMutantWithDiagonalSequence() {
        String[] dna = {
                "AGAATG",
                "TACAGT",
                "GCAGCC",
                "TTGATG",
                "GTAGTC",
                "AGTCAA"
        };
        assertTrue(MutanteDetector.isMutant(dna));
    }

    @Test
    public void testNonMutantDna() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        assertFalse(MutanteDetector.isMutant(dna));
    }

    @Test
    public void testMutantWithMultipleSequences() {
        String[] dna = {
                "AAAA",
                "CCCC",
                "TCAG",
                "GGTC"
        };
        assertTrue(MutanteDetector.isMutant(dna));
    }

    @Test
    public void testNonMutantSmallMatrix() {
        String[] dna = {
                "AAAT",
                "AACC",
                "AAAC",
                "CGGG"
        };
        assertFalse(MutanteDetector.isMutant(dna));
    }

    @Test
    public void testMutantWithComplexPattern() {
        String[] dna = {
                "TCGGGTGAT",
                "TGATCCTTT",
                "TACGAGTGA",
                "AAATGTACG",
                "ACGAGTGCT",
                "AGACACATG",
                "GAATTCCAA",
                "ACTACGACC",
                "TGAGTATCC"
        };
        assertTrue(MutanteDetector.isMutant(dna));
    }

    @Test
    public void testMutantWithDiagonalInverseSequence() {
        String[] dna = {
                "ATGCGA",
                "CAGTAC",
                "TTATAT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(MutanteDetector.isMutant(dna));
    }

    @Test
    public void testNonMutantWithOnlyThreeConsecutive() {
        String[] dna = {
                "AAATGA",
                "CAGTGC",
                "TTATTT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        assertFalse(MutanteDetector.isMutant(dna));
    }

    @Test
    public void testMutantWithExactlyTwoSequences() {
        String[] dna = {
                "AAAAGA",
                "CAGTGC",
                "TTTTGT",
                "AGAAGG",
                "CCTCTA",
                "TCACTG"
        };
        assertTrue(MutanteDetector.isMutant(dna));
    }

    @Test
    public void testNonMutantAlternatingPattern() {
        String[] dna = {
                "ATATATAT",
                "TATATATA",
                "ATATATAT",
                "TATATATA",
                "ATATATAT",
                "TATATATA",
                "ATATATAT",
                "TATATATA"
        };
        assertTrue(MutanteDetector.isMutant(dna));
    }

    @Test
    public void testMutantWithVerticalAndHorizontal() {
        String[] dna = {
                "AAAATG",
                "TGCAGT",
                "GCTTCC",
                "CCCCTC",
                "GTAGTC",
                "AGTCAC"
        };
        assertTrue(MutanteDetector.isMutant(dna));
    }

    @Test
    public void testMutantMinimumSize4x4() {
        String[] dna = {
                "AAAA",
                "TGCA",
                "GCTA",
                "TTTT"
        };
        assertTrue(MutanteDetector.isMutant(dna));
    }

    @Test
    public void testNonMutantAllDifferent() {
        String[] dna = {
                "ATGC",
                "CGTA",
                "TACG",
                "GCAT"
        };
        assertFalse(MutanteDetector.isMutant(dna));
    }

    @Test
    public void testMutantWithDiagonalsOnly() {
        String[] dna = {
                "ATGCGA",
                "CAGTAC",
                "TTATGT",
                "AGAAGG",
                "CCTCTA",
                "TCACTG"
        };
        assertFalse(MutanteDetector.isMutant(dna));
    }

    @Test
    public void testMutantLargeMatrix10x10() {
        String[] dna = {
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
        assertTrue(MutanteDetector.isMutant(dna));
    }

    @Test
    public void testNonMutantNearMiss() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGACGG",
                "CCGTCA",
                "TCACTG"
        };
        assertFalse(MutanteDetector.isMutant(dna));
    }

    @Test
    public void testMutantWithOverlappingSequences() {
        String[] dna = {
                "AAAAAA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCCC",
                "TCACTG"
        };
        assertTrue(MutanteDetector.isMutant(dna));
    }

    @Test
    public void testMutantWithAllSameBase() {
        String[] dna = {
                "AAAA",
                "AAAA",
                "AAAA",
                "AAAA"
        };
        assertTrue(MutanteDetector.isMutant(dna));
    }
}