#ifndef _ARP_H
#define _ARP_H

#include "types.h"
#include "ethernet.h"

#define ETHERTYPE_ARP   0x0608  /* Address resolution protocol  */
#define ARPHRD_ETHER    1   	/* ethernet hardware format     */

#define ARPOP_REQUEST   1   	/* request to resolve address   */
#define ARPOP_REPLY     2   	/* response to previous request */

/* ETHERARP -- Ethernet ARP protocol type. */
struct ETHERARP
{
    USHORT arp_hrd;    			/* Format of hardware address.  */
    USHORT arp_pro;    			/* Format of protocol address.  */
    UCHAR  arp_hln;    			/* Length of hardware address.  */
    UCHAR  arp_pln;    			/* Length of protocol address.  */
    USHORT arp_op;     			/* ARP operation. 		 	    */
    UCHAR arp_sha[6]; 			/* Source hardware address.     */
    ULONG arp_spa;    			/* Source protocol address. 	*/
    UCHAR arp_tha[6]; 			/* Target hardware address.     */
    ULONG arp_tpa;    			/* Target protocol address.     */
}__attribute__((packed));

/* arp_frame -- Ethernet ARP frame type. */
struct ARPFRAME
{
    struct ETHERHDR eth_hdr;	/* Ethernet Header Strcut		*/
    struct ETHERARP eth_arp;	/* ARP Protocol Info Struct		*/
}__attribute__((packed));

/* arp_entry -- Ethernet ARP Entry struct. */
struct ARPENTRY
{
    ULONG ae_ip;       			/* IP address. 					*/
    UCHAR ae_ha[6];    			/* Hardware address. 			*/
}__attribute__((packed));

extern int  ArpRequest(ULONG ip, UCHAR *mac);
extern void ArpRespond(void);

#endif /* _ARP_H */

