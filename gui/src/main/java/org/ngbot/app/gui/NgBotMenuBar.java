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

/**
 * @author Arpan Mukhopadhyay
 */
@Slf4j
public class NgBotMenuBar extends JMenuBar {

    private JMenu fileMenu;

    public void createMenuBar() {
        makeFileMenu();

        this.add(fileMenu);
    }

    private void makeFileMenu() {
    }
}
