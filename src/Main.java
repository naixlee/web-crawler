import InputReader.InputReader;
import InputReader.SECInputReader;
import InputReader.Seed;
import java.util.ArrayList;


public class Main {
  public static void main(String[] args) {
    if (args.length < 2) {
      printUsage();
      System.exit(-1);
    }
    InputReader reader = new SECInputReader(args[0]);
    ArrayList<Seed> seedsList = reader.generateSeeds();
    for (Seed s : seedsList) {
      System.out.println(s.getSeedURL());
    }
  }

  public static void printUsage() {
    System.out.println("Main urlInputFile outputFolder");
  }
}
