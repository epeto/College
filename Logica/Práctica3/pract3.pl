/* Facultad de Ciencias UNAM, Lógica Computacional 2017-1.
   Noé Salomón Hernández Sánchez
   Albert Manuel Orozco Camacho
   Cenobio Moisés Vázquez Reyes
   Diego Murillo Albarrán
   José Roberto Piche Limeta */

:-dynamic on/2.

/* on(A,B). */
on(a,b). on(b,c). on(c,piso).
on(d,piso).
on(e,f). on(f,piso).
on(g,h). on(h,i). on(i,piso).

/* blocked(A). */
blocked(A):-on(_,A).

/* onTop(A). */
onTop(A):- \+blocked(A).

/* movePiso(A). */
movePiso(A):-onTop(A),retract(on(A,_)),assertz(on(A,piso)).

/* move(A,B). */
move(A,B):-onTop(A),onTop(B),retract(on(A,_)),assertz(on(A,B)).

/* bottom(A,B). */
bottom(A,A):-on(A,piso),onTop(A).
bottom(A,C):-on(A,B),bottom(B,C).

/* move_reversed(A,B). */
move_reversed(A,B):-onTop(A),move(A,B).
move_reversed(A,C):-on(A,B),move(A,C),move_reversed(B,A).


computologos([lourdes,susana,francisco,jose]).

competencia:-
	solucion(S,B),
	write('Reto de programación = '),write(S),nl,
        write('Reto de demostración = '),write(B),nl.

solucion(Prog,Dem):-
	computologos(C),permutacion(Prog,C),permutacion(Dem,C),
	primer(Prog,PriP),jose\==PriP,
	primer(Dem,PriD),jose\==PriD,
	cuarto(Prog,CuaP),francisco\==CuaP,
	cuarto(Dem,CuaD),francisco\==CuaD,
	posicion(susana,Prog,lourdes),
	posicion(jose,Prog,francisco),
	posicion(francisco,Dem,jose),
	primer(Dem,lourdes),
	tercer(Dem,TerD),TerD==PriP.

/* elimina(X,L1,L2). */
elimina(H,[H|T],T).
elimina(X,[H|L1],[H|L2]):-elimina(X,L1,L2).

/* permutacion(X,Y).*/
permutacion([],[]).
permutacion([H|T],Q):-member(H,Q),elimina(H,Q,P),permutacion(T,P).

/* posicion(Mejor,List,Peor). */
posicion(Mejor,List,Peor):-
	primer(List,Mejor),segundo(List,Peor);
	primer(List,Mejor),tercer(List,Peor);
    primer(List,Mejor),cuarto(List,Peor);
    segundo(List,Mejor),tercer(List,Peor);
    segundo(List,Mejor),cuarto(List,Peor);
    tercer(List,Mejor),cuarto(List,Peor).

primer([X|_],X).
segundo([_,X,_,_],X).
tercer([_,_,X,_],X).
cuarto([_,_,_,X],X).
