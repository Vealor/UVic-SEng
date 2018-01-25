reset							; reset board
memwrite 0x01D30000 0x00000000	; WTCON (watchdog timer control Register)  disable warch dog
memwrite 0x01E0000C 0x07ffffff	; INTMSK (disable all interrupt)
memwrite 0x01E00024 0xffffffff	; clear all interrupt
memwrite 0x01C80000 0x11110102	; BWSCON (Bus Width & Wait Status Control Register)
memwrite 0x01C80004 0x00000600	; BANKCON0
memwrite 0x01C80008 0x00007FFC	; BANKCON1
memwrite 0x01C8000C 0x00007FFC	; BANKCON2
memwrite 0x01C80010 0x00007FFC	; BANKCON3
memwrite 0x01C80014 0x00007FFC	; BANKCON4
memwrite 0x01C80018 0x00007FFC	; BANKCON5
memwrite 0x01C8001C 0x00018000	; BANKCON6
memwrite 0x01C80020 0x00018000	; BANKCON7
memwrite 0x01C80024 0x00860459	; REFRESH
memwrite 0x01C80028 0x00000010	; BANKSIZE
memwrite 0x01C8002C 0x00000020	; MRSRB6
memwrite 0x01C80030 0x00000020	; MRSRB7
