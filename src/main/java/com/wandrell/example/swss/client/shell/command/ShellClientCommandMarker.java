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

package com.wandrell.example.swss.client.shell.command;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliAvailabilityIndicator;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

/**
 * Basic commands for the SWSS shell-based client.
 *
 * @author Bernardo Martínez Garrido
 */
@Component
public final class ShellClientCommandMarker implements CommandMarker {

    enum MessageType {
        Type1("type1"), Type2("type2"), Type3("type3");

        private String type;

        private MessageType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    private boolean simpleCommandExecuted = false;

    /**
     * Constructs a {@code ShellClientCommandMarker}.
     */
    public ShellClientCommandMarker() {
        super();
    }

    @CliCommand(
            value = "hw enum",
            help = "Print a simple hello world message from an enumerated value (run 'hw simple' once first)")
    public
            String
            eenum(@CliOption(key = { "message" }, mandatory = true,
                    help = "The hello world message") final MessageType message) {
        return "Hello.  Your special enumerated message is " + message;
    }

    @CliCommand(
            value = "hw complex",
            help = "Print a complex hello world message (run 'hw simple' once first)")
    public
            String
            hello(@CliOption(key = { "message" }, mandatory = true,
                    help = "The hello world message") final String message,
                    @CliOption(key = { "name1" }, mandatory = true,
                            help = "Say hello to the first name") final String name1,
                    @CliOption(key = { "name2" }, mandatory = true,
                            help = "Say hello to a second name") final String name2,
                    @CliOption(key = { "time" }, mandatory = false,
                            specifiedDefaultValue = "now",
                            help = "When you are saying hello") final String time,
                    @CliOption(key = { "location" }, mandatory = false,
                            help = "Where you are saying hello") final String location) {
        return "Hello " + name1 + " and " + name2
                + ". Your special message is " + message + ". time=[" + time
                + "] location=[" + location + "]";
    }

    @CliAvailabilityIndicator({ "hw complex", "hw enum" })
    public boolean isComplexAvailable() {
        if (simpleCommandExecuted) {
            return true;
        } else {
            return false;
        }
    }

    @CliAvailabilityIndicator({ "hw simple" })
    public boolean isSimpleAvailable() {
        // always available
        return true;
    }

    @CliCommand(value = "hw simple",
            help = "Print a simple hello world message")
    public String simple(@CliOption(key = { "message" }, mandatory = true,
            help = "The hello world message") final String message, @CliOption(
            key = { "location" }, mandatory = false,
            help = "Where you are saying hello",
            specifiedDefaultValue = "At work") final String location) {
        simpleCommandExecuted = true;
        return "Message = [" + message + "] Location = [" + location + "]";
    }

}
