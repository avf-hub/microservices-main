#!/bin/bash

rootDir() {
  local unameOut="$(uname -s)"
  local machine="UNKNOWN:${unameOut}"

  case "${unameOut}" in
      Linux*)     machine=Linux;;
      Darwin*)    machine=Mac;;
      CYGWIN*)    machine=Cygwin;;
      MINGW*)     machine=MinGw;;
      MSYS_NT*)   machine=Git;;
  esac

  local root=$(dirname -- "$( readlink -f -- "$0"; )")

  if [ "$machine" = "Cygwin" ]; then
    root=$(cygpath -w "$root")
  fi

  echo "$root"
}

ROOT="$(rootDir)"

if [ -z "$ISTIO_HOME" ]; then
    echo "Please download Istio distribution for your system from https://github.com/istio/istio/releases/tag/1.19.3 (or newer)".
    echo "Unzip it to local directory and set ISTIO_HOME environment variable."
    exit 1
fi

PATH=$ISTIO_HOME/bin:$PATH

docker context use default
docker context rm -f minikube

# spin up kubernetes cluster
export MINIKUBE_IN_STYLE=true
minikube start --memory=12384 --cpus=4
minikube addons enable metrics-server

# install istio, apply sample services
istioctl install --set profile=demo -y
kubectl label namespace default istio-injection=enabled
kubectl apply -f "$ISTIO_HOME/samples/addons"

"$ROOT/setup-minikube-context.sh"

echo
echo "☝ run 'minikube dashboard' in separate terminal, check memory consumption in 'Nodes' tab"
echo "☝ run 'minikube tunnel' in separate terminal"
echo
echo "☝ To get rid of your k8s cluster run '$ROOT/cluster-destroy.sh'"

#export INGRESS_HOST=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.status.loadBalancer.ingress[0].ip}')
#export INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].port}')
