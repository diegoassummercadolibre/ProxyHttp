# ProxyHttp

Este proyecto corresponde a un ejercicio de **Proxy HTTP** desarrollado en Java.

## Instrucciones de uso:
 - Descargar el proyecto.
 - Abrir el mismo con algún IDE de java, como puede ser Intellij.
 - Ejecutar el proyecto.
 - Configurar el Proxy en su PC del siguiente modo:
 	- IP: 127.0.0.1
	- Puerto: 8080
- Abrir el navegador web y dirigirse a la página http://example.org/.
- Abrir el explorador de Windows y dirigirse a la carpeta del proyecto, como puede ser *C:\Users\userName\IdeaProjects\ProxyHttp*
- En dicha carpeta se encontrará un arcicho ProxyHttp.log el cual contiene la solicitud (request) y respuesta (response) a la página web solicitada.

## Arquitectura del Proyecto:

### El proyecto posee tres archivos:
 - Un Main.java, el cual inicia la aplicación.
 - Un ProxyThread, es un hilo recibe el socket del servidor enviado por el Main y realiza la comunicacion entre el cliente y el servidor.
 - Un LogHelper encargado de crear el archivo log (ProxyHTTP.log) y escribir sobre el mismo.
	
## Limitaciones:
 - Solo funciona con paginas web no seguras como por ejemplo: http://example.org/, http://www.lavoz.com.ar/, http://ionicons.com/.
 - En algunos casos puede tomarle varios minutos en procesar las peticiones y mostrar la pagina.
