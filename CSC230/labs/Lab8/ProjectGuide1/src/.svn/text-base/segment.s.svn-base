
	.include	"segment.a"

	.equ		LED8ADDR,	0x2140000

	.text
	.global		segment_display
	.global		segment_init
	.global		segment_digit

@Init the 8 segment display. We will turn off all segments
segment_init:
	stmfd	sp!,{r0,lr}
	mov		r0,#0x00
	bl		segment_display
	ldmfd	sp!,{r0,pc}

@Display a digit(0-9 or A-F) on the 8 segment display
@Input R0:digit to display (0-15)
segment_digit:
	stmfd	sp!,{r0-r1,lr}
	ldr		r1,=digits
	ldr		r0,[r1,r0,lsl#2]
	bl		segment_display
	ldmfd	sp!,{r0-r1,pc}

@Display a pattern in the 8 segment display
@Input R0:pattern to display(only bottom 8 bits are used)
segment_display:
	stmfd	sp!,{r0-r1,lr}
	
	@reverse the bit pattern. 0 means turn on, 1 means turn off
	mvn		r0,r0
	
	@setup memory address of 8 segment display and write pattern
	ldr		r1,=LED8ADDR
	strb	r0,[r1]
	
	ldmfd	sp!,{r0-r1,pc}

	.data
	.align

@This table defines the patterns for the digits 0-9 and A-F (hexidecimal)
digits:	
		.word	SEG_A|SEG_B|SEG_C|SEG_D|SEG_E|SEG_G			@0
		.word	SEG_B|SEG_C									@1
		.word	SEG_A|SEG_B|SEG_F|SEG_E|SEG_D				@2
		.word	SEG_A|SEG_B|SEG_F|SEG_C|SEG_D				@3
		.word	SEG_G|SEG_F|SEG_B|SEG_C						@4
		.word	SEG_A|SEG_G|SEG_F|SEG_C|SEG_D				@5
		.word	SEG_A|SEG_G|SEG_F|SEG_E|SEG_D|SEG_C			@6
		.word	SEG_A|SEG_B|SEG_C							@7
		.word	SEG_A|SEG_B|SEG_C|SEG_D|SEG_E|SEG_F|SEG_G	@8
		.word	SEG_A|SEG_B|SEG_F|SEG_G|SEG_C				@9
		.word	SEG_A|SEG_B|SEG_C|SEG_F|SEG_E|SEG_G			@A
		.word	SEG_C|SEG_D|SEG_F|SEG_E|SEG_G				@B
		.word	SEG_A|SEG_D|SEG_E|SEG_G						@C
		.word	SEG_B|SEG_C|SEG_D|SEG_F|SEG_E				@D
		.word	SEG_A|SEG_G|SEG_E|SEG_F|SEG_D				@E
		.word	SEG_A|SEG_G|SEG_E|SEG_F						@F
		.end
