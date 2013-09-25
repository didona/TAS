package csv;

import eu.cloudtm.autonomicManager.commons.EvaluatedParam;
import static eu.cloudtm.autonomicManager.commons.EvaluatedParam.ACF;
import static eu.cloudtm.autonomicManager.commons.EvaluatedParam.CORE_PER_CPU;
import static eu.cloudtm.autonomicManager.commons.EvaluatedParam.MAX_ACTIVE_THREADS;
import eu.cloudtm.autonomicManager.commons.ForecastParam;
import static eu.cloudtm.autonomicManager.commons.ForecastParam.ReplicationDegree;
import eu.cloudtm.autonomicManager.commons.Param;
import static eu.cloudtm.autonomicManager.commons.Param.AverageWriteTime;
import static eu.cloudtm.autonomicManager.commons.Param.AvgClusteredGetCommandReplySize;
import static eu.cloudtm.autonomicManager.commons.Param.AvgGetsPerROTransaction;
import static eu.cloudtm.autonomicManager.commons.Param.AvgGetsPerWrTransaction;
import static eu.cloudtm.autonomicManager.commons.Param.AvgLocalGetTime;
import static eu.cloudtm.autonomicManager.commons.Param.AvgNTCBTime;
import static eu.cloudtm.autonomicManager.commons.Param.AvgPrepareCommandSize;
import static eu.cloudtm.autonomicManager.commons.Param.AvgPutsPerWrTransaction;
import static eu.cloudtm.autonomicManager.commons.Param.AvgTxArrivalRate;
import static eu.cloudtm.autonomicManager.commons.Param.GMUClusteredGetCommandServiceTime;
import static eu.cloudtm.autonomicManager.commons.Param.LocalUpdateTxCommitServiceTime;
import static eu.cloudtm.autonomicManager.commons.Param.LocalUpdateTxLocalResponseTime;
import static eu.cloudtm.autonomicManager.commons.Param.LocalUpdateTxLocalRollbackServiceTime;
import static eu.cloudtm.autonomicManager.commons.Param.LocalUpdateTxLocalServiceTime;
import static eu.cloudtm.autonomicManager.commons.Param.LocalUpdateTxPrepareResponseTime;
import static eu.cloudtm.autonomicManager.commons.Param.LocalUpdateTxPrepareServiceTime;
import static eu.cloudtm.autonomicManager.commons.Param.LocalUpdateTxRemoteRollbackServiceTime;
import static eu.cloudtm.autonomicManager.commons.Param.MemoryInfo_used;
import static eu.cloudtm.autonomicManager.commons.Param.NumNodes;
import static eu.cloudtm.autonomicManager.commons.Param.NumberOfEntries;
import static eu.cloudtm.autonomicManager.commons.Param.PercentageSuccessWriteTransactions;
import static eu.cloudtm.autonomicManager.commons.Param.PercentageWriteTransactions;
import static eu.cloudtm.autonomicManager.commons.Param.ReadOnlyTxTotalCpuTime;
import static eu.cloudtm.autonomicManager.commons.Param.RemoteGetServiceTime;
import static eu.cloudtm.autonomicManager.commons.Param.RemoteUpdateTxCommitServiceTime;
import static eu.cloudtm.autonomicManager.commons.Param.RemoteUpdateTxPrepareServiceTime;
import static eu.cloudtm.autonomicManager.commons.Param.RemoteUpdateTxRollbackServiceTime;
import eu.cloudtm.autonomicManager.commons.ReplicationProtocol;
import eu.cloudtm.autonomicManager.oracles.InputOracle;
import parse.radargun.Ispn5_2CsvParser;

import java.io.File;
import java.io.IOException;

/**
 * @author Diego Didona, didona@gsd.inesc-id.pt Date: 27/08/13
 */
public class CsvInputOracle implements InputOracle {

   Ispn5_2CsvParser csvParser;

   public CsvInputOracle(Ispn5_2CsvParser csvParser) {
      this.csvParser = csvParser;
   }

   public CsvInputOracle(String path) {
      try {
         this.csvParser = new Ispn5_2CsvParser(path);
      } catch (IOException e) {
         throw new IllegalArgumentException("Path " + path + " is nonexistent");
      }
   }

   public CsvInputOracle(File f) {
      try {
         this.csvParser = new Ispn5_2CsvParser(f.getAbsolutePath());
      } catch (IOException e) {
         throw new IllegalArgumentException("Path " + f.getAbsolutePath() + " is nonexistent");
      }
   }

   @Override
   public Object getParam(Param param) {
      switch (param) {
         case NumNodes:
            return param.castTo(numNodes(),param.getClazz());
         case ReplicationDegree:
            return param.castTo(replicationDegree(),param.getClazz());
         case AvgPutsPerWrTransaction:
            return param.castTo(putsPerWrXact(),param.getClazz());
         case AvgPrepareCommandSize:
            return param.castTo(prepareCommandSize(),param.getClazz());
         case MemoryInfo_used:
            return param.castTo(memory(),param.getClazz());
         case AvgGetsPerROTransaction:
            return param.castTo(getsPerRoXact(),param.getClazz());
         case AvgGetsPerWrTransaction:
            return param.castTo(getsPerWrXact(),param.getClazz());
         case LocalUpdateTxLocalServiceTime:
            return param.castTo(localUpdateTxLocalServiceTime(),param.getClazz());
         case LocalUpdateTxPrepareServiceTime:
            return param.castTo(localUpdateTxPrepareServiceTime(),param.getClazz());
         case LocalUpdateTxCommitServiceTime:
            return param.castTo(localUpdateTxCommitServiceTime(),param.getClazz());
         case LocalUpdateTxLocalRollbackServiceTime:
            return param.castTo(localUpdateTxLocalRollbackServiceTime(),param.getClazz());
         case LocalUpdateTxRemoteRollbackServiceTime:
            return param.castTo(localUpdateTxRemoteRollbackServiceTime(),param.getClazz());
         case RemoteGetServiceTime:
            return param.castTo(remoteGetServiceTime(),param.getClazz());
         case GMUClusteredGetCommandServiceTime:
            return param.castTo(gmuClusterGetCommandServiceTime(),param.getClazz());
         case RemoteUpdateTxPrepareServiceTime:
            return param.castTo(remoteUpdateTxPrepareServiceTime(),param.getClazz());
         case RemoteUpdateTxCommitServiceTime:
            return param.castTo(remoteUpdateTxCommitServiceTime(),param.getClazz());
         case RemoteUpdateTxRollbackServiceTime:
            return param.castTo(remoteUpateTxRollbackServiceTime(),param.getClazz());
         case ReadOnlyTxTotalCpuTime:
            return param.castTo(localReadOnlyTxTotalCpuTime(),param.getClazz());
         case PercentageSuccessWriteTransactions:
            return param.castTo(writePercentage(),param.getClazz());
         // parameter added to make this class DAGS compliant
         case PercentageWriteTransactions:
            return param.castTo(writePercentage(),param.getClazz());
         case AvgLocalGetTime:
            return param.castTo(AvgLocalGetTime(),param.getClazz());
         case LocalUpdateTxPrepareResponseTime:
            return param.castTo(LocalUpdateTxPrepareResponseTime(),param.getClazz());
         case LocalUpdateTxLocalResponseTime:
            return param.castTo(LocalUpdateTxLocalResponseTime(),param.getClazz());
         case AverageWriteTime:
            return param.castTo(AverageWriteTime(),param.getClazz());
         //these are not present in csvfile
         case AvgTxArrivalRate:
            return param.castTo(AvgTxArrivalRate(),param.getClazz());
         case AvgNTCBTime:
            return param.castTo(AvgNTCBTime(),param.getClazz());
         case NumberOfEntries:
            return param.castTo(numberOfEntries(),param.getClazz());
         case AvgClusteredGetCommandReplySize:
             return param.castTo(AvgClusteredGetCommandReplySize(),param.getClazz());
         default:
            throw new IllegalArgumentException("Param " + param + " is not present");
      }

   }

   @Override
   public Object getEvaluatedParam(EvaluatedParam evaluatedParam) {
      switch (evaluatedParam) {
         case  MAX_ACTIVE_THREADS:
            return numThreadsPerNode();
         case ACF:
            return acf();
         case CORE_PER_CPU:
            return cpus();
         default:
            throw new IllegalArgumentException("Param " + evaluatedParam + " is not present");
      }
   }

   @Override
   public Object getForecastParam(ForecastParam forecastParam) {
      switch (forecastParam) {
         case ReplicationProtocol:
            return replicationProtocol();
         case ReplicationDegree:
            return replicationDegree();
         case NumNodes:
            return numNodes();
         default:
            throw new IllegalArgumentException("Param " + forecastParam + " is not present");
      }
   }

   /**
    * AD HOC METHODS *
    */

   private int numNodes() {
      return (int)csvParser.getNumNodes();
   }

   private int replicationDegree() {
      return (int)csvParser.replicationDegree();
   }

   private double putsPerWrXact() {
      return csvParser.putsPerWrXact();
   }

   private int numThreadsPerNode() {
      return (int)csvParser.numThreads();
   }

   private double prepareCommandSize() {
      return csvParser.sizePrepareMsg();
   }

   private double acf() {
      return 1.0D / csvParser.numKeys();
   }

   private double memory() {
      return 1e-6 * csvParser.mem();
   }

   private int cpus() {
      return 2;
   }

   private ReplicationProtocol replicationProtocol() {
      return ReplicationProtocol.valueOf(csvParser.getReplicationProtocol());
   }

   private double getsPerRoXact() {
      return csvParser.readsPerROXact();
   }

   private double getsPerWrXact() {
      return csvParser.readsPerWrXact();
   }

   private double localUpdateTxLocalServiceTime() {
      return csvParser.localServiceTimeWrXact();
   }

   private double localUpdateTxPrepareServiceTime() {
      return csvParser.getAvgParam("LocalUpdateTxPrepareServiceTime");
   }

   private double localUpdateTxCommitServiceTime() {
      return csvParser.getAvgParam("LocalUpdateTxCommitServiceTime");
   }

   private double localUpdateTxLocalRollbackServiceTime() {
      return csvParser.getAvgParam("LocalUpdateTxLocalRollbackServiceTime");
   }

   private double localUpdateTxRemoteRollbackServiceTime() {
      return csvParser.getAvgParam("LocalUpdateTxRemoteRollbackServiceTime");
   }

   private double localReadOnlyTxTotalCpuTime() {
      return csvParser.localServiceTimeROXact();
   }

   private double remoteGetServiceTime() {
      return csvParser.localRemoteGetServiceTime();
   }

   private double remoteUpdateTxPrepareServiceTime() {
      return csvParser.remotePrepareServiceTime();
   }

   private double remoteUpdateTxCommitServiceTime() {
      return csvParser.remoteCommitCommandServiceTime();
   }

   private double remoteUpateTxRollbackServiceTime() {
      return csvParser.remoteRollbackServiceTime();
   }

   private double gmuClusterGetCommandServiceTime() {
      return csvParser.remoteRemoteGetServiceTime();
   }

   private double writePercentage() {
      return csvParser.writePercentageXact();
   }

   private double AvgTxArrivalRate() {
      return csvParser.usecThroughput() * csvParser.getNumNodes();
   }

   private double AvgNTCBTime() {
      return 0D;
   }


   private double AvgLocalGetTime() {
      return csvParser.getAvgParam("AvgLocalGetTime");
   }

   private double LocalUpdateTxPrepareResponseTime() {
      return csvParser.getAvgParam("LocalUpdateTxPrepareResponseTime");
   }

   private double LocalUpdateTxLocalResponseTime() {
      return csvParser.getAvgParam("LocalUpdateTxLocalResponseTime");
   }

   private double AverageWriteTime() {
      return csvParser.getAvgParam("LocalUpdateTxLocalServiceTime");//adapted
   }

   private double numberOfEntries() {
      return csvParser.numKeys();
   }
   
   private double AvgClusteredGetCommandReplySize(){
   
       return csvParser.getAvgParam("AvgClusteredGetCommandReplySize");
   }


}
