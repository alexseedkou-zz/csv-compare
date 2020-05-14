package rainier.org.csvcompare;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import rainier.org.csvcompare.compare.CsvCompare;
import rainier.org.csvcompare.compare.CsvCompareImp;
import rainier.org.csvcompare.config.Config;
import rainier.org.csvcompare.dto.FileToCompare;
import rainier.org.csvcompare.loadFile.LoadFile;
import rainier.org.csvcompare.loadFile.LoadFileHttp;
import rainier.org.csvcompare.utils.CsvFileConverter;
import rainier.org.csvcompare.utils.CsvFileConverterImp;

public class CsvDiffApplication {

  private static final Logger logger = Logger.getLogger(CsvDiffApplication.class);

  public static void main(String[] args) {
    CsvDiffApplication object = new CsvDiffApplication();
    object.waitMethod();
  }

  private synchronized void waitMethod() {
    while (true) {

      try {
        System.out.println(
            "csv compare program is running ==> " + Calendar.getInstance().getTime());

        CsvFileConverter<Map<String, String>> csvFileConverter = new CsvFileConverterImp();
        HttpClient http = HttpClientBuilder.create().build();
        LoadFile loadFile = new LoadFileHttp(http);
        initCompare(CsvCompareImp.getInstance(), csvFileConverter, loadFile);
        this.wait(Config.getTimeWindow());
      } catch (InterruptedException ex) {
        logger.error(ex.getMessage(), ex.getCause());
        ex.printStackTrace();
      } catch (IOException ex) {
        int[] counter = new int[] {0};
        int maxTriex = 3;
        retry(counter, maxTriex);

        if (counter[0] == 3) {
          logger.error(ex.getMessage(), ex.getCause());
          ex.printStackTrace();
        }
      }
    }
  }

  private <T> void initCompare(
      CsvCompare<T> csvCompare, CsvFileConverter<T> csvFileConverter, LoadFile loadFile)
      throws IOException {

    if (csvCompare.getFileToComperInstance() == null) {
      // first time to read data
      logger.info("first time to load data into " + Config.getCurFileName());
      if (loadFile.loadFile(Config.getCurFileName())) {
        FileToCompare<T> fileToCompare = new FileToCompare<>();
        csvCompare.setFileToCompare(fileToCompare);
        File curFile = new File(Config.getCurFileName());
        fileToCompare.setCurFile(csvFileConverter.convert(curFile));
      }
    } else if (csvCompare.getFileToComperInstance().getTargetFile() == null) {
      // not first time to read data
      logger.info("start to write data into " + Config.getTargetFileName());
      if (loadFile.loadFile(Config.getTargetFileName())) {
        File targetFile = new File(Config.getTargetFileName());
        csvCompare.getFileToComperInstance().setTargetFile(csvFileConverter.convert(targetFile));
        csvCompare.compare();
      }
    }
  }

  private void retry(int[] counter, int maxTries) {
    if (counter[0] == maxTries) return;

    try {
      Thread.sleep(5000);
      CsvFileConverter<Map<String, String>> csvFileConverter = new CsvFileConverterImp();
      HttpClient http = HttpClientBuilder.create().build();
      LoadFile loadFile = new LoadFileHttp(http);
      initCompare(CsvCompareImp.getInstance(), csvFileConverter, loadFile);
    } catch (IOException ex) {
      counter[0]++;

      retry(counter, maxTries);
    } catch (InterruptedException ex) {
      logger.error(ex.getMessage(), ex.getCause());
      ex.printStackTrace();
    }
  }
}
