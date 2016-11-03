/*
 * Initialize.java
 *
 * Copyright 2016 Caique Jhones
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.jhonesdeveloper.swing;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.alee.laf.WebLookAndFeel;
import com.apple.eawt.Application;

import io.jhonesdeveloper.swing.adapter.MacApplicationAdapter;
import io.jhonesdeveloper.swing.adapter.MacMenu;
import io.jhonesdeveloper.swing.util.OS;

/**
 * @author Caique Jhones
 * @version 1
 * @since 1.0.0
 */
public final class Initialize {

	private Initialize() {
	}

	public static void init(final JFrame root, final MacMenu macMenu) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Install WebLaF as application L&F
				WebLookAndFeel.install();
				if (OS.getCurrent() == OS.MAC && macMenu != null) {
					// set some mac-specific properties
					System.setProperty("apple.awt.graphics.EnableQ2DX", "true");
					System.setProperty("apple.laf.useScreenMenuBar", "true");
					System.setProperty("com.apple.mrj.application.apple.menu.about.name", macMenu.appName());
					// create an instance of the Mac Application class, so i can handle the
					// mac quit event with the Mac ApplicationAdapter
					Application macApplication = Application.getApplication();
					MacApplicationAdapter adapter = new MacApplicationAdapter(macMenu);
					macApplication.addApplicationListener(adapter);
					// need to enable the preferences option manually
					macApplication.setEnabledPreferencesMenu(true);
				}

				root.setVisible(true);
			}
		});
	}

	public static void init(final JFrame root) {
		init(root, null);
	}
}
