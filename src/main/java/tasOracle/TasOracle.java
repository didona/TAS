package tasOracle;


/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class TasOracle implements TasOracle_I {



   @Override
   public tem.OutputOracle forecast(tem.InputOracle input) throws tem.OracleException {
      TasOracle_I tas = new TasOracleFactory().buildTasOracle(input);
      return tas.forecast(input);
   }
}
