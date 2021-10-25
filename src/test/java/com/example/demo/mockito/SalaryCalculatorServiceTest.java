package com.example.demo.mockito;

import com.example.demo.domain.Employee;
import com.example.demo.service.IRPFCalculator;
import com.example.demo.service.IVACalculator;
import com.example.demo.service.SalaryCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

class SalaryCalculatorServiceTest {

    // dependencias
    IRPFCalculator irpfCalculator; // mock
    IVACalculator ivaCalculator; // mock

    SalaryCalculatorService service;

    @BeforeEach
    void setUp() {
        irpfCalculator = mock(IRPFCalculator.class);
        ivaCalculator = mock(IVACalculator.class);
        service = new SalaryCalculatorService(irpfCalculator, ivaCalculator);

    }

    @Test
    void test1() {

        when(irpfCalculator.calculateIRPF(anyDouble())).thenReturn(4950d);
        when(ivaCalculator.calculateIVA(anyDouble())).thenReturn(7969.5);

        Employee employee = new Employee(1L, "Prueba", 30);
        double result = service.calculateSalary(employee);

        assertEquals(45919.5, result);
        verify(irpfCalculator).calculateIRPF(anyDouble());
        verify(ivaCalculator).calculateIVA(anyDouble());

    }


    @Test
    void testOrder(){

        when(irpfCalculator.calculateIRPF(anyDouble())).thenReturn(4950d);
        when(ivaCalculator.calculateIVA(anyDouble())).thenReturn(7969.5);

        Employee employee = new Employee(1L, "Prueba", 30);
        double result = service.calculateSalary(employee);

        InOrder order = inOrder(irpfCalculator,ivaCalculator);

        // este tipo de verify permite comprobar el orden exacto
        // de ejecución de los métodos

        // así falla porque este orden no es el correcto
//        order.verify(ivaCalculator).calculateIVA(anyDouble());
//        order.verify(irpfCalculator).calculateIRPF(anyDouble());

        // manera correcta, tal cual se ejecuta en el método real
        order.verify(irpfCalculator).calculateIRPF(anyDouble());
        order.verify(ivaCalculator).calculateIVA(anyDouble());
    }
}