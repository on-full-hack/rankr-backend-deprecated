#!/bin/bash
#mvn clean install

#sudo docker stop rankr_container
#sudo docker rm rankr_container
#sudo docker image rm rankr_container --force

#sudo docker stop database
#sudo docker rm database
#sudo docker image rm database --force
cd ./Database/
sudo docker image build . -t rankr_database
sudo docker run -p 5000:5432 -d rankr_database -name rankr_database
#cd ..

#sudo docker image build . -t rankr_container
#sudo docker run -p 4000:8080 -p 5000:5432 -d rankr_container -name rankr_container