package rpn;
import java.util.Arrays;

public class Pila {
   private double[] numeros;
   private int contador;

   
   public Pila(){
    numeros = new double[10];
    contador = 0;
   }


   public void apilar (double valor){
    if (contador == numeros.length){
        numeros = Arrays.copyOf(numeros, numeros.length * 2);
    } 
    numeros [contador] = valor;
    contador++;
   }

   public double desapilar(){
    if(contador == 0){
        throw new IllegalStateException("La pila está vacía");
    }
    contador --;
    return numeros[contador];
    }


   public double verTope(){
    if (contador == 0){
        throw new  IllegalStateException("La pila está vacía");
    }
    return numeros[contador-1];
   }

   public int tamano(){
    return contador;
   }

   public boolean estaVacia(){
    return contador == 0;
   }

   public void vaciar(){
    contador =0;
   }

}
