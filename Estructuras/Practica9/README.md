# Práctica 9 - Árboles Binarios Ordenados.

**Estructuras de Datos**
**Semestre 2022-1**

Esta práctica no incluye pruebas unitarias, en lugar de ello se proveen 
bibliotecas para visualizar las estructuras programadas, cuando o métodos
requeridos por cada demo ya se encuentren correctamente implementados.

Para utilizar la visualización se requieren las bibliotecas de ```JavaFX```.  Necesitas descargar las que correspondan a la versión que instalaste de la ```jdk```.  Encontrarás las más actuales con soporte en [Gluon JavaFX](https://gluonhq.com/products/javafx/).  La documentación está en [Openjfx](https://openjfx.io/).

Esta vez se te provee con un archivo ```Makefile``` para ayudarte a compilar y ejecutar tu práctica, este configura las acciones del comando ```make```.  Necesitarás abrir este archivo y, en la línea que dice:
```
PATH_TO_FX=<dirección de JavaFX>
```
escribir la dirección dónde descargaste ```JavaFX```. Por ejemplo:
```
PATH_TO_FX=/home/blackzafiro/Descargas/openjfx-11.0.1_linux-x64_bin-sdk/javafx-sdk-11.0.1/lib/
```

Si tienes Ubuntu y te indica que no tienes ```make```, instálalo con:
```
sudo apt install build-essential
```
Este archivo define objetivos.  Para compilar el código utiliza:
```
make compile
```
Para ejecutar:
```
make run
```
Para borrar los archivos generados:
```
make clean
```