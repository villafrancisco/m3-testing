package com.example.demo.junit;

import com.example.demo.service.IVACalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IVACalculatorTest {

    @Test
    void calculateIVATest() {
        IVACalculator calculator = new IVACalculator();
        double result = calculator.calculateIVA(100);
        assertEquals(21, result);
    }
}