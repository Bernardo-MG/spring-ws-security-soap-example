package com.wandrell.example.ws.soap.spring.testing.integration.ws.unsecure;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wandrell.example.ws.generated.entity.Entity;
import com.wandrell.example.ws.generated.entity.GetEntityResponse;
import com.wandrell.example.ws.soap.spring.testing.config.WSPathConfig;

/**
 * Basis for an integration test.
 *
 * Requires the WS to be running.
 *
 * @author Bernardo Martínez Garrido
 */
public final class ITSampleEndpointNotSecured {

    /**
     * Logger for the tests.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ITSampleEndpointNotSecured.class);

    public ITSampleEndpointNotSecured() {
        super();
    }

    @Test
    public final void testEndpoint() throws UnsupportedOperationException,
            SOAPException, IOException {
        final SOAPConnectionFactory soapConnectionFactory;
        final SOAPConnection soapConnection;
        final SOAPMessage message;
        final String messageStr;
        final ByteArrayOutputStream out;
        final Entity entity;

        soapConnectionFactory = SOAPConnectionFactory.newInstance();
        soapConnection = soapConnectionFactory.createConnection();

        message = soapConnection.call(createSOAPRequest(),
                WSPathConfig.ENDPOINT_ENTITIES);

        out = new ByteArrayOutputStream();
        message.writeTo(out);
        messageStr = new String(out.toByteArray());
        LOGGER.debug(String.format("Response SOAP Message = \n%s", messageStr));

        Assert.assertTrue(!messageStr.contains("faultcode"));

        try {
            entity = parseResponse(message);

            Assert.assertEquals(entity.getId(), 1);
            Assert.assertEquals(entity.getName(), "entity_1");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a SOAP message like this:
     * <p>
     *
     * <pre>
     *   {@code
     * <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:sam="http://wandrell.com/demo/ws/samples">
     *    <soapenv:Header/>
     *    <soapenv:Body>
     *       <sam:getSampleRequest>
     *          <sam:cod1>0.1</sam:cod1>
     *          <sam:cod2>0.2</sam:cod2>
     *          <sam:description>text</sam:description>
     *          <sam:extra>more text</sam:extra>
     *       </sam:getSampleRequest>
     *    </soapenv:Body>
     * </soapenv:Envelope>
     *   }
     * </pre>
     *
     * @return
     * @throws SOAPException
     * @throws IOException
     */
    private final SOAPMessage createSOAPRequest() throws SOAPException,
            IOException {
        final MessageFactory factory;
        final SOAPMessage message;
        final InputStream streamFile;
        final ByteArrayOutputStream out;
        final String messageStr;

        streamFile = ClassLoader.class
                .getResourceAsStream("/soap/entity-not-secured.xml");

        factory = MessageFactory.newInstance();

        message = factory.createMessage(new MimeHeaders(), streamFile);

        out = new ByteArrayOutputStream();
        message.writeTo(out);
        messageStr = new String(out.toByteArray());
        LOGGER.debug(String.format("Request SOAP Message = \n%s", messageStr));

        return message;
    }

    private final Entity parseResponse(final SOAPMessage message)
            throws JAXBException, SOAPException {
        final JAXBContext jc;
        final Unmarshaller um;
        final GetEntityResponse response;

        jc = JAXBContext.newInstance(GetEntityResponse.class);
        um = jc.createUnmarshaller();
        response = (GetEntityResponse) um.unmarshal(message.getSOAPBody()
                .extractContentAsDocument());

        return response.getEntity();
    }

}
