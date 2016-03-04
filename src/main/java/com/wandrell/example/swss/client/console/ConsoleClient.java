/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2016 the original author or authors.
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

package com.wandrell.example.swss.client.console;

import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.client.WebServiceIOException;

import com.wandrell.example.swss.client.EntityClient;
import com.wandrell.example.ws.generated.entity.Entity;

/**
 * Runnable shell-based client.
 *
 * @author Bernardo Martínez Garrido
 */
public class ConsoleClient {

    private static final String ENCRYPTION_WSS4J      = "encryption_wss4j";
    private static final String ENCRYPTION_XWSS       = "encryption_xwss";
    private static final String ENDPOINT_URL_TEMPLATE = "http://localhost:8080/swss%s";
    private static final String PASSWORD_DIGEST_WSS4J = "password_digest_wss4j";
    private static final String PASSWORD_DIGEST_XWSS  = "password_digest_xwss";
    private static final String PASSWORD_PLAIN_WSS4J  = "password_plain_wss4j";
    private static final String PASSWORD_PLAIN_XWSS   = "password_plain_xwss";
    private static final String PROPERTY_ENDPOINT_URI = "wsdl.locationUri";
    private static final String SIGNATURE_WSS4J       = "signature_wss4j";
    private static final String SIGNATURE_XWSS        = "signature_xwss";
    private static final String UNSECURE              = "unsecure";

    /**
     * Main runnable method.
     *
     * @param args
     *            command line arguments
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {
        final PrintStream output;

        output = System.out;

        printTitle(output);
        printHelp(output);

        runMainLoop(output, getClients(), getUris());
    }

    private static final void callEndpoint(final EntityClient client,
            final String uri, final PrintStream output, final Scanner scanner) {
        final Entity entity;
        final Integer id;

        output.println("------------------------------------");
        output.println("Write the id of the entity to query.");
        output.println("The id 1 is always valid.");
        output.println("------------------------------------");

        output.println();
        output.print("Id: ");
        id = getInteger(scanner);
        output.println();

        try {
            entity = client.getEntity(uri, id);

            if (entity == null) {
                output.println(String.format("No entity with id %d exists", id));
            } else {
                output.println("Found entity.");
                output.println();
                output.println(String.format("Entity id:\t%d", entity.getId()));
                output.println(String.format("Entity name:\t%s",
                        entity.getName()));
            }
        } catch (final WebServiceIOException e) {
            output.println(String.format("Error: %s", e.getMostSpecificCause()
                    .getMessage()));
        }

        waitForKeyPress(output, scanner);
    }

    private static final Map<String, EntityClient> getClients()
            throws IOException {
        final Map<String, EntityClient> clients = new LinkedHashMap<String, EntityClient>();

        clients.put(UNSECURE,
                getEntityClient("context/client/client-unsecure.xml"));
        clients.put(
                PASSWORD_PLAIN_XWSS,
                getEntityClient("context/client/password/plain/xwss/client-password-plain-xwss.xml"));
        clients.put(
                PASSWORD_PLAIN_WSS4J,
                getEntityClient("context/client/password/plain/wss4j/client-password-plain-wss4j.xml"));
        clients.put(
                PASSWORD_DIGEST_XWSS,
                getEntityClient("context/client/password/digest/xwss/client-password-digest-xwss.xml"));
        clients.put(
                PASSWORD_DIGEST_WSS4J,
                getEntityClient("context/client/password/digest/wss4j/client-password-digest-wss4j.xml"));
        clients.put(
                SIGNATURE_XWSS,
                getEntityClient("context/client/signature/xwss/client-signature-xwss.xml"));
        clients.put(
                SIGNATURE_WSS4J,
                getEntityClient("context/client/signature/wss4j/client-signature-wss4j.xml"));
        clients.put(
                ENCRYPTION_XWSS,
                getEntityClient("context/client/encryption/xwss/client-encryption-xwss.xml"));
        clients.put(
                ENCRYPTION_WSS4J,
                getEntityClient("context/client/encryption/wss4j/client-encryption-wss4j.xml"));

        return clients;
    }

    private static final String getEndpointUri(final String propertiesPath)
            throws IOException {
        final Properties properties;

        properties = new Properties();
        properties.load(new ClassPathResource(propertiesPath).getInputStream());

        return String.format(ENDPOINT_URL_TEMPLATE,
                (String) properties.get(PROPERTY_ENDPOINT_URI));
    }

    private static final EntityClient getEntityClient(final String contextPath)
            throws IOException {
        final ClassPathXmlApplicationContext context;
        final EntityClient client;

        context = new ClassPathXmlApplicationContext(contextPath);

        client = context.getBean(EntityClient.class);

        context.close();

        return client;
    }

    private static final Integer getInteger(final Scanner scanner) {
        Integer value = null;
        Boolean valid;

        valid = false;
        while (!valid) {
            valid = scanner.hasNextInt();
            if (valid) {
                value = scanner.nextInt();
            } else {
                scanner.nextLine();
            }
        }

        return value;
    }

    private static final Map<String, String> getUris() throws IOException {
        final Map<String, String> uris = new LinkedHashMap<String, String>();

        uris.put(UNSECURE,
                getEndpointUri("context/endpoint/endpoint-unsecure.properties"));
        uris.put(
                PASSWORD_PLAIN_XWSS,
                getEndpointUri("context/endpoint/password/plain/xwss/endpoint-password-plain-xwss.properties"));
        uris.put(
                PASSWORD_PLAIN_WSS4J,
                getEndpointUri("context/endpoint/password/plain/wss4j/endpoint-password-plain-wss4j.properties"));
        uris.put(
                PASSWORD_DIGEST_XWSS,
                getEndpointUri("context/endpoint/password/digest/xwss/endpoint-password-digest-xwss.properties"));
        uris.put(
                PASSWORD_DIGEST_WSS4J,
                getEndpointUri("context/endpoint/password/digest/wss4j/endpoint-password-digest-wss4j.properties"));
        uris.put(
                SIGNATURE_XWSS,
                getEndpointUri("context/endpoint/signature/xwss/endpoint-signature-xwss.properties"));
        uris.put(
                SIGNATURE_WSS4J,
                getEndpointUri("context/endpoint/signature/wss4j/endpoint-signature-wss4j.properties"));
        uris.put(
                ENCRYPTION_XWSS,
                getEndpointUri("context/endpoint/encryption/xwss/endpoint-encryption-xwss.properties"));
        uris.put(
                ENCRYPTION_WSS4J,
                getEndpointUri("context/endpoint/encryption/wss4j/endpoint-encryption-wss4j.properties"));

        return uris;
    }

    private static final void printClientOptions(final PrintStream output) {
        output.println("Choose a security configuration:");
        output.println();
        output.println("1.- Unsecure");
        output.println("2.- Plain password (XWSS)");
        output.println("3.- Plain password (WSS4J)");
        output.println("4.- Digested password (XWSS)");
        output.println("5.- Digested password (WSS4J)");
        output.println("6.- Signature (XWSS)");
        output.println("7.- Signature (WSS4J)");
        output.println("8.- Encryption (XWSS)");
        output.println("9.- Encryption (WSS4J)");
    }

    private static final void printClientSelection(final String uri,
            final String security, final PrintStream output) {
        final String securityName;

        switch (security) {
            case UNSECURE:
                securityName = "unsecure";
                break;
            case PASSWORD_PLAIN_XWSS:
                securityName = "plain password (XWSS)";
                break;
            case PASSWORD_PLAIN_WSS4J:
                securityName = "plain password (WSS4J)";
                break;
            case PASSWORD_DIGEST_XWSS:
                securityName = "digested password (XWSS)";
                break;
            case PASSWORD_DIGEST_WSS4J:
                securityName = "digested password (WSS4J)";
                break;
            case SIGNATURE_XWSS:
                securityName = "signature (XWSS)";
                break;
            case SIGNATURE_WSS4J:
                securityName = "signature (WSS4J)";
                break;
            case ENCRYPTION_XWSS:
                securityName = "encryption (XWSS)";
                break;
            case ENCRYPTION_WSS4J:
                securityName = "encryption (WSS4J)";
                break;
            default:
                securityName = null;
        }

        output.println("+++++++++++++++++++++++++++++++++++++");
        output.println(String.format("Preparing to query the %s endpoint.",
                securityName));
        output.println(String.format("This is located at %s", uri));
        output.println("+++++++++++++++++++++++++++++++++++++");
        output.println();
    }

    private static final void printHelp(final PrintStream output) {
        output.println("========================================================================");
        output.println("Pick an option. Write 'exit' to close the client.");
        output.println();
        output.println("The server should be running at the default URI for this client to work.");
        output.println("Check the server log for the SOAP messages traces.");
        output.println("========================================================================");
    }

    private static final void printTitle(final PrintStream output) {
        output.println();
        output.println("Spring WSS example console client.");
        output.println();
    }

    private static final void runMainLoop(final PrintStream output,
            final Map<String, EntityClient> clients,
            final Map<String, String> uris) {
        final Scanner scanner = new Scanner(System.in);
        String security;
        EntityClient client = null;
        String uri;
        String command;

        do {
            output.println();
            printClientOptions(output);
            output.println();
            output.print("Pick an option: ");
            command = scanner.next();
            output.println();
            switch (command) {
                case "1":
                    security = UNSECURE;
                    break;
                case "2":
                    security = PASSWORD_PLAIN_XWSS;
                    break;
                case "3":
                    security = PASSWORD_PLAIN_WSS4J;
                    break;
                case "4":
                    security = PASSWORD_DIGEST_XWSS;
                    break;
                case "5":
                    security = PASSWORD_DIGEST_WSS4J;
                    break;
                case "6":
                    security = SIGNATURE_XWSS;
                    break;
                case "7":
                    security = SIGNATURE_WSS4J;
                    break;
                case "8":
                    security = ENCRYPTION_XWSS;
                    break;
                case "9":
                    security = ENCRYPTION_WSS4J;
                    break;
                default:
                    security = null;
            }

            if (security != null) {
                client = clients.get(security);
                uri = uris.get(security);

                printClientSelection(uri, security, output);
                callEndpoint(client, uri, output, scanner);
            }
        } while (!command.equalsIgnoreCase("exit"));

        scanner.close();
    }

    private static final void waitForKeyPress(final PrintStream output,
            final Scanner scanner) {

        output.println();
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        output.println("Press Enter to continue.");
        scanner.nextLine();
    }

    /**
     * Constructs a {@code ShellClient}.
     */
    public ConsoleClient() {
        super();
    }

}
