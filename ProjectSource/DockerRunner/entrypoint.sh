#!/bin/bash
# dockerd --host=tcp://0.0.0.0:2376 --experimental
# 
# echo '------------------------------------'
# 
# tail -f /dev/null
echo '------------------------------------ ENTRY POINT.SH --------------------------'
ps aux
pkill -f "dockerd"
rm -f /var/run/docker.pid # to remveo prevos docker preoces from previous run
export DOCKER_HOST=tcp://0.0.0.0:2376
echo "start docker"
dockerd --host=tcp://0.0.0.0:2376 --experimental &
until docker info &>/dev/null; do
    echo "Waiting for Docker daemon to start..."
    sleep 10
done
docker pull nginx
docker pull filipredocode/redocode:inputoutput
docker pull filipredocode/redocode:CppRunner
docker pull hello-world
tail -f /dev/null