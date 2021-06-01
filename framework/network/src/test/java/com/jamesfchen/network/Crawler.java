package com.jamesfchen.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Crawler {
    public static final int THREAD_COUNT = 20;
    public static final Long CACHE_BYTE_COUNT = 1024L * 1024L * 100L;
    private Set<HttpUrl> fetchedUrls = Collections.synchronizedSet(new LinkedHashSet<HttpUrl>());
    private LinkedBlockingQueue<HttpUrl> queue = new LinkedBlockingQueue<>();
    private ConcurrentHashMap<String, AtomicInteger> hostnames = new ConcurrentHashMap<>();

    public static final String FILE_PATH = "";
    private String mUrl = "https://jsoup.org/download";

    private static OkHttpClient sClient;
    static ExecutorService sExecutorService = Executors.newFixedThreadPool(THREAD_COUNT);


    public static void main(String[] args) {
        Cache cache = new Cache(new File(FILE_PATH), CACHE_BYTE_COUNT);
        sClient = new OkHttpClient.Builder()
//                .cache(cache)
                .build();
        Crawler crawler = new Crawler();
        crawler.testCrawler();
    }

    public void testCrawler() {
        System.out.println("testCrawler:" + sClient);
        queue.add(HttpUrl.parse(mUrl));
        for (int i = 0; i < THREAD_COUNT; i++) {
            sExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        HttpUrl httpUrl;
                        while (((httpUrl = queue.take()) != null)) {
                            if (!fetchedUrls.add(httpUrl)) continue;

                            Thread currentThread = Thread.currentThread();
                            String name = currentThread.getName();
                            currentThread.setName("Crawler " + httpUrl);
                            try {
                                fetch(httpUrl);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                currentThread.setName(name);
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
        sExecutorService.shutdown();
    }

    private void fetch(HttpUrl httpUrl) throws IOException {
        AtomicInteger hostnameCount = new AtomicInteger();
        AtomicInteger previous = hostnames.putIfAbsent(httpUrl.host(), hostnameCount);
        if (previous != null) hostnameCount = previous;
        if (hostnameCount.incrementAndGet() > 100) return;

        Request request = new Request.Builder()
                .url(httpUrl)
                .build();
        try (Response response = sClient.newCall(request).execute()) {
            String responseSource = response.networkResponse() != null
                    ? ("network: " + response.networkResponse().code() + " over " + response.protocol() + ")")
                    : "(cache)";

            int responseCode = response.code();
            System.out.printf("%03d: %s %s%n", responseCode, httpUrl, responseSource);
            String contentType = response.header("Content-Type");
            if (responseCode != 200 || contentType == null) return;
            MediaType mediaType = MediaType.parse(contentType);
            if (mediaType == null || !mediaType.subtype().equalsIgnoreCase("html")) return;
            Document document = Jsoup.parse(response.body().string(), httpUrl.toString());
            for (Element element : document.select("a[href]")) {
                String href = element.attr("href");
                HttpUrl link = response.request().url().resolve(href);
                if (link == null) continue;
                queue.add(link.newBuilder().fragment(null).build());

            }
        }

    }

}
