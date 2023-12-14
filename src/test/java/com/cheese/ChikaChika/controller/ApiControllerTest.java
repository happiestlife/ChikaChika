package com.cheese.ChikaChika.controller;

import com.cheese.ChikaChika.model.Teeth;
import com.cheese.ChikaChika.model.TeethSource;
import com.cheese.ChikaChika.util.TeethSessionManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApiControllerTest {

    @Autowired
    private ApiController apiController;

    @Autowired
    private TeethSessionManager teethSession;

    private final int[] origin = {1, 2, 3, 4, 5};

    @BeforeEach
    public void set() {
        teethSession.setBrushedCnt(new int[origin.length]);
    }

    @Test
    void getTeethBrushedInformation() {
        final int[] CORRECT_RESULT = {0, 0, 1, 1, 1};

        teethSession.setBrushedCnt(origin);

        Teeth result = apiController.getTeethBrushedInformation();
        int[] isSectionBrushed = result.getTeethSectionBrushedCnt();
        for (int i = 0; i < isSectionBrushed.length; i++) {
            Assertions.assertThat(isSectionBrushed[i]).isEqualTo(CORRECT_RESULT[i]);
            System.out.print(isSectionBrushed[i] + " ");
        }
    }

    @Test
    void setTeethBrushedInformation() {
        apiController.setTeethBrushedInformation(new TeethSource(origin));

        System.out.println(1);
        int[] brushedTeethInfo = teethSession.getBrushedCnt();
        for (int i = 0; i < brushedTeethInfo.length; i++) {
            Assertions.assertThat(brushedTeethInfo[i]).isEqualTo(origin[i]);
            System.out.print(brushedTeethInfo[i] + " ");
        }


        apiController.setTeethBrushedInformation(new TeethSource(origin));

        System.out.println("\n2");
        brushedTeethInfo = teethSession.getBrushedCnt();
        for (int i = 0; i < brushedTeethInfo.length; i++) {
            Assertions.assertThat(brushedTeethInfo[i]).isEqualTo(origin[i] * 2);
            System.out.print(brushedTeethInfo[i] + " ");
        }
    }
}