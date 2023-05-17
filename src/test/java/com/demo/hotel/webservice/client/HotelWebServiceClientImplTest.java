package com.demo.hotel.webservice.client;

import com.demo.hotel.webservice.client.dto.GetHotelRequest;
import com.demo.hotel.webservice.client.dto.GetHotelResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelWebServiceClientImplTest {

    private final HotelWebServiceClientImpl sut = new HotelWebServiceClientImpl("https://test.hotel.com/hotel-ws");

    @Mock
    private WebServiceTemplate webServiceTemplate;

    @Test
    void process() {

        GetHotelRequest request = new GetHotelRequest();
        GetHotelResponse response = new GetHotelResponse();
        when(webServiceTemplate.marshalSendAndReceive(eq(request), any(SoapActionCallback.class)))
                .thenReturn(response);
        sut.setWebServiceTemplate(webServiceTemplate);

        GetHotelResponse result = sut.process(request, GetHotelResponse.class);

        // Verify result
        Assertions.assertNotNull(result);

    }
}