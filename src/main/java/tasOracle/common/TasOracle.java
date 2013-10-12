package tasOracle.common;


import eu.cloudtm.autonomicManager.oracles.InputOracle;
import eu.cloudtm.autonomicManager.oracles.OutputOracle;
import eu.cloudtm.autonomicManager.oracles.exceptions.OracleException;
import tasOracle.common.factories.TasMetaFactory;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class TasOracle implements TasOracle_I {


   @Override
   public OutputOracle forecast(InputOracle input) throws OracleException {
      TasOracle_I tas = new TasMetaFactory().buildTasOracle(input);
      return tas.forecast(input);
   }
}
