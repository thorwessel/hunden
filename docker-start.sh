#!/bin/sh

# Create a new image version with latest code changes.
docker build . --tag hunden

# Build the code.
docker run \
  --name pleo-anteus-app \
  --publish 7000:7000 \
  --rm \
  --interactive \
  --tty \
  --volume pleo-antaeus-build-cache:/home/pleo/.gradle \
  hunden
