
#ifndef _DHCP_H
#define _DHCP_H

#define DHCP_SERVERPORT     67
#define DHCP_CLIENTPORT     68

#define DHCPOPT_PAD         0
#define DHCPOPT_NETMASK     1
#define DHCPOPT_GATEWAY     3
#define DHCPOPT_DNS         6
#define DHCPOPT_HOSTNAME    12
#define DHCPOPT_DOMAIN      15
#define DHCPOPT_BROADCAST   28
#define DHCPOPT_REQUESTIP   50
#define DHCPOPT_LEASETIME   51
#define DHCPOPT_MSGTYPE     53
#define DHCPOPT_SID         54
#define DHCPOPT_RENEWALTIME 58
#define DHCPOPT_REBINDTIME  59
#define DHCPOPT_END         255

#define DHCP_DISCOVER   1
#define DHCP_OFFER      2
#define DHCP_REQUEST    3
#define DHCP_DECLINE    4
#define DHCP_ACK        5
#define DHCP_NAK        6
#define DHCP_RELEASE    7
#define DHCP_INFORM     8

struct DHCPHDR
{
    UCHAR  bp_op;              /* Packet opcode type: 1=request, 2=reply    */
    UCHAR  bp_htype;           /* Hardware address type: 1=Ethernet 		*/
    UCHAR  bp_hlen;            /* Hardware address length: 6 for Ethernet   */
    UCHAR  bp_hops;            /* Gateway hops 								*/
    ULONG  bp_xid;             /* Transaction ID 							*/
    USHORT bp_secs;            /* Seconds since boot began 					*/
    USHORT bp_flags;           /* RFC1532 broadcast, etc. 					*/
    ULONG  bp_ciaddr;          /* Client IP address 						*/
    ULONG  bp_yiaddr;          /* 'Your' IP address 						*/
    ULONG  bp_siaddr;          /* Server IP address 						*/
    ULONG  bp_giaddr;          /* Gateway IP address 						*/
    UCHAR  bp_chaddr[16];      /* Client hardware address 					*/
    char   bp_sname[64];       /* Server host name 							*/
    char   bp_file[128];       /* Boot file name 							*/
    UCHAR  bp_options[312];    /* Vendor-specific area 						*/
}__attribute__((packed));

extern int DhcpQuery(void);

#endif
