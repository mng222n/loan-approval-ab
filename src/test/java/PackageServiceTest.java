//package com.ipc.test;
//
//import static org.junit.Assert.assertNotNull;
//
//import java.io.IOException;
//
//import org.junit.Test;
//import org.junit.Before;
//import org.junit.runner.RunWith;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.boot.test.context.TestConfiguration;
//
//import com.ipc.service.impl.PackageServiceImpl;
//
//@WebAppConfiguration
//@RunWith(SpringJUnit4ClassRunner.class)
//
//public class PackageServiceTest {
//    @TestConfiguration
//    static class PackageServiceImplTestContextConfiguration {
//
//        @Bean
//        public PackageService packageService() {
//            return new PackageServiceImpl();
//        }
//    }
//
//    @Autowired
//    private PackageService packageService;
//
//    @Before public void setUp() {
//
//    }
//
//    @Test
//    public void testPackageService() throws JsonProcessingException, IOException {
//
//    }
//}