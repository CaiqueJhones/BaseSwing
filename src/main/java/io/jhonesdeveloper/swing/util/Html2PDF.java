/*
 * Html2PDF.java.java
 *
 * Copyright 2016 Caique Jhones
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jhonesdeveloper.swing.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

/**
 * Converte HTML para PDF.
 * @author Caique Jhones
 * @version 1
 */
public final class Html2PDF {
	
	private Html2PDF() {
	}
	
	public static void convert(String input, OutputStream out) throws DocumentException{
        convert(new ByteArrayInputStream(input.getBytes()), out);
	}
	
	public static void convert(InputStream input, OutputStream out) throws DocumentException{
    	Tidy tidy = new Tidy();     
    	Document doc = tidy.parseDOM(input, null);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(doc, null);
        renderer.layout();       
        renderer.createPDF(out);        		
	}
	
}
