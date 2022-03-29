package edu.eci.ieti.petshore.controllers;


import edu.eci.ieti.petshore.model.TokenUser;
import edu.eci.ieti.petshore.model.Usuario;
import edu.eci.ieti.petshore.persistence.PetShoreException;
import edu.eci.ieti.petshore.services.AuthServices;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthQuickMobilityController extends BaseController {
	@Autowired
	private AuthServices authServices;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody TokenUser tokenUser) {
		try {
			if (authServices.login(tokenUser.getUsername(), tokenUser.getPassword())) {
				String token = getJWTToken(tokenUser.getUsername());
				tokenUser.setToken(token);
				tokenUser.setPassword(null);
			} else {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		} catch (PetShoreException e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(tokenUser, HttpStatus.OK);
	}

	@RequestMapping(value = "/loggedUser/{username}", method = RequestMethod.GET)
	public ResponseEntity<?> isLogged(@PathVariable String username) throws PetShoreException {
		Usuario user = authServices.getBasicInfoUser(username);
		return new ResponseEntity<>(getCurrentUser(user), HttpStatus.OK);

	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public ResponseEntity<?> addUser(@RequestBody Usuario usuario) {

		authServices.addUser(usuario);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private String getJWTToken(String username) {
		String secretKey = "quickmobilityAuth";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts.builder().setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Bearer " + token;
	}

}
