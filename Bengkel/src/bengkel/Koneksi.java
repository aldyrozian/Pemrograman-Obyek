/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bengkel;

import java.sql.Connection;
import java.sql.DriverManager;


public class Koneksi {
    public Connection bukaKoneksi(){
        Connection con = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/dbworkshop","root","");
            System.out.println("Berhasil konek");
            return con;
        } catch (Exception e){
            System.out.println("Gagal konek"+e.getMessage());
            return con = null;
        }
    }
}
