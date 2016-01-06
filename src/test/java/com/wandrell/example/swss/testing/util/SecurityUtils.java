/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2015 the original author or authors.
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

package com.wandrell.example.swss.testing.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Utilities class for security procedures.
 *
 * @author Bernardo Martínez Garrido
 */
public final class SecurityUtils {

    /**
     * Creates a SOAP message with a digested password, username and nonce.
     * <p>
     * A freemarker template will be used for this. It will receive the
     * generated values, and give a basic structure to the message.
     *
     * @param path
     *            path to the freemarker template
     * @param password
     *            password to use
     * @return the text content for the passworded message
     * @return a SOAP message with a digested password, username and nonce
     * @throws Exception
     *             when any error occurs during the message creation
     */
    public static final SOAPMessage getDigestedPasswordMessage(
            final String path, final String user, final String password)
                    throws Exception {
        final MessageFactory factory;
        final SOAPMessage message;
        final InputStream streamMessage;
        final ByteArrayOutputStream out;

        streamMessage = new ByteArrayInputStream(
                getMessageContent(path, user, password).getBytes("UTF-8"));

        factory = MessageFactory.newInstance();

        message = factory.createMessage(new MimeHeaders(), streamMessage);

        out = new ByteArrayOutputStream();
        message.writeTo(out);

        return message;
    }

    /**
     * Generates the digest value for the SOAP secure header.
     * <p>
     * This is a codified password, with the help of the date and nonce values.
     * Both of these values should be found on the SOAP secure header.
     *
     * @param password
     *            password to digest
     * @param date
     *            date used on the SOAP header
     * @param nonce
     *            nonce used on the SOAP header
     * @return the digested password
     * @throws UnsupportedEncodingException
     *             if the UTF-8 encoding is not supported
     */
    private static final String generateDigest(final String password,
            final String date, final String nonce)
                    throws UnsupportedEncodingException {
        final ByteBuffer buf;
        byte[] toHash;
        byte[] hash;

        buf = ByteBuffer.allocate(1000);
        buf.put(Base64.decodeBase64(nonce));
        buf.put(date.getBytes("UTF-8"));
        buf.put(password.getBytes("UTF-8"));
        toHash = new byte[buf.position()];
        buf.rewind();
        buf.get(toHash);
        hash = DigestUtils.sha1(toHash);

        return Base64.encodeBase64String(hash);
    }

    /**
     * Generates the date value for the SOAP secure header.
     *
     * @return the date value for the header
     */
    private static final String getDate() {
        final DateFormat zuluTime;
        final String date;

        zuluTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        zuluTime.setTimeZone(TimeZone.getTimeZone("UTC"));

        date = zuluTime.format(new Date());

        return date;
    }

    /**
     * Generates the text content for the passworded message.
     * <p>
     * This will be created from a freemarker template.
     *
     * @param path
     *            path to the freemarker template
     * @param user
     *            username to use
     * @param password
     *            password to use
     * @return the text content for the passworded message
     * @throws Exception
     *             if any error occurs during the message creation
     */
    private static final String getMessageContent(final String path,
            final String user, final String password) throws Exception {
        final String nonce;
        final String date;
        final String digest;
        final Configuration cfg;
        final Template template;
        final Map<String, Object> data;
        final ByteArrayOutputStream out;

        cfg = new Configuration(Configuration.VERSION_2_3_0);
        template = cfg.getTemplate(path);

        nonce = getNonce();
        date = getDate();
        digest = generateDigest(password, date, nonce);

        data = new LinkedHashMap<String, Object>();

        data.put("user", user);
        data.put("password", password);
        data.put("nonce", nonce);
        data.put("date", date);
        data.put("digest", digest);

        out = new ByteArrayOutputStream();
        template.process(data, new OutputStreamWriter(out));

        return new String(out.toByteArray());
    }

    /**
     * Generates the nonce value for the SOAP secure header.
     *
     * @return the nonce value for the header
     * @throws Exception
     *             if any error occurs while generating the nonce
     */
    private static final String getNonce() throws Exception {
        final SecureRandom random;
        final byte[] nonceBytes;

        random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(System.currentTimeMillis());
        nonceBytes = new byte[16];
        random.nextBytes(nonceBytes);

        return new String(Base64.encodeBase64(nonceBytes), "UTF-8");
    }

    /**
     * Private constructor to avoid initialization.
     */
    private SecurityUtils() {
        super();
    }

}
