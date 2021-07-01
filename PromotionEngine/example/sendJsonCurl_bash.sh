#!/bin/bash

curl -v -X POST -H "Content-Type: application/json" -H "Accept: application/json" -d @$1 http://localhost:9091/order
