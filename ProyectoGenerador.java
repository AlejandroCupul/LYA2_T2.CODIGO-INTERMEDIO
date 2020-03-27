package proyectogenerador;
import java.util.Scanner; //Importamos la librería para leer datos por teclado
import java.util.Stack; //Importamos la librería para hacer uso de pilas

public class ProyectoGenerador {
    
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.println("INTRODUZCA UNA EXPRESIÓN ALGEBRAICA INFIJA: ");
        String exprInfi = CorregirExpresion(teclado.nextLine()); //Se lee y agregan separaciones para diferenciar los operandos y operadores de la expresión introducida
        String[] arrInf = exprInfi.split(" "); //Guardamos en un arreglo cada elemento que esté separado por espacios de la expresión leida
        
        //Se declaran la pilas a usar
        Stack < String > E = new Stack < String > (); //Pila que nos servirá de entrada
        Stack < String > T = new Stack < String > (); //Pila auxiliar temporal
        Stack < String > S = new Stack < String > (); //Pila para la salida a postfija
        
        for (int i = arrInf.length - 1; i >= 0; i--) { //Recorremos el arreglo que contiene los datos de nuestra expresión de fin-inicio
            E.push(arrInf[i]); //Añadimos cada elemento del arreglo a la Pila de entrada (E)
        }
        
       
    
}
