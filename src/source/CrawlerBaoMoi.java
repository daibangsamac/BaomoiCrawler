package source;
import java.io.IOException;

import java.util.LinkedList;


public class CrawlerBaoMoi {
	
	private static String[] sourceLinks;
	
	public static void prepare()
	{
		sourceLinks = new String[7];
		
		sourceLinks[0] = "http://www.baomoi.com/Home/TheThao";
		sourceLinks[1] = "http://www.baomoi.com/Home/GiaiTri";
		sourceLinks[2] = "http://www.baomoi.com/Home/PhapLuat";
		sourceLinks[3] = "http://www.baomoi.com/Home/GiaoDuc";
		sourceLinks[4] = "http://www.baomoi.com/Home/SucKhoe";
		sourceLinks[5] = "http://www.baomoi.com/Home/OtoXemay";
		sourceLinks[6] = "http://www.baomoi.com/Home/NhaDat";
	}
	
	public static void main( String[] args) throws IOException, InterruptedException {
		
		int i, j;
		String link, lineHtml;
		LinkedList<String> links;
		Web web;

		prepare();
		
		for (i = 0; i < 7; i++)
		{
			link = sourceLinks[i] + ".epi";
			FileReadWrite file = new FileReadWrite();
			file.createFile("0_LinkOfArticle.txt", FileReadWrite.createPath(link));
			
			System.out.println("Crawler " + file.getPathFile() + " running...");
			file.setBufferWrite();
			// Duyệt 1000 trang tiêu để ở mỗi category và lưu các đường link vào file
			for (j = 1; j <= 10000; j++)
			{				
				System.out.print("    *) Page: " + j + " of " + file.getPathFile());
				link = sourceLinks[i] + "/p/" + j + ".epi";
				web = new Web(link);
				
				//Lấy các link của các bài báo ở mỗi trang 
				links = web.getLinksOfArticles();
				System.out.println(" Getted: " + links.size() + " articales ");
				
				while (!links.isEmpty()){
					file.bufferWrite().write(links.pop() + "\n");
				}
			}
			file.closeWrite();
			System.out.println(file.getPathFile() + ": get links: DONE!");
			
			/**
			 * Đọc file links, lấy nội dung của từng bài báo
			 */
			System.out.println("Get Articles from ' " + file.getPathFile() + " ' starting...");
			file.setBufferRead();
			j = 0;
			
			while ((link = file.bufferRead().readLine()) != null)
			{
				j++;
				String nameFile = "" + j;
				while (nameFile.length() < 6) nameFile = "0" + nameFile;
				//Lấy nội dung của từng bài báo
				web = new Web(link);
				
				FileReadWrite fileArticle = new FileReadWrite();
				fileArticle.createFile(nameFile + ".json", FileReadWrite.createPath(link));
				fileArticle.setBufferWrite();
				
				Article article = new Article();
				article.setUrl(link);
				while ((lineHtml = web.readLine()) != null)
				{
					article.setTime(web.checkLineOfTime(lineHtml, article.getTime()));
					article.setTitle(web.checkLineOfTitle(lineHtml, article.getTitle()));
					article.setCategory(web.checkLineOfCategory(lineHtml, article.getCategory()));
					article.setDescription(web.checkLineOfDescription(lineHtml, article.getDescription()));;
					article.setContent(web.checkLineOfContent(lineHtml, article.getContent()));
					article.setKeyWord(web.checkLineOfKeyWords(lineHtml));
					article.setTrends(web.checkLineOfTrends(lineHtml), web);
				}
				
				GsonParser gsonParser = new GsonParser();
				gsonParser.writeOutput(article, fileArticle);
				
				fileArticle.closeWrite();
				
				System.out.println("    *) " + j + " in " + fileArticle.getPathFile());
			}
			file.closeRead();
			System.out.println(file.getPathFile() + ": get Articles: DONE! Total: " + j);
		}
	}
}
