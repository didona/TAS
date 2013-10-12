package tasOracle.rr;

import eu.cloudtm.autonomicManager.oracles.InputOracle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tasOracle.common.TasOracle_I;
import tasOracle.common.factories.TasOracleAbstractFactory;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class TasOracleFactory_RR extends TasOracleAbstractFactory {
   private final static Log log = LogFactory.getLog(TasOracleFactory_RR.class);

   @Override
   public TasOracle_I buildTasOracle(InputOracle input) {
      final boolean t = log.isTraceEnabled();
      switch (rp(input)) {
         case TWOPC: {
            if (t) log.trace("Going to solve for TPC");
            return new TasOracle_RR_TPC_QUEUE_CPU_CUBIST_NET_MULE();
         }
         default:
            throw new IllegalArgumentException("Replication protocol not supported " + rp(input));
      }
   }
}
