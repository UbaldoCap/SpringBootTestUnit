package com.example.SpringBootTestUnit;

import com.example.SpringBootTestUnit.controllers.UserController;
import com.example.SpringBootTestUnit.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.profiles.active=test")
@ActiveProfiles(value = "test")
@AutoConfigureMockMvc
class UserControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserController userController;

	private User user() {
		return new User();
	}

	private void add () throws Exception {
		this.mockMvc.perform(post("/user/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user())))
				.andExpect(status().isOk());
	}


	private User addAndReturn() throws Exception {
		MvcResult result = this.mockMvc.perform(post("/user/add")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user())))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		return objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
	}

	@Test
	void createUser() throws Exception {
		User user = addAndReturn();
		assertThat(user.getId()).isNotZero().isNotNull();
	}

	@Test
	void getAll() throws Exception {
		add();
		add();
		add();
		MvcResult result = this.mockMvc.perform(get("/user/getall"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		assertThat(objectMapper.readValue(result.getResponse().getContentAsString(), List.class).size())
				.isGreaterThan(0).isNotNull();
	}

	@Test
	void getByid() throws Exception {
		add();
		add();
		add();
		User user = addAndReturn();
		MvcResult result = this.mockMvc.perform(get("/user/get/" + user.getId()))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		assertThat(objectMapper.readValue(result.getResponse().getContentAsString(), User.class).getId())
				.isNotNull();
	}

	@Test
	void updateById() throws Exception {
		User user = addAndReturn();
		User userupdate = user();
		userupdate.setUsername("pinoooo");
		MvcResult result = this.mockMvc.perform(put("/user/update/" + user.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(userupdate)))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		assertThat(objectMapper.readValue(result.getResponse().getContentAsString(), User.class).getUsername())
				.isEqualTo("pinoooo");
	}

	@Test
	void deleteById() throws Exception {
		User user = addAndReturn();
		this.mockMvc.perform(delete("/user/delete/" + user.getId()))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		assertThat(userController.getById(user.getId())).isNotPresent();
	}
}
