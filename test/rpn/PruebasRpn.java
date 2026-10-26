package rpn;

public class PruebasRpn {

    private static int fallos = 0;

    static void verificar(String nombre, boolean condicion) {
        System.out.println((condicion ? "OK     " : "FALLO  ") + nombre);
        if (!condicion) fallos++;
    }

    public static void main(String[] args) {

        // ---- Pruebas de Pila (issue #1) ----
        Pila p = new Pila();
        verificar("pila nueva esta vacia", p.estaVacia());

        p.apilar(1);
        p.apilar(2);
        p.apilar(3);
        verificar("tamano 3", p.tamano() == 3);
        verificar("verTope es 3", p.verTope() == 3);
        verificar("verTope no cambia el tamano", p.tamano() == 3);
        verificar("desapilar da 3", p.desapilar() == 3);
        verificar("desapilar da 2", p.desapilar() == 2);

        for (int i = 0; i < 50; i++) {
            p.apilar(i);
        }
        verificar("crece mas alla de 10", p.tamano() == 51);

        p.vaciar();
        verificar("vaciar deja la pila vacia", p.estaVacia());

        try {
            p.desapilar();
            verificar("desapilar en vacia lanza error", false);
        } catch (IllegalStateException e) {
            verificar("desapilar en vacia lanza error", true);
        }

        try {
            p.verTope();
            verificar("verTope en vacia lanza error", false);
        } catch (IllegalStateException e) {
            verificar("verTope en vacia lanza error", true);
        }

        System.out.println(fallos == 0 ? "\nSin fallos." : "\nFallos: " + fallos);
        if (fallos > 0) System.exit(1);
    }
}