package tasOracle;/*
 * INESC-ID, Instituto de Engenharia de Sistemas e Computadores Investigação e Desevolvimento em Lisboa
 * Copyright 2013 INESC-ID and/or its affiliates and other
 * contributors as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a full listing of
 * individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3.0 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

import eu.cloudtm.commons.EvaluatedParam;
import eu.cloudtm.commons.Param;
import ispn_53.input.ISPN_52_TPC_GMU_Workload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import tem.InputOracle;

/**
 * @author Diego Didona, didona@gsd.inesc-id.pt
 *         Date: 25/08/13
 */
public abstract class CommonTasOracle implements TasOracle_I {

   protected Log log = LogFactory.getLog(CommonTasOracle.class);

   protected double getDoubleParam(InputOracle input, Param param) {
      return (Double) input.getParam(param);
   }

   protected ISPN_52_TPC_GMU_Workload buildWorkload(InputOracle inputOracle) {
      ispn_53.input.ISPN_52_TPC_GMU_Workload workload = new ISPN_52_TPC_GMU_Workload();
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
      workload.setUnconditionalLocalCommitQueueWaitingTime(localCommitW);
      workload.setUnconditionalRemoteCommitQueueWaitingTime(remoteCommitW);
      workload.setUnconditionalRemoteGetWaitingTime(0);
      System.out.println(workload);
      return workload;
   }

}
