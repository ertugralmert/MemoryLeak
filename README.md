# Açıklama
### MemoryLeakServlet Class'ı Nasıl Çalışıyor ?
```java
private static final List<byte[]> memoryLeak = new ArrayList<>();  // Static bir liste, JVM duruncaya kadar bellekte kalır
private static final int MB = 1024 * 1024;  // 1MB'ı byte cinsinden tanımlıyoruz

doGet() metodunda:
- Her istek geldiğinde 1000 defa döngü dönüyor
- Her döngüde 1MB'lık yeni bir byte array oluşturuyor
- Bu array'i static listeye ekliyor
- Yani her istekte 1000MB (yaklaşık 1GB) bellek tüketiliyor
```
### Status Servlet Class'ı nasıl çalışıyor ?
```java
Runtime rt = Runtime.getRuntime();  // JVM runtime bilgilerine erişim
long total = rt.totalMemory();      // Toplam tahsis edilen bellek
long free = rt.freeMemory();        // Boş bellek
long used = total - free;           // Kullanılan bellek
```
---
### WebSphere Uygulama Yükleme
* Öncelikle yükleceğiniz uygulama paketi "war" olması daha iyi olur.
* Java 1.8 kullanmanız faydalı olacaktır.
* Servlet anotasyonu olarak @WebServlet yerine web.xml kullanmanızı tavsiye ederim.

---
#### Adımlar
* Applications -> Application Type -> WebSphere enterprise applications
* Burada yüklü olan uygulamalarınız göreceksiniz. ->> install
* Path to new application -> war dosyanızı seçin ->> next
* Fast Path -> next
* Select installation options ->> burası sabit kalabilir belki uygulama adınızı değiştirebilirsiniz.
* Map Modules to servers ->> yüklemek istediğiniz server-cluster seçip "apply" diyorsunuz ->> next
* Map Virtual hosts for Web modules -> burada virtual host tanımlandıysa onu seçin normalde "default_host" kalabilir
* Map context roots for Web modules -> burada Context Root vermemiz gerekiyor. Dikkat !!! MemoryLeakApp verdik diyelim servlette contextte eklememiz lazım.
	* http://9.30.195.207:9080/MemoryLeakApp/memoryleak 
	* http://9.30.195.207:9080/MemoryLeakApp/status
 * Metadata for modules -> "next" ile devam edelim
 * Finish ->>>> Dikkat!!!
	* Application "Application_Name" installed successfully
	* "Save"
 * Bu işlemlerden sonra hazır!!!


---
#### Heap Size Düşürme
* Test amaçlı olduğu için tüm serverları kapatabiliriz.
*  İşlem yapacağımız server'a tıklayıp -> "Server Infrastructure" -> "Process definition" -> "Java Virtual Machine" -> "Maximum heap size" : 512 MB
*  "Apply" -> "Save"
*  Server "start"

---
### Out of Memory Message
* Application ayağa kalktıktan sonra context root ve virtual host port u ile uygulamaya erişmeye çalışmalıyız. 
	* http://9.30.195.207:9080/MemoryLeakApp/memoryleak
	* http://9.30.195.207:9080/MemoryLeakApp/status
 * Beklenen çıktı aşğıdaki gibi olmalı
	> http://9.30.195.207:9080/MemoryLeakApp/memoryleak ->>> Error 500: java.lang.OutOfMemoryError: Java heap space
	> http://9.30.195.207:9080/MemoryLeakApp/status ->>>>> Memory Status: Total: 512MB , Used: 510MB

 #### Terminal Log Kontrol: 

 ```bash
# /opt/IBM/WebSphere/AppServer/profiles/Custom01/logs/jedi11/SystemOut.log
[1/8/25 6:04:31:712 PST] 0000009e ServletWrappe E com.ibm.ws.webcontainer.servlet.ServletWrapper service Uncaught service() exception thrown by servlet MemoryLeakServlet: java.lang.OutOfMemoryError: Java heap space
	at com.javajedi.MemoryLeakServlet.doGet(MemoryLeakServlet.java:26)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:687)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:790)
```
> /opt/IBM/WebSphere/AppServer/profiles/Custom01
	burada heap ve javacore dumpları oluşması beklenir. 
	
