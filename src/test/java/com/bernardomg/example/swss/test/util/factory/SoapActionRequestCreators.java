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

package com.bernardomg.example.swss.test.util.factory;

import java.io.IOException;

import javax.xml.transform.Source;

import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.test.server.RequestCreator;
import org.springframework.ws.test.support.creator.PayloadMessageCreator;
import org.springframework.ws.test.support.creator.SoapEnvelopeMessageCreator;
import org.springframework.ws.test.support.creator.WebServiceMessageCreator;
import org.springframework.xml.transform.ResourceSource;

/**
 * Factory methods for {@link RequestCreator} classes which include a SOAP
 * action into the generated request.
 * <p>
 * This is required to make some of the unit tests for certain security
 * protocols, mostly the encrypted SOAP messages, work. Without a SOAP action
 * the endpoint won't recognize the request.
 * 
 * @author Bernardo Mart&iacute;nez Garrido
 */
public final class SoapActionRequestCreators {

    /**
     * Adapts a {@link WebServiceMessageCreator} to the {@link RequestCreator}
     * contract including a SOAP action.
     * 
     * @author Bernardo Mart&iacute;nez Garrido
     */
    private static class SoapActionWebServiceMessageCreatorAdapter
            implements RequestCreator {

        /**
         * Action to be added into the request.
         */
        private final String                   action;

        /**
         * Adapted message creator.
         */
        private final WebServiceMessageCreator adaptee;

        /**
         * Creates an adapter with for the specified
         * {@code WebServiceMessageCreator} and adding the received SOAP action.
         * 
         * @param action
         *            the SOAP action to add into the request
         * @param adaptee
         *            the message creator to adapt
         */
        public SoapActionWebServiceMessageCreatorAdapter(final String action,
                final WebServiceMessageCreator adaptee) {
            this.adaptee = adaptee;
            this.action = action;
        }

        @Override
        public final WebServiceMessage createRequest(
                WebServiceMessageFactory messageFactory) throws IOException {
            final WebServiceMessage message; // Message to create

            message = adaptee.createMessage(messageFactory);
            if (message instanceof SoapMessage) {
                ((SoapMessage) message).setSoapAction(action);
            }

            return message;
        }

    }

    /**
     * Create a request with the given {@link Resource} XML as payload, and the
     * received string as action.
     *
     * @param action
     *            the SOAP action
     * @param payload
     *            the request payload
     * @return the request creator
     */
    public static RequestCreator withPayload(final String action,
            final Resource payload) throws IOException {
        return withPayload(action, new ResourceSource(payload));
    }

    /**
     * Create a request with the given {@link Source} XML as payload, and the
     * received string as action.
     *
     * @param action
     *            the SOAP action
     * @param payload
     *            the request payload
     * @return the request creator
     */
    public static RequestCreator withPayload(final String action,
            final Source payload) throws IOException {
        Assert.notNull(action, "Received a null pointer as action");
        Assert.notNull(payload, "Received a null pointer as payload");
        return new SoapActionWebServiceMessageCreatorAdapter(action,
                new PayloadMessageCreator(payload));
    }

    /**
     * Create a request with the given {@link Resource} XML as SOAP envelope,
     * and the received string as action.
     *
     * @param action
     *            the SOAP action
     * @param soapEnvelope
     *            the request SOAP envelope
     * @return the request creator
     */
    public static RequestCreator withSoapEnvelope(final String action,
            final Resource soapEnvelope) throws IOException {
        return withSoapEnvelope(action, new ResourceSource(soapEnvelope));
    }

    /**
     * Create a request with the given {@link Source} XML as SOAP envelope, and
     * the received string as action.
     *
     * @param action
     *            the SOAP action
     * @param soapEnvelope
     *            the request SOAP envelope
     * @return the request creator
     */
    public static RequestCreator withSoapEnvelope(final String action,
            final Source soapEnvelope) throws IOException {
        Assert.notNull(action, "Received a null pointer as action");
        Assert.notNull(soapEnvelope, "Received a null pointer as envelope");
        return new SoapActionWebServiceMessageCreatorAdapter(action,
                new SoapEnvelopeMessageCreator(soapEnvelope));
    }

    /**
     * Private constructor to avoid initialization.
     */
    private SoapActionRequestCreators() {
        super();
    }

}
