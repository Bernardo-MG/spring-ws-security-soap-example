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

package com.wandrell.example.swss.security;

import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Executable class for generating the key stores used on the tests.
 * <p>
 * This is to be used in case the key stores are to be rebuilt for any reason.
 *
 * @author Bernardo Martínez Garrido
 */
public final class KeystoreGenerator {

    /**
     * The logger used for logging the key store creation.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(KeystoreGenerator.class);

    /**
     * Runs the generator and creates a new set of key stores.
     * 
     * @param args
     *            the arguments
     * @throws Exception
     *             if any problem occurs while generating the key stores
     */
    public static final void main(final String[] args) throws Exception {
        final FileOutputStream fos;
        final FileOutputStream fosjks;
        final FileOutputStream fosSecond;
        final FileOutputStream fosSym;
        final KeyStore jksMain;
        final KeyStore jksSecond;
        final KeyStore jceksSym;
        final String jksMainPath;
        final String jksSecondPath;
        final String jceksSymPath;
        final String password;
        final String alias;
        final String issuer;

        jksMainPath = "src/main/resources/keystore/keystore.jks";
        jksSecondPath = "src/main/resources/keystore/keystore2.jks";
        jceksSymPath = "src/main/resources/keystore/symmetric.jceks";

        password = "123456";
        alias = "swss-cert";
        issuer = "CN=www.wandrell.com, O=Wandrell, OU=None, L=London, ST=England, C=UK";

        Security.addProvider(new BouncyCastleProvider());

        // Main key store

        LOGGER.trace("Creating main key store");

        jksMain = KeystoreFactory.getJKSKeystore(password, alias, issuer);

        // Saves the main keystore
        fos = new FileOutputStream(jksMainPath);
        fosjks = new FileOutputStream(jksMainPath);
        jksMain.store(fosjks, password.toCharArray());
        fosjks.close();
        fos.close();

        LOGGER.trace("Created main key store");

        // Second key store

        LOGGER.trace("Creating second key store");

        jksSecond = KeystoreFactory.getJKSKeystore(password, alias, issuer);

        // Saves the second keystore
        fosSecond = new FileOutputStream(jksSecondPath);
        jksSecond.store(fosSecond, password.toCharArray());
        fosSecond.close();

        LOGGER.trace("Created second key store");

        // Symmetric key store

        LOGGER.trace("Creating symmetric key store");

        jceksSym = KeystoreFactory.getJCEKSKeystore(password, alias);

        // Saves the symmetric keystore
        fosSym = new FileOutputStream(jceksSymPath);
        jceksSym.store(fosSym, password.toCharArray());
        fosSym.close();

        LOGGER.trace("Created symmetric key store");

        LOGGER.trace("Finished creating key stores");
    }

    /**
     * Private constructor to avoid initialization.
     */
    private KeystoreGenerator() {
        super();
    }

}
