#!/bin/bash

minikube delete --all
docker context use default
docker context rm minikube
