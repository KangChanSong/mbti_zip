package com.mbtizip.controller.profile;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileControllerTest {

    @Test
    public void real_profile이_조회된다(){

        //given
        String expectedProfile = "real";
        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("oauth");
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile();
        //then
        assertEquals(expectedProfile, profile);
    }

    @Test
    public void real_profile이_없으면_첫번째가_조회된다(){

        //given
        String expectedProfile = "oauth";
        MockEnvironment env = new MockEnvironment();

        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);

        //when
        String profile = controller.profile();

        //then
        assertEquals(expectedProfile, profile);
    }

    @Test
    public void active_profile_이_없으면_default가_조회된다(){

        //given
        String expectedProfile = "default";
        MockEnvironment env = new MockEnvironment();
        ProfileController controller = new ProfileController(env);
        //when
        String profile = controller.profile();
        //then
        assertEquals(expectedProfile, profile);
    }
}
