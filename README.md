# ProxyHttp

Este proyecto corresponde a un ejercicio de un Proxy HTTP desarrollado en Java.

## Instrucciones de uso:
 - Descargu

##Arquitectura del Proyecto:

### El proyecto posee tres archivos:
 - Un Main.java, el cual inicia la aplicación.
 - Un ProxyThread, es un hilo recibe el socket del servidor enviado por el Main y realiza la comunicacion entre el cliente y el servidor.
 - Un LogHelper encargado de crear el archivo log (ProxyHTTP.log) y escribir sobre el mismo.
	
Limitaciones:
 - Solo funciona con paginas web simples como por ejemplo: http://example.org/, http://ionicons.com/
 
