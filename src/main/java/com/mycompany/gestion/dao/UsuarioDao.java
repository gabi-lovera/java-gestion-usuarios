/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestion.dao;

import com.mycompany.gestion.models.Usuario;
import com.mysql.jdbc.StringUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabriel
 */
public class UsuarioDao {
    
    public Connection conectar() {
        String baseDeDatos = "java";
        String usuario = "root";
        String password = "";
        String host = "localhost";
        String puerto = "3307";
        String driver = "com.mysql.jdbc.Driver";
        String conexionUrl = "jdbc:mysql://" + host + ":" + puerto + "/" + baseDeDatos + "?useSSL=false";
    
        Connection conexion = null;
        
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(conexionUrl, usuario, password);
            
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
    }
    
    public void agregar(Usuario usuario) {
       
        try {
            Connection conexion = conectar();
            String sql = "INSERT INTO `usuarios` (`id`, `nombre`, `apellido`, `telefono`, `email`) VALUES (NULL, '" 
                    + usuario.getNombre() + "', '" 
                    + usuario.getApellido() +  "', '" 
                    + usuario.getTelefono() + "', '" 
                    + usuario.getEmail() + "');";
            
            Statement statement = conexion.createStatement();
            statement.execute(sql);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void actualizar(Usuario usuario) {
       
        try {
            Connection conexion = conectar();
            String sql = "UPDATE `usuarios` SET `nombre` = '" + usuario.getNombre() 
                    + "', `apellido` = '" + usuario.getApellido() 
                    + "', `telefono` = '"+ usuario.getTelefono() 
                    + "', `email` = '"+usuario.getEmail()
                    +"' WHERE `usuarios`.`id` = "+usuario.getId()+";";
          
            Statement statement = conexion.createStatement();
            statement.execute(sql);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public List<Usuario> listar() {
        List<Usuario> listado = new ArrayList<>();
        
        try {
            Connection conexion = conectar();
            String sql = "SELECT * FROM `usuarios`;";
          
            Statement statement = conexion.createStatement();
            ResultSet resultado = statement.executeQuery(sql);
            
            while(resultado.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultado.getString("id"));
                usuario.setNombre(resultado.getString("nombre"));
                usuario.setApellido(resultado.getString("apellido"));
                usuario.setTelefono(resultado.getString("telefono"));
                usuario.setEmail(resultado.getString("email"));
                listado.add(usuario);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listado;
    }
    
    public void eliminar(String id) {
        try {
            Connection conexion = conectar();
            String sql = "DELETE FROM `usuarios` WHERE `usuarios`.`id` = " + id;
          
            Statement statement = conexion.createStatement();
            statement.execute(sql);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void guardar(Usuario usuario) {
        if (StringUtils.isEmptyOrWhitespaceOnly(usuario.getId())) {
            agregar(usuario);
        } else {
            actualizar(usuario);
        }
    }
}
