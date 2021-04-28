
#docker 启动服务（记得版本号对应上）
docker pull registry.cn-beijing.aliyuncs.com/wfapp/localchroniclegrpcsearchadmin:1.2020.15
#测试环境：
    #启动server
    docker run -d -p 8880:8880 -v /home/cpc-deploy:/home/cpc-deploy  registry.cn-beijing.aliyuncs.com/wfapp/localchroniclegrpcsearchadmin:1.2020.15
#生产环境：
    #启动server
    docker run -d --add-host solr.solr8:10.1.1.4 --add-host access.limit.redis.host:10.1.3.201 --add-host mysqlserver:10.1.1.9 -p 8880:8880   -v /home/cpc-deploy:/home/cpc-deploy  -e MYSQL_USERNAME="fz" -e MYSQL_PASSWORD="Fz@2017@wf"  -e SPRING_PROFILES_ACTIVE="prd" registry.cn-beijing.aliyuncs.com/wfapp/localchroniclegrpcsearchadmin:1.2020.15