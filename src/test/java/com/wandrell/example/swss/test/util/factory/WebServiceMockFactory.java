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

package com.wandrell.example.swss.test.util.factory;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.ws.soap.security.callback.AbstractCallbackHandler;

import com.sun.xml.wss.impl.callback.PasswordValidationCallback;
import com.sun.xml.wss.impl.callback.PasswordValidationCallback.PasswordValidationException;
import com.sun.xml.wss.impl.callback.PasswordValidationCallback.PasswordValidator;
import com.sun.xml.wss.impl.callback.TimestampValidationCallback;
import com.sun.xml.wss.impl.callback.TimestampValidationCallback.TimestampValidator;
import com.wandrell.example.swss.model.ExampleEntity;
import com.wandrell.example.swss.service.domain.ExampleEntityService;

/**
 * Factory which creates mocked dependencies for the web service endpoints.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
public final class WebServiceMockFactory {

    /**
     * Default constructor.
     */
    public WebServiceMockFactory() {
        super();
    }

    /**
     * Returns a mocked example entity domain service.
     *
     * @return a mocked domain service
     */
    public final ExampleEntityService getExampleEntityService() {
        final ExampleEntityService service; // Mocked service
        final ExampleEntity entity;         // Mocked returned entity

        // Mocks the entity
        entity = Mockito.mock(ExampleEntity.class);
        Mockito.when(entity.getId()).thenReturn(1);
        Mockito.when(entity.getName()).thenReturn("name");

        // Mocks the service
        service = Mockito.mock(ExampleEntityService.class);
        Mockito.when(service.findById(Matchers.anyInt())).thenReturn(entity);

        return service;
    }

    /**
     * Returns a mocked callback handler which validates any data.
     *
     * @return a mocked callback handler
     * @throws PasswordValidationException
     *             never, this is a required declaration
     */
    public final CallbackHandler getValidationCallbackHandler()
            throws PasswordValidationException {
        final CallbackHandler callbackHandler;     // Mocked handler
        final PasswordValidator passwordValidator; // Mocked validator

        passwordValidator = getPasswordValidator();

        callbackHandler = new AbstractCallbackHandler() {

            @Override
            protected void handleInternal(final Callback callback)
                    throws IOException, UnsupportedCallbackException {
                if (callback instanceof PasswordValidationCallback) {
                    ((PasswordValidationCallback) callback)
                            .setValidator(passwordValidator);
                } else if (callback instanceof WSPasswordCallback) {
                    // TODO:The callback handler should accept any password
                    // Where is this password being validated?
                    ((WSPasswordCallback) callback).setPassword("myPassword");
                } else if (callback instanceof TimestampValidationCallback) {
                    ((TimestampValidationCallback) callback)
                            .setValidator(getTimestampValidator());
                }
            }

        };

        return callbackHandler;
    }

    /**
     * Returns a mocked password validator.
     * <p>
     * This validates any password.
     *
     * @return a mocked password validator
     * @throws PasswordValidationException
     *             never, this is a required declaration
     */
    private final PasswordValidator getPasswordValidator()
            throws PasswordValidationException {
        final PasswordValidator passwordValidator; // Mocked validator

        passwordValidator = Mockito.mock(PasswordValidator.class);
        Mockito.when(passwordValidator.validate(
                Matchers.any(PasswordValidationCallback.Request.class)))
                .thenReturn(true);

        return passwordValidator;
    }

    /**
     * Returns a mocked timestamp validator.
     * <p>
     * This validates any timestamp.
     *
     * @return a mocked timestamp validator
     * @throws PasswordValidationException
     *             never, this is a required declaration
     */
    private final TimestampValidator getTimestampValidator() {
        final TimestampValidator passwordValidator; // Mocked validator

        passwordValidator = Mockito.mock(TimestampValidator.class);

        return passwordValidator;
    }

}
