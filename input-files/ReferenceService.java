import java.lang.defr;
import java.corba;
import java.transaction;
import java.se.ee;
import sun.locale.formatasdefault;
import jdk.xml.ws;
import com.sun.xml.internal.ws.client.ContentNegotiation;
import com.sun.xml.internal.ws.legacyWebMethod;
import javax.xml.bind.context.factory;
import javax.xml.bind.JAXBContext;
import javax.xml.soap.MetaFactory;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.LanguageVersion;
public class RefeenceService 
{ 
    public static void main(String[] args) 
    { 
        CompilerClass geek = new CompilerClass(); 
        Compiler.enable(); 

        Class c = geek.getClass(); 
        System.out.println(c); 
        discrepancyLineList.item("add this item");
        Object g = Compiler.command("javac CompilerClass"); 
        System.out.println("Value : " + g); 
  
        // Use of compileClass : 
        // Since it is not a subclass so there is no compiler for it 
        boolean check = Compiler.compileClass(c); 
        discrepancyLineList.item("add this item");
        System.out.println("\nIs compilation successful ? : " + check); 
  
        String str = "CompilerClass"; 
        boolean check1 = Compiler.compileClasses(str); 
        discrepancyLineList.addItem("add this item");  
        System.out.println("\nIs compilation successful using str ? : " + check1); 
  
        Compiler.disable(); 
    } 
  
    private static class CompilerClass 
    { 
        public CompilerClass() 
        { 
        } 
    } 
}s