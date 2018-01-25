
	.include	"ethernet.a"
	.include	"timer.a"

	.text
	.global		ethernet_init
	.global		ethernet_reset
	.global		ethernet_close
	.global		ethernet_output
	.global		ethernet_input
	.global		ethernet_getmac
	.global		ethernet_getip
	
@/*--- RTL8019AS Register defines ---*/
@/* Register offset applicable to all register pages. */
.equ NIC_CR, 			0x00    @/* Command register 			*/
.equ NIC_IOPORT, 		0x10    @/* I/O data port 				*/
.equ NIC_RESET, 		0x1f    @/* Reset port 					*/

@/* Page 0 register offsets. */
.equ NIC_PG0_CLDA0, 	0x01    @/* Current local DMA address 0 	*/
.equ NIC_PG0_PSTART, 	0x01    @/* Page start register 		 	*/
.equ NIC_PG0_CLDA1, 	0x02    @/* Current local DMA address 1 	*/
.equ NIC_PG0_PSTOP, 	0x02    @/* Page stop register 		 	*/
.equ NIC_PG0_BNRY, 	0x03    @/* Boundary pointer 		 	*/
.equ NIC_PG0_TSR, 	0x04    @/* Transmit status register 	*/
.equ NIC_PG0_TPSR, 	0x04    @/* Transmit page start address 	*/
.equ NIC_PG0_NCR, 	0x05    @/* Number of collisions register*/
.equ NIC_PG0_TBCR0, 	0x05    @/* Transmit byte count register 0 */
.equ NIC_PG0_FIFO, 	0x06    @/* FIFO 						*/
.equ NIC_PG0_TBCR1, 	0x06    @/* Transmit byte count register 1 */
.equ NIC_PG0_ISR, 	0x07    @/* Interrupt status register 	*/
.equ NIC_PG0_CRDA0, 	0x08    @/* Current remote DMA address 0 */
.equ NIC_PG0_RSAR0, 	0x08    @/* Remote start address register 0 
                                 @  Low byte address to read from the buffer.  */
.equ NIC_PG0_CRDA1, 	0x09    @/* Current remote DMA address 1 */
.equ NIC_PG0_RSAR1, 	0x09    @/* Remote start address register 1 
                                 @  High byte address to read from the buffer. */
.equ NIC_PG0_RBCR0, 	0x0a    @/* Remote byte count register 0 
                                 @  Low byte of the number of bytes to read
                                 @  from the buffer. 			*/
.equ NIC_PG0_RBCR1, 	0x0b    @/* Remote byte count register 1
                                 @  High byte of the number of bytes to read
                                 @  from the buffer. 			*/
.equ NIC_PG0_RSR, 	0x0c    @/* Receive status register 	 	*/
.equ NIC_PG0_RCR, 	0x0c    @/* Receive configuration register */
.equ NIC_PG0_CNTR0, 	0x0d    @/* Tally counter 0 (frame alignment errors)*/
.equ NIC_PG0_TCR, 	0x0d    @/* Transmit configuration register*/
.equ NIC_PG0_CNTR1, 	0x0e    @/* Tally counter 1 (CRC errors)	*/
.equ NIC_PG0_DCR, 	0x0e    @/* Data configuration register 	*/
.equ NIC_PG0_CNTR2, 	0x0f    @/* Tally counter 2 (Missed packet errors)  */
.equ NIC_PG0_IMR, 	0x0f    @/* Interrupt mask register 	 	*/

@/* Page 1 register offsets. */
.equ NIC_PG1_PAR0, 	0x01    @/* Physical address register 0 	*/
.equ NIC_PG1_PAR1, 	0x02    @/* Physical address register 1 	*/
.equ NIC_PG1_PAR2, 	0x03    @/* Physical address register 2 	*/
.equ NIC_PG1_PAR3, 	0x04    @/* Physical address register 3 	*/
.equ NIC_PG1_PAR4, 	0x05    @/* Physical address register 4 	*/
.equ NIC_PG1_PAR5, 	0x06    @/* Physical address register 5 	*/
.equ NIC_PG1_CURR, 	0x07    @/* Current page register
                               @    The next incoming packet will be stored
                               @    at this page address. 		*/
.equ NIC_PG1_MAR0, 	0x08    @/* Multicast address register 0 */
.equ NIC_PG1_MAR1, 	0x09    @/* Multicast address register 1 */
.equ NIC_PG1_MAR2, 	0x0a    @/* Multicast address register 2 */
.equ NIC_PG1_MAR3, 	0x0b    @/* Multicast address register 3 */
.equ NIC_PG1_MAR4, 	0x0c    @/* Multicast address register 4 */
.equ NIC_PG1_MAR5, 	0x0d    @/* Multicast address register 5 */
.equ NIC_PG1_MAR6, 	0x0e    @/* Multicast address register 6 */
.equ NIC_PG1_MAR7, 	0x0f    @/* Multicast address register 7 */

@/* Page 2 register offsets. */
.equ NIC_PG2_PSTART, 	0x01   	@/* Page start register 		 	*/
.equ NIC_PG2_CLDA0, 	0x01    @/* Current local DMA address 0 	*/
.equ NIC_PG2_PSTOP, 	0x02    @/* Page stop register 		 	*/
.equ NIC_PG2_CLDA1, 	0x02    @/* Current local DMA address 1 	*/
.equ NIC_PG2_RNP, 	0x03    @/* Remote next packet pointer  	*/
.equ NIC_PG2_TSPR, 	0x04    @/* Transmit page start register	*/
.equ NIC_PG2_LNP, 	0x05    @/* Local next packet pointer   	*/
.equ NIC_PG2_ACU, 	0x06    @/* Address counter (upper) 	 	*/
.equ NIC_PG2_ACL, 	0x07    @/* Address counter (lower) 	 	*/
.equ NIC_PG2_RCR, 	0x0c    @/* Receive configuration register */
.equ NIC_PG2_TCR, 	0x0d    @/* Transmit configuration register*/
.equ NIC_PG2_DCR, 	0x0e    @/* Data configuration register 	*/
.equ NIC_PG2_IMR, 	0x0f    @/* Interrupt mask register 	 	*/

@/* Page 3 register offsets. */
.equ NIC_PG3_EECR,    0x01   	@/* EEPROM command register 		*/
.equ NIC_PG3_BPAGE,   0x02   	@/* Boot-ROM page register 		*/
.equ NIC_PG3_CONFIG0, 0x03   	@/* Configuration register 0 (r/o) */
.equ NIC_PG3_CONFIG1, 0x04   	@/* Configuration register 1 	*/
.equ NIC_PG3_CONFIG2, 0x05   	@/* Configuration register 2 	*/
.equ NIC_PG3_CONFIG3, 0x06   	@/* Configuration register 3 	*/
.equ NIC_PG3_CSNSAV,  0x08   	@/* CSN save register (r/o) 		*/
.equ NIC_PG3_HLTCLK,  0x09   	@/* Halt clock 					*/
.equ NIC_PG3_INTR,    0x0b   	@/* Interrupt pins (r/o) 		*/

@/* Command register bits. */
.equ NIC_CR_STP, 		0x01    @/* Stop 						*/
.equ NIC_CR_STA, 		0x02    @/* Start 						*/
.equ NIC_CR_TXP, 		0x04    @/* Transmit packet 				*/
.equ NIC_CR_RD0, 		0x08    @/* Remote DMA command bit 0 	*/
.equ NIC_CR_RD1, 		0x10    @/* Remote DMA command bit 1 	*/
.equ NIC_CR_RD2, 		0x20    @/* Remote DMA command bit 2 	*/
.equ NIC_CR_PS0, 		0x40    @/* Page select bit 0 			*/
.equ NIC_CR_PS1, 		0x80    @/* Page select bit 1 			*/

@/* Interrupt status register bits. */
.equ NIC_ISR_PRX, 	0x01    @/* Packet received 				*/
.equ NIC_ISR_PTX, 	0x02    @/* Packet transmitted 			*/
.equ NIC_ISR_RXE, 	0x04    @/* Receive error 				*/
.equ NIC_ISR_TXE, 	0x08    @/* Transmit error 				*/
.equ NIC_ISR_OVW, 	0x10    @/* Overwrite warning 			*/
.equ NIC_ISR_CNT, 	0x20    @/* Counter overflow 			*/
.equ NIC_ISR_RDC, 	0x40    @/* Remote DMA complete			*/
.equ NIC_ISR_RST, 	0x80    @/* Reset status 				*/

@/* Interrupt mask register bits. */
.equ NIC_IMR_PRXE, 	0x01    @/* Packet received interrupt enable 	*/
.equ NIC_IMR_PTXE, 	0x02    @/* Packet transmitted interrupt enable 	*/
.equ NIC_IMR_RXEE, 	0x04    @/* Receive error interrupt enable 		*/
.equ NIC_IMR_TXEE, 	0x08    @/* Transmit error interrupt enable 		*/
.equ NIC_IMR_OVWE, 	0x10    @/* Overwrite warning interrupt enable 	*/
.equ NIC_IMR_CNTE, 	0x20    @/* Counter overflow interrupt enable 	*/
.equ NIC_IMR_RCDE, 	0x40    @/* Remote DMA complete interrupt enable */

@/* Data configuration register bits. */
.equ NIC_DCR_WTS, 	0x01    @/* Word transfer select 		*/
.equ NIC_DCR_BOS, 	0x02    @/* Byte order select 			*/
.equ NIC_DCR_LAS, 	0x04    @/* Long address select 			*/
.equ NIC_DCR_LS, 		0x08    @/* Loopback select 				*/
.equ NIC_DCR_AR, 		0x10    @/* Auto-initialize remote 		*/
.equ NIC_DCR_FT0, 	0x20    @/* FIFO threshold select bit 0 	*/
.equ NIC_DCR_FT1, 	0x40    @/* FIFO threshold select bit 1 	*/

@/* Transmit configuration register bits. */
.equ NIC_TCR_CRC, 	0x01    @/* Inhibit CRC 					*/
.equ NIC_TCR_LB0, 	0x02    @/* Encoded loopback control bit 0 */
.equ NIC_TCR_LB1, 	0x04    @/* Encoded loopback control bit 1 */
.equ NIC_TCR_ATD,	0x08    @/* Auto transmit disable 		*/
.equ NIC_TCR_OFST, 	0x10    @/* Collision offset enable 		*/

@/* Transmit status register bits. */
.equ NIC_TSR_PTX, 	0x01	@/* Packet transmitted 			*/
.equ NIC_TSR_COL, 	0x04    @/* Transmit collided 			*/
.equ NIC_TSR_ABT, 	0x08    @/* Transmit aborted 			*/
.equ NIC_TSR_CRS, 	0x10    @/* Carrier sense lost 			*/
.equ NIC_TSR_FU, 		0x20    @/* FIFO underrun 				*/
.equ NIC_TSR_CDH, 	0x40    @/* CD heartbeat 				*/
.equ NIC_TSR_OWC, 	0x80    @/* Out of window collision 		*/

@/* Receive configuration register bits. */
.equ NIC_RCR_SEP, 	0x01    @/* Save errored packets 		*/
.equ NIC_RCR_AR, 		0x02    @/* Accept runt packets 			*/
.equ NIC_RCR_AB, 		0x04    @/* Accept broadcast 			*/
.equ NIC_RCR_AM, 		0x08    @/* Accept multicast 			*/
.equ NIC_RCR_PRO, 	0x10    @/* Promiscuous physical 		*/
.equ NIC_RCR_MON, 	0x20    @/* Monitor mode 				*/

@/* Receive status register bits. */
.equ NIC_RSR_PRX, 	0x01    @/* Packet received intact 		*/
.equ NIC_RSR_CRC, 	0x02    @/* CRC error 					*/
.equ NIC_RSR_FAE, 	0x04    @/* Frame alignment error 		*/
.equ NIC_RSR_FO, 		0x08    @/* FIFO overrun 				*/
.equ NIC_RSR_MPA, 	0x10    @/* Missed packet 				*/
.equ NIC_RSR_PHY, 	0x20    @/* Physical/multicast address 	*/
.equ NIC_RSR_DIS, 	0x40    @/* Receiver disabled 			*/
.equ NIC_RSR_DFR, 	0x80    @/* Deferring 					*/

@/* EEPROM command register bits. */
.equ NIC_EECR_EEM1,  	0x80    @/* EEPROM Operating Mode 		*/
.equ NIC_EECR_EEM0,  	0x40    @/*  EEPROM Operating Mode
                                 @   - 0 0 Normal operation
                                 @   - 0 1 Auto-load 
                                 @   - 1 0 9346 programming 
                                 @   - 1 1 Config register write enab */
.equ NIC_EECR_EECS,  	0x08    @/* EEPROM Chip Select 			*/
.equ NIC_EECR_EESK,  	0x04    @/* EEPROM Clock 				*/
.equ NIC_EECR_EEDI,  	0x02    @/* EEPROM Data In 				*/
.equ NIC_EECR_EEDO,  	0x01    @/* EEPROM Data Out 				*/

@/* Configuration register 2 bits. */
.equ NIC_CONFIG2_PL1, 	0x80 	@/* Network media type 		*/
.equ NIC_CONFIG2_PL0, 	0x40 	@/* Network media type
                                  @  	- 0 0 TP/CX auto-detect 
                                  @  	- 0 1 10baseT 
                                  @  	- 1 0 10base5 
                                  @  	- 1 1 10base2 			*/
.equ NIC_CONFIG2_BSELB, 	0x20 	@/* BROM disable 			*/
.equ NIC_CONFIG2_BS4,   	0x10 	@/* BROM size/base 			*/
.equ NIC_CONFIG2_BS3,   	0x08
.equ NIC_CONFIG2_BS2,   	0x04
.equ NIC_CONFIG2_BS1,   	0x02
.equ NIC_CONFIG2_BS0,   	0x01

@/* Configuration register 3 bits */
.equ NIC_CONFIG3_PNP,     0x80 	@/* PnP Mode 				*/
.equ NIC_CONFIG3_FUDUP,   0x40 	@/* Full duplex 				*/
.equ NIC_CONFIG3_LEDS1,   0x20 	@/* LED1/2 pin configuration 
                                  @      - 0 LED1 == LED_RX, LED2 == LED_TX 
                                  @      - 1 LED1 == LED_CRS, LED2 == MCSB */
.equ NIC_CONFIG3_LEDS0,   0x10 	@/* LED0 pin configration
                                  @      - 0 LED0 pin == LED_COL 
                                  @      - 1 LED0 pin == LED_LINK*/
.equ NIC_CONFIG3_SLEEP,   0x04 	@/* Sleep mode 				*/
.equ NIC_CONFIG3_PWRDN,   0x02 	@/* Power Down 				*/
.equ NIC_CONFIG3_ACTIVEB, 0x01 	@/* inverse of bit 0 in PnP Act Reg */

@/* Configuration register 1 bits */
.equ NIC_CONFIG1_IRQEN,	0x80	@/* Enable IRQ				*/
.equ NIC_CONFIG1_IRQS2,	0x40	@/* IRQ Select				*/
.equ NIC_CONFIG1_IRQS1,	0x20	@/* IRQ Select				*/
.equ NIC_CONFIG1_IRQS0,	0x10	@/* IRQ Select				
									@	- 0 0 0 INT0
									@	- 0 0 1 INT1
									@	- 0 1 0 INT2
									@	- 0 1 1 INT3
									@	- 1 0 0 INT4
									@	- 1 0 1 INT5
									@	- 1 1 0 INT6
									@	- 1 1 1 INT7			*/
.equ NIC_CONFIG1_IOS3,	0x08	@/* I/O Base Address			*/
.equ NIC_CONFIG1_IOS2,	0x04	@/* I/O Base Address			*/
.equ NIC_CONFIG1_IOS1,	0x02	@/* I/O Base Address			*/
.equ NIC_CONFIG1_IOS0,	0x01	@/* I/O Base Address			*/

.equ NIC_PAGE_SIZE,       0x100
.equ NIC_START_PAGE,      0x40
.equ NIC_STOP_PAGE,       0x60
.equ NIC_TX_PAGES,        6
.equ NIC_TX_BUFFERS,      2
.equ NIC_FIRST_TX_PAGE,   NIC_START_PAGE
.equ NIC_FIRST_RX_PAGE,   (NIC_FIRST_TX_PAGE + NIC_TX_PAGES * NIC_TX_BUFFERS)
.equ TX_PAGES,            12

.equ	ether_base,			0x02180000

.equ	NICPKYTHEADERSZ,		4

@/* Read byte from controller register. */
@#define nic_read(reg) 		 *(ether_base + (reg)*0x200)

.macro nic_read reg
	stmfd	sp!,{r1-r2}
	ldr		r1,=\reg
	ldr		r2,=ether_base
	ldrb	r0,[r2,r1,lsl#9]
	ldmfd	sp!,{r1-r2}
.endm

.macro nic_write reg
	stmfd	sp!,{r1-r2}
	ldr		r1,=\reg
	ldr		r2,=ether_base
	strb	r0,[r2,r1,lsl#9]
	ldmfd	sp!,{r1-r2}
.endm

.macro nic_writec reg value
	stmfd	sp!,{r0-r2}
	ldr		r0,=\value
	ldr		r1,=\reg
	ldr		r2,=ether_base
	strb	r0,[r2,r1,lsl#9]
	ldmfd	sp!,{r0-r2}
.endm

ethernet_init:
	stmfd	sp!,{r1-r5,lr}

	bl		ethernet_reset
	cmp		r0,#0
	bne		ein99

	nic_writec	NIC_PG0_IMR	0x0		@// disable 8019 interrupt
    nic_writec	NIC_PG0_ISR	0xff	@// clear interrupt status
   	
	@/* stop command,complete DMA,RTL8019 configuration(Page 3) */
	nic_writec	NIC_CR	NIC_CR_STP | NIC_CR_RD2 | NIC_CR_PS0 | NIC_CR_PS1
	
    @/* write register enable */
    nic_writec	NIC_PG3_EECR		NIC_EECR_EEM0 | NIC_EECR_EEM1
    
    @/* jumper mode, half-duplex */
    nic_writec	NIC_PG3_CONFIG3		0x0
    
    @/* 8019as */
    nic_writec	NIC_PG3_CONFIG0		0x0
    
    @/* Auto-detect, BROM disable */
    nic_writec	NIC_PG3_CONFIG2		NIC_CONFIG2_BSELB
    
    @/* write register disable */
    nic_writec	NIC_PG3_EECR		0x0

	mov		r0,#100
	bl		timer_delay
    
    @/* Page 0 configure */
    nic_writec	NIC_CR				NIC_CR_STP | NIC_CR_RD2
    
   	@/* Normal Operate, FIFO threshold 1 */
    nic_writec	NIC_PG0_DCR			NIC_DCR_LS | NIC_DCR_FT1
    
    @/* clear Remote byte count Register */
    nic_writec	NIC_PG0_RBCR0		0x0
    nic_writec	NIC_PG0_RBCR1		0x0
    
    @/* receive monitor mode */
    nic_writec	NIC_PG0_RCR			NIC_RCR_MON
    
    @/* Internel Loopback */
    nic_writec	NIC_PG0_TCR			NIC_TCR_LB0
    
    @/* Transmit Page Start */
    nic_writec	NIC_PG0_TPSR		NIC_FIRST_TX_PAGE
    
    @/* Boundary */
    nic_writec	NIC_PG0_BNRY		NIC_STOP_PAGE-1
    
    @/* Receive Page Start */
    nic_writec	NIC_PG0_PSTART		NIC_FIRST_RX_PAGE
    
    @/* Receive Page Stop */
    nic_writec	NIC_PG0_PSTOP		NIC_STOP_PAGE
    nic_writec	NIC_PG0_ISR			0xff			@// clear interrupt status
    
    @/* Page 1 configure */
    nic_writec	NIC_CR				NIC_CR_STP | NIC_CR_RD2 | NIC_CR_PS0

	@set mac address of NIC
	ldr		r1,=NIC_PG1_PAR0
	ldr		r2,=ether_base
	ldr		r3,=mac_addr
	mov		r4,#6
ein05:
	ldrb	r0,[r3],#1
	strb	r0,[r2,r1,lsl#9]
	add		r1,r1,#1
	subs	r4,r4,#1
	bne		ein05
	
	@set multi-cast address of NIC
	mov		r0,#0x0
	ldr		r1,=NIC_PG1_MAR0
	ldr		r2,=ether_base
	mov		r4,#8
ein10:
	strb	r0,[r2,r1,lsl#9]
	add		r1,r1,#1
	subs	r4,r4,#1
	bne		ein10

    @/* Current Page */
    nic_writec	NIC_PG1_CURR		NIC_FIRST_RX_PAGE
    
    @/* Page 0 Configure */
    nic_writec	NIC_CR				NIC_CR_STP | NIC_CR_RD2
    
    @/* only accept destination address message OR broadcast address */
    nic_writec	NIC_PG0_RCR			0x4
    nic_writec	NIC_PG0_ISR			0xff			@// clear interrupt status
    
    @/* enable received interrupt */
    nic_writec	NIC_PG0_IMR			NIC_IMR_PRXE
    
    @/* Start command */
    nic_writec	NIC_CR				NIC_CR_STA | NIC_CR_RD2
    
   	@/* CRC enable, Normal Operate */
    nic_writec	NIC_PG0_TCR			0x0

	mov		r0,#100
	bl		timer_delay

ein99:
	ldmfd	sp!,{r1-r5,pc}


ethernet_reset:
	stmfd	sp!,{r1-r7,lr}

	mov		r6,#20
ers05:
	nic_read	NIC_RESET
	mov		r5,r0

	mov		r0,#50
	bl		timer_delay
	
	mov		r0,r5
	nic_write	NIC_RESET

	mov		r7,#20
ers10:
	mov		r0,#50
	bl		timer_delay

@if(nic_read(NIC_PG0_ISR) & NIC_ISR_RST)
	nic_read	NIC_PG0_ISR
	ands		r0,r0,#NIC_ISR_RST
	mov			r0,#0
	bne			ers99
	
	subs		r7,r7,#1
	bne			ers10

	subs		r6,r6,#1
	bne			ers05

	mov			r0,#1

ers99:
	ldmfd	sp!,{r1-r7,pc}


	
ethernet_close:
	stmfd	sp!,{r0,lr}

	nic_writec	NIC_PG0_IMR		0		@// disable interrupt
    nic_writec	NIC_PG0_ISR		0xff	@// clear interrupt status
	nic_writec	NIC_CR			NIC_CR_STP | NIC_CR_RD2

	ldmfd	sp!,{r0,pc}

@Input:r0 ptr to int
@returns r0:ptr to input buffer (NULL if no data)
@		 size bytes witten to r0 input
ethernet_input:
	stmfd	sp!,{r2-r12,lr}
	stmfd	sp!,{r0}

einp05:
	nic_read	NIC_PG0_ISR
	tst			r0,#NIC_ISR_RXE
	beq			einp10
	
	ldmfd		sp!,{r0}
	mov			r0,#0
	b			einp99

einp10:
	tst			r0,#NIC_ISR_PRX
	beq			einp05

	@r7==isr
	mov			r7,r0
	
	nic_writec	NIC_CR		NIC_CR_STA | NIC_CR_RD2 | NIC_CR_PS0

	@r5==curr page
	nic_read	NIC_PG1_CURR
	mov			r5,r0
	
	nic_writec	NIC_CR		NIC_CR_STA | NIC_CR_RD2
	
	@r4==bnry page
	nic_read	NIC_PG0_BNRY
	mov			r4,r0
	add			r4,r4,#1
	
	cmp			r4,#NIC_STOP_PAGE
	movge		r4,#NIC_FIRST_RX_PAGE

	@curr == bnry?
	cmp			r4,r5
	bne			einp15
	
	mov			r0,r7
	nic_write	NIC_PG0_ISR
	b			einp05

einp15:

	@/* Read the NIC specific packet header. */
	nic_writec	NIC_PG0_RBCR0	NICPKYTHEADERSZ
	nic_writec	NIC_PG0_RBCR1	0
	nic_writec	NIC_PG0_RSAR0	0
	
	mov			r0,r4
	nic_write	NIC_PG0_RSAR1
	nic_writec	NIC_CR			NIC_CR_STA | NIC_CR_RD0

	mov			r8,#NICPKYTHEADERSZ
	ldr			r9,=nic_hdr
einp20:
	nic_read	NIC_IOPORT
	strb		r0,[r9],#1
	subs		r8,r8,#1
	bne			einp20
	
	@/** Complete remote dma. */
	nic_writec	NIC_CR			NIC_CR_STA | NIC_CR_RD2
	mov			r8,#20
einp25:
	nic_read	NIC_PG0_ISR
	tst			r0,#NIC_ISR_RDC
	bne			einp30
	subs		r8,r8,#1
	bne			einp25
		
einp30:
	nic_writec	NIC_PG0_ISR		NIC_ISR_RDC

	@nextpg = bnry + (hdr.ph_size >> 8) + ((hdr.ph_size & 0xFF) != 0);
	ldr			r9,=nic_hdr+2
	mov			r8,#0
	ldrh		r8,[r9]
	tst			r8,#0x00ff
	moveq		r11,#0x00
	movne		r11,#0x01
	add			r11,r11,r8,lsr#8
	add			r11,r11,r4

@if( nextpg >= NIC_STOP_PAGE )
@{
@	nextpg -= NIC_STOP_PAGE;
@	nextpg += NIC_FIRST_RX_PAGE;
@}
	cmp			r11,#NIC_STOP_PAGE
	subge		r11,r11,#NIC_STOP_PAGE
	addge		r11,r11,#NIC_FIRST_RX_PAGE

@if( nextpg != hdr.ph_nextpg )
@{
	ldr			r9,=nic_hdr+1
	mov			r8,#0
	ldrb		r8,[r9]
	cmp			r11,r8
	beq			einp31
	
@UCHAR nextpg1 = nextpg + 1;
	mov			r10,r11
	add			r10,r10,#1
            
@if( nextpg1 >= NIC_STOP_PAGE )
@{
@	nextpg1 -= NIC_STOP_PAGE;
@	nextpg1 += NIC_FIRST_RX_PAGE;
@}
	cmp			r10,#NIC_STOP_PAGE
	subge		r10,r10,#NIC_STOP_PAGE
	addge		r10,r10,#NIC_FIRST_RX_PAGE

@if( nextpg1 != hdr.ph_nextpg )
@{
@	nic_write(NIC_PG0_ISR, isr);
@    break;
@}
	ldr			r9,=nic_hdr+1
	mov			r8,#0
	ldrb		r8,[r9]
	cmp			r10,r8
	beq			einp32
	
	mov			r0,r7
	nic_write	NIC_PG0_ISR
	b			einp05

einp32:

@nextpg = nextpg1;
	mov			r11,r10

	
einp31:
	ldr			r9,=nic_hdr
	ldrb		r0,[r9]
	ands		r0,r0,#0x0000000f
	cmp			r0,#0x00000001
	bne			einp05
	
	ldrh		r8,[r9,#2]
	
	
	@/* Set remote dma byte count and start address. Don't read the
	@ * header again. */
	mov			r0,r8
	nic_write	NIC_PG0_RBCR0
	mov			r0,r8,lsl#8
	nic_write	NIC_PG0_RBCR1
	
    nic_writec	NIC_PG0_RSAR0	NICPKYTHEADERSZ
    mov			r0,r4
    nic_write	NIC_PG0_RSAR1
    

	@/* Perform the read. */
	nic_writec	NIC_CR		NIC_CR_STA | NIC_CR_RD0
	ldr			r9,=ether_hdr
einp35:
	nic_read	NIC_IOPORT
	strb		r0,[r9],#1
	subs		r8,r8,#1
	bne			einp35

	@/* Complete remote dma. */
	nic_writec	NIC_CR		NIC_CR_STA | NIC_CR_RD2

	mov		r8,#20
einp40:
	nic_read	NIC_PG0_ISR
	tst			r0,#NIC_ISR_RDC
	bne			einp45
	subs		r8,r8,#1
	bne			einp40	
einp45:
	nic_writec	NIC_PG0_ISR		NIC_ISR_RDC
	
@/* Set boundary register to the last page we read. */
@if( --nextpg < NIC_FIRST_RX_PAGE )
@	nextpg = NIC_STOP_PAGE - 1;
@nic_write(NIC_PG0_BNRY, nextpg);
	sub			r11,r11,#1
	cmp			r11,#NIC_FIRST_RX_PAGE
	movlt		r11,#NIC_STOP_PAGE - 1
	
	mov			r0,r11
	nic_write	NIC_PG0_BNRY

	ldr			r9,=nic_hdr+2
	mov			r1,#0
	ldrh		r1,[r9]
	ldmfd		sp!,{r0}
	str			r1,[r0]
	ldr			r0,=ether_hdr
	
einp99:
	ldmfd	sp!,{r2-r12,pc}

ethernet_getmac:
	ldr		r0,=mac_addr
	mov		pc,lr



@R0:ptr to data
@R1:size in bytes
ethernet_output:
	stmfd		sp!,{r0-r7,lr}
	mov			r5,r0
	mov			r6,r1

	@/* Register Page 0 operate */
	nic_writec	NIC_CR	NIC_CR_STA | NIC_CR_RD2

	@/* set byte count */
	mov			r0,r6
	nic_write	NIC_PG0_RBCR0

	@/* set byte count */
	mov			r0,r6,lsr#8
	nic_write	NIC_PG0_RBCR1

	@/* Remote Start Address*/
	nic_writec	NIC_PG0_RSAR0	0

	@/* Remote Start Address */
	nic_writec	NIC_PG0_RSAR1	NIC_FIRST_TX_PAGE

	@/* remote write */
	nic_writec	NIC_CR		NIC_CR_STA | NIC_CR_RD1
    
	@/* Transfer data. */
	mov			r9,r5
	mov			r8,r6
eto05:
	ldrb		r0,[r9],#1
	nic_write	NIC_IOPORT
	subs		r8,r8,#1
	bne			eto05

	@/** Complete remote dma. */
	nic_writec	NIC_CR	NIC_CR_STA | NIC_CR_RD2

	@/* if completed */
	mov			r8,#20
eto10:
	nic_read	NIC_PG0_ISR
	tst			r0,#NIC_ISR_RDC
	bne			eto15
	subs		r8,r8,#1
	bne			eto10
eto15:
	@/* clear status */
	nic_writec	NIC_PG0_ISR	NIC_ISR_RDC
        
	@/* Number of bytes to be transmitted. */
	mov			r0,r6
	and			r0,r0,#0x00ff
	nic_write	NIC_PG0_TBCR0

	mov			r0,r6,lsr#8
	and			r0,r0,#0x00ff
	nic_write	NIC_PG0_TBCR1
        
	@/* First page of packet to be transmitted. */
	nic_writec	NIC_PG0_TPSR	NIC_FIRST_TX_PAGE
        
	@/* Start transmission. */
	nic_writec	NIC_CR	NIC_CR_STA | NIC_CR_TXP | NIC_CR_RD1 | NIC_CR_RD0

	@/* Wait until transmission is completed or aborted. */
eto20:
	nic_read	NIC_CR
	tst			r0,#NIC_CR_TXP
	bne			eto20

	@/* Complete DMA. */
	nic_writec	NIC_CR	NIC_CR_STA | NIC_CR_RD2

	ldmfd		sp!,{r0-r7,pc}


ethernet_getip:
	ldr			r0,=src_ip
	ldr			r0,[r0]
	mov			pc,lr


	.data
	.align
	
@/* local MAC Address */
src_ip:		.byte	0x8e,0x68,0x63,0x03				@142.104.99.3
mac_addr:	.byte	0x00,0x06,0x98,0x01,0x7E,0x8F

	.align
nic_hdr:	.skip	NICPKYTHEADERSZ
ether_hdr:	.skip	6+6+2
packt_data:	.skip	1500

	.end
