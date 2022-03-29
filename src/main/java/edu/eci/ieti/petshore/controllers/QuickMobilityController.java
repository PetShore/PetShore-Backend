package edu.eci.ieti.petshore.controllers;


import edu.eci.ieti.petshore.model.Barrio;
import edu.eci.ieti.petshore.model.Producto;
import edu.eci.ieti.petshore.model.Usuario;
import edu.eci.ieti.petshore.persistence.PetShoreException;
import edu.eci.ieti.petshore.services.PetShoreServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@RequestMapping("/petshore")
@CrossOrigin(origins = "*")
public class QuickMobilityController extends BaseController{
    @Autowired
    PetShoreServices petShoreServices = null;


    @RequestMapping("/helloworld")
    public String helloWorld(){
        return petShoreServices.helloWorld();
    }

    @RequestMapping(value ="/product/{username}",method = RequestMethod.GET)
    public ResponseEntity<?> getProducts(@PathVariable String username){
        try{
            Collection<Producto> productoCollection = petShoreServices.getProductos(username);
            return new ResponseEntity<>(productoCollection, HttpStatus.ACCEPTED);
        }
        catch (Exception e) {
            Logger.getLogger(QuickMobilityController.class.getName()).log(Level.SEVERE,null,e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value="/product/add/{username}", method= RequestMethod.POST)
    public ResponseEntity<?> addProductoUsuario(@RequestBody Producto producto, @PathVariable String username){
        try{
        	petShoreServices.addProductoUsuario(username, producto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            Logger.getLogger(QuickMobilityController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/product/update/{username}",method = RequestMethod.PUT)
    public ResponseEntity<?> updateProducto(@RequestBody Producto producto, @PathVariable String username){
        try{
            Usuario usuario = getCurrentUser(petShoreServices.getUserByUsername(username));
            petShoreServices.updateProducto(producto,usuario);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            Logger.getLogger(QuickMobilityController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value ="/neighborhood",method = RequestMethod.GET)
    public ResponseEntity<?> getBarrios(){
        try{
            List<Barrio> barrioCollection = petShoreServices.getBarrios();
            return new ResponseEntity<>(barrioCollection,HttpStatus.ACCEPTED);

        } catch (Exception e){
            Logger.getLogger(QuickMobilityController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value="/neighborhood", method=RequestMethod.POST)
    public ResponseEntity<?> addBarrio(@RequestBody Barrio barrio){
        try{
            System.out.println("w");
            petShoreServices.addBarrio(barrio);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            Logger.getLogger(QuickMobilityController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/score/{nameVendedor}/{nameCliente}/{calificacion}", method=RequestMethod.POST)
    public ResponseEntity<?> addCalificacion(@PathVariable String nameConductor,@PathVariable String namePasajero,@PathVariable double calificacion){
        try{
        	petShoreServices.addCalificacion(nameConductor,namePasajero,calificacion);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            Logger.getLogger(QuickMobilityController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/userStatus/{username}",method = RequestMethod.GET)
    public ResponseEntity<?> getUserState(@PathVariable String username){
        try {
            String state = petShoreServices.getUserStatus(username);
            return new ResponseEntity<>(state,HttpStatus.OK);
        } catch (PetShoreException e) {
            Logger.getLogger(QuickMobilityController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value="/score/average/{username}/{type}",method=RequestMethod.GET)
    public ResponseEntity<?> getAverage(@PathVariable String username,@PathVariable String type){
        try {
            double average = petShoreServices.getAverage(username,type);
            return new ResponseEntity<>(average,HttpStatus.OK);
        } catch (PetShoreException e) {
            Logger.getLogger(QuickMobilityController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/user/update",method=RequestMethod.PUT)
    public ResponseEntity<?> getAverage(@RequestBody Usuario usuario){
        try {
        	petShoreServices.updateUserBasicInfo(usuario);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PetShoreException e) {
            Logger.getLogger(QuickMobilityController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/shop/seller/{usernameSeller}",method = RequestMethod.GET)
    public ResponseEntity<?> getShopBySeller(@PathVariable String usernameSeller){
        try {
            return new ResponseEntity<>(petShoreServices.getShopSeller(usernameSeller),HttpStatus.OK);
        } catch (PetShoreException e) {
            Logger.getLogger(QuickMobilityController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/shop/client/{usernameClient}")
    public ResponseEntity<?> getShopByClient(@PathVariable String usernameClient){
        try {
            return new ResponseEntity<>(petShoreServices.getShopClient(usernameClient),HttpStatus.OK);
        } catch (PetShoreException e) {
            Logger.getLogger(QuickMobilityController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
