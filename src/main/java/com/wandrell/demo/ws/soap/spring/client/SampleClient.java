package com.wandrell.demo.ws.soap.spring.client;

import java.math.BigDecimal;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.wandrell.demo.ws.generated.sample.GetSampleRequest;
import com.wandrell.demo.ws.generated.sample.GetSampleResponse;

/**
 * The Class WeatherClient.
 */
public class SampleClient extends WebServiceGatewaySupport {

    public GetSampleResponse getSample(final BigDecimal cod1,
            final BigDecimal cod2) {
        GetSampleRequest request;

        request = new GetSampleRequest();
        request.setCod1(cod1);
        request.setCod2(cod2);

        GetSampleResponse response = (GetSampleResponse) getWebServiceTemplate()
                .marshalSendAndReceive(
                        request,
                        new SoapActionCallback(
                                "http://localhost:9966/ws-security-simple/ws/samples"));

        return response;
    }

}