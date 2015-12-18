package com.wandrell.demo.ws.soap.spring.testing.integration.ws.unsecure;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

import com.wandrell.demo.ws.soap.spring.testing.config.WSPathConfig;

/**
 * Basis for an integration test.
 *
 * Requires the WS to be running.
 *
 * @author Bernardo Martínez Garrido
 */
public class ITSampleEndpointSecured {

    /**
     * Logger for the tests.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ITSampleEndpointSecured.class);

    public ITSampleEndpointSecured() {
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

        soapConnectionFactory = SOAPConnectionFactory.newInstance();
        soapConnection = soapConnectionFactory.createConnection();

        message = soapConnection.call(createSOAPRequest(),
                WSPathConfig.ENDPOINT_SAMPLES);

        out = new ByteArrayOutputStream();
        message.writeTo(out);
        messageStr = new String(out.toByteArray());
        LOGGER.debug(String.format("Response SOAP Message = \n%s", messageStr));

        Assert.assertTrue(!messageStr.contains("faultcode"));
    }

    /**
     * Creates a SOAP message like this:
     * <p>
     *
     * <pre>
     *    {@code
     * <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
     *     xmlns:sam="http://wandrell.com/demo/ws/samples">
     *     <soapenv:Header>
     *         <wsse:Security
     *             xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"
     *             soapenv:mustUnderstand="1">
     *             <wsse:UsernameToken
     *                 xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd"
     *                 wsu:Id="XWSSGID-14072105829651149256297">
     *                 <wsse:Username>user</wsse:Username>
     *                 <wsse:Password
     *                     Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">password</wsse:Password>
     *             </wsse:UsernameToken>
     *         </wsse:Security>
     *     </soapenv:Header>
     *     <soapenv:Body>
     *         <sam:getSampleRequest>
     *             <sam:cod1>0.1</sam:cod1>
     *             <sam:cod2>2</sam:cod2>
     *         </sam:getSampleRequest>
     *     </soapenv:Body>
     * </soapenv:Envelope>
     *    }
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
                .getResourceAsStream("/soap/sample-secured.xml");

        factory = MessageFactory.newInstance();

        message = factory.createMessage(new MimeHeaders(), streamFile);

        out = new ByteArrayOutputStream();
        message.writeTo(out);
        messageStr = new String(out.toByteArray());
        LOGGER.debug(String.format("Request SOAP Message = \n%s", messageStr));

        return message;
    }

}
