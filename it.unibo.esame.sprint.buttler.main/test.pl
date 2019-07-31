comando( test ) :-
	assert( azione( check, 1, 1, 1 ) ),
	assert( azione( wait, 1, 1, 1 ) ),
	assert( azione( check, 2, 2, 2 ) ).
	