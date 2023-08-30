# <img src="https://imgur.com/ehhdBJl.png" height="32"/> ankiconnect-java

[![](https://github.com/caseyscarborough/ankiconnect-java/actions/workflows/gradle.yml/badge.svg)](https://github.com/caseyscarborough/ankiconnect-java/actions/workflows/gradle.yml)

This is a Java library used to interact with [AnkiConnect](https://foosoft.net/projects/anki-connect/).

## Adding the Dependency

Add the dependency using Jitpack:

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    // Specific version
    implementation 'com.github.caseyscarborough:ankiconnect-java:<version>'
    
    // Master branch (latest)
    implementation 'com.github.caseyscarborough:ankiconnect-java:master-SNAPSHOT'
}
```

## Usage

All method names in the library correspond to the actions in the AnkiConnect API and documentation to keep
the usage straightforward.

### Creating a Client

```java
// Default client using localhost:8765
AnkiConnect ankiconnect = new AnkiConnect();

// Specify the host and port
AnkiConnect ankiconnect = new AnkiConnect("localhost", 8765);

// Specify an API key
AnkiConnect ankiconnect = new AnkiConnect("localhost", 8765, "my-api-key");
```

### Find Cards

Find cards based on a search query. The search query is the same as the ones used in the Anki desktop client.

```java
List<Long> ids = ankiconnect.findCards("deck:Default");
```

More information about the search query can be found [here](https://docs.ankiweb.net/searching.html).

### Cards Info

Retrieve information about a list of cards.

```java
List<Long> ids = ankiconnect.findCards("is:suspended");
List<Card> cards = ankiconnect.cardsInfo(ids);
```

### Reviews for Cards

Retrieve a list of reviews for one or more cards.

```java
// Get reviews for a single cards
List<Review> reviews = ankiconnect.getReviewsOfCard(124248248824L);

// Get reviews for multiple cards
List<Long> ids = ankiconnect.findCards("deck:*");
Map<Long, List<Review>> reviewMap = ankiconnect.cardsReviewInfo(ids);
```
