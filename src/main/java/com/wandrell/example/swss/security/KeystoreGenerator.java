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

/**
 * Executable class for generating the key stores used on the tests.
 * <p>
 * This is to be used in case the key stores are to be rebuilt for some reason.
 *
 * @author Bernardo Martínez Garrido
 */
public class KeystoreGenerator {

    public static final void main(final String[] args) throws Exception {
        final FileOutputStream fos;
        final FileOutputStream fos2;
        final KeyStore ks;
        final KeyStore ks2;
        final String keystorePath;
        final String keystorePath2;
        final String password;
        final String alias;
        final String issuer;

        keystorePath = "src/main/resources/keystore/keystore.jks";
        keystorePath2 = "src/main/resources/keystore/keystore2.jks";
        password = "123456";
        alias = "swss-cert";
        issuer = "CN=www.wandrell.com, O=Wandrell, OU=None, L=London, ST=England, C=UK";

        Security.addProvider(new BouncyCastleProvider());

        ks = KeystoreFactory.getKeystore(password, alias, issuer);
        ks2 = KeystoreFactory.getKeystore(password, alias, issuer);

        // Saves the main keystore
        fos = new FileOutputStream(keystorePath);
        ks.store(new FileOutputStream(keystorePath), password.toCharArray());
        fos.close();

        // Saves the second keystore
        fos2 = new FileOutputStream(keystorePath2);
        ks2.store(fos2, password.toCharArray());
        fos2.close();

        System.out.println("Created key stores.");
    }

    /**
     * Private constructor to avoid initialization.
     */
    private KeystoreGenerator() {
        super();
    }

}
