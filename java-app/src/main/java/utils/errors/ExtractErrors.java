package utils.errors;

import com.newrelic.relocated.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ExtractErrors {

    public List<String> extractErrors() {
        List<String> errorMessages = new ArrayList<>();
        try {
            // Path to the Lighthouse JSON report
            String filePath = "lighthouse_report.json";
            FileInputStream fileInputStream = new FileInputStream(filePath);
            JSONTokener tokener = new JSONTokener(fileInputStream);
            JSONObject report = new JSONObject(tokener);

            // Extract the errors-in-console details
            JSONObject categories = report.getJSONObject("audits");
            JSONObject errors = categories.getJSONObject("errors-in-console");
            JSONObject errorDetails = errors.getJSONObject("details");
            JSONArray errorItems = errorDetails.getJSONArray("items");

            // Iterate through each error item
            for (int i = 0; i < errorItems.length(); i++) {
                JSONObject errorItem = errorItems.getJSONObject(i);
                StringBuilder errorMessage = new StringBuilder();

                // Add description to the error message
                errorMessage.append("Description: ").append(errorItem.getString("description")).append(", ");

                // Check if the sourceLocation is present and add it to the error message
                if (errorItem.has("sourceLocation")) {
                    JSONObject sourceLocation = errorItem.getJSONObject("sourceLocation");
                    errorMessage.append("Source URL: ").append(sourceLocation.getString("url")).append(", ");
                    errorMessage.append("Line: ").append(sourceLocation.getInt("line")).append(", ");
                    errorMessage.append("Column: ").append(sourceLocation.getInt("column")).append(", ");
                }

                // Add source if present
                if (errorItem.has("source")) {
                    errorMessage.append("Source: ").append(errorItem.getString("source")).append(", ");
                }

                // Remove the last comma and space if any
                if (errorMessage.length() > 2) {
                    errorMessage.setLength(errorMessage.length() - 2);
                }

                // Add the constructed error message to the list
                errorMessages.add(errorMessage.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return errorMessages;
    }

    public List<String> getExtraInformationAboutErrors() {
        List<String> errorsList = new ArrayList<>();
        try {
            // Path to the Lighthouse JSON report
            String filePath = "lighthouse_report.json";
            FileInputStream fileInputStream = new FileInputStream(filePath);
            JSONTokener tokener = new JSONTokener(fileInputStream);
            JSONObject report = new JSONObject(tokener);

            // Extract the timing and entries details
            JSONObject timing = report.getJSONObject("timing");
            JSONArray entries = timing.getJSONArray("entries");

            // Loop through each entry in the entries array
            for (int i = 0; i < entries.length(); i++) {
                JSONObject entry = entries.getJSONObject(i);
                // Check if the name contains "errors-in-console"
                if (entry.getString("name").contains("errors-in-console")) {
                    // Create a string from the entry's properties
                    String entryAsString = convertEntryToString(entry);
                    // Add the string representation to the errorsList
                    errorsList.add(entryAsString);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return errorsList;
    }

    private String convertEntryToString(JSONObject entry) {
        StringBuilder entryBuilder = new StringBuilder();
        for (String key : entry.keySet()) {
            entryBuilder.append(key).append(": ").append(entry.get(key).toString()).append(", ");
        }
        // Remove the trailing comma and space
        if (entryBuilder.length() > 2) {
            entryBuilder.setLength(entryBuilder.length() - 2);
        }
        return entryBuilder.toString();
    }

    public static void main(String[] args) {
        ExtractErrors extractErrors = new ExtractErrors();
//        List<String> errors = extractErrors.extractErrors();
//        for (String error : errors) {
//            System.out.println(error);
//        }

        List<String> errors = extractErrors.getExtraInformationAboutErrors();
        for (String error : errors) {
            System.out.println(error);
        }
    }
}
