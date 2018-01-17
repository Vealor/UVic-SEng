
#ifndef _ethernet_H_
#define _ethernet_H_

/* MAC Protocol Header */
typedef struct
{
    UCHAR  ether_dhost[6];     		/* Destination MAC address. */
    UCHAR  ether_shost[6];     		/* Source MAC address. 		*/
    USHORT ether_type;         		/* Protocol type. 			*/
}__attribute__((packed)) ETHERHDR;

#endif /* _ethernet_H_ */
