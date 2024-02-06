# Use a specific version of the Node image that's based on Debian
FROM node:16-bullseye

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy the current directory contents into the container at /usr/src/app
COPY . .

# Install Chrome for Lighthouse
RUN dpkg --add-architecture amd64 && \
    wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - && \
    echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google.list && \
    apt-get update && \
    apt-get install -y google-chrome-stable

# Install Maven and Java (OpenJDK)
RUN apt-get update && \
    apt-get install -y maven openjdk-11-jdk

# Make the start.sh file executable
RUN chmod +x start.sh

# Make port 80 available to the world outside this container
EXPOSE 80

# Define environment variable
ENV NAME World


# Run start.sh when the container launches
CMD ["./start.sh"]
