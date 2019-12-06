# Akka HTTP QuickStart

> AkkaHTTP QuickStart Example in Scala

> Modelled off AkkaHTTP Example at https://developer.lightbend.com/guides/akka-http-quickstart-scala/index.html

> This example uses ```akka.actor``` as opposed to ```akka.actor.typed``` approach in the lightbend example above

## Build Setup

``` bash
# install dependencies and run
sbt run
```

## Exercising the project

To get started, try registering and retrieving a few users. You simply need to know the appropriate parameters for the requests. In our example app, each user has a name, an age, and a city.

You pass request parameters in JSON format. While you could do this in a browser, it is much simpler to use one of the following:

- A command line tool, such as cURL.
- Browser add-ons such as RESTClient for Firefox or Postman for Chrome.

### Follow the steps appropriate for your tool:

- cURL commands
- Browser-based tools

#### cURL commands
Open a shell that supports cURL and follow these steps to add and retrieve users:

Copy and paste the following lines to create a few users (enter one command at a time):

```
curl -H "Content-type: application/json" -X POST -d '{"name": "MrX", "age": 31, "city": "Canada"}' http://localhost:8080/users

curl -H "Content-type: application/json" -X POST -d '{"name": "Anonymous", "age": 55, "city": "Iceland"}' http://localhost:8080/users

curl -H "Content-type: application/json" -X POST -d '{"name": "Bill", "age": 67, "city": "USA"}' http://localhost:8080/users
```

The system should respond after each command to verify that the user was created.

To retrieve all users, enter the following command:

```
curl http://localhost:8080/users
```

The system responds with the list of users:

```
{"users":[{"name":"Anonymous","age":55,"city":"Iceland"},{"name":"MrX","age":31,"city":"Canada"},{"name":"Bill","age":67,"city":"USA"}]}
```

To retrieve a specific user, enter the following command:

```
curl http://localhost:8080/users/Bill
```

The system should respond with the following:

```
{"name":"Bill","age":67,"city":"USA"}
```

Finally, it is possible to delete users:

```
curl -X DELETE http://localhost:8080/users/Bill
```

The response should be:

```
User Bill deleted.
```

#### Browser-based clients
Open a tool such as RESTClient or Postman and follow these steps to create and retrieve users:

To create users:

1. Select the POST method.
2. Enter the URL: http://localhost:8080/users
3. Set the Header to Content-Type: application/json.
4. In the body, copy and paste the following lines to create three users (send each payload separately). Note: for Postman, you might need to specify that you want to send raw data.

```
{"name": "MrX", "age": 31, "city": "Canada"}

{"name": "Anonymous", "age": 55, "city": "Iceland"}

{"name": "Bill", "age": 67, "city": "USA"}
```

The system should respond after each command to verify that the user was created.

To retrieve all users:

1. Select the GET method.
2. Enter the URL: http://localhost:8080/users
3. Send the request.

The system should respond with a list of users in JSON format.