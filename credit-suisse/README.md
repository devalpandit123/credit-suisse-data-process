Welcome!

How to execute the code?

Step 0: Please make sure that Java is installed in the system

Step 1: Store the log file by the name "logfile.txt". Save it at a specific location in the user's PC in the below format
```
{"id":"BMW", "state":"RUNNING", "type":"Sedan", "host":"12345", "timestamp":1491377495212}
{"id":"Audi", "state":"STATIONARY", "timestamp":1491377495213}
```

Step 2: Perform "mvn clean install at the project location"

Step 3: Perform "mvn spring-boot: run"

Step 4: Program starts at localhost with 9999. Hit the query
"http://localhost:9999/processLogFile?filePath={Put the location of file}"

For example:
"http://localhost:9999/processLogFile?filePath=D:/Personal/Credit-Suisse/logfile.txt"

Output:
1. You will see the contents of the logfile on the UI when you hit the URL.
2. CHeck logs to see the entries getting added in HSQL DB under "All entries: {}" log.
3. If you want to persist the entries, comment out the code to delete the entries from DB.
Code is available in DataProcessService.java file at line 57
4. To get the code coverage, please run the following command,

```
mvn jacoco:report
```
5. Now, go to project folder(where app is saved) in File Explorer and go to
```
{project}/target/site/jacoco/index.html
```

6. When you double-click the file, you will get an view of files covered by unit tests in the project.

7. Enjoy!