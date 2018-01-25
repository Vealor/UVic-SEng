
	.include	"ports.a"

	.equ	Non_Cache_Start,	0x2000000
	.equ	Non_Cache_End,		0xc000000

	@Cache
	.equ	rNCACHBE0,		0x1c00004
	.equ	rNCACHBE1,		0x1c00008

	.text
	.global		ports_init

ports_init:
	stmfd	sp!,{r0-r1,lr}

	@CAUTION:Follow the configuration order for setting the ports. 
	@ 1) setting value 
	@ 2) setting control register 
	@ 3) configure pull-up resistor.  

	@16bit data bus configuration  
	@ PORT A GROUP
	@ BIT 	9	8	7	6	5	4	3	2	1	0
	@	A24	A23	A22	A21	A20	A19	A18	A17	A16	A0
	ldr	r0,=rPCONA
	ldr	r1,=0x1ff
	str	r1,[r0]

	@ PORT B GROUP
	@ BIT 	10		9		8		7		6		5		4		3		2		1		0
	@		/CS5	/CS4	/CS3	/CS2	/CS1	GPB5	GPB4	/SRAS	/SCAS	SCLK	SCKE
	@		EXT		NIC		USB		IDE		SMC		NC		NC		Sdram	Sdram	Sdram	Sdram
	@      1, 		1,   	1,   	1,    	1,    	0,       0,     1,    	1,    	1,   	1	
	ldr	r0,=rPDATB	@P9-LED1 P10-LED2
	ldr	r1,=0x7ff
	str	r1,[r0]

	ldr	r0,=rPCONB
	ldr	r1,=0x1cf
	str	r1,[r0]
    
	@ PORT C GROUP
	@ BUSWIDTH=16
	@  PC15	14		13		12		11		10		9		8
	@	I		I		RXD1	TXD1	I		I		I		I
	@	NC		NC		Uart1	Uart1	NC		NC		NC		NC
	@	00		00		11		11		00		00		00		00

	@  PC7		6		5		4		3		2		1		0
	@   I		I		I		I		I		I		I		I
	@   NC		NC		NC		NC		IISCLK	IISDI	IISDO	IISLRCK
	@   00		00		00		00		11		11		11		11
	ldr	r0,=rPDATC	@rPDATC = 0xff00;
	ldr	r1,=0xff00
	str	r1,[r0]

	ldr	r0,=rPCONC	@rPCONC = 0x0ff0ffff;
	ldr	r1,=0x0ff0ffff
	str	r1,[r0]

	ldr	r0,=rPUPC	@rPUPC  = 0x30ff;//PULL UP RESISTOR should be enabled to I/O
	ldr	r1,=0x30ff
	str	r1,[r0]

	@ PORT D GROUP
	@ PORT D GROUP(I/O OR LCD)
	@ BIT7		6		5		4		3		2		1		0
	@	VF		VM		VLINE	VCLK	VD3		VD2		VD1		VD0
	@	00		00		00		00		00		00		00		00
	ldr	r0,=rPDATD	@rPDATD= 0xff;
	ldr	r1,=0xff
	str	r1,[r0]

	ldr	r0,=rPCOND	@rPCOND= 0xaaaa;	
	ldr	r1,=0xaaaa
	str	r1,[r0]
	
	ldr	r0,=rPUPD	@rPUPD = 0x0;	
	ldr	r1,=0x0
	str	r1,[r0]
	
	@ These pins must be set only after CPU's internal LCD controller is enable
	@ PORT E GROUP 
	@ Bit	8		7		6		5		4		3		2		1		0
	@  	CODECLK	LED4	LED5	LED6	LED7	BEEP	RXD0	TXD0	LcdDisp
	@  	10		01		01		01		01		01		10		10		01
	ldr	r0,=rPDATE	@rPDATE	= 0x1ff;
	ldr	r1,=0x1ff
	str	r1,[r0]

	ldr	r0,=rPCONE	@rPCONE	= 0x25529;
	ldr	r1,=0x25529
	str	r1,[r0]
	
	ldr	r0,=rPUPE	@rPUPE	= 0x6;
	ldr	r1,=0x6
	str	r1,[r0]
		
	@ PORT F GROUP
	@ Bit8		7		6		5		 4		3		2		1		0
	@ IISCLK	IISDI	IISDO	IISLRCK	Input	Input	Input	IICSDA	IICSCL
	@ 100		100		100		100		00		00		00		10		10
	ldr	r0,=rPDATF	@rPDATF = 0x0;
	ldr	r1,=0x0
	str	r1,[r0]

	ldr	r0,=rPCONF	@rPCONF = 0x252a;
	ldr	r1,=0x252a
	str	r1,[r0]
	
	ldr	r0,=rPUPF	@rPUPF  = 0x0;
	ldr	r1,=0x0
	str	r1,[r0]
	
	@ PORT G GROUP
	@ BIT7		6		5		4		3		2		1		0
	@ 	INT7	INT6	INT5	INT4	INT3	INT2	INT1	INT0
	@	S3		S4		S5		S6		NIC		EXT		IDE		USB
	@	11      11      11      11      11      11      11      11
	ldr	r0,=rPDATG	@rPDATG = 0xff;
	ldr	r1,=0xff
	str	r1,[r0]

	ldr	r0,=rPCONG	@rPCONG = 0xffff;
	ldr	r1,=0xffff
	str	r1,[r0]
	
	ldr	r0,=rPUPG	@rPUPG  = 0x0;		//should be enabled  
	ldr	r1,=0x0
	str	r1,[r0]
	
	ldr	r0,=rSPUCR	@rSPUCR = 0x7;  		//D15-D0 pull-up disable
	ldr	r1,=0x7
	str	r1,[r0]

	@ Non Cache area
	@rNCACHBE0=((Non_Cache_End>>12)<<16)|(Non_Cache_Start>>12);
	ldr	r0,=rNCACHBE0
	ldr	r1,=((Non_Cache_End>>12)<<16)|(Non_Cache_Start>>12)
	str	r1,[r0]

	ldmfd	sp!,{r0-r1,pc}

	.end

