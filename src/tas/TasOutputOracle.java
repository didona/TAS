package tas;


import eu.cloudtm.oracles.OutputOracle;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class TasOutputOracle implements OutputOracle {

   private double throughput;
   private double abortRate;
   private double responseTime;


   public TasOutputOracle(double throughput, double abortRate, double responseTime) {
      this.throughput = throughput;
      this.abortRate = abortRate;
      this.responseTime = responseTime;
   }

   @Override
   public double throughput() {
      return 0;  // TODO: Customise this generated block
   }

   @Override
   public double abortRate() {
      return 0;  // TODO: Customise this generated block
   }

   @Override
   public double responseTime(int i) {
      return 0;  // TODO: Customise this generated block
   }
}
