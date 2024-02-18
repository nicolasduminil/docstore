# CRUDing NoSQL Data with Quarkus: Part One - MongoDB
MongoDB is one of the most reliable and robust document oriented NoSQL database. It allows developers to provide feature-rich
applications and services, with various modern built-in functionalities, like machine learning, streaming, full-text search, etc.
While not a classical relational database, MongoDB is nevertheless used by a wide range of different business sectors 
and its use cases cover all kinds of architecture scenarios and data types. 

Document oriented databases are inherently different from the traditional relational ones, where data are stored in tables
and a single entity might be spread across several such tables. In contrast, document databases store data in separate 
unrelated *collections*, which eliminates the intrinsic heaviness of the relational model. However, given that real world's 
domain models are never so simplistic to consist in unrelated separate entities, document databases, including MongoDB, 
provide several ways to define multi-collection connections, similar to the classical databases relationships, but much 
lighter, more economic and more efficient. 

Quarkus, the "supersonic and subatomic" Java stack, is the new kid on the block that the most trendy and influential 
developers are desperately grabbing and fighting over. Its modern cloud-native facilities, as well as its contrivance, 
compliant with the best of the breeds standard libraries, together with its ability to build native executables, are seducing since
a couple of years Java developers, architects, engineers and software designers.

Here we cannot go, of course, into further details of neither [MongoDB](https://www.mongodb.com/) or 
[Quarkus](https://quarkus.io/) and the reader interested to learn more is invited to check the documentation on the 
official websites. What we are trying to achieve here is to implement a relatively complex use case consisting in 
CRUDing a *customer-order-product* domain model, using Quarkus and its MongoDB extension. And in an attempt to provide
a real-world inspired solution, we're trying to avoid simplistic and caricatural examples, based on a zero-connections 
single entity model,like there are dozens nowadays.

So, here we go !

## The Domain Model
The diagram below shows our *customer-order-product* domain model:

![The Domain Model](docstore-domain/docstore-model.png "The domain model")

As you can see, the central document of the model is `Order`, stored in a dedicated collection named `Orders`. An `Order`
is an aggregate of `OrderItem` documents, each of which points to its associated `Product`. An `Order` document 
references also the `Customer` who placed it. In Java, this is implemented as follows:

    @MongoEntity(database = "mdb", collection="Customers")
    public class Customer
    {
      @BsonId
      private Long id;
      private String firstName, lastName;
      private InternetAddress email;
      private Set<Address> addresses;
      ...
    }

The code above is showing a fragment of the `Customer` class. This is a POJO (*Plain Old Java Object*) annotated with the
`@MongoEntity` annotation which parameters define the database name and the collection name. The `@BsonId` annotation is
used in order to configure the document's unique identifier. While the most common use case is to implement the document's
identifier as an instance of the `ObjectID` class, this would introduce an useless tidal couplig between the MongoDB 
specific classes and our document. The other properties are the customer's first and last name, the email address and a 
set of postal addresses.

Let's look now at the `Order` document.

    @MongoEntity(database = "mdb", collection="Orders")
    public class Order
    {
      @BsonId
      private Long id;
      private DBRef customer;
      private Address shippingAddress;
      private Address billingAddress;
      private Set<DBRef> orderItemSet = new HashSet<>()
      ...
    }

Here we need to create an association between an order and the customer who placed it. We could have embedded the 
associated `Customer` document in our `Order` document, but this would have been a poor design because it would have 
redundantly defined twice the same object. We need to use a reference to the associated `Customer` document and we do 
this using the `DBRef` class. The same thing happens for the set of the associated order items where, instead of embedding
the documents, we use a set of references.

The rest of our domain model is quite similar and based on the same normalization ideas. For example, the `OrderItem`
document:

    @MongoEntity(database = "mdb", collection="OrderItems")
    public class OrderItem
    {
      @BsonId
      private Long id;
      private DBRef product;
      private BigDecimal price;
      private int amount;
      ...
    }

Here, we need to associate the product which makes the object of the current order item. Last but not least, we have the
`Product` document:

    @MongoEntity(database = "mdb", collection="Products")
    public class Product
    {
      @BsonId
      private Long id;
      private String name, description;
      private BigDecimal price;
      private Map<String, String> attributes = new HashMap<>();
      ...
    }

That's pretty much all as far as our domain model is concerned. There are however some additional packages that we need 
to look at: `serializers` and `codecs`.

In order to be able to be exchanged on the wire, all our objects, be them business or purely technical ones, have to be
serialized and deserialized. These operations are the responsibility of special designated components called 
*serializers* / *deserializers*. As we have seen, we're using the `DBRef` type in order to define association between 
different collections. Like any other object, a `DBRef` instance should be able to be serialized / deserialized. 

The MongoDB driver provides serializers / deserializers for the majority of the data types supposed to be used in the 
most common cases. However, for some reason, it doesn't provide serializers / deserializers for the DBRef type. Hence,
we need to implement our own and this is what the `serializers` package does. Let's look at them:

    public class DBRefSerializer extends StdSerializer<DBRef>
    {
      public DBRefSerializer()
      {
        this(null);
      }

      protected DBRefSerializer(Class<DBRef> dbrefClass)
      {
        super(dbrefClass);
      }

      @Override
      public void serialize(DBRef dbRef, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException
      {
        if (dbRef != null)
        {
          jsonGenerator.writeStartObject();
          jsonGenerator.writeStringField("id", (String)dbRef.getId());
          jsonGenerator.writeStringField("collectionName", dbRef.getCollectionName());
          jsonGenerator.writeStringField("databaseName", dbRef.getDatabaseName());
          jsonGenerator.writeEndObject();
        }
      }
     }

This is our `DBRef` serializer and, as you can see, it's a Jackson serializer. This is because the `quarkus-mongodb-panache`
extension, that we're using here, relies on Jackson instead of JSON-B.