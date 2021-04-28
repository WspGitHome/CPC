
#构建镜像
docker build -t envoy:v1 .
#查看镜像
docker images
#删除镜像
docker rmi -f 镜像ID

#运行容器
 docker run -it --network host  envoy:v1
 docker run -d -p 9901:9901 -p 9902:9902  envoy:v1
#查看容器
 docker ps
#停止容器
docker stop 容器ID

#容器交互
docker exec -it 容器ID /bin/bash



#docker 启动服务（记得版本号对应上）
docker pull docker.wanfangdata.com.cn:8443/localchroniclegrpcsearch:版本号
#测试环境：
    #启动envoy
    docker run -d --add-host localchroniclegrpcsearch:10.10.184.72 -p 9901:9901 -p 9902:9902 docker.wanfangdata.com.cn:8443/localchroniclegrpcsearch-envoy:v1
    #启动server
    docker run -d --add-host my.test.wanfangdata.com.cn:10.10.184.76 -p 9903:9903  -e SPRING_PROFILES_ACTIVE="dev" docker.wanfangdata.com.cn:8443/localchroniclegrpcsearch:版本号
#生产环境：
    #启动envoy
    docker run -d --add-host localchroniclegrpcsearch:10.1.1.9 -p 9901:9901 -p 9902:9902 docker.wanfangdata.com.cn:8443/localchroniclegrpcsearch-envoy:v1
    #启动server
     docker run -d  --add-host my.wanfangdata.com.cn:10.1.1.2 --add-host solr.solr8:10.1.1.4 -p 9993:9903  --add-host mysqlserver:10.1.1.9 -e MYSQL_USERNAME="fz" -e MYSQL_PASSWORD="Fz@2017@wf" -v /home/localchroniclegrpcsearch/logs:/app/logs registry.cn-beijing.aliyuncs.com/wfapp/localchroniclegrpcsearch:1.2020.23-SNAPSHOT