# In-memory URL Shortener (IMUS)
An in-memory URL shortener service in Java
 
# How to run it locally
* You will need **Java 8** and **Maven** installed.
* Execute `mvn clean package && java -jar target/url-shortener-1.0.0.jar`
* You can access the application in [http://localhost:8080](http://localhost:8080) 

## How to create a new shortened URL
1. Send a POST request to [http://localhost:8080](http://localhost:8080). You have several ways to do it:
 * E.g. using a curl command like: 
~~~
curl -isbX POST \
    http://localhost:8080 \
    -H 'content-type: application/json' \
    -d '{
        "clientId": "test",
        "urlStr": "http://www.king.com"
    }'
~~~
 * Another option is to use some kind of web browser addon like Postman.
2. You will receive a plain text answer with the shortened URL. You can use it directly to be redirected to your original full URL.

## How to use a shortened URL
You have two options to check:
* Either you introduce the shortened URL `http://localhost:8080/ef919221` in a browser.
* or, from CLI, type something like `curl -v http://localhost:8080/ef919221` and verify the 302 redirection and the shown location.

## How to get hit count of a shortened URL
You have two options to check:
* Either you introduce the shortened URL `http://localhost:8080/hitCount/ef919221` in a browser,
* or, from CLI, type something like `curl -v http://localhost:8080/hitCount/ef919221`.