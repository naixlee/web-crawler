package Crawler;

import InputReader.SECSeed;
import InputReader.Seed;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



/**
 * Created by Xian Li on 8/12/15.
 */
public class SECCrawler extends Crawler implements Runnable {
  public SECCrawler(ArrayList<Seed> seeds, String output, String[] rules) {
    super(seeds, output, rules);
  }

  public void run() {
    getPagesWithDepth(initialSeeds);
  }

  /**
   * Download a web page via a set of hyperlink exploration. The rules for exploration is given as Xpath rules.
   * @param seed
   * @param xpathRules
   */
  public void getPageWithDepth(Seed seed, String[] xpathRules) {
    if (seed == null || seed.getSeedURL() == null || seed.getSeedURL().isEmpty()) {
      LOGGER.info("Missing valid URL information");
      return;
    }
    if (!(seed instanceof SECSeed)) {
      LOGGER.info("Wrong type of seeds");
    }
    ArrayList<String> urlInProcess = new ArrayList<String>();
    ArrayList<String> urlNext = new ArrayList<String>();
    urlInProcess.add(seed.getSeedURL());

    try {
      for (String rule : xpathRules) {
        String[] parsedRule = rule.split("\t");

        for (String url : urlInProcess) {
          Document pageRoot = fetchPage(url);
          if (pageRoot != null) {
            Elements elements = pageRoot.select(parsedRule[0]);
            for (Element e : elements) {
              if (parsedRule.length > 1) {
                urlNext.add(url + '/' + e.attr(parsedRule[1]));
              }
            }
          }
        }
        urlInProcess.clear();
        urlInProcess.addAll(urlNext);
        urlNext.clear();
      }

      for (String url : urlInProcess) {
        SECSeed s = (SECSeed) seed;
        System.out.println("Crawler: " +Thread.currentThread().getId() + "\t" + url + "\t" + s.getLiCompanyID() + "\t" + s.getFilingYear() + "\t" + s.getFilingQuarter());
        File folder = new File(outputFolder + '/' + s.getLiCompanyID());
        if (!folder.exists()) {
          folder.mkdir();
        }

        Document pageRoot = fetchPage(url);
        if (pageRoot != null) {
          BufferedWriter writer = new BufferedWriter(new FileWriter(
              folder.getPath() + '/' + s.getFilingYear() + "-" + s.getFilingQuarter() + ".html"));

          writer.write(pageRoot.html());
          writer.close();
        }
      }
    } catch (IOException exception) {
      LOGGER.info(exception.getMessage());
    }
  }

  public void getPagesWithDepth(ArrayList<Seed> seeds) {
    for (Seed s : seeds) {
      getPageWithDepth(s, extractRules);
    }
  }
}
