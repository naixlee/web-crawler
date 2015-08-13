package Crawler;

import InputReader.Seed;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


/**
 *
 * Created by Xian Li on 8/12/15.
 */

public abstract class Crawler {
  ArrayList<Seed> initialSeeds;
  String outputFolder;
  static final Logger LOGGER = Logger.getLogger(Crawler.class.getName());

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
      BufferedWriter writer = new BufferedWriter(new FileWriter(outputFolder + '/' + fileName));
      Document root = Jsoup.connect(targetURL).get();
      System.out.println(root.toString());
      writer.write(root.html());
      writer.close();
    } catch (IOException exception) {
      LOGGER.info(exception.getMessage());
    }
  }

  public void getDirectPage(Seed seed) {
    getDirectPage(seed, "temp.htm");
  }

  public abstract void getPageWithDepth(Seed seed, String[] xpathRules);
  public abstract void getPagesWithDepth (ArrayList<Seed> seeds, String[] xpathRules);
}
