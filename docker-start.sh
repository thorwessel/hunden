#!/bin/sh

# Create a new image version with latest code changes.
docker build . --tag eu.gcr.io/sage-groove-246108/hunden

# Push to gcloud.
docker push eu.gcr.io/sage-groove-246108/hunden:latest
