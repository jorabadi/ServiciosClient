package turneros.serviciosclient.communication;


import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import turneros.Common.entidades.Peticion;
import turneros.Common.entidades.Reserva;
import turneros.Common.entidades.Servicio;
import turneros.Common.entidades.Taquilla;
import turneros.Common.entidades.Turno;
import turneros.Common.rmi.IRmiObject;
import turneros.Impresion.post.ImpresionTurnosPost;
import turneros.configuration.Configuration;

public class RmiClient {
	
	IRmiObject service;
    /**
     * Port number of server
     */
    private int port = 0;  //default port
    /**
     * Host Name or IP address in String form
     */
    private String serverName = "";//default host name
    
    private String bindingName = "";
    
    public String getBindingName() {
		return bindingName;
	}

	public void setBindingName(String bindingName) {
		this.bindingName = bindingName;
	}

	private int numTaquilla = 0;

    public RmiClient() {
        this.port = Integer.valueOf(Configuration.getPreference("puerto"));
        this.serverName = Configuration.getPreference("serverName");
        this.bindingName = Configuration.getPreference("bindingName");
    }

    public void connect(String hostName, int port) throws IOException, NotBoundException {
    	service = (IRmiObject) Naming.lookup("rmi://"+this.serverName+":"+this.port+"/"+this.bindingName);
    }

    public void requestServicio(int codigoServicio) throws IOException {
    	Servicio servicio = new Servicio();
		servicio.setCodigoServicio(codigoServicio);
		Reserva reserva = service.solicitarServicio(servicio);
		ImpresionTurnosPost.imprimirReserva(reserva);
    }
    
    public List<Servicio> listarServiciosDisponibles() throws IOException {
    	return service.listarServiciosDisponibles();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
    
    public int getNumTaquilla() {
        return this.numTaquilla;
    }

}
