package com.phoenix.phoenix.data.repository;

import com.phoenix.phoenix.data.models.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository repository;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void whenUserIsCreated_thenCreateCartTest(){
        AppUser user = new AppUser();

        user.setAddress("Ojo LASU");
        user.setEmail("omolomolaarinero@gmail.com");
        user.setLastName("Adugbo");
        user.setFirstName("Idamu");

        repository.save(user);
        assertThat(user.getId()).isNotNull();
        assertThat(user.getCart()).isNotNull();
        log.info("App user is created {}", user);
    }
}