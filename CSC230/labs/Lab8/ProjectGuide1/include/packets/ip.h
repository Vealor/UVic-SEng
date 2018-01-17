#ifndef _IP_H
#define _IP_H

#include "types.h"

#define INADDR_BROADCAST    (ULONG)0xffffffff
#define ETHERTYPE_IP    	0x0008  /*  IP protocol */
#define IPPROTO_IP      	0   	/*  Dummy for IP. */
#define IPPROTO_ICMP    	1   	/*  Control message protocol. */
#define IPPROTO_TCP     	6   	/*  Transmission control protocol. */
#define IPPROTO_UDP     	17  	/*  User datagram protocol. */
#define IPVERSION   		4   	/*  IP protocol version. */
#define IP_DF       		0x4000  /*  Don't fragment flag. */
#define IP_MF       		0x2000  /*  More fragments flag. */
#define IP_OFFMASK  		0x1fff  /*  Mask for fragmenting bits. */

typedef struct
{
	UCHAR  ip_hl:4,        /*  Header length. 			*/
           ip_v:4;         /*  Version. 				*/
	UCHAR  ip_tos;         /*  Type of service. 		*/
    short  ip_len;         /*  Total length. 			*/
    USHORT ip_id;          /*  Identification. 			*/
    short  ip_off;         /*  Fragment offset field.  	*/
	UCHAR  ip_ttl;         /*  Time to live. 			*/
	UCHAR  ip_p;           /*  Protocol. 				*/
    USHORT ip_sum;         /*  Checksum. 				*/
    ULONG  ip_src;         /*  Source IP address. 	   	*/
    ULONG  ip_dst;         /*  Destination IP address. 	*/
}__attribute__((packed)) IPHDR;

//extern USHORT IpChkSum(USHORT *buf, USHORT count);
//extern int IpInput(UCHAR proto, USHORT tms);
//extern int IpOutput(ULONG dst, UCHAR proto, USHORT len);

#endif /* _IP_H */
