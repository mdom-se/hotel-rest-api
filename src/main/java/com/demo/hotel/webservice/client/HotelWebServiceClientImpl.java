package com.demo.hotel.webservice.client;

import com.demo.hotel.webservice.client.dto.ResponseStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;


@Service
public class HotelWebServiceClientImpl extends WebServiceGatewaySupport implements HotelWebServiceClient {

    private static final String TARGET_NAMESPACE = "http://demo.hotel.com/hotel-ws/%s";

    public HotelWebServiceClientImpl(@Value("${hotel-backend.webservice.url}") String hotelBackendWSUrl) {
        super.setDefaultUri(hotelBackendWSUrl);
        final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.demo.hotel.webservice.client.dto");
        super.setMarshaller(marshaller);
        super.setUnmarshaller(marshaller);
    }

    public <T extends ResponseStatus> T process(Object request, Class<T> returnType) {
        Object receive = getWebServiceTemplate()
                .marshalSendAndReceive(request,
                        new SoapActionCallback(String.format(TARGET_NAMESPACE, request.getClass().getSimpleName())));
        return returnType.cast(receive);
    }

}
