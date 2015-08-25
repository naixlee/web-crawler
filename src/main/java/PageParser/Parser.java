package PageParser;

import java.util.ArrayList;
import java.util.logging.Logger;


/**
 * Parser.Parser takes an input HTML page and extract nodes as request.
 * Created by Xian Li on 8/12/15.
 */
public abstract class Parser implements Runnable {
  public static final Logger LOGGER = Logger.getLogger(Parser.class.getName());

  /**
   * List directories containing web pages to be processed
   */
  protected String _inputHome;
  /**
   * Root directory of extracted information
   */
  protected String _outputHome;

  public Parser(String rootPath, String outputPath) {
    _inputHome = rootPath;
    _outputHome = outputPath;
  }

  public abstract void run();

  public abstract void extract();
}
