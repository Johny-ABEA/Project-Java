import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.DatabaseHelper;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class gestionUsers {

	private JFrame frmGestionDesUtilisateurs;
	private JTextField idx;
	private JTextField name;
	private JTable table;
	private DatabaseHelper dbh;
	private ArrayList<ArrayList<String>> list;
	private JComboBox comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gestionUsers window = new gestionUsers(1);
					window.frmGestionDesUtilisateurs.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public gestionUsers(int id) {
		this.dbh = new DatabaseHelper();
		dbh.connect();
		initialize(id);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int id) {
		frmGestionDesUtilisateurs = new JFrame();
		frmGestionDesUtilisateurs.setTitle("Gestion Des Utilisateurs");
		frmGestionDesUtilisateurs.setIconImage(Toolkit.getDefaultToolkit().getImage("img\\logo.png"));
		frmGestionDesUtilisateurs.setResizable(false);
		frmGestionDesUtilisateurs.setBounds(100, 100, 816, 656);
		frmGestionDesUtilisateurs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGestionDesUtilisateurs.getContentPane().setLayout(null);
		
		JButton save = new JButton("Modifier");
		save.setForeground(new Color(0, 51, 255));
		save.setBorder(new LineBorder(new Color(0, 51, 255), 2, true));
		save.setBounds(689, 13, 97, 49);
		frmGestionDesUtilisateurs.getContentPane().add(save);
		
		JButton del = new JButton("Supprimer");
		del.setForeground(new Color(255, 51, 51));
		del.setBorder(new LineBorder(new Color(255, 51, 0), 2, true));
		del.setBounds(565, 13, 97, 49);
		frmGestionDesUtilisateurs.getContentPane().add(del);
		
		JButton back = new JButton("Retour");
		back.setBounds(12, 13, 97, 49);
		frmGestionDesUtilisateurs.getContentPane().add(back);
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frmGestionDesUtilisateurs.dispose();
				new gestion_billets(id);
			}
			
		});
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(39, 110, 343, 471);
		frmGestionDesUtilisateurs.getContentPane().add(scrollPane);
		
		table = new JTable();
		
		
		this.list = dbh.getUserList();
		String[][] L = new String[list.size()][3];
		int r= 0;
		for (ArrayList<String> e : list) {
			L[r][0]= e.get(0);
			L[r][1]= e.get(1);
			L[r][2]= e.get(2);
			r++;
		}
		table.setModel(new DefaultTableModel(
			L,
			new String[] {
				"id", "Nom de l'utilisateur","admin"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(28);
		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				filltb(table.getSelectedRow());
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(433, 205, 326, 28);
		frmGestionDesUtilisateurs.getContentPane().add(lblNewLabel);
		
		idx = new JTextField();
		idx.setBounds(433, 231, 326, 35);
		frmGestionDesUtilisateurs.getContentPane().add(idx);
		idx.setColumns(10);
		
		name = new JTextField();
		name.setColumns(10);
		name.setBounds(433, 332, 326, 35);
		frmGestionDesUtilisateurs.getContentPane().add(name);
		
		JLabel lblNomDutilisateur = new JLabel("Nom D'utilisateur");
		lblNomDutilisateur.setBounds(433, 306, 326, 28);
		frmGestionDesUtilisateurs.getContentPane().add(lblNomDutilisateur);
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dbh.addNewUserByAdmin(id,idx.getText(),name.getText(),comboBox.getSelectedIndex());
				frmGestionDesUtilisateurs.dispose();
				new gestionUsers(id);
			}
			
		});
		del.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dbh.deleteUserByAdmin(id,idx.getText(),name.getText());
				frmGestionDesUtilisateurs.dispose();
				new gestionUsers(id);
			}
			
		});
		idx.setEditable(false);
		
		JLabel lblAdmin = new JLabel("admin");
		lblAdmin.setBounds(433, 414, 326, 28);
		frmGestionDesUtilisateurs.getContentPane().add(lblAdmin);
		
		this.comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "ADMIN"}));
		comboBox.setBounds(433, 443, 326, 40);
		frmGestionDesUtilisateurs.getContentPane().add(comboBox);
		frmGestionDesUtilisateurs.setVisible(true);
		
	}

	protected void filltb(int selectedRow) {
		idx.setText(list.get(table.getSelectedRow()).get(0));
		name.setText(list.get(table.getSelectedRow()).get(1));
		if(list.get(table.getSelectedRow()).get(2).equals("ADMIN")) {
			comboBox.setSelectedIndex(1);
		}else {
			comboBox.setSelectedIndex(0);
		}
		
		
	}
}
