package rainier.org.csvcompare.utils;

import de.siegmar.fastcsv.reader.CsvRow;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import rainier.org.csvcompare.config.Config;

public class CsvMultiThread extends Thread {
  private Thread thread;
  private String threadName;
  private List<CsvRow> rows;
  private final int start;
  private final int end;
  private CsvHash csvHash;
  private final Map<String, String> map;

  private static final Logger log = Logger.getLogger(CsvMultiThread.class);

  public CsvMultiThread(
      String threadName,
      List<CsvRow> rows,
      CsvHash csvHash,
      Map<String, String> map,
      int start,
      int end) {
    this.threadName = threadName;
    this.rows = rows;
    this.csvHash = csvHash;
    this.map = map;
    this.start = start;
    this.end = end;
  }

  public void run() {
    System.out.println("Running " + threadName);

    try {
      for (int i = start; i <= end; i++) {
        map.put(rows.get(i).getField(Config.getIdName()), csvHash.hash(rows.get(i).toString()));
      }
    } catch (Exception ex) {
      log.info(ex.getMessage(), ex.getCause());
    }
  }

  public void start() {
    System.out.println("Starting " + threadName);
    if (thread == null) {
      thread = new Thread(this, threadName);
      thread.start();
    }
  }
}
