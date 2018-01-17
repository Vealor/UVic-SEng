# *******************************************************
# * NAME    : timer.s									*
# * Version : 23.Feb.2007								*
# * Description:										*
# *	This module controls the WatchDog timer to run as a	*
# *	free running 16 timer. It is used to calculate		*
# *	elapsed time.										*
# *														*
# *******************************************************
	.global	timer_get_time
	.global	timer_init
	.global	timer_close
	.global	timer_delay	
	
	#Watchdog timer
	.equ 	WTCON,		0x01d30000
	.equ	WTDAT,		0x01d30004
	.equ	WTCNT,		0x01d30008

#Gets the value of the watchdog timer and computes the
#number of ms since the last time called and computes
#a current time.
timer_get_time:
	stmfd	sp!,{r1-r2,lr}

	ldr		r0,=WTCNT
	ldr		r0,[r0]
	ldr	r1,=0xffff
	sub	r2, r1, r0

	mov		r0,r2,LSR#1
	
	ldmfd	sp!,{r1-r2,pc}


#Disable the watchdog timer
#Note: this is called before the stacks are initialized.
#Do not use the stack here!
timer_close:
	ldr	    r0,=WTCON
	ldr	    r1,=0x0 		
	str	    r1,[r0]
	mov		pc,lr


timer_init:
	stmfd	sp!,{r0-r3,lr}
	ldr		r0,=(255<<8)
	orr		r0,r0,#(0x3 << 3)
	ldr		r1,=WTCON
	str		r0,[r1]
	
	ldr		r2,=0xffff
	ldr		r3,=WTDAT
	str		r2,[r3]
	ldr		r3,=WTCNT
	str		r2,[r3]

	orr		r0,r0,#(0x01 << 5)	
	str		r0,[r1]
	
	ldr		r0,=WTCNT
	ldr		r0,[r0]

	ldmfd	sp!,{r0-r3,pc}

timer_delay:
	stmfd	sp!,{r0-r3,lr}
	mov		r1,r0
	bl		timer_get_time
	mov		r2,r0
tmd05:
	bl		timer_get_time
	sub		r0,r0,r2
	cmp		r0,r1
	bcc		tmd05
	ldmfd	sp!,{r0-r3,pc}

	.end
	