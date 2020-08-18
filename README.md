# Kommerc - Simple ecommerce PoC

## How to Compile

The build system used is Gradle. This project can be built using the provided gradle wrapper:

```
$ ./gradlew build
```

## How to Test

The previous command actually also runs tests. However if you'd like to run them separately, you can do so with:

```
$ ./gradlew test
```

## How to Run

The server can be run, by default on port 8080 (Configurable in `resources/application.conf`), with the following 
command:

```
$ ./gradlew run
```

## Developer notes

I decided to pull those notes out of their original place (Code comments) as they are only relevant to this PoC and 
would otherwise pollute the code making it less readable.

I decided to try ktor, it was the first time I developed with this framework but I quite liked it, being lightweight and
not containing too much abstractions or "magic automation" like those often found in bigger, more popular frameworks.
As usual with Kotlin, the integration with the IDE (IntelliJ) is fantastic.

### The Product class

- I opted to deviate slightly from the requirements (String) and use an Int for the sku as I would not see a need of 
the additional space, at least not in this PoC.
- Price and currency in a scaled up version of this service might be made into a map, allowing the existence of multiple
currencies. Currently, and for the purpose of this evaluation only, I'm assuming a single currency. Implementing
multiple currencies would fairly increase the complexity of the implementation.
- Also, I'm making use of Java's Currency class which might be limiting in the future in case we would like a broader
support for currencies, e.g. by supporting cryptocurrencies.

### The discount class

- A similar note should be made on the multiple currencies as we did for the product class.

### MockCatalogueRepository

- In a production environment I would never use this. A relational database would be perfect to be connected with a 
repository and if we would need mocking or demo data I would make it configurable by reading it off a document.

### CheckoutRequest

- I decided to make a proper class instead of using a simple list of skus, mainly for future extensibility.