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
    private String deskripsi;
    public Transaksi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        objKoneksi = new Koneksi();
        autoNumber();
        tampilNamaSpareParts();
        inisialisasi();
        tampiltabel();
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
            String sql = "select * from spareparts where nm_sp = '"+cmbSpareParts.getSelectedItem()+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                jTextArea1.setText(rs.getString("deskripsi"));
                nm_sp = rs.getString("nm_sp");
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
        jml_bayar = Integer.parseInt(txtHarga.getText()) * Integer.parseInt(txtJumlah.getText());
        jml_bayar = jml_bayar - (jml_bayar * discount / 100);
        
        try {
            Connection con = objKoneksi.bukaKoneksi();
            Statement st = con.createStatement();
            String sql = "insert into isi values('"+txtNoServices.getText()+"','"+nm_sp+"',"
                    + "'"+txtJumlah.getText()+"','"+discount+"','"+jml_bayar+"'"++)";
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
                    txtJumlah.setText(rs.getString("jml_item"));
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
                    txtJumlah.setText("");
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
        jml_bayar = Integer.parseInt(txtHarga.getText()) * Integer.parseInt(txtJumlah.getText());
        jml_bayar = jml_bayar - (jml_bayar * discount / 100);
        
        try {
            Connection con = objKoneksi.bukaKoneksi();
            Statement st = con.createStatement();
            String sql = "update isi set jml_item = '"+txtJumlah.getText()+"', discount = '"+discount+"', jml_bayar = "
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
        txtJumlah.setText("");
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
        tb.addColumn("Sparepart");
        tb.addColumn("discount");
        tb.addColumn("Jumlah bayar");
         try{
            String sql = "select * from isi";
            java.sql.Connection conn=(Connection)objKoneksi.bukaKoneksi();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            
            while(res.next()){
                tb.addRow(new Object[]{res.getString(1),res.getString(2),res.getString(4),res.getString(5)});
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
        btnKeluar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/3.png"))); // NOI18N

        txtNoServices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoServicesActionPerformed(evt);
            }
        });

        btnBuatBaru.setText("Buat Baru");
        btnBuatBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuatBaruActionPerformed(evt);
            }
        });

        cmbSpareParts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSparePartsActionPerformed(evt);
            }
        });

        jLabel2.setText("No. Service");

        jLabel3.setText("Spare Part");

        jLabel4.setText("Harga");

        jLabel5.setText("Jumlah");

        jLabel6.setText("Discount");

        rb5.setText("5%");

        rb10.setText("10%");

        rb15.setText("15%");

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
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnKeluar.setText("Keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

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

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(128, 128, 128)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rb5)
                                        .addGap(18, 18, 18)
                                        .addComponent(rb10)
                                        .addGap(18, 18, 18)
                                        .addComponent(rb15))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtNoServices, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(btnBuatBaru))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtJumlah, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtHarga, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
                                    .addComponent(cmbSpareParts, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(94, 94, 94)
                                .addComponent(jScrollPane2))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(btnTambah)
                .addGap(18, 18, 18)
                .addComponent(btnUbah)
                .addGap(18, 18, 18)
                .addComponent(btnHapus)
                .addGap(18, 18, 18)
                .addComponent(btnKeluar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNoServices, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuatBaru)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cmbSpareParts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(rb5)
                            .addComponent(rb10)
                            .addComponent(rb15)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnUbah)
                    .addComponent(btnHapus)
                    .addComponent(btnKeluar))
                .addContainerGap(18, Short.MAX_VALUE))
        );

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

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnKeluarActionPerformed

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
        
        String jumlah = jTable1.getValueAt(baris, 2).toString();
        txtJumlah.setText(jumlah);
        


    }//GEN-LAST:event_jTable1MouseClicked

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
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.JComboBox<String> cmbSpareParts;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JRadioButton rb10;
    private javax.swing.JRadioButton rb15;
    private javax.swing.JRadioButton rb5;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtNoServices;
    // End of variables declaration//GEN-END:variables
}
