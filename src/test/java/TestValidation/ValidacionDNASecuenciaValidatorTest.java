package TestValidation;

import global_mutantes.Validaciones.ValidacionDNASecuenciaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidacionDNASecuenciaValidatorTest {

    private ValidacionDNASecuenciaValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ValidacionDNASecuenciaValidator();
    }

    @Test
    void testValidDnaSequence() {
        // Arrange
        String[] validDna = {"ATGC", "CGTA", "TACG", "GCAT"};

        // Act
        boolean result = validator.isValid(validDna, null);

        // Assert
        assertTrue(result);
    }

    @Test
    void testNullDnaSequence() {
        // Act
        boolean result = validator.isValid(null, null);

        // Assert
        assertFalse(result);
    }

    @Test
    void testEmptyDnaSequence() {
        // Arrange
        String[] emptyDna = {};

        // Act
        boolean result = validator.isValid(emptyDna, null);

        // Assert
        assertFalse(result);
    }

    @Test
    void testInvalidCharactersInDna() {
        // Arrange
        String[] invalidDna = {"ATGC", "CGXA", "TACG", "GCAT"};

        // Act
        boolean result = validator.isValid(invalidDna, null);

        // Assert
        assertFalse(result);
    }

    @Test
    void testNonSquareMatrix() {
        // Arrange
        String[] nonSquareDna = {"ATGC", "CGT", "TACG", "GCAT"};

        // Act
        boolean result = validator.isValid(nonSquareDna, null);

        // Assert
        assertFalse(result);
    }

    @Test
    void testNullRowInDna() {
        // Arrange
        String[] dnaWithNull = {"ATGC", null, "TACG", "GCAT"};

        // Act
        boolean result = validator.isValid(dnaWithNull, null);

        // Assert
        assertFalse(result);
    }

    @Test
    void testDifferentRowLengths() {
        // Arrange
        String[] differentLengths = {"ATGC", "CGTA", "TACGAA", "GCAT"};

        // Act
        boolean result = validator.isValid(differentLengths, null);

        // Assert
        assertFalse(result);
    }

    @Test
    void testValidLargeDnaSequence() {
        // Arrange
        String[] largeDna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        // Act
        boolean result = validator.isValid(largeDna, null);

        // Assert
        assertTrue(result);
    }

    @Test
    void testMinimumValidSize() {
        // Arrange
        String[] minDna = {"A"};

        // Act
        boolean result = validator.isValid(minDna, null);

        // Assert
        assertTrue(result);
    }

    @Test
    void testLowercaseCharactersInvalid() {
        // Arrange
        String[] lowercaseDna = {"atgc", "cgta", "tacg", "gcat"};

        // Act
        boolean result = validator.isValid(lowercaseDna, null);

        // Assert
        assertFalse(result);
    }

    @Test
    void testNumbersInDnaInvalid() {
        // Arrange
        String[] dnaWithNumbers = {"AT1C", "CGTA", "TACG", "GCAT"};

        // Act
        boolean result = validator.isValid(dnaWithNumbers, null);

        // Assert
        assertFalse(result);
    }

    @Test
    void testSpecialCharactersInvalid() {
        // Arrange
        String[] dnaWithSpecial = {"AT-C", "CGTA", "TACG", "GCAT"};

        // Act
        boolean result = validator.isValid(dnaWithSpecial, null);

        // Assert
        assertFalse(result);
    }

    @Test
    void testOnlyValidCharacters() {
        // Arrange
        String[] onlyValidChars = {"AAAA", "TTTT", "CCCC", "GGGG"};

        // Act
        boolean result = validator.isValid(onlyValidChars, null);

        // Assert
        assertTrue(result);
    }

    @Test
    void testEmptyStringInRow() {
        // Arrange
        String[] dnaWithEmpty = {"", "", "", ""};

        // Act
        boolean result = validator.isValid(dnaWithEmpty, null);

        // Assert
        assertFalse(result);
    }

    @Test
    void testSingleCharacterValid() {
        // Arrange
        String[] singleChar = {"A"};

        // Act
        boolean result = validator.isValid(singleChar, null);

        // Assert
        assertTrue(result);
    }

    @Test
    void testTwoByTwoMatrix() {
        // Arrange
        String[] twoByTwo = {"AT", "CG"};

        // Act
        boolean result = validator.isValid(twoByTwo, null);

        // Assert
        assertTrue(result);
    }
}