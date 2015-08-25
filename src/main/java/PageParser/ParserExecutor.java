package PageParser;

import java.util.logging.Logger;


/**
 * Created by xli1 on 8/17/15.
 */
public class ParserExecutor {
  public static final Logger LOGGER = Logger.getLogger(ParserExecutor.class.getName());
  public static void main(String[] args) {
    if (args.length < 2) {
      System.out.println("java ParserExecutor inputHome outputHome");
      return;
    }
    String inputHome = args[0];
    String outputHome = args[1];

    SECParser sparser = new SECParser(inputHome, outputHome);
    sparser.extract(inputHome, inputHome, outputHome);
  }
}
