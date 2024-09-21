## How to run the applications

### java
following code snippits are reuquired to execute them
- ```docker image build -t java-parser:latest .```
- ```docker run -v %cd%/input.xml:/app/input.xml -v %cd%/result:/app/result --rm java-parser input.xml ./result/output-java.xml```

- ```docker run -v %cd%/input.xml:/app/input.xml -v %cd%/result:/app/result --rm java-parser --input input.xml --output ./result/output-java.xml -q //user/username```

### javascript
following code snippits are required to execute them
- ```docker image build -t node-parser:latest .``` 
- ```docker run -v %cd%/input.xml:/app/input.xml -v %cd%/result:/app/result --rm node-parser input.xml ./result/output-javascript.xml```

- ```docker run -v %cd%/input.xml:/app/input.xml -v %cd%/result:/app/result --rm javascript-parser --input input.xml --output ./result/output-javascript.xml --query //user/username```

### python
following code snippits are required to execute them
- ```docker image build -t python-parser:latest .```
- ```docker run -v %cd%/input.xml:/app/input.xml -v %cd%/result:/app/result --rm python-parser input.xml ./result/output-python.xml```

- ```docker run -v %cd%/input.xml:/app/input.xml -v %cd%/result:/app/result --rm python-parser --input input.xml --output ./result/output-python.xml --query //user/username ```