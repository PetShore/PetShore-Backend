package edu.eci.ieti.petshore.services;
import edu.eci.ieti.petshore.model.*;
import edu.eci.ieti.petshore.persistence.PetShoreException;
import edu.eci.ieti.petshore.persistence.PetShorePersistence;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetShoreServices extends UserServices {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	PetShorePersistence petShorePersistence;

	public String helloWorld() {
		return "Hello World Hola Mundo";
	}

	public List<Producto> getProductos(String username) throws PetShoreException {
		return petShorePersistence.getUserByUsername(username).getProductos();
	}

	public boolean addProductoUsuario(String user, Producto producto) throws PetShoreException {
		Usuario usuario = petShorePersistence.getUserByUsername(user);
		
                boolean success = usuario.addProductos(producto);

                petShorePersistence.updateUser(usuario);
                
                return success;
		
	}

	public List<Barrio> getBarrios() {
		return petShorePersistence.getBarrio();
	}

	public void addBarrio(Barrio barrio) throws PetShoreException {
		petShorePersistence.addBarrio(barrio);
	}

	public void addCalificacion(String nameConductor, String namePasajero, double calificacion) throws PetShoreException {
		petShorePersistence.addCalificacion(nameConductor, namePasajero, calificacion);
	}

	public void updateProducto(Producto producto, Usuario usuario) throws PetShoreException {
		petShorePersistence.updateProducto(producto, usuario);
	}

	public String getUserStatus(String username) throws PetShoreException {

		Usuario user = petShorePersistence.getUserByUsername(username);
		String status = "Ninguno";
		if (user.getClientes().size() > 0 || user.getVendedores().size() > 0) {
			if (!user.getClientes().get(user.getClientes().size() - 1).estado.equals(Estado.Finalizado)) {
				status = "Cliente";
			} else if (!user.getVendedores().get(user.getVendedores().size() - 1).estado.equals(Estado.Finalizado)) {
				status = "Vendedor";
			}
		}
		return status;
	}

	public double getAverage(String username, String type) throws PetShoreException {
		Usuario usuario = petShorePersistence.getUserByUsername(username);
		double valueToReturn = 0;
		int totalScore = 0;
		if (type.equals("Vendedor") && usuario.getVendedores().size() > 0) {
			for (Vendedor vendedor : usuario.getVendedores()) {
				if (vendedor.estado.equals(Estado.Finalizado)) {
					valueToReturn += vendedor.getCalificacionPromedio().getValor();
					totalScore += 1;
				}
			}
		} else if(type.equals("Cliente") && usuario.getClientes().size()>0) {
			for (Cliente cliente : usuario.getClientes()) {
				if (cliente.estado.equals(Estado.Finalizado)) {
					valueToReturn += cliente.getCalificacionGeneral().getValor();
					totalScore += 1;
				}
			}
		}
		valueToReturn = valueToReturn / totalScore;

		return valueToReturn;
	}

	public void updateUserBasicInfo(Usuario user) throws PetShoreException {
		
		Usuario oldUserWithBasicChanges = getUserByUsername(user.username);
		

		if (user.password != null) {
			String pwd = user.password;
			String encrypt = bCryptPasswordEncoder.encode(pwd);
			oldUserWithBasicChanges.setPassword(encrypt);
		};
		
		oldUserWithBasicChanges.setNombreCompleto(user.nombreCompleto);
		oldUserWithBasicChanges.setDireccionResidencia(user.direccionResidencia);
		oldUserWithBasicChanges.setEmail(user.email);
		oldUserWithBasicChanges.setNumero(user.numero);
		
		petShorePersistence.updateUser(oldUserWithBasicChanges);
	}

	public List<Producto> solicitudCompraPasajero(InformacionCliente informacionCliente, String usernameVendedor, List<Producto> productos)
			throws PetShoreException {
		Usuario cliente = getUserByUsername(informacionCliente.getClienteUsername());
		Usuario vendedor = getUserByUsername(usernameVendedor);
		Vendedor ventaVendedor = null;

		for (int i = 0; i < vendedor.getVendedores().size(); i++) {
			if (vendedor.getVendedores().get(i).estado.equals(Estado.Disponible)) {

				ventaVendedor = vendedor.getVendedores().get(i);
				break;
			}
		}
		Cliente compraCliente = new Cliente();
		compraCliente.estado = Estado.Disponible;
		compraCliente.username = cliente.username;
		compraCliente.direccionEntrega = informacionCliente.getDireccion();
		if(ventaVendedor.productosVendidos == null){
			ventaVendedor.setProductosVendidos(new ArrayList<>());
		}
		for(int i = 0; i < productos.size(); i++){
			ventaVendedor.addProductoVendido(productos.get(i));
			cliente.addProductos(productos.get(i));
		}
		cliente.getClientes().add(compraCliente);
		petShorePersistence.updateUser(cliente);
		petShorePersistence.updateUser(vendedor);
		return ventaVendedor.productosVendidos;

	}

	public List<Vendedor> getVendedoresDisponibles(){
		return petShorePersistence.getVendedoresDisponibles();
	}


	public JSONObject aceptarORechazarCliente(NuevoEstado info, String usernameCliente) throws PetShoreException {
		Usuario usuarioCliente = getUserByUsername(usernameCliente);
		Usuario usuarioVendedor = getUserByUsername(info.getVendedorUsername());
		boolean estado = info.getEstado();
		Cliente cliente = null;
		for (Cliente cliente1 : usuarioCliente.getClientes()) {
			if (cliente1.estado.equals(Estado.Disponible)) {
				cliente = cliente1;
				break;
			}
		}
		Vendedor vendedor = null;

		if (estado) {
			cliente.estado = Estado.Aceptado;
		} else {
			cliente.estado = Estado.Rechazado;
		}
		petShorePersistence.updateUser(usuarioVendedor);
		petShorePersistence.updateUser(usuarioCliente);
		JSONObject jsonADevolver = new JSONObject(vendedor);
		jsonADevolver.put("estadoCliente", cliente.estado);
		return jsonADevolver;

	}

	public Vendedor estadoVendedor(Estado estado, String usernameVendedor) throws PetShoreException{
		Usuario conductorUsuario = petShorePersistence.getUserByUsername(usernameVendedor);
		Vendedor vendedor = null;
		for(Vendedor v: conductorUsuario.getVendedores()){
			if(v.estado.equals(Estado.Disponible)){
				vendedor = v;
				break;
			}
		}
		vendedor.setEstado(estado);
		petShorePersistence.updateUser(conductorUsuario);
		return vendedor;
	}

	public Cliente estadoCliente(Estado estado, String usernameCliente) throws PetShoreException {
		Usuario usuarioCliente = petShorePersistence.getUserByUsername(usernameCliente);
		Cliente cliente = null;
		for (Cliente cliente1 : usuarioCliente.getClientes()) {
			if (cliente1.estado.equals(Estado.Disponible) || cliente1.estado.equals(Estado.Aceptado)) {
				cliente = cliente1;
				break;
			}
		}
		cliente.setEstado(estado);
		petShorePersistence.updateUser(usuarioCliente);
		return cliente;
	}

	public void finalizarCompra(String sellerName, List<Cliente> clientes) throws PetShoreException {
		estadoVendedor(Estado.Finalizado,sellerName);
		for(Cliente p: clientes){
			estadoCliente(Estado.Finalizado,p.username);
		}

	}

	public Vendedor getShopSeller(String usernameSeller) throws PetShoreException {
		Usuario sellerUser = petShorePersistence.getUserByUsername(usernameSeller);
		Vendedor seller = null;
		for (Vendedor c: sellerUser.getVendedores()){
			if(c.getEstado().equals(Estado.Disponible)){
				seller = c;
				break;
			}
		}

		return seller;
	}

	public Cliente getShopClient(String usernamePassenger) throws PetShoreException {
		Usuario passengerUser = petShorePersistence.getUserByUsername(usernamePassenger);
		Cliente cliente = null;
		for(Cliente p:passengerUser.getClientes()){
			if(p.getEstado().equals(Estado.Aceptado)){
				cliente = p;
				break;
			}
		}
		return cliente;
	}
}
