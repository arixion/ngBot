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

import lombok.Getter;
import lombok.Setter;
import org.ngbot.stack.common.StackEndpoint;
import org.ngbot.stack.common.StackListenerConfiguration;

import java.net.UnknownHostException;

/**
 * @author Arpan Mukhopadhyay
 */
public class UDPStackConfiguration extends StackListenerConfiguration {

    private static final int DEFAULT_PORT = 8805;

    @Getter
    @Setter
    private boolean server;

    /**
     *
     * @param name
     * @param address
     * @param port
     * @throws UnknownHostException
     */
    public UDPStackConfiguration(String name, String address, int port) throws UnknownHostException {
        super(name, null, new StackEndpoint(address, port));
    }

    /**
     *
     * @param name
     * @param address
     * @throws UnknownHostException
     */
    public UDPStackConfiguration(String name, String address) throws UnknownHostException {
        super(name, null, new StackEndpoint(address, DEFAULT_PORT));
    }

    /**
     *
     * @param name
     * @param description
     * @param address
     * @param port
     */
    public UDPStackConfiguration(String name, String description, String address, int port) throws UnknownHostException {
        super(name, description, new StackEndpoint(address, port));
    }

    /**
     *
     * @param name
     * @param description
     * @param address
     * @throws UnknownHostException
     */
    public UDPStackConfiguration(String name, String description, String address) throws UnknownHostException {
        super(name, description, new StackEndpoint(address, DEFAULT_PORT));
    }
}
