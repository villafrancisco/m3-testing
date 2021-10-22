package com.example.demo.junit;

import com.example.demo.domain.Employee;
import com.example.demo.service.IRPFCalculator;
import com.example.demo.service.IVACalculator;
import com.example.demo.service.SalaryCalculatorService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SalaryCalculatorServiceTest {
    IRPFCalculator irpfCalculator=new IRPFCalculator();
    IVACalculator ivaCalculator=new IVACalculator();

    SalaryCalculatorService service=new SalaryCalculatorService(irpfCalculator,ivaCalculator);

    @Test
    void calculateSalaryTest() {
        Employee employee=new Employee(1L,"Francisco",40);
        assertEquals(47311,service.calculateSalary(employee));


    }
}