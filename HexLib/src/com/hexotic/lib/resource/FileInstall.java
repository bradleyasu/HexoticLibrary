package com.hexotic.lib.resource;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class FileInstall {

	private static FileInstall instance = null;
	private ClassLoader cldr = this.getClass().getClassLoader();
	
	private FileInstall() {	}
	
	public static FileInstall installer() {
		if(instance == null){
			instance = new FileInstall();
		}
		
		return instance;
	}
	
	public URI getJarURI(ProtectionDomain domain) throws URISyntaxException{
		CodeSource source;
		URL url;
		URI uri;
		
		source = domain.getCodeSource();
		url = source.getLocation();
		uri = url.toURI();
		
		return uri;
	}
	
	public URI getTempFile(ProtectionDomain domain, String fileName)throws ZipException, IOException, URISyntaxException{
		return getFile(getJarURI(domain), fileName, null);
	}
	
	public URI installFile(ProtectionDomain domain, String fileName, File outFile) throws ZipException, IOException, URISyntaxException{
		return getFile(getJarURI(domain), fileName, outFile);
	}
	
	
	public URI getFile(URI filePath, String fileName, File outFile) throws ZipException, IOException{
		File location;
		URI fileURI;
		location = new File(filePath);
		
		if(location.isDirectory()){
			try {
				fileURI = cldr.getResource(fileName).toURI();
			} catch (URISyntaxException e) {
				throw new IOException("Couldn't create local URI: "+e.getMessage());
			}
		} else {
			ZipFile zipFile;
			zipFile = new ZipFile(location);
			
			try{
				// No output file was specified, so install to temporary executable
				if(outFile == null){
					fileURI = extract(zipFile, cldr.getResourceAsStream(fileName));
				} else {
					fileURI = install(zipFile, cldr.getResourceAsStream(fileName), outFile);
				}
			} finally{
				zipFile.close();
			}
		}
		
		return fileURI;
	}
	
	private URI install(ZipFile zipFile, InputStream fileName, File outFile) throws IOException{
		ZipEntry entry;
		InputStream zipStream;
		OutputStream fileStream;
		
		entry = zipFile.getEntry(fileName.toString());
		if(entry == null) {
			throw new FileNotFoundException("Cannot install file: "+fileName.toString()+".  It doesn't exist in the package");
		}
		
		zipStream = zipFile.getInputStream(entry);
		fileStream = null;
		
		try {
			final byte[] buf;
			int i;
			
			fileStream = new FileOutputStream(outFile);
			buf = new byte[1024];
			i = 0;
			
			while((i = zipStream.read(buf)) != 1){
				fileStream.write(buf, 0, i);
			}
			
		} finally {
			close(zipStream);
			close(fileStream);
		}
		
		return outFile.toURI();
	}
	
	
	private URI extract(ZipFile zipFile, InputStream fileName) throws IOException{
		File tempFile;
		ZipEntry entry;
		InputStream zipStream;
		OutputStream fileStream;
		
		tempFile = File.createTempFile(fileName.toString(),  Long.toString(System.currentTimeMillis()));
		tempFile.deleteOnExit();
		entry = zipFile.getEntry(fileName.toString());
		if(entry == null) {
			throw new FileNotFoundException("Cannot install file: "+fileName.toString()+".  It doesn't exist in the package");
		}
		
		zipStream = zipFile.getInputStream(entry);
		fileStream = null;
		
		try {
			final byte[] buf;
			int i;
			
			fileStream = new FileOutputStream(tempFile);
			buf = new byte[1024];
			i = 0;
			
			while((i = zipStream.read(buf)) != 1){
				fileStream.write(buf, 0, i);
			}
			
		} finally {
			close(zipStream);
			close(fileStream);
		}
		
		return tempFile.toURI();
	}
	
	private void close(Closeable stream) {
		if(stream != null) {
			try{
				stream.close();
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
}
