/*
 * ngBot (https://github.com/arixion/ngBot) is a free software to simulate various network protocol
 * Copyright (C) 2022-present.  Arpan Mukhopadhyay. All rights reserved
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.ngbot.stack.udp;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.ngbot.decoder.Decoder;
import org.ngbot.decoder.bogus.BogusDecoder;
import org.ngbot.decoder.core.DecoderLoadingException;
import org.ngbot.stack.common.StackListener;
import org.ngbot.stack.common.StackListenerConfiguration;
import org.ngbot.util.Assert;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Arpan Mukhopadhyay
 *
 */
@Slf4j
public class UDPListener implements StackListener {

    @Getter
    private final String name;

    private final Bootstrap bootstrap;
    private final EventLoopGroup bossGroup;
    private final StackListenerConfiguration configuration;
    
    private List<Channel> channels = new ArrayList<Channel>();


    /**
     * @param configuration
     * @throws Exception 
     */
    public UDPListener(StackListenerConfiguration configuration) throws Exception {
        Assert.notNull(configuration, "Invalid configuration argument null");
    	this.configuration = configuration;
        this.bossGroup = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        this.name = this.configuration.getName();
        init();
    }

    /**
     * @throws Exception 
     *
     */
    private void init() throws Exception {
		bind();
    }

    @Override
    public void bind() throws Exception {
    	InetSocketAddress listenAddress = new InetSocketAddress(configuration.getEndpoint().getAddress().getHostName(), configuration.getEndpoint().getPort());
    	try {
			final Decoder messageDecoder = configuration.getDecoder() == null ? getDefaultDecoder() : configuration.getDecoder();
	        this.bootstrap.group(bossGroup)
	                .channel(NioDatagramChannel.class)
	                .option(ChannelOption.SO_BROADCAST, true)
	                .handler(new ChannelInitializer<Channel>() {

	                    @Override
	                    protected void initChannel(Channel ch) throws Exception {
	                        ChannelPipeline pipeline = ch.pipeline();
	                        pipeline.addLast(messageDecoder);

	                    }
	                }).localAddress(listenAddress);
	        channels.add(bootstrap.bind().sync().channel());
		} catch (DecoderLoadingException e) {
			logger.error(e.getMessage(), e);
			if (logger.isTraceEnabled()) {
				e.printStackTrace();
			}
		} catch (InterruptedException e) {
			logger.error("Failed to bind to socket " + listenAddress);
		}
    }

    @Override
    public void stop() {
        logger.info("Shutting down listener instance {}[{}]", configuration.getName(), configuration.getId());
        closeChannels();
        closeGroup();
    }
    

    /**
     * Returns the default decoder. Throws exception if
     * default decoder loading fails
     *
     * @return Decoder the default decoder
     * @throws DecoderLoadingException
     */
    private Decoder getDefaultDecoder() throws DecoderLoadingException {
        return new BogusDecoder();
    }
    
    /**
     * 
     */
    private void closeChannels() {
    	for (Channel channel : channels) {
    		try {
    			logger.info("Closing channel on socket [{}]", channel.localAddress());
    			channel.close().sync();
    		} catch (InterruptedException e) {
    			logger.error(e.getMessage(), e);
			}
    	}
    }
    
    /**
     * 
     */
    private void closeGroup() {
    	try {
    		bossGroup.shutdownGracefully();
    	} catch (Exception e) {
    		logger.error(e.getMessage(), e);
		}
    }
}
