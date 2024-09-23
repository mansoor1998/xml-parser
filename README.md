### Overview

This porject is based on XML parser that use XML parsing and XPath querying with three different programming languages, i.e. Java, Javascript and Python. 

Each language comes with a Dockerfile that creats an image of the program and runs the container with necessary volumes to provide the input and output path.

Each language has 4 main function which are read the xml file, writing the xml file, reading the arguments from CMD/Terminal, query the xml document.

For convinience, the project comes with a docker compose file which can be used via the command ```docker compose up```. It creats an image, executes the container for each language and prints the query result alongside read and uploading file in result folder.

The project has a input.xml file in the root path which is taken from the research paper "https://dl.acm.org/doi/abs/10.1145/3597503.3639208". The query option that is passed in docker compose is also taken from the same research paper.

### Instructions on how to create an image and run containers

execute the docker commands in the root directory.

#### Python

Command to create an image ```docker image build -t python-parser:latest ./python```

Command to create a container from relevant image ```docker run -v %cd%/input.xml:/app/input.xml -v %cd%/result:/app/result --rm python-parser --input input.xml --output ./result/output-python.xml -q "//*[@id*(-1)<2]"```

#### Java
Command to create an image ```docker image build -t java-parser:latest ./java```
 
How to create a container with volume ```docker run -v %cd%/input.xml:/app/input.xml -v %cd%/result:/app/result --rm java-parser --input input.xml --output ./result/output-java.xml -q "//*[@id*(-1)<2]"```

#### Javascript
Command to create an image ```docker image build -t javascript-parser:latest ./javascript```

How to create a container with volume ```docker run -v %cd%/input.xml:/app/input.xml -v %cd%/result:/app/result --rm javascript-parser --input input.xml --output ./result/output-javascript.xml -q "//*[@id*(-1)<2]"```