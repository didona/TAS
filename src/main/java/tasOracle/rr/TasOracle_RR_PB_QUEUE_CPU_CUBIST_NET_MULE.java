package tasOracle.rr;

import common.exceptions.TasException;
import eu.cloudtm.autonomicManager.oracles.InputOracle;
import eu.cloudtm.autonomicManager.oracles.OutputOracle;
import eu.cloudtm.autonomicManager.oracles.exceptions.OracleException;
import ispn_53.gmu.pb.core.Tas_QCCN_GMU_PB;
import ispn_53.gmu.pb.input.cpu.ServiceTimes_Cpu_GMU_PB;
import ispn_53.gmu.pb.input.cpuNet.ServiceTimes_CpuNet_QueueCubist_GMU_PB;
import ispn_53.gmu.pb.result.Result_53_PB_GMU;
import ispn_53.input.ISPN_52_TPC_GMU_Workload;
import tasOracle.common.TasOutputOracle;
import tasOracle.gmu.TasOracle_GMU_PB_QUEUE_CPU_CUBIST_NET_MULE;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class TasOracle_RR_PB_QUEUE_CPU_CUBIST_NET_MULE extends TasOracle_GMU_PB_QUEUE_CPU_CUBIST_NET_MULE {
   private static final double fitRemoteServiceTime = 600D;

   @Override
   public OutputOracle forecast(InputOracle input) throws OracleException {
      ServiceTimes_CpuNet_QueueCubist_GMU_PB serviceTimes = buildServiceTimes(input);
      postServiceTimes(serviceTimes);
      Tas_QCCN_GMU_PB tas = new Tas_QCCN_GMU_PB();
      ISPN_52_TPC_GMU_Workload workload = buildWorkload(input);
      postWorkload(workload);
      Result_53_PB_GMU result;
      try {
         result = tas.solve(workload, serviceTimes);
         return new TasOutputOracle(result);
      } catch (TasException e) {
         throw new OracleException(e.getMessage());
      }
   }

   private void postWorkload(ISPN_52_TPC_GMU_Workload workload) {
      workload.setRR(true);
   }

   private void postServiceTimes(ServiceTimes_CpuNet_QueueCubist_GMU_PB st) {
      postCpuServiceTimes(st.getCpuServiceTimes());
   }

   private void postCpuServiceTimes(ServiceTimes_Cpu_GMU_PB st) {
      double old = st.getUpdateTxRemoteExecutionS();
      st.setUpdateTxRemoteExecutionS(old + fitRemoteServiceTime);
   }

}
