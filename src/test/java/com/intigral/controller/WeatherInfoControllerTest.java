package com.intigral.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.intigral.exceptions.CityNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WeatherInfoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser(username="user")
	public void givenCityNotFoundTest() throws Exception {
		String exceptionParam = "abc";

		mockMvc.perform(get("/weatherservice/city?name={exception_id}", exceptionParam)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andExpect(result -> assertThat(result.getResolvedException() instanceof CityNotFoundException))
		.andExpect(result -> assertThat("City not found".equals(result.getResolvedException().getMessage())));
	}

	@Test
	@WithMockUser(username="user")
	public void invalidArgumentTypeTest() throws Exception {
		Double exceptionParam1 = 51.5085;
		String exceptionParam2 = "abc";

		mockMvc.perform(get("/weatherservice/coordinates?lat={exp1}&lon={exp2}", exceptionParam1,exceptionParam2)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertThat(result.getResolvedException() instanceof MethodArgumentTypeMismatchException))
		.andExpect(result -> assertThat("lon should be of type java.lang.Double".equals(result.getResolvedException().getMessage())));
	}

	@Test
	@WithMockUser(username="user")
	public void missingArgumentTest() throws Exception {
		Double exceptionParam1 = 51.5085;

		mockMvc.perform(get("/weatherservice/coordinates?lat={exp1}", exceptionParam1)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(result -> assertThat(result.getResolvedException() instanceof MissingServletRequestParameterException))
		.andExpect(result -> assertThat("lon parameter is missing".equals(result.getResolvedException().getMessage())));
	}
}
