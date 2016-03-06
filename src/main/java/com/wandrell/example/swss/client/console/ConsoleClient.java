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
public final class ConsoleClient {

    /**
     * Template for generating the final endpoint URL.
     * <p>
     * This is just the default URI for the web service, to which the endpoint
     * path is to be added.
     */
    private static final String ENDPOINT_URL_TEMPLATE = "http://localhost:8080/swss%s";
    /**
     * Property key for the endpoint URI.
     */
    private static final String PROPERTY_ENDPOINT_URI = "wsdl.locationUri";

    /**
     * Enumeration for all the security types supported by the console client.
     * 
     * @author Bernardo Martínez Garrido
     */
    private enum Security {
        /**
         * Encryption using WSS4J.
         */
        ENCRYPTION_WSS4J,
        /**
         * Encryption using XWSS.
         */
        ENCRYPTION_XWSS,
        /**
         * Digested password using WSS4J.
         */
        PASSWORD_DIGEST_WSS4J,
        /**
         * Digested password using XWSS.
         */
        PASSWORD_DIGEST_XWSS,
        /**
         * Plain password using WSS4J.
         */
        PASSWORD_PLAIN_WSS4J,
        /**
         * Plain password using XWSS.
         */
        PASSWORD_PLAIN_XWSS,
        /**
         * Signature using WSS4J.
         */
        SIGNATURE_WSS4J,
        /**
         * Signature using XWSS.
         */
        SIGNATURE_XWSS,
        /**
         * Unsecure.
         */
        UNSECURE
    }

    /**
     * Main runnable method.
     *
     * @param args
     *            command line arguments
     * @throws IOException
     *             if any error occurs while loading the client
     */
    public static void main(final String[] args) throws IOException {
        final PrintStream output; // Output for the client information

        output = System.out;

        // The client header is printed
        printTitle(output);
        printHelp(output);

        runMainLoop(output, getClients(), getUris());
    }

    /**
     * Calls an endpoint by using the specified {@code EntityClient}.
     * <p>
     * The client should be the correct one for the endpoint. Otherwise an error
     * may occur.
     * <p>
     * If the endpoint can't be reached a warning will be printed on the screen.
     * 
     * @param client
     *            {@code EntityClient} which will call the endpoint
     * @param uri
     *            URI for the endpoint
     * @param output
     *            output for the client to print information
     * @param scanner
     *            scanner for the input
     */
    private static final void callEndpoint(final EntityClient client,
            final String uri, final PrintStream output, final Scanner scanner) {
        final Entity entity; // Queried entity
        final Integer id;    // Id for the query

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
                // No entity found
                output.println(
                        String.format("No entity with id %d exists", id));
            } else {
                // Entity found
                output.println("Found entity.");
                output.println();
                output.println(String.format("Entity id:\t%d", entity.getId()));
                output.println(
                        String.format("Entity name:\t%s", entity.getName()));
            }
        } catch (final WebServiceIOException e) {
            output.println(String.format("Error: %s",
                    e.getMostSpecificCause().getMessage()));
        }

        // Waits so the result information can be checked
        waitForEnter(output, scanner);
    }

    /**
     * Returns all the {@code EntityClient} instances, one for each security
     * method.
     * <p>
     * These are loaded from Spring context files.
     * 
     * @return a {@code EntityClient} instance for each supported security
     *         method
     */
    private static final Map<Security, EntityClient> getClients() {
        final Map<Security, EntityClient> clients; // Returned clients

        clients = new LinkedHashMap<Security, EntityClient>();
        clients.put(Security.UNSECURE,
                getEntityClient("context/client/client-unsecure.xml"));
        clients.put(Security.PASSWORD_PLAIN_XWSS, getEntityClient(
                "context/client/password/plain/xwss/client-password-plain-xwss.xml"));
        clients.put(Security.PASSWORD_PLAIN_WSS4J, getEntityClient(
                "context/client/password/plain/wss4j/client-password-plain-wss4j.xml"));
        clients.put(Security.PASSWORD_DIGEST_XWSS, getEntityClient(
                "context/client/password/digest/xwss/client-password-digest-xwss.xml"));
        clients.put(Security.PASSWORD_DIGEST_WSS4J, getEntityClient(
                "context/client/password/digest/wss4j/client-password-digest-wss4j.xml"));
        clients.put(Security.SIGNATURE_XWSS, getEntityClient(
                "context/client/signature/xwss/client-signature-xwss.xml"));
        clients.put(Security.SIGNATURE_WSS4J, getEntityClient(
                "context/client/signature/wss4j/client-signature-wss4j.xml"));
        clients.put(Security.ENCRYPTION_XWSS, getEntityClient(
                "context/client/encryption/xwss/client-encryption-xwss.xml"));
        clients.put(Security.ENCRYPTION_WSS4J, getEntityClient(
                "context/client/encryption/wss4j/client-encryption-wss4j.xml"));

        return clients;
    }

    /**
     * Returns the URI for an endpoint. This is created from the path loaded
     * from a properties file, and the endpoint URL template.
     * 
     * @param propertiesPath
     *            path to the properties file with the endpoint path
     * @return the fill URI for the endpoint
     * @throws IOException
     *             if any error occurs while loading the properties
     */
    private static final String getEndpointUri(final String propertiesPath)
            throws IOException {
        final Properties properties; // Properties with the endpoint data

        properties = new Properties();
        properties.load(new ClassPathResource(propertiesPath).getInputStream());

        return String.format(ENDPOINT_URL_TEMPLATE,
                (String) properties.get(PROPERTY_ENDPOINT_URI));
    }

    /**
     * Returns a {@code EntityClient} loaded from the context on the specified
     * file.
     * <p>
     * This is loaded from a Spring context file.
     * 
     * @param contextPath
     *            path to the context file.
     * @return a {@code EntityClient} loaded from the specified context
     */
    private static final EntityClient
            getEntityClient(final String contextPath) {
        final ClassPathXmlApplicationContext context;
        final EntityClient client;

        context = new ClassPathXmlApplicationContext(contextPath);

        client = context.getBean(EntityClient.class);

        context.close();

        return client;
    }

    /**
     * Returns an integer value read from the input.
     * <p>
     * This will try to parse an integer until one is found, rejecting all the
     * invalid lines.
     * 
     * @param scanner
     *            scanner for the input
     * @return an integer read from the input
     */
    private static final Integer getInteger(final Scanner scanner) {
        Integer integer; // Parsed integer
        Boolean valid;   // Status flag for the loop

        valid = false;
        integer = null;
        while (!valid) {
            // Runs until an integer is found
            valid = scanner.hasNextInt();
            if (valid) {
                // Found an integer
                integer = scanner.nextInt();
            } else {
                // The line is not an integer
                // It is rejected
                scanner.nextLine();
            }
        }

        return integer;
    }

    /**
     * Returns all the endpoint URIs, one for each security method.
     * 
     * @return an URI for each supported security method
     * @throws IOException
     *             if any error occurs while loading the URIs
     */
    private static final Map<Security, String> getUris() throws IOException {
        final Map<Security, String> uris; // Returned URIs

        uris = new LinkedHashMap<Security, String>();
        uris.put(Security.UNSECURE, getEndpointUri(
                "context/endpoint/endpoint-unsecure.properties"));
        uris.put(Security.PASSWORD_PLAIN_XWSS, getEndpointUri(
                "context/endpoint/password/plain/xwss/endpoint-password-plain-xwss.properties"));
        uris.put(Security.PASSWORD_PLAIN_WSS4J, getEndpointUri(
                "context/endpoint/password/plain/wss4j/endpoint-password-plain-wss4j.properties"));
        uris.put(Security.PASSWORD_DIGEST_XWSS, getEndpointUri(
                "context/endpoint/password/digest/xwss/endpoint-password-digest-xwss.properties"));
        uris.put(Security.PASSWORD_DIGEST_WSS4J, getEndpointUri(
                "context/endpoint/password/digest/wss4j/endpoint-password-digest-wss4j.properties"));
        uris.put(Security.SIGNATURE_XWSS, getEndpointUri(
                "context/endpoint/signature/xwss/endpoint-signature-xwss.properties"));
        uris.put(Security.SIGNATURE_WSS4J, getEndpointUri(
                "context/endpoint/signature/wss4j/endpoint-signature-wss4j.properties"));
        uris.put(Security.ENCRYPTION_XWSS, getEndpointUri(
                "context/endpoint/encryption/xwss/endpoint-encryption-xwss.properties"));
        uris.put(Security.ENCRYPTION_WSS4J, getEndpointUri(
                "context/endpoint/encryption/wss4j/endpoint-encryption-wss4j.properties"));

        return uris;
    }

    /**
     * Prints all the main options available to the console client.
     * 
     * @param output
     *            output where the options will be printed
     */
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

    /**
     * Prints the client help information.
     * 
     * @param output
     *            output where the information will be printed
     */
    private static final void printHelp(final PrintStream output) {
        output.println(
                "========================================================================");
        output.println("Pick an option. Write 'exit' to close the client.");
        output.println();
        output.println(
                "The server should be running at the default URI for this client to work.");
        output.println("Check the server log for the SOAP messages traces.");
        output.println(
                "========================================================================");
    }

    /**
     * Prints the header telling which endpoint is going to be queried.
     * 
     * @param uri
     *            URI for the endpoint
     * @param security
     *            security method used
     * @param output
     *            output where the header will be printed
     */
    private static final void printQueryHeader(final String uri,
            final Security security, final PrintStream output) {
        final String securityName; // Name of the security method

        // Loads data to customize the header
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
                break;
        }

        // Prints the header
        output.println("+++++++++++++++++++++++++++++++++++++");
        output.println(String.format("Preparing to query the %s endpoint.",
                securityName));
        output.println(String.format("This is located at %s", uri));
        output.println("+++++++++++++++++++++++++++++++++++++");
        output.println();
    }

    /**
     * Prints the client title.
     * 
     * @param output
     *            output where the client title will be printed
     */
    private static final void printTitle(final PrintStream output) {
        output.println();
        output.println("Spring WSS example console client.");
        output.println();
    }

    /**
     * Runs the main application loop.
     * <p>
     * This is what keeps the client running and living. It can be stopped by
     * the user through a specific console command.
     * 
     * @param output
     *            output where all the information will be printed
     * @param clients
     *            an {@code EntityClient} instance for each security method
     * @param uris
     *            an endpoint URI for each security method
     */
    private static final void runMainLoop(final PrintStream output,
            final Map<Security, EntityClient> clients,
            final Map<Security, String> uris) {
        final Scanner scanner; // Scanner for reading the input
        Security security;     // Selected security method
        EntityClient client;   // Client for the selected security
        String uri;            // Endpoint for the selected security
        String command;        // Current user command

        scanner = new Scanner(System.in);
        // The main loop
        // Stops when the 'exit' command is received
        do {
            // Prints options
            output.println();
            printClientOptions(output);
            output.println();

            // Reads command
            output.print("Pick an option: ");
            command = scanner.next();
            output.println();

            // Loads security from the command
            switch (command) {
                case "1":
                    security = Security.UNSECURE;
                    break;
                case "2":
                    security = Security.PASSWORD_PLAIN_XWSS;
                    break;
                case "3":
                    security = Security.PASSWORD_PLAIN_WSS4J;
                    break;
                case "4":
                    security = Security.PASSWORD_DIGEST_XWSS;
                    break;
                case "5":
                    security = Security.PASSWORD_DIGEST_WSS4J;
                    break;
                case "6":
                    security = Security.SIGNATURE_XWSS;
                    break;
                case "7":
                    security = Security.SIGNATURE_WSS4J;
                    break;
                case "8":
                    security = Security.ENCRYPTION_XWSS;
                    break;
                case "9":
                    security = Security.ENCRYPTION_WSS4J;
                    break;
                default:
                    security = null;
                    break;
            }

            // Checks if it was a valid option
            if (security != null) {
                // Valid option
                // The client and URI are acquired
                client = clients.get(security);
                uri = uris.get(security);

                // The endpoint is queried
                printQueryHeader(uri, security, output);
                callEndpoint(client, uri, output, scanner);
            }
        } while (!"exit".equalsIgnoreCase(command));

        scanner.close();
    }

    /**
     * Waits until the 'enter' key is pressed.
     * 
     * @param output
     *            output where the information will be printed
     * @param scanner
     *            scanner for reading the input
     */
    private static final void waitForEnter(final PrintStream output,
            final Scanner scanner) {

        output.println();
        // The scanner is cleaned to remove any new line
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        output.println("Press Enter to continue.");
        scanner.nextLine();
    }

    /**
     * Constructs a {@code ConsoleClient}.
     * <p>
     * The constructor is hidden to avoid initialization.
     */
    private ConsoleClient() {
        super();
    }

}
