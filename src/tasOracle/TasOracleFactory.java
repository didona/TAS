package tasOracle;

import eu.cloudtm.oracles.InputOracle;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class TasOracleFactory {

   public static TasOracle buildTasOracle(InputOracle input){
      return new TasOracle_GMU_TPC_QUEUE_CPU_CUBIST_NET_MULE();
   }
}
