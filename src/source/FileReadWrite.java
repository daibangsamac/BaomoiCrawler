package source;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.Normalizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class FileReadWrite {
	
	private String nameFile;
	private String path;
	private File file;
	private BufferedReader bufferRead;
	private BufferedWriter bufferWrite;
	
	public FileReadWrite() {
		// TODO Auto-generated constructor stub
	}
	
	public void createFile(String nameFile, String path) throws IOException {		
		this.nameFile = nameFile;
		this.path = path;
		
		this.file = new File(path);
		if (!file.exists())
			new File(path).mkdirs();
		this.file = new File(path, nameFile);
	}
	
	public void setBufferRead () throws FileNotFoundException {
		FileInputStream fileIn = new FileInputStream(this.file);
		InputStreamReader inputStream = new InputStreamReader(fileIn);
		this.bufferRead = new BufferedReader(inputStream);
	}
	
	public void setBufferWrite() throws FileNotFoundException {
		FileOutputStream fileOut = new FileOutputStream(this.file);
		OutputStreamWriter outputStream = new OutputStreamWriter(fileOut);
		this.bufferWrite = new BufferedWriter(outputStream);
	}
	
	public String getNameFile() {
		return this.nameFile;
	}
	public String getPathFile() {
		return this.path;
	}

	public BufferedReader bufferRead() {
		return this.bufferRead;
	}
	public BufferedWriter bufferWrite() {
		return this.bufferWrite;
	}
	
	public void closeRead() throws IOException {
		this.bufferRead.close();
	}
	public void closeWrite() throws IOException {
		this.bufferWrite.close();
	}
	
	/**
	 * Tạo đường dẫn từ các categoty của bài báoáo
	 * @param link url bài báo
	 * @return xâu đường dẫn thư mục chứa bài báo
	 * @throws IOException
	 */
	public static String createPath(String link) throws IOException {
		String lineHtml, path;
		Web web = new Web(link);
		Document doc;
		
		while (true){
			lineHtml = web.readLine();
			if (lineHtml.indexOf("page-header article-header") != -1)
			{
				int st = lineHtml.indexOf("<span>");
        		int fn = lineHtml.indexOf("</span") + 7;
        		path = lineHtml.substring(st,  fn);
        		doc = Jsoup.parse(path);
        		path = doc.text();
        		
        		//Tạo đường dẫn thư lục lưu trữ
        		path = path.replaceAll(" > ", "/");
        		path = path.replaceAll(" ", "");
        		path = Normalizer.normalize(path, Normalizer.Form.NFD).replace("đ", "d").replace("Đ", "D").replaceAll("[^\\p{ASCII}]", "");
        		path = path.toLowerCase();
				break;
			}
		}
		return path;
	}	
}
