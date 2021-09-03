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

import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLException;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http2.Http2SecurityUtil;
import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.handler.ssl.ApplicationProtocolConfig.Protocol;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior;
import io.netty.handler.ssl.ApplicationProtocolNames;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;
import io.netty.handler.ssl.SupportedCipherSuiteFilter;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.ngbot.protocol.PacketListener;
import io.ngbot.protocol.http.data.HttpPacketCache;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

/**
 * @author Arpan Mukhopadhyay
 *
 */
@Getter
@Setter
@Log4j2
public class HttpServer implements PacketListener, Constants {

	private String id;
	private String instanceName;
	private String host = DEFAULT_HOST;
	private int port = DEFAULT_PORT;
	private boolean sslEnabled;

	private EventLoopGroup boss;
	private EventLoopGroup worker;

	private ServerBootstrap serverBootstrap;
	private ChannelFuture channelFuture;

	private Http2ServerInitializer serverInitializer;
	private SslContext sslContext;
	
	private HttpPacketCache cache;

	/**
	 * @param host
	 * @param port
	 */
	public HttpServer(String host, int port) {
		this.host = host;
		this.port = port;
	}

	/**
	 * @param host
	 * @param port
	 * @param sslEnabled
	 */
	public HttpServer(String host, int port, boolean sslEnabled) {
		this.host = host;
		this.port = port;
		this.sslEnabled = sslEnabled;
	}

	/**
	 * 
	 */
	private void init() {
		cache = new HttpPacketCache(instanceName);
		boss = new NioEventLoopGroup(1);
		worker = new NioEventLoopGroup();
		serverBootstrap = new ServerBootstrap();
		
		if (sslEnabled) {
			try {
				prepareSslContext();
			} catch (CertificateException | SSLException e) {
				logger.error(e);
				if (logger.isTraceEnabled()) {
					e.printStackTrace();
				}
				return;
			}
		}
		
		serverInitializer = new Http2ServerInitializer(sslContext);
		serverBootstrap.group(boss, worker)
		.channel(NioServerSocketChannel.class)
		.option(ChannelOption.SO_BACKLOG, 200)
		.childOption(ChannelOption.SO_KEEPALIVE, true)
		.childHandler(serverInitializer);
	}

	/**
	 * @throws CertificateException 
	 * @throws SSLException 
	 * 
	 */
	private void prepareSslContext() throws CertificateException, SSLException {
		SslProvider provider = SslProvider.isAlpnSupported(SslProvider.OPENSSL) ? SslProvider.OPENSSL : SslProvider.JDK;
		SelfSignedCertificate ssc = new SelfSignedCertificate();
		sslContext = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).sslProvider(provider)
				/*
				 * NOTE: the cipher filter may not include all ciphers required by the HTTP/2
				 * specification. Please refer to the HTTP/2 specification for cipher
				 * requirements.
				 */
				.ciphers(Http2SecurityUtil.CIPHERS, SupportedCipherSuiteFilter.INSTANCE)
				.applicationProtocolConfig(new ApplicationProtocolConfig(Protocol.ALPN,
						// NO_ADVERTISE is currently the only mode supported by both OpenSsl and JDK
						// providers.
						SelectorFailureBehavior.NO_ADVERTISE,
						// ACCEPT is currently the only mode supported by both OpenSsl and JDK
						// providers.
						SelectedListenerFailureBehavior.ACCEPT, ApplicationProtocolNames.HTTP_2,
						ApplicationProtocolNames.HTTP_1_1))
				.build();
	}

	@Override
	public void bind() {
		init();
		try {
			channelFuture = serverBootstrap.bind(new InetSocketAddress(host, port)).sync();
			if (channelFuture.isSuccess()) {
				logger.info("{} started on address {}:{}", instanceName, host, port);
				channelFuture.channel().closeFuture().sync();
			} else {
				shutDown();
			}
		} catch (InterruptedException e) {
			logger.error(e);
			if(logger.isTraceEnabled()) {
				e.printStackTrace();
			}
			shutDown();
		}
	}

	/**
	 * 
	 */
	public void shutDown() {
		channelFuture.channel().close();
		worker.shutdownGracefully();
		boss.shutdownGracefully();
	}
}
