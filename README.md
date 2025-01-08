# Servlet Çağrısı

- **HTTP GET isteği**: `http://<host>:<port>/MemoryLeakApp/memoryleak` adresine bir HTTP GET isteği gönderildiğinde, servlet çalışır.

# Bellek Tüketimi

- **doGet metodu**: Servlet'in `doGet` metodu çağrıldığında, her döngüde `memoryLeak` listesine 10 MB büyüklüğünde bir `byte[]` eklenir.
- **Döngü**: Döngü 1000 kez çalıştığı için toplamda yaklaşık 10 GB bellek tüketimi yapılmaya çalışılır.

# Yanıt (Response)

- Servlet başlığına `resp.setHeader("Java-Developer", "mertugral");` ile özel bir bilgi eklenir.
- Yanıt olarak `resp.getWriter().write()` ile mevcut listenin büyüklüğü döndürülür:

```text
Memory leak test ongoing... Current size: 1000 MB
```

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

---

## Check Heap Dump
#### GUI 


### GUI (WebSphere Admin Console) Üzerinden:

1. **Admin Console'a Giriş Yapın**:
	- `http://<host>:<port>/ibm/console` adresinden giriş yapın.
2. **JVM Ayarlarına Gidin**:
	- **Servers** > **Server Types** > **WebSphere Application Servers** > [Sunucunuzun adı] > **Java and Process Management** > **Process Definition** > **Java Virtual Machine**.
3. **Heap Ayarlarını Değiştirin**:
	- **Initial Heap Size** (Xms): Minimum belleği ayarlar (örnek: `16` MB).
	- **Maximum Heap Size** (Xmx): Maksimum belleği ayarlar (örnek: `32` MB).
4. **Kaydedip Restart Edin**:
	- Değişiklikleri kaydedin ve sunucuyu yeniden başlatın.


----

### CLI (Komut Satırı) Üzerinden:

1. **JVM Argümanları Ayarları**: Sunucuyu başlatırken JVM argümanlarını değiştirin:

	`./server start <serverName> -Xms16m -Xmx32m`


	- **Xms**: Başlangıçta tahsis edilen bellek (örnek: `16m`).
	- **Xmx**: Maksimum tahsis edilecek bellek (örnek: `32m`).
2. **Profil Konfigürasyonu (server.xml)**: `server.xml` dosyasında JVM ayarlarını ekleyin:

` -Xms16m -Xmx32m

`
----

## 3. **Heap Dump Alma**


Heap Dump, uygulama `OutOfMemoryError` aldığında veya manuel olarak tetiklenerek alınabilir.

### a. **OutOfMemoryError Durumunda Dump Alma**


`-XX:+HeapDumpOnOutOfMemoryError` JVM argümanını ekleyerek, `OutOfMemoryError` alındığında dump otomatik olarak oluşturulur:

`./server start <serverName> -Xms16m -Xmx32m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/path/to/dump`


- **HeapDumpPath**: Dump dosyasının kaydedileceği yolu belirtir.


----

### b. **Manuel Olarak Dump Alma**

1. **JConsole veya jcmd**:
	- `jconsole` veya `jcmd` ile bağlı sunucu için heap dump alınabilir:

	`jcmd <pid> GC.heap_dump /path/to/dumpfile.hprof`


	- **`<pid>`**: JVM işleminin PID'si.
2. **Admin Console Üzerinden**:
	- **Monitoring and Tuning** > **Performance Viewer** > **Dump Heap**.

