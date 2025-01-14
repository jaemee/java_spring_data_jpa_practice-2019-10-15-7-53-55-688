package com.tw.apistackbase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.apistackbase.core.Company;
import com.tw.apistackbase.repository.CompanyRepository;
import com.tw.apistackbase.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CompanyController.class)
@ActiveProfiles(profiles = "test")
class CompanyControllerTest {

    Iterable<Company> companies;

    private Company company;

    @MockBean
    private CompanyRepository companyRepository;

    @MockBean
    private CompanyService companyService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_return_status_created_when_company_is_created() throws Exception {
        company = new Company();
        ResultActions result = mvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(company)));
        result.andExpect(status().isCreated());
    }

    @Test
    void should_return_all_companies() throws Exception {
        ResultActions result = mvc.perform(get("/companies/all")
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    void should_return_ok_when_company_is_updated() throws Exception {
        when(companyService.isUpdated(any())).thenReturn(true);
        ResultActions result = mvc.perform(put("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Company())));
        result.andExpect(status().isOk());
    }

    @Test
    void should_return_not_found_when_company_is_invalid() throws Exception {
        when(companyService.isUpdated(any())).thenReturn(false);
        ResultActions result = mvc.perform(put("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Company())));
        result.andExpect(status().isNotFound());
    }

    @Test
    void should_return_not_found_when_deleting_invalid_company() throws Exception {

        ResultActions result = mvc.perform(delete("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Company())));
        result.andExpect(status().isNotFound());
    }

    @Test
    void should_return_ok_when_deleting_invalid_company() throws Exception {
        company = new Company();
        company.setId(3L);
        when(companyService.delete(any())).thenReturn(Optional.of(company));
        ResultActions result = mvc.perform(delete("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(company)));
        result.andExpect(status().isOk());
    }

    @Test
    void should_get_company_by_name() throws Exception {
        company = new Company();
        company.setId(3L);
        company.setName("ACompany");
        when(companyService.findByNameContaining(any())).thenReturn(company);
        ResultActions result = mvc.perform(get("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(company)));
        result.andExpect(status().isOk());
    }
}