package org.acme;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SampleService {
    private static final String[] names = {"James", "Deepak", "Daniel", "Shaaf", "Jeff", "Sally"};

    public List<Sample> get() {
        int count = new Random().nextInt(10);
        return IntStream.range(0, count)
            .mapToObj(idx -> Math.random() > 0.5)
            .map(valid -> new Sample(valid, names[(int)(Math.random() * names.length)], Math.random() + ""))
            .collect(Collectors.toList());
    }
}
