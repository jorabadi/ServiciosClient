package turneros.servicios.view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JButton;

import turneros.Common.entidades.Servicio;
import turneros.serviciosclient.communication.RmiClient;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.List;

public class ServiciosClient {

	private JFrame frame;
	private RmiClient rmiCliente;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServiciosClient window = new ServiciosClient();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServiciosClient() {
		try {
			initialize();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws NotBoundException 
	 * @throws IOException 
	 */
	private void initialize() throws IOException, NotBoundException {
		rmiCliente = new RmiClient();
		rmiCliente.connect(rmiCliente.getServerName(), rmiCliente.getPort());
		frame = new JFrame();
		frame.setBounds(100, 100, 810, 501);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		listarServicios();
	}
	
	public void listarServicios() {
		try {
			List<Servicio> listaServicios = rmiCliente.listarServiciosDisponibles();
			frame.getContentPane().setLayout(new GridLayout(2,(int)Math.ceil(listaServicios.size()/2)));
			
			for (Servicio servicio : listaServicios) {
				final int codigoServicio = servicio.getCodigoServicio();
				JButton button = new JButton(servicio.getNombre());
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							rmiCliente.requestServicio(codigoServicio);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				frame.getContentPane().add(button);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
