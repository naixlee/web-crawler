import PageParser.SECParser;
import java.io.File;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;


/**
 * Created by xli1 on 8/24/15.
 */
public class TestSECParser {
  @Test
  public void testExtractPage() {
    String pagePath = "src/test/resources/sec_pages/3081/2011-4.html";
    String rootPath = "src/test/resources/sec_pages";
    String outputPath = "src/test/resources/sec_results";
    SECParser parser = new SECParser(rootPath, outputPath);
    parser.extractPage(pagePath, rootPath, outputPath);
    assertTrue(new File(outputPath + "/3081/2011-4.txt").exists());
  }
}
