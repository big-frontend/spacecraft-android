Super Retrofit
========
This project fork from [Retrofit](https://github.com/square/retrofit),I refactor project to make custom protocol( base TCP) 、volley、UrlConnection support restful api

You can use some call factorys(UrlConnectionCallFactory 、OkHttpCallFactory )) to create call , and add you custom protocol to this project.
```java
 Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(server.url("/"))
                        .addConverterFactory(new ToStringConverterFactory())
                        .callFactory(UrlConnectionCallFactory.create())
                        .build();
        Service example = retrofit.create(Service.class);

        server.enqueue(new MockResponse().setBody("Hi"));

        Response<String> response = example.getString().execute();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.body()).isEqualTo("Hi");
``` 

```java
Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(server.url("/"))
                        .addConverterFactory(new ToStringConverterFactory())
                        .callFactory(OkHttpCallFactory.create())
                        .build();
        Service example = retrofit.create(Service.class);

        server.enqueue(new MockResponse().setBody("Hi"));

        Response<String> response = example.getString().execute();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.body()).isEqualTo("Hi");
```

```java
OkHttpClient okHttpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .pingInterval(1, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .protocols(listOf(Protocol.HTTP_2))
                .build()
Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(server.url("/"))
                        .addConverterFactory(new ToStringConverterFactory())
                        .callFactory(OkHttpCallFactory.create(okHttpClient))
                        .build();
        Service example = retrofit.create(Service.class);

        server.enqueue(new MockResponse().setBody("Hi"));

        Response<String> response = example.getString().execute();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.body()).isEqualTo("Hi");
```
