package com.jkchat.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.stereotype.Component;

/**
 * @author Jebil Kuruvila
 *
 */
@Component
public class ConcurrencyFilter extends ConcurrentSessionFilter
		implements Filter {

	private static String expiredUrl = "/login";

	/**
	 * @param sessionRegistry
	 */
	@Autowired
	public ConcurrencyFilter(SessionRegistry sessionRegistry) {

		super(sessionRegistry, expiredUrl);
	}
}
