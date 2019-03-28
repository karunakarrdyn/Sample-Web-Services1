#!/bin/sh
id=$(ps -eaf | grep tomcat | grep -v grep | grep -v kill | head -n 1 | awk '{print $2}')
if [ -n "${id}" ]; then
    kill $id
fi

