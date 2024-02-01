import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import org.json.JSONTokener;
import utils.performance_metrics.ExtractPS;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class Main {

    // Replace these constants with your actual API key and account ID.
    private static final String INSERT_API_KEY = "eu01xxc5e0f882ad3e6f1205816c5203FFFFNRAL"; // Example: "eu01xx5...xxxxxx"
    private static final String NEW_RELIC_ENDPOINT = "https://insights-collector.newrelic.com/v1/accounts/4328909/events";

    private static final ExtractPS PERFORMANCE_CALCULATOR = new ExtractPS();

    public static void main(String[] args) {
        try {
            // Path to the Lighthouse JSON report
            String filePath = Paths.get(System.getProperty("user.dir"), "lighthouse_report.json").toString();
            FileInputStream fileInputStream = new FileInputStream(filePath);
            JSONTokener tokener = new JSONTokener(fileInputStream);
            JSONObject report = new JSONObject(tokener);

            // Extract the metrics you care about from the report
            // For example, if you're interested in performance:
            // JSONObject performance = report.getJSONObject("categories").getJSONObject("performance");
            // double performanceScore = performance.getDouble("score");

            // Construct the JSON object you want to send to New Relic
            JSONObject newRelicEvent = new JSONObject();
            newRelicEvent.put("eventType", "LighthouseAudit");
            newRelicEvent.put("performanceScore", PERFORMANCE_CALCULATOR.calculatePerformance()); // Use your extracted metric here

            // Send the data to New Relic
            sendCustomEventToNewRelic(newRelicEvent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendCustomEventToNewRelic(JSONObject event) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, event.toString());
        Request request = new Request.Builder()
                .url(NEW_RELIC_ENDPOINT)
                .post(body)
                .addHeader("X-Insert-Key", INSERT_API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            System.out.println("Response from New Relic: " + response.body().string());
        }
    }
}
