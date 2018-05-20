# ProxyHttp

Este proyecto corresponde a un ejercicio de un Proxy HTTP desarrollado en Java.

## Instrucciones de uso:
 - Descargar el proyecto
 - Abrir el mismo con algun IDE de java, como puede ser Intellij.
 - Presionar ejecuar (para Intellij puede presionar F10)
 - El sistema solicitará un numero de puerto, en cual la aplicacion escuchará las peticiones (Ej.: 8080).
 - Configurar en Proxy del siguiente modo:
 	- IP: 127.0.0.1
	- Puesto: *El número de puerto establecido ateriormente (Ej.: 8080)*
- Abrir el navegador web y dirigirse a la pagina http://example.org/.
- Abrir el explorador de Windows y dirigirse a la carpeta del proyecto, como puede ser *C:\Users\userName\IdeaProjects\ProxyHttp*
- En dicha carpeta se encontrará un arcicho ProxyHttp.log el cual contiene la solicitud y respuesta a la pagina web

## Arquitectura del Proyecto:

### El proyecto posee tres archivos:
 - Un Main.java, el cual inicia la aplicación.
 - Un ProxyThread, es un hilo recibe el socket del servidor enviado por el Main y realiza la comunicacion entre el cliente y el servidor.
 - Un LogHelper encargado de crear el archivo log (ProxyHTTP.log) y escribir sobre el mismo.
	
## Limitaciones:
 - Solo funciona con paginas web simples como por ejemplo: http://example.org/, http://ionicons.com/.
 - Le toma varios minutos en procesar las peticiones y mostrar la pagina.
