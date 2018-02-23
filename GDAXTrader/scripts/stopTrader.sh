#!/bin/sh

jarPath="/home/pi/GDAXTrader/GDAXTrader.jar"

kill $(ps aux | grep $jarPath | awk '{print $2}')