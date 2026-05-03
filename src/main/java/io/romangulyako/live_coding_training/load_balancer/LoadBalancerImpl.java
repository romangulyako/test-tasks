package io.romangulyako.live_coding_training.load_balancer;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class LoadBalancerImpl implements LoadBalancer {
    private final String[] servers;
    private final AtomicInteger index = new AtomicInteger(0);

    public LoadBalancerImpl(String[] servers) {
        if (servers == null || servers.length == 0) {
            throw new IllegalArgumentException("servers is can't be null or empty");
        }
        this.servers = Arrays.copyOf(servers, servers.length);
    }

    @Override
    public String getNext() {
        int idx = Math.floorMod(index.getAndIncrement(), servers.length);

        return servers[idx];
    }
}
