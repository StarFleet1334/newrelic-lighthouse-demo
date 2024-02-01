import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import org.json.JSONTokener;
import utils.errors.ExtractErrors;
import utils.performance_metrics.ExtractPS;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static spark.Spark.get;

public class Main {

    // Replace these constants with your actual API key and account ID.
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




            List<String> errors = extractErrors.extractErrors();
            List<String> additional_information_about_errors = extractErrors.getExtraInformationAboutErrors();
            for (String error : errors) {
                logger.warning(error);
            }
            for (String additionalInformationAboutError : additional_information_about_errors) {
                logger.warning(additionalInformationAboutError);
            }


    }
    }
