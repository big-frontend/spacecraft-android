package retrofit2;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import retrofit2.callFactory.UrlConnectionCallFactory;
import retrofit2.helpers.ToStringConverterFactory;
import retrofit2.http.*;

import static java.util.concurrent.TimeUnit.SECONDS;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class UrlConnectionCallFactoryTest {
    @Rule
    public final MockWebServer server = new MockWebServer();

    interface Service {
        @GET("/")
        Call<String> getString();

        @GET("/")
        Call<ResponseBody> getBody();

        @GET("/")
        @Streaming
        Call<ResponseBody> getStreamingBody();

        @POST("/")
        Call<String> postString(@Body String body);

        @POST("/{a}")
        Call<String> postRequestBody(@Path("a") Object a);
    }
    Service serviceApi;
    @Before
    public void setUp(){
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(server.url("/"))
                        .addConverterFactory(new ToStringConverterFactory())
                        .callFactory(UrlConnectionCallFactory.create())
                        .build();
        serviceApi= retrofit.create(Service.class);

    }
    @Test
    public void http200Sync() throws IOException {

        server.enqueue(new MockResponse().setBody("Hi"));

        Response<String> response = serviceApi.getString().execute();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.body()).isEqualTo("Hi");
    }

    @Test
    public void testPostStringSync() throws IOException {
        int nums = 14;
        new Thread(() -> {
            try {
                for (int i =0;i<nums;++i){
                    System.out.println("[ "+i+" server request body]:" + server.takeRequest().getBody().readByteString().toString());
                    server.enqueue(new MockResponse().setBody(i+" ok post "));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        try {

            for (int i =0;i<nums;++i){
                Call<String> call = serviceApi.postString(i+" Hi testPostString ");
                Response<String> respo =call.execute();
                System.out.println("[ "+i+" client response body]:" + respo.body());
                System.out.println("=================================================");
            }

        } catch (UnsupportedOperationException ignored) {
            System.out.println("testPostString error");
        }
    }
    @Test
    public void testPostStringASync() throws IOException {
        int nums = 14;
        new Thread(() -> {
            try {
                for (int i =0;i<nums;++i){
                    System.out.println("[ "+i+" server request body]:" + server.takeRequest().getBody().readByteString().toString());
                    server.enqueue(new MockResponse().setBody(i+" ok post "));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        try {
            boolean iscancel=true;
            for (int i =0;i<nums;++i){
                Call<String> call = serviceApi.postString(i+" Hi testPostString ");
                Response<String> respo =call.execute();
                if (iscancel){
                    iscancel=false;
                    Thread.sleep(10);
                    call.cancel();

                }

                System.out.println("[ "+i+" client response body]:" + respo.body());
                System.out.println("=================================================");
            }

        } catch (UnsupportedOperationException ignored) {
            System.out.println("testPostString error");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void cancelBeforeExecute() {
        Call<String> call = serviceApi.getString();

        call.cancel();
        assertThat(call.isCanceled()).isTrue();

        try {
            call.execute();
            fail();
        } catch (IOException e) {
            assertThat(e).hasMessage("Canceled");
        }
    }

    @Test
    public void cancelBeforeEnqueue() throws Exception {

        Call<String> call = serviceApi.getString();

        call.cancel();
        assertThat(call.isCanceled()).isTrue();

        final AtomicReference<Throwable> failureRef = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        call.enqueue(
                new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        throw new AssertionError();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        failureRef.set(t);
                        latch.countDown();
                    }
                });
        assertTrue(latch.await(10, SECONDS));
        assertThat(failureRef.get()).hasMessage("Canceled");
    }
}
