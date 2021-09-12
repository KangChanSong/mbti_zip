#!/bin/bash

echo "============== 백엔드 배포 시작 =============="

ZIP_REPOSITORY=/home/ec2-user/zips/back

REPOSITORY=/home/ec2-user/app/back
PROJECT_NAME=mbti_zip

echo "> Build 파일 복사"

cp $ZIP_REPOSITORY/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fl mbti_zip | grep jar | awk '{print $1}')

#CURRENT_PID=$(pgrep -f jar | awk '{print $1}')

echo "> 현재 구동중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID"]; then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar |tail -n 1)

echo "> Jar Name : $JAR_NAME"

echo "> $JAR_NAME에 실행 권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

nohup java -jar \
  -Dspring.config.location=classpath:/application-real.properties,/home/ec2-user/app/application-real-db.properties \
  -Dspring.profiles.active=real \
  $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &