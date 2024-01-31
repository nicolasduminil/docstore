package fr.simplex_software.docstore.repository;

import fr.simplex_software.docstore.domain.*;
import io.quarkus.mongodb.panache.*;
import jakarta.enterprise.context.*;

import java.math.*;

@ApplicationScoped
public class CustomerRepository implements PanacheMongoRepositoryBase<Customer, BigInteger>
{
}
