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

import org.ngbot.app.gui.NgBotMainFrame;
import org.ngbot.app.gui.SplashScreen;

/**
 * @author Arpan Mukhopadhyay
 *
 */
public class NgBotApplication {

    /**
     *
     * @param args
     */
    public void start(String[] args) {
       boolean gui = true;
       if (gui) {
           startGUI();
       }
    }

    /**
     * Starts the GUI
     */
    public void startGUI() {
        SplashScreen splashScreen = new SplashScreen();
        splashScreen.showSplash();
        final int progress = 100;
        int currentProgress = 2;
        try {
            while (currentProgress <= progress) {
                splashScreen.updateProgress(currentProgress);
                Thread.sleep(50);
                currentProgress++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        NgBotMainFrame main = new NgBotMainFrame();
        main.setVisible(true);
        main.toFront();
        splashScreen.close();
    }
}
