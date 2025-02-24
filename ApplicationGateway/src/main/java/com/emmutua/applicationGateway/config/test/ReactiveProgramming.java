package com.emmutua.applicationGateway.config.test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Learn more: <a href="https://projectreactor.io/docs/core/release/reference/gettingStarted.html">Project reactor documentation</a>
 */

public class ReactiveProgramming {
    public static void main(String[] args) {
        long start, end;
        // Measure time for parallel stream
        start = System.nanoTime();
        int parallelResult = parallelStream();
        end = System.nanoTime();
        System.out.println("Parallel Stream Result: " + parallelResult);
        System.out.println("Parallel Stream Execution Time: " + (end - start) + " ns");

    }

    //3268600
    public static int stream(){
        List<Integer> data  = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        return data.stream()
                .map(n -> n * 2)
                .reduce(0, (a, b) -> a + b);
    }

    public static int parallelStream(){
        List<Integer> data  = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        return data.parallelStream()
                .map(n -> n * 2)
                .reduce(0, (a, b) -> a + b);
    }

    public static void reactive(){
        Flux<String> ids = Flux.just("1", "2", "3");
        Flux<String> combinations = ids.flatMap(
                id -> {
                    Mono<String> nameTask = Mono.just(id);
                    Mono<String> associateMembers = Mono.just("Maths");

                    return nameTask.zipWith(associateMembers, (name, assMe) -> "Associated " + name + " with " + assMe);
                }
        );

        Mono<List<String>> result = combinations.collectList();

        result.subscribe(
                it -> it.forEach(System.out::println)
        );
    }


    public void createFluxFromIterable(){
        List<String> iterable  = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
        Flux<String> createdFlux = Flux.fromIterable(iterable);
        createdFlux.subscribe(
                valueEmitted -> System.out.println(valueEmitted)
        );

        createdFlux.subscribe(
                valueEmitted -> {
                    System.out.println(valueEmitted);
                },
                error -> {
                    System.out.println(error);
                }
        );

        //with error and completion

        Flux<Integer> integerFlux = Flux.range(1,10)
                        .map(item -> {
                            if(item <= 3) return item;
                            throw new RuntimeException("Go 2 4");
                        });
        integerFlux.subscribe(
                valueEmitted -> System.out.println(valueEmitted),
                error -> {
                    //do something
                },
                () -> {
                    System.out.println("Done");
                }
        );

        /**
         * All these lambda based variants have a disposable return type
         * Meaning that they can be cancelled by calling dispose() method
         */

    }

}

@FunctionalInterface
interface  ExampleFunctionalInt {
    void method();
}