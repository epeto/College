"""
Lenguajes de Programación: Práctica 8
Rodríguez Hernández Alexis Arturo
Rodrígo Alejandro Sánchez Morales
Emmanuel Peto Gutiérrez
Luis Enrique Cortez Flores
"""


import sys

#Clase Token que describe una parte del árbol de sintaxis abstracta

class Token:
    def __init__(self,tipo,valor = None):

        self.tipo = tipo           #Tipo del token
        self.valor = valor         #Valor del token, varia según el tipo

    def __str__(self):
        s = ""
        s += "[" + self.tipo + "," + str(self.valor) + "]"
        return s
    
    def __repr__(self):
        return str(self)


#Clase que se encarga de separar la entrada del programa en partes de una lista
class Lexer:
        #Tiene un único método y no tiene atributos, pues es un procedimiento muy sencillo el que hace
    
    def analiza(self,entrada):

        command_list = entrada.split(",")
        for i in range(len(command_list)): 
            command_list[i] = " ".join(command_list[i].split())
        return command_list

"""
Clase Parser, se encarga de tomar la lista de lexemas y convertirlos a tokens que
pueden ser entendidos por la máquina, a cada token le asigna un tipo y un valor
dependiente de ese tipo en específico
"""
class Parser:

    #Constructor
    
    def __init__(self,tokenList = []):
        self.tokenList = tokenList

        #Método que hace el trabajo sucio de convertir cada comando en un token
        
    def convert(self,command):
        ret = None        
        com = command.split(" ")             
        if com[0] == "help" :               #Si el comando es help, al token le asignara ese tipo y el valor será el comando que se desea saber
                                            #El valor será nulo si se desea ayuda en general
            if len(com) > 2 :
                print("se esperaban menos de dos agumentos")
                return None
            if len(com) == 2:
                ret = Token("help",com[1])
            else :
                ret = Token("help",None) 
        elif  com[0] == "exit":              #Solo le asigna al token el tipo del comando y el valor es nulo
            ret = Token(com[0])
        elif  com[0] == "drop":              #Solo le asigna al token el tipo del comando y el valor es nulo
            ret = Token(com[0])
        elif  com[0] == "dup":              #Solo le asigna al token el tipo del comando y el valor es nulo
            ret = Token(com[0])
        elif  com[0] == "over":              #Solo le asigna al token el tipo del comando y el valor es nulo
            ret = Token(com[0])
        elif  com[0] == "swap":              #Solo le asigna al token el tipo del comando y el valor es nulo
            ret = Token(com[0])
        elif  com[0] == "show":              #Solo le asigna al token el tipo del comando y el valor es nulo
            ret = Token(com[0])
        elif com[0] == "+" or com[0] == "-" or com[0] == "*" or com[0] == "/" :   #El tipo del token será de operacion y el  
            ret = Token("op", com[0])                                             #valor será la operación que se hará

            
        elif com[0] == "push" :                 #El tipo será push y el valor será el número a introducir
            if len(com) != 2:
                print("push: se esperaba solo un argumento")
                return None
            if not com[1].replace('.','',1).replace("-","",1).isdigit():
                print("push: el argumento debe ser numerico")
                return None
            ret = Token(com[0],com[1])
        elif com[0].replace('.','',1).replace("-","",1).isdigit():
            ret = Token("push",com[0])
        elif com[0] == ":" :                             #El tipo será de defiinción y el valor una lista con los comandos que la componen
            if len(com) < 3:
                print("definition: se esperaba al menos un comando en la definición")
                return None
            ret = Token("definition",com[1:])            
        else :
            ret = Token(com[0])
        return ret

    #Función que realiza la conversión cuantas veces sea necesario
    
    def parsea(self,command_list):
        for command in command_list:
            token = self.convert(command)
            if token == None:
                print("Hubo un error de sintáxis")
                return
            self.tokenList.append(token)            
        return self.tokenList

"""
Clase principal que realiza el análisis semántico y la ejecución del intérprete ante el
usuario. El intérprete debe puede recibir cadenas desde la terminal (interactuando con el usuario)
o leerlas desde un archivo.
"""    

class Forth:

    def __init__(self,stack = []):
        self.stack = stack      #Tendrá una pila para trabajar

        #Se lleva un registro de los comandos definidos para mostrar la ayuda, que no se repitan definiciones
        #y saber que comandos componen a los que van siendo definidos, se inicializa con las palabras reservadas
        self.existingCommands = [("+",),("-",),("*",),("/",),("exit",),("help",),(":",),("definition",),
                                 ("drop",),("dup",),("over",),("swap",),("push",),("show",)]        
        self.lexer = Lexer()          #Tiene un objeto lexer
        self.parser = Parser()        #Tiene un objeto parser

        #Se incluye un diccionario que tiene como claves las palabras reservadas y como valores sus respectivas cadenas de ayuda
        #Claramente se inicializa con los valores ya defindos por defecto
        self.helpdic = {}
        self.helpdic["exit"] = "exit: Termina la ejecución del programa."
        self.helpdic["help"] = "help c: Si no se pasa el parámetro c, muestra la lista de comandos actual con su descripción; en otro caso, muestra información sobre el comando c pasado como parámetro."
        self.helpdic["+"] =  "+ : saca los dos primeros elementos de la pila, los suma, y regresa el resultado al tope de la pila"
        self.helpdic["-"] = "- : sustrae del elemento al tope de la pila el segundo elemento al tope, y regresa el resultado al tope de la pila"
        self.helpdic["*"] = "/ : divide el primer elemento de la pila entre el segundo"
        self.helpdic["/"] = "* : multiplica el primer elemento de la pila por el segundo"
        self.helpdic["drop"] =  "drop: Elimina el elemento del tope de la pila."
        self.helpdic["dup"] =  "dup: Duplica el elemento del tope de la pila."
        self.helpdic["over"] = "over: Duplica el elemento en el tope de la pila, pero almacena el resultado como el tercer elemento en la misma."
        self.helpdic["swap"] =  "swap: Intercambia las posiciones de los dos últimos elementos de la pila."
        self.helpdic["push n"] =  "push n: Pone un número n en el tope de la pila, puede ejecutarse también solo escribiendo el número n."
        self.helpdic["show"] = "Show: Muestra la pila actual."
        self.helpdic["define"] =  ": id c+ : Define un nuevo comando con el nombre id como la composición de los comandos existentes c+. Después, si se escribe el id en el intérprete, se ejecutarán los comandos especificados en c+ en orden."

        #Función encargada de desĺegar la ayuda de un comando específico
        
    def aiuda(self,command) :
        s = ""
        if command is None:             
            for key in self.helpdic:
                s += self.helpdic[key] + "\n\n"
        else:
            s = self.helpdic.get(command,"Comando aún sin existir, puede definirlo, para más información use help define")
        return s

    #Función que revisa si un comando ya existe 
    
    def in_existingCommands(self,command):
        for com in self.existingCommands:
            if command == com[0]:
                return com
        return None
    
    """
    Función principal que realiza el análisis semántico del token dado como parámetro, revisa caso por caso y 
    realiza la acción correspondiente regresa True si el interprete puede continuar y False en caso de 
    que se dé un error fatal o que se desee la terminación del programa
    """
    
    def interp (self,token):
        try:
            toktype = token.tipo
            if toktype == "exit" :
                return False
            elif toktype == "help":
                print(self.aiuda(token.valor))
            elif toktype == "op":
                if token.valor == "+":
                    op1 = self.stack.pop()
                    op2 = self.stack.pop()
                    self.stack.append(op1+op2)
                elif token.valor == "-":
                    op1 = self.stack.pop()
                    op2 = self.stack.pop()
                    self.stack.append(op1-op2)
                elif token.valor == "*":
                    op1 = self.stack.pop()
                    op2 = self.stack.pop()
                    self.stack.append(op1*op2)                    
                else :
                    op1 = self.stack.pop()
                    op2 = self.stack.pop()
                    self.stack.append(op1/op2)
            elif toktype == "drop":
                self.stack.pop()
            elif toktype == "dup":
                elem = self.stack.pop()
                self.stack.append(elem)
                self.stack.append(elem)
            elif toktype == "over":
                self.stack[-2]
                elem = self.stack.insert(-2,self.stack[-1])
            elif toktype == "swap":
                e1 = self.stack.pop()
                e2 = self.stack.pop()
                self.stack.append(e2)
                self.stack.append(e1)
            elif toktype == "push":
                    try:
                        elem = int(token.valor)
                    except ValueError:
                        elem = float(token.valor)
                    self.stack.append(elem)
            elif toktype == "show":
                print(self.stack)
            elif toktype == "definition":
                if self.in_existingCommands(token.valor[0]) or token.valor[0].replace('.','',1).replace("-","",1).isdigit():
                    print("Commando ya previamente definido o es una palabra reservada o un número.")
                    print("por favor,  use otro identificador para la operación")
                    return True
                s = token.valor[0] + " : Comando definido como "
                for i in range(1,len(token.valor)):
                    s += token.valor[i] + " "                    
                self.helpdic[token.valor[0]] = s

                com = token.valor[0],token.valor[1:]
                self.existingCommands.append(com)
            elif  self.in_existingCommands(toktype) != None :
                pair = self.in_existingCommands(toktype)
                for s in pair[1]:
                    t = self.parser.parsea(self.lexer.analiza(s))
                    if t is None:
                        return False
                    t = t[0]
                    self.parser.tokenList.pop()
                    if not self.interp(t):
                        return False
                    
            else:
                print("Comando inexistente, puede definirlo, para más información use help define")                
            return True
        except IndexError:        
             print("stack vacío")
             return False

    #Función que se ejecutará en caso de que se desee interactuar con el usuario
         
    def user_interaction(self):
        exitflag = True
        token = None
        inp = None
        while exitflag:
            inp = input(">")        
            token = self.parser.parsea(self.lexer.analiza(inp))
            if token is None:
                return
            token = token[0]
            self.parser.tokenList.pop()
            exitflag = self.interp(token)

    #Función que se ejecutará en caso de que se desee leer de un archivo
            
    def file_interaction(self,program):
        token = None
        for command in program:      
            token = self.parser.parsea(self.lexer.analiza(command))
            if token is None:
                return False
            token = token[0]
            self.parser.tokenList.pop()
            if not self.interp(token):
                return False
        return True

    
#Función principal revisa los argumentos del sistema y llama a la función correspondiente
        
if __name__ == '__main__':
    if len(sys.argv) > 2:
        print("Uso del programa: \n Para modo interactivo - practica8 \n Para leer de archivo - practica8 <ruta del archivo>")
    elif len(sys.argv) == 2:
        try:
            archivo = open(sys.argv[1],"r")
            program = archivo.readlines()
            f = Forth()
            if f.file_interaction(program):
                print(f.stack)
        except FileNotFoundError:
            print("Archivo no encontrado, revisar la ruta")            
    else:
        f = Forth()
        f.user_interaction()
        print(f.stack)
    
