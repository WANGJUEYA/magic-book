---
title: Nginx
summary: Nginx
tags:
  - Nginx
categories:
  - 07-Programming-Assistant
  - Proxy
date: 2022-10-15 15:13:24
---

## 常用命令

```shell
nginx -v # 显示 nginx 的版本
start nginx # 开启nginx
nginx -s stop # 快速关闭Nginx，可能不保存相关信息，并迅速终止web服务。
nginx -s quit # 平稳关闭Nginx，保存相关信息，有安排的结束web服务。
nginx -s reload # 因改变了Nginx相关配置，需要重新加载配置而重载。
nginx -s reopen # 重新打开日志文件。
nginx -c filename # 为 Nginx 指定一个配置文件，来代替缺省的。
nginx -t # 不运行，而仅仅测试配置文件; nginx 将检查配置文件的语法的正确性，并尝试打开配置文件中所引用到的文件。
```

## CentOS7下安装Nginx

### 1. 安装所需环境
~~~shell
yum install gcc-c++
yum install -y pcre pcre-devel
yum install -y zlib zlib-devel
yum install -y openssl openssl-devel
~~~

### 2. 下载
+ 直接下载 .tar.gz 安装包，地址：https://nginx.org/en/download.html
~~~shell
wget -c https://nginx.org/download/nginx-1.26.2.tar.gz
~~~

### 3. 安装

+ [同一主机安装多个nginx](https://blog.csdn.net/u011066470/article/details/118321392)

~~~shell
# Ubuntu安装nginx https://blog.csdn.net/m0_52985087/article/details/132063280
sudo apt-get install gcc
sudo apt-get install libpcre3 libpcre3-dev
sudo apt-get install zlib1g zlib1g-dev
sudo apt-get install openssl
sudo apt-get install libssl-dev

tar -zxvf nginx-1.26.2.tar.gz
cd nginx-1.26.2
# 使用默认配置
# ./configure
# 指定安装路径(安装多个nginx需要配置不同安装路径)
# ./configure --prefix=/usr/local/nginx
# 指定安装路径并开启ssh  # --原来有的模块（如果有的话）
./configure --prefix=/usr/local/nginx --with-http_stub_status_module --with-http_ssl_module
# 编译安装[nginx.conf](..%2F..%2F..%2F..%2F..%2F..%2F%5B0002%5D%C0%A9%D5%B9%CF%EE%C4%BF%2F%5B2024%5D-002%20%D0%F1%D2%AB%D6%C7%BB%DB%C4%DC%D4%B4%B9%DC%C0%ED%C6%BD%CC%A8%2F%CA%B5%CA%A9%D4%CB%CE%AC%2Fnginx.conf)
make && make install
# 查找安装路径
whereis nginx
~~~

### 4. 启动
~~~shell
cd /usr/local/nginx/sbin/
./nginx 

# 此方式相当于先查出 nginx 进程 id 再使用 kill 命令强制杀掉进程。
./nginx -s stop
# 此方式是待 nginx 进程处理任务完毕后停止
./nginx -s quit

# 重新加载配置文件
./nginx -s reload
~~~

### Nginx升级websocket

```nginx.conf
worker_processes  1;
events {
    worker_connections  1024;
}
http {
    include       mime.types;
    default_type  application/octet-stream;
    sendfile        on;
    keepalive_timeout  65;

    map $http_upgrade $connection_upgrade {
        default upgrade;
        ''      close;
    }
    
    # 开启nginx压缩
    gzip on;
    gzip_min_length 1k;
    gzip_comp_level 9;
    gzip_types text/plain application/javascript application/x-javascript text/css application/xml text/javascript application/x-httpd-php image/jpeg image/gif image/png;
    gzip_vary on;
    gzip_disable "MSIE [1-6]\.";    
    
    server {
        listen       9096;
        server_name  localhost;

        # 请求头参数允许下划线
        underscores_in_headers on;
        proxy_pass_request_headers on;

        location /dev-api/ {
            # 请求服务器升级协议为 WebSocket
            proxy_http_version 1.1;
            proxy_set_header Upgrade $http_upgrade;
            proxy_set_header Connection $connection_upgrade;
            # proxy_set_header Upgrade $http_upgrade;
            # proxy_set_header Connection "upgrade";
            # proxy_http_version 1.1;
            
            # 设置读写超时时间，默认 60s 无数据连接将会断开
            proxy_read_timeout 300s;
            proxy_send_timeout 300s;

            proxy_set_header Host 127.0.0.1;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header REMOTE-HOST $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_pass http://127.0.0.1:8880/api/;
        }
        
        location / {
            root html;
            index index.html index.htm;
            if (!-e $request_filename) {
                rewrite ^(.*)$ /index.html?s=$1 last;
                break;
            }
        }
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }
    }
}
```

### 由一个端口代理到另外一个端口

```nginx.conf
server {
	listen 9003;
	server_name redirectV3;    

    location / {
        proxy_pass_request_headers on;
        proxy_pass       http://127.0.0.1:9010;
        proxy_set_header Host 127.0.0.1;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
    }
}
```

### 给同一端口配置多个上下文【应当有更优雅的方式】

```nginx.conf
server {
    location /vs {
		alias   D:/workspace/web-project/dist;
		try_files $uri $uri/ /vs/index.html;
	}
}
```

### 开启https

```nginx.conf
    # https://www.cnblogs.com/aerfazhe/p/15773667.html
    server {
        listen       9090 ssl;
        server_name  wechat;

        ssl_certificate     /usr/local/nginx/tanpeifang/tanpeifang.pem;
        ssl_certificate_key  /usr/local/nginx/tanpeifang/tanpeifang.key;

        ssl_session_cache    shared:SSL:1m;
        ssl_session_timeout  5m;

        ssl_ciphers  HIGH:!aNULL:!MD5;
        ssl_prefer_server_ciphers  on;

		#后台服务配置		
	    location ^~ /api {
			proxy_pass              http://127.0.0.1:8080/zh-mental/;

           		# 请求服务器升级协议为 WebSocket
            		proxy_http_version 1.1;
            		proxy_set_header Upgrade $http_upgrade;
            		proxy_set_header Connection $connection_upgrade;

			proxy_set_header        Host 127.0.0.1;
			proxy_set_header        X-Real-IP $remote_addr;
			proxy_set_header        X-Forwarded-For $proxy_add_x_forwarded_for;
            # 设置读写超时时间，默认 60s 无数据连接将会断开
            proxy_read_timeout 600s;
            proxy_send_timeout 600s;
		}

       	location / {
			root   html;
			index  index.html index.htm;
			if (!-e $request_filename) {
				rewrite ^(.*)$ /index.html?s=$1 last;
				break;
			}
		}
    }
```