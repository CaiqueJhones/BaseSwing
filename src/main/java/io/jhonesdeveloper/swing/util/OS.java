/*
 * OS.java
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
package io.jhonesdeveloper.swing.util;

/**
 * Enum para verificar em qual sistema operacional o software está sendo executado.
 * @author Caique Jhones
 * @version 1
 * @since 1.0.0
 */
public enum OS {
	
	WINDOWS, MAC, LINUX, OTHER;
	
	private static OS os;
	
	static {
		os = OTHER;
		String name = System.getProperty("os.name").toLowerCase();
		if (name.contains("windows"))
			os = WINDOWS;
		else if (name.contains("linux"))
			os = LINUX;
		else if (name.contains("mac os x"))
			os = MAC;
	}

	/**
	 * Retorna o sistema operacional atual.
	 * @return o sistema operacional atual.
	 */
	public static OS getCurrent() {
		return os;
	}
}
