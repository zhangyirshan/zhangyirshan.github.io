---
title: KUbernetes基础介绍
date: 2022-08-22 21:25:33
tags: KUbernetes
categories: KUbernetes
---

## 信息介绍

google开发，缩写k8s

## 组件说明

- 服务分类
    有状态服务： DBMS
    无状态服务： LVS APACHE

- 高可用集群副本数据最好是 >= 3奇数个

- APISERVER: 所有服务访问同一入口
- CrontrollerManager： 维持副本期望数目
- Scheduler： 负责介绍任务，选择合适的节点进行分配任务
- ETCD：键值对数据库，存储K8S集群所有重要信息（持久化）
- Kubelet：直接跟容器引擎交互实现容器的生命周期管理
- Kube-proxy：负责写入规则至IPABLES、IPVS实现服务映射访问的
- COREDNS：可以为集群中的SVC创建一个域名IP的对应关系解析
- DASHBOARD： 给K8S集群提供一个B/S结果访问体系
- INGRESS CONTROLLER： 官方只能实现四层代理，INGRESS可以实现七层代理
- FEDERATION：提供一个可以跨集群中心多K8S统一管理功能
- PROMETHEUS：提供K8S集群的监控能力
- ELK：提供K8S集群日志统一分析介入平台

## Pod

### Pod类型


- 自主式Pod
- 控制器管理的Pod

> 每个pod之间是相互隔离的，启动一个pod一定会启动一个pause容器，在此pod中启动的容器公用pause容器的网络栈（注意：因此端口不能冲突），每个容器没有独立的ip地址可以通过localhost互相访问；并且都是共享pause容器的存储卷（网页存储）的

### Pod控制器类型

#### ReplicationController & ReplicaSet & Deployment

ReplicationController用来确保容器应用的副本数始终保持在用户定义的副本数，即如果有容器异常退出，会自动创建新的Pod来替代；而如果一场多出来的容易也会自动回收在新版本的Kubernetes中建议使用ReplicatSet来取代ReplicationController

ReplicatSet跟ReplicationController没有本质的不同，但是ReplicatSet支持集合式的selector

虽然ReplicatSet可以独立使用，但一般还是建议使用Deployment来自动管理ReplicatSet，这样就无需担心跟其他机制的不兼容问题（比如ReplicatSet不支持rolling-update但Deployment支持）

#### HPA（HorizontalPodAutoScale）

Horizontal Pod AutoScaling 仅适用于Deployment和ReplicaSet，在V1版本中仅支持根据Pod的CPU利用率扩容，在vlapha版本中，支持根据内存和用户自定义的metric扩缩容

#### StatefulSet

StatefulSet是为了解决有状态服务的问题（对应Deployments和ReplicaSets是为无状态服务而设计），其应用场景包括：

- 稳定的持久化存储，即Pod重新调度后还是能访问到相同的持久化数据，基于PVC来实现
- 稳定的网络标志，即Pod重新调度后其PodName和HostName不变，基于Headless Service（即没有Cluster IP的Service）来实现
- 有序部署，有序扩展，即Pod是由顺序的，在部署或者扩展的时候要依据定义的顺序依次进行（即从0到N-1，在下一个Pod运行之前所有之前的Pod必须都是Running和Ready状态），基于init containers来实现
- 有序收缩，有序删除（即从N-1到0）

#### DaemonSet

DaemonSet确保全部（或者一些）Node上运行一个Pod的副本。当有Node加入集群时，也会为他们新增一个Pod。当有Node移除集群时，这些Pod也会被回收。删除DaemonSet将会删除它创建的所有Pod

使用DaemonSet的一些典型用法：

- 运行集群存储daemon，例如在每个Node上运行glusterd、ceph
- 在每个Node上运行日志收集daemon，例如fluentd、logstash
- 在每个Node上运行监控daemon，例如Prometheus Node Exporter

#### Job，Cronjob

Job负责批处理任务，即仅执行一次的任务，它保证批处理任务的一个或多个Pod成功结束

Cron Job管理基于时间的Job，即：

- 在给定时间点只运行一次
- 周期性地在给定时间点运行

## 网络通信

> Kubernetes的网络模型假定了所有Pod都在一个可以直接联通的扁平的网络空间中，这在GCE（Google Compute Engine）里面是现场的网络模型，Kubernetes假定这个网络已经存在。而在私有云里搭建Kubernetes集群，就不饿能假定这个网络已经存在了。我们需要自己实现这个网络假设，将不同节点上的Docker容器之间互相范文先打通，然后运行Kubernetes

同一个Pod内的多个容器之间：lo
各Pod之间的通讯：Overlay Network
Pod与Service之间的通讯：各节点的Iptables规则

### Overlay Network: Kubernetes + Flannel

> Flannel是CoreOS团队针对Kubernetes设计的一个网络规划服务，功能是让集群中的不同节点主机创建的Docker容器都具有全集群唯一的虚拟IP地址。而且他还能在这些IP地址之间建立一个覆盖网络（Overlay Network），通过这个覆盖网络，将数据包原封不动地传递到目标容器内。

{% asset_img flannel.png flannel %}

#### ETCD

ETCD之Flannel提供说明：

- 存储管理Flannel可分配的IP地址资源
- 监控ETCD中每个Pod的实际地址，并在内存中建立维护Pod节点路由表