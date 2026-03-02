#!/usr/bin/env bash

docker context use minikube

./gradlew \
  :mouse-store-catalog:jibDockerBuild \
  :mouse-store-reviews:jibDockerBuild \
  --stacktrace
