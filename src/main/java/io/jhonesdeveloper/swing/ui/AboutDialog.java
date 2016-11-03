/*
 * AboutDialog.java.java
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
package io.jhonesdeveloper.swing.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.Border;

/**
 * Dialog de ajuda básico.
 * 
 * @author Caique Jhones
 * @version 1
 * @since 1.0.0
 * @see javax.swing.JDialog
 * @see Builder
 */
public class AboutDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private final Border empty5Border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
	
	private String title = "Sobre";
	private String aboutTitle = "Sobre";
	private String licenceTitle = "Licença";
	private String creditsTitle = "Créditos";
	private String aboutMessage = "";
	private String licenceMessage = "";
	private String creditsMessage = "";
	
	private Image icon;
	
	private AboutDialog(JFrame parent) {
		super(parent);
		
		JPanel cp = new JPanel();
		cp.setBorder(empty5Border);
		cp.setLayout(new BorderLayout());
		
		final JTextPane txtArea = new JTextPane();
		txtArea.setContentType("text/html");
		txtArea.setEditable(false);
		txtArea.setBorder(empty5Border);
		txtArea.setText(aboutMessage);
		
		JScrollPane scrollPane = new JScrollPane(txtArea);
		scrollPane.setPreferredSize(new Dimension(300, 200));
		
		JPanel center = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) center.getLayout();
		flowLayout_1.setHgap(50);
		flowLayout_1.setVgap(10);
		center.add(scrollPane);
		cp.add(center, BorderLayout.CENTER);
		
		JButton about = new JButton(aboutTitle);
		about.addActionListener(e -> txtArea.setText(aboutMessage));
		JButton licence = new JButton(licenceTitle);
		licence.addActionListener(e -> txtArea.setText(licenceMessage));
		JButton credits = new JButton(creditsTitle);
		credits.addActionListener(e -> txtArea.setText(creditsMessage));
		
		JPanel top = new JPanel();
		FlowLayout flowLayout = (FlowLayout) top.getLayout();
		flowLayout.setHgap(0);
		top.add(about);
		top.add(licence);
		top.add(credits);
		cp.add(top, BorderLayout.NORTH);
		
		JButton ok = new JButton("OK");
		ok.addActionListener(e -> dispose());
		
		JPanel bottom = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) bottom.getLayout();
		flowLayout_2.setAlignment(FlowLayout.RIGHT);
		bottom.add(ok);
		cp.add(bottom, BorderLayout.SOUTH);
		
		setTitle(title);
		setContentPane(cp);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		if (icon != null) setIconImage(icon);
		pack();
		
	}
	
	/**
	 * Classe para criação do diálogo de ajuda.
	 * 
	 * @author Caique Jhones
	 * @version 1
	 */
	public static class Builder {
		private AboutDialog dialog;
		
		public Builder(JFrame parent) {
			dialog = new AboutDialog(parent);
		}
		
		public AboutDialog build() {
			return dialog;
		}
		
		public Builder withTitle(String title) {
			dialog.title = title;
			return this;
		}
		
		public Builder withIcon(Image icon) {
			dialog.icon = icon;
			return this;
		}
		
		public Builder withAboutTitle(String title) {
			dialog.aboutTitle = title;
			return this;
		}
		
		public Builder withAboutMessage(String message) {
			dialog.aboutMessage = message;
			return this;
		}
		
		public Builder withLicenceTitle(String title) {
			dialog.licenceTitle = title;
			return this;
		}
		
		public Builder withLicenceMessage(String message) {
			dialog.licenceMessage = message;
			return this;
		}
		
		public Builder withCreditsTitle(String title) {
			dialog.creditsTitle = title;
			return this;
		}
		
		public Builder withCreditsMessage(String message) {
			dialog.creditsMessage = message;
			return this;
		}
	}
}
