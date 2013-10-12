package tasOracle.common.factories;

import eu.cloudtm.autonomicManager.commons.EvaluatedParam;
import eu.cloudtm.autonomicManager.commons.IsolationLevel;
import eu.cloudtm.autonomicManager.oracles.InputOracle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tasOracle.common.TasOracle_I;
import tasOracle.gmu.TasOracleFactory_GMU;
import tasOracle.rr.TasOracleFactory_RR;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class TasMetaFactory implements TasOracleFactory{

   private final static TasOracleFactory_GMU gmuF = new TasOracleFactory_GMU();
   private final static TasOracleFactory_RR rrF = new TasOracleFactory_RR();
   private final static Log log = LogFactory.getLog(TasMetaFactory.class);

   public TasOracle_I buildTasOracle(InputOracle inputOracle) {
      IsolationLevel i = (IsolationLevel) inputOracle.getEvaluatedParam(EvaluatedParam.ISOLATION_LEVEL);
      switch (i) {
         case GMU: {
            return gmuF.buildTasOracle(inputOracle);
         }
         case RR: {
            return rrF.buildTasOracle(inputOracle);
         }
         default: {
            log.error("Isolation level " + i + " is unknown");
            throw new IllegalArgumentException("Isolation level " + i + " is unknown");
         }
      }
   }
}
