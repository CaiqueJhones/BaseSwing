package io.jhonesdeveloper.swing.ui;

import java.io.File;
import java.util.List;

public class EditFileFilter extends javax.swing.filechooser.FileFilter {
	
	String[] extensions;
	String description;
	
	public EditFileFilter(String descr, String... exts) {
		// clone and lowercase the extensions
		extensions = new String[exts.length];
		for (int i = exts.length - 1; i >= 0; i--)
			extensions[i] = exts[i].toLowerCase();
		
		// make sure we have a valid (if simplistic) description
		description = (descr == null ? exts[0] + " files" : descr);
	}
	
	public EditFileFilter(String descr, List<String> exts) {
		this(descr, exts.toArray(new String[exts.size()]));
	}
	
	@Override
	public boolean accept(File f) {
		// we always allow directories, regardless of their extension
		if (f.isDirectory()) return true;
		
		// ok, it's a regular file so check the extension
		for (String extension : extensions)
			if (f.getName().toLowerCase().endsWith(extension)) return true;
		
		return false;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
}
