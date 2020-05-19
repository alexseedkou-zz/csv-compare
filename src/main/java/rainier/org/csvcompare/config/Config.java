package rainier.org.csvcompare.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

public class Config {

  private static String curFileName;

  private static String targetFileName;

  private static String diffFileDelete;

  private static String diffFileCreate;

  private static String diffFileModification;

  private static String idName;

  private static int timeWindow;

  private static String fileEndPoint;

  private static int maxTries;

  private static int retryTimeSlot;

  private static final Logger logger = Logger.getLogger(Config.class);

  static {
    Properties props = new Properties();
    InputStream is = Config.class.getResourceAsStream("/application.properties");

    try {
      props.load(is);
      Config.curFileName = props.getProperty("cur-file");
      Config.targetFileName = props.getProperty("target-file");
      Config.diffFileCreate = props.getProperty("diff-file-create");
      Config.diffFileDelete = props.getProperty("diff-file-delete");
      Config.diffFileModification = props.getProperty("diff-file-modification");
      Config.idName = props.getProperty("id");
      Config.timeWindow = Integer.parseInt(props.getProperty("time-window"));
      Config.maxTries = Integer.parseInt(props.getProperty("max-tries"));
      Config.retryTimeSlot = Integer.parseInt(props.getProperty("retry-time-slot"));
      Config.fileEndPoint = props.getProperty("load-file-endpoint");
    } catch (FileNotFoundException ex) {
      logger.info(ex.getMessage());
      ex.printStackTrace();
    } catch (IOException ex) {
      logger.info(ex.getMessage());
      ex.printStackTrace();
    }
  }

  public static String getCurFileName() {
    return curFileName;
  }

  public static String getTargetFileName() {
    return targetFileName;
  }

  public static String getDiffCreateFile() {
    return diffFileCreate;
  }

  public static String getDiffDeleteFile() {
    return diffFileDelete;
  }

  public static String getDiffModificationFile() {
    return diffFileModification;
  }

  public static int getTimeWindow() {
    return timeWindow;
  }

  public static String getIdName() {
    return idName;
  }

  public static int getMaxRetries() {
    return maxTries;
  }

  public static int getRetryTimeSlot() {
    return retryTimeSlot;
  }

  public static String getFileEndPoint() {
    return fileEndPoint;
  }
}
