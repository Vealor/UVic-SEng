SECTIONS
{
	. = 0x0C000000;
	Image_RO_Base = .;
	.text : { *(.text) }
	Image_RO_Limit = .;
	Image_RW_Base = .;
	.data : { *(.data) }
	.rodata : { *(.rodata) }
	Image_RW_Limit = .;
	Image_ZI_Base = .;
	.bss : { *(.bss) }
	Image_ZI_Limit = .;
	__bss_start__ = .;
	__bss_end__ = .;
	__EH_FRAME_BEGIN__ = .;
	__EH_FRAME_END__ = .;
PROVIDE (__stack = .);
	end = .;
	_end = .;
	.debug_info     0 : { *(.debug_info)  }
 	.debug_line		0 : { *(.debug_line)  } 
   	.debug_abbrev   0 : { *(.debug_abbrev)}
   	.debug_frame    0 : { *(.debug_frame) }
}