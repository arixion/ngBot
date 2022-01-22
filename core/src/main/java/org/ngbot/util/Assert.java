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

package org.ngbot.util;

import java.util.function.Supplier;

/**
 * @author Arpan Mukhopadhyay
 */
public class Assert {

    /**
     * @param object
     * @param message
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     *
     * @param object
     * @param messageSupplier
     */
    public static void notNull(Object object, Supplier<String> messageSupplier) {
        if (object == null) {
            throw new IllegalArgumentException(getMessage(messageSupplier));
        }
    }

    /**
     *
     * @param text
     * @param message
     */
    public static void notBlankText(String text, String message) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     *
     * @param text
     * @param messageSupplier
     */
    public static void notBlankText(String text, Supplier<String> messageSupplier) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(getMessage(messageSupplier));
        }
    }

    /**
     * @param messageSupplier
     * @return
     */
    private static String getMessage(Supplier<String> messageSupplier) {
        return messageSupplier != null ? messageSupplier.get() : null;
    }

    /**
     *
     * @param port
     * @param message
     */
    public static void isPort(int port, String message) {
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException(message);
        }
    }
}
