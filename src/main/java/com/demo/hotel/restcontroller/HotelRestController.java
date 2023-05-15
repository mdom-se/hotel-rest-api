package com.demo.hotel.restcontroller;

import com.demo.hotel.webservice.client.HotelWSClient;
import com.demo.hotel.webservice.client.dto.AddHotelRequest;
import com.demo.hotel.webservice.client.dto.AddHotelResponse;
import com.demo.hotel.webservice.client.dto.DeleteHotelRequest;
import com.demo.hotel.webservice.client.dto.DeleteHotelResponse;
import com.demo.hotel.webservice.client.dto.GetHotelListRequest;
import com.demo.hotel.webservice.client.dto.GetHotelListResponse;
import com.demo.hotel.webservice.client.dto.GetHotelRequest;
import com.demo.hotel.webservice.client.dto.GetHotelResponse;
import com.demo.hotel.webservice.client.dto.HotelDto;
import com.demo.hotel.webservice.client.dto.HotelListDto;
import com.demo.hotel.webservice.client.dto.UpdateHotelRequest;
import com.demo.hotel.webservice.client.dto.UpdateHotelResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotels")
public class HotelRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelRestController.class);

    private final HotelWSClient hotelWSClient;

    public HotelRestController(HotelWSClient hotelWSClient) {
        this.hotelWSClient = hotelWSClient;
    }

    @GetMapping(value = "/{hotelId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HotelDto> getHotel(@PathVariable Long hotelId) {
        ResponseEntity<HotelDto> responseEntity;
        try {
            GetHotelRequest request = new GetHotelRequest();
            request.setHotelId(hotelId);
            GetHotelResponse response = hotelWSClient.getHotel(request);
            responseEntity = ResponseEntity.ok(response.getHotelDto());
        } catch (Exception ex) {
            LOGGER.error("Error getHotel", ex);
            responseEntity = ResponseEntity.internalServerError().build();
        }
        return responseEntity;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HotelDto> createHotel(@RequestBody HotelDto hotelDto) {
        ResponseEntity<HotelDto> responseEntity;
        try {
            AddHotelRequest request = new AddHotelRequest();
            request.setHotelDto(hotelDto);
            AddHotelResponse response = hotelWSClient.addHotel(request);
            responseEntity = ResponseEntity.ok(response.getHotelDto());
        } catch (Exception ex) {
            LOGGER.error("Error createHotel", ex);
            responseEntity = ResponseEntity.internalServerError().build();
        }
        return responseEntity;
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HotelDto> updateHotel(@RequestBody HotelDto hotelDto) {
        ResponseEntity<HotelDto> responseEntity;
        try {
            UpdateHotelRequest request = new UpdateHotelRequest();
            request.setHotelDto(hotelDto);
            UpdateHotelResponse response = hotelWSClient.updateHotel(request);
            responseEntity = ResponseEntity.ok(response.getHotelDto());
        } catch (Exception ex) {
            LOGGER.error("Error updateHotel", ex);
            responseEntity = ResponseEntity.internalServerError().build();
        }
        return responseEntity;
    }

    @DeleteMapping(value = "/{hotelId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteHotel(@PathVariable Long hotelId) {
        ResponseEntity<Boolean> responseEntity;
        try {
            DeleteHotelRequest request = new DeleteHotelRequest();
            request.setHotelId(hotelId);
            DeleteHotelResponse response = hotelWSClient.deleteHotel(request);
            //@TODO add a dto to return the result of the delete
            responseEntity = ResponseEntity.ok(response.isResult());
        } catch (Exception ex) {
            LOGGER.error("Error deleteHotel", ex);
            responseEntity = ResponseEntity.internalServerError().build();
        }
        return responseEntity;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HotelListDto> getHotelList(@RequestParam(required = false) String hotelName,
                                                     @RequestParam int page,
                                                     @RequestParam int pageSize) {
        ResponseEntity<HotelListDto> responseEntity;
        try {
            GetHotelListRequest request = new GetHotelListRequest();
            request.setHotelName(hotelName);
            request.setPage(page);
            request.setSize(pageSize);
            GetHotelListResponse response = hotelWSClient.getHotelList(request);
            responseEntity = ResponseEntity.ok(response.getHotelListDto());
        } catch (Exception ex) {
            LOGGER.error("Error getHotelList", ex);
            responseEntity = ResponseEntity.internalServerError().build();
        }
        return responseEntity;
    }
}
