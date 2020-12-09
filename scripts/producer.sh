#!/bin/bash

"$PULSAR_HOME"/bin/pulsar-client produce persistent://sample/standalone/ns1/hello-input \
  --num-produce 1 \
  --messages "Hello Barney"
