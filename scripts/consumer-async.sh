#!/bin/bash

"$PULSAR_HOME"/bin/pulsar-client consume persistent://sample/standalone/ns1/async-output \
  --subscription-name my-subscription-async \
  --num-messages 0