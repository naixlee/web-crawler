package PageParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Created by xli1 on 8/17/15.
 */
public class SECParser extends Parser {
  public SECParser(ArrayList<String> filePaths, String outputPath) {
    super(filePaths, outputPath);
  }

  public void extract() {
    for (String filePath : _filePathToProcess) {
      File f = new File(filePath);
      if (f.exists() && f.isFile())
        extractPage(filePath, _outputHome);
      else if (f.isDirectory()) {
        String[] htmlFiles = f.list(new FilenameFilter() {
          @Override
          public boolean accept(File dir, String name) {
            return name.endsWith(".html");
          }
        });
        for (String htmlFile : htmlFiles) {
          extractPage(f.getAbsolutePath() + "/" + htmlFile, _outputHome);
        }
      }
    }
  }

  /**
   * Extract the biggest table in the page. Extract text elements from each row.
   * The first table row might be table header.
   */
  public void extractPage(String filePath, String outputHome) {
    if (filePath == null || filePath.isEmpty()) return;
    File input = new File(filePath);

    int lastindex = filePath.lastIndexOf('/');
    String temp = filePath.substring(0, lastindex);
    String filename = filePath.substring(lastindex).replace("html", "txt");
    String compID = temp.substring(temp.lastIndexOf('/')+1);
    File outputFolder = new File(outputHome + "/" + compID);
    if (!outputFolder.exists()) outputFolder.mkdir();

    try {
      File outputFile = new File(outputFolder + "/" + filename);
      BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
      Document root = Jsoup.parse(input, "utf-8");
      if (root == null) {
        LOGGER.info("Not be able to parse page: " + filePath);
      }
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
            writer.write(text.trim() + "\n");
        }
      }
      writer.close();
    } catch (IOException ioException) {
      LOGGER.info(ioException.getMessage());
    }
  }

  public void run() {

  }
}
