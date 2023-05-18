package com.demo.hotel.restcontroller;

import com.demo.hotel.configuration.WebConfigProperties;
import com.demo.hotel.webservice.client.HotelWebServiceClient;
import com.demo.hotel.webservice.client.dto.AmenityDto;
import com.demo.hotel.webservice.client.dto.GetAmenityListRequest;
import com.demo.hotel.webservice.client.dto.GetAmenityListResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AmenityRestController.class)
@Import(WebConfigProperties.class)
@AutoConfigureMockMvc(addFilters = false)
class AmenityRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelWebServiceClient hotelWebServiceClient;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void getHotelAmenities() throws Exception {
        GetAmenityListResponse expectedResponse = new GetAmenityListResponse();
        expectedResponse.getAmenityListDto().addAll(LongStream.iterate(1, n -> n + 1)
                .limit(5)
                .mapToObj(n -> {
                    AmenityDto dto = new AmenityDto();
                    dto.setAmenityId(n);
                    return dto;
                }).collect(Collectors.toList()));
        expectedResponse.setStatusCode(OK.value());
        expectedResponse.setMessage(OK.name());

        when(hotelWebServiceClient.process(any(GetAmenityListRequest.class), eq(GetAmenityListResponse.class)))
                .thenReturn(expectedResponse);

        // test
        ResultActions resultActions = mockMvc.perform(get("/amenities"));

        // verify results;
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

}