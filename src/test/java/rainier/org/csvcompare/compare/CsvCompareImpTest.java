package rainier.org.csvcompare.compare;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import de.siegmar.fastcsv.writer.CsvWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import rainier.org.csvcompare.config.Config;
import rainier.org.csvcompare.dto.FileToCompare;

public class CsvCompareImpTest {

  @InjectMocks private CsvCompareImp csvCompareImp;

  @Mock private CsvWriter csvWriter;

  @Before
  public void setup() {
    initMocks(this);
  }

  @Test
  public void compareTest() throws IOException {
    FileToCompare<Map<String, String>> fileToCompare = new FileToCompare<>();
    Map<String, String> curFile = new HashMap<>();
    Map<String, String> targetFile = new HashMap<>();
    fileToCompare.setCurFile(curFile);
    fileToCompare.setTargetFile(targetFile);
    csvCompareImp.setFileToCompare(fileToCompare);
    csvCompareImp.compare();
    File diffFile = new File(Config.getDiffModificationFile());
    List<String[]> data = new ArrayList<>();
    verify(csvWriter, times(0)).write(diffFile, StandardCharsets.UTF_8, data);
  }
}
