# Use the official Python 3.12 base image
FROM python:3.12

# Set the working directory
WORKDIR /app

# Copy the rest of your application code
COPY ./parse.py .
COPY ./requirements.txt .

# Install dependencies
RUN pip install -r requirements.txt

# Define the command to run your application (adjust as needed)
ENTRYPOINT ["python", "parse.py"]
