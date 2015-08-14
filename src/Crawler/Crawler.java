package Crawler;

import InputReader.Seed;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * Created by Xian Li on 8/12/15.
 */

public abstract class Crawler {

  public static final Logger LOGGER = Logger.getLogger(Crawler.class.getName());
  public static FileHandler ERROR_CASE_HANDLER = null;
  public static int MAX_RETRY = 3;

  protected ArrayList<Seed> initialSeeds;
  protected String outputFolder;

  public Crawler(ArrayList<Seed> seeds, String output) {
    initialSeeds = seeds;
    outputFolder = output;
  }

  public ArrayList<Seed> getInitialSeeds() {
    return initialSeeds;
  }

  public void setInitialSeeds(ArrayList<Seed> initialSeeds) {
    this.initialSeeds = initialSeeds;
  }

  public String getOutputFolder() {
    return outputFolder;
  }

  public void setOutputFolder(String outputFolder) {
    this.outputFolder = outputFolder;
  }

  public static int getMaxRetry() {
    return MAX_RETRY;
  }

  public static void setMaxRetry(int maxRetry) {
    MAX_RETRY = maxRetry;
  }

  public void getDirectPage(Seed seed, String fileName) {
    if (seed == null || seed.getSeedURL() == null || seed.getSeedURL().isEmpty()) {
      LOGGER.info("Missing valid URL information");
    }
    String targetURL = seed.getSeedURL();
    if (outputFolder == null || outputFolder.isEmpty()) return;
    File outputDirectory = new File(outputFolder);
    if (!outputDirectory.exists()) {
      outputDirectory.mkdir();
    }
    try {
      Document root = fetchPage(targetURL);
      if (root != null) {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFolder + '/' + fileName));
        writer.write(root.html());
        writer.close();
      }
    } catch (IOException exception) {
      LOGGER.info(exception.getMessage());
    }
  }

  public void getDirectPage(Seed seed) {
    getDirectPage(seed, "temp.htm");
  }

  public abstract void getPageWithDepth(Seed seed, String[] xpathRules);

  /**
   * Crawl a list of urls in single thread manner.
   * @param seeds
   * @param xpathRules
   */
  public abstract void getPagesWithDepth (ArrayList<Seed> seeds, String[] xpathRules);

  /**
   * Use JSoup to fetch the web page. Allow MAX_RETRY times of retries. Log the url if failed after max number
   * of retries.
   * @param targetURL
   * @return
   */
  protected Document fetchPage(String targetURL) {
    int retry = 0;
    Document root = null;
    while (retry < MAX_RETRY) {
      try {
        root = Jsoup.connect(targetURL).timeout(3000).get();
        break;
      } catch (IOException exception) {
        LOGGER.info("Retry fetching: " + targetURL);
      }
      retry++;
    }
    if (root == null) {
      LOGGER.warning("Fail to download: " + targetURL);
    }
    return root;
  }
}
