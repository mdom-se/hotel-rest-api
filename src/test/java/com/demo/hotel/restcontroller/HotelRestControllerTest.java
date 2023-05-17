package com.demo.hotel.restcontroller;

import com.demo.hotel.configuration.WebConfigProperties;
import com.demo.hotel.webservice.client.HotelWebServiceClient;
import com.demo.hotel.webservice.client.dto.AddHotelAmenityRequest;
import com.demo.hotel.webservice.client.dto.AddHotelAmenityResponse;
import com.demo.hotel.webservice.client.dto.AddHotelRequest;
import com.demo.hotel.webservice.client.dto.AddHotelResponse;
import com.demo.hotel.webservice.client.dto.AmenityDto;
import com.demo.hotel.webservice.client.dto.DeleteHotelAmenityRequest;
import com.demo.hotel.webservice.client.dto.DeleteHotelAmenityResponse;
import com.demo.hotel.webservice.client.dto.DeleteHotelRequest;
import com.demo.hotel.webservice.client.dto.DeleteHotelResponse;
import com.demo.hotel.webservice.client.dto.GetAmenityListRequest;
import com.demo.hotel.webservice.client.dto.GetAmenityListResponse;
import com.demo.hotel.webservice.client.dto.GetHotelListRequest;
import com.demo.hotel.webservice.client.dto.GetHotelListResponse;
import com.demo.hotel.webservice.client.dto.GetHotelRequest;
import com.demo.hotel.webservice.client.dto.GetHotelResponse;
import com.demo.hotel.webservice.client.dto.HotelAmenityDto;
import com.demo.hotel.webservice.client.dto.HotelDto;
import com.demo.hotel.webservice.client.dto.HotelListDto;
import com.demo.hotel.webservice.client.dto.ResponseStatus;
import com.demo.hotel.webservice.client.dto.UpdateHotelRequest;
import com.demo.hotel.webservice.client.dto.UpdateHotelResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.demo.hotel.restcontroller.HotelRestControllerExceptionHandler.CONTENT_TYPE_NOT_SUPPORTED_ERROR;
import static com.demo.hotel.restcontroller.HotelRestControllerExceptionHandler.UNEXPECTED_ERROR;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HotelRestController.class)
@Import(WebConfigProperties.class)
class HotelRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelWebServiceClient hotelWebServiceClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getHotel() throws Exception {
        // scenario setup
        long hotelId = 1L;

        GetHotelResponse expectedResponse = new GetHotelResponse();
        HotelDto hotelDto = new HotelDto();
        hotelDto.setHotelId(hotelId);
        hotelDto.setHotelName("Hotel Name");
        hotelDto.setRating(4);
        hotelDto.setAddress("Address test");
        expectedResponse.setHotelDto(hotelDto);
        expectedResponse.setStatusCode(OK.value());
        expectedResponse.setMessage(OK.name());

        when(hotelWebServiceClient.process(any(GetHotelRequest.class), eq(GetHotelResponse.class)))
                .thenReturn(expectedResponse);

        // test
        ResultActions resultActions = mockMvc.perform(get("/hotels/{id}", hotelId));

        // verify results;
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

    }

    @Test
    void createHotel() throws Exception {
        // scenario setup
        long hotelId = 1L;

        HotelDto requestBody = new HotelDto();
        requestBody.setHotelName("Hotel Name");
        requestBody.setRating(4);
        requestBody.setAddress("Address test");


        AddHotelResponse expectedResponse = new AddHotelResponse();

        when(hotelWebServiceClient.process(any(AddHotelRequest.class), eq(AddHotelResponse.class)))
                .thenAnswer(input -> {
                    AddHotelRequest request1 = input.getArgument(0, AddHotelRequest.class);
                    expectedResponse.setHotelDto(request1.getHotelDto());
                    expectedResponse.getHotelDto().setHotelId(hotelId);
                    expectedResponse.setStatusCode(OK.value());
                    expectedResponse.setMessage(OK.name());
                    return expectedResponse;
                });

        // test
        ResultActions resultActions = mockMvc.perform(post("/hotels")
                .content(objectMapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // verify results;
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    void updateHotel() throws Exception {
        long hotelId = 1L;

        HotelDto requestBody = new HotelDto();
        requestBody.setHotelId(hotelId);
        requestBody.setHotelName("Hotel Name");
        requestBody.setRating(4);
        requestBody.setAddress("Address test");

        UpdateHotelResponse expectedResponse = new UpdateHotelResponse();

        when(hotelWebServiceClient.process(any(UpdateHotelRequest.class), eq(UpdateHotelResponse.class)))
                .thenAnswer(input -> {
                    UpdateHotelRequest request1 = input.getArgument(0, UpdateHotelRequest.class);
                    expectedResponse.setHotelDto(request1.getHotelDto());
                    expectedResponse.setStatusCode(OK.value());
                    expectedResponse.setMessage(OK.name());
                    return expectedResponse;
                });

        // test
        ResultActions resultActions = mockMvc.perform(put("/hotels")
                .content(objectMapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        // verify results;
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    void deleteHotel() throws Exception {
        // scenario setup
        long hotelId = 1L;

        DeleteHotelResponse expectedResponse = new DeleteHotelResponse();
        expectedResponse.setResult(true);
        expectedResponse.setStatusCode(OK.value());
        expectedResponse.setMessage(OK.name());

        when(hotelWebServiceClient.process(any(DeleteHotelRequest.class), eq(DeleteHotelResponse.class)))
                .thenReturn(expectedResponse);

        // test
        ResultActions resultActions = mockMvc.perform(delete("/hotels/{id}", hotelId));

        // verify results;
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    void getHotelList() throws Exception {


        GetHotelListResponse expectedResponse = new GetHotelListResponse();
        expectedResponse.setStatusCode(OK.value());
        expectedResponse.setMessage(OK.name());
        HotelListDto value = new HotelListDto();
        value.getHotelDtoList().addAll(LongStream.iterate(1, n -> n + 1)
                .limit(5)
                .mapToObj(n -> {
                    HotelDto dto = new HotelDto();
                    dto.setHotelId(n);
                    return dto;
                }).collect(Collectors.toList()));
        value.setTotalElements(10);
        value.setTotalPages(2);
        expectedResponse.setHotelListDto(value);

        when(hotelWebServiceClient.process(any(GetHotelListRequest.class), eq(GetHotelListResponse.class)))
                .thenReturn(expectedResponse);

        // test
        ResultActions resultActions = mockMvc.perform(get("/hotels")
                .queryParam("hotelName", "Hotel")
                .queryParam("page", "1")
                .queryParam("pageSize", "5"));

        // verify results;
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    void addHotelAmenities() throws Exception {
        long hotelId = 1L;
        long amenityId = 1L;

        AddHotelAmenityResponse expectedResponse = new AddHotelAmenityResponse();
        HotelAmenityDto value = new HotelAmenityDto();
        value.setAmenityId(amenityId);
        value.setHotelId(hotelId);
        value.setHotelAmenityId(10L);
        expectedResponse.setHotelAmenityDto(value);
        expectedResponse.setStatusCode(OK.value());
        expectedResponse.setMessage(OK.name());

        when(hotelWebServiceClient.process(any(AddHotelAmenityRequest.class), eq(AddHotelAmenityResponse.class)))
                .thenReturn(expectedResponse);

        // test
        ResultActions resultActions = mockMvc.perform(post("/hotels/{hotelId}/amenities/{amenityId}", hotelId, amenityId));

        // verify results;
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    void deleteHotelAmenities() throws Exception {
        long hotelId = 1L;
        long amenityId = 1L;

        DeleteHotelAmenityResponse expectedResponse = new DeleteHotelAmenityResponse();
        expectedResponse.setResult(true);
        expectedResponse.setStatusCode(OK.value());
        expectedResponse.setMessage(OK.name());

        when(hotelWebServiceClient.process(any(DeleteHotelAmenityRequest.class), eq(DeleteHotelAmenityResponse.class)))
                .thenReturn(expectedResponse);

        // test
        ResultActions resultActions = mockMvc.perform(delete("/hotels/{hotelId}/amenities/{amenityId}", hotelId, amenityId));

        // verify results;
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    void getHotelAmenities() throws Exception {
        long hotelId = 1L;

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
        ResultActions resultActions = mockMvc.perform(get("/hotels/{hotelId}/amenities", hotelId));

        // verify results;
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    void getHotel_unexpectedError() throws Exception {
        // scenario setup
        long hotelId = 1L;

        ResponseStatus expectedResponse = new ResponseStatus();
        expectedResponse.setStatusCode(INTERNAL_SERVER_ERROR.value());
        expectedResponse.setMessage(UNEXPECTED_ERROR);

        when(hotelWebServiceClient.process(any(GetHotelRequest.class), eq(GetHotelResponse.class)))
                .thenThrow(new RuntimeException("unexpectedError"));

        // test
        ResultActions resultActions = mockMvc.perform(get("/hotels/{id}", hotelId));

        // verify results;
        resultActions.andExpect(status().isInternalServerError())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

    }

    @Test
    void getHotel_httpMediaNotSupportedError() throws Exception {
        // scenario setup
        HotelDto requestBody = new HotelDto();
        requestBody.setHotelName("Hotel Name");
        requestBody.setRating(4);
        requestBody.setAddress("Address test");

        ResponseStatus expectedResponse = new ResponseStatus();
        expectedResponse.setStatusCode(BAD_REQUEST.value());
        expectedResponse.setMessage(CONTENT_TYPE_NOT_SUPPORTED_ERROR);

        // test
        ResultActions resultActions = mockMvc.perform(post("/hotels")
                .content(objectMapper.writeValueAsString(requestBody))
                .contentType(MediaType.APPLICATION_XML));

        // verify results;
        resultActions.andExpect(status().isInternalServerError())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

    }
}