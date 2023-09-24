//Proyecto de prueba
package proyectointegrador_equipo10;

//Librerias
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import javax.swing.table.DefaultTableModel;

public class ProyectoIntegradorP2_Equipo10 {

    public static void main(String[] args) {
    
    String Archivo = ("P1ASM.asm"); //Variable auxiliar para leer el archivo
    
    //Instanciar objeto linea con variables inicializadas en null
    Linea linea = new Linea(null , null , null, null, null); // Instanciar objeto Linea
    
    //Leer el archivo 
    try { //Leer archivo
        BufferedReader Read = new BufferedReader(new FileReader(Archivo)); //Leer el archivo P1ASM.asm
        String Linea; //Variable auxiliar, todo lo que se lee se guarda en este variable

        //Guardar cada linea en la variable Linea
        while((Linea = Read.readLine()) != null) {
            
        //Inicializar objetos en null para las iteraciones 
        linea.setEtiqueta(null);
        linea.setCodop(null);
        linea.setOperando(null);
        linea.setDireccion(null);
        linea.setTamaño(null);

            Linea = Linea.trim();
            //.trim evita los espacios o los tabuladores que hay de una palabra a otra y pasa directamente hacia la siguiente palabra

            //Validar linea de comentario
            if(Linea.isEmpty()) { //Si la linea esta vacia
                continue; //Continuar con las demas lineas
            }//Fin de if 

           // Validar si es comentario
            else if (Linea.startsWith(";")) { // Si la línea empieza con ;
                if (!Linea.matches("^;.{0,80}$")) { // Verificar que después del ";" pueda tener hasta 80 caracteres
                    System.out.println("Error de comentario\n");
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
                System.out.println("DIRECCION = null");
                System.out.println("TAMANO = null" + "\n");
                Read.close(); // Funcion para cerrar el archivo de lectura
                break; //El break indica el fin del ciclo 
            } //Fin de else if

            //Leer palabras 
            else {
                String[] Palabras = Linea.split("\\s+"); //Arreglo que parte los espacios
                /* - Palabras es un arreglo que guardara las palabras que esten separadas por espacio en la variable linea                                       
                /  - Funcion Split: Se utiliza para dividir la cadena en partes, en este caso mediante espacios "\\S+"
                     Esta funcion nos proporciona palabra por palabra, es util en nuestro codigo porque asi podemos
                     guardar dichas palabras en las variables de Etiqueta, Codop y Operando.
                   - s+ : Significa espacios " " */                                                       

                for(String Palabra : Palabras) {

                    Pattern patronetiqueta = Pattern.compile("[a-zA-Z][0-9]{0,8}+$"); //Crear un patron con minusculas, mayusculas, puntos y con una longitud de 0 a 8 caracteres                 
                    Matcher matcheretiqueta = patronetiqueta.matcher(Palabra); // Crear un Matcher para verificar si la Palabra cumple con el patrón

                    //Validar etiqueta
                    if(Palabra.endsWith(":")) {
                        Palabra = Palabra.substring(0, Palabra.length() - 1 ); //Eliminar ":" de la palabra etiqueta
                        if (!matcheretiqueta.matches()) { //Validador de longitud maximo 8 caracteres                       
                        linea.setEtiqueta(Palabra); //La palabra identificada se guardara en el objrto etiqueta
                        } //Fin de if
                        else {
                            System.out.println("Error de Etiqueta: La etiqueta '" + linea.getEtiqueta() + "' excede la longitud maxima de 8 caracteres."); 
                            linea.setEtiqueta("Error"); // Restablecer etiqueta solo si excede la longitud máxima 
                        } //Fin de else 
                    } // Fin de if                                                                      

                    //Las variables ya estan inicializadas en null, por lo tanto siempre entra a la condicion
                    //Validacion CODOP
                    else if(linea.getCodop() == null) { //Si el codigo operando es igual a null                        
                        linea.setCodop(Palabra); //La palabra identificada se guardara en el objeto Codop

                        //Validar que la palabra comience con una letra en mayúscula o minúscula
                        //Su longitud maxima es de 5 caracteres
                        //Cualquier otro caracter es un error
                        Pattern patron = Pattern.compile("[a-zA-Z.]{0,5}+$"); //Crear un patron con minusculas, mayusculas, puntos y con una longitud de 0 a 5 caracteres                 
                        Matcher matcher = patron.matcher(Palabra); // Crear un Matcher para verificar si Palabra cumple con el patrón

                    if (!matcher.matches() && codops(linea.getCodop()) != true) { // Verificar si la palabra no cumple con el patrón
                           System.out.println("Error Codop: El Codop '" + linea.getCodop() + "' excede la longitud maxima de caracteres o contiene un simbolo invalidado"); //Si no cumple el patron entonces manda un mensaje de error
                           linea.setCodop("Error"); //Mensaje de error
                        } //Fin de if       
                    } //Fin de else if

                    //Validaciones de Operando                    
                    else if(linea.getOperando() == null) { //Si el operando es igual a null
                        
                        linea.setOperando(Palabra); //La palabra identificada se guardara en el objeto Operando
                        
                        //Validadores para identificar que tipo de opernado es
                        if(linea.getOperando().startsWith("%")) { //Si empieza con % entonces puede ser binario
                            if(!Metodos.IsBinario(linea.getOperando())) { //Validar si la sintaxis no es igual 
                                 linea.setOperando(linea.getOperando() + " No es binario"); //Mostrar mensaje de error
                            } //Fin de if sintaxis
                        } //Fin de if
                        else if(linea.getOperando().startsWith("$")) { //Validar si es hexadecimal
                            System.out.println("Holahexa");
                        } //Fin de else if
                        else if(linea.getOperando().startsWith("@")) { //Validar si octal
                            System.out.println("hola octal");
                        } //Fin de else if
                        else if(linea.getOperando().matches("\\d+")) { //Validar si es decimal 
                            Metodos.IsDecimal(linea.getOperando()); //Validar sintaxis de un decimal con un metodo
                            System.out.println("hola decimal");
                        } //Fin de else if
                        else { //Si no es nignuno de los posibles tipos de operandos, entonces es invalido
                            linea.setOperando("Error");
                        } //Fin de else if                        
                    } //Fin de else if                       
                } //Fin de for                                         

               // Validar espacios en blanco o tabuladores en Etiqueta, CODOP y Operando
               if (linea.getEtiqueta() != null && (linea.getEtiqueta().contains(" ") || linea.getEtiqueta().contains("\t"))) {
                   System.out.println("Error Etiqueta: La etiqueta contiene espacios en blanco o tabuladores");
                   linea.setEtiqueta(null); // Restablecer etiqueta si es inválida
               } //Fin de if                  

                //Impresion de las variables
                System.out.println("ETIQUETA = " + linea.getEtiqueta());
                System.out.println("CODOP = " + linea.getCodop());
                System.out.println("OPERANDO = " + linea.getOperando());
                System.out.println("DIRECCION = " + linea.getDireccion());
                System.out.println("TAMANO = " + linea.getTamaño() + "\n");
            } //Fin de else 
        } //Fin de while       
        
        } //Fin de try                        
        catch (Exception e) { //Mostrar mensaje de error
            System.out.println("Error " + e.getMessage()); //Mensaje de error
        } //Fin de catch       
    } //Fin de main 
    
    //Funciones 
    
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
    }//Fin de codops
   
} //Fin de la clase principal