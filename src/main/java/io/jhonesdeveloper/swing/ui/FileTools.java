package io.jhonesdeveloper.swing.ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JOptionPane;

/**
 * Esta classe contém alguns métodos que auxiliam a entrada e saída de arquivos.
 * @author Caique Jhones
 * @version 2
 */
public final class FileTools {
	
	private FileTools() {
	}
	
	/**
	 * Faz a cópia de um diretório.
	 * @param in O diretório que deverá ser copiado.
	 * @param out O diretório ao qual deverá ser colocado a cópia.
	 */
	public static void copyDirectory(File in, File out){
		int len = in.getPath().length();
		for (int i = 0; i < in.listFiles().length; i++) {
			listDirectory(in.listFiles()[i], out, len);
		}
	}
	
	/**
	 * Faz a cópia de um diretório e cria o diretório de destino.
	 * @param in O diretório que deverá ser copiado.
	 * @param out O diretório ao qual deverá ser colocado a cópia.
	 */
	public static void copyDirectoryAndOut(File in, File out){
		int len = in.getPath().length();
		out.mkdir();
		for (int i = 0; i < in.listFiles().length; i++) {
			listDirectory(in.listFiles()[i], out, len);
		}
	}
	
	private static void listDirectory(File in, File out, int len){
		if(in.isDirectory()){
			File dir = new File(out.getPath()+in.getPath().substring(len));
			if(!dir.exists()){
				dir.mkdir();
			}
			for (int i = 0; i < in.listFiles().length; i++) {
				listDirectory(in.listFiles()[i], out, len);
			}
		}else{
			Path pathIn = in.toPath();
			Path pathOut = new File(out.getPath()+in.getPath().substring(len)).toPath();
			try {
				Files.copy(pathIn, pathOut);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erro ao criar arquivo");
				e.printStackTrace();
			}
		}
	}

}
