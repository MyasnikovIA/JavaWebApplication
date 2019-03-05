
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
