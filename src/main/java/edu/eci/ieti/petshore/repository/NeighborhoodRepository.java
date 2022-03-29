package edu.eci.ieti.petshore.repository;

import edu.eci.ieti.petshore.model.Barrio;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NeighborhoodRepository extends MongoRepository<Barrio,String> {
}
