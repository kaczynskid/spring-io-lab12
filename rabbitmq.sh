#!/usr/bin/env bash
docker run -d --name sprio-rabbit --hostname sprio-rabbit -p 5672:5672 -d rabbitmq:3
docker run -d --name sprio-rabbit-mgmt -p 15672:15672 rabbitmq:3-management
