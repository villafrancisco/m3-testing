package com.example.demo.junit;

import com.example.demo.domain.Employee;
import com.example.demo.domain.SmartPhone;
import com.example.demo.domain.pieces.Battery;
import com.example.demo.domain.pieces.CPU;
import com.example.demo.domain.pieces.Camera;
import com.example.demo.domain.pieces.RAM;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.EmployeeRepositoryImpl;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.EmployeeServiceImpl;
import com.example.demo.service.SmartPhoneServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Operaciones CRUD sobre employees")
class EmployeeServiceImplTest {

    EmployeeService service;

    @BeforeEach
    void setUp() {
        EmployeeRepository employeeRepository=new EmployeeRepositoryImpl();
        service=new EmployeeServiceImpl(employeeRepository);
    }

    @DisplayName("Funcionalidad contar sobre empleados")
    @Nested
    class CountTest{
        @DisplayName("count(): Contar el numero de empleados")
        @Test
        void count() {
            Integer count=service.count();
            assertNotNull(count);
            assertEquals(3,count);
        }
    }
    @DisplayName("Funcionalidad buscar todos los empleados")
    @Nested
    class FindAllTest{
        @DisplayName("findAll(): Buscar todos los empleados")
        @Test
        void findAll() {
            List<Employee> employees=service.findAll();
            assertNotNull(employees);
            assertEquals(3,employees.size());
        }
    }
    @DisplayName("Funcionalidad buscar un empleado")
    @Nested
    class FindOneTest{
        @DisplayName("Buscar un empleado con id 1")
        @Test
        void findOne1Test() {
            Employee employee=service.findOne(1L);
            assertNotNull(employee);
            assertEquals(1L,employee.getId());
            assertNotNull(employee.getName());
        }

        @DisplayName("Buscar un empleado que no existe")
        @Test
        void findOneNotExistTest() {
            Employee employee  = service.findOne(999L);
            assertNull(employee);
        }
        @DisplayName("Comprobar excepción al buscar un empleado nulo")
        @Test
        void findOneExceptionTest() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> service.findOne(null)
            );
        }
    }

    @DisplayName("Funcionalidad buscar un empleado optional")
    @Nested
    class FindOneOptionalTest {
        @Test
        void findOne1Optional() {
            Optional<Employee> employeeOpt = service.findOneOptional(1L);
            assertTrue(employeeOpt.isPresent());

            Employee employee = employeeOpt.get();
            Long id = employee.getId();

            assertEquals(1L, id);
        }
        @Test
        void findOneNullOptional() {
            Optional<Employee> employeeOpt = service.findOneOptional(null);

            assertTrue(employeeOpt.isEmpty());
        }
        @Test
        void findOneNotExistOptional() {
            Optional<Employee> employeeOpt = service.findOneOptional(999L);

            assertTrue(employeeOpt.isEmpty());
        }
    }

    @DisplayName("Funcionalidad crear y modificar sobre empleados")
    @Nested
    class SaveTest {
        @DisplayName("Comprobar excepción cuando el empleado a guardar es nulo")
        @Test
        void saveNullTest(){

            assertThrows(
                    IllegalArgumentException.class,
                    () -> service.save(null)
            );
        }

        @DisplayName("Comprobar que se asigna un id cuando el id que pasamos es null")
        @Test
        void saveIdNullTest(){


            Employee employee=new Employee(1L,"Francisco",40);

            assertEquals(3, service.count());
            Employee result = service.save(employee);
            assertEquals(4, service.count());
            assertNotNull(result);
            assertNotNull(result.getId());
            assertEquals(4, result.getId());

        }
        @DisplayName("Comprobar que se asigna un id cuando el id que pasamos es 0")
        @Test
        void saveIdZeroTest(){


            Employee employee=new Employee(0L,"Francisco",40);

            assertEquals(3, service.count());
            Employee result = service.save(employee);
            assertEquals(4, service.count());
            assertNotNull(result);
            assertNotNull(result.getId());
            assertEquals(4, result.getId());
        }

        @DisplayName("Comprobar que se actualiza un empleado existente")
        @Test
        void saveUpdateTest(){


            Employee employee=new Employee(1L,"Francisco editado",40);

            assertEquals(3, service.count());
            Employee result = service.save(employee);
            assertEquals(3, service.count());


            assertEquals(1L, result.getId());


            Employee employee1 = service.findOne(1L);
            assertEquals("Francisco editado", employee1.getName());

        }
        @DisplayName("Comprobar id negativo, no se debería añadir un empleado")
        @Test
        void saveNegativeIdTest(){
            Employee employee=new Employee(-1L,"Francisco",40);

            assertEquals(3, service.count());

            assertThrows(
                    IllegalArgumentException.class,
                    () -> service.save(employee)
            );

            assertEquals(3, service.count());


        }

    }

    @DisplayName("Funcionalidad borrar un empleados")
    @Nested
    class DeleteTest {

        @Test
        void deleteNullTest(){
            boolean result = service.delete(null);
            assertFalse(result);
        }

        @Test
        void deleteNotContainsTest(){

            boolean result = service.delete(666L);
            assertFalse(result);
        }

        @Test
        void deleteOkTest(){

            boolean result = service.delete(1L);
            assertTrue(result);
        }

        @Test
        void deleteAllTest(){
            assertTrue(service.count() > 0);
            service.deleteAll();
            assertEquals(0, service.count());

        }

        @Test
        void deleteEmptyAllTest(){
            assertTrue(service.count() > 0);
            service.deleteAll();
            assertEquals(0, service.count());
            service.deleteAll();
            assertEquals(0, service.count());

        }
    }









}