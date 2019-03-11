#!/bin/bash
mvn clean install
chmod 777 target/rankr*
docker rm $(docker ps -a -q)
sudo docker stop rankr_container
sudo docker rm rankr_container
sudo docker image rm rankr_container --force

sudo docker image build . -t rankr_container
sudo docker run -p 4000:8080 -p 5000:5432 -d rankr_container -name rankr_container