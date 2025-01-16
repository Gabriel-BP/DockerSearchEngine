package es.ulpgc;

import com.hazelcast.collection.IQueue;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class Main {
    private static final String PROGRESS_MAP_FILE = "progressMap.dat";

    public static void main(String[] args) {
        // Initialize Hazelcast
        Config config = new Config();
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.getNetworkConfig().getJoin().getTcpIpConfig()
                .setEnabled(true)
                .addMember("192.168.14.45:5701") // Replace with the actual IP addresses
                .addMember("192.168.14.16:5701")
                .addMember("192.168.14.15:5701");
        HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance(config);
        IQueue<Integer> taskQueue = hazelcast.getQueue("bookIdQueue");
        IMap<Integer, Boolean> progressMap = hazelcast.getMap("progressMap");

        // Initialize resources
        ResourceInitializer resourceInitializer = new ResourceInitializer(taskQueue, progressMap, PROGRESS_MAP_FILE);
        resourceInitializer.initialize();

        // Start crawling process
        CrawlerManager crawlerManager = new CrawlerManager(taskQueue, progressMap, PROGRESS_MAP_FILE);
        crawlerManager.startCrawling();

        // Shutdown Hazelcast
        hazelcast.shutdown();
    }
}
