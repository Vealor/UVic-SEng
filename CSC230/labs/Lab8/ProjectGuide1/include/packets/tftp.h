#ifndef _TFTP_H
#define _TFTP_H

#include "types.h"

/* TFTP protocol struct */
struct TFTPHDR
{
    short th_opcode;            /* packet type 			*/
    short tu_block;         	/* block # 				*/
}__attribute__((packed));

#define TFTP_RRQ     01  		/* TFTP read request packet. 			*/
#define TFTP_WRQ     02  		/* TFTP write request packet. 			*/
#define TFTP_DATA    03  		/* TFTP data packet. 					*/
#define TFTP_ACK     04  		/* TFTP acknowledgement packet. 		*/
#define TFTP_ERROR   05  		/* TFTP error packet. 					*/
#define TFTP_OACK    06  		/* TFTP option acknowledgement packet.  */
#define TFTP_CLOSE	 0x80		/* TFTP close							*/

#define TFTP_PORT	 4660

extern char* TftpRecv(int* pLen);
extern int MakeAnswer();

#endif /* _TFTP_H */
