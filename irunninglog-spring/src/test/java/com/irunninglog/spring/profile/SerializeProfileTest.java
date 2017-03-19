package com.irunninglog.spring.profile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irunninglog.api.Gender;
import com.irunninglog.api.profile.IProfile;
import com.irunninglog.spring.AbstractTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertTrue;

public class SerializeProfileTest extends AbstractTest {

    private IProfile profile;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        profile = applicationContext.getBean(IProfile.class);
    }

    @Test
    public void serialize() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        String nullGender = mapper.writeValueAsString(profile);

        profile.setGender(Gender.MALE);
        String maleGender = mapper.writeValueAsString(profile);

        profile.setGender(Gender.FEMALE);
        String femaleGender = mapper.writeValueAsString(profile);

        assertTrue(nullGender.contains("\"gender\":null"));
        assertTrue(maleGender.contains("\"gender\":\"Male\""));
        assertTrue(femaleGender.contains("\"gender\":\"Female\""));
    }

}
