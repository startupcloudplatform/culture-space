package com.crossent.microservice.culturalspacefront.admin.controller;

import com.crossent.microservice.culturalspacefront.admin.controller.CulturalSpaceAdminController;
import com.crossent.microservice.culturalspacefront.admin.service.CulturalSpaceAdminService;
import com.crossent.microservice.culturalspacefront.configuration.TestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.FileInputStream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    private CulturalSpaceAdminController adminController;

    @Mock
    private CulturalSpaceAdminService adminService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    public void list() throws Exception{
        when(adminService.listData()).thenReturn(TestConfiguration.setData());
        mockMvc.perform(get("/api/facilities/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void save() throws Exception {

        mockMvc.perform(post("/api/facilities/update").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void uploadFile() throws Exception {

        FileInputStream fis = new FileInputStream(new File("src/test/resources/seoul_cultural_space.xlsx"));
        MockMultipartFile file = new MockMultipartFile("file","seoul_cultural_space.xlsx", MediaType.MULTIPART_FORM_DATA_VALUE, fis);

        when(adminService.uploadFile(file)).thenReturn((int) TestConfiguration.setCount());

        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/facilities/file/upload?file=").file(file))
                .andExpect(status().isOk())
                .andDo(print());
    }

};
