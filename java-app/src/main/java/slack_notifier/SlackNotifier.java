package slack_notifier;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;

public class SlackNotifier {

    public static void main(String[] args) {
        // Load .env file using java-dotenv library
        Dotenv dotenv = Dotenv.load();

        // Read environment variables
        String token = dotenv.get("SLACK_BOT_TOKEN");
        String channelID = dotenv.get("SLACK_CHANNEL_ID");

        // Initialize Slack client
        Slack slack = Slack.getInstance();

        try {
            // Prepare and send the message
            ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
                    .channel(channelID)
                    .text("This is a test message from my Java application!")
            );

            // Check response
            if (response.isOk()) {
                System.out.println("Message successfully sent to channel " + channelID);
            } else {
                System.err.println("Failed to send message due to error: " + response.getError());
            }

        } catch (IOException | SlackApiException e) {
            e.printStackTrace();
        }
    }
}

