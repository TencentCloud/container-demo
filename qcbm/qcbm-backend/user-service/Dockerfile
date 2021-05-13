# 从 Java 8 映像开始构建镜像
FROM java:8-jre

# 参考 niginx Dockerfile https://github.com/nginxinc/docker-nginx/blob/master/Dockerfile-alpine.template

ARG APP_NAME=user-service
ARG APP_VERSION=1.0.0
ARG FULL_APP_NAME=${APP_NAME}-${APP_VERSION}

# 容器中的工作目录为 /app
WORKDIR /app

# 将本地打包出来的应用添加到镜像中
COPY ./target/${FULL_APP_NAME}.zip .

# 创建日志目录 logs，解压并删除原始文件和解压后的目录
RUN mkdir -p logs \
    && unzip ${FULL_APP_NAME}.zip \
    && mv ${FULL_APP_NAME}/** . \
    && rm -rf ${FULL_APP_NAME}*

ENTRYPOINT ["/app/bin/user-service.sh"]

CMD ["start", "-t"]

EXPOSE 20880
