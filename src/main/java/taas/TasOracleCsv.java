package taas;

import eu.cloudtm.autonomicManager.commons.ForecastParam;
import eu.cloudtm.autonomicManager.commons.Param;
import eu.cloudtm.autonomicManager.commons.ReplicationProtocol;
import eu.cloudtm.autonomicManager.oracles.InputOracle;
import eu.cloudtm.autonomicManager.oracles.InputOracleWPM;
import eu.cloudtm.autonomicManager.oracles.Oracle;
import eu.cloudtm.autonomicManager.oracles.OutputOracle;
import eu.cloudtm.autonomicManager.oracles.exceptions.OracleException;
import parse.radargun.Ispn5_2CsvParser;

import java.io.File;
import java.io.IOException;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
/*
A TasOracleCsv takes in input from the InputOracle only the forecast param and other params  needed to detect a workload
(e.g., acf, writes), taking other parameters from the csv files
 */


@Deprecated
public class TasOracleCsv implements Oracle {

   private IOHelper helper = new IOHelper();

   private String repository;

   @Override
   public OutputOracle forecast(InputOracle input) throws OracleException {
      return null;  // TODO: Customise this generated block
   }


   private WorkloadID extractWlFromInput(InputOracle o) {
      double rd = helper.toDouble(o, ForecastParam.ReplicationDegree);
      ReplicationProtocol rp = (ReplicationProtocol) o.getForecastParam(ForecastParam.ReplicationProtocol);
      double nodes = helper.toDouble(o, ForecastParam.NumNodes);
      double acf = helper.toDouble(o, Param.ApplicationContentionFactor);
      return null;
   }

   private void completeInputOracle(InputOracle inputOracle, WorkloadID id) {
      if (inputOracle instanceof InputOracleWPM)
         completeInputOracleWPM((InputOracleWPM) inputOracle, id);
      else
         throw new IllegalArgumentException("Input oracle is not WPM but of class " + inputOracle.getClass());
   }

   private void completeInputOracleWPM(InputOracleWPM inputOracleWPM, WorkloadID id) {
      Ispn5_2CsvParser parser = parserFromID(id);
      if (parser == null)
         return;
   }

   private Ispn5_2CsvParser parserFromID(WorkloadID id) {
      try {
         File dir = new File(this.repository);
         Ispn5_2CsvParser parser;
         WorkloadID temp;
         for (File f : dir.listFiles()) {
            if (isCsv(f)) {
               parser = new Ispn5_2CsvParser(f.getAbsolutePath());
               temp = new WorkloadID(parser);
               if (temp.equals(id))
                  return parser;
            }
         }
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
      System.out.println("I could not find a csv relevant to workloadId " + id);
      return null;

   }

   private boolean isCsv(File f) {
      try {
         return f.getCanonicalPath().endsWith("csv");
      } catch (IOException e) {
         e.printStackTrace();
         throw new RuntimeException("Problems in using  " + f.getAbsolutePath());
      }
   }


}
