#!/bin/bash

cd ..
mvn clean
mvn package
cp ./target/pulsar-functions-java-1.0-SNAPSHOT.jar "$PULSAR_HOME"/program.jar
"$PULSAR_HOME"/bin/pulsar-admin functions localrun \
  --jar program.jar \
  --className plarboulette.AsyncFunction \
  --inputs persistent://sample/standalone/ns1/async-input \
  --output persistent://sample/standalone/ns1/async-output \
  --name test