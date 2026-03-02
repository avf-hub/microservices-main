#!/bin/bash

docker context use default
docker context rm -f minikube
eval $(minikube docker-env --shell bash)
docker context create minikube

echo "☝ Docker context switched to minikube docker daemon, if you build your images now - "
echo "    those will be stored in kubernetes, not your local registry"
echo "☝ Do 'docker context use default' to get back to your default docker"
