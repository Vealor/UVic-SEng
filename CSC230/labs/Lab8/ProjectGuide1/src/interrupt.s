
	.include	"interrupt.a"

	@start of ram address	
	.equ	__RAM_START,		0x0c000000

	@start of the ISR vectors address
	.equ	_ISR_STARTADDRESS,			0xc7fff00
	
	@these vectors are the 8 trap vectors of the ARM cpu
	.equ	pISR_RESET,		_ISR_STARTADDRESS+0x0
	.equ	pISR_UNDEF,		_ISR_STARTADDRESS+0x4
	.equ	pISR_SWI,		_ISR_STARTADDRESS+0x8
	.equ	pISR_PABORT,	_ISR_STARTADDRESS+0xc
	.equ	pISR_DABORT,	_ISR_STARTADDRESS+0x10
	.equ	pISR_RESERVED,	_ISR_STARTADDRESS+0x14
	.equ	pISR_IRQ,		_ISR_STARTADDRESS+0x18
	.equ	pISR_FIQ,		_ISR_STARTADDRESS+0x1c

	@these vectors are for the individual interrupts of the iterrupt controller
	@there are 26 external interrupts available
	.equ	pISR_ADC,		_ISR_STARTADDRESS+0x20
	.equ	pISR_RTC,		_ISR_STARTADDRESS+0x24
	.equ	pISR_UTXD1,		_ISR_STARTADDRESS+0x28
	.equ	pISR_UTXD0,		_ISR_STARTADDRESS+0x2c
	.equ	pISR_SIO,		_ISR_STARTADDRESS+0x30
	.equ	pISR_IIC,		_ISR_STARTADDRESS+0x34
	.equ	pISR_URXD1,		_ISR_STARTADDRESS+0x38
	.equ	pISR_URXD0,		_ISR_STARTADDRESS+0x3c
	.equ	pISR_TIMER5,	_ISR_STARTADDRESS+0x40
	.equ	pISR_TIMER4,	_ISR_STARTADDRESS+0x44
	.equ	pISR_TIMER3,	_ISR_STARTADDRESS+0x48
	.equ	pISR_TIMER2,	_ISR_STARTADDRESS+0x4c
	.equ	pISR_TIMER1,	_ISR_STARTADDRESS+0x50
	.equ	pISR_TIMER0,	_ISR_STARTADDRESS+0x54
	.equ	pISR_UERR01,	_ISR_STARTADDRESS+0x58
	.equ	pISR_WDT,		_ISR_STARTADDRESS+0x5c
	.equ	pISR_BDMA1,		_ISR_STARTADDRESS+0x60
	.equ	pISR_BDMA0,		_ISR_STARTADDRESS+0x64
	.equ	pISR_ZDMA1,		_ISR_STARTADDRESS+0x68
	.equ	pISR_ZDMA0,		_ISR_STARTADDRESS+0x6c
	.equ	pISR_TICK,		_ISR_STARTADDRESS+0x70
	.equ	pISR_EINT4567,	_ISR_STARTADDRESS+0x74
	.equ	pISR_EINT3,		_ISR_STARTADDRESS+0x78
	.equ	pISR_EINT2,		_ISR_STARTADDRESS+0x7c
	.equ	pISR_EINT1,		_ISR_STARTADDRESS+0x80
	.equ	pISR_EINT0,		_ISR_STARTADDRESS+0x84


	@INTERRUPT
	.equ	rINTCON,	0x1e00000
	.equ	rINTPND,	0x1e00004
	.equ	rINTMOD,	0x1e00008
	.equ	rINTMSK,	0x1e0000c

	.equ	rI_PSLV,	0x1e00010
	.equ	rI_PMST,	0x1e00014
	.equ	rI_CSLV,	0x1e00018
	.equ	rI_CMST,	0x1e0001c
	.equ	rI_ISPR,	0x1e00020
	.equ	rI_ISPC,	0x1e00024

	.text
	.global interrupt_mask_all
	.global interrupt_init
	.global interrupt_disable
	.global	interrupt_enable
	.global	interrupt_clear_pending
	.global	interrupt_setvector
	.global interrupt_clearAll


@mask all interrupts. DO NOT USE STACK
@ This function is called at startup before
@ any stacks are init.
interrupt_mask_all:
    ldr	    r0,=rINTMSK
    ldr	    r1,=0x07ffffff  	/* all interrupt disable */
    str	    r1,[r0]
	mov		pc,lr
	
@Initialize the interrupt controller
interrupt_init:
	stmfd	sp!,{r0-r2,lr}

	@all interrupts are set to generate an IRQ	
	ldr		r0,=rINTMOD
	ldr		r1,=0x00
	str		r1,[r0]

	@non-vector mode, IRQ enabled, FIQ disabled
	ldr		r0,=rINTCON
	ldr		r1,=0x05
	str		r1,[r0]

	@disable all interrupts
	ldr		r0,=rINTMSK
	ldr		r1,=0x07ffffff		@all interrupt disable
	str		r1,[r0]

	@clear all pending interrupts
	bl		interrupt_clearAll

	@Setup IRQ handler
	ldr	r0,=pISR_IRQ
	ldr	r1,=IsrIRQ
	str	r1,[r0]

	ldmfd	sp!,{r0-r2,pc}


@IRQ handler
@Called when an IRQ interrupt has occurred
@IRQ line was asserted by the interrupt controller
IsrIRQ:
	sub	sp,sp,#4	@reserved for PC
	stmfd	sp!,{r8-r9}

	@Note:ISPR can be 0 if controller is not handled correctly.
	@make a check here
	ldr	r9,=rI_ISPR
	ldr	r9,[r9]
	cmp	r9,#0x0		@If the IDLE mode work-around is used,
				@r9 may be 0 sometimes
	beq	l2

	mov	r8,#0x0
l0:
	movs	r9,r9,lsr #1
	bcs	l1
	add	r8,r8,#4
	b	l0
l1:
	ldr	r9,=pISR_ADC
	add	r9,r9,r8
	ldr	r9,[r9]
	str	r9,[sp,#8]
	ldmfd	sp!,{r8-r9,pc}
l2:
	ldmfd	sp!,{r8-r9}
	add	sp,sp,#4
	subs	pc,lr,#4

@R0:interrupt index to set
@R1:vector to set	
interrupt_setvector:
	stmfd	sp!,{r0-r2,lr}

	mov		r2,#0
isv05:
	movs	r0,r0,ror#1
	addcc	r2,r2,#1
	bcc		isv05
	
	ldr		r0,=pISR_ADC
	str		r1,[r0,r2,lsl#2]

	ldmfd	sp!,{r0-r2,pc}

@Disable a set of interrupts
@R0:bits set will disable corresponding interrupts
interrupt_disable:
	stmfd	sp!,{r0-r2,lr}
	ldr		r1,=rINTMSK
	ldr		r2,[r1]
	orr		r2,r2,r0
	str		r2,[r1]
	ldmfd	sp!,{r0-r2,pc}

@Enable a set of interrupts
@R0:bits set will enable corresponding interrupts
interrupt_enable:
	stmfd	sp!,{r0-r2,lr}
	ldr		r1,=rINTMSK
	ldr		r2,[r1]
	mvn		r0,r0
	and		r2,r2,r0
	str		r2,[r1]
	ldmfd	sp!,{r0-r2,pc}

@Clears a specific set of pending interrupts
@R0:bits set will clear corresponding interrupts
interrupt_clear_pending:
	stmfd	sp!,{r1,lr}
	ldr		r1,=rI_ISPC
	str		r0,[r1]
	ldmfd	sp!,{r1,pc}

@Clears all pending interrupts
interrupt_clearAll:
	stmfd	sp!,{r0,lr}
	ldr		r0,=0xffffffff
	bl		interrupt_clear_pending
	ldmfd	sp!,{r0,pc}

	.end
	
