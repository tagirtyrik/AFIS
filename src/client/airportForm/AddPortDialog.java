package client.airportForm;
import client.ClientSender;
import exception.AirportNotFoundException;
import exception.InvalidArgumentsException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.*;
import java.util.ArrayList;
/**
 *диалог для добавления аэропорта
 * @author GeneraL
 */
public class AddPortDialog extends javax.swing.JDialog {
static final String portNameDefault="Название";
static final String portLocationDefault="Место";
static Airport airport=null;
    /**
     * Creates new form addPortDialog
     */
    public AddPortDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        airportInfoPanel.setData("Введите атрибуты порта", portNameDefault, portLocationDefault);
    }
    public Airport showDialog(){
        this.setVisible(true);
        return airport;
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        airportInfoPanel = new client.airportForm.AirportInfoPanel();
        okButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        okButton.setText("Ок");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(airportInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(okButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(airportInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(okButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * метод для добавления аэропорта
     * @param evt 
     */
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        // TODO add your handling code here:
        try{
        ClientSender send=new ClientSender();
        ArrayList<Airport> airports=send.sendPort("addport "+airportInfoPanel.getPortName()+" "+airportInfoPanel.getPortLocation());
        if(airports.size()>0)airport=airports.get(0);
        this.setVisible(false);
        }catch (UnknownHostException  e){
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Exeption",JOptionPane.ERROR_MESSAGE);
            System.err.println(e.getMessage());
        }
        catch (IOException  e){
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Exeption",JOptionPane.ERROR_MESSAGE);
            System.err.println(e.getMessage());
        }
        catch (TimeoutException | AirportNotFoundException | InvalidArgumentsException  e){
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Exeption",JOptionPane.ERROR_MESSAGE);
            System.err.println(e.getMessage());
        }
        finally {
            
        }
    }//GEN-LAST:event_okButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddPortDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddPortDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddPortDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddPortDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddPortDialog dialog = new AddPortDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private client.airportForm.AirportInfoPanel airportInfoPanel;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables
}
