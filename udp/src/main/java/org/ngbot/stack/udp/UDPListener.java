/*
 * ngBot (https://github.com/arixion/ngBot) is a free software to simulate varius network protocol
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

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.ngbot.decoder.Decoder;
import org.ngbot.decoder.bogus.BogusDecoder;
import org.ngbot.decoder.core.DecoderLoadingException;
import org.ngbot.stack.common.StackListener;
import org.ngbot.stack.common.StackListenerConfiguration;

import java.net.InetSocketAddress;

/**
 * @author Arpan Mukhopadhyay
 */
@Slf4j
public class UDPListener implements StackListener {

    @Getter
    private final String name;

    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup;
    private final StackListenerConfiguration configuration;


    /**
     * @param configuration
     */
    public UDPListener(StackListenerConfiguration configuration) {
        this.configuration = configuration;
        this.eventLoopGroup = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
        this.name = this.configuration.getName();
        init();
    }

    /**
     *
     */
    private void init() {
        Decoder messageDecoder = configuration.getDecoder();
        if (messageDecoder == null) {
            //load the default decoder considering user does not want to convert raw UDP message
            try {
                messageDecoder = loadDefaultDecoder();
            } catch (DecoderLoadingException e) {
                
            }
        }
        InetSocketAddress listenAddress = InetSocketAddress.createUnresolved(configuration.getEndpoint().getAddress().getHostAddress(), configuration.getEndpoint().getPort());
        this.bootstrap.group(this.eventLoopGroup)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.AUTO_CLOSE, false)
                .handler(new ChannelInitializer<Channel>() {

                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

                    }
                }).localAddress(listenAddress);
    }

    @Override
    public void bind() throws Exception {
        bootstrap.bind().sync().channel();
    }

    @Override
    public void stop() {
        eventLoopGroup.shutdownGracefully();
    }

    /**
     * Loads the default decoder. Throws exception if
     * default decoder loading fails
     *
     * @return Decoder
     * @throws DecoderLoadingException
     */
    private Decoder loadDefaultDecoder() throws DecoderLoadingException {
        return new BogusDecoder();
    }
}
