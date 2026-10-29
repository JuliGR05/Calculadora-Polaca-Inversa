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
}