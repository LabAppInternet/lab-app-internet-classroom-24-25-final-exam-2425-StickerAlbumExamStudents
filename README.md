# Exam course 2024-2025

## Before you begin
* Read this file carefully
* BEFORE **touching** the code, run the application and see it working on your computer. There is a set of tests for each question in a separate file. They should only succeed once you implement the corresponding exercise.
* You have example calls in the file "resources/calls.http," but it might be easier to use the **swagger** documentation at http://localhost:8080/swagger-ui.html
* You have the h2-console enabled at http://localhost:8080/h2-console. The database is in memory and is created every time you run the application.
* When attempting to answer the questions, modify the code in small steps and try the application (run it) after every step. In this way, it is easier to track possible errors
* A code that doesn't compile or run will be marked with zero points
* All the questions are independent and can be answered in any order. So, if you get stuck on a question, attempt to answer another one.
* Read the questions and the TODOs
* In the code, you'll see **TODO**s where you must insert new code. TODOs explain what you need to do and may contain some clues. TODOs are numbered according to the question number. When a question has more than one TODO, they are
  numbered TODO X.1, TODO X.2, .., where X is the question number. There are few TODOs that don't need any code, and they are there to explain code relevant to the question (and its answer)
* Please, **don't delete the TODOs** from the code.
* See that the file data.sql contains some data to test the application. If you change it, some tests may fail, so please **don't change it**.

#### TODOs in Intellij
In Intellij, you can see all the TODOs in the TODO tool window. You can open it using the menu View -> Tool Windows -> TODO.
Using this tool window to track the TODOs you must do is a good idea. You can also navigate to the TODOs in the code from this window.

## The domain: Albums, Stickers, Collections and Auctions
This domain is familiar to you, and it has advantages and disadvantages at the same time. It is an advantage because you know the domain and what to expect, but it could also be a disadvantage because
it may be implemented differently than your own project. Please read the code carefully and understand the domain before you start coding.

+ Users are called *Collectors*
+ There are *Albums*, *Sections* and *Stickers*. I believe my code is the same as yours.
+ There are *Collections* that consist of a set of stickers that a collector has of a given album.
```java
@Entity
public class Collection {

    @EmbeddedId
    CollectionPK collectionPK;

    @ManyToOne
    @JoinColumn(name="collector_id", referencedColumnName="id")
    @MapsId("collector_id")
    private Collector collector;

    @ManyToOne
    @JoinColumn(name="album_id", referencedColumnName="id")
    @MapsId("album_id")
    private Album album;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "collection")
    List<HasSticker> ownedStickers;

    //and more
```
The primary key is embedded in the class CollectionPK and contains the id of the collector and the Album. The Collection also has a Collector and an Album as attributes.
For the attributes *collector_id* and *album_id* not to be repeated in the database table we use the @MapsId annotation. This is a JPA annotation that tells the JPA provider to use the id of the Album and the Collector as the id of the Collection.

+ A *Collection* has a list of *HasSticker*. A *HasSticker* is a class that represents a sticker that a collector has in his collection with the number of copies.
```java
@Entity
public class HasSticker {

    @EmbeddedId
    HasStickerPK hasStickerPK;

    @ManyToOne
    @JoinColumn(name="sticker_id", referencedColumnName="id")
    @MapsId("sticker_id")
    private Sticker sticker;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="collector_id", referencedColumnName="collector_id", insertable = false, updatable = false),
            @JoinColumn(name="album_id", referencedColumnName="album_id", insertable = false, updatable = false)
    })
    private Collection collection;
    
    // and more
```
The primary key is embedded in the class HasStickerPK and contains the id of the sticker and the id of the collection (that in turn is a composed primary key).
The HasSticker also has a Sticker and a Collection. The @MapsId annotation is used to avoid repeating the *sticker_id* in the database table, and the
@JoinColumns annotation is used to avoid repeating the *collector_id* and *album_id* in the database table.

+ The association between *Collection* and *HasSticker* is a @OneToMany relationship. Following the Vlad Mihaela's advice [@OneToMany](https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/), we implement it with a
  bidirectional association in order to avoid extra updates when adding and deleting HasStickers in to a Collection. Note that, in JPA, the owner of the relationship
  is the entity that contains the foreign key. In this case, the HasSticker entity contains the foreign key references to both the Collection and Sticker entities.
  The Collection entity has a List<HasSticker> with the mappedBy attribute, indicating that the HasSticker entity owns the relationship. It also indicates that when
  HasStickers are added, modified, or deleted in its list; the HasSticker must be inserted, updated, or deleted in the database.

  The @JoinColumns annotation in the HasSticker entity is necessary because the HasSticker entity has a composite primary key that includes foreign keys from both the Collection
  and Sticker entities. The Collection entity itself has a composite primary key consisting of collector_id and album_id. Therefore, to establish the relationship
  between HasSticker and Collection, we must specify collector_id and album_id as foreign keys. It also indicates that these columns are not insertable or updatable,
  because they are part of the primary key of the HasSticker entity. It tells not to repeat the attributes in the database table.

  One consequence of *Collection* having a *List<HasSticker>* is that the logic of collecting stickers is in the Collection entity. In fact, the *Collection* 
  does not have any public method that needs as input or returns a HasSticker.  

+ The *ExchangeOfStickers* is a class representing an exchange of stickers proposal from one collector (origin) to another (destination). The proposal
  can be accepted or rejected by the destination user. The exchange must be from stickers of the same Album. The constructor of the ExchangeOfStickers calculates
  the maximum number of stickers that can be exchanged, and the actual stickers to exchange. Note that the class *Collection* has a method that calculates the stickers that could be exchanged.
  Both classes use the *ExchangeableStickers* record, which contains stickers that can be exchanged between two collections.

+ In this application, one collector, can make an **auction (*BlindAuction*)** of a sticker he has. The auction has a starting date, an end date, and an initial price. Other collectors can bid for the sticker.
  A bid must offer a price equal to or higher than the current price of the auction. One can bid only when the auction is open (between the starting and end date).
  The auction is called blind because one bids without knowing the other bids. In this case, the association between *BlindAuction* and *Bid* is a @ManyToOne relationship
  because an auction could have thousands of bids. One consequence of the auction not having a list of bids is that most of the logic is in the service layer.

+ See also that in the application layer there is a class called AuctionClosedWithWinner that has a method that runs every 5 seconds. The method
calls the BlindAuctionService to calculate the winner of the auction. When the auction ends (the end date is passed), the auction is closed. Then,
when the winner is calculated, we say that the auction is finished. The winner is the bid of the collector that offered the highest price. In case of a tie, the winner 
is randomly chosen among the oldest bids. Note that the bid's date is a LocalDate so that several bids can have the same date.


### TODO 1 (1 point):
We want to be sure that the attributes when creating application objects are correct. Namely:
+ Album:
  + All the parameters in the AlbumCommand are set (not null)
  + The name begins with a capital letter and is larger than 5 characters
  + The editor is not empty
  + The beginning date is today or after today
  + The end date is after today
+ Collection
  + All the parameters in the CollectionCommand are set
  + The beginning date is today or after today
  + The end date is after today
+ Add Sticker to a collection
  + All the parameters in the AddStickerCommand are set
  + The sticker number is greater than 0 (positive)
  + The number of stickers is positive
+ Blind Auction
  + All the parameters in the BlindAuctionCommand are set
  + The initial price is at least 1
  + The end date is after today
+ Add a Bid to an Auction
  + All the parameters in the AddBidCommand are set
  + The bid amount is at least 1
  
When the attributes are not correct, the system must return a message with all the errors, and an
HTTP status code 400 (BAD REQUEST). When errors are correctly validated and handled the tests in TODO1_Tests should pass. Be aware
that error messages must be the same as the ones in the tests (TODO1_Tests). After implementing this, the tests in the file TODO1_Tests should pass.

### TODO 2 (1 point):
The services in the application, when object ids are given, check that the object exists in the database.
If not, they throw an exception of type XXXDoesNotExistException. All these exceptions are of type RuntimeException.
Make sure that the exceptions are treated and the system returns a message with the error and HTTP status code 404 (NOT FOUND).
After implementing this, the tests in the file TODO2_Tests should pass. See that the tests check that messages are the same as the ones in the exceptions.

### TODO 3 (1 point):
The services in the application, when there are domain errors (for example, a bid is lower than the current price of the auction),
throw an exception of type IllegalStateException with a message describing the error. Make sure that the exceptions are treated
and the system returns a message with the error contained in the exception where the text "Domain error. " is
appended at the beginning. Also, it must return an HTTP status code 400 (BAD REQUEST). After implementing this, the tests in the file TODO3_Tests should pass.

### TODO 4 (2 points):
The BlindAuctionService has the method *getWinnerBidOfAuction* that should return the winner bid of an auction. As it is now, it
returns the oldest bid of the auction. Note that the bid's date is a LocalDate, so several bids can have the same date.
Change the method so that it returns the bid with the highest. To do this, you need to do two steps:
1. Rewrite the query in the BlindAuctionRepository so that it returns the bids with the highest amount ordered by their date (TODO 4.2)
2. Change the method in the BlindAuctionService so that it returns the oldest bid of the list of bids returned by the repository. In case, there are ties it should randomly choose one. (TODO 4.1, 4.3)

For example, if the bids are (id, amount, date): (1, 10, 01-12-2024), (2, 12, 02-12-2024), (3, 12, 02-12-2024), (4, 12, 03-12-2024) (5, 11, 02-12-2024).
+ The query (TODO 4.2) should return these bids in this order: (2, 12, 02-12-2024), (3, 12, 02-12-2024), (4, 12, 03-12-2024)
+ The method (TODO 4.1, 4.3) should return one of the bids with id 2, or 3.
After implementing this, the tests in the file TODO5_Tests should pass.

### TODO 5 (2 points):
A collector (origin) can **propose an exchange of stickers** to another collector (destination). Later, the user that receives the proposal can accept or reject it.
You should write a method to get the proposed exchanges that a given collector has received. The method should return a list of *ExchangeOfStickersDTO*.
Note that it contains two lists of *StickerInExchangeDTO*: the ones that the other collector is offering and the ones that the other collector is receiving.
The query should return the exchanges ordered by the date of the exchange.
Follow the steps in the TODOs 5.1, 5.2, 5.3, 5.4
After implementing this, the tests in the file TODO5_Tests should pass. You must uncomment the tests in the file.

### TODO 6 (2 points):
A collector wants to know the stickers that he has got in his collection with the quantities. Write a method that returns the stickers that the user has in his collection.
Follow the steps in the TODOs 6.1, 6.2, 6.3, 6.4
After implementing this, the tests in the file TODO6_Tests should pass. You must uncomment the tests in the file.

### TODO 7 (1 point):
Implement a query that returns a ranking of collectors ordered by total number of sticker's copies they have in their collections of active albums. The first in the ranking is the collector with the most copies.
It should return a list of *CollectorStickersQuantityDTO*. See TODO 7.1 and TODO 7.2.
After implementing this, the tests in the file TODO7_Tests should pass.