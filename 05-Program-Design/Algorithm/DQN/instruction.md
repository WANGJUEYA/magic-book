---
title: DQN
keywords: DQN
summary: DQN instruction
tags:
  - instruction
date: 2023-12-28 16:44:50
---

## 学习目标

初级：学习包括完整的训练过程和测试。利用argparse 模块实现最简单的参数化运行,完成训练模型的保存和加载。[参考地址](https://github.com/ericyangyu/PPO-for-Beginners)

高级：利用 DQN设计一个agent，通过探索 8x8-map 迷宫，学习从起始位置 (start，绿色 block)到目标 (end，红色block)的最优路径。

输出文档：包括训练环境设置、算法、模型、超参数设置、训练曲线(损失函数、平均累计回报)等。

## 背景介绍

DeepMind 于 2013 年和 2015 年分别提出的两篇论文

[《Playing Atari with Deep Reinforcement Learning》](https://www.cs.toronto.edu/~vmnih/docs/dqn.pdf)

[《Human-level Control through Deep Reinforcement Learning：Nature杂志》](https://storage.googleapis.com/deepmind-media/dqn/DQNNaturePaper.pdf)

其中DeepMind在第一篇中第一次提出Deep Reinforcement Learning（DRL）这个名称，并且提出DQN算法，实现从视频纯图像输入，完全通过Agent学习来玩Atari游戏的成果。之后DeepMind在Nature上发表了改进版的DQN文章（Human-level .....）, 这将深度学习与RL结合起来实现从Perception感知到Action动作的端到端的一种全新的学习算法。**简单理解就是和人类一样，输入感知信息比如眼睛看到的东西，然后通过大脑（深度神经网络)，直接做出对应的行为（输出动作）的学习过程。** 而后DeepMind提出了AlphaZero（完美的运用了DRL+Monte Calo Tree Search）取得了超过人类的水平！

## 相关链接

+ [DQN(Deep Q-learning)算法原理与实现](https://zhuanlan.zhihu.com/p/97856004)