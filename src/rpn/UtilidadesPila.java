package rpn;

import java.util.Arrays;

public class UtilidadesPila {

    public static int[] buscar(Pila pila, double valor) {
        int[] resultado = new int[pila.tamano()];
        int cuenta = 0;

        for (int i = 0; i < pila.tamano(); i++) {
            if (pila.obtener(i) == valor) {
                resultado[cuenta] = i;
                cuenta++;
            }
        }

        return Arrays.copyOf(resultado, cuenta);
    }

    // Algoritmo: ordenamiento por seleccion (selection sort) de forma
    // descendente. En cada pasada se busca el mayor valor del arreglo y se
    // coloca en la posicion actual, hasta dejarlo ordenado.
    public static void ordenarDescendente(Pila pila) {
        double[] valores = pila.aArreglo();
        int n = valores.length;
        for (int i = 0; i < n - 1; i++) {
            int maximoIndice = i;
            for (int j = i + 1; j < n; j++) {
                if (valores[j] > valores[maximoIndice]) {
                    maximoIndice = j;
                }
            }
            if (maximoIndice != i) {
                double temporal = valores[i];
                valores[i] = valores[maximoIndice];
                valores[maximoIndice] = temporal;
            }
        }
        pila.reemplazarContenido(valores);
    }
}