package com.demo.hotel.webservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class WSClientConfiguration {

    @Value("${hotel-backend.webservice.url}")
    private String hotelBackendWSUrl;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.demo.hotel.webservice.client.dto");
        return marshaller;
    }

    @Bean
    public HotelWSClient hotelWSClient(Jaxb2Marshaller marshaller) {
        HotelWSClient client = new HotelWSClient(hotelBackendWSUrl);
        client.setDefaultUri(hotelBackendWSUrl);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
