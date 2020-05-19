package rainier.org.csvcompare.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;

public class CsvHashImp implements CsvHash {

  private static final Logger logger = Logger.getLogger(CsvHashImp.class);

  @Override
  public String hash(String data) {

    try {
      byte[] hash = getSHA(data);

      StringBuilder sb = new StringBuilder();

      for (byte b : hash) {
        sb.append(String.format("%02x", b));
      }
      return sb.toString();
    } catch (NoSuchAlgorithmException ex) {
      logger.info(ex.getMessage(), ex.getCause());
      return null;
    }
  }

  private byte[] getSHA(String input) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    return md.digest(input.getBytes(StandardCharsets.UTF_8));
  }
}
