/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bengkel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import static javax.swing.UIManager.getString;

/**
 *
 * @author ASUS
 */
public class Transaksi extends javax.swing.JFrame {
    Koneksi objKoneksi;
    private String kd_sp;
    public Transaksi() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Transaksi");
        objKoneksi= new Koneksi();
        autoNumber();
        tampilnamasparepart();
        inisialisasi();
    }
    
    
    private void autoNumber(){
        String noService = "SER000";
        int i =0;
        try {
            Connection con = objKoneksi.bukaKoneksi ();
            Statement st = con.createStatement();
            String sql = "select no_service from isi";
            ResultSet rs = st.executeQuery(sql);
                while(rs.next()){
                    noService = rs.getString("no_service");
                }
            noService = noService.substring(3);
            i = Integer.parseInt(noService)+1;
            noService = "00"+i;
            noService = "SER"+noService.substring(noService.length()-3);
            txtNoService.setText(noService);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    private void tampilnamasparepart(){
        try{
            Connection con = objKoneksi.bukaKoneksi ();
            Statement st = con.createStatement();
            String sql = "select nmsparepart from sparepart order by nmsparepart";
            ResultSet rs = st.executeQuery(sql);
                while(rs.next()){
                    cmbsparepart.addItem(rs.getString("nmsparepart"));
                }
            con.close();
            st.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private void detilsparepart(){
        String kd_sp="";
        try{
            Connection con = objKoneksi.bukaKoneksi ();
            Statement st = con.createStatement();
            String sql = "select * from sparepart where nmsparepart ='"+cmbsparepart.getSelectedItem()+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                txtharga.setText(rs.getString("harga"));
                kd_sp=rs.getString(kd_sp);
            }    
            
            con.close();
            st.close();
            System.out.println(""+kd_sp);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private void inisialisasi (){
        btnTambah.setEnabled(true);
        btnUbah.setEnabled(true);
        btnHapus.setEnabled(true);
    }
    
    private void tambahData(){
        int jml_bayar = 0;
        jml_bayar = Integer.parseInt(txtharga.getText()) * Integer.parseInt(txtJumlah.getText());
        
        try {
            Connection con = objKoneksi.bukaKoneksi();
            Statement st = con.createStatement();
            String sql = "insert into isi values ('"+txtNoService.getText()+"','"+kd_sp+"',"+"'"+txtJumlah.getText()+"','"+jml_bayar+"')";
            int sukses = st.executeUpdate(sql);
            if(sukses > 0){
                JOptionPane.showMessageDialog(rootPane, "Data berhasil di tambah");
            }else{
                JOptionPane.showMessageDialog(rootPane, "Data gagal di tambah");
            }
            con.close();
            st.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    private void caridata(){
        try{
            Connection con = objKoneksi.bukaKoneksi ();
            Statement st = con.createStatement();
            String sql = "select * from isi where no_service = '"+txtNoService.getText()+"' "+"and kd_sp ='"+kd_sp+"'";
            ResultSet rs = st.executeQuery(sql);
            inisialisasi();
            if(rs.next()){
                btnUbah.setEnabled(true);
                btnHapus.setEnabled(true);
            }else {
                inisialisasi();
                btnTambah.setEnabled(true);
                txtJumlah.setText("");
            }
            con.close();
            st.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    private void ubahdata(){
        int jml_bayar = 0;
        jml_bayar = Integer.parseInt(txtharga.getText()) * Integer.parseInt(txtJumlah.getText());
        try{
            Connection con = objKoneksi.bukaKoneksi ();
            Statement st = con.createStatement();
            String sql = "update isi set jml_item = '"+txtJumlah.getText()+"', jml_bayar ="+"'"+jml_bayar+"' where no_service = '"+txtNoService.getText()+"' and kd_sp '"+kd_sp+"'";
            int sukses = st.executeUpdate(sql);
            if (sukses > 0){
                JOptionPane.showMessageDialog(rootPane, "Data berhasil diupdate");
            }else{
                JOptionPane.showMessageDialog(rootPane, "Data tidak berhasil diupdate");
            }
            con.close();
            st.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    private void bersih(){
        txtJumlah.setText("");
    }
    
    private void hapus(){
        try{
            Connection con = objKoneksi.bukaKoneksi ();
            Statement st = con.createStatement();
            String sql = "delete from isi where no_service = '"+txtNoService.getText()+"' and kd_sp = '"+kd_sp+"'";
            int sukses = st.executeUpdate(sql);
            if (sukses > 0){
                JOptionPane.showMessageDialog(rootPane, "Data berhasil dihaupus");
                bersih();inisialisasi();
            }else{
                JOptionPane.showMessageDialog(rootPane, "Data tidak berhasil dihaupus");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNoService = new javax.swing.JTextField();
        txtharga = new javax.swing.JTextField();
        txtJumlah = new javax.swing.JTextField();
        btnBuatBaru = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        cmbsparepart = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Transaksi");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/3.png"))); // NOI18N
        jLabel1.setText("jLabel1");

        jLabel2.setText("No Servis");

        jLabel3.setText("Spare Part");

        jLabel4.setText("Harga");

        jLabel5.setText("Jumlah");

        txtNoService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoServiceActionPerformed(evt);
            }
        });

        btnBuatBaru.setText("Buat Baru");

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnUbah.setText("Ubah");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");

        btnKeluar.setText("Keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        cmbsparepart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbsparepartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtNoService, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuatBaru))
                            .addComponent(cmbsparepart, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtharga)
                            .addComponent(txtJumlah)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(btnTambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUbah)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapus)
                        .addGap(18, 18, 18)
                        .addComponent(btnKeluar)))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNoService, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuatBaru))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbsparepart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnUbah)
                    .addComponent(btnHapus)
                    .addComponent(btnKeluar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        // TODO add your handling code here:
     dispose();   
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void cmbsparepartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbsparepartActionPerformed
        // TODO add your handling code here:
        detilsparepart();
        caridata();
    }//GEN-LAST:event_cmbsparepartActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        tambahData();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void txtNoServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoServiceActionPerformed
        // TODO add your handling code here:
        caridata();
    }//GEN-LAST:event_txtNoServiceActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        // TODO add your handling code here:
        ubahdata();
    }//GEN-LAST:event_btnUbahActionPerformed

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
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuatBaru;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.JComboBox cmbsparepart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtNoService;
    private javax.swing.JTextField txtharga;
    // End of variables declaration//GEN-END:variables
}
