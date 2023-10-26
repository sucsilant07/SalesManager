
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class productoDAO {
    conexion cn = new conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean registrarProducto(producto pto){
        String sql = "INSERT INTO productos (codigo, nombre, proveedor, stock, precio) VALUES (?, ?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, pto.getCodigo());
            ps.setString(2, pto.getNombre());
            ps.setString(3, pto.getProveedor());
            ps.setInt(4, pto.getStock());
            ps.setDouble(5, pto.getPrecio());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        } finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public void consultarProveedor(JComboBox proveedor){
        String sql = "SELECT nombre FROM proveedor";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                proveedor.addItem(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    
    public List listarProducto(){
        List<producto> listaPto = new ArrayList();
        String sql = "SELECT * FROM productos";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                producto pto = new producto();
                pto.setId(rs.getInt("id"));
                pto.setCodigo(rs.getString("codigo"));
                pto.setNombre(rs.getString("nombre"));
                pto.setProveedor(rs.getString("proveedor"));
                pto.setStock(rs.getInt("stock"));
                pto.setPrecio(rs.getDouble("precio"));
                listaPto.add(pto);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listaPto;
    }
    
    public boolean eliminarProducto(int id){
        String sql = "DELETE FROM productos WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }
    
    public boolean modificarProducto(producto pto){
        String sql = "UPDATE productos SET codigo=?, nombre=?, proveedor=?, stock=?, precio=? WHERE id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pto.getCodigo());
            ps.setString(2, pto.getNombre());
            ps.setString(3, pto.getProveedor());
            ps.setInt(4, pto.getStock());
            ps.setDouble(5, pto.getPrecio());
            ps.setInt(6, pto.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public producto buscarProducto(String cod){
        producto producto = new producto();
        String sql = "SELECT * FROM productos WHERE codigo=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cod);
            rs = ps.executeQuery();
            if(rs.next()){
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));
        }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return producto;
    }
    
    public config buscarDatos(){
        config config = new config();
        String sql = "SELECT * FROM config";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){
                config.setId(rs.getInt("id"));
                config.setRuc(rs.getInt("ruc"));
                config.setNombre(rs.getString("nombre"));
                config.setTelefono(rs.getInt("telefono"));
                config.setDireccion(rs.getString("direccion"));
                config.setRazon(rs.getString("razon"));
        }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return config;
    }
    
    public boolean modificarDatos(config config){
        String sql = "UPDATE config SET ruc=?, nombre=?, telefono=?, direccion=?, razon=? WHERE id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, config.getRuc());
            ps.setString(2, config.getNombre());
            ps.setInt(3, config.getTelefono());
            ps.setString(4, config.getDireccion());
            ps.setString(5, config.getRazon());
            ps.setInt(6, config.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
}
