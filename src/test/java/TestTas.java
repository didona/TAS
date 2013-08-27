import eu.cloudtm.autonomicManager.oracles.exceptions.OracleException;
import org.apache.log4j.PropertyConfigurator;
import tasOracle.TasOracle;

import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * @author Diego Didona, didona@gsd.inesc-id.pt
 *         Date: 27/08/13
 */
public class TestTas {

   private static final String testPath = "/Users/diego/Software/Validation/data/130724";

   public static void main(String[] args) {
      PropertyConfigurator.configure("conf/log4j.properties");
      File dir = new File(testPath);
      for (File dir_2 : dir.listFiles()) {
         if (dir_2.isDirectory()) {
            for (File csv : dir_2.listFiles()) {
               if (!csv(csv)) {
                  continue;
               }
               CsvInputOracle csvI = new CsvInputOracle(csv);
               TasOracle t = new TasOracle();
               try {
                  t.forecast(csvI);
                  System.out.println(csv.getPath() + " OK");
               } catch (OracleException e) {
                  System.out.println(csv.getPath() + " KO " + e.getMessage());
               }
            }
         }
      }
   }

   private static boolean csv(File f) {
      return f.toString().endsWith("csv");
   }
}
