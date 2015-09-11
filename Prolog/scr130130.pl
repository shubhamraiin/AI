%
%  Name: Shubham Rai
%  NetID: scr130130
%
% =============================================================================

% ---------------------------------------------------------
%  Note: In every predicate definition where there is a list variable, you may
%        need to use separate Head and Tail instead of a single variable.
%        e.g. [H|T] instead of InList
% ---------------------------------------------------------

% ---------------------------------------------------------
%  Note: Some predicates may require more than one left hand side.
%        For example: to provide separate base case and recursion case.
% ---------------------------------------------------------


% Q1: is_even/1
% Uncomment the following line and implement
is_even(X) :- Y is X mod 2,Y = 0.
 

% Q2: my_fact/2
% Uncomment the following line and implement
my_fact(0,1) :-!.
my_fact(X,Y) :-X>0,X2 is X-1, my_fact(X2,Factorial), Y is X * Factorial.



% Q3: is_prime/1
% Uncomment the following line and implement
divisible(X,Y) :- 0 is X mod Y, !.
divisible(X,Y) :- X > Y+1, divisible(X, Y+1).
is_prime(2) :- !.
is_prime(1) :- !.
is_prime(X) :- not(divisible(X, 2)).


% Q4: segregate/3
% Uncomment the following line and implement
segregate([X|Y], [X|O], E):- 0 is X mod 2, segregate(Y,O,E),!.
segregate([X|Y], O, [X|E]):- 1 is X mod 2, segregate(Y,O,E),!.
segregate([],[],[]):-!.


% Q5: prod_list/2
% Uncomment the following line and implement
prod_list([],0).
prod_list([H|T],P) :- prod_list(T,Product), (T = [] -> P is H; P is H*Product).

 


% Q6: bookends/3
% Uncomment the following line and implement
bookends(P,S,L) :- prefix(P,L),suffix(S,L).
prefix([],_).
prefix([HP|TP],[HL|TL]):- HP=HL,prefix(TP,TL).
suffix(L,L).
suffix(S,[_|T]):- suffix(S,T),!. 


% Q7: subslice/2
% Uncomment the following line and implement
subslice([],_).
subslice([HS|TS], [_|TL]):- subslice([HS|TS],TL),!.
subslice([HS|TS], [HL|TL]):- HS=HL, sub(TS,TL),!.
sub([],_).
sub([HS|TS], [HL|TL]):- HS=HL, sub(TS,TL),!.



% Q8: Graph
% Uncomment the following lines and implement
% 	Facts go here: develop your own graph. 
% 	Two edges are provided, but you are not obligated to keep them for your graph
edge(a,b).
edge(b,c).
edge(c,d).
edge(d,e).
edge(d,a).

% Uncomment the following line and implement
path(X,Y):- edge(X,Y),!.
path(X,Y):- not(X=Y),edge(X,A),path(A,Y),!.
% Uncomment the following line and implement
cycle(X) :- edge(X,A), path(A,X),!.





% Q9: Clue

affair(mrBoddy,msGreen).
affair(missScarlett, mrBoddy).
greedy(colMustard).
rich(mrBoddy).
rich(colMustard).   % added fact.

married(profPlum, msGreen).
is_married(X,Y) :- confirm_marriage(X,Y).
confirm_marriage(X,Y):- married(Y,X).
confirm_marriage(X,Y):- married(X,Y).

has_affair(X,Y) :- confirm_affair(X,Y).
confirm_affair(X,Y) :- affair(X,Y).
confirm_affair(X,Y) :- affair(Y,X).

% Predicate rules go here
hatred(X,Y) :- is_married(X,Partner), has_affair(Y,Partner).
greed(X,Y) :- greedy(X), not(rich(X)), rich(Y).


% suspect(Killer,mrBoddy) :- 
suspect(X,Y):- hatred(X,Y).
suspect(X,Y):- greed(X,Y),!.




% =============================================================================
%
%  TEST CASES
%
% =============================================================================

testall :- 
	testQ1, 
	testQ2,
	testQ3,
	testQ4,
	testQ5,
	testQ6,
	testQ7,
	testQ8,
	testQ9.

testQ1 :-
	line, nl,
	write('Q1: is_even/1'), nl,
	line('-',40),nl,
	write('is_even(7)'),
	not(is_even(7)),
	line('-',40),nl,
	write('is_even(0)'),
	is_even(0),
	line('-',40),nl,
	write('is_even(12)'),
	is_even(12),
	nl.

testQ2 :-
	line, nl,
	write('Q2: my_fact/2'), nl,
	line('-',40),nl,
	write('my_fact(4, F1)'), nl,
	my_fact(4, F1), write('F1 = '), write(F1),
	line('-',40),nl,
	write('my_fact(5, F2)'), nl,
	my_fact(5, F2), write('F2 = '), write(F2),
	line('-',40),nl,
	write('my_fact(7, F3)'), nl,
	my_fact(7, F3), write('F3 = '), write(F3),
	nl.

testQ3 :-
	line, nl,
	write('Q3: is_prime/1'), nl,
	line('-',40),nl,
	write('is_prime(7)'), nl,
	%	is_prime(7), nl,
	line('-',40),nl,
	write('is_prime(81)'), nl,
	%	is_prime(81), nl,
	line('-',40),nl,
	write('is_prime(101)'), nl,
	%	is_prime(101), nl,
	nl.

testQ4 :-
	line, nl,
	write('Q4: segregate/3'), nl,
	write('segregate([8, 7, 6, 5, 4, 3], E, O)'), nl,
	segregate([8, 7, 6, 5, 4, 3], E, O),  write('E = '), write(E),nl,write('O = '), write(O), nl,
	write('segregate([7, 2, 3, 5, 8], E, O)'), nl,
	segregate([7, 2, 3, 5, 8], Even, Odd),  write('E = '), write(Even),nl,write('O = '), write(Odd), nl,
	
	nl.
	

testQ5 :-
	line, nl,
	write('Q5: prod_list/2'), nl,
	write('prod_list([4, 3], P)'), nl,
	prod_list([4, 3], P), write('P = '), write(P),nl,
	write('prod_list([6, 2, 5, 10], P)'), nl,
	prod_list([6, 2, 5, 10], Prod), write('P = '), write(Prod), nl,
	write('prod_list([], P)'), nl,
	prod_list([], Product), write('P = '), write(Product),
	nl.

testQ6 :-
	line, nl,
	write('Q6: bookends/3'), nl,
	nl.

testQ7 :-
	line, nl,
	write('Q7: subslice/2'), nl,
	nl.

testQ8 :-
	line, nl,
	write('Q8: Graph'), nl,
	write('\tpath/2'), nl,
	write('\tcycle/1'), nl,
	nl.

testQ9 :-
	line, nl,
	write('Q9: Clue'), nl,
	write('\tsuspect(Killer,mrBoddy)'),
	% suspect(Killer,mrBoddy) :- 
	nl.



% =============================================================================
%  Pretty formatting
% =============================================================================

line :- line('=').
line(C) :- line(C, 60), !.
line(_, 0) :- write('\n'), !.
line(C, N) :- X is N - 1, line(C, X), write(C).



