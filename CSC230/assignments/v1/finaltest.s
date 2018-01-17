@ CSC230 --  Final practice

@ Author:  Jakob Roberts
@		v00484900
@ Filename:	A4KolakoskiA.s

@ ====================================================================
@ Local Constants
@ ====================================================================
	.equ	SWI_EXIT,      	0x11
	.equ	SWI_PrintInt,  	0x6B
	.equ	SWI_PrStr,	0x02
	.equ	Stdout,        	1
	.equ	EndInput,	1000
@ ====================================================================
        .text           
        .global _start
@ ====================================================================
@ ====================================================================
@===== The entry point of the program
_start:	
	MOV	R9,#EndInput
	LDR	R0,[R9]
	LDR	R8,=K
	LDR	R2,[R8]
	MOV	R1,#1		
StartFinal:
	CMP	R1,R0
	BGE	EndFinal
	MOV	R1,R1,LSL #1
	ADD	R2,R2,#1
	BAL	StartFinal
EndFinal:
	STR	R2,[R8]
	
	MOV	R1,R2        	@ R1:value-to-print = R5:Index
	MOV	R0, #Stdout   	@ print to console
	SWI	SWI_PrintInt  	@ PrintInt(R0:where, R1:value)
	SWI	SWI_EXIT		@ program main exit
@---------------------------------------------------------------------------------------------	
@@@@@@@@@@@@=========================
	.data
	.align
N:	.word	42
K:	.word	0

	.end