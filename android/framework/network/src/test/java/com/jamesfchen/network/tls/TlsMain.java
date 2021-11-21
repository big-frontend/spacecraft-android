package com.jamesfchen.network.tls;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okhttp3.tls.HandshakeCertificates;
import okhttp3.tls.HeldCertificate;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Aug/10/2019  Sat
 */
public class TlsMain {

    public static void main(String[] args) {
        //case1
//        String localhost = null;
//        try {
//            localhost = InetAddress.getByName("localhost").getCanonicalHostName();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        HeldCertificate localhostCertificate = new HeldCertificate.Builder()
//                .addSubjectAlternativeName(localhost)
//                .build();
//
//        HandshakeCertificates serverCertificates = new HandshakeCertificates.Builder()
//                .heldCertificate(localhostCertificate)
//                .build();
//
//        MockWebServer server = new MockWebServer();
//        server.useHttps(serverCertificates.sslSocketFactory(), false);
//
//
//        HandshakeCertificates clientCertificates = new HandshakeCertificates.Builder()
//                .addTrustedCertificate(localhostCertificate.certificate())
//                .build();
//        OkHttpClient client = new OkHttpClient.Builder()
//                .sslSocketFactory(clientCertificates.sslSocketFactory(), clientCertificates.trustManager())
//                .build();

        //The above example uses a self-signed certificate. This is convenient for testing but not
        //representative of real-world HTTPS deployment

        //case2
        serverCertificateAuthenticator();
        clientCertificateAuthenticator();

    }

    private static void clientCertificateAuthenticator() {
        MockWebServer server = new MockWebServer();
// Create the root for client and server to trust. We could also use different roots for each!
        HeldCertificate rootCertificate = new HeldCertificate.Builder()
                .certificateAuthority(0)
                .build();

// Create a server certificate and a server that uses it.
        HeldCertificate serverCertificate = new HeldCertificate.Builder()
                .commonName("ingen")
                .addSubjectAlternativeName(server.getHostName())
                .signedBy(rootCertificate)
                .build();
        HandshakeCertificates serverCertificates = new HandshakeCertificates.Builder()
                .addTrustedCertificate(rootCertificate.certificate())
                .heldCertificate(serverCertificate)
                .build();
        server.useHttps(serverCertificates.sslSocketFactory(), false);
        server.requestClientAuth();
        server.enqueue(new MockResponse());

// Create a client certificate and a client that uses it.
        HeldCertificate clientCertificate = new HeldCertificate.Builder()
                .commonName("ianmalcolm")
                .signedBy(rootCertificate)
                .build();
        HandshakeCertificates clientCertificates = new HandshakeCertificates.Builder()
                .addTrustedCertificate(rootCertificate.certificate())
                .heldCertificate(clientCertificate)
//                .addPlatformTrustedCertificates()
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(clientCertificates.sslSocketFactory(), clientCertificates.trustManager())
                .build();

// Connect 'em all together. Certificates are exchanged in the handshake.
        Call call = client.newCall(new Request.Builder()
                .url(server.url("/"))
                .build());
        Response response = null;
        try {
            response = call.execute();
            System.out.println(response.handshake().peerPrincipal());
            RecordedRequest recordedRequest = server.takeRequest();
            System.out.println(recordedRequest.getHandshake().peerPrincipal());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void serverCertificateAuthenticator() {
        HeldCertificate rootCertificate = new HeldCertificate.Builder()
                .certificateAuthority(1)
                .build();

        HeldCertificate intermediateCertificate = new HeldCertificate.Builder()
                .certificateAuthority(0)
                .signedBy(rootCertificate)
                .build();

        String localhost = null;
        try {
            localhost = InetAddress.getByName("localhost").getCanonicalHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        HeldCertificate serverCertificate = new HeldCertificate.Builder()
                .addSubjectAlternativeName(localhost)
                .signedBy(intermediateCertificate)
                .build();

        HandshakeCertificates serverHandshakeCertificates = new HandshakeCertificates.Builder()
                .heldCertificate(serverCertificate, intermediateCertificate.certificate())
                .build();
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.useHttps(serverHandshakeCertificates.sslSocketFactory(), false);

//        HandshakeCertificates clientCertificates = new HandshakeCertificates.Builder()
//                .addTrustedCertificate(rootCertificate.certificate())
//                .build();
//        OkHttpClient client = new OkHttpClient.Builder()
//                .sslSocketFactory(clientCertificates.sslSocketFactory(), clientCertificates.trustManager())
//                .authenticator()
//                .build();

    }
}
