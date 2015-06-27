/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class RegistroPlanesXML {

    private Document documento;
    private Element raiz;
    private String ruta;

    private RegistroPlanesXML(String ruta) throws IOException, JDOMException {
        SAXBuilder saxb = new SAXBuilder();
        saxb.setIgnoringElementContentWhitespace(true);
        this.documento = saxb.build(ruta);
        this.ruta = ruta;
        this.raiz = documento.getRootElement();
    }

    private RegistroPlanesXML(String ruta, String rootName) throws IOException {
        this.ruta = ruta;
        this.raiz = new Element(rootName);
        this.documento = new Document(raiz);
        this.save();
    }

    private void save() throws IOException {
        XMLOutputter xmlo = new XMLOutputter();
        xmlo.output(documento, new PrintWriter(this.ruta));
        xmlo.output(documento, System.out);
    }

    public static RegistroPlanesXML crearDocumento(String ruta) throws IOException {
        return new RegistroPlanesXML(ruta, "planes");
    }

    public static RegistroPlanesXML abrirDocumento(String ruta) throws IOException, JDOMException {
        return new RegistroPlanesXML(ruta);
    }

    public void addUser(Planes planes) throws IOException {
        Element ePlan = new Element("plan");

        Attribute aPrecio = new Attribute("precio", Integer.toString(planes.getPrecio()));
        Attribute aNumMensajes = new Attribute("NumMensajes", planes.getMensajes());
        Attribute aNumMinutos = new Attribute("NumMinutos", planes.getMinutos());
        Attribute aNombre = new Attribute("nombre", planes.getNombre());

        ePlan.setAttribute(aNombre);
        ePlan.setAttribute(aPrecio);
        ePlan.setAttribute(aNumMensajes);
        ePlan.setAttribute(aNumMinutos);
        this.raiz.addContent(ePlan);
        this.save();
    }

    private Element buscarNombre(String nombre) {
        List<Element> list = (List<Element>) this.raiz.getChildren();
        for (Element e : list) {
            if (e.getAttributeValue("nombre").equalsIgnoreCase(nombre)) {
                return e;
            }
        }
        return null;
    }

    public boolean verificarNombre(String nombre) {
        Element user = buscarNombre(nombre);
        if (user != null) {
            return true;
        }
        return false;
    }

    public void removerPlan(String nombre) throws IOException {
        Element user = buscarNombre(nombre);
        this.raiz.removeContent(user);
        this.save();
    }

    public void modificar(Planes planes) {

        Element planEncontrado = this.buscarNombre(planes.getNombre());
        planEncontrado.getAttribute("nombre").setValue(planes.getNombre());
        planEncontrado.getAttribute("precio").setValue(Integer.toString(planes.getPrecio()));
        planEncontrado.getAttribute("NumMensajes").setValue(planes.getMensajes());
        planEncontrado.getAttribute("NumMinutos").setValue(planes.getMinutos());

        try {
            save();
        } catch (IOException ex) {
            Logger.getLogger(RegistroPlanesXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
