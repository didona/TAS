package fake;

import eu.cloudtm.autonomicManager.commons.ForecastParam;
import eu.cloudtm.autonomicManager.commons.Param;
import eu.cloudtm.autonomicManager.commons.ReplicationProtocol;
import eu.cloudtm.autonomicManager.oracles.InputOracle;
import eu.cloudtm.autonomicManager.oracles.Oracle;
import eu.cloudtm.autonomicManager.oracles.OutputOracle;
import eu.cloudtm.autonomicManager.oracles.exceptions.OracleException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class FakeOracle implements Oracle {

   private final static double WIN_T = 1D;
   private final static double LOSE_T = 0D;
   private final static double WIN_R = 1D;
   private final static double LOSE_R = 100D;
   private final static double WIN_A = 0D;
   private final static double LOSE_A = 1D;
   private final static double WIN_NODES = 5D;
   private final static Log log = LogFactory.getLog(FakeOracle.class);

   @Override
   public OutputOracle forecast(InputOracle inputOracle) throws OracleException {
      final boolean t = log.isTraceEnabled();
      ReplicationProtocol winnerRP = winnerRP(inputOracle);
      ReplicationProtocol queryRP = (ReplicationProtocol) inputOracle.getForecastParam(ForecastParam.ReplicationProtocol);
      double nodes = ((Number) inputOracle.getForecastParam(ForecastParam.NumNodes)).doubleValue();
      if (t)
         log.trace("Forecasting for " + queryRP + " and " + nodes + " nodes");
      if (queryRP.equals(winnerRP) &&
              nodes == WIN_NODES)
         return buildWinnerFakeOutput(inputOracle);
      return buildLoserFakeOutput(inputOracle);
   }

   private FakeOutputOracle buildWinnerFakeOutput(InputOracle i) {
      double abort[] = buildDoubleArray(WIN_A);
      double resp[] = buildDoubleArray(WIN_R);
      double xput[] = buildDoubleArray(WIN_T);
      return new FakeOutputOracle(xput, resp, abort);
   }

   private FakeOutputOracle buildLoserFakeOutput(InputOracle i) {
      double abort[] = buildDoubleArray(LOSE_A);
      double resp[] = buildDoubleArray(LOSE_R);
      double xput[] = buildDoubleArray(LOSE_T);
      return new FakeOutputOracle(xput, resp, abort);
   }

   private ReplicationProtocol winnerRP(InputOracle inputOracle) {
      double avgPutsPerTx = (Double) inputOracle.getParam(Param.AvgPutsPerWrTransaction);
      if (avgPutsPerTx < 5) {
         return ReplicationProtocol.TWOPC;
      } else if (avgPutsPerTx >= 5 && avgPutsPerTx <= 20) {
         return ReplicationProtocol.TO;
      } else {
         return ReplicationProtocol.PB;
      }
   }

   private double[] buildDoubleArray(double value) {
      return new double[]{value, value};
   }

   private ReplicationProtocol rp(InputOracle input) {
      return (ReplicationProtocol) input.getForecastParam(ForecastParam.ReplicationProtocol);
   }

}
