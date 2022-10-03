# shorturl
A testing short url service

Please change h2 disk directory "C:/workspace/payroc/data/paydb" in application.properties to your local address -->
spring.datasource.url=jdbc:h2:file:C:/workspace/payroc/data/paydb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false

This is a spring boot embed tomcat application

After running the app it will be waiting for pre-generated short urls
to initiate the app and generate short urls use these urls:

http://localhost:8080/admin/init then http://localhost:8080/admin/genkey
Then go to the following address and enjoy the app:

http://localhost:8080/

You can speed up the project by increasing blocking queue size and bulk sinze in config
You can scale easily this app to multiple servers, all locks are managed by jpa



