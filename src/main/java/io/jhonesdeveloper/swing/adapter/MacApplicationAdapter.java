/*
 * MacApplicationAdapter.java
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
package io.jhonesdeveloper.swing.adapter;

import com.apple.eawt.ApplicationAdapter;
import com.apple.eawt.ApplicationEvent;

/**
 * @author Caique Jhones
 * @version 1
 * @since 1.0.0
 */
public class MacApplicationAdapter extends ApplicationAdapter {
	
	private MacMenu macMenu;

	/**
	 * @param macMenu
	 */
	public MacApplicationAdapter(MacMenu macMenu) {
		this.macMenu = macMenu;
	}

	@Override
	public void handleAbout(ApplicationEvent e) {
		e.setHandled(true);
		macMenu.about();
	}

	@Override
	public void handlePreferences(ApplicationEvent e) {
		e.setHandled(true);
		macMenu.preferences();
	}

	@Override
	public void handleQuit(ApplicationEvent arg0) {
		macMenu.quit();
	}
}
