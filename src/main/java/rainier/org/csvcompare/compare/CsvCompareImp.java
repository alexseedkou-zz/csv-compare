package rainier.org.csvcompare.compare;

import de.siegmar.fastcsv.writer.CsvWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import rainier.org.csvcompare.config.Config;
import rainier.org.csvcompare.dto.FileToCompare;

public class CsvCompareImp implements CsvCompare<Map<String, String>> {

  private static CsvCompareImp obj;

  private FileToCompare<Map<String, String>> fileToCompare;

  private CsvWriter csvWriter;

  private static final Logger logger = Logger.getLogger(CsvCompareImp.class);

  private CsvCompareImp() {
    csvWriter = new CsvWriter();
  }

  public static CsvCompareImp getInstance() {
    if (obj == null) obj = new CsvCompareImp();
    return obj;
  }

  @Override
  public void setFileToCompare(FileToCompare<Map<String, String>> fileToCompare) {
    this.fileToCompare = fileToCompare;
  }

  @Override
  public FileToCompare<Map<String, String>> getFileToComperInstance() {
    return this.fileToCompare;
  }

  @Override
  public void compare() throws IOException {
    List<String> modification = new ArrayList<>();
    List<String> delete = new ArrayList<>();
    List<String> create = new ArrayList<>();
    Map<String, String> curFile = fileToCompare.getCurFile();
    Map<String, String> targetFile = fileToCompare.getTargetFile();

    for (String key : targetFile.keySet()) {
      if (!curFile.containsKey(key)) {
        create.add(key);
      } else {
        if (!curFile.get(key).equals(targetFile.get(key))) {
          modification.add(key);
        }
        curFile.remove(key);
      }
    }

    delete.addAll(curFile.keySet());

    if (modification.size() > 0) writeDiffFile(modification, Config.getDiffModificationFile());

    if (delete.size() > 0) writeDiffFile(delete, Config.getDiffDeleteFile());

    if (create.size() > 0) writeDiffFile(create, Config.getDiffCreateFile());

    this.fileToCompare.setCurFile(targetFile);
    this.fileToCompare.setTargetFile(null);
  }

  private void writeDiffFile(List<String> diff, String fileName) throws IOException {
    File diffFile = new File(fileName);
    List<String[]> data = new ArrayList<>();
    String[] diffArray = new String[diff.size()];

    logger.info("start to write data to " + fileName);
    diffArray = diff.toArray(diffArray);
    data.add(diffArray);
    csvWriter.write(diffFile, StandardCharsets.UTF_8, data);
  }
}
