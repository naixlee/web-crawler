package PageParser;

import java.util.ArrayList;
import java.util.logging.Logger;


/**
 * Parser.Parser takes an input HTML page and extract nodes as request.
 * Created by Xian Li on 8/12/15.
 */
public abstract class Parser implements Runnable {
  public static final Logger LOGGER = Logger.getLogger(Parser.class.getName());

  protected ArrayList<String> _filePathToProcess;
  protected String _outputHome;

  public Parser(ArrayList<String> filePaths, String outputPath) {
    _filePathToProcess = filePaths;
    _outputHome = outputPath;
  }

  public abstract void run();

  public abstract void extract();
}
