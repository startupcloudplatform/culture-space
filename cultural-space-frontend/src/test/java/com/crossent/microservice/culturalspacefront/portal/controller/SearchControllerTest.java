package com.crossent.microservice.culturalspacefront.portal.controller;

import com.crossent.microservice.culturalspacefront.configuration.TestConfiguration;
import com.crossent.microservice.culturalspacefront.portal.controller.SearchController;
import com.crossent.microservice.culturalspacefront.portal.service.SearchService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SearchControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    private SearchController searchController;

    @Mock
    private SearchService searchService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
    }

    @Test
    public void listByKeyword() throws Exception {
        when(searchService.listByKeyword(TestConfiguration.QUERY_KEYWORD)).thenReturn(TestConfiguration.setData());

        mockMvc.perform(
                get("/api/search/list?query="+TestConfiguration.QUERY_KEYWORD).contentType(
                        MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andReturn();
    }

    @Test
    public void listByAddress() throws Exception {
        when(searchService.listByAddress(TestConfiguration.QUERY_ADDRESS)).thenReturn(TestConfiguration.setData());

        mockMvc.perform(
                get("/api/search/address/list?query="+TestConfiguration.QUERY_TYPE_CODE).contentType(
                        MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void listWithCondition() throws Exception {

        when(searchController.listWithCondition(TestConfiguration.QUERY_TYPE_CODE
                ,TestConfiguration.QUERY_FREE, TestConfiguration.QUERY_ADDRESS)).thenReturn(TestConfiguration.setData());

        Float type = TestConfiguration.QUERY_TYPE_CODE;
        String free = TestConfiguration.QUERY_FREE;
        String address = TestConfiguration.QUERY_ADDRESS;

        mockMvc.perform(get("/api/search/condition?type="+type+"&free="+free+"&address="+address).contentType(
                MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void listGroupByType() throws Exception {
        when(searchController.listGroupByType()).thenReturn(TestConfiguration.setListType());

        mockMvc.perform(get("/api/search/type/group").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void listGroupByFree() throws Exception {
        when(searchService.listGroupByFree()).thenReturn(TestConfiguration.setGroupFree());
        mockMvc.perform(
                get("/api/search/fee/group").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

}