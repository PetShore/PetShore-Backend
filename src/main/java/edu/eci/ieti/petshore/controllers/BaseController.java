package edu.eci.ieti.petshore.controllers;


import edu.eci.ieti.petshore.model.Usuario;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin(origins = "*", methods= {RequestMethod.OPTIONS})
public class BaseController {
    public Usuario getCurrentUser(@AuthenticationPrincipal Usuario user) {
        return user;
    }
}
