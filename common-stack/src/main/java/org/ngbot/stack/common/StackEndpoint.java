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

package org.ngbot.stack.common;

import lombok.Getter;
import org.ngbot.util.Assert;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Arpan Mukhopadhyay
 */
@Getter
public class StackEndpoint {

    private final String id;
    private final InetAddress address;
    private final int port;

    /**
     * @param address
     * @param port
     */
    public StackEndpoint(InetAddress address, int port) {
        Assert.notNull(address, "Address can not be null");
        Assert.notPort(port, "Not a valid port " + port);
        this.id = UUID.randomUUID().toString();
        this.address = address;
        this.port = port;
    }

    /**
     * @param address
     * @param port
     */
    public StackEndpoint(String address, int port) throws UnknownHostException {
        Assert.notBlankText(address, "Address can not be null or blank");
        Assert.notPort(port, "Not a valid port " + port);
        this.id = UUID.randomUUID().toString();
        this.address = InetAddress.getByName(address);
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StackEndpoint)) return false;

        StackEndpoint that = (StackEndpoint) o;

        if (port != that.port) return false;
        return address.equals(that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address.toString(), String.valueOf(port));
    }

    @Override
    public String toString() {
        return "StackEndpoint[Address=" + address.toString() + ", Port=" + port + "]";
    }
}
