package Crawler;

import InputReader.Seed;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


/**
 *
 * Created by Xian Li on 8/12/15.
 */

public abstract class Crawler {
  ArrayList<Seed> initialSeeds;
  String outputFolder;
  WebDriver webDriver;
  static final Logger LOGGER = Logger.getLogger(Crawler.class.getName());

  public Crawler(ArrayList<Seed> seeds, String output) {
    initialSeeds = seeds;
    outputFolder = output;
    webDriver = new HtmlUnitDriver();
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
    if (seed == null || seed.getSeedURL() == null) return;
    String targetURL = seed.getSeedURL();
    if (outputFolder == null || outputFolder.isEmpty()) return;
    File outputDirectory = new File(outputFolder);
    if (!outputDirectory.exists()) {
      outputDirectory.mkdir();
    }
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(outputFolder + '/' + fileName));
      writer.write(webDriver.getPageSource());
      writer.close();
    } catch (IOException exception) {
      LOGGER.info(exception.getMessage());
    }
  }
  public abstract void getPageWithDepth();
}
