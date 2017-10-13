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

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Users authentication service.
 * <p>
 * This is used by the the user-based authentication systems, and for that
 * reason implements an interface from the Spring framework.
 * <p>
 * To make it as simple as possible, the users are not loaded from anywhere,
 * instead the validation is hardcoded inside the service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
public final class DefaultUserDetailsService implements UserDetailsService {

	/**
	 * The logger used for logging the user details service usage.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUserDetailsService.class);

	/**
	 * Constructs a user details service.
	 */
	public DefaultUserDetailsService() {
		super();
	}

	@Override
	public final UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final UserDetails user; // Details for the user
		final Collection<SimpleGrantedAuthority> authorities; // Privileges of
																// the user

		checkNotNull(username, "Received a null pointer as username");

		if ("myUser".equalsIgnoreCase(username)) {
			// User for password-based security
			authorities = new ArrayList<SimpleGrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

			user = new User(username, "myPassword", authorities);
		} else {
			// User not found
			LOGGER.debug("User for username {} not found", username);

			throw new UsernameNotFoundException(String.format("Invalid username '%s'", username));
		}

		LOGGER.debug("Found user for username {}", username);

		return user;
	}

}
