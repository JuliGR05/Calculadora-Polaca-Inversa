package rpn;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Pila pila = new Pila();
        Memoria memoria = new Memoria();
        CalculadoraRPN calculadora = new CalculadoraRPN(pila, memoria);
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Calculadora Polaca Inversa (RPN) ===");

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            String opcion = leerLinea(scanner, "Elija una opción: ");
            if (opcion == null) {
                System.out.println();
                break;
            }

            try {
                switch (opcion.trim()) {
                    case "1":
                        ingresarExpresion(scanner, calculadora, pila);
                        break;
                    case "2":
                        verPila(pila);
                        break;
                    case "3":
                        buscarNumero(scanner, pila);
                        break;
                    case "4":
                        ordenarPila(pila);
                        break;
                    case "5":
                        guardarVariable(scanner, pila, memoria);
                        break;
                    case "6":
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida. Elija un número del 1 al 6.");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("¡Hasta luego!");
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("1. Ingresar expresión");
        System.out.println("2. Ver pila");
        System.out.println("3. Buscar número en la pila");
        System.out.println("4. Ordenar pila de forma descendente");
        System.out.println("5. Guardar variable");
        System.out.println("6. Salir");
    }

    private static String leerLinea(Scanner scanner, String mensaje) {
        System.out.print(mensaje);
        if (!scanner.hasNextLine()) {
            return null;
        }
        return scanner.nextLine();
    }

    private static void ingresarExpresion(Scanner scanner, CalculadoraRPN calculadora,
                                          Pila pila) {
        String expresion = leerLinea(scanner, "Expresión RPN (ej: 3 4 + 2 *): ");
        if (expresion == null) {
            return;
        }
        if (expresion.trim().isEmpty()) {
            System.out.println("La expresión no puede estar vacía.");
            return;
        }

        double resultado = calculadora.evaluar(expresion);
        System.out.println("Resultado: " + resultado);
        System.out.println("Pila: " + pila);
    }

    private static void verPila(Pila pila) {
        if (pila.estaVacia()) {
            System.out.println("La pila está vacía.");
            return;
        }
        System.out.println("Pila (fondo -> tope): " + pila);
        System.out.println("Tamaño: " + pila.tamano() + " | Tope: " + pila.verTope());
    }

    private static void buscarNumero(Scanner scanner, Pila pila) {
        String texto = leerLinea(scanner, "Número a buscar: ");
        if (texto == null) {
            return;
        }

        double valor;
        try {
            valor = Double.parseDouble(texto.trim());
        } catch (NumberFormatException e) {
            System.out.println("Eso no es un número válido: " + texto.trim());
            return;
        }

        int[] posiciones = UtilidadesPila.buscar(pila, valor);
        if (posiciones.length == 0) {
            System.out.println("El número " + valor + " no está en la pila.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < posiciones.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(posiciones[i]);
        }
        System.out.println("El número " + valor + " está en la(s) posición(es): " + sb
                + " (0 = fondo)");
    }

    private static void ordenarPila(Pila pila) {
        if (pila.estaVacia()) {
            System.out.println("La pila está vacía, no hay nada que ordenar.");
            return;
        }
        UtilidadesPila.ordenarDescendente(pila);
        System.out.println("Pila ordenada de forma descendente: " + pila);
    }

    private static void guardarVariable(Scanner scanner, Pila pila, Memoria memoria) {
        if (pila.estaVacia()) {
            System.out.println("La pila está vacía: no hay un valor para guardar.");
            return;
        }

        String nombre = leerLinea(scanner, "Nombre de la variable: ");
        if (nombre == null) {
            return;
        }
        nombre = nombre.trim();

        if (nombre.isEmpty() || nombre.contains(" ")) {
            System.out.println("Nombre inválido: no puede estar vacío ni tener espacios.");
            return;
        }
        if (nombre.equals("+") || nombre.equals("-") || nombre.equals("*") || nombre.equals("/")) {
            System.out.println("Nombre inválido: no puede ser un operador.");
            return;
        }
        try {
            Double.parseDouble(nombre);
            System.out.println("Nombre inválido: no puede ser un número.");
            return;
        } catch (NumberFormatException e) {
            // No es un número: el nombre es válido.
        }

        double valor = pila.verTope();
        memoria.guardar(nombre, valor);
        System.out.println("Variable '" + nombre + "' guardada con el valor " + valor
                + ". Puede usarla en las expresiones.");
    }
}
