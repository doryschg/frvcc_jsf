package bo.gob.sin.sre.fac.frvcc.jsf.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import bo.gob.sin.str.util.compresion.CompresorZip;

@ManagedBean
@ViewScoped
public class FileUploadBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7173843855643973415L;
	private String fileContentEncode64Compress;
	private File file;
	private String fileName;
	private String hashFile;
	private long size;

	public void handleUpload(FileUploadEvent event) {

		UploadedFile file = event.getFile();
		byte[] vArchivo = file.getContents();
		this.size = vArchivo.length;
		this.fileName = file.getFileName();
		this.file = uploadedFileToFileConverter(event.getFile());

//		try {
//			byte[] vArchivoCompreso = file.getContents();
//			vArchivoCompreso = CompresorZip.zipBytes(fileName, file.getContents());
//			String vEncodedBase64 = new String(Base64.getEncoder().encode(vArchivoCompreso));
//			this.fileContentEncode64Compress = vEncodedBase64;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public static File uploadedFileToFileConverter(UploadedFile uf) {
		File file = new File("_" + uf.getFileName());

		try (InputStream stream = uf.getInputstream()) {
			FileUtils.copyInputStreamToFile(stream, file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return file;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileContentEncode64Compress() {
		return fileContentEncode64Compress;
	}

	public void setFileContentEncode64Compress(String fileContentEncode64Compress) {
		this.fileContentEncode64Compress = fileContentEncode64Compress;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getHashFile() {
		return hashFile;
	}

	public void setHashFile(String hashFile) {
		this.hashFile = hashFile;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}