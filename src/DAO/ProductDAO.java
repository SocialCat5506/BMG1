/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Utility.DBConnection;
import entity.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ACE
 */
public class ProductDAO extends DBConnection {

    public void create(Product p) {
        try {
            PreparedStatement ps = this.connect().prepareStatement("insert into product(pname, category, price, brand, barcode, piece, descr)" + "values(?,?,?,?,?,?,?)");
            /*Statement yerine PreparedStatement kullanılmasının sebebi ? sembolü, jdbc bu sembolü tıpkı bir değişken gibi algılıyor ve hazır query'ye sonradan verilen
            değerlerin atamasını yapıyor. Bu şekilde javada yazılan query okunmaz hale gelmeden daha karmaşık update ve insert işlemleri yapılabiliyor*/
            ps.setString(1, p.getName());
            ps.setString(2, p.getCategory());
            ps.setDouble(3, p.getPrice());
            ps.setString(4, p.getBrand());
            ps.setString(5, p.getBarcode());
            ps.setInt(6, p.getPiece());
            ps.setString(7, p.getDescr());
            ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Product> read() {
        List<Product> pList = new ArrayList<>();
        try {
            Statement st = this.connect().createStatement();
            ResultSet rs = st.executeQuery("select * from product order by pid asc");

            while (rs.next()) {
                Product tmp = new Product(rs.getInt("pid"), rs.getString("pname"),
                        rs.getString("category"), rs.getDouble("price"), rs.getString("brand"),
                        rs.getString("barcode"), rs.getInt("piece"), rs.getString("descr"));

                pList.add(tmp);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return pList;
    }

    public void update(Product p) {
        try {
            PreparedStatement ps = this.connect().prepareStatement("update product set pname=?, category=?, price=?, barcode=? where pid=" + p.getpID());
            ps.setString(1, p.getName());
            ps.setString(2, p.getCategory());
            ps.setDouble(3, p.getPrice());
            ps.setString(4, p.getBarcode());

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void delete(int v) {//arayüzde kullanıcıya ürünün veri tabanındaki tüm bilgileri gösterilerek seçim yaptırırken arayüzden gelecek olan bilgi yalnızca id değeri olacak
        int r;
        try {
            Statement st = this.connect().createStatement();
            r = st.executeUpdate("delete from product where pid=" + v);
            if (r == 1) {
                System.out.println("operation was successful");
            } else {
                System.out.println("id could not be found");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    

}
