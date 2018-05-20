# ProxyHttp

Arquitectura del Proyecto:

El proyecto posee tres archivos:
 - Un Main.java, el cual inicia la aplicacion
 - Un ProxyThread, es un hilo recibe el socket del servidor enviado por el Main y realiza la comunicacion entre el cliente y el servidor.
 - Un LogHelper encargado de crear el archivo log (ProxyHTTP.log) y escribir sobre el mismo.
	
