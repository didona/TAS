package tasOracle;


import eu.cloudtm.oracles.InputOracle;
import eu.cloudtm.oracles.Oracle;
import eu.cloudtm.oracles.OutputOracle;
import eu.cloudtm.oracles.exceptions.OracleException;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class TasOracleImpl implements Oracle {

   @Override
   public OutputOracle forecast(InputOracle inputOracle) throws OracleException {
      TasOracle tas = TasOracleFactory.buildTasOracle(inputOracle);
      return tas.forecast(inputOracle);
   }
}
