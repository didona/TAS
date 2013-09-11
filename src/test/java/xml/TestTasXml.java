package xml;

import eu.cloudtm.autonomicManager.debug.UnMarshalledInputOracle;
import eu.cloudtm.autonomicManager.debug.XmlInputOracleUnMarshaller;
import eu.cloudtm.autonomicManager.oracles.OutputOracle;
import eu.cloudtm.autonomicManager.oracles.exceptions.OracleException;
import tasOracle.TasOracle;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class TestTasXml {

   private static final String sample = "/Users/diego/Desktop/dump_7";

   public static void main(String[] args) {
      XmlInputOracleUnMarshaller xmlInputOracleUnMarshaller = new XmlInputOracleUnMarshaller(sample);
      UnMarshalledInputOracle uio = xmlInputOracleUnMarshaller.unMarshal();
      TasOracle to = new TasOracle();
      try {
         OutputOracle out = to.forecast(uio);
         System.out.println("readR " + out.responseTime(0) + " writeR " + out.responseTime(1) + " readT " + out.throughput(0) + " writeT " + out.throughput(1) + " readA " + out.abortRate(0) + " writeA " + out.abortRate(1));
      } catch (OracleException e) {
         System.out.println("KO " + e.getMessage());
      }
   }

}
