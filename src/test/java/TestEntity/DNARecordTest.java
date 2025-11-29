package TestEntity;

import global_mutantes.Entidades.DNARecord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DNARecordTest {

    @Test
    void testDNARecordNoArgsConstructor() {
        DNARecord record = new DNARecord();

        assertNotNull(record);
        assertNull(record.getId());
        assertNull(record.getDna());
        assertFalse(record.isMutant());
    }

    @Test
    void testDNARecordAllArgsConstructor() {
        DNARecord record = new DNARecord(1L, "hash123", true);

        assertEquals(1L, record.getId());
        assertEquals("hash123", record.getDna());
        assertTrue(record.isMutant());
    }

    @Test
    void testDNARecordTwoArgsConstructor() {
        DNARecord record = new DNARecord("hash456", false);

        assertNull(record.getId());
        assertEquals("hash456", record.getDna());
        assertFalse(record.isMutant());
    }

    @Test
    void testDNARecordBuilder() {
        DNARecord record = DNARecord.builder()
                .id(10L)
                .dna("hashABC")
                .isMutant(true)
                .build();

        assertEquals(10L, record.getId());
        assertEquals("hashABC", record.getDna());
        assertTrue(record.isMutant());
    }

    @Test
    void testDNARecordSetters() {
        DNARecord record = new DNARecord();

        record.setId(5L);
        record.setDna("newHash");
        record.setMutant(true);

        assertEquals(5L, record.getId());
        assertEquals("newHash", record.getDna());
        assertTrue(record.isMutant());
    }

    @Test
    void testDNARecordGetters() {
        DNARecord record = DNARecord.builder()
                .id(20L)
                .dna("testHash")
                .isMutant(false)
                .build();

        assertEquals(20L, record.getId());
        assertEquals("testHash", record.getDna());
        assertFalse(record.isMutant());
    }

    @Test
    void testDNARecordMutantStatusChange() {
        DNARecord record = new DNARecord("hash", false);

        assertFalse(record.isMutant());

        record.setMutant(true);
        assertTrue(record.isMutant());
    }

    @Test
    void testDNARecordWithNullDna() {
        DNARecord record = new DNARecord(null, true);

        assertNull(record.getDna());
        assertTrue(record.isMutant());
    }

    @Test
    void testDNARecordBuilderPartial() {
        DNARecord record = DNARecord.builder()
                .dna("partialHash")
                .build();

        assertNull(record.getId());
        assertEquals("partialHash", record.getDna());
        assertFalse(record.isMutant());
    }

    @Test
    void testDNARecordLongHash() {
        String longHash = "a".repeat(64);
        DNARecord record = new DNARecord(longHash, true);

        assertEquals(64, record.getDna().length());
        assertEquals(longHash, record.getDna());
    }

    @Test
    void testDNARecordMultipleUpdates() {
        DNARecord record = new DNARecord("initial", false);

        record.setDna("updated1");
        assertEquals("updated1", record.getDna());

        record.setDna("updated2");
        assertEquals("updated2", record.getDna());

        record.setMutant(true);
        assertTrue(record.isMutant());
    }

    @Test
    void testDNARecordIdAutoGeneration() {
        DNARecord record = new DNARecord("hash", true);

        assertNull(record.getId()); // ID should be null before persistence
    }

    @Test
    void testDNARecordEquality() {
        DNARecord record1 = DNARecord.builder()
                .id(1L)
                .dna("sameHash")
                .isMutant(true)
                .build();

        DNARecord record2 = DNARecord.builder()
                .id(1L)
                .dna("sameHash")
                .isMutant(true)
                .build();

        assertEquals(record1.getId(), record2.getId());
        assertEquals(record1.getDna(), record2.getDna());
        assertEquals(record1.isMutant(), record2.isMutant());
    }

    @Test
    void testDNARecordDifferentHashes() {
        DNARecord record1 = new DNARecord("hash1", true);
        DNARecord record2 = new DNARecord("hash2", true);

        assertNotEquals(record1.getDna(), record2.getDna());
    }

    @Test
    void testDNARecordBuilderWithAllFields() {
        DNARecord record = DNARecord.builder()
                .id(100L)
                .dna("completeHash123")
                .isMutant(false)
                .build();

        assertEquals(100L, record.getId());
        assertEquals("completeHash123", record.getDna());
        assertFalse(record.isMutant());
    }
}