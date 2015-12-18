package com.wandrell.demo.ws.soap.spring.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.wandrell.demo.ws.generated.sample.GetSampleRequest;
import com.wandrell.demo.ws.generated.sample.GetSampleResponse;
import com.wandrell.demo.ws.soap.spring.model.Sample;
import com.wandrell.demo.ws.soap.spring.model.SamplePk;
import com.wandrell.demo.ws.soap.spring.repository.SampleRepository;

/**
 * The Class SampleEndpoint.
 */
@Endpoint
public class SampleEndpoint {

    /** The Constant NAMESPACE_URI. */
    private static final String NAMESPACE_URI = "http://wandrell.com/demo/ws/sample";

    /** The sample repository. */
    private SampleRepository sampleRepository;

    /**
     * Instantiates a new sample endpoint.
     *
     * @param sampleRepository
     *            the sample repository
     */
    @Autowired
    public SampleEndpoint(final SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    /**
     * Gets the sample.
     *
     * @param request
     *            the request
     * @return the sample
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getSampleRequest")
    @ResponsePayload
    public GetSampleResponse getSample(GetSampleRequest request) {
        final GetSampleResponse response;
        final SamplePk samplePk;
        final Sample sample;
        final com.wandrell.demo.ws.generated.sample.Sample sampleResponse;

        response = new GetSampleResponse();

        samplePk = new SamplePk();
        samplePk.setCod1(request.getCod1());
        samplePk.setCod2(request.getCod2());
        sample = sampleRepository.findOne(samplePk);

        if (sample != null) {
            sampleResponse = new com.wandrell.demo.ws.generated.sample.Sample();
            sampleResponse.setCod1(sample.getSamplePk().getCod1());
            sampleResponse.setCod2(sample.getSamplePk().getCod2());
            sampleResponse.setDescription(sample.getDescription());
            sampleResponse.setExtra(sample.getExtra());
            response.setSample(sampleResponse);
        }

        return response;
    }
}