/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2;
import java.util.List;      
import java.util.ArrayList; 
import java.io.*;
import javax.swing.*;
/**
 *
 * @author Adrian, Gianfranco y Alirio
 */
public class Controlador {
    public List<Modelo.Usuario> usuarios = new ArrayList<>();
    public Estructura.Monticulo cola = new Estructura.Monticulo();
    public Estructura.TablaHash tabla = new Estructura.TablaHash();
    public double reloj = 0.0;

    public void cargarUsuariosCSV() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader br = new BufferedReader(new FileReader(fc.getSelectedFile()))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split(",");
                    if (datos.length < 2) continue;
                    Modelo.TipoPrioridad tp = Modelo.TipoPrioridad.valueOf(datos[1].trim().toUpperCase().replace("PRIORIDAD_", ""));
                    usuarios.add(new Modelo.Usuario(datos[0].trim(), tp));
                }
            } catch (Exception e) { JOptionPane.showMessageDialog(null, "Error en CSV"); }
        }
    }

    public void enviarAImprimir(Modelo.Documento doc, boolean esPrioritario) {
        double etiqueta = esPrioritario ? reloj / doc.dueño.tipo.factor : reloj;
        cola.insertar(new Estructura.Monticulo.Nodo(doc, etiqueta));
        tabla.insertar(doc);
        doc.enCola = true;
        reloj += 1.0; // El reloj avanza con cada acción
    }

    public void eliminarDeCola(Modelo.Documento doc) {
        // Truco del enunciado: Para eliminar, le damos la prioridad más alta (-9999) 
        // para que suba al inicio y luego hacemos eliminarMin.
        for (Estructura.Monticulo.Nodo n : cola.getElementos()) {
            if (n.doc.id.equals(doc.id)) {
                n.etiqueta = -9999.0; // Máxima prioridad artificial
                // Aquí se debería re-ordenar el montículo (subir el nodo)
                cola.eliminarMin();
                tabla.eliminar(doc.id);
                doc.enCola = false;
                break;
            }
        }
    }
}
