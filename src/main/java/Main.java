import InputReader.InputReader;
import InputReader.SECInputReader;
import InputReader.Seed;
import Crawler.SECCrawler;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {
  public static void main(String[] args) {
    if (args.length < 3) {
      printUsage();
      System.exit(-1);
    }
    InputReader reader = new SECInputReader(args[0]);
    ArrayList<Seed> seedsList = reader.generateSeeds();
    int totalThreads = Integer.parseInt(args[2]);

    String[] dummy = {"html > body > table > tbody > tr > td > a[href]:matches((.+)ex21(.+))\thref"};
    int numTasksPerThread = seedsList.size()/totalThreads + 1;

    ExecutorService service = Executors.newFixedThreadPool(totalThreads);
    for (int i = 0; i < totalThreads; i++) {
      ArrayList<Seed> subtasks = new ArrayList<Seed>();
      for (int j = 0; j < numTasksPerThread && i * numTasksPerThread + j < seedsList.size(); j++) {
        subtasks.add(seedsList.get(i * numTasksPerThread + j));
      }
      service.submit(new SECCrawler(subtasks, args[1], dummy));
    }
    service.shutdown();
  }

  public static void printUsage() {
    System.out.println("Main urlInputFile outputFolder #threads");
  }
}
