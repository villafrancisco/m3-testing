package com.example.demo.junit;

import com.example.demo.service.IRPFCalculator;
import com.example.demo.service.IVACalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IRPFCalculatorTest {

    @Test
    void calculateIRPFTest(){
        IRPFCalculator calculator = new IRPFCalculator();
        double result = calculator.calculateIRPF(100);
        assertEquals(15, result);
    }

}