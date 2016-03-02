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
import java.util.Properties;
import java.util.Scanner;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import com.wandrell.example.swss.client.EntityClient;
import com.wandrell.example.ws.generated.entity.Entity;

/**
 * Runnable shell-based client.
 *
 * @author Bernardo Martínez Garrido
 */
public class ConsoleClient {

    /**
     * Main runnable method.
     *
     * @param args
     *            command line arguments
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {
        final Scanner scanner = new Scanner(System.in);
        final PrintStream output;
        String command;
        ClassPathXmlApplicationContext context;
        Entity entity;

        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        Properties properties = new Properties();
        properties.load(new ClassPathResource(
                "context/client/client.properties").getInputStream());
        configurer.setProperties(properties);

        context = new ClassPathXmlApplicationContext(
                "context/client/client-unsecure.xml");
        context.addBeanFactoryPostProcessor(configurer);

        String wsUri = "http://localhost:8080/swss/unsecure/entities";
        EntityClient clientUnsecure = context.getBean(EntityClient.class);

        output = System.out;

        printTitle(output);
        printHelp(output);

        do {
            output.println();
            printClientOptions(output);
            output.println();
            output.print("Pick an option: ");
            command = scanner.next();
            switch (command) {
                case "1":
                    entity = clientUnsecure.getEntity(wsUri, 1);
                    output.println(String.format(
                            "Received entity with id %1$d and name %2$s",
                            entity.getId(), entity.getName()));
                    break;
            }
        } while (!command.equalsIgnoreCase("exit"));

        scanner.close();
        context.close();
    }

    private static final void printClientOptions(final PrintStream output) {
        output.println("1.- Unsecure");
    }

    private static final void printHelp(final PrintStream output) {
        output.println("-------------------------------------------------");
        output.println("Pick an option. Write 'exit' to close the client.");
        output.println("-------------------------------------------------");
    }

    private static final void printTitle(final PrintStream output) {
        output.println();
        output.println("Spring WSS example console client.");
        output.println();
    }

    /**
     * Constructs a {@code ShellClient}.
     */
    public ConsoleClient() {
        super();
    }

}
