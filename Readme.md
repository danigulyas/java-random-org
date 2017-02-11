# random_org_api

API client for using [random.org's api](http://api.random.org/), it's WIP.

### Synopsis.

```java
String myApiKey;
RandomApiContext context = new RandomApiContext(myApiKey);
Response<Result<String>> response = RandomApiClient.generateStrings(context, 10, 5);

if (response.isSuccessful()) {
    // check response.getError().getData() to find additional data coming from the server
    // check response.getError().getCode() to find the code, or .getMessage() for the message
}

List<String> randomStrings = response.getResult().getRandom().getData();
DateTime completionTime = response.getResult().getRandom().getCompletionTime();

// Each response comes with additional information about availability.
Integer bitsLeft = response.getResult().getBitsLeft();
Integer requestsLeft = response.getResult().getRequestsLeft();
Integer advisoryDelay = response.getResult().getAdvisoryDelay();
```