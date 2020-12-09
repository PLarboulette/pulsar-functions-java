#!/bin/bash

"$PULSAR_HOME"/bin/pulsar-client produce persistent://sample/standalone/ns1/async-input \
  --num-produce 1 \
  --messages "Call Barney"
