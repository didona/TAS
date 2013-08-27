import eu.cloudtm.autonomicManager.oracles.exceptions.OracleException;
import tasOracle.TasOracle;

import java.io.File;

/**
 * @author Diego Didona, didona@gsd.inesc-id.pt
 *         Date: 27/08/13
 */
public class TestTas {

   private static final String testPath = "data/tasTest/";

   public static void main(String[] args) {
      File dir = new File(testPath);
      for (File dir_2 : dir.listFiles()) {
         for (File csv : dir_2.listFiles()) {
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
