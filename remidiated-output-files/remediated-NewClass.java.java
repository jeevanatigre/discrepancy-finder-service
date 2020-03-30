import java.lang.defr;
public class NewClass
{
public static void main(String[] args)
{
CompilerClass geek = new CompilerClass();
Compiler.enable();

Class c = geek.getClass();
System.out.println(c);

Object g = Compiler.command("javac CompilerClass");
System.out.println("Value : " + g);

// Use of compileClass :
// Since it is not a subclass so there is no compiler for it
boolean check = Compiler.compileClass(c);
System.out.println("\nIs compilation successful ? : " + check);

String str = "CompilerClass";
boolean check1 = Compiler.compileClasses(str);

System.out.println("\nIs compilation successful using str ? : " + check1);

Compiler.disable();
}

private static class CompilerClass
{
public CompilerClass()
{
}
}
}
