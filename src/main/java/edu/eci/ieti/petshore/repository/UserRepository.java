package edu.eci.ieti.petshore.repository;

import edu.eci.ieti.petshore.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Usuario,String> {
    public Usuario findByUsername(String username);
}
