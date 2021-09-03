/*
 * ngBot is an open source tool for simulate network packets
 * Copyright (C) 2021-present original author(s). All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package io.ngbot.protocol.http;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;

/**
 * @author Arpan Mukhopadhyay
 *
 */
@Log4j2
class HttpServerTest {

	@Test
	void test() {
		final String host = "0.0.0.0";
		final int port = 9000;
		logger.info("Creating new HttpServer with address {}:{}", host, port);
		HttpServer server = new HttpServer(host, port);
		server.bind();
	}

}
