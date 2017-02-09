# random_org_api

API client for using [random.org's api](http://api.random.org/), it's WIP.

### Synopsis.

There's a RandomClientImpl class which you can initiate with a builder:
```java
RandomClient client = new RandomClientImpl.Builder().apiKey("00000000-0000-0000-0000-000000000123").build();
// the URL of the api is configured by default to https://api.random.org/json-rpc/1/invoke, you can set it before build
// by calling .url(String);
```

You can get a `Response<T>` (where T is the type of the variables in data from the API's output) by calling any of the
methods listed on http://api.random.org/. Only `generateIntegers()` is implemented yet.
```java
Response<Integer> response;

try {
 response = client.generateIntegers();
} catch (JSONRPC2SessionException e) {
    //Session exception, see JSONRPC2's documentation
} catch (JSONRPC2Error e) {
    //Problem in JSONRPC2
} catch (IOException e) {
    //Problem during parsing JSON
}

List<Integer> randomIntegers = response.getRandom().getData();
```