# pingpong-backend

This is Pingpong game backend developed by Purushottam Kumar, Bangkok.
Below is the feature of this project.

1. Three hard coded login/password added with basic authentication and security.

2. Created a new protected end point called /ppmatch, that takes a list of strings as input and returns as json with below logic.
    a. "pong" if the input string is "ping" (the case does not matter)
    b. "ping" if the string is exactly "PoNg" (the case does not matter)
    c. "smash" if the string is "ping followed by one or more spaces followed by pong"(the case does not matter)
    d. a blank string if the input string is "SMASH" (the case does matter)
    e. "bad" for any other entries.

3. Created a new public end point called /highscore that displays the all time high score.

4. Created a new end public end point called /shutdown that saves the high scores to disk.

5. On application start up, reload the high score table saved on disk if it exists. 

6. Created a protected end point called /health with a fixed password "Health-check-Pwd#1" to check the application health status.

Tech used

1. Java Spring boot
2. H2 database

Below users are already registerd and saved into database on application startup.
1. user: Aadi1234, password: 
2. user: Puru1234, password: 
3. user: Archana123, password: 

How to Run the project

1. Right click on PingpongApplication.java file and run it as java application.
2. Import collection of APIs in your Postman.
3. Call the login API with any of the above user credentials.

Request:

    curl --location --request POST 'localhost:8081/login' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "userName": "Puru",
        "password": "puru123"
    }'

Response:

{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwidXNlck5hbWUiOiJQdXJ1IiwiaWF0IjoxNjY5Mzc2MDQxLCJzdWIiOiJVc2VyIiwiaXNzIjoiUHVydSJ9.Qdsy9PFXpbzhwy9GFSw4ymEY3SnIrunUnMF7NzKJ_fQ",
    "expiry": null,
    "id": 1,
    "username": "Puru"
}

4. Copy the token from login API response and set it as Authorization header while calling ppmatch API.

Request: 

    curl --location --request GET 'localhost:8081/ppmatch?input=ping,bad,pong,ping pong,SMASH' \
    --header 'Authorization: eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwidXNlck5hbWUiOiJQdXJ1IiwiaWF0IjoxNjY5Mzc2MDQxLCJzdWIiOiJVc2VyIiwiaXNzIjoiUHVydSJ9.Qdsy9PFXpbzhwy9GFSw4ymEY3SnIrunUnMF7NzKJ_fQ' \
    --data-raw ''

Response:

    {
    "recentScore": 1,
    "userName": "Puru",
    "resultDataList": [
        {
            "input": "ping",
            "output": "pong"
        },
        {
            "input": "bad",
            "output": "Bad"
        },
        {
            "input": "pong",
            "output": "ping"
        },
        {
            "input": "ping pong",
            "output": "Smash"
        },
        {
            "input": "SMASH",
            "output": " "
        }
    ]
}

5. Login with other Users and try to play the above game several times with different set of input strings.

6. Now Call highestScores API as below, it will show your the highest scores of all the player until now in descending order.

Request:
    curl --location --request GET 'localhost:8081/highscore' \
    --data-raw ''

Response:
    [
        {
            "userId": 1,
            "username": "Puru",
            "highestScore": 3
        },
        {
            "userId": 2,
            "username": "Addi",
            "highestScore": 2
        }
    ]

7. Call shutdown API as below, it will save all the user scores until now into a file and save it to local disk.

    curl --location --request GET 'localhost:8081/shutdown' \
    --data-raw ''

8. Close the applicaion and start it again.

9. Call highestscore API once again. We will still see the scores which we see ealier as we pesisted it into our disk.

10. Call health API as below to check the application health status as below. We added ealth-check-Pwd#1 as fixed authorization token.

    curl --location --request GET 'localhost:8081/health' \
    --header 'Authorization: Health-check-Pwd#1' \
    --data-raw ''