package edu.eci.ieti.petshore.sockets;


import edu.eci.ieti.petshore.model.*;
import edu.eci.ieti.petshore.persistence.PetShoreException;
import edu.eci.ieti.petshore.services.PetShoreServices;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ws")
public class WebSocketController {

    @Autowired
    SimpMessagingTemplate msgt;

    @Autowired
    PetShoreServices petShoreServices;

    @MessageMapping("/clientRequest.{usernameSeller}")
    public void clienteSolicitudDeCompra(InformacionCliente infoCliente, @DestinationVariable String usernameSeller, List<Producto> productos){
        try {
            List<Producto> possibleClients= petShoreServices.solicitudCompraPasajero(infoCliente,usernameSeller,productos);
            msgt.convertAndSend("/petshore/clientRequest."+usernameSeller,possibleClients);
        } catch (PetShoreException e) {
            e.printStackTrace();
            msgt.convertAndSend("/petshore/clientRequest."+usernameSeller,"No encontré el usuario cliente o el vendedor");
        }
    }

    @MessageMapping("/offerProduct.{sellerUsername}")
    public void ofrecerProducto(Producto producto, @DestinationVariable String sellerUsername ) throws PetShoreException {
        boolean hasProduct = true;
        if(producto.isDisponible()){
            msgt.convertAndSend("/petshore/sellers", petShoreServices.getVendedoresDisponibles());
            hasProduct = false;
        }
    }

    @MessageMapping("/acceptOrRejectClient.{usernameClient}")
    public void aceptarORechazarCliente(NuevoEstado state, @DestinationVariable String usernameClient){
        try{
            JSONObject json = petShoreServices.aceptarORechazarCliente(state,usernameClient);
            msgt.convertAndSend("/petshore/acceptOrRejectClient."+usernameClient,json.toMap());
        } catch (Exception e){
            msgt.convertAndSend("/petshore/acceptOrRejectClient."+usernameClient,"No se encontró un cliente o vendedor con el username dado");
        }

    }

    @MessageMapping("/clientState.{usernameClient}")
    public void estadoPasajero(Estado state, @DestinationVariable String usernameClient){
        try {
            msgt.convertAndSend("/petshore/clientState."+usernameClient, petShoreServices.estadoCliente(state,usernameClient));
        } catch (PetShoreException e) {
            e.printStackTrace();
        }
    }

    @MessageMapping("/finishShop.{usernameSeller}")
    public void finishTravel(List<Cliente> clientes, @DestinationVariable String usernameSeller){
        try {
        	petShoreServices.finalizarCompra(usernameSeller,clientes);
            msgt.convertAndSend("/finishShop."+usernameSeller,"The Shop is finished");
        } catch (PetShoreException e) {
            msgt.convertAndSend("/finishShop."+usernameSeller,"I got a error: "+e.getMessage());
            e.printStackTrace();
        }
    }
}
