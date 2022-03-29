package edu.eci.ieti.petshore.services;

import edu.eci.ieti.petshore.model.Usuario;
import edu.eci.ieti.petshore.persistence.PetShoreException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServices extends UserServices{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    public void addUser(Usuario usuario) {

        petShorePersistence.saveUser(usuario);
    }



    public boolean login(String username,String password) throws PetShoreException {
        Usuario user = petShorePersistence.getUserByUsername(username);
        return bCryptPasswordEncoder.matches(password,user.password);

    }


}

