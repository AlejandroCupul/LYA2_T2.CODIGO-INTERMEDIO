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
        
        //Algoritmo que pasa nuestra expresión de Infijo a Postfijo
        try { //Manejador de errores
            
            while (!E.isEmpty()) { //Mientras nuestra pila de entrada no esté vacía realiza lo siguiente:
                switch (Jerarquia(E.peek())){ //Envia el dato de la cima de la pila consultada para obtener el grado de importancia si es un operador
                    case 1: // "("
                        T.push(E.pop());
                        break;
                    case 3: // "+" || "-"
                    case 4: // "*" || "/"
                        while(Jerarquia(T.peek()) >= Jerarquia(E.peek())) {
                            S.push(T.pop());
                        }
                        T.push(E.pop());
                        break;
                    case 2: // ")"
                        while(!T.peek().equals("(")) {
                            S.push(T.pop());
                        }
                        T.pop();
                        E.pop();
                        break;
                    default:
                        S.push(E.pop());
                }
            }
            
            String exprPost = S.toString().replaceAll("[\\]\\[,]",""); //Convertimos la pila de salida (S) en una cadena y le eliminamos los "[]" y ","
            String[] arrPost = exprPost.split(" "); //Pasamos a un arreglo cada elemento que esté separado por espacios de la expresión postfija 
            E.clear(); //Vaciamos la pila de entrada para reutilizarla
            T.clear(); //Vaciamos la pila de los temporales para reutilizarla

            for (int i = arrPost.length - 1; i >= 0; i--) { //Recorremos el arreglo que contiene los datos de nuestra expresión postfija de fin-inicio
                E.push(arrPost[i]); //Añadimos cada elemento del arreglo postfijo a la Pila de entrada (E)
            }

            //Algoritmo que divide nuestra expresión y genera los temporales para el código intermedio
            String operadores = "+-*/";
            int cont=0; //Declaramos nuestro contador inicializado en 0 que llevará el registro de las T generadas
            while (!E.isEmpty()) { //Mientras la pila de entrada no esté vacía realiza lo siguente:
                if (operadores.contains("" + E.peek())) { //Comsulta el dato de la cima de la pila de entrada (E) y si se encuentra contenido en la cadena operadores, realiza lo siguiente:
                    cont++; //Incrementa el contador
                    T.push(Concatenar(E.pop(), T.pop(), T.pop()) + ""); //Extrae el operador de la pila E, extrae el último y el anterior operando de la pila T y los envía a la función "Cocatenar"; el resultado que devuelve dicha función, es ingresada a la pila T
                    System.out.println("T"+cont+"= "+T.pop()); //Imprimimos la extracción del último elemento ingresado a la pila T, es decir, el ingresado en la instrucción anterior
                    T.push("T"+cont); //Ingresa el equivalente de la subexpresión a la pila T
                }else { //En caso contrario:
                    T.push(E.pop()); //Guarda los operandos de la pila E a la pila T
                }
            }
            
        }catch(Exception ex){
            System.out.println("ERROR -> La expresión algebraica es incorrecta");
            System.err.println(ex);
        }
    }
  
    //Método que corrige la expresión algebraica introducida para eliminar los espacios en blanco de exceso y reducirlos a uno solo
    private static String CorregirExpresion(String e) {
        e = e.replaceAll("\\s+", ""); //Reemplaza por vacío donde haya 1 o más espacios en blanco
        e = "("+e+")"; //Agregamos paréntesis externos para delimitar el inicio y fin de nuestra expresión algebraica 
        String operadores = "+-*/()";
        String ne = "";
        for (int i = 0; i < e.length(); i++) { //Recorremos el tamaño de la expresion algebraica
            if (operadores.contains(""+e.charAt(i))) { //Buscamos si hay coincidencias por cada letra de la expresión con la de la cadena operadores   
                ne+= " "+e.charAt(i)+" "; //Si es verdadero, agregamos un espacio antes y después de cada operador dentro de la expresión
            }else
                ne += e.charAt(i); //Si es falso, sólo agregamos el operando a nuestra nueva cadena
        }
        ne = ne.replaceAll("\\s+", " ").trim(); //Reducimos a un sólo espacio en blanco donde hayan 2 o más, y además eliminamos los espacios al inicio y final de la expresión
        return ne; //Retornamos nuestra expresión ya separada por espacios en blanco
    }
    
    //Devuelve el número de orden de acuerdo a la jerarquia de los operadores en postfija
    private static int Jerarquia(String operador) {
        int orden=0; //Declaramos nuestra variable orden inicializada en 0
        if (operador.equals("(")) orden = 1; //Si el operador es "(", la variable "orden" toma el valor de 1
        if (operador.equals(")")) orden = 2; //Si el operador es ")", la variable "orden" toma el valor de 2
        if (operador.equals("+") || operador.equals("-")) orden = 3; //Si el operador es "+" ó "-", la variable "orden" toma el valor de 3
        if (operador.equals("*") || operador.equals("/")) orden = 4; //Si el operador es "*" ó "/", la variable "orden" toma el valor de 4
    return orden; //Retornamos el valor de la variable "orden"
    }
    
    //Método que se encarga de concatenar los valores de la expresión de acuerdo a los operadores
    private static String Concatenar(String op, String v2, String v1) {
        if (op.equals("+")) return (v1+"+"+v2); //Si el operador es suma, retorna la concatenación de la variable 1 y la variable 2 con el símbolo "+" al medio de los dos
        if (op.equals("-")) return (v1+"-"+v2); //Si el operador es resta, retorna la concatenación de la variable 1 y la variable 2 con el símbolo "-" al medio de los dos
        if (op.equals("*")) return (v1+"*"+v2); //Si el operador es multiplicación, retorna la concatenación de la variable 1 y la variable 2 con el símbolo "*" al medio de los dos
        if (op.equals("/")) return (v1+"/"+v2); //Si el operador es división, retorna la concatenación de la variable 1 y la variable 2 con el símbolo "/" al medio de los dos
    return "";
    }
    
}
