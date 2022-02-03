package com.phoenix.phoenix;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
class PhoenixApplicationTests {

	@Value("${test.property.name}")
	private String testName;
	@Test
	void contextLoads() {
	}

	@Test
	void valueExists(){
		assertThat(testName).isEqualTo("phoenix");
		log.info(testName);
	}

}
