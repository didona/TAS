package morphr;

import eu.cloudtm.autonomicManager.oracles.OutputOracle;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class MorphrOutputOracle implements OutputOracle {
   private double[] xput = new double[2];
   private double[] respT = new double[2];
   private double[] abort = new double[2];

   public MorphrOutputOracle(double[] xput, double[] respT, double[] abort) {
      this.xput = xput;
      this.respT = respT;
      this.abort = abort;
   }

   @Override
   public double throughput(int i) {
      return xput[i];
   }

   @Override
   public double abortRate(int i) {
      return abort[i];
   }

   @Override
   public double responseTime(int i) {
      return respT[i];
   }

   @Override
   public double getConfidenceThroughput(int i) {
      return 1;
   }

   @Override
   public double getConfidenceAbortRate(int i) {
      return 1;
   }

   @Override
   public double getConfidenceResponseTime(int i) {
      return 1;
   }
}
