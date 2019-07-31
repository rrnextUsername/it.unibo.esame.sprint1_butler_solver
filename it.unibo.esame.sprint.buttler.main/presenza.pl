presenza(Nome, Categoria, Inventario) :-
	inventario(Inventario, Lista),
	presente(Lista, (Nome, Categoria)).
	
%% Comandi Interni %%

presente([Oggetto|_], Oggetto).
presente([H|T], Oggetto) :-
	presente(T, Oggetto).
