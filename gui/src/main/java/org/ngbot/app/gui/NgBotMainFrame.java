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

/**
 * @author Arpan Mukhopadhyay
 */
@Slf4j
public class NgBotMainFrame extends JFrame {

    private LoggingPanel logPanel;
    private GuiManager guiManager = new GuiManager();

    /**
     *
     */
    public NgBotMainFrame() throws IOException, FontFormatException {
        init();
    }

    /**
     *
     */
    private void init() throws IOException, FontFormatException {
        JPanel root = new JPanel(new BorderLayout());
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        root.add(mainSplitPane, BorderLayout.CENTER);
        logPanel = createLoggingPanel(guiManager);
        root.add(logPanel);
        getContentPane().add(root);
        setTitle("NGBot");
        setSize(new Dimension(640, 480));
    }

    /**
     *
     * @return
     */
    private LoggingPanel createLoggingPanel(GuiManager guiManager) throws IOException, FontFormatException {
        LoggingPanel loggingPanel = new LoggingPanel(guiManager);
        loggingPanel.setMinimumSize(new Dimension(100, 150));
        loggingPanel.setPreferredSize(new Dimension(100, 150));
        loggingPanel.setSize(new Dimension(100, 150));
        return loggingPanel;
    }
}
