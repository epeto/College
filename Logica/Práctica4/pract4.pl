
/*Yo soy mi propio abuelo*/
padre(pedro,carlos).
conyugue(carlos,daniela).
conyugue(pedro,karla).
hijo(daniela,karla).
madre(daniela,karla).

hijo(X,Y):-padre(Y,X).
hijo(X,Y):-madre(Y,X).

conyugue(X,Y):-conyugue(Y,X).

hermano(X,Y):-hijo(Y,Z),madre(Z,X).
hermano(X,Y):-hijo(Y,Z),padre(Z,X).
hermano(X,Y):-hermano(Y,X).

suegro(X,Y):-conyugue(Y,Z),padre(X,Z).

suegra(X,Y):-conyugue(Y,Z),madre(X,Z).

abuelo(X,Y):-padre(X,Z), padre(Z,Y).
abuelo(X,Y):-padre(X,Z), madre(Z,Y).

cuñado(X,Y):-conyugue(X,Z),hermano(Z,Y).
cuñado(X,Y):-cuñado(Y,X).

tio(X,Y):-madre(Z,Y),hermano(Z,X).
tio(X,Y):-padre(Z,Y),hermano(Z,X).

/*Los libros de Albert*/
nombres([donald,jordi,michael,stephen,frank]).
apellidos([miller,rosado,knuth,spivak,king]).
precios([2,3,4,5,6]).

permutacion([],[]).
permutacion([X|XS],P):-member(X,P),select(X,P,R),permutacion(XS,R).

solucion(Sol):-Sol=[[Nom1,Apell1,Precio1], [Nom2,Apell2,Precio2],
               [Nom3,Apell3,Precio3],[Nom4,Apell4,Precio4],
               [Nom5,Apell5,Precio5]],
               nombres(Nom),apellidos(Apell),precios(Prec),
               permutacion(Nom,[Nom1,Nom2,Nom3,Nom4,Nom5]),
               permutacion(Apell,[Apell1,Apell2,Apell3,Apell4,Apell5]),
               permutacion(Prec,[Precio1,Precio2,Precio3,Precio4,Precio5]),
               member([stephen,king,_],Sol),
	       member([_,spivak,Pspi],Sol),Pdon is Pspi+1,member([donald,_,Pdon],Sol),
	       member([frank,_,Pfra],Sol),Pspi is Pfra+1,
	       member([jordi,_,Pjor],Sol),Pknu is Pjor+3,member([_,knuth,Pknu],Sol),
	       member([_,rosado,Pros],Sol),Pmic is Pros*2,member([michael,_,Pmic],Sol).

