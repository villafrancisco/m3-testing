package com.example.demo.junit;

import com.example.demo.domain.Employee;
import com.example.demo.domain.SmartPhone;
import com.example.demo.domain.pieces.Battery;
import com.example.demo.domain.pieces.CPU;
import com.example.demo.domain.pieces.Camera;
import com.example.demo.domain.pieces.RAM;
import com.example.demo.service.SmartPhoneService;
import com.example.demo.service.SmartPhoneServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Operaciones CRUD sobre smartphones")
class SmartPhoneServiceImplTest {

    SmartPhoneService service;
    @BeforeEach
    void setUp() {
        service=new SmartPhoneServiceImpl();
    }

    @Test
    void countTest() {
        Integer count=service.count();
        assertNotNull(count);
        assertEquals(3,count);
    }

    @DisplayName("Funcionalidad buscar  smartphones")
    @Nested
    class FindAllTest{
        @DisplayName("findAll(): Buscar todos los smartphones")
        @Test
        void findAll() {
            List<SmartPhone> smartphones=service.findAll();
            assertNotNull(smartphones);
            assertEquals(3,smartphones.size());
        }


        @DisplayName("Buscar un smartphones con id 1")
        @Test
        void findOne1Test() {
            SmartPhone smartphone=service.findOne(1L);
            assertNotNull(smartphone);
            assertEquals(1L,smartphone.getId());
            assertNotNull(smartphone.getName());
        }

        @DisplayName("Buscar un smartphone que no existe")
        @Test
        void findOneNotExistTest() {
            SmartPhone smartphone  = service.findOne(999L);
            assertNull(smartphone);
        }
        @DisplayName("Comprobar excepción al buscar un smartphone nulo")
        @Test
        void findOneExceptionTest() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> service.findOne(null)
            );
        }
    }
    @DisplayName("Funcionalidad crear y modificar sobre smartphones")
    @Nested
    class SaveTest {
        @DisplayName("Comprobar excepción cuando el smartphones a guardar es nulo")
        @Test
        void saveNullTest() {

            assertThrows(
                    IllegalArgumentException.class,
                    () -> service.save(null)
            );
        }

        @DisplayName("Comprobar que se asigna un id cuando el id que pasamos es null")
        @Test
        void saveIdNullTest() {


            SmartPhone smartphone = new SmartPhone(null, "One plus 9",
                    new RAM(1L, "DDR4", 8),
                    new Battery(1L, 4500.0),
                    new CPU(1L, 4),
                    false,
                    new Camera(1L, "front camera", 12.5));


            assertEquals(3, service.count());
            SmartPhone result = service.save(smartphone);
            assertEquals(4, service.count());
            assertNotNull(result);
            assertNotNull(result.getId());
            assertEquals(4, result.getId());

        }

        @DisplayName("Comprobar que se asigna un id cuando el id que pasamos es 0")
        @Test
        void saveIdZeroTest() {


            SmartPhone smartphone = new SmartPhone(0L, "One plus 9",
                    new RAM(1L, "DDR4", 8),
                    new Battery(1L, 4500.0),
                    new CPU(1L, 4),
                    false,
                    new Camera(1L, "front camera", 12.5));


            assertEquals(3, service.count());
            SmartPhone result = service.save(smartphone);
            assertEquals(4, service.count());
            assertNotNull(result);
            assertNotNull(result.getId());
            assertEquals(4, result.getId());
        }

        @DisplayName("Comprobar que se actualiza un smartphone existente")
        @Test
        void saveUpdateTest() {


            SmartPhone smartphone = new SmartPhone(1L, "One plus 9 editadp",
                    new RAM(1L, "DDR4", 8),
                    new Battery(1L, 4500.0),
                    new CPU(1L, 4),
                    false,
                    new Camera(1L, "front camera", 12.5));

            assertEquals(3, service.count());
            SmartPhone result = service.save(smartphone);
            assertEquals(3, service.count());


            assertEquals(1L, result.getId());


            SmartPhone smartphone1 = service.findOne(1L);
            assertEquals("One plus 9 editadp", smartphone1.getName());

        }

        @DisplayName("Comprobar id negativo, no se debería añadir un smartphone")
        @Test
        void saveNegativeIdTest() {
            SmartPhone smartphone = new SmartPhone(-1L, "One plus 9 editadp",
                    new RAM(1L, "DDR4", 8),
                    new Battery(1L, 4500.0),
                    new CPU(1L, 4),
                    false,
                    new Camera(1L, "front camera", 12.5));

            assertEquals(3, service.count());

            assertThrows(
                    IllegalArgumentException.class,
                    () -> service.save(smartphone)
            );

            assertEquals(3, service.count());

        }

    }

    @DisplayName("Funcionalidad borrar smartphones")
    @Nested
    class DeleteTest {

        @Test
        void deleteNullTest() {
            boolean result = service.delete(null);
            assertFalse(result);
        }

        @Test
        void deleteNotContainsTest() {

            boolean result = service.delete(666L);
            assertFalse(result);
        }

        @Test
        void deleteOkTest() {

            boolean result = service.delete(1L);
            assertTrue(result);
        }

        @Test
        void deleteAllTest() {
            assertTrue(service.count() > 0);
            service.deleteAll();
            assertEquals(0, service.count());

        }

        @Test
        void deleteEmptyAllTest() {
            assertTrue(service.count() > 0);
            service.deleteAll();
            assertEquals(0, service.count());
            service.deleteAll();
            assertEquals(0, service.count());

        }
    }
    @Test
    void findByWifiTrueTest() {
        List<SmartPhone> result=service.findByWifi(true);
        assertEquals(2,result.size());
    }
    @Test
    void findByWifiFalseTest() {
        List<SmartPhone> result=service.findByWifi(false);
        assertEquals(1,result.size());
    }


}