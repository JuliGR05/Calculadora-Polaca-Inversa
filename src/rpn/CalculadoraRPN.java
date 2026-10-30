package rpn;

public class CalculadoraRPN {

    private Pila pila;
    private Memoria memoria;

    public CalculadoraRPN (Pila pila){
    this(pila, new Memoria());
    }

    public CalculadoraRPN (Pila pila, Memoria memoria){
    this.pila = pila;
    this.memoria = memoria;
    }

    public void procesarToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token inválido: el token está vacío.");
        }

        if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
            if (pila.tamano() < 2) {
                throw new IllegalArgumentException("Operandos insuficientes para el operador: " + token);
            }

            double b = pila.obtener(pila.tamano() - 1);
            double a = pila.obtener(pila.tamano() - 2);
            if (token.equals("/") && b == 0.0) {
                throw new IllegalArgumentException("División por cero.");
            }

            pila.desapilar();
            pila.desapilar();
            if (token.equals("+")) {
                pila.apilar(a + b);
            } else if (token.equals("-")) {
                pila.apilar(a - b);
            } else if (token.equals("*")) {
                pila.apilar(a * b);
            } else {
                pila.apilar(a / b);
            }
            return;
        }

        if (memoria.existe(token)) {
            pila.apilar(memoria.obtener(token));
            return;
        }

        try {
            pila.apilar(Double.parseDouble(token));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Token inválido: no es un número ni una variable definida: " + token, e);
        }
    }

    public double evaluar(String expresion) {
        if (expresion == null || expresion.trim().isEmpty()) {
            throw new IllegalArgumentException("La expresión no puede estar vacía.");
        }

        double[] pilaOriginal = pila.aArreglo();
        try {
            String[] tokens = expresion.trim().split("\\s+");
            for (String token : tokens) {
                procesarToken(token);
            }
            return pila.verTope();
        } catch (RuntimeException e) {
            pila.reemplazarContenido(pilaOriginal);
            throw e;
        }
    }
}
