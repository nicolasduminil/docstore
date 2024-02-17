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