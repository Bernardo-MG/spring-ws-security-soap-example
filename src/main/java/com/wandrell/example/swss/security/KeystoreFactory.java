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
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
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
import java.util.Date;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory for generating a key store.
 * <p>
 * If possible, should be removed and the procedure handled in a Spring context.
 *
 * @author Bernardo Martínez Garrido
 */
public final class KeystoreFactory {

    /**
     * The logger used for logging the key store creation.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(KeystoreFactory.class);

    public static final KeyStore getJCEKSKeystore(final String password,
            final String alias, final String issuer)
                    throws NoSuchAlgorithmException, CertificateException,
                    KeyStoreException, IOException, InvalidKeyException,
                    NoSuchProviderException, OperatorCreationException {
        final KeyStore ks;

        ks = getKeystore(password, "JCEKS");
        // addCertificate(ks, password, alias, issuer);
        addSecretKey(ks, alias, password);

        return ks;
    }

    public static final KeyStore getJKSKeystore(final String password,
            final String alias, final String issuer)
                    throws NoSuchAlgorithmException, CertificateException,
                    KeyStoreException, IOException, InvalidKeyException,
                    NoSuchProviderException, SecurityException,
                    SignatureException, OperatorCreationException {
        final KeyStore ks;

        ks = getKeystore(password);
        addCertificate(ks, password, alias, issuer);

        return ks;
    }

    private static void addCertificate(final KeyStore ks, final String password,
            final String alias, final String issuer)
                    throws NoSuchAlgorithmException, NoSuchProviderException,
                    InvalidKeyException, CertIOException,
                    OperatorCreationException, CertificateException,
                    IOException, KeyStoreException {
        final KeyPair keypair;
        final Certificate certificate;
        final Certificate[] chain;

        keypair = getKeyPair();
        certificate = getCertificate(keypair, issuer);

        chain = new Certificate[] { certificate };

        ks.setKeyEntry(alias, keypair.getPrivate(), password.toCharArray(),
                chain);
        // ks.setCertificateEntry(alias, certificate);

        LOGGER.debug(String.format(
                "Added certificate with alias %s and password %s for issuer %s",
                alias, password, issuer));
    }

    private final static void addSecretKey(final KeyStore ks,
            final String alias, final String password)
                    throws KeyStoreException {
        final KeyStore.SecretKeyEntry keyStoreEntry;
        final PasswordProtection keyPassword;
        final SecretKey secretKey;
        final byte[] key;

        key = new byte[] { 1, 2, 3, 4, 5 };
        secretKey = new SecretKeySpec(key, "DES");

        LOGGER.debug(String.format("Created secret key %s with format %s",
                secretKey.getEncoded(), secretKey.getFormat()));

        keyStoreEntry = new KeyStore.SecretKeyEntry(secretKey);
        keyPassword = new PasswordProtection(password.toCharArray());
        ks.setEntry(alias, keyStoreEntry, keyPassword);

        LOGGER.debug(
                String.format("Added secret key with alias %s and password %s",
                        alias, password));
    }

    private static SubjectKeyIdentifier
            createSubjectKeyIdentifier(final Key key) throws IOException {
        final ASN1Sequence seq;
        ASN1InputStream is = null;

        try {
            is = new ASN1InputStream(
                    new ByteArrayInputStream(key.getEncoded()));
            seq = (ASN1Sequence) is.readObject();
        } finally {
            IOUtils.closeQuietly(is);
        }

        return new BcX509ExtensionUtils()
                .createSubjectKeyIdentifier(new SubjectPublicKeyInfo(seq));
    }

    private final static Certificate getCertificate(final KeyPair keypair,
            final String issuer) throws CertIOException, IOException,
                    OperatorCreationException, CertificateException,
                    InvalidKeyException, NoSuchAlgorithmException,
                    NoSuchProviderException {
        final X509v3CertificateBuilder builder;
        final X509Certificate certificate;

        builder = getCertificateBuilder(keypair.getPublic(), issuer);

        certificate = signCertificate(builder, keypair.getPrivate());

        certificate.checkValidity(new Date());
        try {
            certificate.verify(keypair.getPublic());
        } catch (SignatureException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        LOGGER.debug(String.format(
                "Created certificate of type %s with encoded value %s",
                certificate.getType(), certificate.getEncoded()));
        LOGGER.debug(String.format("Created certificate with public key:\n%s",
                certificate.getPublicKey()));

        return certificate;
    }

    private static final X509v3CertificateBuilder getCertificateBuilder(
            final PublicKey publicKey, final String issuer) throws IOException {
        final X500Name issuerName;
        final X500Name subjectName;
        final BigInteger serial;
        final X509v3CertificateBuilder builder;
        final Date start;
        final Date end;
        final KeyUsage usage;
        final ASN1EncodableVector purposes;

        issuerName = new X500Name(issuer);
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

        return builder;

    }

    private static final KeyPair getKeyPair()
            throws NoSuchAlgorithmException, NoSuchProviderException {
        final KeyPairGenerator keyPairGenerator;
        final KeyPair keypair;

        keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024, new SecureRandom());

        keypair = keyPairGenerator.generateKeyPair();

        LOGGER.debug(String.format(
                "Created key pair with private key %3$s %1$s and public key %4$s %2$s",
                keypair.getPrivate().getEncoded(),
                keypair.getPublic().getEncoded(),
                keypair.getPrivate().getAlgorithm(),
                keypair.getPublic().getAlgorithm()));

        return keypair;
    }

    private static final KeyStore getKeystore(final String password)
            throws NoSuchAlgorithmException, CertificateException, IOException,
            KeyStoreException {
        return getKeystore(password, KeyStore.getDefaultType());
    }

    private static final KeyStore getKeystore(final String password,
            final String type) throws NoSuchAlgorithmException,
                    CertificateException, IOException, KeyStoreException {
        final KeyStore ks;
        final char[] pass;

        ks = KeyStore.getInstance(type);

        pass = password.toCharArray();
        ks.load(null, pass);

        LOGGER.debug(String.format("Created %s key store with password %s",
                type, password));

        return ks;
    }

    private static X509Certificate signCertificate(
            final X509v3CertificateBuilder builder, final PrivateKey key)
                    throws OperatorCreationException, CertificateException {
        final ContentSigner signer;
        final String provider;
        final X509Certificate signed;

        provider = BouncyCastleProvider.PROVIDER_NAME;
        signer = new JcaContentSignerBuilder("SHA256WithRSAEncryption")
                .setProvider(provider).build(key);

        signed = new JcaX509CertificateConverter().setProvider(provider)
                .getCertificate(builder.build(signer));

        LOGGER.debug(String.format(
                "Signed certificate with %1$s private key %3$s, using algorithm %2$s",
                key.getAlgorithm(), key.getFormat(), key.getEncoded()));

        return signed;
    }

    /**
     * Private constructor to avoid initialization.
     */
    private KeystoreFactory() {
        super();
    }

}
