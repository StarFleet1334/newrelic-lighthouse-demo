import utils.errors.ExtractErrors;

import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static spark.Spark.*;





public class App {

    private static final ExtractErrors extractErrors = new ExtractErrors();
    public static void main(String[] args) {

        Logger logger = Logger.getLogger(App.class.getName());
        
        try {
            // Create a file handler to log to "log.txt"
            FileHandler fileHandler = new FileHandler("log.txt");
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);

            // Add the file handler to the logger
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Please navigate to http://localhost:4567/hello");
        // Set up a route that responds to HTTP GET requests at the "/hello" endpoint
        get("/hello", (req, res) -> {
            // Set the response type to JSON
            res.type("application/json");
            String jsonResponse = "{\"message\": \"Hello, World!\"}";

            // Sending Logs

            List<String> errors = extractErrors.extractErrors();
            List<String> additional_information_about_errors = extractErrors.getExtraInformationAboutErrors();
            for (String error : errors) {
                logger.warning(error);
            }
            for (String additionalInformationAboutError : additional_information_about_errors) {
                logger.warning(additionalInformationAboutError);
            }


            // Sending Metrices

            return jsonResponse;
        });

    }
}
