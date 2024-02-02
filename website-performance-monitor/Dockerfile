# Use the official Selenium base image with Standalone Chrome
FROM selenium/standalone-chrome:latest

# Set environment variables for Chrome to run properly
ENV DBUS_SESSION_BUS_ADDRESS=/dev/null

# Install utilities
USER root
RUN apt-get update -y \
    && apt-get install -y wget unzip software-properties-common \
    && rm -rf /var/lib/apt/lists/* /var/cache/apt/*

# Download and install the latest version of ChromeDriver
RUN wget -q "https://chromedriver.storage.googleapis.com/LATEST_RELEASE" -O /tmp/latest_chromedriver \
    && LATEST_CHROMEDRIVER=$(cat /tmp/latest_chromedriver) \
    && wget -q "https://chromedriver.storage.googleapis.com/${LATEST_CHROMEDRIVER}/chromedriver_linux64.zip" -O /tmp/chromedriver_linux64.zip \
    && unzip /tmp/chromedriver_linux64.zip -d /usr/local/bin/ \
    && rm /tmp/chromedriver_linux64.zip \
    && rm /tmp/latest_chromedriver

# Replace the existing ChromeDriver with the downloaded version
RUN mv -f /usr/local/bin/chromedriver /usr/bin/chromedriver \
    && chown root:root /usr/bin/chromedriver \
    && chmod 0755 /usr/bin/chromedriver

# Set back to selenium user
USER seluser

# Expose the port the app runs on
EXPOSE 4444

# Health check to make sure selenium is up and running
HEALTHCHECK --interval=10s --timeout=10s --retries=3 --start-period=30s \
  CMD curl -f http://localhost:4444/wd/hub || exit 1

# The entry point script is provided by the base image. It starts Selenium.
ENTRYPOINT ["/opt/bin/entry_point.sh"]
