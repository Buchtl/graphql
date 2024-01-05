#!/bin/bash

FILE=$1
echo "File: $FILE"
curl \
-v \
-X POST \
-H "Content-Type: application/json" \
-d @$FILE \
http://localhost:8080/graphql
