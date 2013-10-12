package taas;

import eu.cloudtm.autonomicManager.commons.EvaluatedParam;
import eu.cloudtm.autonomicManager.commons.ForecastParam;
import eu.cloudtm.autonomicManager.commons.Param;
import eu.cloudtm.autonomicManager.oracles.InputOracle;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class IOHelper {

   public double toDouble(InputOracle inputOracle, Param param) {
      return ((Number) inputOracle.getParam(param)).doubleValue();
   }

   public double toDouble(InputOracle inputOracle, EvaluatedParam param) {
      return ((Number) inputOracle.getEvaluatedParam(param)).doubleValue();
   }

   public double toDouble(InputOracle inputOracle, ForecastParam param) {
      return ((Number) inputOracle.getForecastParam(param)).doubleValue();
   }
}
