package com.crossent.microservice.admin.controller;

import com.crossent.microservice.admin.service.CulturalSpaceAdminService;
import com.crossent.microservice.client.service.SearchService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CulturalSpaceAdminControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    private CulturalSpaceAdminController culturalSpaceAdminController;

    @Mock
    private CulturalSpaceAdminService culturalSpaceAdminService;

    @Mock
    private SearchService searchService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(culturalSpaceAdminController).build();
    }

    @Test
    public void list() throws Exception{
        mockMvc.perform(
                get("/api/facilities/list").contentType(
                        MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void save() throws Exception{
        mockMvc.perform(
                post("/api/facilities/update").contentType(
                        MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andDo(print());

    }
    
    @Test
    public void delete() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders
                    .delete("/api/facilities/delete")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void deleteByCode() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/facilities/delete/{code}",1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


}
