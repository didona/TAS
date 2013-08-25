package tasOracle;




/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class TasOutputOracle implements OutputOracle {

   private double throughput;
   private double abortRate;
   private double responseTimeW;
   private double responseTimeR;

   public TasOutputOracle(double throughput, double abortRate, double responseTimeW, double responseTimeR) {
      this.throughput = throughput;
      this.abortRate = abortRate;
      this.responseTimeW = responseTimeW;
      this.responseTimeR = responseTimeR;
   }


   @Override
   public double throughput() {
      return this.throughput;
   }

   @Override
   public double abortRate() {
      return this.abortRate;
   }

   @Override
   public double responseTime(int i) {
      if (i == 0)
         return responseTimeW;
      return responseTimeR;
   }
}
