package com.jamesfchen.network.slack;

import java.io.IOException;
import java.io.InterruptedIOException;

import okhttp3.HttpUrl;
import okio.Timeout;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Jun/27/2019  Thu
 */
public final class SlackClient {

    public static void main(String[] args) throws Exception {
        String clientId = "596481655859.680130574832";
        String clientSecret = "1bfef302f6d339529c5f629ba3bd6bac";
        int port = 53203;
        SlackApi slackApi = new SlackApi(clientId, clientSecret, port);
        SlackClient slackClient = new SlackClient(slackApi);
        String scopes = "channels:history channels:read channels:write chat:write:bot chat:write:user "
                + "dnd:read dnd:write emoji:read files:read files:write:user groups:history groups:read "
                + "groups:write im:history im:read im:write mpim:history mpim:read mpim:write pins:read "
                + "pins:write reactions:read reactions:write search:read stars:read stars:write team:read "
                + "usergroups:read usergroups:write users:read users:write bot";
        slackClient.requestOAuthSession(scopes, null);
        slackClient.awaitAccessToken(Timeout.NONE);
        slackClient.startRtm();
    }

    SlackApi slackApi;

    OAuthSession session;
    OAuthSessionFactory sessionFactory;

    public SlackClient(SlackApi slackApi) {
        this.slackApi = slackApi;
    }

    private void startRtm() throws IOException {
        String botAccessToken;
        synchronized (this) {
            botAccessToken = session.bot.bot_access_token;
        }
        RtmSession rtmSession = new RtmSession(slackApi);
        rtmSession.open(botAccessToken);
    }

    private synchronized void awaitAccessToken(Timeout timeout) throws InterruptedIOException {
        while (session == null) {
            timeout.waitUntilNotified(this);
        }
    }


    private void requestOAuthSession(String scopes, String team) throws Exception {
        if (sessionFactory == null) {
            sessionFactory = new OAuthSessionFactory(slackApi);
            sessionFactory.start();
        }
        HttpUrl authorizeUrl = sessionFactory.newAuthorizeUrl(scopes, team, new OAuthSessionFactory.Listener() {
            @Override
            public void sessionGranted(OAuthSession session) {
                System.out.printf("session granted: %s\n", session);
                initOauthSession(session);

            }


        });
        System.out.printf("open this URL in a browser: %s\n", authorizeUrl);
    }

    private synchronized void initOauthSession(OAuthSession session) {
        this.session = session;
        this.notify();

    }
}
