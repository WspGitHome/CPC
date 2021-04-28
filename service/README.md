
#docker 启动服务（记得版本号对应上）
docker pull registry.cn-beijing.aliyuncs.com/wfapp/localchroniclegrpcsearchservice:1.2020.15
#测试环境：
    #启动server
    docker run -d --add-host my.test.wanfangdata.com.cn:10.10.184.76 -p 8883:8883  -e SPRING_PROFILES_ACTIVE="dev" registry.cn-beijing.aliyuncs.com/wfapp/localchroniclegrpcsearchservice:1.2020.15
#生产环境：
    #启动server
    docker run -d  --add-host my.wanfangdata.com.cn:10.1.1.2 --add-host access.limit.redis.host:10.1.3.201 --add-host authquery:10.1.1.4  --add-host solr.solr8:10.1.1.4 --add-host mysqlserver:10.1.1.9 -p 8883:8883  -e MYSQL_USERNAME="fz" -e MYSQL_PASSWORD="Fz@2017@wf"  -v /home/localchroniclegrpcsearchservice/logs:/app/logs registry.cn-beijing.aliyuncs.com/wfapp/localchroniclegrpcsearchservice:1.2020.19
    
     