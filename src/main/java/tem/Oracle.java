package tem;


/**
 * Created by: Fabio Perfetti
 * E-mail: perfabio87@gmail.com
 * Date: 6/12/13
 */
public interface Oracle {

    public OutputOracle forecast(InputOracle input) throws OracleException;

}
