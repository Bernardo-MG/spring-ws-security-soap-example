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
        final KeyStore ks;

        Security.addProvider(new BouncyCastleProvider());

        ks = KeystoreFactory.getKeystore("src/main/resources/keystore.jks",
                "123456", "alias",
                "CN=www.wandrell.com, O=Wandrell, L=London, ST=England, C=UK");

        fos = new FileOutputStream("src/main/resources/keystore.jks");

        // Store away the keystore.
        ks.store(fos, "123456".toCharArray());
        fos.close();
    }

    /**
     * Private constructor to avoid initialization.
     */
    private KeystoreGenerator() {
        super();
    }

}
