/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import modelo.Planes;
import modelo.RegistroPlanesXML;
import org.jdom2.JDOMException;

/**
 *
 * @author Diego castro
 */
@ManagedBean
@RequestScoped
public class AdministradorPlan {

    /**
     * Creates a new instance of AdministradorPlan
     */
    @ManagedProperty(value = "#{planes}")
    private Planes planes;

    public AdministradorPlan() {
    }

    public Planes getPlanes() {
        return planes;
    }

    public void setPlanes(Planes planes) {
        this.planes = planes;
    }

    public RegistroPlanesXML getRegister() {
        File file = new File("planes.xml");
        RegistroPlanesXML register = null;
        if (file.exists()) {
            try {
                register = RegistroPlanesXML.abrirDocumento("planes.xml");
            } catch (IOException | JDOMException ex) {
                Logger.getLogger(AdministradorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                register = RegistroPlanesXML.crearDocumento("planes.xml");
            } catch (IOException ex) {
                Logger.getLogger(AdministradorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return register;
    }

    public String agregarPlan(ActionEvent e) {
        try {
            RegistroPlanesXML register = this.getRegister();
            register.addUser(planes);
        } catch (IOException ex) {
            Logger.getLogger(AdministradorPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "inicioAdmin";
    }
    
    public void modificarPlan(ActionEvent e){
        RegistroPlanesXML register=this.getRegister();
        register.modificar(planes);
        
    }
    public void buscarPlanes(ActionEvent e){
        RegistroPlanesXML register=this.getRegister();
        if(register.verificarNombre(planes.getNombre())){
            
        }
    }
    public void eliminarPlan(ActionEvent e){
        RegistroPlanesXML register=this.getRegister();
        try {
            register.removerPlan(planes.getNombre());
        } catch (IOException ex) {
            Logger.getLogger(AdministradorPlan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
