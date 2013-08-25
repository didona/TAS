package tasOracle;


/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class TasOracle implements TasOracleI {



   @Override
   public tem.OutputOracle forecast(tem.InputOracle input) throws tem.OracleException {
      TasOracleI tas = new TasOracleFactory().buildTasOracle(input);
      return tas.forecast(input);
   }
}
