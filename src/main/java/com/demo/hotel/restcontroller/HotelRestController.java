package com.demo.hotel.restcontroller;

import com.demo.hotel.webservice.client.HotelWebServiceClient;
import com.demo.hotel.webservice.client.dto.AddHotelAmenityRequest;
import com.demo.hotel.webservice.client.dto.AddHotelAmenityResponse;
import com.demo.hotel.webservice.client.dto.AddHotelRequest;
import com.demo.hotel.webservice.client.dto.AddHotelResponse;
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
import com.demo.hotel.webservice.client.dto.UpdateHotelRequest;
import com.demo.hotel.webservice.client.dto.UpdateHotelResponse;
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

    private final HotelWebServiceClient hotelWebServiceClient;

    public HotelRestController(HotelWebServiceClient hotelWebServiceClient) {
        this.hotelWebServiceClient = hotelWebServiceClient;
    }

    @GetMapping(value = "/{hotelId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetHotelResponse> getHotel(@PathVariable Long hotelId) {
        GetHotelRequest request = new GetHotelRequest();
        request.setHotelId(hotelId);
        GetHotelResponse response = hotelWebServiceClient.process(request, GetHotelResponse.class);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddHotelResponse> createHotel(@RequestBody HotelDto hotelDto) {
        AddHotelRequest request = new AddHotelRequest();
        request.setHotelDto(hotelDto);
        AddHotelResponse response = hotelWebServiceClient.process(request, AddHotelResponse.class);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);

    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateHotelResponse> updateHotel(@RequestBody HotelDto hotelDto) {
        UpdateHotelRequest request = new UpdateHotelRequest();
        request.setHotelDto(hotelDto);
        UpdateHotelResponse response = hotelWebServiceClient.process(request, UpdateHotelResponse.class);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @DeleteMapping(value = "/{hotelId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeleteHotelResponse> deleteHotel(@PathVariable Long hotelId) {
        DeleteHotelRequest request = new DeleteHotelRequest();
        request.setHotelId(hotelId);
        DeleteHotelResponse response = hotelWebServiceClient.process(request, DeleteHotelResponse.class);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetHotelListResponse> getHotelList(@RequestParam(required = false) String hotelName,
                                                     @RequestParam int page,
                                                     @RequestParam int pageSize) {
        GetHotelListRequest request = new GetHotelListRequest();
        request.setHotelName(hotelName);
        request.setPage(page);
        request.setPageSize(pageSize);
        GetHotelListResponse response = hotelWebServiceClient.process(request, GetHotelListResponse.class);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }


    @GetMapping(value = "/{hotelId}/amenities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetAmenityListResponse> addHotelAmenities(@PathVariable Long hotelId) {
        GetAmenityListRequest request = new GetAmenityListRequest();
        request.setHotelId(hotelId);
        GetAmenityListResponse response = hotelWebServiceClient.process(request, GetAmenityListResponse.class);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);

    }
    @PostMapping(value = "/{hotelId}/amenities/{amenityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddHotelAmenityResponse> addHotelAmenities(@PathVariable Long hotelId,
                                                             @PathVariable Long amenityId) {
        AddHotelAmenityRequest request = new AddHotelAmenityRequest();
        HotelAmenityDto dto = new HotelAmenityDto();
        dto.setHotelId(hotelId);
        dto.setAmenityId(amenityId);
        request.setHotelAmenityDto(dto);
        AddHotelAmenityResponse response = hotelWebServiceClient.process(request, AddHotelAmenityResponse.class);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);

    }


    @DeleteMapping(value = "/{hotelId}/amenities/{amenityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeleteHotelAmenityResponse> deleteHotelAmenities(@PathVariable Long hotelId,
                                                          @PathVariable Long amenityId) {
        DeleteHotelAmenityRequest request = new DeleteHotelAmenityRequest();
        HotelAmenityDto dto = new HotelAmenityDto();
        dto.setHotelId(hotelId);
        dto.setAmenityId(amenityId);
        request.setHotelAmenityDto(dto);
        DeleteHotelAmenityResponse response = hotelWebServiceClient.process(request, DeleteHotelAmenityResponse.class);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);

    }
}
