package rpn;
import java.util.HashMap;

public class Memoria {
       private HashMap<String, Double> valores;   

    public Memoria() {
        valores = new HashMap<>();           
    }

    public void guardar(String nombre, double valor) {
    valores.put(nombre, valor);
    }

    public boolean existe(String nombre) {
        return valores.containsKey(nombre);
    }

    public double obtener(String nombre) {
        if ( !existe(nombre) ) {
            throw new IllegalArgumentException("Variable no definida: " + nombre);
        }
        return valores.get(nombre);
}
}
