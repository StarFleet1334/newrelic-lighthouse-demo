package utils.performance_metrics;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class ExtractTBT {

    public JSONObject extractTotalBlockingTime() {
        JSONObject lcp = null;
        try {
            // Path to the Lighthouse JSON report
            String filePath = Paths.get(System.getProperty("user.dir"), "lighthouse_report.json").toString();
            FileInputStream fileInputStream = new FileInputStream(filePath);
            JSONTokener tokener = new JSONTokener(fileInputStream);
            JSONObject report = new JSONObject(tokener);

            JSONObject categories = report.getJSONObject("audits");
            lcp = categories.getJSONObject("total-blocking-time");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lcp;
    }
}
