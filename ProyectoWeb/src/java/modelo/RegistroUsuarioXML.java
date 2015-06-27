/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Diego castro
 */
public class RegistroUsuarioXML {
    
     private Document documento;
    private Element raiz;
    private String ruta;
    
    private RegistroUsuarioXML(String ruta) throws IOException, JDOMException{
        SAXBuilder saxb= new SAXBuilder();
        saxb.setIgnoringElementContentWhitespace(true);
        this.documento=saxb.build(ruta);
        this.ruta=ruta;
        this.raiz=documento.getRootElement();
    }
    
    private RegistroUsuarioXML(String ruta,String rootName) throws IOException{
        this.ruta=ruta;
        this.raiz= new Element(rootName);
        this.documento= new Document(raiz);
        this.save();
    }

    private void save() throws IOException {
        XMLOutputter xmlo= new XMLOutputter();
        xmlo.output(documento, new PrintWriter(this.ruta));
        xmlo.output(documento,System.out);
    }
    
    public static RegistroUsuarioXML crearDocumento(String ruta) throws IOException{
        return new RegistroUsuarioXML(ruta,"usuarios");
    }
    
    public static RegistroUsuarioXML abrirDocumento(String ruta) throws IOException, JDOMException{
        return new RegistroUsuarioXML(ruta);
    }
    
    public void addUser(Usuario usuario) throws IOException{
        Element eUsuario= new Element("usuario");
        Element eNombre= new Element("nombre");
        Element eApellido= new Element("apellido");
        Element eFechaN= new Element("fechaNacimiento");
        Element eCorreo= new Element("correo");
        Attribute aNombreUsuario= new Attribute("nombreUsuario",usuario.getNombreUsuario());
        Attribute aClave= new Attribute("clave",usuario.getClave());
        
        
        
        eNombre.addContent(usuario.getNombre());
        eApellido.addContent(usuario.getApellido());
        eFechaN.addContent(usuario.getCorreo());
        
        
        
        eUsuario.addContent(eNombre);
        eUsuario.addContent(eApellido);
        eUsuario.addContent(eFechaN);
        eUsuario.addContent(eCorreo);
        eUsuario.setAttribute(aNombreUsuario);
        eUsuario.setAttribute(aClave);
       
        
        this.raiz.addContent(eUsuario);
        this.save();
    }
    
    private Element buscarNombre(String usuario){
        List<Element> list= (List<Element>) this.raiz.getChildren();
        for(Element e:list){
            if(e.getAttributeValue("nombreUsuario").equalsIgnoreCase(usuario)){
                return e;
            }
        }
        return null;
    }
     private Element buscarClave(String clave){
        List<Element> list= (List<Element>) this.raiz.getChildren();
        for(Element e:list){
            if(e.getAttributeValue("clave").equalsIgnoreCase(clave)){
                return e;
            }
        }
        return null;
    }
      public boolean verificarClave(String clave){
        Element user= buscarClave(clave);
        if(user!=null){
            return true;
        }
        return false;
    }
    
    public boolean verificarNombre(String nombreUsuario){
        Element user= buscarNombre(nombreUsuario);
        if(user!=null){
            return true;
        }
        return false;
    }
    
    public void removeUser(String nombreUsuario) throws IOException{
        Element user= buscarNombre(nombreUsuario);
        this.raiz.removeContent(user);
        this.save();
    }
    
    
}
