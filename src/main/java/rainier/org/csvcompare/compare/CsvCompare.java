package rainier.org.csvcompare.compare;

import java.io.IOException;
import rainier.org.csvcompare.dto.FileToCompare;

public interface CsvCompare<T> {

  void setFileToCompare(FileToCompare<T> objectToCompare);

  FileToCompare<T> getFileToComperInstance();

  void compare() throws IOException;
}
