/*
Programacion de bajo nivel
Proyecto Integrador | Parte 1

    Integrantes de este codigo 
    -Martinez Isaac
    -Hernandez Gutierrez Emmanuel
    -Jimenez Castellanos Jesus Alejandro
*/

package proyectointegrador_equipo10;

//Libreria
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProyectoIntegrador_Equipo10 {

    public static void main(String[] args) {
        
        String Archivo = ("P1ASM.asm"); //Variable auxiliar para leer el archivo
    
        //Leer el archivo P1ASM.asm
        try { //Leer archivo
            BufferedReader Read = new BufferedReader(new FileReader(Archivo)); //Leer el archivo P1ASM.asm
            String Linea; //Variable auxiliar, todo lo que se lee se guarda en este variable
            
            //Guardar cada linea en la variable Linea
            while((Linea = Read.readLine()) != null) {
                
                //Inicializar Variables
                String Etiqueta = null;
                String Codop = null;
                String Operando = null;      
                
                Linea = Linea.trim();
                //.trim evita los espacios o los tabuladores que hay de una palabra a otra y pasa directamente hacia la siguiente palabra
                 
                //Validar linea de comentario
                if(Linea.isEmpty()) { //Si la linea esta vacia
                    continue; //Continuar con las demas lineas
                }//Fin de if 
                
               // Validar si es comentario
                else if (Linea.startsWith(";")) { // Si la línea empieza con ;
                    if (!Linea.matches("^;.{0,80}$")) { // Verificar que después del ";" pueda tener hasta 80 caracteres
                        System.out.println("Error de comentario");
                    } //Fin de if 
                    else { //Entonces hay menos de 80 caracteres 
                        System.out.println("COMENTARIO \n"); // Escribir comentario
                    } //Fin de else 
                } // Fin de else if
              
                //Valifacion para terminar el archivo
                else if(Linea.equalsIgnoreCase("END")) {//Si la linea termina en "END", entonces deja de leer el archivo
                    //Impresion de las variables por default cuando encuentre END
                    System.out.println("ETIQUETA = null");
                    System.out.println("CODOP = END");
                    System.out.println("OPERANDO = null");
                    Read.close(); // Funcion para cerrar el archivo de lectura
                    break; //El break indica el fin del ciclo 
                } //Fin de else if
                
                //Leer palabras 
                else {
                    String[] Palabras = Linea.split("\\s+"); //Arreglo que parte los espacios
                    //Palabras es un arreglo que guardara las palabras que esten separadas por espacio en la variable linea
                                        
                    /*Funcion Split: Se utiliza para dividir la cadena en partes, en este caso mediante espacios "\\S+"
                    Esta funcion nos proporciona palabra por palabra, es util en nuestro codigo porque asi podemos
                    guardar dichas palabras en las variables de Etiqueta, Codop y Operando.
                    \\s+ : Significa espacios " " */                                                       

                    for(String Palabra : Palabras) {
                        //Validar Etiqueta
                        if(Palabra.endsWith(":")) { //Validacion para etiqueta          
                            Etiqueta = Palabra; //La palabra identificada se guardara en el String Etiqueta
                                                   
                            if (Etiqueta.length() >= 8 || cracteretq(Etiqueta) != true && valespacios(Etiqueta) != true) { //Validador de longitud maximo 8 caracteres
                                System.out.println("Error de Etiqueta: La etiqueta '" + Etiqueta + "' excede la longitud maxima de 8 caracteres.");
                                Etiqueta = "Error"; // Restablecer etiqueta solo si excede la longitud máxima
                            } //Fin de if            
                        } //Fin de if
                        
                        //Las variables ya estan inicializadas en null, por lo tanto siempre entra a la condicion
                        //Validacion CODOP
                        else if(Codop == null) { //Si el codigo operando es igual a null                        
                            Codop = Palabra; //La palabra identificada se guardara en el String Codop

                            //Validar que la palabra comience con una letra en mayúscula o minúscula
                            //Su longitud maxima es de 5 caracteres
                            //Cualquier otro caracter es un error
                            Pattern patron = Pattern.compile("[a-zA-Z.]{0,5}+$"); //Crear un patron con minusculas, mayusculas, puntos y con una longitud de 0 a 5 caracteres                 
                            Matcher matcher = patron.matcher(Palabra); // Crear un Matcher para verificar si Palabra cumple con el patrón

                            if (!matcher.matches() && codops(Codop) != true) { // Verificar si la palabra no cumple con el patrón
                               System.out.println("Error Codop: El Codop '" + Codop + "' excede la longitud maxima de caracteres o contiene un simbolo invalidado"); //Si no cumple el patron entonces manda un mensaje de error
                               Codop = "Error"; //Mensaje de error
                            } //Fin de if       
                        } //Fin de else if
                        
                        //Validacion Operando
                        //Pueden comenzar con cualquier caracter
                        //Pueden tener cualquier longitud
                        else if(Operando == null) { //Si el operando es igual a null
                                Operando = Palabra; //La palabra identificada se guardara en el String Operando
                            } //Fin de else if                       
                        } //Fin de for                                         
                    
                       // Validar espacios en blanco o tabuladores en Etiqueta, CODOP y Operando
                       if (Etiqueta != null && (Etiqueta.contains(" ") || Etiqueta.contains("\t"))) {
                           System.out.println("Error Etiqueta: La etiqueta contiene espacios en blanco o tabuladores");
                           Etiqueta = null; // Restablecer etiqueta si es inválida
                       } //Fin de if                  
                    
                    //Impresion de las variables
                    System.out.println("ETIQUETA = " + Etiqueta);
                    System.out.println("CODOP = " + Codop);
                    System.out.println("OPERANDO = " + Operando + "\n");
                } //Fin de else 
            } //Fin de while       
        
        } //Fin de try                        
        catch (Exception e) { //Mostrar mensaje de error
            System.out.println("Error " + e.getMessage()); //Mensaje de error
        } //Fin de catch
        
    } //Fin de main 
    static boolean valespacios(String y){
        String cet2 = ":\t\s";//Caracteres permitidos en la comparacion.
        for(int i=0; i < y.length();i++){//Recorre la cadena y, que se ingresa a la funcion. 
            char vals = y.charAt(i);//Se recorre la cadena caracter por caracter en la pisicion de i.
            if(cet2.indexOf(vals) == -1){//Se revisa si los caracteres si estan en cet2.
                return false;
            } //Fin de if
        } //Fin de for
        return true;
    }//Fin vlaespacios
    
    static boolean cracteretq(String x){
        String cet1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789:_";//Caracteres permitidos en la comparacion.
        String etqupper = x.toUpperCase();//Convierte la variable en mayusculas para la comparacion.
        for(int i=0; i < etqupper.length(); i++){//Recorre la cadena x, que se ingresa a la funcion.
            char charetq = etqupper.charAt(i);//Se recorre la cadena caracter por caracter en la pisicion de i.
            if(cet1.indexOf(charetq) == -1){//Se revisa si los caracteres si estan en cet1.
                return false;
            } //Fin de if
        } //Fin de for
        return true;
    }//Fin de caracteretq
    
    static boolean codops(String o){
        String cet3 = "\t\s";//Caracteres permitidos en la comparacion.
        for(int i=0; i < o.length();i++){//Recorre la cadena o, que se ingresa a la funcion.
            char vals = o.charAt(i);//Se recorre la cadena caracter por caracter en la pisicion de i.
            if(cet3.indexOf(vals) == -1){//Se revisa si los caracteres si estan en cet3.
                return false;
            } //Fin de if
        } //Fin de for
        return true;
    }//FIn de codops
    
} //Fin de la clase