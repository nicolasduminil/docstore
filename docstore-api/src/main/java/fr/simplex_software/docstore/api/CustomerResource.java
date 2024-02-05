package fr.simplex_software.docstore.api;

import fr.simplex_software.docstore.domain.*;
import fr.simplex_software.docstore.repository.*;
import io.quarkus.mongodb.rest.data.panache.*;

import java.math.*;

public interface CustomerResource extends PanacheMongoRepositoryResource<CustomerRepository, Customer, Long>
{
}
