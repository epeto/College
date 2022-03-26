

Para compilación sin uso de Make
	$ gcc SumaBinariaParalela.c -o SBP.out -fopenmp

Ejecución

	$ ./SBP.out <n>

	Dónde n es el exponente de base dos, es decir, si se ejecuta
			$ ./SBP.out 1
			Se creará un arreglo de tamaño 2^1

Para la compilación y ejecución usando Make, basta con colocarse en la carpeta y ejecutar
	$ make

el cual creará siempre un arreglo de tamaño 2^4 y mostrará la salida. 

		