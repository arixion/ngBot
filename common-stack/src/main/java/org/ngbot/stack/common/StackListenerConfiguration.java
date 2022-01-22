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

package org.ngbot.stack.common;

import lombok.Getter;
import org.ngbot.decoder.Decoder;
import org.ngbot.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Arpan Mukhopadhyay
 */
public abstract class StackListenerConfiguration extends AbstractStackConfiguration {

    @Getter
    private final StackEndpoint endpoint;
    @Getter
    private final Decoder decoder;
    private List<StackEndpoint> secondaryEndpoints = new ArrayList<>(); //required for multi homing enabled endpoints

    /**
     *
     * @param name
     * @param description
     * @param endpoint
     * @param decoder
     */
    public StackListenerConfiguration(String name, String description, StackEndpoint endpoint, Decoder decoder) {
        super(name, description);
        this.endpoint = endpoint;
        this.decoder = decoder;
    }

    /**
     *
     * @param endpoints
     */
    public void addSecondaryEndpoint(StackEndpoint... endpoints) {
        for (StackEndpoint stackEndpoint : endpoints) {
            Assert.notNull(stackEndpoint, "An endpoint can not be null.");
            if (!secondaryEndpoints.contains(stackEndpoint)) {
                secondaryEndpoints.add(stackEndpoint);
            } else {
                throw new IllegalArgumentException("Duplicate stack endpoint '" + stackEndpoint.getAddress() + "'");
            }
        }
    }
}
