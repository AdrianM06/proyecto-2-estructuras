/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2;
import java.util.*;

/**
 *
 * @author Adrian, Gianfranco y Alirio
 */
public class Estructura {
    
    // --- MONTÍCULO BINARIO (Cola de Prioridad) ---
    public static class Monticulo {
        private List<Nodo> heap = new ArrayList<>();

        public static class Nodo implements Comparable<Nodo> {
            public Modelo.Documento doc;
            public double etiqueta; // El tiempo ajustado por prioridad
            public Nodo(Modelo.Documento d, double e) { doc = d; etiqueta = e; }
            @Override
            public int compareTo(Nodo o) { return Double.compare(this.etiqueta, o.etiqueta); }
        }

        public void insertar(Nodo n) {
            heap.add(n);
            subir(heap.size() - 1);
        }

        private void subir(int i) {
            while (i > 0 && heap.get(i).compareTo(heap.get((i-1)/2)) < 0) {
                Collections.swap(heap, i, (i-1)/2);
                i = (i-1)/2;
            }
        }

        public Nodo eliminarMin() {
            if (heap.isEmpty()) return null;
            Nodo min = heap.get(0);
            heap.set(0, heap.get(heap.size()-1));
            heap.remove(heap.size()-1);
            if (!heap.isEmpty()) hundir(0);
            return min;
        }

        private void hundir(int i) {
            int menor = i, izq = 2*i+1, der = 2*i+2;
            if (izq < heap.size() && heap.get(izq).compareTo(heap.get(menor)) < 0) menor = izq;
            if (der < heap.size() && heap.get(der).compareTo(heap.get(menor)) < 0) menor = der;
            if (menor != i) {
                Collections.swap(heap, i, menor);
                hundir(menor);
            }
        }

        public List<Nodo> getElementos() { return heap; }
    }

    // --- TABLA HASH (Para búsqueda O(1)) ---
    public static class TablaHash {
        private LinkedList<Modelo.Documento>[] cubetas = new LinkedList[31];

        public TablaHash() {
            for (int i = 0; i < 31; i++) cubetas[i] = new LinkedList<>();
        }

        private int funcionHash(String id) {
            return Math.abs(id.hashCode()) % 31;
        }

        public void insertar(Modelo.Documento d) {
            cubetas[funcionHash(d.id)].add(d);
        }

        public void eliminar(String id) {
            cubetas[funcionHash(id)].removeIf(d -> d.id.equals(id));
        }
    }
}