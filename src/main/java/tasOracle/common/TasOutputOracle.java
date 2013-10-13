package tasOracle.common;


import eu.cloudtm.autonomicManager.oracles.OutputOracle;
import ispn_53.common.ISPN_53_D_GMU_Result;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class TasOutputOracle implements OutputOracle {

   private static final double micToSec = 1e6;
   private static final double micToMSec = 1e-3;
   ISPN_53_D_GMU_Result result;

   public TasOutputOracle(ISPN_53_D_GMU_Result result) {
      this.result = result;
   }

   @Override
   public double throughput(int txClassId) {
      if (txClassId == TasOracle_I.AVG_WR)
         return result.wrThroughput() * micToSec;
      else if (txClassId == TasOracle_I.AVG_RO)
         return result.roThroughput() * micToSec;
      throw new IllegalArgumentException("Response time for tx class id " + txClassId + " is not available");
   }

   @Override
   public double abortRate(int txClassId) {
      if (txClassId == TasOracle_I.AVG_WR)
         return 1.0D - result.writeCommitProbability();
      else if (txClassId == TasOracle_I.AVG_RO)
         return 0;
      throw new IllegalArgumentException("Response time for tx class id " + txClassId + " is not available");
   }

   @Override
   public double responseTime(int i) {
      if (i == TasOracle_I.AVG_WR)
         return result.updateXactR() * micToMSec;
      else if (i == TasOracle_I.AVG_RO)
         return result.readOnlyXactR() * micToMSec;
      throw new IllegalArgumentException("Response time for tx class id " + i + " is not available");
   }

   @Override
   public double getConfidenceThroughput(int txClassId) {
      return 1; //TODO
   }

   @Override
   public double getConfidenceAbortRate(int txClassId) {
      return 1; //TODO
   }

   @Override
   public double getConfidenceResponseTime(int txClassId) {
      return 1; //TODO
   }

}
