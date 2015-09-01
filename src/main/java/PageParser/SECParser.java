package PageParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Created by xli1 on 8/17/15.
 */
public class SECParser extends Parser {
  Map<String, String> pageURLMap = null;

  private void initPageURLMap(String urlInfoPath) {
    try {
      pageURLMap = new HashMap<String, String>();
      BufferedReader reader = new BufferedReader(new FileReader(urlInfoPath));
      String temp = null;
      while ((temp = reader.readLine()) != null) {
        String[] strs = temp.split("\t");
        String key = strs[0] + "/" + strs[3] + "-" + strs[4];
        String url = strs[5];
        pageURLMap.put(key, url);
      }

    } catch (IOException exception) {
      LOGGER.info(exception.getMessage() + " " + urlInfoPath);
    }
  }

  public SECParser(String inputHome, String outputHome) {
    super(inputHome, outputHome);
    initPageURLMap("/Users/xli1/project/relationship-analysis/test/sec_pages_urls.txt");
    System.out.println(pageURLMap.size());
  }

  public void extract() {

  }

  public void extract(String currentFilePath, String rootDir, String outputRoot) {
    if (currentFilePath == null) return;
    File curFile = new File(currentFilePath);
    if (curFile == null || !curFile.exists()) return;

    if (curFile.isFile() && curFile.getName().endsWith(".html")) {
      // is a file
      extractPage(currentFilePath, rootDir, outputRoot);
    } else if (curFile.isDirectory()) {
      // is a directory
      for (String f : curFile.list()) {
        extract(curFile.getAbsolutePath() + '/' + f, rootDir, outputRoot);
      }
    }
  }

  /**
   * Extract the tables displaying in the page. Extract text elements from each row.
   * The first table row might be table header.
   */
  public void extractPage(String filePath, String inputHome, String outputHome) {
    if (filePath == null || filePath.isEmpty()) return;
    File input = new File(filePath);

    // Compute the path of output files
    String outputFilePath = filePath.replace(inputHome, outputHome).replace(".html", ".txt");

    try {
      File outputFile = new File(outputFilePath);
      Document root = Jsoup.parse(input, "utf-8");
      if (root == null) {
        LOGGER.info("Not be able to parse page: " + filePath);
        return;
      }
      // Create folder if necessary
      int i = outputFilePath.indexOf(outputHome) + outputHome.length() + 1;
      while (i >= 0 && i < outputFilePath.length()) {
        i = outputFilePath.indexOf('/', i);
        if (i == -1) break;
        String nextLevelDirectoryStr = outputFilePath.substring(0, i);
        File nextLevelDirectory = new File(nextLevelDirectoryStr);
        if (!nextLevelDirectory.exists()){
          nextLevelDirectory.mkdir();
        }
        i = i + 1;
      }
      BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
      BufferedWriter exceptionWriter = new BufferedWriter(new FileWriter(outputHome + "/" + "exception_list.txt", true));
      StringBuilder textContent = new StringBuilder();
      Elements tables = root.getElementsByTag("table");
      for (Element t : tables) {
        Elements rows = t.getElementsByTag("tr");
        for (Element row : rows) {
          String text = "";
          for (Element col : row.getElementsByTag("td")) {
            String cellText = col.text().replace("\u00a0", "");
            if (cellText.trim().length() > 0) text += cellText.trim() + "\t";
          }
          if (text.trim().length() > 0)
            textContent.append(text.trim() + "\n");
        }
      }

      if (textContent.length() == 0) {
        LOGGER.info("Fail to extract: " + filePath);
        String key = filePath.substring(filePath.indexOf("sec_pages/") + "sec_pages/".length(), filePath.indexOf(".html"));
        if (pageURLMap != null && pageURLMap.containsKey(key)) {
          exceptionWriter.write(key + ".txt\t" + pageURLMap.get(key) + "\n");
          exceptionWriter.close();
        }
      } else {
        writer.write(textContent.toString());
        writer.close();
      }

    } catch (IOException ioException) {
      LOGGER.info(ioException.getMessage());
    }
  }

  public void run() {

  }
}
