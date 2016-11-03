package io.jhonesdeveloper.swing.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esta classe contém alguns métodos que auxiliam a entrada e saída de arquivos.
 * 
 * @author Caique Jhones
 * @version 2
 */
public final class FileTools {
	
	private static final Logger log = LoggerFactory.getLogger(FileTools.class);

	private FileTools() {
	}

	/**
	 * Faz a cópia de um diretório.
	 * 
	 * @param in
	 *        O diretório que deverá ser copiado.
	 * @param out
	 *        O diretório ao qual deverá ser colocado a cópia.
	 */
	public static void copyDirectory(File in, File out) {
		int len = in.getPath().length();
		for (int i = 0; i < in.listFiles().length; i++) {
			listDirectory(in.listFiles()[i], out, len);
		}
	}

	/**
	 * Faz a cópia de um diretório e cria o diretório de destino.
	 * 
	 * @param in
	 *        O diretório que deverá ser copiado.
	 * @param out
	 *        O diretório ao qual deverá ser colocado a cópia.
	 */
	public static void copyDirectoryAndOut(File in, File out) {
		int len = in.getPath().length();
		out.mkdir();
		for (int i = 0; i < in.listFiles().length; i++) {
			listDirectory(in.listFiles()[i], out, len);
		}
	}

	private static void listDirectory(File in, File out, int len) {
		if (in.isDirectory()) {
			File dir = new File(out.getPath() + in.getPath().substring(len));
			if (!dir.exists()) {
				dir.mkdir();
			}
			for (int i = 0; i < in.listFiles().length; i++) {
				listDirectory(in.listFiles()[i], out, len);
			}
		} else {
			Path pathIn = in.toPath();
			Path pathOut = new File(out.getPath() + in.getPath().substring(len)).toPath();
			try {
				Files.copy(pathIn, pathOut);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erro ao criar arquivo");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Faz a descompressão de um arquivo zipado.
	 * 
	 * @param in
	 *        InputStream associado ao aquivo .zip
	 * @param out
	 *        Diretório onde o arquivo deve ser descompactado.
	 * @param observer
	 *        Caso haja a necessidade de acompanhar o progresso de leitura o observador será
	 *        notificado enquanto o processo de leitura avança. Pode ser um valor nulo, caso não
	 *        queira ser notificado.
	 * @throws IOException
	 *         Todos os erros relacionados as operações de leitura e escrita dos arquivos.
	 */
	public static void unzip(InputStream in, File out, Observer<Integer> observer) throws IOException {
		if (!out.exists()) out.mkdirs();

		byte[] buffer = new byte[4096];
		int readed = 0;
		ZipInputStream zipInputStream = new ZipInputStream(in);
		ZipEntry ze = zipInputStream.getNextEntry();
		while (ze != null) {

			String fileName = ze.getName();
			File newFile = new File(out + File.separator + fileName);
			
			log.info("file uncompressed {}.", ze.getSize());
			log.info("file unzip : " + newFile.getAbsoluteFile());

			// create all non exists folders
			// else you will hit FileNotFoundException for compressed folder
			new File(newFile.getParent()).mkdirs();

			FileOutputStream fos = new FileOutputStream(newFile);

			int len;
			while ((len = zipInputStream.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
				readed += len;
				if (observer != null) {
					int percent = (int) (readed / ze.getSize()) * 100;
					observer.onChange(percent);
				}
			}

			fos.close();
			ze = zipInputStream.getNextEntry();
		}

		zipInputStream.closeEntry();
		zipInputStream.close();

		log.info("Fim da descompressão do arquivo");
	}

}
