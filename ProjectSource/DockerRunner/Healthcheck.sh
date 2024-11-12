echo '------------------------------------'

pgrep -f docker > /dev/null || exit -1;
docker image inspect hello-world >/dev/null 2>&1 || exit -1;