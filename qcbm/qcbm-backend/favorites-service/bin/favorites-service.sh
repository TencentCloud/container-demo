#!/bin/bash

#======================================================================
# 项目启动 shell 脚本
# author: willdomequer
# date: 2021-03-16
#======================================================================
APP_NAME=favorites-service

function startup() {

    APP_JAR="${APP_NAME}.jar"

    # 进入bin目录
    cd `dirname $0`
    cd ..
    # 项目根目录绝对路径
    BASE_PATH=`pwd`
    CONFIG_DIR=${BASE_PATH}"/config/"

    echo "BASE_PATH: ${BASE_PATH}"
    echo "CONFIG_DIR: ${CONFIG_DIR}"

    TRACE_OPT=""
    if [[ $1 == '-t' ]]; then
      TRACE_OPT="-javaagent:${BASE_PATH}/skywalking/skywalking-agent.jar"
    fi

    OPTIONS="-Dspring.config.location=${CONFIG_DIR} -Dspring.profiles.active=prod ${TRACE_OPT}"

    echo
    echo "start command:"
    echo "java ${OPTIONS} -jar ${BASE_PATH}/boot/${APP_JAR}"
    echo

    java ${OPTIONS} -jar ${BASE_PATH}/boot/${APP_JAR}
}

case "$1" in
    "start")
        startup $2
        ;;
    "stop")
        PID=`pgrep -u root -f ${APP_NAME}`
        echo "shutdown ${APP_NAME} pid = ${PID}"
        echo
        kill -9 ${PID}
        ;;
esac