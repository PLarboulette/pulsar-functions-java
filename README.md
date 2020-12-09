# pulsar-functions 

## Tools 
I use the version 2.7.0 Pulsar project, you can download it [here](https://pulsar.apache.org/en/download/). 
I defined a `PULSAR_HOME` environment variable which will be used to launch command lines. 

You can run Pulsar in a standalone mode with 
``` 
$PULSAR_HOME/bin/pulsar standalone --advertised-address 127.0.0.1
```

You can also run it with Docker
``` 
docker run -it \
  -p 6650:6650 \
  -p 8080:8080 \
  --mount source=pulsardata,target=/pulsar/data \
  --mount source=pulsarconf,target=/pulsar/conf \
  apachepulsar/pulsar:2.7.0 \
  bin/pulsar standalone
```
I prefer the first option because I will use some other tools of the Pulsar distribution. 

## Function 

You can find the different Pulsar functions in the `src/main/java` folder. 

- The  `HelloWorldFunction` is an amazing function which say "Goodbye X" when we produce a "Hello X". 
- The `AsyncFunction` is a little function which call an external API to get random data. Here, I just want to test the possibility to get data of a service and to create an enriched topic. 


## How tou launch the function CountHelloFunction

WARNING : Go to pom.xml file and change the line to :` <mainClass>plarboulette.HelloWorldFunction</mainClass>`

- First we have to generate the JAR
- After that, we send the JAR to the Pulsar Admin functions tool. You can find these two first steps in the script `launch.sh` located in the `scripts` folder. 
- If all runs normally, the function wait for new input. 
- So, after that, we have two last things to do. Run a consumer that will read the messages "Goodbye X" and a producer that will push some "Hello X". You can do this with : 

For the consumer : (also available in a script `consumer.sh`)
``` 
$PULSAR_HOME/bin/pulsar-client consume persistent://sample/standalone/ns1/hello-output \
  --subscription-name my-subscription \
  --num-messages 0
```

Be careful of the name of the topic (ths `persistent://sample/standalone/ns1/hello-output`). It corresponds of the name of the topic which is specified in the script `launch.sh` (argument `output` )

For the producer : (also available in a script `producer.sh` )
``` 
$PULSAR_HOME/bin/pulsar-client produce persistent://sample/standalone/ns1/hello-input \
  --num-produce 1 \
  --messages "Hello Barney"
```
Be careful of the name of the topic (ths `persistent://sample/standalone/ns1/hello-input`). It corresponds of the name of the topic which is specified in the script `launch.sh` (argument `input` )

You will have in the Pulsar function terminal a return like this : 
``` 
----- got message -----
key:[null], properties:[__pfn_input_msg_id__=CLkBEAAgAA==, __pfn_input_topic__=persistent://sample/standalone/ns1/hello-input], content:Goodbye Barney
```

## How to launch the function AsyncFunction 

WARNING : Go to pom.xml file and change the line to :` <mainClass>plarboulette.AsyncFunction</mainClass>`

- Launch the script `launch-async.sh`
- Launch the script `consumer-async.sh`
- Launch the script `producer-async.sh`

FYI: I change just topic names, the className, and the event I send to the topic :) 

The consumer terminal wil print a message like this. (The API I use to create fake async HTTP requests send random data)

``` 
----- got message -----
key:[null], properties:[__pfn_input_msg_id__=CNoKEAAgAA==, __pfn_input_topic__=persistent://sample/standalone/ns1/async-input], content:{
  "userId": 1,
  "id": 1,
  "title": "delectus aut autem",
  "completed": false
}
```