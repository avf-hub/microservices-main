#!/usr/bin/env bash

docker context use default

./gradlew \
  :mouse-store-catalog:jibDockerBuild \
  :mouse-store-reviews:jibDockerBuild
