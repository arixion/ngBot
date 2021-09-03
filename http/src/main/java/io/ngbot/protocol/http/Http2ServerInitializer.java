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

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;

/**
 * @author Arpan Mukhopadhyay
 *
 */
public class Http2ServerInitializer extends ChannelInitializer<SocketChannel> implements Constants {

	private final SslContext sslContext;
	private final int maxHttpContentLen;
	
	/**
	 * @param sslContext
	 */
	public Http2ServerInitializer(SslContext sslContext) {
		this(sslContext, 8 * MB);
	}

	/**
	 * @param sslContext
	 * @param maxHttpContentLen
	 */
	public Http2ServerInitializer(SslContext sslContext, int maxHttpContentLen) {
		if (maxHttpContentLen < 0 ) {
			throw new IllegalArgumentException("maxHttpContentLen (expected >= 0):" + maxHttpContentLen);
		}
		this.sslContext = sslContext;
		this.maxHttpContentLen = maxHttpContentLen;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		if (null != sslContext) {
			configureSSL(ch);
		} else {
			configureClearText(ch);
		}
	}

	private void configureSSL(SocketChannel ch) {
		ch.pipeline().addLast(sslContext.newHandler(ch.alloc()), new Http2OrHttpHandler());
	}
	
	private void configureClearText(SocketChannel ch) {
		final ChannelPipeline pipeline = ch.pipeline();
		final HttpServerCodec codec = new HttpServerCodec();
		
		pipeline.addLast(codec)
		.addLast(new SimpleChannelInboundHandler<HttpMessage>() {

			@Override
			protected void channelRead0(ChannelHandlerContext ctx, HttpMessage msg) throws Exception {
				ChannelPipeline pipeline = ctx.pipeline();
				pipeline.addAfter(ctx.name(), null, new Http1RequestHandler());
				pipeline.replace(this, null, new HttpObjectAggregator(maxHttpContentLen));
				ctx.fireChannelRead(msg);
			}
		});
	}
}
