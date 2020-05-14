package rainier.org.csvcompare.loadFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;
import java.io.File;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.log4j.Logger;
import rainier.org.csvcompare.config.Config;

public class LoadFileHttp implements LoadFile {

  private HttpClient client;

  private HttpUriRequest request;

  private static final Logger logger = Logger.getLogger(LoadFileHttp.class);

  public LoadFileHttp(HttpClient client) {
    this.client = client;
    this.request = new HttpGet(Config.getFileEndPoint());
  }

  public LoadFileHttp(HttpClient client, HttpUriRequest request) {
    this.client = client;
    this.request = request;
  }

  @Override
  public boolean loadFile(String filePath) {

    try {

      logger.info("get file data through http");
      HttpResponse response = client.execute(request);

      if (response == null) return false;
      JsonNode jsonTree = new ObjectMapper().readTree(response.getEntity().getContent());
      Builder csvSchemaBuilder = CsvSchema.builder();
      JsonNode firstObject = jsonTree.elements().next();
      firstObject
          .fieldNames()
          .forEachRemaining(
              fieldName -> {
                csvSchemaBuilder.addColumn(fieldName);
              });
      CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
      CsvMapper csvMapper = new CsvMapper();
      csvMapper.writerFor(JsonNode.class).with(csvSchema).writeValue(new File(filePath), jsonTree);
      return true;
    } catch (IOException ex) {
      logger.error("error when load file", ex.getCause());
      return false;
    } catch (Exception ex) {
      logger.error("error when load file", ex.getCause());
      return false;
    }
  }
}
