/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bengkels;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Transaksi extends javax.swing.JDialog {
    public static Connection con;
    public static Statement stm;
    public static ResultSet res;
    
    Koneksi objKoneksi;
    private String nm_sp;
    public Transaksi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        objKoneksi = new Koneksi();
        autoNumber();
        tampilNamaSpareParts();
        inisialisasi();
        tampiltabel();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }



     private void autoNumber(){
        String noServices = "SER000";
        int i =0;
        try {
            Connection con = objKoneksi.bukaKoneksi ();
            Statement st = con.createStatement();
            String sql = "select no_services from isi";
            ResultSet rs = st.executeQuery(sql);
                while(rs.next()){
                    noServices = rs.getString("no_services");
                }
            noServices = noServices.substring(3);
            i = Integer.parseInt(noServices)+1;
            noServices = "00"+i;
            noServices = "SER"+noServices.substring(noServices.length()-3);
            txtNoServices.setText(noServices);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private void tampilNamaSpareParts(){
        try{
            Connection con = objKoneksi.bukaKoneksi();
            Statement st = con.createStatement();
            String sql = "select nm_sp from spareparts order by nm_sp";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                cmbSpareParts.addItem(rs.getString("nm_sp"));
            }
            con.close();
            st.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private void detilSpareParts(){
        try {
            Connection con = objKoneksi.bukaKoneksi();
            Statement st = con.createStatement();
            String sql = "select * from spareparts where nm_sp = '"+cmbSpareParts.getSelectedItem()+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                txtHarga.setText(rs.getString("harga"));
                nm_sp = rs.getString("nm_sp");
            }
            con.close();
            st.close();
            System.out.println(""+nm_sp);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        
        try {
            Connection con = objKoneksi.bukaKoneksi();
            Statement st = con.createStatement();
            String sql2 = "select * from rncsparepart where nm_sp = '"+cmbSpareParts.getSelectedItem()+"'";
            ResultSet rs2 = st.executeQuery(sql2);
            if(rs2.next()){
                txtJumlah.setText(rs2.getString("jumlah"));
                rincian.setText(rs2.getString("rincian"));
                nm_sp = rs2.getString("nm_sp");
            }
            con.close();
            st.close();
            System.out.println(""+nm_sp);
        } catch (SQLException e){
            System.out.println(e.getMessage());
            
        }
        
        
    }
    private void inisialisasi(){
        btnTambah.setEnabled(true);
        btnUbah.setEnabled(true);
        btnHapus.setEnabled(true);
    }
    private void tambahData(){
        int discount = 0;
        int jml_bayar = 0;
        
        if(rb5.isSelected()){
            discount = 5;
        }
        if(rb10.isSelected()){
            discount = 10;
        }
        if(rb15.isSelected()){
            discount = 15;
        }
        jml_bayar = Integer.parseInt(txtHarga.getText());
        jml_bayar = jml_bayar - (jml_bayar * discount / 100);
        
        try {
            Connection con = objKoneksi.bukaKoneksi();
            Statement st = con.createStatement();
            String sql = "insert into isi values('"+txtNoServices.getText()+"','"+nm_sp+"',"+ "'"+txtJumlah.getText()+"','"+discount+"','"+jml_bayar+"','"+txtEmail.getText()+"')";
            int sukses = st.executeUpdate(sql);
            if(sukses > 0){
                JOptionPane.showMessageDialog(rootPane, "Data Berhasil di Tambahkan");
            }else{
                JOptionPane.showMessageDialog(rootPane, "Data Tidak Berhasil di Tambahkan");
            }
            con.close();
            st.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private void cariData(){
        try {
            Connection con = objKoneksi.bukaKoneksi();
            Statement st = con.createStatement();
            String sql = "select * from isi where no_services = '"+txtNoServices.getText()+"' "
                    + "and nm_sp = '"+nm_sp+"'";
            ResultSet rs = st.executeQuery(sql);
                if(rs.next()){
                    txtJumlah.setText(rs.getString("jumlah"));
                    if(rs.getString("discount").equals("5")){
                        rb5.setSelected(true);
                    }
                    if(rs.getString("discount").equals("10")){
                        rb10.setSelected(true);
                    }
                    if(rs.getString("discount").equals("15")){
                        rb15.setSelected(true);
                    }
                    inisialisasi();
                    btnUbah.setEnabled(true);
                    btnHapus.setEnabled(true);
                } else {
                    inisialisasi();
                    btnTambah.setEnabled(true);
                    rb5.setSelected(true);
                }
               con.close();
               st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private void ubahData(){
        int discount = 0;
        int jml_bayar = 0;
        
        if(rb5.isSelected()){
            discount = 5;
        }
        if(rb10.isSelected()){
            discount = 10;
        }
        if(rb15.isSelected()){
            discount = 15;
        }
        jml_bayar = Integer.parseInt(txtHarga.getText());
        jml_bayar = jml_bayar - (jml_bayar * discount / 100);
        
        try {
            Connection con = objKoneksi.bukaKoneksi();
            Statement st = con.createStatement();
            String sql = "update isi set jumlah = '"+txtJumlah.getText()+"', discount = '"+discount+"', jml_bayar = "
                    + "'"+jml_bayar+"' where no_services = '"+txtNoServices.getText()+"' and nm_sp = '"+nm_sp+"'";
            int sukses = st.executeUpdate(sql);
                if(sukses > 0){
                    JOptionPane.showMessageDialog(rootPane, "Data Berhasil di Ubah");
                    inisialisasi();
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Data Tidak Berhasil di Ubah");
                }
                con.close();
                st.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
        }
    }
    private void bersih(){
        rb5.setSelected(true);
    }
    private void hapusData(){
        try {
            Connection con = objKoneksi.bukaKoneksi();
            Statement st = con.createStatement();
            String sql = "delete from isi where no_services = '"+txtNoServices.getText()+"' and nm_sp = '"+nm_sp+"'";
            int sukses = st.executeUpdate(sql);
            if(sukses > 0){
                JOptionPane.showMessageDialog(rootPane, "Data Berhasil di Hapus");
                bersih();
                inisialisasi();
            }else{
                JOptionPane.showMessageDialog(rootPane, "Data Tidak Berhasil di Hapus");
            }
            con.close();
            st.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public void tampiltabel(){
        DefaultTableModel tb = new DefaultTableModel();
        tb.addColumn("No. Service");
        tb.addColumn("Umur Kendaraan");
        tb.addColumn("discount");
        tb.addColumn("Jumlah bayar");
        tb.addColumn("Email");
         try{
            String sql = "select * from isi";
            java.sql.Connection conn=(Connection)objKoneksi.bukaKoneksi();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            
            while(res.next()){
                tb.addRow(new Object[]{res.getString(1),res.getString(2),res.getString(4),res.getString(5),res.getString(6)});
            }
            jTable1.setModel(tb);
        }catch (SQLException e){
            System.out.println("Error :" + e.getMessage());
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
        txtNoServices = new javax.swing.JTextField();
        btnBuatBaru = new javax.swing.JButton();
        cmbSpareParts = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtHarga = new javax.swing.JTextField();
        txtJumlah = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        rb5 = new javax.swing.JRadioButton();
        rb10 = new javax.swing.JRadioButton();
        rb15 = new javax.swing.JRadioButton();
        btnTambah = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        rincian = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/3.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 33, -1, -1));

        txtNoServices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoServicesActionPerformed(evt);
            }
        });
        getContentPane().add(txtNoServices, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 238, 102, -1));

        btnBuatBaru.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        btnBuatBaru.setText("Buat Baru");
        btnBuatBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuatBaruActionPerformed(evt);
            }
        });
        getContentPane().add(btnBuatBaru, new org.netbeans.lib.awtextra.AbsoluteConstraints(285, 238, -1, -1));

        cmbSpareParts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbSparePartsMouseClicked(evt);
            }
        });
        cmbSpareParts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSparePartsActionPerformed(evt);
            }
        });
        getContentPane().add(cmbSpareParts, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 278, 148, -1));

        jLabel2.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("No. Service");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 244, -1, -1));

        jLabel3.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Umur Kendaraan");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 283, -1, -1));

        jLabel4.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Harga");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 322, -1, -1));
        getContentPane().add(txtHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 316, 210, -1));

        txtJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahActionPerformed(evt);
            }
        });
        getContentPane().add(txtJumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 363, 210, -1));

        jLabel5.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Jumlah Perbaikan");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 369, -1, -1));

        jLabel6.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Discount");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 410, -1, -1));

        rb5.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        rb5.setForeground(new java.awt.Color(0, 0, 0));
        rb5.setText("5%");
        getContentPane().add(rb5, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 409, -1, -1));

        rb10.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        rb10.setForeground(new java.awt.Color(0, 0, 0));
        rb10.setText("10%");
        getContentPane().add(rb10, new org.netbeans.lib.awtextra.AbsoluteConstraints(215, 409, -1, -1));

        rb15.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        rb15.setForeground(new java.awt.Color(0, 0, 0));
        rb15.setText("15%");
        getContentPane().add(rb15, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 409, -1, -1));

        btnTambah.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });
        getContentPane().add(btnTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 439, -1, -1));

        btnUbah.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        btnUbah.setText("Ubah");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });
        getContentPane().add(btnUbah, new org.netbeans.lib.awtextra.AbsoluteConstraints(118, 439, -1, -1));

        btnHapus.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });
        getContentPane().add(btnHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 439, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No. Service", "Sparepart", "discount", "Total Harga"
            }
        ));
        jTable1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jTable1AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 479, 597, 377));

        jLabel7.setFont(new java.awt.Font("Copperplate Gothic Bold", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Segera cek disini dan perbaiki motor anda");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, -1, 30));

        rincian.setColumns(20);
        rincian.setRows(5);
        jScrollPane3.setViewportView(rincian);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(385, 238, -1, 229));

        jLabel8.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Email");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 204, -1, -1));
        getContentPane().add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 198, 210, -1));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/gears-5193383.png"))); // NOI18N
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, 860));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNoServicesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoServicesActionPerformed
        // TODO add your handling code here:
        cariData();
    }//GEN-LAST:event_txtNoServicesActionPerformed

    private void cmbSparePartsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSparePartsActionPerformed
        // TODO add your handling code here:
        detilSpareParts();
        cariData();
    }//GEN-LAST:event_cmbSparePartsActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        tambahData();
        tampiltabel();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        // TODO add your handling code here:
        ubahData();
        tampiltabel();
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        hapusData();
        tampiltabel();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnBuatBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuatBaruActionPerformed
        // TODO add your handling code here:
        autoNumber();
        bersih();
        inisialisasi();
    }//GEN-LAST:event_btnBuatBaruActionPerformed

    private void jTable1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jTable1AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1AncestorAdded

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int baris = jTable1.rowAtPoint (evt.getPoint());
        String noservices = jTable1.getValueAt(baris, 0).toString();
        txtNoServices.setText(noservices);
        
        String SpareParts = jTable1.getValueAt(baris, 1).toString();
        cmbSpareParts.setSelectedItem(SpareParts);
        
       // String jumlah = jTable1.getValueAt(baris, 3).toString();
       // txtJumlah.setText(jumlah);
        
        String Email = jTable1.getValueAt(baris, 4).toString();
        txtEmail.setText(Email);


    }//GEN-LAST:event_jTable1MouseClicked

    private void cmbSparePartsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbSparePartsMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_cmbSparePartsMouseClicked

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtJumlahActionPerformed

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

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Transaksi dialog = new Transaksi(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnBuatBaru;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.JComboBox<String> cmbSpareParts;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JRadioButton rb10;
    private javax.swing.JRadioButton rb15;
    private javax.swing.JRadioButton rb5;
    private javax.swing.JTextArea rincian;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtNoServices;
    // End of variables declaration//GEN-END:variables
}
