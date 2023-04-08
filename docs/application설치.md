
# 🍏 공통사항

### 한국시간으로 변경 및 hostname
```
sudo rm /etc/localtime
sudo ln -s /usr/share/zoneinfo/Asia/Seoul /etc/localtime
sudo hostnamectl set-hostname 400-elk
```

### jdk 설치
```
sudo apt update
sudo apt install openjdk-17-jre-headless
```

# 🍎 Elastic 설치


### OS환경설정
/etc/sysctl.conf 에 아래내용 추가
```
vm.max_map_count=262144
```

/etc/security/limits.conf
```
*       soft    nproc   65536
*       hard    nproc   65536
*       soft    nofile  65536
*       hard    nofile  65536
```

### vi .bashrc
```shell
ES_JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export ES_JAVA_HOME
```


## elastic search 설치

### vi jvm.options
-Xms2g
-Xmx2g

### vi elasticsearch.yml
```
cluster.name: my-elk
node.name: node-1
network.host: 0.0.0.0
http.port: 9200
transport.port: 9300
discovery.seed_hosts: ["0.0.0.0"]
discovery.type: single-node

xpack.security.enabled: false
xpack.security.http.ssl:
  enabled: false
xpack.security.transport.ssl:
  enabled: false
```

### vi start.sh
```
cd /home/ubuntu/elasticsearch-8.5.2
bin/elasticsearch -d -p PID
```

### elastic service 확인
```shell
curl http://3.34.51.162:9200/_cat/nodes?v
curl http://3.34.51.162:9200/_cat/health?v
```


# 🍐 kibana 설치

### vi kibana.yml
```
server.port: 5601
server.host: "0.0.0.0"
elasticsearch.hosts: ["http://localhost:9200"]
```

### vi start.sh
```shell
cd /home/ubuntu/kibana-8.5.2
./bin/kibana > /dev/null 2>&1 & KIBANA_PID=$!
echo $KIBANA_PID > ./kibana.pid
```


### vi kill.sh
```shell
KIBANA_PID=`cat ./kibana.pid`
kill -9 $KIBANA_PID
echo "Kibana Daemon is killed"
sleep 2
rm -f ./kibana.pid
```



## logstash 설치

### vi logstash-my.conf
```
input {
  beats {
    port => 5044
    codec => "json"
  }
}

output {
  elasticsearch {
    hosts => ["http://localhost:9200"]
    index => "adlog-%{+YYYY.MM.dd}"
  }
}
```

### vi start.sh
```shell
./bin/logstash -f config/logstash-my.conf &
```


# 🍊 mysql 
### mysql 설치
```shell
sudo apt update
sudo apt install mysql-server
```

### 권할 설정
```
sudo mysql -u root -p
ALTER user 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'password!';
flush privileges;

create database test;
create user test1@'%' identified by 'password!';
grant select, insert, update, delete on test.* to teset1@'%' ;
grant all privileges on test.* to test1@'%' ;
```

### 원격접속 허용
/etc/mysql/mysql.conf.d/mysqld.cnf bind-address 편집
```
#bind-address           = 127.0.0.1
#mysqlx-bind-address    = 127.0.0.1

sudo systemctl restart mysql
sudo service mysql restart
```

# 🍋 redis 서버

### Docker 설치
```shell
sudo apt-get update
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable edge test"
sudo apt install docker.io

-- /usr/bin/docker.io 실행 파일을 /usr/local/bin/docker로 링크하여 사용
sudo ln -sf /usr/bin/docker.io /usr/local/bin/docker
```

### Docker 권한부여
```shell
sudo usermod -a -G docker $USER
sudo service docker restart
```

### redis-cli 설치
```shell
sudo apt install redis-tools
```

### vi redis.conf
```
protected-mode no
port 3000
tcp-backlog 511

timeout 0
tcp-keepalive 300
#daemonize yes
daemonize no
supervised no
#pidfile /data/redis-server.pid

loglevel notice
logfile "/data/redis-server.log"
databases 16
always-show-logo yes
save ""
stop-writes-on-bgsave-error no
rdbcompression yes
rdbchecksum yes
dbfilename "dump.rdb"
dir "/data"

replica-serve-stale-data yes
replica-read-only yes
repl-diskless-sync no
repl-diskless-sync-delay 5
repl-disable-tcp-nodelay no
replica-priority 100

maxmemory 500000000
maxmemory-policy allkeys-lru
maxmemory-samples 5

lazyfree-lazy-eviction no
lazyfree-lazy-expire no
lazyfree-lazy-server-del no
replica-lazy-flush no

appendonly yes
appendfilename "appendonly.aof"
appendfsync everysec

no-appendfsync-on-rewrite no
auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 12mb

aof-load-truncated yes
aof-use-rdb-preamble yes
lua-time-limit 5000

slowlog-log-slower-than 10000
slowlog-max-len 128
latency-monitor-threshold 0
dynamic-hz yes
aof-rewrite-incremental-fsync yes
rdb-save-incremental-fsync yes
```

### Dockerfile로 빌드
```shell
FROM redis
RUN groupmod -g 1000 redis
RUN usermod -u 1000 -g 1000 redis
USER 1000
CMD [ "redis-server", "/data/redis.conf" ]
```

### docker 생성 및 실행
```shell
docker build -t seaking7/redis:1.0 .
docker build -t seaking7/redis:1.0 -f redis-server/Dockerfile .
docker login -u seaking7
docker push seaking7/redis:1.0
docker pull seaking7/redis:1.0

docker run --name redis-alone -v /home/ubuntu/redis-alone:/data -p 3000:3000 -d seaking7/redis:1.0 
docker exec -it redis-alone /bin/bash
docker run --name redis-container -v /home/ubuntu/redis:/data -p 6379:6379 -d redis:6.2.6 redis-server /data/redis.conf
```

### docker 실행시 conf 수정 참고
```shell
daemonize no
# bind 127.0.0.1
protected-mode no
```


# 🍌 application 서버

### vi start.sh
```shell
PROCESS=deliver
CLASSPATH=/home/ubuntu/${PROCESS}/${PROCESS}-1.0.jar;

export CLASSPATH

count=$(ps -ef | grep "app.name=$PROCESS" | grep -v grep | grep -v tail | wc -l)
if [ $count -eq 1 ]
then
        p_string=$(ps -ef | grep "app.name=$PROCESS" | grep -v grep | grep -v tail)
        pid=$(echo $p_string | awk '{print $2}')
        kill -9 $pid
        echo "$PROCESS is killed"
        sleep 3
fi

java -cp $CLASSPATH -Dprocess_id=$PROCESS -Dapp.name=$PROCESS -jar ${PROCESS}-1.0.jar >> log/${PROCESS}.log &
echo "$PROCESS is started."
```

### filebeat apt 설치
```shell
wget -qO - https://artifacts.elastic.co/GPG-KEY-elasticsearch | sudo apt-key add -
sudo apt-get install apt-transport-https
echo "deb https://artifacts.elastic.co/packages/8.x/apt stable main" | sudo tee -a /etc/apt/sources.list.d/elastic-8.x.list
sudo apt-get update && sudo apt-get install filebeat
sudo systemctl enable filebeat
sudo systemctl restart filebeat
```

### vi filebeat.yml
```shell
filebeat.inputs:
- type: log
  enabled: true
  paths:
    - /home/ubuntu/deliver/log/deliver.log

json.keys_under_root: true
json.add_error_key: true
json.message_key: log

filebeat.config.modules:
  path: ${path.config}/modules.d/*.yml
  reload.enabled: false

setup.ilm.enabled: false
setup.template.enabled: false

output.elasticsearch:
  hosts: ["100.51.7.67:9200"]
  protocol: "http"
    #pipeline: "geoip"
  index: "deliver-log"

#output.logstash:
  #hosts: ["localhost:5044"]

processors:
  - add_id:
      target_field: "deliverSeq"
  - decode_json_fields:
      fields: ['message']
      target: ''
      overwrite_keys: true
      max_depth: 1
  - drop_fields:
      fields: ["ecs", "agent", "message", "log"]
```

### filebeat restart
sudo service filebeat restart




## nginx 설치
sudo apt install nginx

### vi /etc/nginx/sites-available/map.conf
```shell
server {
listen 80 default_server;
listen [::]:80 default_server;
        root /var/www/html;
        index index.html index.htm index.nginx-debian.html;
        server_name map.ilovegithub.com;

        location / {
                proxy_pass http://localhost:8080;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header Host $http_host;
        }
}
```

ln -s /etc/nginx/sites-available/map.conf /etc/nginx/sites-enabled/

## https 적용
```shell
sudo apt install letsencrypt
sudo letsencrypt certonly --standalone -d map.ilovegithub.com
Successfully received certificate.
Certificate is saved at: /etc/letsencrypt/live/map.ilovegithub.com/fullchain.pem
Key is saved at:         /etc/letsencrypt/live/map.ilovegithub.com/privkey.pem
```

### map.conf 수정
```shell
server {
        server_name map.ilovegithub.com;
        listen 80;

        location / {
                return 301 https://map.ilovegithub.com$request_uri;
        }
}

server {
        server_name map.ilovegithub.com;
        listen 443 ssl;

        ssl on;
        ssl_certificate /etc/letsencrypt/live/map.ilovegithub.com/fullchain.pem;
        ssl_certificate_key /etc/letsencrypt/live/map.ilovegithub.com/privkey.pem;

        location / {
                proxy_pass http://localhost:8080;
        }
}
```


# oauth 설정

### naver 로그인
서비스 URL
```shell
https://map.ilovegithub.com
```
Callback URL
```shell
http://map.ilovegithub.com/login/oauth2/code/naver
```

## google 로그인
API 및 서비스 - 사용자 인증정보 

승인된 리디렉션 URI
https://map.ilovegithub.com/login/oauth2/code/google
