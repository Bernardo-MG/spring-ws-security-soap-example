/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2015-2017 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.wandrell.example.swss.test.integration.endpoint.signature.xwss;

import java.security.KeyStore;

import javax.xml.soap.SOAPMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.wandrell.example.swss.test.util.SoapMessageUtils;
import com.wandrell.example.swss.test.util.config.context.TestContextPaths;
import com.wandrell.example.swss.test.util.config.properties.SoapPropertiesPaths;
import com.wandrell.example.swss.test.util.config.properties.TestEndpointXwssPropertiesPaths;
import com.wandrell.example.swss.test.util.config.properties.TestPropertiesPaths;
import com.wandrell.example.swss.test.util.test.integration.endpoint.AbstractITEndpoint;

/**
 * Integration test for a signature protected endpoint using XWSS.
 * <p>
 * Pay attention to the fact that it requires the WS to be running.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@ContextConfiguration(locations = { TestContextPaths.KEYSTORE })
@TestPropertySource({ TestPropertiesPaths.KEYSTORE,
        SoapPropertiesPaths.SIGNATURE,
        TestEndpointXwssPropertiesPaths.SIGNATURE })
public final class ITEntityEndpointSignatureXwss extends AbstractITEndpoint {

    /**
     * Alias for the certificate for signing messages.
     */
    @Value("${keystore.alias}")
    private String   alias;

    /**
     * Key store for signing messages.
     */
    @Autowired
    @Qualifier("keyStore")
    private KeyStore keystore;

    /**
     * Password for the certificate for signing messages.
     */
    @Value("${keystore.password}")
    private String   password;

    /**
     * Path to the file containing the invalid SOAP request.
     */
    @Value("${soap.request.invalid.path}")
    private String   pathInvalid;

    /**
     * Default constructor.
     */
    public ITEntityEndpointSignatureXwss() {
        super();
    }

    @Override
    protected final SOAPMessage getInvalidSoapMessage() throws Exception {
        return SoapMessageUtils.getMessage(pathInvalid);
    }

    @Override
    protected final SOAPMessage getValidSoapMessage() throws Exception {
        // TODO: Get a valid signed SOAP message

        // return SecurityUtils.getSignedMessage(pathUnsigned,
        // alias, password, alias, keystore);
        return null;
    }

}
