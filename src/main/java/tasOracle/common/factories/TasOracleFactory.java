package tasOracle.common.factories;

import eu.cloudtm.autonomicManager.oracles.InputOracle;
import tasOracle.common.TasOracle_I;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public interface TasOracleFactory {

   public TasOracle_I buildTasOracle(InputOracle i);
}
