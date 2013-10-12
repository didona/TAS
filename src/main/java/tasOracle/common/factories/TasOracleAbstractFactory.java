package tasOracle.common.factories;

import eu.cloudtm.autonomicManager.commons.ForecastParam;
import eu.cloudtm.autonomicManager.commons.ReplicationProtocol;
import eu.cloudtm.autonomicManager.oracles.InputOracle;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public abstract class TasOracleAbstractFactory implements TasOracleFactory {

   protected ReplicationProtocol rp(InputOracle input) {
      return (ReplicationProtocol) input.getForecastParam(ForecastParam.ReplicationProtocol);
   }
}
