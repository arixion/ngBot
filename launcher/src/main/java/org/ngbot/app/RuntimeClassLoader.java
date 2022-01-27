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

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * This class will load class in runtime dynamically
 * @author Arpan Mukhopadhyay
 */
public class RuntimeClassLoader extends URLClassLoader {

    /**
     *
     * @param urls
     * @param parent
     */
    public RuntimeClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    /**
     *
     * @param urls
     */
    public RuntimeClassLoader(URL[] urls) {
        super(urls);
    }

    /**
     *
     * @param urls
     * @param parent
     * @param factory
     */
    public RuntimeClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    /**
     *
     * @param url
     */
    public void addURL(URL url) {
        super.addURL(url);
    }

    /**
     *
     * @param urls
     */
    public static void updateLoader(URL... urls) {
        RuntimeClassLoader loader = (RuntimeClassLoader) Thread.currentThread().getContextClassLoader();
        for (URL url : urls) {
            loader.addURL(url);
        }
    }
}
