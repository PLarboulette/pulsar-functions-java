package plarboulette;

import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncFunction implements Function<String, CompletableFuture<String>> {

    @Override
    public CompletableFuture<String> process(String s, Context context) throws Exception {
        return fakeAsyncFunction();
    }

    public CompletableFuture<String> fakeAsyncFunction () throws IOException, ExecutionException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(
                URI.create("https://jsonplaceholder.typicode.com/todos/1"))
                .GET()
                .build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        CompletableFuture<String> str = response.thenApplyAsync(
                HttpResponse::body
        );

        return str;
    }
}
