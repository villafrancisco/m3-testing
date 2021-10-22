package com.example.demo.junit;

import com.example.demo.domain.SmartDevice;
import com.example.demo.domain.SmartPhone;
import com.example.demo.domain.SmartWatch;
import com.example.demo.service.SmartDeviceFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmartDeviceFactoryTest {


    @Test
    void createByTypePhoneTest() {
        SmartDevice phone=SmartDeviceFactory.createByType("phone");
        assertTrue(phone instanceof SmartPhone);

    }
    @Test
    void createByTypeWatchTest() {
        SmartDevice watch=SmartDeviceFactory.createByType("watch");
        assertTrue(watch instanceof SmartWatch);
    }

    @Test
    void createByTypeNotPhoneWatchTest() {

        assertThrows(
                IllegalArgumentException.class,
                () -> SmartDeviceFactory.createByType("otro")
        );
    }
    @Test
    void createByTypeNulTest() {

        assertThrows(
                NullPointerException.class,
                () -> SmartDeviceFactory.createByType(null)
        );
    }
}