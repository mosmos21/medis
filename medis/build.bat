@echo off

cd /d %~dp0
mvn install -Dmaven.test.skip=true
