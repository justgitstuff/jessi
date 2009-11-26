package src;

import java.io.File;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JessiGUI.java
 *
 * Created on Nov 21, 2009, 1:44:40 AM
 */

/**
 * 
 * @author tonym
 */
public class JessiGUI extends javax.swing.JFrame {

	private String horariosOutput = "/horariosOutput";

	/** Creates new form JessiGUI */
	public JessiGUI() {
		initLog();
		log.debug("Iniciando componentes.");
		initComponents();
		log.debug("Los componentes se iniciaron correcta.");
		this.setResizable(false);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		textArea1 = new java.awt.TextArea();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenuItem6 = new javax.swing.JMenuItem();
		jMenuItem1 = new javax.swing.JMenuItem();
		jMenuItem2 = new javax.swing.JMenuItem();
		jMenuItem7 = new javax.swing.JMenuItem();
		jMenuItem5 = new javax.swing.JMenuItem();
		jMenu2 = new javax.swing.JMenu();
		jMenuItem3 = new javax.swing.JMenuItem();
		jMenuItem4 = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("JESSI");

		jButton1.setText("Generar Horarios");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jButton2.setText("Reportes");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});

		jButton3.setText("Horario");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});

		textArea1.setEditable(false);
		textArea1.setRows(150);

		jMenu1.setText("File");

		jMenuItem6.setText("Generar horarios");
		jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem6ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem6);

		jMenuItem1.setText("Ver reportes");
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem1ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem1);

		jMenuItem2.setText("Ver horario");
		jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem2ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem2);

		jMenuItem7.setText("Ir a horarios");
		jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem7ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem7);

		jMenuItem5.setText("Salir");
		jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem5ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem5);

		jMenuBar1.add(jMenu1);

		jMenu2.setText("Help");

		jMenuItem3.setText("Ayuda");
		jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem3ActionPerformed(evt);
			}
		});
		jMenu2.add(jMenuItem3);

		jMenuItem4.setText("Acerca de...");
		jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem4ActionPerformed(evt);
			}
		});
		jMenu2.add(jMenuItem4);

		jMenuBar1.add(jMenu2);

		setJMenuBar(jMenuBar1);

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(
								layout
										.createSequentialGroup()
										.add(189, 189, 189)
										.add(
												layout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING)
														.add(
																layout
																		.createSequentialGroup()
																		.add(
																				jButton1,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				164,
																				Short.MAX_VALUE)
																		.addContainerGap())
														.add(
																layout
																		.createSequentialGroup()
																		.add(
																				jButton3,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				164,
																				Short.MAX_VALUE)
																		.addContainerGap())
														.add(
																layout
																		.createSequentialGroup()
																		.add(
																				jButton2,
																				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																				164,
																				Short.MAX_VALUE)
																		.addContainerGap())))
						.add(
								layout
										.createSequentialGroup()
										.add(20, 20, 20)
										.add(
												textArea1,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												325,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(25, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				layout.createSequentialGroup().add(21, 21, 21).add(jButton1)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.UNRELATED).add(
								jButton2).addPreferredGap(
								org.jdesktop.layout.LayoutStyle.UNRELATED).add(
								jButton3).addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED).add(
								textArea1,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								107, Short.MAX_VALUE).add(19, 19, 19)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void initLog() {

		log = GetLogger.getInstance(JessiGUI.class.getName(), pathToLogConf);

	}

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
		log.debug("Se han activado los resportes.");
		System.out.println("Reportes");
		textArea1.append("Reportes\n");
	}// GEN-LAST:event_jButton2ActionPerformed

	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
		log.debug("Consultando horario.");
		verHorarioAccion();
	}// GEN-LAST:event_jButton3ActionPerformed

	private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem4ActionPerformed
		System.out.println("About");
		textArea1.append("About\n");
		log.debug("Abriendo about...");
		verAcercaAccion();
	}// GEN-LAST:event_jMenuItem4ActionPerformed

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		System.out.println("Generar");
		textArea1.append("Generar\n");
		textArea1.append("Path destino de los horario generados: "
				+ horariosOutput);
	}// GEN-LAST:event_jButton1ActionPerformed

	private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem6ActionPerformed
		System.out.println("Generar");
		textArea1.append("Generar\n");
	}// GEN-LAST:event_jMenuItem6ActionPerformed

	private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem1ActionPerformed
		System.out.println("Reportes");
		textArea1.append("Reportes\n");
	}// GEN-LAST:event_jMenuItem1ActionPerformed

	private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem2ActionPerformed
		log.debug("Consultando horarios.");
		verHorarioAccion();

	}// GEN-LAST:event_jMenuItem2ActionPerformed

	private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem3ActionPerformed
		System.out.println("Ayuda");
		textArea1.append("Ayuda\n");
	}// GEN-LAST:event_jMenuItem3ActionPerformed

	private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem5ActionPerformed
		System.out.println("Salir");
		System.exit(0);
	}// GEN-LAST:event_jMenuItem5ActionPerformed

	private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem7ActionPerformed
		try {
			log.debug("Abriendo carpeta de horarios en .xls");
			File f = new File("horariosXls");
			String logPath = f.getAbsolutePath();
			f.delete();
			System.out.println(logPath);
			Runtime.getRuntime().exec("open . " + logPath);
		} catch (IOException e) {
			log.debug(e.getMessage());
		}
	}// GEN-LAST:event_jMenuItem7ActionPerformed

	private void verAcercaAccion() {
		String content = "";
		content += "\t\tJESSI\n\n";
		content += "Jade Enterprise Integrated Scheduling System\n";
		content += "\t\tby\n";
		content += "Rogelio, Tony, Malto, Pablo, Bulos, Benji\n y los LAtis que son ISC\n\n";
		content += "\t\tCopyrigth ITESM - CEM";

		JOptionPane.showMessageDialog(null, content, "Acerca de...",
				JOptionPane.PLAIN_MESSAGE);
	}

	private void verHorarioAccion() {
		System.out.println("Ver horario");
		textArea1.append("Ver horario\n");
		String id = JOptionPane.showInputDialog(null,
				"Inserta Matricula o Nomina: ", "Obtener horario", 1);
		textArea1.append("Has elegido el id: " + id + "\n");
		textArea1.append("Obteniendo horario en .xls\n");
		InsertCSV xls = new InsertCSV(id);
		xls.run();
		textArea1.append("Horario .xls generado exitosamente.");
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new JessiGUI().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JMenuItem jMenuItem1;
	private javax.swing.JMenuItem jMenuItem2;
	private javax.swing.JMenuItem jMenuItem3;
	private javax.swing.JMenuItem jMenuItem4;
	private javax.swing.JMenuItem jMenuItem5;
	private javax.swing.JMenuItem jMenuItem6;
	private javax.swing.JMenuItem jMenuItem7;
	private java.awt.TextArea textArea1;
	// End of variables declaration//GEN-END:variables

	private Logger log;
	private final Integer WIDTH = 500;
	private final Integer HEIGTH = 350;
	private final String pathToLogConf = "conf/config.logger.properties";
}