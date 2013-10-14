package tasOracle.rr;

import eu.cloudtm.autonomicManager.oracles.InputOracle;
import eu.cloudtm.autonomicManager.oracles.OutputOracle;
import eu.cloudtm.autonomicManager.oracles.exceptions.OracleException;
import tasOracle.common.TasOutputOracle;
import tasOracle.common.TasOutputOracle_TO;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class TasOracle_RR_TO_QUEUE_CPU_CUBIST_NET_MULE extends TasOracle_RR_TPC_QUEUE_CPU_CUBIST_NET_MULE {

   private final static double DISCOUNT = .1D;
   private final static double AB = .05D;

   @Override
   public OutputOracle forecast(InputOracle input) throws OracleException {
      TasOutputOracle out = (TasOutputOracle) super.forecast(input);
      double thR = out.throughput(0) * (1.0D - DISCOUNT);
      double thW = out.throughput(1) * (1.0D - DISCOUNT);
      double rR = out.responseTime(0) * (1.0D + DISCOUNT);
      double rW = out.responseTime(1) * (1.0D + DISCOUNT);
      double aW = out.abortRate(1) * (1.0D - AB);

      return new TasOutputOracle_TO(null, thR, thW, rR, rW, 0, aW);
   }
}
