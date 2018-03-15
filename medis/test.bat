@echo off

cd /d %~dp0

rd /s /q target
mkdir target\resources\sql
copy resources\block.json target\resources\
copy resources\sql\* target\resources\sql\

echo Build
mvn jacoco:prepare-agent test jacoco:report
