package tasOracle;


import ispn_53.common.ISPN_53_D_GMU_ResultImpl;
import tem.OutputOracle;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class TasOutputOracle implements OutputOracle {

   ISPN_53_D_GMU_ResultImpl result;

   public TasOutputOracle(ISPN_53_D_GMU_ResultImpl result) {
      this.result = result;
   }


   @Override
   public double throughput(int txClassId) {
      if (txClassId == TasOracleI.AVG_WR)
         return result.wrThroughput();
      else if (txClassId == TasOracleI.AVG_RO)
         return result.roThroughput();
      throw new IllegalArgumentException("Response time for tx class id " + txClassId + " is not available");
   }

   @Override
   public double abortRate(int txClassId) {
      if (txClassId == TasOracleI.AVG_WR)
         return 1.0D - result.writeCommitProbability();
      else if (txClassId == TasOracleI.AVG_RO)
         return 0;
      throw new IllegalArgumentException("Response time for tx class id " + txClassId + " is not available");
   }

   @Override
   public double responseTime(int i) {
      if (i == TasOracleI.AVG_WR)
         return result.updateXactR();
      else if (i == TasOracleI.AVG_RO)
         return result.readOnlyXactR();
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
