# 1.指定基础镜像信息
FROM java:8
# 2.作者信息 作者名和邮箱
MAINTAINER xiaozhiwei xiao@ynu.icu

# 环境变量
ENV SERVICE_NAME service_ucenter
ENV PORT 8004
ENV VERSION 0.0.1-SNAPSHOT
# 3.添加指定文件到docker容器,第二个参数为添加后的新名字
COPY ./target/${SERVICE_NAME}-${VERSION}.jar service.jar
# 4.其他操作
RUN echo "${SERVICE_NAME} 完毕..."

# 5.声明要暴露的端口号,增强可读性
EXPOSE ${PORT}


# 7.执行docker run时运行的命令
CMD ["java","-Xms100m","-Xmx100m","-jar","service.jar","--spring.profiles.active=prod","--server.port=${PORT}"]