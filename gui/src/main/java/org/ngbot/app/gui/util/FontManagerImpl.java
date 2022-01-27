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

package org.ngbot.app.gui.util;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author Arpan Mukhopadhyay
 */
public class FontManagerImpl implements FontManager {

    private GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

    public FontManagerImpl() {}

    /**
     *
     * @return
     */
    public Font defaultConsoleFont() throws IOException, FontFormatException {
        return loadFont("/home/arixion/Workspaces/ngBot/gui/src/main/resources/org/ngbot/app/RobotoMono-Regular.ttf");
    }

    public Font defaultFont() throws IOException, FontFormatException {
        return loadFont("/home/arixion/Workspaces/ngBot/gui/src/main/resources/org/ngbot/app/Roboto-Regular.ttf");
    }


    private Font loadFont(String fontResource) throws IOException, FontFormatException {
        Font f = Font.createFont(Font.TRUETYPE_FONT, new File(fontResource));
        Map attributes = f.getAttributes();
        attributes.put(TextAttribute.LIGATURES, TextAttribute.LIGATURES_ON);
        attributes.put(TextAttribute.SIZE, 15f);
        f = f.deriveFont(attributes);
        ge.registerFont(f);
        return f;
    }
}
