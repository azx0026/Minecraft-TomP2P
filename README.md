# TomP2P

> Implementation of the P2P API using the external library TomP2P.

This mod is a `provider` for the [P2P](https://github.com/Nauja/Minecraft/wiki/P2P) mod. It relies on the external library [TomP2P](http://tomp2p.net/) to implement the peer-to-peer functionalities. It takes care of setting up the peer-to-peer network server-side and joining it client-side.

## In-Depth

For the P2P network to work, it requires a master peer known by all the others peers wanting to join the network. Here, the master peer is the Minecraft server and the others are the players.

On server-side, this mod hides all the process of starting the master peer. To make it easier for the players to join the network, it also takes care of sending some informations as the `ip address` and the `port` to players connecting to the Minecraft server so that they don't have to configure it by themselves. The master peer can be configured via the `TomP2P.cfg` described below.

On client-side, this mod hides all the process of starting a peer and joining the P2P network when informations from the master peer have been received. This way, players don't have to make a separate configuration for each server and don't have to search for these informations.

## How to install it

### Client

1. Make sure to have [Forge](http://www.minecraftforge.net/wiki/Installation/Universal) installed.
2. Installs the [P2P](https://github.com/Nauja/Minecraft-P2P) mod.
3. Downloads the `lib.zip` and put its content into `.minecraft/mods`.
4. Downloads the latest `TomP2P-x.x.x-x.x.x.zip` and put it into `.minecraft/mods`.
5. It's done.

### Server

1. Make sure to have [Forge](http://www.minecraftforge.net/wiki/Installation/Universal) installed.
2. Installs the [P2P](https://github.com/Nauja/Minecraft-P2P) mod.
3. Downloads the `lib.zip` and put its content into `yourserver/mods`.
4. Downloads the latest `TomP2P-x.x.x-x.x.x.zip` and put it into `yourserver/mods`.
5. It's done.

## Download

[Releases](https://github.com/Nauja/Minecraft-TomP2P/releases)

## Configuration

As it can sometime be difficult to correctly configure network applications, the configurations showed below are real and working configurations that were used to test this mod.

The server was hosted on a computer with:
* LAN address: 192.168.1.12
* WAN address: arena.servequake.com
* Minecraft port: 25565
* P2P port: 4000

The client was on the same computer with:
* LAN address: 192.168.1.12
* P2P port: 4001

Advices:
* The LAN address is the local address displayed by `ipconfig` or `ifconfig`. The WAN address is a `no-ip` address redirecting to the real WAN address displayed on [mywanip](https://www.google.fr/url?sa=t&rct=j&q=&esrc=s&source=web&cd=1&cad=rja&ved=0CDAQFjAA&url=http%3A%2F%2Fwww.mywanip.com%2F&ei=elW6UdzUCOHl4QSW9YH4BQ&usg=AFQjCNGaAkaIyVPM5G9I_mTvXn8M9qMKNw&sig2=y3UavH4qD_Ch91pKOyEmqA&bvm=bv.47883778,d.bGE).
* Note that the two P2P ports need to be differents if the client and the server are on the same computer. Otherwise, the client and the server will try to listen on the same port.
* For the same reason, the P2P port and the Minecraft port need to be differents.
* Both the P2P port and Minecraft port must be opened server-side or players won't be able to reach the server.
* The P2P port doesn't need to be opened client-side.

### Client

The configuration file `TomP2P.cfg` can be found in the `.minecraft/config` folder. It contains the following:

```
# Configuration file

####################
# client
####################

client {
    S:Address=192.168.1.12
    B:BehindFirewall=false
    I:Port=4001
    S:Storage=memory
}
```

* client
    * Address: LAN address.
    * BehindFirewall: if you are behind a firewall.
        * true: you are behind a firewall.
        * false: you are not.
    * Port: port to listen to.
    * Storage: method used for storage.
        * memory: data are stored in memory.
        * absolute/path/to/folder: data are stored on disk.

### Server

The configuration file `TomP2P.cfg` can be found in the `yourserver/config` folder. It contains the following:

```
# Configuration file

####################
# server
####################

server {
    S:Address=192.168.1.12
    B:BehindFirewall=true
    S:ExternalAddress=arena.servequake.com
    I:Port=4000
    S:Storage=none
}
```

* server
    * Address: LAN address.
    * BehindFirewall: if you are behind a firewall.
        * true: you are behind a firewall.
        * false: you are not.
    * ExternalAddress: WAN address.
    * Port: port to listen to.
    * Storage: method used for storage.
        * none: data are not stored.
        * memory: data are stored in memory.
        * absolute/path/to/folder: data are stored on disk.

## Bugs

Feel free to report any bug [here](https://github.com/Nauja/Minecraft/issues).