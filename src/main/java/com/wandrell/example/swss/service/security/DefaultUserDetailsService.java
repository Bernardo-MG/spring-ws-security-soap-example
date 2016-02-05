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

package com.wandrell.example.swss.service.security;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Service for handling users access.
 *
 * @author Bernardo Martínez Garrido
 */
public final class DefaultUserDetailsService implements UserDetailsService {

    /**
     * Constructs a {@code DefaultUserDetailsService}.
     */
    public DefaultUserDetailsService() {
        super();
    }

    @Override
    public final UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        final UserDetails user;
        final Collection<SimpleGrantedAuthority> authorities;

        checkNotNull(username, "Received a null pointer as username");

        if ("myUser".equalsIgnoreCase(username)) {
            // User for password-based security
            authorities = new LinkedList<SimpleGrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

            user = new User(username, "myPassword", authorities);
        } else if ("www.wandrell.com".equalsIgnoreCase(username)) {
            // User for keystore-based security
            authorities = new LinkedList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            user = new User(username, "password", true, true, true, true,
                    authorities);
        } else if ("swss-cert".equalsIgnoreCase(username)) {
            // User for keystore-based security
            authorities = new LinkedList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            user = new User(username, "123456", true, true, true, true,
                    authorities);
        } else {
            throw new UsernameNotFoundException(String.format(
                    "Invalid username '%s'", username));
        }

        return user;
    }

}
