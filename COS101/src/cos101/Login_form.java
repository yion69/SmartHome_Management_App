/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cos101;

import java.sql.DriverManager;
import com.formdev.flatlaf.IntelliJTheme;
import java.awt.Image;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Asus
 */
public class Login_form extends javax.swing.JFrame {
	
	public Login_form() {
		initComponents();
		
		ImageIcon imageIcon2 = new ImageIcon(getClass().getResource("/cos101/Assets/eye2.png"));
		Image image2 = imageIcon2.getImage();
		Image newimg2 = image2.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); 
		imageIcon2 = new ImageIcon(newimg2);
		showPasswordLabel.setIcon(imageIcon2);
	
	}
	
	public void checkAuthentication(String user_id, String password) {
		if (user_id.equals("") || password.equals("")) {
			if (user_id.equals("") && !"".equals(password)) {
				JOptionPane.showMessageDialog(this, "Username is Empty");
				return;
			}
			if (!"".equals(user_id) && password.equals("")) {
				JOptionPane.showMessageDialog(this, "Password is Empty");
				return;
			}
			JOptionPane.showMessageDialog(this, "Input username and password");
			return;
		}
		if(!termsAndCons.isSelected()) {
			JOptionPane.showMessageDialog(this, "Please check Our Terms&Conditions","Alert",JOptionPane.INFORMATION_MESSAGE);
		}
		try {	
			String url = "jdbc:mysql://localhost:3305/testDb";
			String username = "root";
			String passwor = "thutanaing";
			String driver = "com.mysql.jdbc.Driver";
			Connection con = null;
			Statement stmt = null; 
			ResultSet rs = null;
	
			String sql = "select * from login_table where user_ID='"+user_id+"' and password='"+password+"' ";
			con = DriverManager.getConnection(url,username, passwor);
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			int count = 0; 
			
			while(rs.next()){
				count++;
			}
			if(count == 1) {
				Main_form main = new Main_form();
				UserInformation info = new UserInformation(txtUserID.getText(), txtPassword.getText());
				main.setUser(info);
				this.setVisible(false);
				main.setVisible(true);
			}
			else {
				JOptionPane.showMessageDialog(this, "User ID or Password error");
			}
		} catch (Exception e) {
			System.err.println("Error Code: "+e);
		}
		
	}
	/**
	 * This method is called from within the constructor to initialize the
	 * form. WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jPanel1 = new javax.swing.JPanel();
                jPanel2 = new javax.swing.JPanel();
                jButton1 = new javax.swing.JButton();
                showPasswordLabel = new javax.swing.JLabel();
                txtUserID = new javax.swing.JTextField();
                txtPassword = new javax.swing.JPasswordField();
                jLabel2 = new javax.swing.JLabel();
                jLabel3 = new javax.swing.JLabel();
                termsAndCons = new javax.swing.JCheckBox();
                jLabel5 = new javax.swing.JLabel();
                jLabel7 = new javax.swing.JLabel();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                setBackground(new java.awt.Color(15, 17, 26));
                setMinimumSize(new java.awt.Dimension(400, 420));
                setResizable(false);
                getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jPanel1.setBackground(new java.awt.Color(253, 246, 227));
                jPanel1.setForeground(new java.awt.Color(248, 244, 225));
                jPanel1.setMinimumSize(new java.awt.Dimension(700, 550));
                jPanel1.setName(""); // NOI18N
                jPanel1.setPreferredSize(new java.awt.Dimension(400, 420));
                jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jPanel2.setBackground(new java.awt.Color(253, 246, 227));
                jPanel2.setPreferredSize(new java.awt.Dimension(400, 420));
                jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jButton1.setBackground(new java.awt.Color(116, 139, 117));
                jButton1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
                jButton1.setForeground(new java.awt.Color(245, 251, 239));
                jButton1.setText("LOGIN");
                jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
                jButton1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton1ActionPerformed(evt);
                        }
                });
                jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 330, 290, 40));

                showPasswordLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                showPasswordLabelMouseClicked(evt);
                        }
                });
                jPanel2.add(showPasswordLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 260, 20, 20));

                txtUserID.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
                txtUserID.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtUserIDActionPerformed(evt);
                        }
                });
                jPanel2.add(txtUserID, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 170, 290, 40));

                txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
                jPanel2.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 290, 40));

                jLabel2.setFont(new java.awt.Font("Sylfaen", 3, 18)); // NOI18N
                jLabel2.setForeground(new java.awt.Color(116, 139, 117));
                jLabel2.setText("Home Decorations");
                jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 160, 20));

                jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
                jLabel3.setForeground(new java.awt.Color(116, 139, 117));
                jLabel3.setText("Password");
                jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, 90, 20));

                termsAndCons.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
                termsAndCons.setForeground(new java.awt.Color(116, 139, 117));
                termsAndCons.setText("Terms & Conditions");
                jPanel2.add(termsAndCons, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 300, -1, -1));

                jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
                jLabel5.setForeground(new java.awt.Color(116, 139, 117));
                jLabel5.setText("Smart Home");
                jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 220, 40));

                jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
                jLabel7.setForeground(new java.awt.Color(116, 139, 117));
                jLabel7.setText("User ID");
                jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 97, 20));

                jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 420));

                getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 420));

                pack();
                setLocationRelativeTo(null);
        }// </editor-fold>//GEN-END:initComponents

        private void txtUserIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserIDActionPerformed
                // TODO add your handling code here:
        }//GEN-LAST:event_txtUserIDActionPerformed

        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                checkAuthentication(txtUserID.getText(), txtPassword.getText());
        }//GEN-LAST:event_jButton1ActionPerformed

        private void showPasswordLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showPasswordLabelMouseClicked
                if(txtPassword.getEchoChar() == '•'){
			txtPassword.setEchoChar((char) 0);
			return;
		}
		txtPassword.setEchoChar('•');
        }//GEN-LAST:event_showPasswordLabelMouseClicked

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		IntelliJTheme.setup(Login_form.class.getResourceAsStream("solarizedLight.json"));
		UIManager.put("Button.arc", 10);
		UIManager.put("TextComponent.arc", 10);
		UIManager.put("Button.font", "h1.regular.font");
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Login_form().setVisible(true);
			}
		});
	}

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton jButton1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel7;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JLabel showPasswordLabel;
        private javax.swing.JCheckBox termsAndCons;
        private javax.swing.JPasswordField txtPassword;
        private javax.swing.JTextField txtUserID;
        // End of variables declaration//GEN-END:variables
}
