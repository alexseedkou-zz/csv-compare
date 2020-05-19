package rainier.org.csvcompare.utils;

import java.io.File;

public interface CsvFileConverter<T> {

  T convert(File file);
}
