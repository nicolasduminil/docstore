package fr.simplex_software.docstore.repository;

import fr.simplex_software.docstore.domain.*;
import io.quarkus.mongodb.panache.*;
import jakarta.enterprise.context.*;

import java.math.*;

@ApplicationScoped
public class OrderRepository implements PanacheMongoRepositoryBase<Order, Long>
{
}
