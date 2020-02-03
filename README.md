# metrics-service
Simple API that can collect data for metrics


## Running project (Requirements)
  1. Must have maven installed
  2. Must have Docker installed

## Options for running this project

1. Run the 'build' bash script located in the project root

2. Manually 
  a. Run 'mvn clean package'
  b. Run 'docker build -t metrics-service .'
  c. Run 'docker run -p 8080:8080 metrics-service'

3. Load project on IntelliJ and run as a springboot profile using the default profile

4. Run your classic maven command 'mvn spring-boot:run'
