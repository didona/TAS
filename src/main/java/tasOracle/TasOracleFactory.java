package tasOracle;


import eu.cloudtm.autonomicManager.commons.ForecastParam;
import eu.cloudtm.autonomicManager.oracles.InputOracle;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class TasOracleFactory {

   public TasOracle_I buildTasOracle(InputOracle input){
      switch (rp(input)) {
         case TPC:
            return new TasOracle_GMU_TPC_QUEUE_CPU_CUBIST_NET_MULE();
         case PB:
            return new TasOracle_GMU_PB_QUEUE_CPU_CUBIST_NET_MULE();
         case TO:
            return new TasOracle_GMU_TO_QUEUE_CPU_CUBIST_NET_MULE();
         default:
            throw new IllegalArgumentException("Unknwon replication protocol");
      }

   }

   private RP rp(InputOracle input){
      return RP.valueOf(input.getForecastParam(ForecastParam.ReplicationDegree).toString());
   }

   private enum RP{
      TPC,TO,PB
   }

}
