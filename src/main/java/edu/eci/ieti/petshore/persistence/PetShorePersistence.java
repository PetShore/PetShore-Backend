package edu.eci.ieti.petshore.persistence;


import edu.eci.ieti.petshore.model.Barrio;
import edu.eci.ieti.petshore.model.Producto;
import edu.eci.ieti.petshore.model.Usuario;
import edu.eci.ieti.petshore.model.Vendedor;

import java.util.List;

public interface PetShorePersistence {

    void saveUser(Usuario usuario);

    Usuario getUserByUsername(String username) throws PetShoreException;

    List<Barrio> getBarrio();

    void addBarrio(Barrio barrio) throws PetShoreException;

    void addCalificacion(String idVendedor, String idCliente, double calificacion) throws PetShoreException;

    void updateProducto(Producto producto, Usuario usuario) throws PetShoreException;

    void updateUser(Usuario user) throws PetShoreException;

    List<Vendedor> getVendedoresDisponibles();

    List<Producto> getProductosDisponibles();
}
