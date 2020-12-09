#!/bin/bash

"$PULSAR_HOME"/bin/pulsar-client consume persistent://sample/standalone/ns1/hello-output \
  --subscription-name my-subscription \
  --num-messages 0