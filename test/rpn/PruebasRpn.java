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


        // ---- Pruebas del issue #2 ----
        Pila q = new Pila();
        q.reemplazarContenido(new double[]{5, 3, 8});
        verificar("reemplazar: tamano 3", q.tamano() == 3);
        verificar("obtener(0) es 5 (fondo)", q.obtener(0) == 5);
        verificar("obtener(2) es 8 (tope)", q.obtener(2) == 8);
        verificar("verTope es 8", q.verTope() == 8);
        verificar("toString", q.toString().equals("[5.0, 3.0, 8.0]"));
        verificar("aArreglo devuelve [5, 3, 8]",
                java.util.Arrays.equals(q.aArreglo(), new double[]{5, 3, 8}));

        q.apilar(9);
        verificar("apilar despues de reemplazar", q.verTope() == 9 && q.tamano() == 4);

        Pila r = new Pila();
        r.reemplazarContenido(new double[]{});
        verificar("reemplazar con vacio", r.estaVacia());
        r.apilar(1);
        verificar("apilar tras reemplazar con vacio", r.verTope() == 1);

        try {
            q.obtener(4);
            verificar("obtener(4) lanza error", false);
        } catch (IndexOutOfBoundsException e) {
            verificar("obtener(4) lanza error", true);
        }
        try {
            q.obtener(-1);
            verificar("obtener(-1) lanza error", false);
        } catch (IndexOutOfBoundsException e) {
            verificar("obtener(-1) lanza error", true);
        }


        // ---- Pruebas del issue #3 ----
        Pila p1 = new Pila();
        CalculadoraRPN calc1 = new CalculadoraRPN(p1);
        calc1.procesarToken("3");
        calc1.procesarToken("4");
        calc1.procesarToken("+");
        verificar("3 4 + = 7", p1.verTope() == 7);
        verificar("3 4 + deja un solo elemento", p1.tamano() == 1);

        Pila p2 = new Pila();
        CalculadoraRPN calc2 = new CalculadoraRPN(p2);
        calc2.procesarToken("10");
        calc2.procesarToken("2");
        calc2.procesarToken("-");
        verificar("10 2 - = 8", p2.verTope() == 8);

        Pila p3 = new Pila();
        CalculadoraRPN calc3 = new CalculadoraRPN(p3);
        calc3.procesarToken("10");
        calc3.procesarToken("2");
        calc3.procesarToken("/");
        verificar("10 2 / = 5", p3.verTope() == 5);

        Pila p4 = new Pila();
        CalculadoraRPN calc4 = new CalculadoraRPN(p4);
        calc4.procesarToken("6");
        calc4.procesarToken("7");
        calc4.procesarToken("*");
        verificar("6 7 * = 42", p4.verTope() == 42);


        System.out.println(fallos == 0 ? "\nSin fallos." : "\nFallos: " + fallos);
        if (fallos > 0) System.exit(1);
    }



}