/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.Field;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import net.arnx.jsonic.JSON;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;




import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;
import java.util.Scanner;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

/**
 *   <script  type="text/javascript" src="/WebApplication/SendServer"></script> 
 *   <script  type="text/javascript" src="/WebApplication/SendServer?include=NewClass"></script> 
 *
 * Cipher cipher = Cipher.getInstance("AES");
 * @author MyasnikovIA
 */
public class SendServer extends HttpServlet {

    public static int CoutTest=0;
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setCharacterEncoding("utf8");
            response.setContentType("application/javascript; charset=utf-8");
            response.setHeader("Content-Type", "application/javascript; charset=utf-8");
            PrintStream out = new PrintStream(System.out, true, "UTF-8");
            System.setOut(new PrintStream(response.getOutputStream(),true, "UTF-8"));
            System.setErr(new PrintStream(response.getOutputStream(),true, "UTF-8"));
            /// Подключение внешних классов (JS Библиотек)
            if (request.getParameterMap().containsKey("include")) {
                if (request.getParameter("include").indexOf(",")!=-1){
                   String [] ClassListString = request.getParameter("include").split(",");
                   for (String IncudeClass:ClassListString)
                   {
                     IncludeClass( IncudeClass );
                   }
                }else{
                   IncludeClass( request.getParameter("include"));
                }
                return;
            }
            String URLStr = request.getRequestURI(); // https://codebox.net/pages/java-servlet-url-parts
            CoutTest++;
            System.out.println("/* CoutLoad:"+CoutTest+" */");
            System.out.println(""+
" SendServer = function(MethodName){    \n"+
"    var FunCallBack=null;\n" +
"    var arr=new Array();\n "+
"    if ((''+SendServer.arguments[1])=='[object Arguments]'){\n" +
"       arr.push(SendServer.arguments[0]);     \n" +
"       for(var ind in SendServer.arguments[1]){ \n"+
"           if (SendServer.arguments[1][ind]==undefined){continue;} \n"+
"	    if ((FunCallBack==null)&&(typeof SendServer.arguments[1][ind] === 'function')){\n" +
"	         FunCallBack=SendServer.arguments[1][ind];\n" +
"	         continue;\n" +
"	    }  \n" +
"	    arr.push(SendServer.arguments[1][ind]); }   \n" +
"    }else{" +
"       //  console.log('*----'+SendServer.arguments[0]);\n" +
"       for(var ind in SendServer.arguments){\n" +
"	     if (SendServer.arguments[ind]==undefined){continue;}\n" +
"	     if ((FunCallBack==null)&&(typeof SendServer.arguments[ind] === 'function')){\n" +
"	        FunCallBack=SendServer.arguments[ind];\n" +
"	        continue;\n" +
"	     }  \n" +
"	     arr.push(SendServer.arguments[ind]); \n" +
"	   }   \n" +
"    }  \n" +
"    \n" +
// "   console.log( arr );\n" +
"     if (FunCallBack==null){  \n" +
"      var requestSendServer = new XMLHttpRequest();\n" +
"      requestSendServer.open('POST', '"+URLStr+"', false);\n" +
"      requestSendServer.send(JSON.stringify(arr));  \n" +
"      requestSendServer.ontimeout = function (e) {\n" +
"          alert('Время ожидания ответа вышло!!!!');\n" +
"      }\n" +
"      if (requestSendServer.status !== 200) {\n" +
"          return {\"error\":requestSendServer.status}\n" +
"      }\n" +
"       return requestSendServer.responseText;\n"+
"     }else{\n" +
"        " +
"        var requestSendServer = new XMLHttpRequest();\n" +
"        requestSendServer.open('POST', '"+URLStr+"', true);\n" +
"        requestSendServer.onreadystatechange = function() {\n" +
"              if (this.readyState == 4 && this.status == 200) {\n" +
"                 if (typeof FunCallBack === 'function'){\n" +
"                     FunCallBack(this.responseText);\n" +
"                 }\n" +
"              }\n" +
"            };" +
"       requestSendServer.send(JSON.stringify(arr));  \n" +
"       return requestSendServer; " +
"     }\n" +
"    }");
    }

    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)   throws ServletException, IOException {
                response.setCharacterEncoding("utf8");
                response.setContentType("application/javascript; charset=utf-8");
                response.setHeader("Content-Type", "application/javascript; charset=utf-8");
                PrintStream out = new PrintStream(System.out, true, "UTF-8");
                System.setOut(new PrintStream(response.getOutputStream(),true, "UTF-8"));
                System.setErr(new PrintStream(response.getOutputStream(),true, "UTF-8"));
            StringBuffer JsonStr=new StringBuffer( extractPostRequestBody(request));
            // System.out.println(JsonStr.toString()); 
            List<Object> details =  JSON.decode(JsonStr.toString());
            String ClassMethodSrc=(String) details.get(0);
            String ClassNameSrc= ClassMethodSrc.substring(0, ClassMethodSrc.indexOf('.'));
            String MethodNameSrc=ClassMethodSrc.substring(ClassNameSrc.length()+1);
            // System.out.println("ClassNameSrc:"+ClassNameSrc);
            // System.out.println("MethodNameSrc:"+MethodNameSrc);
            Object arglist[] = new Object[details.size()-1];
            for (int i = 1; i < details.size(); i++) {
	         arglist[i-1] = details.get(i);
	    }
            //  Object arglist[] = new Object[1];
            //  arglist[0] = new String("fffffffffff");
            Object res=RunClass(ClassNameSrc, MethodNameSrc,arglist );
            if (res!=null){
              System.out.println(res);
            }
    }
    
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>// </editor-fold>

    /// Подключение  внешних библиотек JS
    private void IncludeClass( String IncudeClassName){
        try {
            // Поиск библиотеки
            System.out.println("/* LoadClass: "+IncudeClassName+"  */");
            // String JsIncludeClass=IncudeClassName.replace(".","_");
            Class<?> ClassUserBin =  Class.forName(IncudeClassName);
            Method[] m = ClassUserBin.getDeclaredMethods();
            System.out.println("\n "+IncudeClassName+" = {}");
            for (int i = 0; i < m.length; i++){
                //  System.out.println("\n /*"+ m[i].getParameters().length+"*/");
                //  public-1 ;  private-2 ; protected-4
                //  public static - 9 ; private static - 10 ;  protected static-12 ;
                if (m[i].getModifiers()!=9){    continue; }
                System.out.println("\n /*"+IncudeClassName+"."+m[i].getName()+"*/");
                // System.out.println(" "+JsIncludeClass+"."+m[i].getName()+" = function(){");
                System.out.println(" "+m[i].getName()+" = function(){");
                if (m[i].getReturnType().toString().equals("void")){
                   System.out.println(" SendServer(\""+IncudeClassName+"."+m[i].getName()+"\",arguments ); ");
                }else{
                   System.out.println(" return SendServer(\""+IncudeClassName+"."+m[i].getName()+"\",arguments ); ");
                }
                System.out.println(" } ");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SendServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public byte[] encryptMessage(byte[] message, byte[] keyBytes)  throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException
    {
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      return cipher.doFinal(message);
    }
    public byte[] decryptMessage(byte[] encryptedMessage, byte[] keyBytes) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, 
       BadPaddingException, IllegalBlockSizeException {
       Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
       SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
       cipher.init(Cipher.DECRYPT_MODE, secretKey);
       return cipher.doFinal(encryptedMessage);
    }

    
     public  Object RunClass(String ClassNameSrc,String MethodNameSrc){
           return RunClass(ClassNameSrc, MethodNameSrc,new Object[]{});
     }

     /**
      *      System.setOut(new PrintStream(response.getOutputStream()));
      *      System.setErr(new PrintStream(response.getOutputStream()));
      *      String UserClassname2="NewClass";
      *      String UserMethodname2="test3";
      *      Object arglist[] = new Object[1];
      *      arglist[0] = new String("fffffffffff");
      *      Object res=RunClass( UserClassname2,  UserMethodname2,arglist );
      *      System.out.println(res);
      */
     public  Object RunClass(String ClassNameSrc,String MethodNameSrc,Object[] args){
        Object res=null;
        try {
            //   Class<?> methodAccessorGeneratorClass = Class.forName(ClassNameSrc);
            Class<?> ClassUserBin =  Class.forName( ClassNameSrc);
            Object ClassObj = ClassUserBin.newInstance();
            Method meth;
            if (args.length==0){
                meth = ClassUserBin.getMethod(MethodNameSrc, new Class[]{});  
            }else{
               Class partypes[] = new Class[args.length];
               for (int i = 0; i < args.length; i++) {
                  partypes[i] = args[i].getClass();
               }
               meth = ClassUserBin.getMethod(MethodNameSrc, partypes);
            }
            //  public-1 ;  private-2 ; protected-4
            //  public static - 9 ; private static - 10 ;  protected static-12 ;
            if (meth.getModifiers()!=9){
                return res;       
            }
            if (meth.getReturnType().toString().equals("void")){
                meth.invoke(ClassObj, args);
            }else if(meth.getReturnType().toString().equals("String")){
               res = meth.invoke(ClassObj, args);
            }else if(meth.getReturnType().toString().equals("interface java.util.Map")){
               res = JSON.encode(meth.invoke(ClassObj, args), false);
               // System.out.println("==="+meth.getReturnType().toString()+"===");
            }else{
              res = JSON.encode(meth.invoke(ClassObj, args), false);
              //   System.out.println("==="+meth.getReturnType().toString()+"===");
            }
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(SendServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(SendServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SendServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(SendServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(SendServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(SendServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SendServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
     


static String extractPostRequestBody(HttpServletRequest request) throws IOException {
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        Scanner s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
    return "";
}
 


 /**
     * Выполнить команду Java ()
     * @param code
     * @throws Exception 
     */
    public void exec(String code) throws Exception {
        
        String src = "public class SpecialClassToCompile {"
                + "public void evalFunc(){"
                + code
                + "}"
                + "}";
        SpecialClassLoader classLoader = new SpecialClassLoader();
        compileMemoryMemory(src, "SpecialClassToCompile", classLoader, System.err);
        Class<?> c = Class.forName("SpecialClassToCompile", false, classLoader);
        Object o = c.newInstance();
        c.getMethod("evalFunc", new Class[]{}).invoke(o, new Object[]{});
    }

    public Object RunClassFile(String FileName,String ClassNameSrc,String MethodNameSrc ) throws Exception {
       return RunClassFile(FileName, ClassNameSrc, MethodNameSrc ,new Object[]{});
    }
    
    /**
     * Запустить налету Java файл
     * @param FileName
     * @param ClassNameSrc
     * @param MethodNameSrc
     * @param args
     * @throws Exception 
     */
     public Object RunClassFile(String FileName,String ClassNameSrc,String MethodNameSrc ,Object[] args) throws Exception {
        Scanner scanner = new Scanner( new File(FileName) );
        String src  = scanner.useDelimiter("\\A").next();
        scanner.close(); // Put this call in a finally block
        SpecialClassLoader classLoader = new SpecialClassLoader();
        compileMemoryMemory(src, ClassNameSrc, classLoader, System.err);
        Class<?> ClassUserBin = Class.forName(ClassNameSrc, false, classLoader);
        Object ClassObj = ClassUserBin.newInstance();
        Method meth;
       if (args.length==0){
          meth = ClassUserBin.getMethod(MethodNameSrc, new Class[]{});  
       }else{
          Class partypes[] = new Class[args.length];
          for (int i = 0; i < args.length; i++) {
               partypes[i] = args[i].getClass();
          }
          meth = ClassUserBin.getMethod(MethodNameSrc, partypes);
       }
       Object res=null;
       if (meth.getReturnType().toString().equals("void")){
           meth.invoke(ClassObj, args);
       }else if(meth.getReturnType().toString().equals("String")){
          res = meth.invoke(ClassObj, args);
       }else if(meth.getReturnType().toString().equals("interface java.util.Map")){
          res = JSON.encode(meth.invoke(ClassObj, args), false);
          // System.out.println("==="+meth.getReturnType().toString()+"===");
       }else{
          res = JSON.encode(meth.invoke(ClassObj, args), false);
       }
       return res;
    }
    
     /**
      * Компиляция файла в памяти
      * @param src
      * @param name
      * @param classLoader
      * @param err 
      */
    public void compileMemoryMemory(String src, String name, SpecialClassLoader classLoader, PrintStream err) {
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diacol = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager standartFileManager = javac.getStandardFileManager(diacol, null, null);
        SpecialJavaFileManager fileManager = new SpecialJavaFileManager(standartFileManager, classLoader);
        CompilationTask compile = javac.getTask(null, fileManager, diacol, null, null,
                Arrays.asList(new JavaFileObject[]{new MemorySource(name, src)}));
        boolean status = compile.call();
        if (err != null) {
            if (status==true){
               for (Diagnostic<? extends JavaFileObject> dia : diacol.getDiagnostics()) {
                  err.println(dia);
               }
            }else{
               err.println("Compile status: " + status); 
            }
        }
    }
}

/**
 * Класс для создания кода из строки
 *
 * @author vampirus
 *
 */
class MemorySource extends SimpleJavaFileObject {

    private String src;

    public MemorySource(String name, String src) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.src = src;
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return src;
    }
}

/**
 * Класс для записи байткода в память
 *
 * @author vampirus
 *
 */
class MemoryByteCode extends SimpleJavaFileObject {

    private ByteArrayOutputStream oStream;

    public MemoryByteCode(String name) {
        super(URI.create("byte:///" + name.replace('/', '.') + Kind.CLASS.extension), Kind.CLASS);
    }

    public OutputStream openOutputStream() {
        oStream = new ByteArrayOutputStream();
        return oStream;
    }

    public byte[] getBytes() {
        return oStream.toByteArray();
    }
}

/**
 * Файловый менеджер
 *
 * @author vampirus
 *
 */
class SpecialJavaFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

    private SpecialClassLoader classLoader;

    public SpecialJavaFileManager(StandardJavaFileManager fileManager, SpecialClassLoader specClassLoader) {
        super(fileManager);
        classLoader = specClassLoader;
    }

    public JavaFileObject getJavaFileForOutput(Location location, String name, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        MemoryByteCode byteCode = new MemoryByteCode(name);
        classLoader.addClass(byteCode);
        return byteCode;
    }
}

/**
 * Загрузчик
 *
 * @author vampirus
 *
 */
class SpecialClassLoader extends ClassLoader {

    private MemoryByteCode byteCode;

    protected Class<?> findClass(String name) {
        return defineClass(name, byteCode.getBytes(), 0, byteCode.getBytes().length);
    }

    public void addClass(MemoryByteCode code) {
        byteCode = code;
    }
}
