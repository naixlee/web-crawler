package InputReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by xli1 on 8/12/15.
 */
public class SECInputReader extends InputReader {
  public SECInputReader(String inputFilePath) {
    super(inputFilePath);
  }
  public ArrayList<Seed> generateSeeds() {
    if (inputFilePath == null || inputFilePath.isEmpty()) {
      LOGGER.warning("Missing Input InputReader.Seed Files");
    }
    File inputFile = new File(inputFilePath);
    if (!inputFile.exists()) {
      LOGGER.warning("Missing Input InputReader.Seed Files");
    }
    ArrayList<File> inputFiles = new ArrayList<File>();
    if (inputFile.isFile()) inputFiles.add(inputFile);
    else if (inputFile.isDirectory()) inputFiles.addAll(Arrays.asList(inputFile.listFiles()));

    ArrayList<Seed> urlSeeds = new ArrayList<Seed>();
    for (File file : inputFiles) {
      try {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
          String[] values = line.trim().split("\t");
          if (values.length != 6) {
            LOGGER.fine("Invalid input line: " + line);
          } else {
            SECSeed s = new SECSeed(values[5]);
            s.setCik(values[0]);
            s.setLiCompanyID(values[1]);
            s.setCompanyName(values[2]);
            s.setFilingYear(values[3]);
            s.setFilingQuarter(values[4]);
            urlSeeds.add(s);
          }
        }
        reader.close();
      } catch (IOException exception) {
        LOGGER.info("Empty Input InputReader.Seed Files");
      }
    }
    return urlSeeds;
  }
}
