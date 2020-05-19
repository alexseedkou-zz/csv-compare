package rainier.org.csvcompare.loadFile;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import rainier.org.csvcompare.config.Config;

public class LoadFileHttpTest {
  @InjectMocks private LoadFileHttp loadFileHttp;

  @Mock private HttpClient client;

  @Mock private HttpUriRequest request;

  @Before
  public void setup() {
    initMocks(this);
  }

  @Test
  public void loadFileTest() throws ClientProtocolException, IOException {
    loadFileHttp.loadFile(Config.getTargetFileName());
    ArgumentCaptor<HttpGet> requestCaptor = ArgumentCaptor.forClass(HttpGet.class);
    verify(client, times(1)).execute(requestCaptor.capture());
    assertEquals(request, requestCaptor.getValue());
  }
}
