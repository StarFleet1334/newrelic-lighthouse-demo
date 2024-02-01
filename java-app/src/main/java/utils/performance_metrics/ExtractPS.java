package utils.performance_metrics;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class ExtractPS {

    public double calculatePerformance() {
        double performanceScore = 0;
        try {
            // Path to the Lighthouse JSON report
            String filePath = Paths.get(System.getProperty("user.dir"), "/java-app/lighthouse_report.json").toString();
            FileInputStream fileInputStream = new FileInputStream(filePath);
            JSONTokener tokener = new JSONTokener(fileInputStream);
            JSONObject report = new JSONObject(tokener);


            JSONObject categories = report.getJSONObject("categories");
            JSONObject performance = categories.getJSONObject("performance");
            performanceScore = performance.getDouble("score") * 100; // The score is usually between 0 and 1, so you might want to multiply by 100
        } catch (IOException e) {
            e.printStackTrace();
        }
        return performanceScore;
    }

}
