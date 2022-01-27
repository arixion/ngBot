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

package org.ngbot.app;

import java.lang.reflect.Method;
import java.net.URL;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arpan Mukhopadhyay
 */
public class Launcher {

    private static final RuntimeClassLoader loader;

    static {
        List<URL> jars = new ArrayList<>();
        loader = createClassLoader(jars);
    }

    /**
     *
     * @param jars
     * @return
     */
    @SuppressWarnings("removal")
    private static RuntimeClassLoader createClassLoader(List<URL> jars) {
        return java.security.AccessController.doPrivileged(
                (PrivilegedAction<? extends RuntimeClassLoader>) () ->
                        new RuntimeClassLoader(jars.toArray(new URL[jars.size()]))
        );
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            Class<?> clazz = loader.loadClass("org.ngbot.app.NgBotApplication");
            Object instance = clazz.getDeclaredConstructor().newInstance();
            Method startupMethod = clazz.getMethod("start", new Class[] {new String[0].getClass()});
            startupMethod.invoke(instance, new Object[]{args});
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
