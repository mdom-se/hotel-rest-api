package com.demo.hotel.webservice.client;

import com.demo.hotel.webservice.client.dto.ResponseStatus;

public interface HotelWebServiceClient {
    public <T extends ResponseStatus> T process(Object request, Class<T> returnType);
}
