package com.example.demo.mockito;

import com.example.demo.domain.Employee;
import com.example.demo.repository.EmployeeRepositoryImpl;
import com.example.demo.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    EmployeeRepositoryImpl repositoryMock;

    EmployeeServiceImpl service;
    @BeforeEach
    void setUp() {
        repositoryMock =mock(EmployeeRepositoryImpl.class);
        service=new EmployeeServiceImpl(repositoryMock);
    }

    @Test
    void count() {

        //configuracion escenario
        when(repositoryMock.count()).thenReturn(5);
        //ejecutar el comportamiento a testear
        Integer result=service.count();
        //Aserciones y verificaciones
        assertNotNull(result);
        assertEquals(5,result);

    }

    @Test
    void findAll() {

        //configuracion escenario
        List<Employee> employees= Arrays.asList(
                new Employee(1L,"Nombre1",40),
                new Employee(2L,"Nombre2",60));
        when(repositoryMock.findAll()).thenReturn(employees);
        //ejecutar el comportamiento a testear
        List<Employee> result=service.findAll();
        //Aserciones y verificaciones
        assertEquals(2,result.size());
        verify(repositoryMock).findAll();

    }

    @Test
    void findOne() {
    }

    @Test
    void findOneOptional() {
        //configuracion escenario

        Employee employee=new Employee(1L,"ee",20);
        //when(repositoryMock.findOne(1L)).thenReturn(employee);
        when(repositoryMock.findOne(anyLong())).thenReturn(employee);

        //ejecutar el comportamiento a testear
        Optional<Employee> employeeOpt=service.findOneOptional(1L);

        //Aserciones y verificaciones
        assertTrue(employeeOpt.isPresent());

    }

    @Test
    void findOneNullOptional(){

        //configuracion escenario
        when(repositoryMock.findOne(anyLong())).thenReturn(null);
        //ejecutar el comportamiento a testear
        Optional<Employee> employeeOpt=service.findOneOptional(1L);
        //Aserciones y verificaciones
        assertTrue(employeeOpt.isEmpty());
        verify(repositoryMock).findOne(anyLong());
    }

    @Test
    void findOneOptionalException(){
        //configuracion escenario
        when(repositoryMock.findOne(anyLong())).thenThrow(new IllegalArgumentException());
        //ejecutar el comportamiento a testear
        Optional<Employee> employeeOpt=service.findOneOptional(1L);

        //verificaciones
        assertTrue(employeeOpt.isEmpty());
        verify(repositoryMock).findOne(anyLong());
    }




    @Test
    void save() {
    Employee employee=new Employee(1L,"EE",55);
    when(repositoryMock.save(any())).thenReturn(employee);

    Employee result=service.save(employee);

    assertNotNull(result);
    assertEquals(1L,result.getId());

    }

    @Test
    void delete() {

        // 1
        when(repositoryMock.delete(any())).thenReturn(true);

        // 2 comportamiento
        boolean result = service.delete(1L);

        // 3
        assertTrue(result);
        verify(repositoryMock).delete(any());

    }

    @Test
    void deleteAll() {
    }
}