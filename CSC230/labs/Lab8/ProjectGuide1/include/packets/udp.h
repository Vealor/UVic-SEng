#ifndef _UDP_H
#define _UDP_H

#include "types.h"

/* UDP protocol header struct */
typedef struct
{
    USHORT uh_sport;   			/* Source port 		*/
    USHORT uh_dport;   			/* Destination port */
    USHORT uh_ulen;    			/* UDP length 		*/
    USHORT uh_sum;     			/* UDP checksum 	*/
}__attribute__((packed)) UDPHDR;

/* max_len -- buffer size which pDataBuffer pointer */
//extern int UdpInput(USHORT port, USHORT tms, char* pDataBuffer, int max_len);

/* dip   -- destination ip address
   dport -- destination UDP port
   sport -- source UPD port
   pData -- data pointer
   len   -- data len (must no more than 1024 bytes) */
//extern int UdpOutput(ULONG dip, USHORT dport, USHORT sport, char *pData, USHORT len);

#endif /* _UDP_H */
