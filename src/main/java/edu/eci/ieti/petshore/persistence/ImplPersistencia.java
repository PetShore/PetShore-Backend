package edu.eci.ieti.petshore.persistence;

import edu.eci.ieti.petshore.model.*;
import edu.eci.ieti.petshore.repository.NeighborhoodRepository;
import edu.eci.ieti.petshore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImplPersistencia implements PetShorePersistence{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NeighborhoodRepository neighborhoodRepository;

    @Override public void saveUser(Usuario usuario) {
        String pwd = usuario.getPassword();
        String encrypt = bCryptPasswordEncoder.encode(pwd);
        usuario.setPassword(encrypt);
        userRepository.save(usuario);
    }

    @Override
    public Usuario getUserByUsername(String username) throws PetShoreException {
        Usuario usuario = userRepository.findByUsername(username);
        if(usuario == null){
            throw new PetShoreException(PetShoreException.USERNAME_NOT_FOUND);
        }
        return usuario;
    }

    @Override
    public List<Barrio> getBarrio() {
        List<Barrio> allNeighborhoods = neighborhoodRepository.findAll();
        return allNeighborhoods;
    }

    @Override
    public void addBarrio(Barrio barrio) throws PetShoreException {
        if(barrio != null){
        	neighborhoodRepository.save(barrio);
        } else {
            throw new PetShoreException(PetShoreException.INVALID_NEIGHBORHOOD);
        }
    }

    @Override
    public void addCalificacion(String nameVendedor, String nameCliente, double calificacion) throws PetShoreException {
        if(calificacion > 0) {
            if(nameCliente.equals("-1")) {
                Usuario user = getUserByUsername(nameVendedor);
                Calificacion qualification = new Calificacion(calificacion);
                for (Vendedor vendedor : user.getVendedores()) {
                    if (vendedor.getEstado().equals(Estado.Disponible)) {
                        ArrayList<Calificacion> calificacions = vendedor.getCalificacions();
                        if(vendedor.getCalificacionPromedio() == null){
                            vendedor.setCalificacionPromedio(qualification);
                        }else{
                            double nuevaCalProm = calificacionPromedio(calificacions, vendedor.getCalificacionPromedio(), calificacion);
                            vendedor.setCalificacionPromedio(new Calificacion(nuevaCalProm));
                        }
                        calificacions.add(qualification);
                        vendedor.setCalificacions(calificacions);
                        break;
                    }
                }
                userRepository.save(user);
            }else if(nameVendedor.equals("-1")){
                Usuario user = getUserByUsername(nameCliente);
                Calificacion qualification = new Calificacion(calificacion);
                for (Cliente cliente : user.getClientes()) {
                    if (cliente.getEstado().equals(Estado.Aceptado)) {
                        ArrayList<Calificacion> calificacions = cliente.getCalificacions();
                        if(cliente.getCalificacionGeneral() == null){
                            cliente.setCalificacionGeneral(qualification);
                        }else{
                            double nuevaCalProm = calificacionPromedio(calificacions, cliente.getCalificacionGeneral(), calificacion);
                            cliente.setCalificacionGeneral(new Calificacion(nuevaCalProm));
                        }
                        calificacions.add(qualification);
                        cliente.setCalificacions(calificacions);
                        break;
                    }
                }
                userRepository.save(user);
            } else {
                throw new PetShoreException(PetShoreException.USERNAME_NOT_FOUND);
            }
        } else {
            throw new PetShoreException(PetShoreException.INVALID_RATING);
        }
    }

    public double calificacionPromedio(ArrayList<Calificacion> Caificaciones, Calificacion calificacionPromedio, double nuevaCalificacion){
        int tamanioLista = Caificaciones.size();
        double nuevaCalPr = ((calificacionPromedio.getValor() * tamanioLista) + nuevaCalificacion)/tamanioLista + 1;
        return nuevaCalPr;
    }

    @Override
    public void updateProducto(Producto producto, Usuario usuario) throws PetShoreException {
        if(producto != null) {
            usuario.setProducto(producto);
            userRepository.save(usuario);
        } else {
            throw new PetShoreException(PetShoreException.INVALID_PRODUCT);
        }
    }


    @Override
    public void updateUser(Usuario user) throws PetShoreException {
        Usuario oldUser = getUserByUsername(user.username);
        oldUser.changeValues(user);
        userRepository.save(oldUser);
    }

    @Override
    public List<Vendedor> getVendedoresDisponibles() {
        List<Usuario> usuarios= userRepository.findAll();
        List<Vendedor> sellersTemp = new ArrayList<Vendedor>();
        List<Producto> productosDisponibles = new ArrayList<Producto>();
        for(Usuario user:usuarios){
            for(Vendedor sell: user.getVendedores()){
                if(sell.getEstado().equals(Estado.Disponible)){
                    sell.setUsername(user.getUsername());
                    sell.setProductosDisponibles(user.getProductos());
                    sellersTemp.add(sell);

                }
            }
        }
        return sellersTemp;
    }

    @Override
    public List<Producto> getProductosDisponibles() {
        List<Vendedor> vendedorsDisponibles = getVendedoresDisponibles();
        List<Producto> productosDisponibles = new ArrayList<Producto>();
        for(Vendedor seller: vendedorsDisponibles){
            for(Producto producto: seller.getProductosDisponibles()){
                productosDisponibles.add(producto);
            }
        }
        return productosDisponibles;
    }

}