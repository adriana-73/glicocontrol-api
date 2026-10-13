package com.glicocontrol.backend.model;

import com.glicocontrol.backend.models.GlucoseCategory;
import com.glicocontrol.backend.models.GlucoseMeasurement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlucoseMeasurementTest {

    @Test
    void testCalculateCategoryHipoglicemia() {
        GlucoseMeasurement measurement = new GlucoseMeasurement();
        measurement.setGlucoseValue(60); // Valor abaixo de 70
        measurement.calculateCategory();

        assertEquals(GlucoseCategory.HIPOGLICEMIA, measurement.getCategory());
    }

    @Test
    void testCalculateCategoryNormal() {
        GlucoseMeasurement measurement = new GlucoseMeasurement();
        measurement.setGlucoseValue(110); // Valor entre 70 e 180
        measurement.calculateCategory();

        assertEquals(GlucoseCategory.NORMAL, measurement.getCategory());
    }

    @Test
    void testCalculateCategoryHiperglicemia() {
        GlucoseMeasurement measurement = new GlucoseMeasurement();
        measurement.setGlucoseValue(200); // Valor acima de 180
        measurement.calculateCategory();

        assertEquals(GlucoseCategory.HIPERGLICEMIA, measurement.getCategory());
    }
}