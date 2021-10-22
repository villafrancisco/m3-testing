package com.example.demo.junit;

import com.example.demo.domain.SmartPhone;
import com.example.demo.domain.SmartWatch;
import com.example.demo.domain.pieces.*;
import com.example.demo.service.SmartPhoneService;
import com.example.demo.service.SmartPhoneServiceImpl;
import com.example.demo.service.SmartWatchService;
import com.example.demo.service.SmartWatchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SmartWatchServiceImplTest {


        SmartWatchService service;
        @BeforeEach
        void setUp() {
            service=new SmartWatchServiceImpl();
        }

        @Test
        void countTest() {
            Integer count=service.count();
            assertNotNull(count);
            assertEquals(3,count);
        }

        @DisplayName("Funcionalidad buscar  smartwatch")
        @Nested
        class FindAllTest{
            @DisplayName("findAll(): Buscar todos los smartwatch")
            @Test
            void findAll() {
                List<SmartWatch> smartwatches=service.findAll();
                assertNotNull(smartwatches);
                assertEquals(3,smartwatches.size());
            }


            @DisplayName("Buscar un smartwatches con id 1")
            @Test
            void findOne1Test() {
                SmartWatch smartwatches=service.findOne(1L);
                assertNotNull(smartwatches);
                assertEquals(1L,smartwatches.getId());
                assertNotNull(smartwatches.getName());
            }

            @DisplayName("Buscar un smartwatch que no existe")
            @Test
            void findOneNotExistTest() {
                SmartWatch smartwatch  = service.findOne(999L);
                assertNull(smartwatch);
            }
            @DisplayName("Comprobar excepción al buscar un smartwatch nulo")
            @Test
            void findOneExceptionTest() {
                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.findOne(null)
                );
            }
        }
        @DisplayName("Funcionalidad crear y modificar sobre smartwatches")
        @Nested
        class SaveTest {
            @DisplayName("Comprobar excepción cuando el smartwatch a guardar es nulo")
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


                SmartWatch smartwatch = new SmartWatch(null, "Fitbit sense",
                        new RAM(1L, "DDR4", 2),
                        new Battery(1L, 4500.0),
                        new CPU(1L, 4),
                        true,
                        new HealthMonitor(1L, 0.0, 0));

                assertEquals(3, service.count());
                SmartWatch result = service.save(smartwatch);
                assertEquals(4, service.count());
                assertNotNull(result);
                assertNotNull(result.getId());
                assertEquals(4, result.getId());

            }

            @DisplayName("Comprobar que se asigna un id cuando el id que pasamos es 0")
            @Test
            void saveIdZeroTest() {

                SmartWatch smartwatch = new SmartWatch(0L, "Fitbit sense",
                        new RAM(1L, "DDR4", 2),
                        new Battery(1L, 4500.0),
                        new CPU(1L, 4),
                        true,
                        new HealthMonitor(1L, 0.0, 0));


                assertEquals(3, service.count());
                SmartWatch result = service.save(smartwatch);
                assertEquals(4, service.count());
                assertNotNull(result);
                assertNotNull(result.getId());
                assertEquals(4, result.getId());
            }

            @DisplayName("Comprobar que se actualiza un smartwatch existente")
            @Test
            void saveUpdateTest() {



                SmartWatch smartwatch = new SmartWatch(1L, "Fitbit sense modificado",
                        new RAM(1L, "DDR4", 2),
                        new Battery(1L, 4500.0),
                        new CPU(1L, 4),
                        true,
                        new HealthMonitor(1L, 0.0, 0));


                assertEquals(3, service.count());
                SmartWatch result = service.save(smartwatch);
                assertEquals(3, service.count());


                assertEquals(1L, result.getId());


                SmartWatch smartwatch1 = service.findOne(1L);
                assertEquals("Fitbit sense modificado", smartwatch1.getName());

            }

            @DisplayName("Comprobar id negativo, no se debería añadir un smartwatch")
            @Test
            void saveNegativeIdTest() {
                SmartWatch smartwatch = new SmartWatch(-1L, "Fitbit sense modificado",
                        new RAM(1L, "DDR4", 2),
                        new Battery(1L, 4500.0),
                        new CPU(1L, 4),
                        true,
                        new HealthMonitor(1L, 0.0, 0));

                assertEquals(3, service.count());

                assertThrows(
                        IllegalArgumentException.class,
                        () -> service.save(smartwatch)
                );

                assertEquals(3, service.count());

            }

        }

        @DisplayName("Funcionalidad borrar smartwatch")
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



    }