---
title: AIAssistant
keywords: AIAssistant
summary: AIAssistant
tags:
  - AI
  - Assistant
categories:
  - 07-Programming-Assistant
date: 2025-02-13 10:49:00
---

## 名词解释

+ **AI编程助手**:
  当前主流AI编程助手离线版本并不具备或仅简单具备编程能力，
  其更多是通过调用云服务器或本地服务器部署的大语言模型来辅助开发者完成各种编程任务，
  例如代码解释、代码生成、代码优化、代码片段补全等。
  插件将AI模型的答复和编译器优雅的结合，方便用户更快速的完成代码开发。


+ **LLMs**:
  大型语言模型（Large Language Models）是指具有数十亿甚至数百亿参数的深度学习模型，
  并且在大规模语料库上进行预训练。这些模型可以生成自然语言文本，
  并且可以用于各种自然语言处理任务，包括文本生成、文本摘要、文本分类等。


+ **ollama**:
  Ollama 是一个专为本地机器设计的开源框架，旨在简化大型语言模型（LLM）的部署、管理和运行。
  它通过整合工具链和预构建模型库，使用户无需依赖云服务或复杂配置即可在本地高效使用各类开源大模型
  （如 LLaMA、Mistral 等），尤其适合隐私敏感、网络受限或需定制化模型的场景


+ **成熟大语言模型**:
  cursor-small、claude-3.5-sonnet、claude-3.5-haiku、claude-3-opus、gpt-4o-mini、gpt-4o、o1、o1-mini、o3-mini、deepseek-v3、deepseek-r1

## 常用插件对比分析

+ **总结**:
  编码助手是一个调用服务端大语言模型的用户友好编程协助工具，
  笔者认为其收费核心应该在于大语言模型自身的能力，而非特定的云服务提供厂商。
  基于以上原因，推荐cursor 和 continue 两个可以灵活配置不同大语言模型的协助工具，
  其中cursor是独立运行的客户端，如果更习惯使用 VSCode 或 IntelliJ IDEA 进行编码，则更推荐 continue。
  对于具体大语言模型的选择，不同模型有不同收费方式，国外节点有网络延迟问题，故推荐 DeepSeek 作为本地大模型选择。
  企业级也可以根据自身需要可以使用 Ollama 私有化部署大语言模型。

|      助手名称       | 核心功能点                                          |                                                                           支持大模型                                                                           |                                       价格                                        |                        官网                        |
|:---------------:|:-----------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------:|:------------------------------------------------:|
| cursor<br>独立客户端 | 多行编辑<br>智能重写<br>光标预测<br>解释代码                   | cursor-small<br>claude-3.5-sonnet<br>claude-3.5-haiku<br>claude-3-opus<br>gpt-4o-mini<br>gpt-4o<br>o1<br>o1-mini<br>o3-mini<br>deepseek-v3<br>deepseek-r1 |                     [价格](https://www.cursor.com/cn/pricing)                     |     [官网](https://www.cursor.com/cn/features)     |
|    continue     | 解释代码<br>生成单元测试<br>生成注释<br>优化代码<br>代码片段补全       |            Ollama 本地部署大模型<br>Claude 3.5 Sonnet<br>Llama 3.1 405B<br>GPT-4o<br>Grok-2<br>Gemini 1.5 Pro<br>Llama 3.1 8B<br>DeepSeek Coder 2 16B            |                                    各api独立收费                                     | [官网](https://docs.continue.dev/chat/model-setup) |
| GitHub Copilot  | 代码生成<br>测试用例生成<br>文档生成                         |                                                          Claude 3.5 Sonnet<br>GPT 4o<br>O3-mini                                                           | [价格](https://github.com/features/copilot/plans?cft=copilot_li.features_copilot) |    [官网](https://github.com/features/copilot)     |
|    CodeGeeX     | 代码自动生成与补全<br>代码翻译<br>自动添加注释                    |                                                                     CodeGeeX4-ALL-9B                                                                      |                                                                                 |            [官网](https://codegeex.cn/)            |
|      通义灵码       | 解释代码<br>生成单元测试<br>生成注释<br>优化代码<br>代码片段补全       |                                                          qwen-2.5<br>deepseek-v3<br>deepseek-r1                                                           |                     [价格](https://lingma.aliyun.com/pricing)                     |         [官网](https://lingma.aliyun.com/)         |
|    腾讯云AI代码助手    | 代码补全<br>单元测试<br>代码审查<br>代码解释                   |                                                                           混元大模型                                                                           |                    [价格](https://copilot.tencent.com/pricing)                    |        [官网](https://copilot.tencent.com/)        |
|   豆包 MarsCode   | 代码补全<br>单元测试<br>代码解释<br>生成注释                   |                                                                           豆包大模型                                                                           |                                                                                 |          [官网](https://www.marscode.cn/)          |
|   文心一言 Comate   | 代码生成<br>注释生成代码<br>代码解释<br>生成单测<br>生成注释<br>代码优化 |                                                                           文心大模型                                                                           |                    [价格](https://comate.baidu.com/zh/pricing)                    |         [官网](https://comate.baidu.com/)          |
|   讯飞 iFlyCode   | 代码生成<br>代码补全<br>代码解释<br>文档注释                   |                                                                           星火大模型                                                                           |                     [价格](https://iflycode.xfyun.cn/product)                     |         [官网](http://iflycode.xfyun.cn/)          |

## 参考链接

+ [国产AI编程辅助插件对比](https://blog.csdn.net/u010554324/article/details/135027694)
+ [IDEA 接入 DeepSeek,太酷了!](https://juejin.cn/post/7468200664117248040)
+ [DeepSeek API 文档](https://api-docs.deepseek.com/zh-cn/quick_start/pricing)