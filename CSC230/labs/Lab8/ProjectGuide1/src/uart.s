
	.include	"uart.a"
	.include	"timer.a"

	@UART
	.equ	rULCON0,	0x1d00000
	.equ	rULCON1,	0x1d04000
	.equ	rUCON0,		0x1d00004
	.equ	rUCON1,		0x1d04004
	.equ	rUFCON0,	0x1d00008
	.equ	rUFCON1,	0x1d04008
	.equ	rUMCON0,	0x1d0000c
	.equ	rUMCON1,	0x1d0400c
	.equ	rUTRSTAT0,	0x1d00010
	.equ	rUTRSTAT1,	0x1d04010
	.equ	rUERSTAT0,	0x1d00014
	.equ	rUERSTAT1,	0x1d04014
	.equ	rUFSTAT0,	0x1d00018
	.equ	rUFSTAT1,	0x1d04018
	.equ	rUMSTAT0,	0x1d0001c
	.equ	rUMSTAT1,	0x1d0401c
	.equ	rUBRDIV0,	0x1d00028
	.equ	rUBRDIV1,	0x1d04028

	@Little Endian
	.equ	rUTXH0,		0x1d00020
	.equ	rUTXH1,		0x1d04020
	.equ	rURXH0,		0x1d00024
	.equ	rURXH1,		0x1d04024
	.equ	WrUTXH0,	0x1d00020	@byte access
	.equ	WrUTXH1,	0x1d04020	@byte access
	.equ	RdURXH0,	0x1d00024
	.equ	RdURXH1,	0x1d04024
	.equ	UTXH0,		0x1d00020	@//byte_access address by BDMA
	.equ	UTXH1,		0x1d04020
	.equ	URXH0,		0x1d00024
	.equ	URXH1,		0x1d04024

	.text
	.global		uart_init
	.global		uart_sendByte
	.global		uart_sendString
	.global		uart_getString
	.global		uart_getByte
	.global		uart_checkByte
	
uart_init:
	stmfd	sp!,{r0-r2,lr}

	ldr	r0,=rUFCON0	@rUFCON0=0x0;     //FIFO disable
	ldr	r1,=0x00
	str	r1,[r0]

	ldr	r0,=rUFCON1	@rUFCON1=0x0;
	str	r1,[r0]

	ldr	r0,=rUMCON0	@rUMCON0=0x0;
	str	r1,[r0]

	ldr	r0,=rUMCON1	@rUMCON1=0x0;
	str	r1,[r0]

	@UART0
	ldr	r0,=rULCON0	@rULCON0=0x3;     //Normal,No parity,1 stop,8 bit
	ldr	r1,=0x3
	str	r1,[r0]

	ldr	r0,=rUCON0	@rUCON0=0x245;    //rx=edge,tx=level,disable timeout int.,enable rx error int.,normal,interrupt or polling
	ldr	r1,=0x245
	str	r1,[r0]

	ldr	r0,=rUBRDIV0	@rUBRDIV0=( (int)(mclk/16./baud + 0.5) -1 );
	ldr	r1,=0x22
	str	r1,[r0]

	@UART1
	ldr	r0,=rULCON1	@rULCON1=0x3;     //Normal,No parity,1 stop,8 bit
	ldr	r1,=0x3
	str	r1,[r0]

	ldr	r0,=rUCON1	@rUCON1=0x245;    //rx=edge,tx=level,disable timeout int.,enable rx error int.,normal,interrupt or polling
	ldr	r1,=0x245
	str	r1,[r0]

	ldr	r0,=rUBRDIV1	@rUBRDIV1=( (int)(mclk/16./baud + 0.5) -1 );
	ldr	r1,=0x22
	str	r1,[r0]

	ldmfd	sp!,{r0-r2,pc}
	
	
uart_sendByte:
	stmfd	sp!,{r0-r4,lr}

	ldr		r1,=rUTRSTAT0
	ldr		r3,=WrUTXH0

	mov		r4,r0
	cmp		r4,#'\n'
	bne		usb05

	ldr		r0,=10

usb10:
	ldr		r2,[r1]
	ands	r2,r2,#0x02
	beq		usb10
@	bl		timer_delay
	ldr		r2,='\r'
	strb	r2,[r3]	
usb05:
	ldr		r2,[r1]
	ands	r2,r2,#0x02
	beq		usb05

@	bl		timer_delay
	strb	r4,[r3]

	ldmfd	sp!,{r0-r4,pc}

uart_sendString:
	stmfd	sp!,{r0-r1,lr}
	mov		r1,r0
uss05:
	ldrb	r0,[r1],#1
	cmp		r0,#0
	beq		uss10
	bl		uart_sendByte
	b		uss05
uss10:
	ldmfd	sp!,{r0-r1,pc}

uart_checkByte:
	ldr		r0,=rUTRSTAT0
	ldr		r0,[r0]
	and		r0,r0,#0x01
	mov		pc,lr

uart_getByte:
	stmfd	sp!,{lr}
ugb05:
	bl		uart_checkByte
	cmp		r0,#0
	beq		ugb05
	ldr		r0,=RdURXH0
	ldr		r0,[r0]
	ldmfd	sp!,{pc}

uart_getString:
	stmfd	sp!,{lr}
	ldmfd	sp!,{pc}

	.end

