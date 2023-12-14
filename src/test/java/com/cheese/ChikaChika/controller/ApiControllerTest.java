package com.cheese.ChikaChika.controller;

import com.cheese.ChikaChika.model.Teeth;
import com.cheese.ChikaChika.util.TeethSessionManager;
import org.assertj.core.api.Assertions;
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

    private final boolean[] origin = {true, true, false, false, true, false};


    @Test
    void getTeethBrushedInformation() {
        teethSession.setBrushedTeethInfo(origin);

        Teeth result = apiController.getTeethBrushedInformation();
        boolean[] isSectionBrushed = result.getIsSectionBrushed();
        for (int i = 0; i < isSectionBrushed.length; i++) {
            Assertions.assertThat(origin[i]).isEqualTo(isSectionBrushed[i]);
            System.out.print(isSectionBrushed[i] + " ");
        }
    }

    @Test
    void setTeethBrushedInformation() {
        String test = "110010";
        apiController.setTeethBrushedInformation(test);

        System.out.println(1);
        boolean[] brushedTeethInfo = teethSession.getBrushedTeethInfo();
        for (int i = 0; i < brushedTeethInfo.length; i++) {
            Assertions.assertThat(brushedTeethInfo[i]).isEqualTo(origin[i]);
            System.out.print(brushedTeethInfo[i] + " ");
        }

        System.out.println("\n2");
        apiController.setTeethBrushedInformation(test);
        brushedTeethInfo = teethSession.getBrushedTeethInfo();
        for (int i = 0; i < brushedTeethInfo.length; i++) {
            Assertions.assertThat(brushedTeethInfo[i]).isEqualTo(origin[i]);
            System.out.print(brushedTeethInfo[i] + " ");
        }
    }
}