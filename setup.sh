#!/bin/sh

#set up a brand new ruby on rails env

path='/home/ubuntu/health'
ip=$(ifconfig eth0 | grep 'inet addr:' | cut -d: -f2 | awk '{ print $1}')
port=80

sudo apt-get update
sudo apt-get dist-upgrade -y
sudo apt-get install -y ruby1.9.1 ruby1.9.1-dev nodejs
sudo gem install rails
sudo gem install rdoc-data
sudo rdoc-data --install
sudo service apache2 stop
sudo aptitude purge apache2
mkdir -p $path
cd $path
rails new $path 
rails server -b $ip -p $port
