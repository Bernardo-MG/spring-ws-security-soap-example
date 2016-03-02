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

package com.wandrell.example.swss.client.shell;

import java.io.IOException;

import org.springframework.shell.Bootstrap;

/**
 * Runnable shell-based client.
 *
 * @author Bernardo Martínez Garrido
 */
public class ShellClient {

    /**
     * Main class that delegates to Spring Shell's Bootstrap class in order to
     * simplify debugging inside an IDE
     *
     * @param args
     *            command line arguments
     * @throws IOException
     *             if any IO error occurrs while running the client
     */
    public static void main(final String[] args) throws IOException {
        Bootstrap.main(args);
    }

    /**
     * Constructs a {@code SWSSShellClient}.
     */
    public ShellClient() {
        super();
    }

}
