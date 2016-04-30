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
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateException;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Executable class for generating the key stores used on the tests.
 * <p>
 * This is to be used only when for any reason new key stores are required.
 * Otherwise, as long as the ones included in the application work they should
 * be left untouched.
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
        final KeyStore jksMain;     // Main key store
        final KeyStore jksSecond;   // Second key store
        final KeyStore jceksSym;    // Symmetric key store
        final String jksMainPath;   // Path for the main key store
        final String jksSecondPath; // Path for the second key store
        final String jceksSymPath;  // Path for the symmetric key store
        final String password;      // Password to apply to the key stores
        final String alias;         // Alias for the certificate
        final String issuer;        // Issuer for the certificate

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
        saveToFile(jksMain, jksMainPath, password.toCharArray());

        LOGGER.trace("Created main key store");

        // Second key store

        LOGGER.trace("Creating second key store");

        jksSecond = KeystoreFactory.getJKSKeystore(password, alias, issuer);

        // Saves the second keystore
        saveToFile(jksSecond, jksSecondPath, password.toCharArray());

        LOGGER.trace("Created second key store");

        // Symmetric key store

        LOGGER.trace("Creating symmetric key store");

        jceksSym = KeystoreFactory.getJCEKSKeystore(password, alias);

        // Saves the symmetric keystore
        saveToFile(jceksSym, jceksSymPath, password.toCharArray());

        LOGGER.trace("Created symmetric key store");

        LOGGER.trace("Finished creating key stores");
    }

    /**
     * Saves the received key store to a file.
     *
     * @param keyStore
     *            key store to save
     * @param path
     *            path where the key store will be saved
     * @param password
     *            password to applyt to the saved key store
     * @throws KeyStoreException
     *             if the keystore has not been initialized
     * @throws NoSuchAlgorithmException
     *             if the appropriate data integrity algorithm could not be
     *             found
     * @throws CertificateException
     *             if any of the certificates included in the keystore data
     *             could not be stored
     * @throws IOException
     *             if an I/O error occurs
     */
    private static final void saveToFile(final KeyStore keyStore,
            final String path, final char[] password) throws KeyStoreException,
            NoSuchAlgorithmException, CertificateException, IOException {
        FileOutputStream output = null; // Output stream for the keystore

        try {
            output = new FileOutputStream(path);
            keyStore.store(output, password);
        } finally {
            IOUtils.closeQuietly(output);
        }
    }

    /**
     * Private constructor to avoid initialization.
     */
    private KeystoreGenerator() {
        super();
    }

}
