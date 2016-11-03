/*
 * EmailUtil.java.java
 *
 * Copyright 2016 Caique Jhones
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.jhonesdeveloper.swing.util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * @author Caique Jhones
 */
public final class EmailUtil {
	
	private EmailUtil() {
	}
	
	public static void sendBasicText(String hostName, String username, String password, String from, String subject,
			String msg, String[] to) throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName(hostName);
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator(username, password));
		email.setSSLOnConnect(true);
		email.setFrom(from);
		email.setSubject(subject);
		email.setMsg(msg);
		email.addTo(to);
		email.send();
	}
}
