package edu.eci.ieti.petshore.services;

import edu.eci.ieti.petshore.model.Usuario;
import edu.eci.ieti.petshore.persistence.PetShoreException;
import edu.eci.ieti.petshore.persistence.PetShorePersistence;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServices {
    @Autowired
    PetShorePersistence petShorePersistence;
    public Usuario getUserByUsername(String username) throws PetShoreException {
        return petShorePersistence.getUserByUsername(username);
    }
    
    public Usuario getBasicInfoUser(String username) throws PetShoreException {
    	Usuario user = petShorePersistence.getUserByUsername(username);
        // not show vars
        user.password = null;
        user.userId = null;
        return user;
    }
}
