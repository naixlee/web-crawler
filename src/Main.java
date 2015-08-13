import InputReader.InputReader;
import InputReader.SECInputReader;
import InputReader.Seed;
import Crawler.Crawler;
import Crawler.SECCrawler;
import java.util.ArrayList;


public class Main {
  public static void main(String[] args) {
    if (args.length < 2) {
      printUsage();
      System.exit(-1);
    }
    InputReader reader = new SECInputReader(args[0]);
    ArrayList<Seed> seedsList = reader.generateSeeds();

    Crawler crawler = new SECCrawler(seedsList, args[1]);
    String[] dummy = {"html > body > table > tbody > tr > td > a[href]:matches((.+)ex21(.+))\thref"};
    crawler.getPagesWithDepth(seedsList, dummy);
  }

  public static void printUsage() {
    System.out.println("Main urlInputFile outputFolder");
  }
}
