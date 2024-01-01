---
title: DQN practice-maze
keywords: DQN practice-maze
summary: DQN practice maze
tags:
  - DQN
categories:
  - 05-Program-Design
  - Algorithm
  - DQN
date: 2023-12-30 09:47:56
fileCode: true
---

## 系统环境

+ windows + CUDA=11.6
+ miniconda3
+ python=[3.7.12](https://www.python.org/downloads/ 
  - conda create -n dqn python=3.7.12
+ pytorch=[1.7.1](https://pytorch.org/get-started/previous-versions/)  # numpy==1.21.6
  - conda install pytorch==1.7.1 cudatoolkit=11.0 -c pytorch
  - pip install "torch-1.7.1+cpu-cp37-cp37m-win_amd64.whl" # https://download.pytorch.org/whl/torch/
+ conda install gym[classic_control] # gym=0.21.0
+ conda install pygame==2.1.0 tensorboard=2.11.2 tensorboardX==2.5.1 pyglet==1.5.27


## 附录

### 项目地址

```shell
git clone git@github.com:WANGJUEYA/DQN.git
```

### 参考资料

+ [《Playing Atari with Deep Reinforcement Learning》](https://www.cs.toronto.edu/~vmnih/docs/dqn.pdf)
+ [《Human-level Control through Deep Reinforcement Learning：Nature杂志》](https://storage.googleapis.com/deepmind-media/dqn/DQNNaturePaper.pdf)
+ [DQN(Deep Q-learning)算法原理与实现](https://zhuanlan.zhihu.com/p/97856004)
+ [《深度强化学习实战》](https://www.manning.com/books/deep-reinforcement-learning-in-action)
+ [DQN基本概念和算法流程](https://zhuanlan.zhihu.com/p/630554489)

### [CartPole](CartPole.py)

```shell
python CartPole.py
tensorboard --logdir="run/MemoryCapacity_100_CustomReward/"
```

[@fileCode](CartPole.py)

