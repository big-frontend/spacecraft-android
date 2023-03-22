# ENV
## 包管理
包管理 |虚拟环境管理
|---|---
pip|venv/virtualenv/Virtualenvwrapper
pipenv|pipenv
conda|conda

pipenv常用指令
```bash
pipenv --python 3.7
pipenv shell  # 激活虚拟环境
##requirements 包管理
pip3 freeze > requirements.txt
pip3 install -r requirements.txt
##pipenv 包管理
pipenv install /  pipenv install -d --skip-lock
pipenv lock -r --dev > requirements.txt
pipenv install -r  requirements.txt
pipenv graph  # 查看目前安装的库及其依赖
pipenv update --outdated  # 查看所有需要更新的依赖项
pipenv update  # 更新所有包的依赖项
pipenv update <包名>  # 更新指定的包的依赖项
exit  # 退出当前虚拟环境
```

## `环境配置 + 依赖获取`
- pipenv install
- docker-compose up -d(influxdb：root/12345678、grafana)

## `项目部署`

### docker部署
1. docker build -t viserver:default .
2. docker run -d -p 9000:9000 --name viserver viserver:default


### gunicorn + nginx部署

1.安装Nginx并且在 /etc/nginx/sites-available/default文件中server -- location位置添加如下转发
```
proxy_pass http://127.0.0.1:8000;
proxy_set_header Host $host;
proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
```
2.gunicorn -c gunicorn.conf.py main:app启动服务


# Devop

## tsdb vs. sqldb
InfluxDB|	MySQL|	解释|
---|---|---|
Buckets|	Database|	数据桶-数据库，即存储数据的命名空间。
Measurement	|Table|	度量-表。
Point|	Record|	数据点-记录。
Field|	Field|	未设置索引的字段。
Tag |	Index|	设置了索引的字段。

## flask配置文件读取
- 配置文件config_file/config_object 总会被.flaskenv文件覆盖，.flaskenv
- pycharm 配置的env debug等优先级高与一切配置


## instance folder copy to /

instance为整个web服务的外部配置,应该被copy到操作系统的根目录