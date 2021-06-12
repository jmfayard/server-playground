# Getting Started

## Running the app
```
./gradlew bootRun
```
Starts the web app on port 8080.

## Querying the Graphql

Navigate to the [GraphiQL UI](http://localhost:8080/index.html)

Example query:

```graphql
query {
  dog {
    fact
    length
    latency
  }
  cat {
    fact
    length
    latency
  }
}
```
