package utils.performance_metrics;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class ExtractSI {
    public JSONObject extractSpeedIndex() {
        JSONObject speed_index = null;
        try {
            // Path to the Lighthouse JSON report
            String filePath = Paths.get(System.getProperty("user.dir"), "/java-app/lighthouse_report.json").toString();
            FileInputStream fileInputStream = new FileInputStream(filePath);
            JSONTokener tokener = new JSONTokener(fileInputStream);
            JSONObject report = new JSONObject(tokener);


            JSONObject categories = report.getJSONObject("audits");
            speed_index = categories.getJSONObject("speed-index");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return speed_index;
    }

}
