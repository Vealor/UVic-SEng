@ Example program in ARMSim# and on the Embest Board
@ Written By:M. Serra
@ Normally used in Lab 8

@@ draw a name on LCD
@@ left LED on, right off, 2 seconds
@@ left LED off, right on, 2 seconds
@@ both LED on
@@ both LED off
@@ message prompting for black button, to be done within 10 seconds
@@ check black press:
@@	if left, left LED on for 3 seconds
@@	if right, right LED on for 3 seconds
@@ final message on LCD

	.equ	SWI_SETSEG8,		0x200	@display on 8 Segment
	.equ	SWI_SETLED,			0x201	@LEDs on/off
	.equ	SWI_CheckBlack,		0x202	@check press Black button
	.equ	SWI_CheckBlue,		0x203	@check press Blue button
	.equ	SWI_DRAW_STRING,	0x204	@display a string on LCD
	.equ	SWI_DRAW_INT,		0x205	@display an int on LCD	
	.equ	SWI_CLEAR_DISPLAY,	0x206	@clear LCD
	.equ	SWI_DRAW_CHAR,		0x207	@display a char on LCD
	.equ	SWI_CLEAR_LINE,		0x208	@clear a line on LCD	
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
	.equ	BOTH_LED,			0x03
	.equ	NO_LED,				0x00	
@bit patterns for buttons
	.equ	LEFT_BLACK_BUTTON,		0x02
	.equ	RIGHT_BLACK_BUTTON,		0x01	
@bit patterns for blue buttons ==> check
	.equ	BLUE_KEY_00,			0x0001		@button(0)
	.equ	BLUE_KEY_01,			0x0002		@button(1)
	.equ	BLUE_KEY_02,			0x0004		@button(2)
	.equ	BLUE_KEY_03,			0x0008		@button(3)
	.equ	BLUE_KEY_04,			0x0010		@button(4)
	.equ	BLUE_KEY_05,			0x0020		@button(5)
	.equ	BLUE_KEY_06,			0x0040		@button(6)
	.equ	BLUE_KEY_07,			0x0080		@button(7)
	.equ	BLUE_KEY_00,			0x0100		@button(8)
	.equ	BLUE_KEY_01,			0x0200		@button(9)
	.equ	BLUE_KEY_02,			0x0400		@button(10)
	.equ	BLUE_KEY_03,			0x0800		@button(11)
	.equ	BLUE_KEY_04,			0x1000		@button(12)
	.equ	BLUE_KEY_05,			0x2000		@button(13)
	.equ	BLUE_KEY_06,			0x4000		@button(14)
	.equ	BLUE_KEY_07,			0x8000		@button(15)
@ patterns for time delays
	.equ	ONESEC,				1000
	.equ	TWOSEC,				2000
	.equ	THREESEC,			3000
	.equ    Point1Secs, 		100        
	.equ	Top15bitRange,		0x0000ffff	@(2^15) -1 = 32,767
	.equ    EmbestTimerMask, 	0x7fff   @ 15 bit mask for timer values
	.text
	.global	_start
_start:
@====== display a name on LCD
	ldr 	r2,=myname
	mov 	r0,#0		@ column number
	mov 	r1,#0		@ line number
	swi 	SWI_DRAW_STRING
		
@====== turn the left led on and wait for two seconds
	mov 	r0,#LEFT_LED
	swi 	SWI_SETLED
	mov 	r3,#TWOSEC
	bl  	Wait		@ void Wait(Delay:r3)	
@====== turn the right led on and wait for two seconds
	mov 	r0,#RIGHT_LED
	swi 	SWI_SETLED
	mov 	r3,#TWOSEC
	bl  	Wait		@ void Wait(Delay:r3)	
@====== turn both the leds on and wait for two seconds
	mov 	r0,#BOTH_LED
	swi 	SWI_SETLED
	mov 	r3,#TWOSEC
	bl  	Wait		@ void Wait(Delay:r3)
@====== turn both the leds off
	mov 	r0,#NO_LED
	swi 	SWI_SETLED
@========  display a count down of numbers on 8 segment
CountDown:		
	mov	r4,#9
	mov	r1,#0
Down:
	mov	r0,r4
	bl	Display8Segment		@start at 9 and count down
	mov r3,#TWOSEC			@wait 2 seconds
	bl	Wait
	subs	r4,r4,#1
	bne	Down
@====== clear 8-segment and display the point only
	mov	r0,#10
	mov	r1,#1
	bl	Display8Segment		@Display8Segment (Number:R0; Point:R1;)
@======  Check for 10 seconds if a black button is pressed. Turn on left/right
@ LED if either left/right black button is pressed
	ldr 	r2,=promptblack
	mov 	r0,#2		@ column number
	mov 	r1,#2		@ line number
	swi 	SWI_DRAW_STRING
	ldr     r7, =EmbestTimerMask	@mask for 15 bit timer
	ldr		r8,=Top15bitRange		@top range of timer
	ldr		r10,=interval			@interval to check
	swi     SWI_GetTicks			@get time T1
	and		r0,r0,r7				@T1 in 15 bits
	ldr     r1, =Time0
	str     r0, [r1]				@save T1 in Time0
RepeatTillTime:
	swi 	SWI_CheckBlack
	cmp 	r0, #0
	bne		WhichButton
	swi     SWI_GetTicks		@get time T2
	and		r0,r0,r7			@T2 in 15 bits
	mov		r2,r0				@r2 is T2
	ldr     r3, =Time0
	ldr     r1, [r3]			@ r1 is T1
	cmp		r2,r1			@ is T2>T1?
	bge		simpletime
	sub		r9,r8,r1		@ elapsed TIME= 32,676 - T1
	add		r9,r9,r2		@    + T2
	bal		CheckInterval
simpletime:
	sub		r9,r2,r1		@ elapsed TIME = T2-T1
CheckInterval:
	cmp		r9,r10			@is TIME < 10 secs?
	blt		RepeatTillTime
	bal		Closure
WhichButton:
	cmp 	r0, #LEFT_BLACK_BUTTON
	beq 	LeftButton
	mov 	r0,#RIGHT_LED
	swi 	SWI_SETLED	@ else set the right LED on
	ldr 	r3,=THREESEC
	bl  	Wait		@ void Wait(Delay:r3)
	bal 	Closure	
LeftButton:
	mov 	r0,#LEFT_LED
	swi 	SWI_SETLED	@set the left LED on
	ldr 	r3,=THREESEC
	bl  	Wait		@ void Wait(Delay:r3)
Closure:	@clear all components on the board and exit
	mov 	r0,#NO_LED
	swi 	SWI_SETLED
	mov	r0,#10
	mov	r1,#0
	bl	Display8Segment		@Display8Segment (Number:R0; Point:R1;)
	ldr 	r2,=bye
	mov 	r0,#5		@ column number
	mov 	r1,#5		@ line number
	swi 	SWI_DRAW_STRING
	SWI	SWI_EXIT

@@ ======================================================
@ Wait(Delay:r3) wait for r3 milliseconds
@ Delays for the amount of time stored in r3 for a 15-bit timer
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
Roll:	sub	r5,r4,r1	@compute rolled elapsed time
	add	r5,r5,r2
CmpLoop:	cmp	r5,r3	@is elapsed time < delay?
	blt	Wloop			@Continue with delay	
Xwait:	ldmfd	sp!,{r0-r5,pc}
	
@ *** void Display8Segment (Number:R0; Point:R1;) ***
@ Displays the number 0-9 in R0 on the LED 8-segment display
@ If R1 = 1, the point is also shown
Display8Segment:
	stmfd 	sp!,{r0-r2,lr}
	ldr	r2, =Digits
	ldr	r0, [r2, r0, lsl#2]
	tst	r1,#0x01		@if r1 = 1
	orrne	r0, r0, #SEG_P		@then show "P"
	swi	SWI_SETSEG8
	ldmfd 	sp!,{r0-r2,pc}

	.data
interval:	.word	10000
Time0:		.word   0
Digits:
	.word 	SEG_A|SEG_B|SEG_C|SEG_D|SEG_E|SEG_G 		@0
	.word 	SEG_B|SEG_C 					@1
	.word 	SEG_A|SEG_B|SEG_F|SEG_E|SEG_D 			@2
	.word 	SEG_A|SEG_B|SEG_F|SEG_C|SEG_D 			@3
	.word 	SEG_G|SEG_F|SEG_B|SEG_C 			@4
	.word 	SEG_A|SEG_G|SEG_F|SEG_C|SEG_D 			@5
	.word 	SEG_A|SEG_G|SEG_F|SEG_E|SEG_D|SEG_C 		@6
	.word 	SEG_A|SEG_B|SEG_C 				@7
	.word 	SEG_A|SEG_B|SEG_C|SEG_D|SEG_E|SEG_F|SEG_G	@8
	.word 	SEG_A|SEG_B|SEG_F|SEG_G|SEG_C 			@9
	.word 	0 											@Blank display

myname:			.asciz	"Harrison Ford"
promptblack:	.asciz	"Press a black button"
bye:			.asciz	"BYE"
	.end