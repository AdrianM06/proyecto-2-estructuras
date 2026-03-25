/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adrian, Gianfranco y Alirio
 */
public class Modelo {
    public enum TipoPrioridad {
        ALTA(3.0), MEDIA(1.5), BAJA(1.0);
        public double factor;
        TipoPrioridad(double f) { this.factor = f; }
    }

    public static class Usuario {
        public String nombre;
        public TipoPrioridad tipo;
        public List<Documento> documentos = new ArrayList<>();
        public Usuario(String n, TipoPrioridad t) { nombre = n; tipo = t; }
    }

    public static class Documento {
        public String nombre, tipo, id;
        public int tamaño;
        public boolean enCola = false;
        public Usuario dueño;

        public Documento(String n, int t, String tp, Usuario u) {
            nombre = n; tamaño = t; tipo = tp; dueño = u;
            this.id = n + "_" + System.nanoTime(); // ID único para la Tabla Hash
        }
    }
}