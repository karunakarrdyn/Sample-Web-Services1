#!/bin/sh
id=$(ps -eaf | grep $1 | grep -v grep | grep -v kill | awk '{print $2}')
if [ -n "${id}" ]; then
    echo $id
    kill -9 $id
fi

