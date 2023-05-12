package com.demo.hotel.webservice.client;

import com.demo.hotel.webservice.client.dto.AddAmenityRequest;
import com.demo.hotel.webservice.client.dto.AddAmenityResponse;
import com.demo.hotel.webservice.client.dto.AddHotelRequest;
import com.demo.hotel.webservice.client.dto.AddHotelResponse;
import com.demo.hotel.webservice.client.dto.DeleteAmenityRequest;
import com.demo.hotel.webservice.client.dto.DeleteAmenityResponse;
import com.demo.hotel.webservice.client.dto.DeleteHotelRequest;
import com.demo.hotel.webservice.client.dto.DeleteHotelResponse;
import com.demo.hotel.webservice.client.dto.GetAmenityListRequest;
import com.demo.hotel.webservice.client.dto.GetAmenityListResponse;
import com.demo.hotel.webservice.client.dto.GetHotelListRequest;
import com.demo.hotel.webservice.client.dto.GetHotelListResponse;
import com.demo.hotel.webservice.client.dto.GetHotelRequest;
import com.demo.hotel.webservice.client.dto.GetHotelResponse;
import com.demo.hotel.webservice.client.dto.UpdateHotelRequest;
import com.demo.hotel.webservice.client.dto.UpdateHotelResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class HotelWSClient extends WebServiceGatewaySupport {

    private static final String TARGET_NAMESPACE = "http://demo.hotel.com/hotel-ws/%s";
    private String webServiceUrl;

    public HotelWSClient(String webServiceUrl) {
        this.webServiceUrl = webServiceUrl;
    }

    /**
     * @param request AddHotelRequest
     * @return AddHotelResponse
     */
    public AddHotelResponse addHotel(AddHotelRequest request) {
        AddHotelResponse response = (AddHotelResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SoapActionCallback(String.format(TARGET_NAMESPACE, AddHotelResponse.class.getSimpleName())));
        return response;
    }

    /**
     * @param request UpdateHotelRequest
     * @return UpdateHotelResponse
     */
    public UpdateHotelResponse updateHotel(UpdateHotelRequest request) {
        UpdateHotelResponse response = (UpdateHotelResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SoapActionCallback(String.format(TARGET_NAMESPACE, UpdateHotelResponse.class.getSimpleName())));
        return response;
    }

    /**
     * @param request DeleteHotelRequest
     * @return DeleteHotelResponse
     */
    public DeleteHotelResponse deleteHotel(DeleteHotelRequest request) {
        DeleteHotelResponse response = (DeleteHotelResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SoapActionCallback(String.format(TARGET_NAMESPACE, DeleteHotelResponse.class.getSimpleName())));
        return response;
    }

    /**
     * @param request GetHotelRequest
     * @return GetHotelResponse
     */
    public GetHotelResponse getHotel(GetHotelRequest request) {
        GetHotelResponse response = (GetHotelResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SoapActionCallback(String.format(TARGET_NAMESPACE, GetHotelResponse.class.getSimpleName())));
        return response;
    }

    /**
     * @param request GetHotelListRequest
     * @return GetHotelListResponse
     */
    public GetHotelListResponse getHotelList(GetHotelListRequest request) {
        GetHotelListResponse response = (GetHotelListResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SoapActionCallback(String.format(TARGET_NAMESPACE, GetHotelListResponse.class.getSimpleName())));
        return response;
    }

    /**
     * @param request AddAmenityRequest
     * @return AddAmenityResponse
     */
    public AddAmenityResponse addAmenity(AddAmenityRequest request) {
        AddAmenityResponse response = (AddAmenityResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SoapActionCallback(String.format(TARGET_NAMESPACE, AddAmenityResponse.class.getSimpleName())));
        return response;
    }

    /**
     * @param request DeleteAmenityRequest
     * @return DeleteAmenityResponse
     */
    public DeleteAmenityResponse deleteAmenity(DeleteAmenityRequest request) {
        DeleteAmenityResponse response = (DeleteAmenityResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SoapActionCallback(String.format(TARGET_NAMESPACE, DeleteAmenityResponse.class.getSimpleName())));
        return response;
    }

    /**
     * @param request GetAmenityListRequest
     * @return GetAmenityListResponse
     */
    public GetAmenityListResponse getAmenityList(GetAmenityListRequest request) {
        GetAmenityListResponse response = (GetAmenityListResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SoapActionCallback(String.format(TARGET_NAMESPACE, GetAmenityListResponse.class.getSimpleName())));
        return response;
    }

}
