import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Frame extends JFrame implements ActionListener {


	private static final long serialVersionUID = 1L;
	JButton agregar;
	JButton eliminar;
	JButton limpiar;
	JButton guardar;
	JTextField modelo;
	JTextField potencia;
	JComboBox<String> marca;
	JComboBox<String> motor;
	JLabel emarca;
	JLabel emodelo;
	JLabel emotor;
	JLabel epotencia;
	Dimension dmax;
	Dimension dmin;
	JTable tabla;

	public Frame() {
		super("Lista de Vehiculos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

		emarca = new JLabel("Marca: ");
		p.add(emarca);

		marca = new JComboBox<String>();
		dmax = marca.getMaximumSize();
		dmin = marca.getMinimumSize();
		marca.setMaximumSize(new Dimension(dmax.width, dmin.height));
		marca.addActionListener(this);
		marca.setActionCommand("marca");
		marca.setEditable(false);
		marca.setFocusable(true);
		p.add(marca);

		emodelo = new JLabel("Modelo: ");
		p.add(emodelo);

		modelo = new JTextField(" ");
		modelo.setEditable(true);
		dmax = modelo.getMaximumSize();
		dmin = modelo.getMinimumSize();
		modelo.setMaximumSize(new Dimension(dmax.width, dmin.height));
		p.add(modelo);

		emotor = new JLabel("Motor: ");
		p.add(emotor);
		motor = new JComboBox<String>();
		dmax = motor.getMaximumSize();
		dmin = motor.getMinimumSize();
		motor.setMaximumSize(new Dimension(dmax.width, dmin.height));
		motor.addActionListener(this);
		motor.setActionCommand("motor");
		motor.setEditable(false);
		motor.addItem("Gasolina");
		motor.addItem("Diesel");
		motor.addItem("Hibrido");
		motor.addItem("Eléctrico");
		
		p.add(motor);

		epotencia = new JLabel("Potencia: ");
		p.add(epotencia);

		potencia = new JTextField(" ");
		potencia.setEditable(true);
		dmax = potencia.getMaximumSize();
		dmin = potencia.getMinimumSize();
		potencia.setMaximumSize(new Dimension(dmax.width, dmin.height));
		p.add(potencia);

		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));

		agregar = new JButton("Agregar");
		agregar.addActionListener(this);
		agregar.setActionCommand("agregar");
		agregar.setFocusable(false);
		p2.add(agregar);

		eliminar = new JButton("Eliminar");
		eliminar.addActionListener(this);
		eliminar.setActionCommand("eliminar");
		eliminar.setFocusable(false);
		p2.add(eliminar);

		limpiar = new JButton("Limpiar");
		limpiar.addActionListener(this);
		limpiar.setActionCommand("limpiar");
		limpiar.setFocusable(false);
		p2.add(limpiar);

		guardar = new JButton("Guardar");
		guardar.addActionListener(this);
		guardar.setActionCommand("guardar");
		guardar.setFocusable(false);
		p2.add(guardar);

		tabla = new JTable(new DefaultTableModel(new String[] { "Marca", "Modelo", "Motor", "Potencia" }, 0));
		tabla.setFillsViewportHeight(true);
		c.add(p);
		c.add(p2);
		c.add(new JScrollPane(tabla));
		leerFichero();
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if (actionCommand.equals("agregar")) {
			if(!modelo.getText().trim().equals("") && !potencia.getText().trim().equals("")) {
			Object[] row= {marca.getSelectedItem(),modelo.getText(),motor.getSelectedItem(),potencia.getText()};
			((DefaultTableModel)tabla.getModel()).addRow(row);
			}else {
				JOptionPane.showMessageDialog(null, "Por favor, añada los campos","Error",JOptionPane.ERROR_MESSAGE);
			}
		} else if (actionCommand.equals("eliminar")) {
			if(tabla.getSelectedRow()>=0) {
				((DefaultTableModel)tabla.getModel()).removeRow(tabla.getSelectedRow());
			}else {
				JOptionPane.showMessageDialog(null, "Por favor, seleccione una columna","Aviso",JOptionPane.WARNING_MESSAGE);
			}

		} else if (actionCommand.equals("limpiar")) {
			if(!modelo.getText().trim().equals("") && !potencia.getText().trim().equals("")) {
				modelo.setText(null);
				potencia.setText(null);
				
			}else {
				JOptionPane.showMessageDialog(null, "Rellene los campos correspondientes para poder limpiar","Aviso",JOptionPane.WARNING_MESSAGE);
			}

		} else if (actionCommand.equals("guardar")) {
			guardarLista();
		}
	}
	
	
	
	private void guardarLista() {
		JFileChooser fc = new JFileChooser();
		int resultado = fc.showSaveDialog(this);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			BufferedOutputStream out = null;
			try {
				out =  new BufferedOutputStream(new FileOutputStream("Tabla.txt"));
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}finally {
				if (out != null)
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	}
	

	private void leerFichero() {
		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/marcas.txt")));

			System.out.println("Leyendo el fichero marcas.txt");
			String linea;
			while ((linea = br.readLine()) != null)
				marca.addItem(linea);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != br) {
					br.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
