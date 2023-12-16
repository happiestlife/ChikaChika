//package com.cheese.ChikaChika.service;
//
//import com.cheese.ChikaChika.controller.ApiController;
//import com.cheese.ChikaChika.model.TeethSource;
//import com.cheese.ChikaChika.util.TeethSessionManager;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//
//@SpringBootTest
//class TeethBrushedServiceTest {
//
//    @Autowired
//    private TeethBrushedService teethBrushedService;
//
//    @Autowired
//    private TeethSessionManager teethSession;
//
//    private final int[] origin = {1, 2, 3, 4, 5};
//
//    @BeforeEach
//    public void set() {
//        teethSession.setBrushedCnt(new int[origin.length]);
//    }
//
//    @Test
//    void getTeethSectionBrushedLevel() {
//        final int[] RESULT = {0, 0, 1, 1, 1};
//
//        teethSession.setBrushedCnt(origin);
//        int[] teethSectionBrushedLevel = teethBrushedService.getTeethSectionBrushedLevel();
//        System.out.println(teethSectionBrushedLevel);
//        for (int i = 0; i < teethSectionBrushedLevel.length; i++) {
//            Assertions.assertThat(teethSectionBrushedLevel[i]).isEqualTo(RESULT[i]);
//        }
//
//        final int[] RESULT_DOUBLE = {0, 1, 1, 2, 3};
//        int[] doubleOrigin = new int[origin.length];
//        for (int i = 0; i < doubleOrigin.length; i++) {
//            doubleOrigin[i] = origin[i] * 2;
//        }
//        teethSession.setBrushedCnt(doubleOrigin);
//        teethSectionBrushedLevel = teethBrushedService.getTeethSectionBrushedLevel();
//        System.out.println(teethSectionBrushedLevel);
//        for (int i = 0; i < teethSectionBrushedLevel.length; i++) {
//            Assertions.assertThat(teethSectionBrushedLevel[i]).isEqualTo(RESULT_DOUBLE[i]);
//        }
//    }
//
//    @Test
//    void setTeethSectionBrushedLevel() {
//        teethBrushedService.setTeethSectionBrushedLevel(new TeethSource(origin));
//
//        int[] brushedCnt = teethSession.getBrushedCnt();
//        for (int i = 0; i < brushedCnt.length; i++) {
//            Assertions.assertThat(brushedCnt[i]).isEqualTo(origin[i]);
//        }
//
//        teethBrushedService.setTeethSectionBrushedLevel(new TeethSource(origin));
//        brushedCnt = teethSession.getBrushedCnt();
//        for (int i = 0; i < brushedCnt.length; i++) {
//            Assertions.assertThat(brushedCnt[i]).isEqualTo(origin[i] * 2);
//        }
//    }
//}