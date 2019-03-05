# JavaWebApplication
Пример вызова публичных статических методов из JS кода 

<pre>
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
/**
 * Тестовый класс
 * @author root
 */
public class NewClass {
    /// метод для вызова из JS   
    public static Map<String,Object> GetJson(String name) {
      Map<String,Object>FunctionCalBackValue=new HashMap<String,Object>();
      FunctionCalBackValue.put("key1" ,new String("unJSName"));
      FunctionCalBackValue.put("key2" ,222222);
      FunctionCalBackValue.put("key3" ,111111);
      FunctionCalBackValue.put("args" ,name);
      FunctionCalBackValue.put("Рус" ,"фывафывафывафыва");
      return FunctionCalBackValue;
    }
    
    // при передачи чисел из JS они преобразуются в тип BigDecimal
    public static void printDog(String name, BigDecimal age) {
       System.out.println(name + " is " + age + " year(s).");
    }
}
</pre>


Подключение библиотеки и класса
<pre>
    <script language="JavaScript" type="text/javascript" src="/WebApplication/SendServer"></script> 
    <script language="JavaScript" type="text/javascript" src="/WebApplication/SendServer?include=NewClass"></script> 
</pre>

Вызов в JS
<pre>
   var res=document.getElementById("ResQuery"); 
   res.innerHTML='';
   res.innerHTML+='\n'+SendServer("NewClass.GetJson","Вариант вызова 1",) ;
   SendServer("NewClass.GetJson","Вариант вызова 2(Асинхронно)",function(txt){res.innerHTML+='\n'+txt;} ) ;
   // Для вызова вариантом 3 и 4 необходимо подключить класс как библиотеку  <script language="JavaScript" type="text/javascript" src="/WebApplication/SendServer?include=NewClass"></ script> 
   res.innerHTML+='\n'+GetJson("Вариант вызова 3") ;
   GetJson("Вариант вызова 4(Асинхронно)",function(txt){res.innerHTML+='\n'+txt;} ) ;
   res.innerHTML+='\n'+ SendServer("NewClass.printDog","ПесГраф" , 44); 
   res.innerHTML+='\n';
</pre>
