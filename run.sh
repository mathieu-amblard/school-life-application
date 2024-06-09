#!/bin/sh
set -e

export JAVA_HOME="/usr/lib/jvm/mobi-azuljdk21"

startDb() {
  echo "Starting databases..."
  docker compose up --detach
  while ! pg_isready -U user -h localhost
  do
    echo "Waiting until database is ready"
    sleep 1
  done
  echo "-----------------------------------"
  echo "Databases successfully started"
  echo "-----------------------------------"
}

stopDb() {
  echo "Stopping databases..."
  docker compose down
  echo "-----------------------------------"
  echo "Databases successfully stopped"
  echo "-----------------------------------"
}

restartDb() {
  stopDb
  startDb
}

database() {
  restartDb
}

start() {
  echo "Starting service ${1}..."
  cd "${1}"
  mvn spring-boot:start
  while ! nc -z localhost "${2}";
  do
    echo "Waiting until ${1} is ready"
    sleep 1
  done
  cd ..
  echo "-----------------------------------"
  echo "${1} successfully started"
  echo "-----------------------------------"
}

stop() {
  echo "Starting service ${1}..."
  cd "${1}"
  mvn spring-boot:stop
  cd ..
  echo "-----------------------------------"
  echo "${1} successfully stopped"
  echo "-----------------------------------"
}

compile() {
  echo "Compiling service ${1}..."
  cd "${1}"
  mvn clean compile -DskipTests
  cd ..
  echo "-----------------------------------"
  echo "${1} successfully compiled"
  echo "-----------------------------------"
}

restart() {
  stop "$1"
  start "$1" "$2"
}

compileAndRestart() {
  compile "$1"
  restart "$1" "$2"
}

userManagement() {
  compileAndRestart user-management-service 8081
}

apiGateway() {
  compileAndRestart api-gateway 8080
}

student() {
  compileAndRestart student-service 8082
}

teacher() {
  compileAndRestart teacher-service 8083
}

workshop() {
  compileAndRestart workshop-service 8084
}

all() {
  database
  userManagement
  apiGateway
  student
  teacher
  workshop
}

$1