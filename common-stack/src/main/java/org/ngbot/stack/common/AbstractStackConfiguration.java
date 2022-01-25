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
import lombok.Setter;

import java.util.UUID;

/**
 * @author Arpan Mukhopadhyay
 */
public class AbstractStackConfiguration {

    @Getter
    private final String id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;

    /**
     * @param name
     */
    public AbstractStackConfiguration(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    /**
     * @param name
     * @param description
     */
    public AbstractStackConfiguration(String name, String description) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
    }
}
