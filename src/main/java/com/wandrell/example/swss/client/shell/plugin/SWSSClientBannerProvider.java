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

package com.wandrell.example.swss.client.shell.plugin;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.shell.plugin.BannerProvider;
import org.springframework.shell.support.util.FileUtils;
import org.springframework.shell.support.util.OsUtils;
import org.springframework.stereotype.Component;

/**
 * Banner provider for the shell client.
 *
 * @author Bernardo Martínez Garrido
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public final class SWSSClientBannerProvider implements BannerProvider {

    /**
     * Shell client version.
     */
    private final String clientVersion;

    /**
     * Constructs a {@code SWSSClientBannerProvider}.
     *
     * @param version
     *            client version
     */
    @Autowired
    public SWSSClientBannerProvider(
            @Value("${shell.version}") final String version) {
        super();

        clientVersion = checkNotNull(version);
    }

    @Override
    public final String getBanner() {
        final StringBuffer sb = new StringBuffer();
        final ClassPathResource bannerResource;

        bannerResource = new ClassPathResource("shell/banner.txt");

        try {
            sb.append(FileUtils.readBanner(new InputStreamReader(bannerResource
                    .getInputStream())));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        sb.append(OsUtils.LINE_SEPARATOR);
        sb.append("Version: ");
        sb.append(getVersion()).append(OsUtils.LINE_SEPARATOR);
        sb.append(OsUtils.LINE_SEPARATOR);
        return sb.toString();
    }

    @Override
    public final String getVersion() {
        return clientVersion;
    }

    @Override
    public final String getWelcomeMessage() {
        return "Welcome to " + getProviderName() + ".";
    }

    @Override
    public final String getProviderName() {
        return "SWSS shell client";
    }

}
