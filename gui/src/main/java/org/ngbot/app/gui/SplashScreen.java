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
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.Serial;

/**
 * @author Arpan Mukhopadhyay
 */
@Slf4j
public class SplashScreen extends JDialog {

    @Serial
    private static final long serialVersionUID = -5995433139691651455L;

    private final JProgressBar progressBar = new JProgressBar(0,100);

    /**
     *
     */
    public SplashScreen() {
        setLayout(new BorderLayout());
        add(createLogo(), BorderLayout.CENTER);
        add(progressBar, BorderLayout.SOUTH);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setAutoRequestFocus(true);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     *
     * @return
     */
    private JComponent createLogo() {
        JLabel logo = new JLabel();
        logo.setBorder(new EmptyBorder(10, 10, 10, 10));
        ImageIcon logoIcon = new ImageIcon("/home/arixion/Workspaces/ngBot/gui/src/main/resources/org/ngbot/app/splash.png");
        logo.setIcon(logoIcon);
        return logo;
    }

    /**
     *
     * @param progress
     */
    public void updateProgress(final int progress) {
        SwingUtilities.invokeLater(() -> progressBar.setValue(progress));
    }

    /**
     *
     */
    public void showSplash() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    /**
     *
     */
    public void close() {
        SwingUtilities.invokeLater(() -> {
            setVisible(false);
            dispose();
        });
    }
}
