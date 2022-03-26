%Nombre: Emmanuel Peto Gutiérrez
%Cuenta: 414008117

nat(cero).
nat(suc(N)) :- nat(N).

suma(cero,N,N).
suma(N,cero,N).
suma(suc(N),M,suc(K)) :- suma(N,M,K).

prod(cero,_,cero).
prod(_,cero,cero).
prod(suc(N),M,K) :- suma(P,M,K),prod(N,M,P).

factorial(cero,suc(cero)).
factorial(suc(cero),suc(cero)).
factorial(suc(M),F) :- factorial(M,N),prod(suc(M),N,F).

potencia(_,cero,suc(cero)).
potencia(X,suc(N),R) :- potencia(X,N,M),prod(X,M,R).

menor(cero,suc(_)).
menor(suc(X),suc(Y)) :- menor(X,Y).

igual(cero,cero).
igual(suc(N),suc(M)) :- igual(N,M).

elem(X,[X|_]).
elem(X,[_|XS]) :- elem(X,XS).

conct([],L,L).
conct([X|XS],L,[X|YS]) :- conct(XS,L,YS).

reversa([],[]).
reversa([X|XS],R) :- conct(Y,[X],R),reversa(XS,Y).

listasiguales([],[]).
listasiguales([X|XS],[X|YS]) :- listasiguales(XS,YS).

palindroma([]).
palindroma(L) :- listasiguales(L,L2),reversa(L,L2).

ultimo([X],X).
ultimo([_|YS],X) :- ultimo(YS,X).

long([],cero).
long([_|XS],suc(N)) :- long(XS,N).

elimina(_,[],[]).
elimina(X,[X|XS],YS) :- elimina(X,XS,YS).
elimina(X,[Z|ZS],[Z|YS]) :-elimina(X,ZS,YS).

