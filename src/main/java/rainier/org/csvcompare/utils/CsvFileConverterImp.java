package rainier.org.csvcompare.utils;

import de.siegmar.fastcsv.reader.CsvParser;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import rainier.org.csvcompare.config.Config;

public class CsvFileConverterImp implements CsvFileConverter<Map<String, String>> {
  private final CsvReader csvReader = new CsvReader();

  private CsvHash csvHash;

  private static final Logger logger = Logger.getLogger(CsvFileConverterImp.class);

  public CsvFileConverterImp() {
    csvHash = new CsvHashImp();
  }

  public CsvFileConverterImp(CsvHash csvHash) {
    this.csvHash = csvHash;
  }

  @Override
  public Map<String, String> convert(File file) {
    Map<String, String> map = new HashMap<>();
    csvReader.setContainsHeader(true);
    try (CsvParser csvParser = csvReader.parse(file, StandardCharsets.UTF_8)) {
      CsvRow row;
      while ((row = csvParser.nextRow()) != null) {
        map.put(row.getField(Config.getIdName()), csvHash.hash(row.toString()));
      }
    } catch (IOException ex) {
      logger.info(ex.getMessage(), ex.getCause());
      ex.printStackTrace();
    }

    return map;
  }
}
