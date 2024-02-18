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
references also the `Customer` who placed it.   