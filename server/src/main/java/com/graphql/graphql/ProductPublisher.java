package com.graphql.graphql;

import org.springframework.stereotype.Component;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Component
public class ProductPublisher {
    private final Flux<Product> publisher;
    private FluxSink<Product> emitter;

    public ProductPublisher() {
        Flux<Product> flux = Flux.create(emitter -> {this.emitter=emitter;}, FluxSink.OverflowStrategy.BUFFER);
        ConnectableFlux<Product> connectableFlux = flux.share().publish();
        connectableFlux.connect();
        publisher = Flux.from(connectableFlux);
    }

    public void emit(Product product) {
        emitter.next(product);
    }

    public Flux<Product> getPublisher() {
        return publisher;
    }
}
