---
title: Huggingface 模型初尝试 | 启动 ChatGLM 模型
keywords: ChatGLM
summary: Huggingface 模型初尝试 | 启动 ChatGLM 模型
tags:
  - ChatGLM
  - Huggingface
date: 2023-08-16 16:21:51
---

## 前言

+ 本系列文章旨在使用 ChatGLM 预处理模型接入业务系统，完成项目助手类插件服务开发调用
+ 基本思路是学会使用基础模型、学会微调基础模型、了解并掌握向量数据库的使用(实时数据交互)、完成业务接入 <u>NLP</u> 服务
+ 本文是系列文章第一篇：启动 ChatGLM 模型

### 提前安装

+ [conda](https://docs.conda.io/en/latest/miniconda.html#windows-installers) # python包管理工具、可以安装一个内置的 [python](https://www.python.org/)

```shell
# 添加相关镜像源 加快下载速度
conda config --add channels https://pypi.tuna.tsinghua.edu.cn/simple/ # python 软件包索引源
conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/main/
conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/free/
conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud/conda-forge/
conda config --add channels https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud/pytorch/

conda config --show channels # 展示所有镜像源
```

+ [pytorch](https://pytorch.org) # 根据 CUDA 版本选择正确的安装命令; `nvidia-smi` 查看对应 version

```shell
# 当前系统查询出来 CUDA版本为 11.6
conda install pytorch torchvision torchaudio pytorch-cuda=11.6 -c pytorch -c nvidia
```

## 下载并启动 chatGLM 代码

### 下载仓库代码

```shell
git clone git@github.com:THUDM/ChatGLM-6B.git # https://github.com/THUDM/ChatGLM-6B.git
```

### 下载预处理模型

```shell
git lfs install # 大文件存储工具
# 在项目根目录下创建`THUDM`模型文件夹，同时进入目录克隆模型[注册一个账户配置 ssh 可以下载更快]
git clone git@hf.co:THUDM/chatglm-6b-int4 # https://huggingface.co/THUDM/chatglm-6b-int4
cd chatglm-6b-int4
# 大文件下载；如果不能下载，前往下方地址手动下载模型
git lfs pull
```

[清华大学模型下载地址](https://cloud.tsinghua.edu.cn/d/674208019e314311ab5c/)

| 模型名称        | 模型大小 | 模型所需GPT与内存    |
| --------------- | -------- | -------------------- |
| chatglm-6b      | 12.4g    | 最低13G显存，16G内存 |
| chatglm-6b-int8 | 7.2G     | 最低8G显存       |    
| chatglm-6b-int4 | 3.6G     | 最低4.3G显存         |

### 通过conda创建一个克隆环境；也可以直接忽略该步骤使用base环境安装依赖

```shell
conda create -n chatglm --clone base # 创建一个克隆环境
conda activate chatglm # 激活新创建的环境; conda deactivate # 退出当前环境
```

### 运行web示例demo; 需要先安装`gradio`

```shell
conda install --file requirements.txt # 提示找不到包时可增加其他镜像源
# pip install -r requirements.txt -i https://mirror.sjtu.edu.cn/pypi/web/simple
pip install mdtex2html torch cpm_kernels # 提示找不到包尝试用pip命令安装
# 下载模型文件, 创建`THUDM`文件夹

python web_demo.py
```

## docker部署`Langchain-Chatchat` 

> `Langchain-Chatchat` 一个基于 `chatGLM` 的本地知识库实现

```shell
git clone git@github.com:chatchat-space/Langchain-Chatchat.git
```

## 名词解释

### NLP

NLP 是自然语言处理（Natural Language Processing）的缩写，本文学习的 transformers 是一种自然语言处理的思想及技术

### Hugging face

Hugging face 起初是一家聊天机器人初创服务商，其在github上开源了 Transformers python 库。
还提供了 Trainer （构建和训练自定义模型的框架） 、 Tuning （用于微调预训练模型的库）等库。
这些工具和库都使用 Python 编写，并支持多种不同的深度学习框架，如 PyTorch 和 TensorFlow 等。
主要目的是为 NLP 社区提供一个统一的框架，便于研究人员和开发人员能够轻松地使用最先进的预训练模型，并将其应用于各种不同的 NLP 任务中。

### PyTorch

PyTorch 是一个开源的Python机器学习库，Hugging face很多模型需要前置安装 PyTorch

### Transformer

Transformer 是 <u>Hugging face</u> 提供的自然语言处理工具库，也是当前比较流行的机器学习工具库。
因为NLP通常是多个任务顺序而成，通常使用 `transformer` 中 `pipeline` 进行流水线工作

```shell
# pip 是 python 包管理工具, Python 3.4+ 自带pip工具
pip install transformers==4.26.1 # 安装指定版本
pip install tensorflow
# 如果你是conda的话
conda install -c huggingface transformers  # 4.0以后的版本才会有
```

### requirements.txt

Python项目中必须包含一个 requirements.txt 文件，用于记录所有依赖包及其精确的版本号。以便新环境部署。
```shell
pip freeze > requirements.txt # 生成requirements.txt
pip install -r requirements.txt # 从requirements.txt安装依赖
```

## 参考资料

+ [Hugging face 官方网站](https://huggingface.co/models)
+ [Huggingface 超详细介绍](https://zhuanlan.zhihu.com/p/535100411)
+ [ChatGLM-6B 代码仓库](https://github.com/THUDM/ChatGLM-6B)
+ [从零开始的ChatGLM 配置详细教程](https://blog.csdn.net/qq_51116518/article/details/130299417)
