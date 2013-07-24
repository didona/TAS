package tasOracle;

import common.exceptions.TasException;
import eu.cloudtm.commons.EvaluatedParam;
import eu.cloudtm.commons.Param;
import eu.cloudtm.oracles.InputOracle;
import eu.cloudtm.oracles.OutputOracle;
import eu.cloudtm.oracles.exceptions.OracleException;
import ispn_53.QueueCpuCubistNetGmuTas;
import ispn_53.input.ISPN_52_TPC_GMU_Workload;
import ispn_53.input.physical.GmuCpuServiceTimesImpl;
import ispn_53.input.physical.GmuCubistNetTimes;
import ispn_53.input.physical.GmuQueueCpuCubistNetServiceTimes;
import ispn_53.output.ISPN_53_D_TPC_GMU_Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class TasOracle_GMU_TPC_QUEUE_CPU_CUBIST_NET_MULE implements TasOracle {

   private static final Log log = LogFactory.getLog(TasOracle_GMU_TPC_QUEUE_CPU_CUBIST_NET_MULE.class);


   @Override
   public OutputOracle forecast(InputOracle input) throws OracleException {
      GmuQueueCpuCubistNetServiceTimes serviceTimes = buildServiceTimes(input);
      QueueCpuCubistNetGmuTas tas = new QueueCpuCubistNetGmuTas();
      ISPN_52_TPC_GMU_Workload workload = buildWorkload(input);
      ISPN_53_D_TPC_GMU_Result result;
      try {
         result = tas.solve(workload, serviceTimes);
         return responseFromTas(result);
      } catch (TasException e) {
         return this.defaultResponse();
      }
   }

   protected GmuCpuServiceTimesImpl buildCpuS(InputOracle input) {
      double updateTxBusinessLogicS = businessLogicWrXactS(input);
      double updateTxPrepareS = getDoubleParam(input, Param.LocalUpdateTxPrepareServiceTime);
      double updateTxCommitS = getDoubleParam(input, Param.LocalUpdateTxCommitServiceTime);
      double updateTxLocalLocalRollbackS = getDoubleParam(input, Param.LocalUpdateTxLocalRollbackServiceTime);
      double updateTxLocalRemoteRollbackS = getDoubleParam(input, Param.LocalUpdateTxRemoteRollbackServiceTime);

      double localRemoteGetS = getDoubleParam(input, Param.RemoteGetServiceTime);
      double localGetS = 0;
      double remoteRemoteGetS = getDoubleParam(input, Param.GMUClusteredGetCommandServiceTime);
      double putS = 0;

      double updateTxRemoteExecutionS = getDoubleParam(input, Param.RemoteUpdateTxPrepareServiceTime);
      double updateTxRemoteCommitS = getDoubleParam(input, Param.RemoteUpdateTxCommitServiceTime);
      double updateTxRemoteRollbackS = getDoubleParam(input, Param.RemoteUpdateTxRollbackServiceTime);

      double readOnlyTxBusinessLogicS = this.businessLogicRoXactS(input);
      double readOnlyTxPrepareS = 0;
      double readOnlyTxCommitS = 0;
      GmuCpuServiceTimesImpl cpu = new GmuCpuServiceTimesImpl();

      cpu.setUpdateTxBusinessLogicS(updateTxBusinessLogicS);
      cpu.setUpdateTxPrepareS(updateTxPrepareS);
      cpu.setUpdateTxCommitS(updateTxCommitS);
      cpu.setUpdateTxLocalLocalRollbackS(updateTxLocalLocalRollbackS);
      cpu.setUpdateTxLocalRemoteRollbackS(updateTxLocalRemoteRollbackS);
      cpu.setLocalRemoteGetS(localRemoteGetS);
      cpu.setLocalGetS(localGetS);
      cpu.setRemoteRemoteGetS(remoteRemoteGetS);
      cpu.setPutS(putS);
      cpu.setUpdateTxRemoteExecutionS(updateTxRemoteExecutionS);
      cpu.setUpdateTxRemoteCommitS(updateTxRemoteCommitS);
      cpu.setUpdateTxRemoteRollbackS(updateTxRemoteRollbackS);
      cpu.setReadOnlyTxBusinessLogicS(readOnlyTxBusinessLogicS);
      cpu.setReadOnlyTxPrepareS(readOnlyTxPrepareS);
      cpu.setReadOnlyTxCommitS(readOnlyTxCommitS);
      if (log.isTraceEnabled())
         log.trace(cpu.toString());
      return cpu;
   }

   private double businessLogicWrXactS(InputOracle inputOracle) {
      return getDoubleParam(inputOracle, Param.LocalUpdateTxLocalServiceTime);
   }

   private double businessLogicRoXactS(InputOracle inputOracle) {
      return getDoubleParam(inputOracle, Param.ReadOnlyTxTotalCpuTime);
   }

   private double getDoubleParam(InputOracle input, Param param) {
      return (Double) input.getParam(param);
   }

   protected ISPN_52_TPC_GMU_Workload buildWorkload(InputOracle inputOracle) {
      ISPN_52_TPC_GMU_Workload workload = new ISPN_52_TPC_GMU_Workload();
      //Common
      double numNodes = this.getDoubleParam(inputOracle, Param.NumNodes);
      double writePercentage = this.getDoubleParam(inputOracle, Param.PercentageSuccessWriteTransactions);
      double wrPerXact = this.getDoubleParam(inputOracle, Param.AvgPutsPerWrTransaction);
      double threadsPerNode = (Double) inputOracle.getEvaluatedParam(EvaluatedParam.MAX_ACTIVE_THREADS);
      double applicationContentionFactor = (Double) inputOracle.getEvaluatedParam(EvaluatedParam.ACF);
      double prepareMessageSize = this.getDoubleParam(inputOracle, Param.AvgPrepareCommandSize);
      double mem = this.getDoubleParam(inputOracle, Param.MemoryInfo_used) * 1e-6;
      double numCores = (Double) inputOracle.getEvaluatedParam(EvaluatedParam.CORE_PER_CPU);
      double lambda = 0;

      //Gmu-specific
      int firstWrite = 1;//(int)this.getDoubleParam(inputOracle,Param. NumReadsBeforeWrite);
      double localAccessProbability = 1.0D / numNodes;
      double replicationDegree = this.getDoubleParam(inputOracle, Param.ReplicationDegree);
      double readsPerROXact = this.getDoubleParam(inputOracle, Param.AvgGetsPerROTransaction);
      double readsPerWrXact = this.getDoubleParam(inputOracle, Param.AvgGetsPerWrTransaction);
      double primaryOwnerProb = 1.0D / numNodes;
      double localCommitW = 0;//this.getDoubleParam(inputOracle,Param.WaitedTimeInLocalCommitQueue);
      double remoteCommitW = 0;//this.getDoubleParam(inputOracle,Param.WaitedTimeInRemoteCommitQueue);

      workload.setNumNodes(numNodes);
      workload.setWritePercentage(writePercentage);
      workload.setWriteOpsPerTx(wrPerXact);
      workload.setThreadsPerNode(threadsPerNode);
      workload.setApplicationContentionFactor(applicationContentionFactor);
      workload.setPrepareMessageSize(prepareMessageSize);
      workload.setMem(mem);
      workload.setNumCores(numCores);
      workload.setLambda(lambda);

      workload.setFirstWriteIndex(firstWrite);
      workload.setLocalAccessProbability(localAccessProbability);
      workload.setReplicationDegree(replicationDegree);
      workload.setReadsPerROXact(readsPerROXact);
      workload.setReadsPerWrXact(readsPerWrXact);
      workload.setLocalPrimaryOwnerProbability(primaryOwnerProb);
      workload.setLocalCommitQueueWaitingTime(localCommitW);
      workload.setRemoteCommitQueueWaitingTime(remoteCommitW);
      System.out.println(workload);
      return workload;
   }

   private GmuQueueCpuCubistNetServiceTimes buildServiceTimes(InputOracle input) {
      GmuCpuServiceTimesImpl cpuS = buildCpuS(input);
      GmuCubistNetTimes netS = buildNetS(input);
      return new GmuQueueCpuCubistNetServiceTimes(cpuS, netS);
   }

   private GmuCubistNetTimes buildNetS(InputOracle input) {
      GmuCubistNetTimes net = new GmuCubistNetTimes();
      return net;
   }

   private OutputOracle defaultResponse() {

      return new TasOutputOracle(0, 0, 0);
   }

   private OutputOracle responseFromTas(ISPN_53_D_TPC_GMU_Result result) {
      double throughput = result.throughput() * 1e6;
      double responseTimeW = result.updateXactR();
      double responseTimeR = result.readOnlyXactR();
      double abortProb = 1.0D - result.getProbabilities().commitProbability();
      return new TasOutputOracle(throughput, abortProb, responseTimeW, responseTimeR);
   }

}
