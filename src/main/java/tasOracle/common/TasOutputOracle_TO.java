package tasOracle.common;

import ispn_53.common.ISPN_53_D_GMU_Result;
import tasOracle.common.TasOutputOracle;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class TasOutputOracle_TO extends TasOutputOracle {

   private double thR, thW, rR, rW, aR, aW;

   public TasOutputOracle_TO(ISPN_53_D_GMU_Result result) {
      super(result);
   }

   public TasOutputOracle_TO(ISPN_53_D_GMU_Result result,double thR, double thW, double rR, double rW, double aR, double aW) {
      super(result);
      this.thR = thR;
      this.thW = thW;
      this.rR = rR;
      this.rW = rW;
      this.aR = aR;
      this.aW = aW;
   }

   @Override
   public double throughput(int txClassId) {
      if (txClassId == 0)
         return thR;
      return thW;
   }

   @Override
   public double abortRate(int txClassId) {
      if (txClassId == 0)
         return aR;
      return aW;
   }

   @Override
   public double responseTime(int i) {
      if (i == 0)
         return rR;
      return rW;
   }
}
