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

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.ApplicationProtocolNames;
import io.netty.handler.ssl.ApplicationProtocolNegotiationHandler;

/**
 * @author Arpan Mukhopadhyay
 *
 */
public class Http2OrHttpHandler extends ApplicationProtocolNegotiationHandler implements ChannelHandler, Constants {

	private static final int MAX_CONTENT_LENGTH = 10 * MB;
	
	/**
	 * 
	 */
	protected Http2OrHttpHandler() {
		super(ApplicationProtocolNames.HTTP_1_1);
	}
	
	/**
	 * @param fallbackProtocol
	 */
	protected Http2OrHttpHandler(String fallbackProtocol) {
		super(fallbackProtocol);
	}

	@Override
	protected void configurePipeline(ChannelHandlerContext ctx, String protocol) throws Exception {
		if (ApplicationProtocolNames.HTTP_2.equals(protocol)) {
			return;
		}
		if (ApplicationProtocolNames.HTTP_1_1.equals(protocol)) {
			ctx.pipeline().addLast(new HttpServerCodec(), new HttpObjectAggregator(MAX_CONTENT_LENGTH));
			return;
		}
		throw new IllegalStateException("Unknown protocol: " + protocol);
	}
}
