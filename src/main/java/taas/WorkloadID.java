package taas;

import eu.cloudtm.autonomicManager.commons.ReplicationProtocol;
import parse.radargun.Ispn5_2CsvParser;

/**
 * // TODO: Document this
 *
 * @author diego
 * @since 4.0
 */
public class WorkloadID {

   private double acf;
   private double numNodes;
   private double replicationDegree;
   private ReplicationProtocol replicationProtocol;

   public WorkloadID(double acf, double numNodes, double replicationDegree, ReplicationProtocol replicationProtocol) {
      this.acf = acf;
      this.numNodes = numNodes;
      this.replicationDegree = replicationDegree;
      this.replicationProtocol = replicationProtocol;
   }

   public WorkloadID(Ispn5_2CsvParser parser) {
      this.replicationProtocol = replicationProtocolFromCsv(parser);
      this.numNodes = parser.getNumNodes();
      this.acf = 1.0D / parser.numKeys();
      this.replicationDegree = parser.getAvgParam("ReplicationDegree");
   }

   @Override
   public boolean equals(Object o) {
      return super.equals(o);    // TODO: Customise this generated block
   }

   private ReplicationProtocol replicationProtocolFromCsv(Ispn5_2CsvParser csv) {
      double protocol = csv.getAvgParam("CurrentProtocolAsInt");
      if (protocol == 0)
         return ReplicationProtocol.TWOPC;
      if (protocol == 1)
         return ReplicationProtocol.PB;
      if (protocol == 2)
         return ReplicationProtocol.TO;
      throw new IllegalArgumentException("Replication protocol in " + csv.getPath() + " is " + protocol);
   }

   @Override
   public String toString() {
      return "WorkloadID{" +
            "acf=" + acf +
            ", numNodes=" + numNodes +
            ", replicationDegree=" + replicationDegree +
            ", replicationProtocol=" + replicationProtocol +
            '}';
   }
}
