---
title: ffmpeg
keywords: ffmpeg
summary: ffmpeg
tags:
  - ffmpeg
categories:
  - 06-Frameworks-and-Components
date: 2025-08-21 18:14:23
---

# 官方文档

https://ffmpeg.org/about.html

# 安装过程

# 使用场景

## 提取音频中的文字

### 方法一：使用 FFmpeg + OpenAI Whisper

+ 安装必要的工具
```shell
# 安装
pip install openai-whisper
```

+ 使用 FFmpeg 预处理音频（可选但推荐）

```shell
ffmpeg -i input_video.mp4 -vn -acodec pcm_s16le -ar 16000 -ac 1 output_audio.wav
#-i input_video.mp4: 指定输入视频文件。
#-vn: 禁止输出视频（Video No）。
#-acodec pcm_s16le: 指定音频编码为 PCM 16bit little-endian（Whisper 处理的理想格式之一）。
#-ar 16000: 将采样率设置为 16kHz（对于语音识别完全足够，可以减小文件体积）。
#-ac 1: 将音频混音为单声道（Mono）。
#output_audio.wav: 输出的音频文件名。
```

+ 使用 Whisper 进行语音识别

```shell
whisper output_audio.wav --model base --language zh
```

+ 更高效的“一站式”命令（跳过生成音频文件）

```shell
# 方法A：直接处理视频文件（新版本Whisper支持）
whisper input_video.mp4 --model base --language zh

# 方法B：使用FFmpeg管道输出音频流直接给Whisper
ffmpeg -i input_video.mp4 -vn -acodec pcm_s16le -ar 16000 -ac 1 -f wav - | whisper - --model base --language zh
```