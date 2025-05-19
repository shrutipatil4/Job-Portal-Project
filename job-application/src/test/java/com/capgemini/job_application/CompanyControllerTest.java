package com.capgemini.job_application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.capgemini.job_application.controllers.CompanyController;
import com.capgemini.job_application.entities.Company;
import com.capgemini.job_application.entities.User;
import com.capgemini.job_application.services.CompanyService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CompanyController.class)
class CompanyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private CompanyService companyService;

	@Test
	@DisplayName("Should create a new company")
	void shouldCreateCompany() throws Exception {
		User user=new User();
		user.setUserId(100L);
		Company company = new Company(1L, user, "Capgemini", "IT", "Pune");

		Mockito.when(companyService.createCompany(Mockito.any())).thenReturn(company);

		mockMvc.perform(post("/api/companies").contentType(MediaType.APPLICATION_JSON).content(
				"{\"userId\":100,\"companyName\":\"Capgemini\",\"companyDomain\":\"IT\",\"headOffice\":\"Pune\"}"))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.companyName").value("Capgemini"))
				.andExpect(jsonPath("$.companyDomain").value("IT")).andExpect(jsonPath("$.headOffice").value("Pune"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("Should get company by ID")
	void shouldGetCompanyById() throws Exception {
		User user=new User();
		user.setUserId(101L);
		Company company = new Company(1L, user, "Infosys", "IT", "Bangalore");

		Mockito.when(companyService.getCompanyById(1L)).thenReturn(company);

		mockMvc.perform(get("/api/companies/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.companyName").value("Infosys"))
				.andExpect(jsonPath("$.headOffice").value("Bangalore")).andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("Should return all companies")
	void shouldGetAllCompanies() throws Exception {
		User user1=new User();
		user1.setUserId(1L);
		User user2=new User();
		user2.setUserId(2L);
		Company c1 = new Company(1L, user1, "A", "X", "Y");
		Company c2 = new Company(2L, user2, "B", "Y", "Z");

		Mockito.when(companyService.getAllCompanies()).thenReturn(List.of(c1, c2));

		mockMvc.perform(get("/api/companies")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].companyName").value("A")).andExpect(jsonPath("$[1].companyDomain").value("Y"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@DisplayName("Should update company with PUT")
	void shouldUpdateCompany() throws Exception {
		User user=new User();
		user.setUserId(200L);
		Company updated = new Company(1L, user, "UpdatedCo", "NewDomain", "NewHQ");

		Mockito.when(companyService.updateCompany(Mockito.eq(1L), Mockito.any())).thenReturn(updated);

		mockMvc.perform(put("/api/companies/1").contentType(MediaType.APPLICATION_JSON).content(
				"{\"userId\":200,\"companyName\":\"UpdatedCo\",\"companyDomain\":\"NewDomain\",\"headOffice\":\"NewHQ\"}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.companyName").value("UpdatedCo"))
				.andExpect(jsonPath("$.companyDomain").value("NewDomain"))
				.andExpect(jsonPath("$.headOffice").value("NewHQ")).andDo(MockMvcResultHandlers.print());
	}


	@Test
	@DisplayName("Should delete company")
	void shouldDeleteCompany() throws Exception {
		Mockito.doNothing().when(companyService).deleteCompany(1L);

		mockMvc.perform(delete("/api/companies/1")).andExpect(status().isNoContent())
				.andDo(MockMvcResultHandlers.print());
	}
}
