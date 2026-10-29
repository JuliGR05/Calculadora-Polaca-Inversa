package rpn;

public class PruebasRpn {

    private static int fallos = 0;

    static void verificar(String nombre, boolean condicion) {
        System.out.println((condicion ? "OK     " : "FALLO  ") + nombre);
        if (!condicion) fallos++;
    }

    static void verificarError(String nombre, Runnable accion, String mensajeEsperado) {
        try {
            accion.run();
            verificar(nombre, false);
        } catch (RuntimeException e) {
            verificar(nombre, e instanceof IllegalArgumentException
                    && e.getMessage() != null
                    && e.getMessage().toLowerCase().contains(mensajeEsperado.toLowerCase()));
        }
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


        // ---- Pruebas del issue #4 ----
        Pila p5 = new Pila();
        CalculadoraRPN calc5 = new CalculadoraRPN(p5);
        verificar("evaluar 3 4 + 2 * = 14", calc5.evaluar("3 4 + 2 *") == 14);
        verificar("evaluar deja el resultado en el tope", p5.verTope() == 14);
        verificar("evaluar deja un solo resultado", p5.tamano() == 1);

        Pila p6 = new Pila();
        CalculadoraRPN calc6 = new CalculadoraRPN(p6);
        verificar("evaluar expresión RPN compuesta = 14",
                calc6.evaluar("5 1 2 + 4 * + 3 -") == 14);
        verificar("expresión compuesta deja un solo resultado", p6.tamano() == 1);

        Pila p7 = new Pila();
        p7.apilar(42);
        CalculadoraRPN calc7 = new CalculadoraRPN(p7);
        verificarError("evaluar detecta operandos insuficientes",
                () -> calc7.evaluar("+"), "operandos insuficientes");
        verificar("error por operandos restaura la pila", p7.tamano() == 1 && p7.verTope() == 42);

        Pila p8 = new Pila();
        p8.apilar(42);
        CalculadoraRPN calc8 = new CalculadoraRPN(p8);
        verificarError("evaluar detecta división por cero",
                () -> calc8.evaluar("10 0 /"), "división por cero");
        verificar("error de división restaura la pila", p8.tamano() == 1 && p8.verTope() == 42);

        Pila p9 = new Pila();
        p9.apilar(99);
        CalculadoraRPN calc9 = new CalculadoraRPN(p9);
        verificarError("evaluar detecta token inválido",
                () -> calc9.evaluar("3 4 + xyz"), "token inválido");
        verificar("token inválido restaura la pila", p9.tamano() == 1 && p9.verTope() == 99);

        Pila p10 = new Pila();
        p10.apilar(8);
        CalculadoraRPN calc10 = new CalculadoraRPN(p10);
        verificarError("evaluar rechaza expresión vacía",
                () -> calc10.evaluar("  \t "), "expresión");
        verificar("expresión vacía conserva la pila", p10.tamano() == 1 && p10.verTope() == 8);


        // ---- Pruebas del issue #6 ----
        Pila b1 = new Pila();
        b1.reemplazarContenido(new double[]{5, 3, 5, 8});
        verificar("buscar 5 en [5,3,5,8] = {0, 2}",
                java.util.Arrays.equals(UtilidadesPila.buscar(b1, 5), new int[]{0, 2}));
        verificar("buscar 8 en [5,3,5,8] = {3}",
                java.util.Arrays.equals(UtilidadesPila.buscar(b1, 8), new int[]{3}));
        verificar("buscar 7 (no esta) = arreglo vacio",
                UtilidadesPila.buscar(b1, 7).length == 0);

        Pila b2 = new Pila();
        verificar("buscar en pila vacia = arreglo vacio",
                UtilidadesPila.buscar(b2, 5).length == 0);

        Pila b3 = new Pila();
        b3.apilar(4);
        verificar("un solo elemento: buscar 4 = {0}",
                java.util.Arrays.equals(UtilidadesPila.buscar(b3, 4), new int[]{0}));
        verificar("un solo elemento: buscar 9 = arreglo vacio",
                UtilidadesPila.buscar(b3, 9).length == 0);
        
         // ---- Pruebas del issue #8 ----
        Memoria m = new Memoria();

        m.guardar("x", 5);
        verificar("existe x despues de guardar", m.existe("x"));
        verificar("obtener x devuelve 5", m.obtener("x") == 5);
        verificar("y no existe todavia", !m.existe("y"));

        m.guardar("x", 8);
        verificar("guardar de nuevo sobrescribe el valor", m.obtener("x") == 8);

        m.guardar("y", 2.5);
        verificar("varias variables conviven", m.obtener("x") == 8 && m.obtener("y") == 2.5);

        try {
            m.obtener("z");
            verificar("obtener variable inexistente lanza error", false);
        } catch (IllegalArgumentException e) {
            verificar("obtener variable inexistente lanza error", true);
        }
        
        System.out.println(fallos == 0 ? "\nSin fallos." : "\nFallos: " + fallos);
        if (fallos > 0) System.exit(1);
    }

}