package com.irunninglog.spring.challenges;

import com.irunninglog.api.athletes.IAthlete;
import com.irunninglog.api.challenges.IChallenge;
import com.irunninglog.api.challenges.IChallengesService;
import com.irunninglog.api.progress.Progress;
import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.security.IUser;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.spring.strava.StravaService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ChallengesServiceTest extends AbstractTest {

    private IChallengesService service;
    private IUser user;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) throws Exception {
        super.afterBefore(applicationContext);

        service = applicationContext.getBean(IChallengesService.class);
        StravaService stravaService = applicationContext.getBean(StravaService.class);

        restTemplate.setAthlete(factory.get(IAthlete.class)
                .setId(-1)
                .setEmail("mock@irunninglog.com")
                .setFirstname("Mock")
                .setLastname("User")
                .setAvatar("https://irunninglog.com/profiles/mock"));

        restTemplate.setRuns(factory.get(IRun.class).setDistance("1609344").setId(1).setName("run"));

        user = stravaService.userFromToken("token");
    }

    @Test
    public void get() throws InterruptedException {
        waitForRuns(user);

        List<IChallenge> challenges = service.getChallenges(user);

        assertNotNull(challenges);
        assertEquals(12, challenges.size());

        expect(challenges.get(0), "New York to Boston", "Driving distance", "215.1 mi", "215.1 mi", 100, Progress.GOOD, 215);
        expect(challenges.get(1), "London to Rome", "Driving distance", "1,168.3 mi", "1,000 mi", 85, Progress.GOOD, 1168);
        expect(challenges.get(2), "Appalachain Trail", "Official website distance", "2,190 mi", "1,000 mi", 45, Progress.OK, 2190);
        expect(challenges.get(3), "New York to Los Angeles", "Driving distance", "2,776.8 mi", "1,000 mi", 36, Progress.OK, 2776);
        expect(challenges.get(4), "Miami to Prudhoe Bay", "Driving distance", "5,341.3 mi", "1,000 mi", 18, Progress.BAD, 5341);
        expect(challenges.get(5), "Around the Moon", "Distance at the equator", "6,786 mi", "1,000 mi", 14, Progress.BAD, 6786);
        expect(challenges.get(6), "London to Cape Town", "Driving distance", "8,246.7 mi", "1,000 mi", 12, Progress.BAD, 8246);
        expect(challenges.get(7), "Lisbon to Magadan", "Driving distance", "9,410.7 mi", "1,000 mi", 10, Progress.BAD, 9410);
        expect(challenges.get(8), "Around Mars", "Distance at the equator", "13,263 mi", "1,000 mi", 7, Progress.BAD, 13263);
        expect(challenges.get(9), "Prudhoe Bay to Ushuaia", "Driving distance", "15,051 mi", "1,000 mi", 6, Progress.BAD, 15051);
        expect(challenges.get(10), "Around the World", "Distance at the equator", "24,873.5 mi", "1,000 mi", 4, Progress.BAD, 24873);
        expect(challenges.get(11), "From the Earth to the Moon", "Average distance", "238,855 mi", "1,000 mi", 0, Progress.BAD, 238855);
    }

    private void expect(IChallenge challenge, String name, String description, String total, String done, int percentage, Progress progress, int distance) {
        assertEquals(name, challenge.getName());
        assertEquals(description, challenge.getDescription());
        assertEquals(total, challenge.getDistanceTotal());
        assertEquals(done, challenge.getDistanceDone());
        assertEquals(percentage, challenge.getPercentage());
        assertEquals(progress, challenge.getProgress());
        assertEquals(distance, challenge.getDistanceInt());
    }

}
