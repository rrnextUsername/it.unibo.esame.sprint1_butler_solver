spostaEsterno(Nome, Categoria, Inventario, aggiungi) :-
	%%retract(inventario(Inventario, ListaOld)),
	%%aggiungi(ListaOld, ListaNew, (Nome, Categoria)),
	%%assert(inventario(Inventario, ListaNew)).
	retract( inventario( Inventario, Lista ) ),
	assert( inventario( Inventario, [( Nome, Categoria )|Lista] ) ).
	
spostaEsterno(Nome, Categoria, Inventario, rimuovi) :-
	retract(inventario(Inventario, ListaOld)),
	rimuovi(ListaOld, ListaNew, (Nome, Categoria)),
	assert(inventario(Inventario, ListaNew)).

%% Comandi Interni %%

aggiungi([], Oggetto, Oggetto).
aggiungi([H|T1], [H|T2], Oggetto) :-
	aggiungi(T1, T2, Oggetto).

rimuovi([], [], Oggetto). %% Solo se non deve esplodere quando togli un elemento non esistente
rimuovi([Oggetto|T1], T1, Oggetto).
rimuovi([H|T1], [H|T2], Oggetto) :-
	rimuovi(T1, T2, Oggetto).
