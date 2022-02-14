# spring-swagger-demo
Advertiser API project

TO RUN:
1. Import as a Gradle project
2. In the root folder, run with command:
gradle bootRun
3. Access this URL in your browser to see the Swagger UI of the exposed API endpoints:
http://localhost:8080/swagger-ui.html

TROUBLESHOOTING GRADLE:
If facing Gradle issues on MAC:
    a. Manually download Gradle to /opt/gradle
    b. Update PATH by adding this line to .bash_profile
        export PATH=$PATH:/opt/gradle/gradle-4.10/bin
    c. Save the changes with command:
        source .bash_profile
    d. Re-import the project in IntelliJ and point to the manually installed Gradle path during set-up

DATABASE:
1. Open this URL
http://localhost:8080/h2-console
2. For the JDBC URL use
jdbc:h2:mem:testdb
3. Click Connect
4. Click ADVERTISERS and click Run
5. Notice that the values that are already loaded in memory are because of these files located in /resources
schema.sql, data.sql


TEST WITH JUNIT:
1. Run this command in workspace terminal
./gradlew clean test --info

DEV INFO

TO CREATE A NEW REPO:
1. In root folder, initialize repo with this command:
git init

TO COMMIT VIA GIT:
1. Clone repo with command:
git clone git@github.com:KentTheCreator/spring-swagger-demo.git
2. In root folder, set origin with command:
git remote add origin git@github.com:{username}/spring-swagger-demo.git
3. Make changes if a contributor
4. Email and Username have to be setup with commands:
git config --global user.name "name"
git config --global user.email "email"
5. Save changes with commands:
git add .
git commit -m "detailed commit message"
git push -u origin master
