package com.jamesfchen.network.slack;

/**
 * Copyright ® $ 2019
 * All right reserved.
 *
 * @author: hawksjamesf
 * @email: hawksjamesf@gmail.com
 * @since: Jun/27/2019  Thu
 */
public final class OAuthSession {
    public final boolean ok;
    public final String access_token;
    public final String scope;
    public final String user_id;
    public final String team_name;
    public final String team_id;
    public  Bot bot;

    public OAuthSession(
            boolean ok, String accessToken, String scope, String userId, String teamName, String teamId) {
        this.ok = ok;
        this.access_token = accessToken;
        this.scope = scope;
        this.user_id = userId;
        this.team_name = teamName;
        this.team_id = teamId;
    }

    @Override public String toString() {
        return String.format("(ok=%s, access_token=%s, scope=%s, user_id=%s, team_name=%s, team_id=%s, bot=%s)",
                ok, access_token, scope, user_id, team_name, team_id,bot);
    }

    static class Bot {
        public String bot_user_id;
        public String bot_access_token;

        @Override
        public String toString() {
            return "Bot{" +
                    "bot_user_id='" + bot_user_id + '\'' +
                    ", bot_access_token='" + bot_access_token + '\'' +
                    '}';
        }
    }
}
