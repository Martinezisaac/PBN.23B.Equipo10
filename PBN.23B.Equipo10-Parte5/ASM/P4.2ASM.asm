	ORG	0
	STAA	1,-SP
	STAA	1,SP-
	STX	2,+SP
	STX	2,SP+
	STAA	A,X
	STAA	B,X
	STAA	D,X
	STAB	-8,Y
Et1:	LDAA	,X
	LDAA	15,X
	LDAA	255,X
Et2:	LDAA	-256,X
	LDAA	-18,X
	LDAA	31483,X
	END


