# Use the official Node.js base image
FROM node:20.17.0

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of your application code
COPY ./parse.js .

# Expose the port your app runs on (if applicable)
# EXPOSE 3000

# Define the command to run your application
ENTRYPOINT [ "node", "parse.js" ]
