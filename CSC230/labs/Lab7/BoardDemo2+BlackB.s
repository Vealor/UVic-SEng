@ BOARD DEMO in ARMSim# and on the Embest Board
@Written By:M. Serra

@@ Start by testing LEDs, 8-segment, LCD screen
@@ then add prompt and test for 2 black button
@@@ this file is a subset of BoardDemoFull.s
@@@ and comes after BoardDemo1

@@ draw a message on LCD
@@ display H in 8-segment
@@ left LED on, right off, 3 seconds
@@ left LED off, right on, 3 seconds
@@ both LED on
@@ message prompting for black button
@@ check black press:
@@	if left draw |-, and left LED on
@@	if right draw -| and right LED on

@SWI Codes for Board View
	.equ	SWI_SETSEG8,		0x200	@display on 8 Segment
	.equ	SWI_SETLED,		0x201	@LEDs on/off
	.equ	SWI_CheckBlack,		0x202	@check press Black button
	.equ	SWI_CheckBlue,		0x203	@check press Blue button
	.equ	SWI_DRAW_STRING,	0x204	@display a string on LCD
	.equ	SWI_DRAW_INT,		0x205	@display an int on LCD	
	.equ	SWI_CLEAR_DISPLAY,	0x206	@clear LCD
	.equ	SWI_DRAW_CHAR,		0x207	@display a char on LCD
	.equ	SWI_CLEAR_LINE,		0x208	@clear a line on LCD
	.equ	SCREEN_COLS,		40		@number of columns on LCD screen
	.equ	SCREEN_ROWS,		14		@number of rows on LCD screen
	
	.equ	SWI_EXIT,			0x11	@terminate program
	.equ	SWI_GetTicks,		0x6d	@get current time 
@ patterns for 8 segment display
	.equ 	SEG_A,		0x80	@byte values for each segment
	.equ 	SEG_B,		0x40	@of the 8 segment display
	.equ 	SEG_C,		0x20
	.equ 	SEG_D,		0x08
	.equ 	SEG_E,		0x04
	.equ 	SEG_F,		0x02
	.equ 	SEG_G,		0x01
	.equ 	SEG_P,		0x10
		
@bit patters for LED lights
	.equ	LEFT_LED,			0x02
	.equ	RIGHT_LED,			0x01
		
@bit patterns for buttons
	.equ	LEFT_BLACK_BUTTON,		0x02
	.equ	RIGHT_BLACK_BUTTON,		0x01
		
@bit patterns for blue buttons
	.equ	BLUE_KEY_00,			0x0001		@button(0)
	.equ	BLUE_KEY_01,			0x0002		@button(1)
	.equ	BLUE_KEY_02,			0x0004		@button(2)
	.equ	BLUE_KEY_03,			0x0008		@button(3)
	.equ	BLUE_KEY_04,			0x0010		@button(4)
	.equ	BLUE_KEY_05,			0x0020		@button(5)
	.equ	BLUE_KEY_06,			0x0040		@button(6)
	.equ	BLUE_KEY_07,			0x0080		@button(7)
	.equ	BLUE_KEY_08,			0x0100		@button(8)
	.equ	BLUE_KEY_09,			0x0200		@button(9)
	.equ	BLUE_KEY_10,			0x0400		@button(10)
	.equ	BLUE_KEY_11,			0x0800		@button(11)
	.equ	BLUE_KEY_12,			0x1000		@button(12)
	.equ	BLUE_KEY_13,			0x2000		@button(13)
	.equ	BLUE_KEY_14,			0x4000		@button(14)
	.equ	BLUE_KEY_15,			0x8000		@button(15)
	.text
	.global	_start
_start:
@Clear the board by clearing each peripheral in turn
@Clear the LCD screen	
	swi	SWI_CLEAR_DISPLAY
@Both LEDs off
	mov	r0,#0
	swi	SWI_SETLED
@8-segment blank
	mov	r0,#10
	mov	r1,#0
	BL	Display8Segment		@Display8Segment(R0:num;R1:P)

@draw a message to the lcd screen on line#1, column 4
	mov	r0,#4				@ column number
	mov r1,#1				@ row number
	ldr	r2,=Welcome			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
	
@display the letter H in 7segment display
	ldr	r0,=SEG_B|SEG_C|SEG_G|SEG_E|SEG_F
	swi	SWI_SETSEG8
	
@turn on LEFT led only
	mov	r0,#LEFT_LED
	swi	SWI_SETLED
@draw a message to the lcd screen on line#2, column 4
	mov	r0,#4				@ column number
	mov r1,#2				@ row number
	ldr	r2,=LeftLED			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
@Wait for 3 second
	ldr	r3,=3000
	BL 	Wait				@ void Wait(Delay:r3)
	
@turn on RIGHT led only
	mov	r0,#RIGHT_LED
	swi	SWI_SETLED
	@draw a message to the lcd screen on line#2, column 4
	mov	r0,#4				@ column number
	mov r1,#2				@ row number
	ldr	r2,=RightLED		@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen

@Wait for 3 second
	ldr	r3,=3000
	BL 	Wait				@ void Wait(Delay:r3)

@turn on BOTH led
	mov	r0,#(LEFT_LED|RIGHT_LED)
	swi	SWI_SETLED
	@draw a message to the lcd screen on line#2, column 4
	mov	r0,#4				@ column number
	mov r1,#2				@ row number
	ldr	r2,=BothLED			@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
@Wait for 3 second
	ldr	r3,=3000
	BL 	Wait				@ void Wait(R3:delaytime)
	
@@@@@@@@@@@@@@@@@@
@ Part 2 to test black buttons
@ clear previous line 2
	mov	r0,#2		
	swi	SWI_CLEAR_LINE	
@draw a message to inform user to press a black button
	mov	r0,#6				@ column number
	mov r1,#2				@ row number
	ldr	r2,=PressBlackL		@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen
@wait for user to press a black button
	mov r0,#0
LB1:
	swi	SWI_CheckBlack		@get button press into R0
	cmp	r0,#0
	beq	LB1					@ if zero, no button pressed
	
	@ now a black button has been pressed
	cmp	r0,#RIGHT_BLACK_BUTTON
	bne	LD1
	ldr	r0,=SEG_B|SEG_C|SEG_F  	@right button, show -|
	swi	SWI_SETSEG8
	mov	r0,#RIGHT_LED			@turn on right led
	swi	SWI_SETLED
	bal	NextButtons
LD1:		@left black pressed 
	ldr	r0,=SEG_G|SEG_E|SEG_F	@display |- on 8segment
	swi	SWI_SETSEG8
	mov	r0,#LEFT_LED			@turn on LEFT led
	swi	SWI_SETLED
NextButtons:				@Wait for 3 second
	ldr	r3,=3000
	BL 	Wait				@ void Wait(R3:delaytime)
	
@Prepare to exit: lst message and clear the board
@draw a message to the lcd screen on line#10, column 1
	mov	r0,#1				@ column number
	mov r1,#10				@ row number
	ldr	r2,=Bye				@ pointer to string
	swi	SWI_DRAW_STRING		@ draw to the LCD screen	
@Turn off both LED's
	ldr	r0,=0
	swi	SWI_SETLED
@8-segment blank
	mov	r0,#10
	mov	r1,#0
	BL	Display8Segment		@Display8Segment(R0:num;R1:P)
	ldr	r3,=2000			@delay a bit
	BL	Wait
@Clear the LCD screen	
	swi	SWI_CLEAR_DISPLAY
		
	swi	SWI_EXIT			@all done, exit
	
@ ===================================================================
@ Display8Segment (Number:R0; Point:R1) 
@ Displays the number 0-9 on the LED 8-segment display
@	with or without the extra point
@ INPUT PARAMETERS:
@	R0 = number to be displayed
@	R1: if =1, the point is also shown, else not
@ OUTPUT PARAMETERS: none
Display8Segment:
	stmfd 	sp!,{r0-r2,lr}
	ldr 	r2,=Digits			@get table of digits pre-made
	ldr 	r0,[r2,r0,lsl#2]	@compute display value in R0
	tst 	r1,#0x01 			@if r1=1,
	orrne 	r0,r0,#SEG_P 		@then show P dot
	swi 	SWI_SETSEG8
	ldmfd 	sp!,{r0-r2,pc}

@@ ======================================================
@ Wait(Delay:r3) wait for r3 milliseconds
@ Delays for the amount of time stored in r3 for a 15-bit timer
@ INPUT PARAMETERS: R3 = times in milliseconds
@ OUTPUT PARAMETERS: none
Wait:
	stmfd	sp!,{r0-r5,lr}	
	ldr	r4,=0x00007FFF	@mask for 15-bit timer
	SWI	SWI_GetTicks	@Get start time
	and	r1,r0,r4		@adjusted time to 15-bit			
Wloop:
	SWI	SWI_GetTicks	@Get current time
	and	r2,r0,r4		@adjusted time to 15-bit
	cmp	r2,r1
	blt	Roll			@rolled above 15 bits
	sub	r5,r2,r1		@compute easy elapsed time
	bal	CmpLoop
Roll:	
	sub	r5,r4,r1		@compute rolled elapsed time
	add	r5,r5,r2
CmpLoop:	
	cmp	r5,r3			@is elapsed time < delay?
	blt	Wloop			@Continue with delay	
Xwait:	
	ldmfd	sp!,{r0-r5,pc}


@ ================================================
	.data
	.align
Digits:
	.word 	SEG_A|SEG_B|SEG_C|SEG_D|SEG_E|SEG_G @0
	.word 	SEG_B|SEG_C @1
	.word 	SEG_A|SEG_B|SEG_F|SEG_E|SEG_D @2
	.word 	SEG_A|SEG_B|SEG_F|SEG_C|SEG_D @3
	.word 	SEG_G|SEG_F|SEG_B|SEG_C @4
	.word 	SEG_A|SEG_G|SEG_F|SEG_C|SEG_D @5
	.word 	SEG_A|SEG_G|SEG_F|SEG_E|SEG_D|SEG_C @6
	.word 	SEG_A|SEG_B|SEG_C @7
	.word 	SEG_A|SEG_B|SEG_C|SEG_D|SEG_E|SEG_F|SEG_G @8
	.word 	SEG_A|SEG_B|SEG_F|SEG_G|SEG_C @9
	.word 	0 		@Blank display
Welcome:	.asciz	"Welcome to Board Testing"
LeftLED:	.asciz	"LEFT light"
RightLED:	.asciz	"RIGHT light"
BothLED:	.asciz	"BOTH lights"
PressBlackL:	.asciz	"Press a BLACK button"
Bye:		.asciz	"Bye for now."
Blank:		.asciz	" "
PressBlue:	.asciz	"Press a BLUE button 0-9 only - 15 tests"
InvBlue:	.asciz	"Invalid blue button - try again"
TestBlue:	.asciz	"Tests ="

	.end