package plarboulette;

import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;

public class AsyncFunction implements Function<String, String> {

    @Override
    public String process(String s, Context context) throws Exception {
       String toReturn;
        if (s.startsWith("Call")) {
            toReturn = fakeAsyncFunction();
        } else {
            toReturn =  "Ok, I wait";
        }
        return toReturn;
    }

    public String fakeAsyncFunction () throws IOException, ExecutionException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(
                URI.create("https://jsonplaceholder.typicode.com/todos/1"))
                .GET()
                .build();
        var responseFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        var response =  responseFuture.get(); //Oh we love Java :)
        return response.body();
    }
}
