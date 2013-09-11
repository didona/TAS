package xml;

import eu.cloudtm.autonomicManager.debug.UnMarshalledInputOracle;
import eu.cloudtm.autonomicManager.debug.XmlInputOracleUnMarshaller;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class TestTasXml {

   private static final String sample = "/Users/diego/Desktop/dump_7";

   public static void main(String[] args){
      XmlInputOracleUnMarshaller xmlInputOracleUnMarshaller = new XmlInputOracleUnMarshaller(sample);
      UnMarshalledInputOracle uio = xmlInputOracleUnMarshaller.unMarshal();
      System.out.println(uio);
   }
}
