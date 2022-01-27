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

package org.ngbot.app.gui;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.util.Map;

/**
 * @author Arpan Mukhopadhyay
 */
@Slf4j
public class LoggingPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 6761177260406622942L;

    private JTextArea logTextArea;

    /**
     *
     */
    public LoggingPanel() {
        logTextArea = init();
    }

    /**
     *
     * @return
     */
    private JTextArea init() {
        this.setLayout(new BorderLayout());
        final JScrollPane scrollPane;
        final JTextArea jTextArea = new JTextArea(15, 80);
        Font f;
        try {
            f = Font.createFont(Font.TRUETYPE_FONT, new File("/home/arixion/Workspaces/ngBot/gui/src/main/resources/org/ngbot/app/RobotoMono-Regular.ttf"));
            Map attributes = f.getAttributes();
            attributes.put(TextAttribute.LIGATURES, TextAttribute.LIGATURES_ON);
            attributes.put(TextAttribute.SIZE, 14f);
            f = f.deriveFont(attributes);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(f);
            jTextArea.setFont(f);
            jTextArea.setText("Some dummy text");
//            jTextArea.setEditable(false);
            jTextArea.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
            scrollPane = new JScrollPane(jTextArea);
            add(scrollPane, BorderLayout.CENTER);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jTextArea;
    }
}
