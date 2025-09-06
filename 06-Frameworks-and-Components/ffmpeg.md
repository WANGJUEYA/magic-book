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

## 快速切割合并视频

+ 切割文件 https://zhuanlan.zhihu.com/p/567582170
+ 合并文件 https://www.moonfly.net/archives/145.html

```shell
# -ss 开始帧 -t 时长 -to 结束帧 -告诉 FFmpeg 复制音频和视频而不执行重新编码——这非常快！
ffmpeg -ss 00:00:00 -i 3.mp4 -t 01:18:00 -c:v copy -c:a copy  trim_ipseek_copy1.mp4
ffmpeg -i 3.mp4 -ss 01:23:30 -to 03:00:00 -c:v copy -c:a copy  trim_ipseek_copy2.mp4
ffmpeg -i trim_ipseek_copy1.mp4 -i trim_ipseek_copy2.mp4 -filter_complex "[0:v][0:a][1:v][1:a]concat=n=2:v=1:a=1[v][a]" -map "[v]" -map "[a]" output.mp4
## 硬件加速  NVIDIA  -c:v h264_nvenc  | -c:v  hevc_nvenc 
ffmpeg -encoders | findstr hevc # ffmpeg -encoders | grep hevc
ffmpeg -i trim_ipseek_copy1.mp4 -i trim_ipseek_copy2.mp4 -filter_complex "[0:v][0:a][1:v][1:a]concat=n=2:v=1:a=1[v][a]" -map "[v]" -map "[a]" -c:v h264_nvenc -c:a aac output.mp4
```

## 提取音频中的文字

### 方法一：使用 FFmpeg + OpenAI Whisper

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
# 安装
pip install openai-whisper
# 使用显卡直接管道处理
whisper output_audio.wav --language zh --device cuda  --output_format txt | tee output.txt
# 使用显卡处理
# 转换
whisper output_audio.wav --model base --language zh
# 打印控制台并输出到文档
whisper output_audio.wav --model base --language Chinese --model base > output_text.txt
# 将繁体字转换成简体字
conda install -c conda-forge opencc 
# 使用pip直接下载的opencc-python 无法在命令行中使用
# pip install opencc # python -c "import opencc; print('OpenCC 已成功安装')"
opencc -i output_text.txt -o output_text_simplified.txt -c t2s
# 全程管道处理
whisper output_audio.wav --language zh --output_format txt | opencc -c t2s | tee output.txt
```

+ 更高效的“一站式”命令（跳过生成音频文件）

```shell
# 方法A：直接处理视频文件（新版本Whisper支持）
whisper input_video.mp4 --model base --language zh

# 方法B：使用FFmpeg管道输出音频流直接给Whisper
ffmpeg -i input_video.mp4 -vn -acodec pcm_s16le -ar 16000 -ac 1 -f wav - | whisper - --model base --language zh
```