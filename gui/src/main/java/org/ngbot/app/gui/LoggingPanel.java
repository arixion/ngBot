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
import java.io.IOException;
import java.io.Serial;

/**
 * @author Arpan Mukhopadhyay
 */
@Slf4j
public class LoggingPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 6761177260406622942L;

    private GuiManager guiManager;
    private JTextArea logTextArea;
    private JPopupMenu logContextMenu;
    private final Font defaultConsoleFont;
    private final Font defaultFont;

    /**
     *
     */
    public LoggingPanel(GuiManager guiManager) throws IOException, FontFormatException {
        this.guiManager = guiManager;
        defaultConsoleFont = guiManager.getFontManager().defaultConsoleFont();
        defaultFont = guiManager.getFontManager().defaultFont();
        logTextArea = init();
        addLogContextMenu();
    }

    /**
     *
     * @return
     */
    private JTextArea init() {
        this.setLayout(new BorderLayout());
        final JScrollPane scrollPane;
        final JTextArea jTextArea = new JTextArea(15, 80);
        jTextArea.setFont(defaultConsoleFont);
        jTextArea.setText("11:50:51 PM: Execution finished ':launcher:Launcher.main()'.");
        jTextArea.setEditable(false);
        jTextArea.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        scrollPane = new JScrollPane(jTextArea);
        add(scrollPane, BorderLayout.CENTER);
        return jTextArea;
    }

    /**
     *
     * @throws IOException
     * @throws FontFormatException
     */
    private void addLogContextMenu() throws IOException, FontFormatException {
        logContextMenu = new JPopupMenu();
        //TODO - Event driven menu item
        logContextMenu.add(createContextMenuItem("Copy"));
        logContextMenu.add(new JSeparator());
        logContextMenu.add(createContextMenuItem("Clear all"));
        logTextArea.setComponentPopupMenu(logContextMenu);
    }

    /**
     *
     * @param text
     * @return
     */
    private JMenuItem createContextMenuItem(String text) {
        JMenuItem item = new JMenuItem();
        item.setFont(defaultFont);
        item.setText(text);
        return item;
    }
}
