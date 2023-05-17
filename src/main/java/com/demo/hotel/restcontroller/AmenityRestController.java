package com.demo.hotel.restcontroller;

import com.demo.hotel.webservice.client.HotelWebServiceClient;
import com.demo.hotel.webservice.client.dto.GetAmenityListRequest;
import com.demo.hotel.webservice.client.dto.GetAmenityListResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/amenities")
public class AmenityRestController {

    private final HotelWebServiceClient hotelWebServiceClient;

    public AmenityRestController(HotelWebServiceClient hotelWebServiceClient) {
        this.hotelWebServiceClient = hotelWebServiceClient;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetAmenityListResponse> getAmenityList() {
        GetAmenityListResponse response = hotelWebServiceClient.process(new GetAmenityListRequest(), GetAmenityListResponse.class);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);

    }

}
