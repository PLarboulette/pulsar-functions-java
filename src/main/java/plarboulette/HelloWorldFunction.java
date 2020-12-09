package plarboulette;

import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;

public class HelloWorldFunction implements Function<String, String> {

    @Override
    public String process(String s, Context context) throws Exception {
        String[] splitInput = s.split(" ");
        String myFinalString;
        if (splitInput.length == 2) {
            myFinalString =  "Goodbye " + splitInput[1];
        } else {
            myFinalString = "I don't know your name";
        }
        return myFinalString;
    }
}
