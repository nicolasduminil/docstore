# CRUDing NoSQL Data with Quarkus: Part Two - Elasticsearch
In the [1st part](https://dzone.com/articles/cruding-nosql-data-with-quarkus-part-one-mongodb) of this short series, we 
have looked at MongoDB, one of the most reliable and robust document oriented NoSQL database. In this 2nd part we'll 
examine another quite unavoidable NoSQL database: Elasticsearch.

More than just a popular and powerful open source distributed NoSQL database, Elasticsearch is, first of all, a search 
and analytics engine. It is built on the top of Apache Lucene, the most famous search engine Java library, and is able 
to perform real-time search and analysis operations on structured and unstructured data. It is designed to handle 
efficiently large amount of data.

Once again, we need to disclaim that this short post is by no means an Elasticsearch tutorial and, accordingly,the 
inpatient reader is strongly advised to extensively use the official documentation, as well as the excellent book 
"Elasticsearch in Action" by Madhusudhan Konda (Manning 2023), in order to learn more about the product's architecture
and operations. Here we're just reimplement the same use case as previously, but using this time Elasticsearch insted of 
MongoDB.

So, here we go !

## The Domain Model
The diagram below shows our *customer-order-product* domain model:

![The Domain Model](docstore-domain/docstore-model.png "The domain model")

This diagram is the same as the one presented in the 1st part. Like MongoDB, Elasticsearch is also a document data store 
and, as such, it expects documents to be presented in JSON notation. The only difference being the fact that, in order to
handle its data, Elasticsearch needs to get them indexed. There are several ways that data can be indexed into an Elasticsearch
data store, for example piping them from a relational database, extracting them from a filesystem, streaming them from
a real-time source, etc. But whatever the ingestion method might be, it eventually consists in invoking the Elasticsearch
RESTful API via a dedicated client. There are two categories of such dedicated clients:
  - REST-based clients like `cUrl`, `Postman`, HTTP modules for Java, JavaScript, NodeJS, etc.
  - Progtramming language SDKs (*Software Development Kit*). Elasticsearch provides SDKs for all the most used programming languages, including but not limited to Java, Python, etc.

Indexing a new document with Elasticsearch means creating it using a POST request against a special RESTful API endpoint
named `_doc`. For example, the following request will create a new Elasticsearch index and store a new customer instance
in it.

    POST customers/_doc/
    {
      "id": 10,
      "firstName": "John",
      "lastName": "Doe",
      "email": {
        "address": "john.doe@gmail.com",
        "personal": "John Doe",
        "encodedPersonal": "John Doe",
        "type": "personal",
        "simple": true,
        "group": true
      },
      "addresses": [
        {
          "street": "75, rue Véronique Coulon",
          "city": "Coste",
          "country": "France"
        },
        {
          "street": "Wulfweg 827",
          "city": "Bautzen",
          "country": "Germany"
        }
      ]
    }

Running the request above, using `curl` or the Kibana console, as we'll see later, will produce the following result:

    {
      "_index": "customers",
      "_id": "ZEQsJI4BbwDzNcFB0ubC",
      "_version": 1,
      "result": "created",
      "_shards": {
        "total": 2,
        "successful": 1,
        "failed": 0
      },
      "_seq_no": 1,
      "_primary_term": 1
    }

This is the Elasticsearch standard response to a `POST` request. It confirms having created the index named `customers`,
having a new `customer` document, identified by an automatically generated ID, in this case  `ZEQsJI4BbwDzNcFB0ubC`. Other
interesting parameters appear here, like `_version` and, especially `_shards`. Without going too much in details here,
Elasticsearch creates indexes a logical collections of documents. Just like keeping paper documents in a filling cabinet, 
Elasticsearch keeps documents in an index. Each index is composed of *shards* which are physical instances of Apache 
Lucene, the engine behind the scene responsible for getting the data in or out the storage. They might be either *primary*,
storing documents, or *replicas* storing, as the name suggests, copies of primary shards. More on that in the Elasticsearch
documentation, for know we need to notice that our index, named `customers` is composed of two shards, which one, of 
course, primary.

A final notice: the `POST` request above doesn't mention the ID value as it is automatically generated. While this is
probably the most common use case, we could have provided our own ID value, in each case the HTTP request to be used isn't 
`POST` anymore, but `PUT`.

To come back on our domain model diagram, as you can see, its central document is `Order`, stored in a dedicated 
collection named `Orders`. An `Order`is an aggregate of `OrderItem` documents, each of which points to its associated 
`Product`. An `Order` document references also the `Customer` who placed it. In Java, this is implemented as follows:

    public class Customer
    {
      private Long id;
      private String firstName, lastName;
      private InternetAddress email;
      private Set<Address> addresses;
      ...
    }

The code above is showing a fragment of the `Customer` class. This is a simple POJO (*Plain Old Java Object*) having
properties like the customer's ID, first and last name, the email address and a set of postal addresses.

Let's look now at the `Order` document.

    public class Order
    {
      private Long id;
      private String customerId;
      private Address shippingAddress;
      private Address billingAddress;
      private Set<DBRef> orderItemSet = new HashSet<>()
      ...
    }

Here you can notice some differences compared to the MongoDB version. we need to create an association between an order and the customer who placed it. We could have embedded the 
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
extension, that we're using here, relies on Jackson. Perhaps, in a future release, JSON-B will be used but, for now, we're 
stuck with Jackson. It extends the `StdSerializer` class, as usual and serializes its associated `DBRef` object by using the 
JSON generator, passed as an input argument, to write on the output stream the `DBRef` components, i.e. the object ID, the 
collection name and the database name. For more information concerning the `DBRef` structure, please see the MongoDB documentation.

The deserializer is performing the complementary operation, as shown below:

    public class DBRefDeserializer extends StdDeserializer<DBRef>
    {
      public DBRefDeserializer()
      {
        this(null);
      }

      public DBRefDeserializer(Class<DBRef> dbrefClass)
      {
        super(dbrefClass);
      }

       @Override
       public DBRef deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException
       {
         JsonNode node = jsonParser.getCodec().readTree(jsonParser);
         return new DBRef(node.findValue("databaseName").asText(), node.findValue("collectionName").asText(), node.findValue("id").asText());
       }
    }

Here, the `deserialize()` method uses the JSON parser passed as an input argument to retrieve the JSON tree of the paylod, from 
which to construct the returned new `DBRef` object. In order to effecively use these customized serializers / deserializers, we need to
register them with our Jackson `ObjectMapper` instance. This is the responsibility of the class `JacksonConfig`, here below:

    @Singleton
    public class JacksonConfig implements ObjectMapperCustomizer
    {
      @Override
      public void customize(ObjectMapper objectMapper)
      {
        SimpleModule simpleModule = new SimpleModule();
        objectMapper.registerModule(simpleModule.addDeserializer(DBRef.class, new DBRefDeserializer()));
        objectMapper.registerModule(simpleModule.addSerializer(DBRef.class, new DBRefSerializer()));
      }
    }

This is pretty much all that it may be said as far as the serializers / deserializers are concerned. Let's move further to see what the
`codecs` package brings to us. 

Java objects are stored in a MongoDB database using the BSON (*Binary JSON*) format. In order to store information, the MongoDB
driver needs the ability of mapping Java objects to their associated BSON representation. It does that on the behalf of the `Codec` interface
which contains the required abstract methods for the mapping of the Java objects to BSON and the other way around. Implementing this interface,
one can define the conversion logic between Java and BSON and conversely. The MongoDB driver includes the required `Codec` implementation for
the most common types but, again, when it comes to `DBRef`, this implementation is, for some reason, only a dummy one, which raises 
`UnsupportedOperationException`. Having contacted the MongoDB driver implementers, I didn't succeed to find any other solution then implementing
my own `Codec` mapper, as shown by the class `DocstoreDBRefCodec`. For brevity reasons, we won't reproduce here this class' source code.

Once our dedicated `Codec` implemented, we need to register it with the MongoDB driver, such that it uses it when it comes to map DBRef types
to Java objects and conversely. In order to do that, we need to implement the interface `CoderProvider` which, as shown by the class 
`DocstoreDBRefCodecProvider`, returns via its abstract `get()` method, the concrete class responsible to perform the mapping, i.e. in our 
case `DocstoreDBRefCodec`. And that's all we need to do here as Quarkus will automatically discover and use our `CodecProvider` customized
implementation. Please have a look at these classes to see and understand how things are done.

## The Data Repositories
Quarkus Panache greatly simplifies the data persistence process by supporting both the *active record* and the *repository* design patterns
(https://www.martinfowler.com/eaaCatalog/activeRecord.html). Here, we'll be using the 2nd one. As opposed to similar persistence stacks, Panache
relies on the compile-time bytecode enhancements of the entities. It includes an annotation processor that is automatically performing these
enhancements. All that this annotation processor needs in order to perform its enhancements job is an interface like the one below:

    @ApplicationScoped
    public class CustomerRepository implements PanacheMongoRepositoryBase<Customer, Long>{}

The code above is all what you need in order to define a complete service able to persist `Customer` document instances. Your interface needs
to extend the `PanacheMongoRepositoryBase` one and to parameter it with your object ID type, in our case a `Long`. The Panache annotation processor
will generate out of it all the required endpoints required to perform the most common CRUD operations, including but not limited to saving, updating,
deleting, querying, paging, sorting, transaction handling, etc. All these details are fully explained [here](https://quarkus.io/guides/mongodb-panache#solution-2-using-the-repository-pattern).

## The REST API
In order for our Panache generated persistence service to become effective, we need to expose it through a REST API. In the most common case we have
to manually craft this API, together with its implementation, consisting in the full set of the required REST endpoints. This fastidious and repetitive
operation might be avoided by using the `quarkus-mongodb-rest-data-panache` extension, which annotation processor is able to automatically
generate the required REST endpoints, out of interfaces having the following pattern:

    public interface CustomerResource extends PanacheMongoRepositoryResource<CustomerRepository, Customer, Long> {}

Believe it if you want, this is all you need to generate a full REST API implementation with all the endpoints required to invoke the persistence
service generated previously by the `mongodb-panache` annotation processor extension. Now we are ready to build our REST API as a Quarkus microservice.
We chose to build this microservice as a Docker image, on the behalf of the `quarkus-container-image-jib` extension. By simply including the 
following maven dependency:

    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-container-image-jib</artifactId>
    </dependency>

the `quarkus-maven-plugin` will create a locally Docker image to run our microservice. The parameters of this Docker image are defined by the
`application.properties` file, as follows:

    quarkus.container-image.build=true
    quarkus.container-image.group=quarkus-nosql-tests
    quarkus.container-image.name=docstore-mongodb
    quarkus.mongodb.connection-string = mongodb://admin:admin@mongo:27017
    quarkus.mongodb.database = mdb
    quarkus.swagger-ui.always-include=true
    quarkus.jib.jvm-entrypoint=/opt/jboss/container/java/run/run-java.sh

Here we define the name of the new created Docker image as being `quarkus-nosql-tests/docstore-mongodb`. This is the concatenation of
the parameters `quarkus.container-image.group` and `quarkus.container-image.name` separated by a "/". The property `quarkus.container-image.build`
having the value `true` instructs the Quarkus plugin to bind the build operation to the `package` phase of `maven`. This way, simply 
executing a `mvn package` command, we generate a Docker image able to run our microservice. This may be tested by running the `docker images`
command. The property named `quarkus.jib.jvm-entrypoint` defines the command to be ran by the new generated Docker image. Here, the `quarkus-run.jar`
is the Quarkus microservice standard startup file used when the base image is `ubi8/openjdk-17-runtime`, as in our case. Other properties are
`quarkus.mongodb.connection-string` and `quarkus.mongodb.database = mdb` which define the MongoDB database connection string and the name of the
database. Last but not least, the property `quarkus.swagger-ui.always-include` includes the Swagger UI interface in our microservice space such
that to allow to test it easily. 

Let's see now how to run and test the whole stuff.

## Running and testing our microservices
Now that we looked at the details of our implementation, let's see how to run and test it. We chose to do it on the behalf of the `docker-compose`
utility. Here is the associated `docker-compose.yml` file:

    version: "3.7"
    services:
      mongo:
        image: mongo
        environment:
        MONGO_INITDB_ROOT_USERNAME: admin
        MONGO_INITDB_ROOT_PASSWORD: admin
        MONGO_INITDB_DATABASE: mdb
        hostname: mongo
        container_name: mongo
        ports:
          - "27017:27017"
        volumes:
          - ./mongo-init/:/docker-entrypoint-initdb.d/:ro
      mongo-express:
        image: mongo-express
        depends_on:
          - mongo
        hostname: mongo-express
        container_name: mongo-express
        links:
          - mongo:mongo
        ports:
          - 8081:8081
        environment:
          ME_CONFIG_MONGODB_ADMINUSERNAME: admin
          ME_CONFIG_MONGODB_ADMINPASSWORD: admin
          ME_CONFIG_MONGODB_URL: mongodb://admin:admin@mongo:27017/
      docstore:
        image: quarkus-nosql-tests/docstore-mongodb:1.0-SNAPSHOT
        depends_on:
          - mongo
          - mongo-express
        hostname: docstore
        container_name: docstore
        links:
          - mongo:mongo
          - mongo-express:mongo-express
        ports:
          - "8080:8080"
          - "5005:5005"
        environment:
          JAVA_DEBUG: "true"
          JAVA_APP_DIR: /home/jboss
          JAVA_APP_JAR: quarkus-run.jar

This file instructs the `docker-compose` utility to run three services:
  - a service named `mongo` running the Mongo DB 7 database;
  - a service named `mongo-express` running the MongoDB administrative UI;
  - a service named `docstore` running our Quarkus microservice.

We should note that the `mongo` service uses an initialisation script mounted on the `docker-entrypoint-initdb.d` directory of the
container. This initialisation script creates the MongoDB database named `mdb` such that it could be used by the microservices.

    db = db.getSiblingDB(process.env.MONGO_INITDB_ROOT_USERNAME);
    db.auth(
      process.env.MONGO_INITDB_ROOT_USERNAME,
      process.env.MONGO_INITDB_ROOT_PASSWORD,
    );
    db = db.getSiblingDB(process.env.MONGO_INITDB_DATABASE);
    db.createUser(
    {
      user: "nicolas",
      pwd: "password1",
      roles: [
      {
        role: "dbOwner",
        db: "mdb"
      }]
    });
    db.createCollection("Customers");
    db.createCollection("Products");
    db.createCollection("Orders");
    db.createCollection("OrderItems");

This is an initialisation JavaScript which creates an user named `nicolas` and a new database named `mdb`. The user has administrative
privileges on the database. Four new collections, named `Customers`, `Products`, `Orders` and, respectivelu `OrderItems`, are created
as well.

In order to test the microservices, proceed as follows:

  1. Clone the associated GitHub repository:
      
    $ git clone https://github.com/nicolasduminil/docstore.git

  2. Go to the project:

    $ cd docstore

  3. Build the project:

    $ mvn clean install

  4. Check that all the required Docker containers are running:

    $ docker ps

  5. Run the integration tests

    $ mvn -DskipTests=false failsafe:integration-test

This last command will run all the integration tests which should succeed. You can also use the Swagger UI interface for testing
purposes by fireing your prefered browser at http://localhost:8080/q:swagger-ui. Then, in order to test endpoints, you can use the
payload in the JSON files located in the `src/resources/data` directory of the `docstore-api` project.

Enjoy !