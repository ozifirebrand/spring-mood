package com.phoenix.phoenix;

import lombok.extern.slf4j.Slf4j;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
class PhoenixApplicationTests {

	@Value("${test.property.name}")
	private String testName;

	@Autowired
	private DataSource datasource;

	@Test
	void contextLoads() {
	}

	@Test
	void valueExists(){
		assertThat(testName).isEqualTo("phoenix");
		log.info(testName);
	}

	@Test
	public void applicationCanConnectToDatabaseTest(){
		assertThat(datasource).isNotNull();
		Connection connection;
		try {
			connection = datasource.getConnection();
			assertThat(connection).isNotNull();
			log.info("Connection-->{}", connection.getSchema());
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}

}
