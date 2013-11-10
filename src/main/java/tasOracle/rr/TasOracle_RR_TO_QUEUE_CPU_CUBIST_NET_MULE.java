package tasOracle.rr;

import eu.cloudtm.autonomicManager.oracles.InputOracle;
import eu.cloudtm.autonomicManager.oracles.OutputOracle;
import eu.cloudtm.autonomicManager.oracles.exceptions.OracleException;
import ispn_53.gmu.to.core.Tas_QCCN_GMU_TO;
import ispn_53.gmu.to.input.cpuNet.ServiceTimes_CpuNet_QueueCubist_GMU_TO;
import ispn_53.gmu.to.result.Result_GMU_TO;
import ispn_53.input.ISPN_52_TPC_GMU_Workload;
import ispn_53.input.physical.GmuCpuServiceTimesImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tasOracle.common.TasOutputOracle;
import tasOracle.gmu.TasOracle_GMU_TO_QUEUE_CPU_CUBIST_NET_MULE;

import java.util.Arrays;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class TasOracle_RR_TO_QUEUE_CPU_CUBIST_NET_MULE extends TasOracle_GMU_TO_QUEUE_CPU_CUBIST_NET_MULE {

   private static final double fitRemoteServiceTime = 600D;
   private final static Log log = LogFactory.getLog(TasOracle_RR_TO_QUEUE_CPU_CUBIST_NET_MULE.class);

   @Override
   public OutputOracle forecast(InputOracle input) throws OracleException {
      try {
         ServiceTimes_CpuNet_QueueCubist_GMU_TO serviceTimes = buildServiceTimes(input);
         postServiceTimes(serviceTimes);
         log.trace("ServiceTimes created " + serviceTimes);
         Tas_QCCN_GMU_TO tas = new Tas_QCCN_GMU_TO();
         ISPN_52_TPC_GMU_Workload workload = buildWorkload(input);
         postWorkload(workload);
         log.trace("Workload created " + workload);
         Result_GMU_TO result;

         result = tas.solve(workload, serviceTimes);
         log.trace("RESULT " + result);
         return new TasOutputOracle(result);
      } catch (Exception e) {
         log.error(e);
         log.error(Arrays.toString(e.getStackTrace()));
         throw new OracleException(e.getMessage());
      }
   }

   private void postWorkload(ISPN_52_TPC_GMU_Workload workload) {
      workload.setRR(true);
   }

   private void postServiceTimes(ServiceTimes_CpuNet_QueueCubist_GMU_TO st) {
      postCpuServiceTimes(st.getCpuServiceTimes());
   }

   private void postCpuServiceTimes(GmuCpuServiceTimesImpl st) {
      double old = st.getUpdateTxRemoteExecutionS();
      st.setUpdateTxRemoteExecutionS(old + fitRemoteServiceTime);
   }

}
