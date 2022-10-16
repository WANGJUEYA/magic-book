---
title: VMwareWorkstations 虚拟机网络配置连接方式
summary: VMwareWorkstations | 虚拟机网络配置连接方式：桥接、仅主机、NAT、LAN区段
tags:
  - VMware
categories:
  - 02-Operating-System
  - Virtual-Machine
date: 2022-10-15 12:05:13
---

## 桥接模式

a. 直接把虚拟机的网卡接到物理网络(不建议)

```
这种方法是虚拟机的网卡直接与物理机网卡进行通信。
对于Windows的虚拟机而言可能非常方便，不用考虑太多，
用在Linux虚拟机中同样也行，但是不建议采用这种方法，
不利于维护，有时候虚拟机可能无法连接到互联网。
```

b. 建议通过虚拟网络进行桥接；打开虚拟网络编辑器(菜单 "编辑" - "虚拟网络编辑器")

该种方式相当于在虚拟机网卡与物理机物理网卡直接加一个虚拟网络VMnet0，
VMnet0可以选择桥接的网卡是有线网卡还是无线网卡，或者是自动选择。

比如物理机是通过无线网卡上网的，此时VMnet0选择了有线网卡，肯定就不能实现联网。
个人经验是选择“自动”，让VMnet0自动选择能够上网的网卡。
“桥接”是虚拟机的网卡直接把数据包交给物理机的物理网卡进行处理，虽然可以直接与外界进行通信，
但是虚拟机必须有自己的IP地址、DNS、网关等信息。此时，物理机和虚拟机的地位是一样的。

举个例子：物理机是房东、虚拟机是房客、物理机的网卡是大门，房东和房客都能通过大门，但是各自都有钥匙，互不影响，只是大门的所有者是房东而已。

可采用桥接的情景：物理机所处的网络中有DHCP服务，能够自动为网络内的主机分配IP地址。
不适合的情景：校园网，可能内部虚拟机无法联网；物理机拨号上网。

注1: DHCP（Dynamic Host Configuration Protocol，动态主机配置协议）


## NAT模式 (Network Address Translation) 网络地址转换

相当于说在虚拟机与物理机直接添加一个交换机，相当于拥有NAT地址转换功能，能够自动把虚拟机的IP转换为与物理机在同一网段的IP。
比如虚拟机网卡连接到物理机上的虚拟网卡VMnet8，当VMnet8收到虚拟机的数据包时，会把数据包转发给物理机的物理网卡。
相当于物理网卡不直接接触虚拟机的数据包，而是接触VMnet8进行处理。实际上VMnet8是NAT模式，自带DHCP功能，能够给虚拟机分配IP地址。

能够实现虚拟机与物理机之间相互通信、虚拟机到外面的网络通信，但是外面的网络不能到虚拟机通信。

## 仅主机模式 (host-only)
该模式是内部虚拟机连接到一个可提供DHCP功能的虚拟网卡VMnet1上去，VMnet1相当于一个交换机，
将虚拟机发来的数据包转发给物理网卡，但是物理网卡不会将该数据包向外转发。
所以仅主机模式只能用于虚拟机与虚拟机之间、虚拟机与物理机之间的通信。

## 名词解释

### LAN区段
LAN区段相当于说模拟出一个交换机或者集线器出来，把不同虚拟机连接起来，
与物理机不进行数据交流，与外网也不进行数据交流，构建一个独立的网络。
没有DHCP功能，需要手工配置IP或者单独配置DHCP服务器。

### 配置静态IP

当前应用场景: 不需要外网能访问虚机，主机能访问虚机即可，故使用NET模式便可, 由虚机的交换机上配置IP

#### 操作示例

+ [Vmware虚拟机设置静态IP地址](https://www.cnblogs.com/chengssblog/p/6531964.html)
+ [VMware虚拟机Windows sever2016配置静态IP](https://blog.csdn.net/oyxyfd/article/details/106060302)

