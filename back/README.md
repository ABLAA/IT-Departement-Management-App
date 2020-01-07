IT department management application FOR the back: 
-you need mysql db you can tune the configuration in src/main/resources/application.properties 

After running:

    you need to insert manually a department
    the sign in will create a department Adminstrator
    the login will give you a jwt
    use this url http://localhost:8080/swagger-ui.html for documentation
    to authorize with swager type "Bearer -jwt-"
    every thing works fine except absence by date
    the error messages are ambigous I'm sorry for that
    I didn't implement soap
    there's no upadates nor deletes for the moment

Docker:
1- make sure that you have docker and docker compose
2- make sure that port 8080 and 3306 are free else where stop services occuping those ports
4- change directory to the app folder where docker-compose.yml exists
3- RUN "docker-compose up --build"