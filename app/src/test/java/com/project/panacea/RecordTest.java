package com.project.panacea;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RecordTest {

    private Record record;

    @Before
    public void setUp() {
        record = new Record(70, 120, 80, "Normal check-up");
    }

    @Test
    //done
    public void testParameterizedConstructor() {
        assertNotNull("Record object should not be null", record);
        assertEquals("Heart rate should be 70", 70, record.heartRate);
        assertEquals("Systolic pressure should be 120", 120, record.systolicPressure);
        assertEquals("Diastolic pressure should be 80", 80, record.diastolicPressure);
        assertEquals("Comment should be 'Normal check-up'", "Normal check-up", record.comment);
    }

    @Test
    //done
    public void testDefaultConstructor() {
        Record defaultRecord = new Record();
        assertNotNull("Default Record object should not be null", defaultRecord);
        assertEquals("Default heart rate should be 0", 0, defaultRecord.heartRate);
        assertEquals("Default systolic pressure should be 0", 0, defaultRecord.systolicPressure);
        assertEquals("Default diastolic pressure should be 0", 0, defaultRecord.diastolicPressure);
        assertNull("Default comment should be null", defaultRecord.comment);
    }

    @Test
    //done
    public void testSetAndGetHeartRate() {
        record.heartRate = 75;
        assertEquals("Heart rate should be 75", 75, record.heartRate);
    }

    @Test
    //done
    public void testSetAndGetSystolicPressure() {
        record.systolicPressure = 130;
        assertEquals("Systolic pressure should be 130", 130, record.systolicPressure);
    }

    @Test
    //done
    public void testSetAndGetDiastolicPressure() {
        record.diastolicPressure = 85;
        assertEquals("Diastolic pressure should be 85", 85, record.diastolicPressure);
    }

    @Test
    //done
    public void testSetAndGetComment() {
        record.comment = "Follow-up visit";
        assertEquals("Comment should be 'Follow-up visit'", "Follow-up visit", record.comment);
    }

    @Test
    //done
    public void testEqualityOfTwoRecords() {
        Record record1 = new Record(70, 120, 80, "Normal check-up");
        Record record2 = new Record(70, 120, 80, "Normal check-up");
        assertEquals("Both records should be equal", record1.heartRate, record2.heartRate);
        assertEquals("Both records should be equal", record1.systolicPressure, record2.systolicPressure);
        assertEquals("Both records should be equal", record1.diastolicPressure, record2.diastolicPressure);
        assertEquals("Both records should be equal", record1.comment, record2.comment);
    }
}
