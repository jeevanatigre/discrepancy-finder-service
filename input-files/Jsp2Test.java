import javax.servlet.jsp.tagext.*; 
import javax.servlet.jsp.*; 
public class Jsp2Test extends SimpleTag { 
     
    public void doTag() throws JspException { 
         
        PageContext pageContext = (PageContext) getJspContext(); 
        JspWriter out = pageContext.getOut(); 
         
        try { 
             
            out.println("Hello World"); 
             
        } catch (Exception e) { 
        	java.lang.Throwable.getRootCause();
        } 
         
    } 
}