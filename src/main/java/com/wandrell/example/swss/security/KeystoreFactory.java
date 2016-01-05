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

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.bc.BcX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

/**
 * Factory for generating a key store.
 * <p>
 * If possible, should be removed and the procedure handled in a Spring context.
 *
 * @author Bernardo Martínez Garrido
 */
public class KeystoreFactory {

    public static final KeyStore getKeystore() throws NoSuchAlgorithmException,
            CertificateException, KeyStoreException, IOException,
            InvalidKeyException, NoSuchProviderException, SecurityException,
            SignatureException, OperatorCreationException {
        final KeyStore ks;
        final KeyPair keypair;
        final Certificate certificate;

        ks = generateKeystore();
        keypair = generateKeyPair();
        certificate = generateCertificate(keypair.getPublic(),
                keypair.getPrivate());

        ks.setCertificateEntry("alias", certificate);

        return ks;
    }

    private static SubjectKeyIdentifier createSubjectKeyIdentifier(Key key)
            throws IOException {
        ASN1InputStream is = null;
        try {
            is = new ASN1InputStream(new ByteArrayInputStream(key.getEncoded()));
            ASN1Sequence seq = (ASN1Sequence) is.readObject();
            SubjectPublicKeyInfo info = new SubjectPublicKeyInfo(seq);
            return new BcX509ExtensionUtils().createSubjectKeyIdentifier(info);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    private final static Certificate generateCertificate(
            final PublicKey publicKey, final PrivateKey privateKey)
            throws CertIOException, IOException, OperatorCreationException,
            CertificateException, InvalidKeyException,
            NoSuchAlgorithmException, NoSuchProviderException,
            SignatureException {
        final X500Name issuerName;
        final X500Name subjectName;
        final BigInteger serial;
        final X509v3CertificateBuilder builder;
        final Date start;
        final Date end;
        final Calendar c;
        final KeyUsage usage;
        final ASN1EncodableVector purposes;
        final X509Certificate cert;

        issuerName = new X500Name(
                "CN=www.mockserver.com, O=MockServer, L=London, ST=England, C=UK");
        subjectName = issuerName;
        serial = BigInteger.valueOf(new Random().nextInt());

        start = new Date(System.currentTimeMillis() - 86400000L * 365);
        end = new Date(System.currentTimeMillis() + 86400000L * 365 * 100);

        builder = new JcaX509v3CertificateBuilder(issuerName, serial, start,
                end, subjectName, publicKey);

        builder.addExtension(Extension.subjectKeyIdentifier, false,
                createSubjectKeyIdentifier(publicKey));
        builder.addExtension(Extension.basicConstraints, true,
                new BasicConstraints(true));

        usage = new KeyUsage(KeyUsage.keyCertSign | KeyUsage.digitalSignature
                | KeyUsage.keyEncipherment | KeyUsage.dataEncipherment
                | KeyUsage.cRLSign);
        builder.addExtension(Extension.keyUsage, false, usage);

        purposes = new ASN1EncodableVector();
        purposes.add(KeyPurposeId.id_kp_serverAuth);
        purposes.add(KeyPurposeId.id_kp_clientAuth);
        purposes.add(KeyPurposeId.anyExtendedKeyUsage);
        builder.addExtension(Extension.extendedKeyUsage, false,
                new DERSequence(purposes));

        cert = signCertificate(builder, privateKey);
        cert.checkValidity(new Date());
        cert.verify(publicKey);

        return cert;
    }

    private static final KeyPair generateKeyPair()
            throws NoSuchAlgorithmException {
        final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

        keyGen.initialize(1024, new SecureRandom());

        return keyGen.generateKeyPair();
    }

    private static final KeyStore generateKeystore()
            throws NoSuchAlgorithmException, CertificateException, IOException,
            KeyStoreException {
        final KeyStore ks;
        final char[] password;
        final FileOutputStream fos;

        ks = KeyStore.getInstance(KeyStore.getDefaultType());

        password = "123456".toCharArray();
        ks.load(null, password);

        fos = new FileOutputStream("newKeyStoreFileName");

        // Store away the keystore.
        ks.store(fos, password);
        fos.close();

        return ks;
    }

    private static X509Certificate signCertificate(
            X509v3CertificateBuilder certificateBuilder,
            PrivateKey signedWithPrivateKey) throws OperatorCreationException,
            CertificateException {
        ContentSigner signer = new JcaContentSignerBuilder(
                "SHA256WithRSAEncryption").setProvider(
                BouncyCastleProvider.PROVIDER_NAME).build(signedWithPrivateKey);
        return new JcaX509CertificateConverter().setProvider(
                BouncyCastleProvider.PROVIDER_NAME).getCertificate(
                certificateBuilder.build(signer));
    }

    private KeystoreFactory() {
        super();
    }

}
