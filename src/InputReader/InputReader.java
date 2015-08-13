package InputReader;

import java.util.ArrayList;
import java.util.logging.Logger;


/**
 * Created by Xian Li on 8/12/15.
 */
public abstract class InputReader {
  String inputFilePath;
  public final static Logger LOGGER = Logger.getLogger(InputReader.class.getName());

  public InputReader(String input) {
    inputFilePath = input;
  }

  public abstract ArrayList<Seed> generateSeeds();
}
