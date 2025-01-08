# Servlet Çağrısı

## Türkçe

- **HTTP GET isteği**: `http://<host>:<port>/MemoryLeakApp/memoryleak` adresine bir HTTP GET isteği gönderildiğinde, servlet çalışır.

# Bellek Tüketimi

## Türkçe

- **doGet metodu**: Servlet'in `doGet` metodu çağrıldığında, her döngüde `memoryLeak` listesine 10 MB büyüklüğünde bir `byte[]` eklenir.
- **Döngü**: Döngü 1000 kez çalıştığı için toplamda yaklaşık 10 GB bellek tüketimi yapılmaya çalışılır.

# Yanıt (Response)

## Türkçe

- Servlet başlığına `resp.setHeader("Java-Developer", "mertugral");` ile özel bir bilgi eklenir.
- Yanıt olarak `resp.getWriter().write()` ile mevcut listenin büyüklüğü döndürülür:

```text
Memory leak test ongoing... Current size: 1000 MB

> JVM'inizin Heap Size sınırına ulaştığında OutOfMemoryError oluşur.
Eğer -XX:+HeapDumpOnOutOfMemoryError JVM parametresi tanımlıysa, bir Heap Dump dosyası oluşturulur.

---
HTTP GET Request: When an HTTP GET request is sent to http://<host>:<port>/MemoryLeakApp/memoryleak, the servlet executes.
Memory Consumption

doGet Method: When the doGet method of the servlet is called, a byte[] of 10 MB is added to the memoryLeak list in each iteration.
Loop: Since the loop runs 1000 times, approximately 10 GB of memory is attempted to be consumed.
Response

A custom information is added to the servlet header with resp.setHeader("Java-Developer", "mertugral");.
The current size of the list is returned as a response using resp.getWriter().write():
text

Memory leak test ongoing... Current size: 1000 MB
Result
English
An OutOfMemoryError occurs when your JVM's Heap Size limit is reached.
If the -XX:+HeapDumpOnOutOfMemoryError JVM parameter is set, a Heap Dump file is created.
