package rpn;

public class CalculadoraRPN {

    private Pila pila;

    public CalculadoraRPN (Pila pila){
    this.pila = pila;
    }

    public void procesarToken(String token){
        if(token.equals("+")){
            double b = pila.desapilar(); //primero en salir
            double a = pila.desapilar(); //segundo en salir
            pila.apilar(a + b);
        } else if (token.equals("-")){
            double b = pila.desapilar(); //primero en salir
            double a = pila.desapilar(); //segundo en salir
            pila.apilar(a - b);
        } else if (token.equals("*")){
            double b = pila.desapilar(); //primero en salir
            double a = pila.desapilar(); //segundo en salir
            pila.apilar(a * b);
        } else if (token.equals("/")){
            double b = pila.desapilar(); //primero en salir
            double a = pila.desapilar(); //segundo en salir
            pila.apilar(a / b);
        } else {
            double numero = Double.parseDouble(token); 
            pila.apilar(numero);
        }

    }
}
