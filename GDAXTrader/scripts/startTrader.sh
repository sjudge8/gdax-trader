#!/bin/sh

dir="/home/pi/GDAXTrader/"
log4j2="log4j2.xml"
jar="GDAXTrader.jar"

java -jar -Dlog4j.configurationFile=$dir$log4j2 $dir$jar &

tail -f $dir/logs/GDAXTrader.log