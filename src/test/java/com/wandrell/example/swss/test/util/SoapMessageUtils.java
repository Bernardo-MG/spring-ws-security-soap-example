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

package com.wandrell.example.swss.test.util;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.springframework.core.io.ClassPathResource;

import com.wandrell.example.swss.generated.entity.Entity;
import com.wandrell.example.swss.generated.entity.GetEntityResponse;

/**
 * SOAP message generation utils.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
public final class SoapMessageUtils {

    /**
     * Creates an {@code Entity} from the received {@code SOAPMessage}.
     * <p>
     * The message should be valid and contain a {@code GetEntityResponse}.
     *
     * @param message
     *            the SOAP message
     * @return an {@code Entity} parsed from the {@code SOAPMessage}
     * @throws JAXBException
     *             if there is any problem when unmarshalling the data
     * @throws SOAPException
     *             if there is any problem when reading the SOAP message
     */
    public static final Entity getEntity(final SOAPMessage message)
            throws JAXBException, SOAPException {
        final JAXBContext jc; // Context for unmarshalling
        final Unmarshaller um; // Unmarshaller for the SOAP message
        final GetEntityResponse response; // Unmarshalled response

        jc = JAXBContext.newInstance(GetEntityResponse.class);
        um = jc.createUnmarshaller();
        response = (GetEntityResponse) um
                .unmarshal(message.getSOAPBody().extractContentAsDocument());

        return response.getEntity();
    }

    /**
     * Creates a {@code SOAPMessage} from the contents of a text file.
     * <p>
     * This file should contain a valid SOAP message.
     *
     * @param path
     *            the path to the file
     * @return a {@code SOAPMessage} parsed from the contents of the file
     * @throws SOAPException
     *             if there is any problem when generating the SOAP message
     * @throws IOException
     *             if there is any problem when reading the file
     */
    public static final SOAPMessage getMessage(final String path)
            throws SOAPException, IOException {
        final MessageFactory factory; // Factory for generating the message
        final InputStream streamFile; // Stream for the file contents

        streamFile = new ClassPathResource(path).getInputStream();
        factory = MessageFactory.newInstance();

        return factory.createMessage(null, streamFile);
    }

    /**
     * Private constructor to avoid initialization.
     */
    private SoapMessageUtils() {
        super();
    }

}
