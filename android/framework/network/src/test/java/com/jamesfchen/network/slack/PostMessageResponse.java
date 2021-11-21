package com.jamesfchen.network.slack;

import java.util.List;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Jun/30/2019  Sun
 */
public class PostMessageResponse {

    /**
     * ok : true
     * channel : C1H9RESGL
     * ts : 1503435956.000247
     * message : {"text":"Here's a message for you","username":"ecto1","bot_id":"B19LU7CSY","attachments":[{"text":"This is an attachment","id":1,"fallback":"This is an attachment's fallback"}],"type":"message","subtype":"bot_message","ts":"1503435956.000247"}
     */

    public boolean ok;
    public String channel;
    public String ts;
    public MessageEntity message;

    public static class MessageEntity {
        /**
         * text : Here's a message for you
         * username : ecto1
         * bot_id : B19LU7CSY
         * attachments : [{"text":"This is an attachment","id":1,"fallback":"This is an attachment's fallback"}]
         * type : message
         * subtype : bot_message
         * ts : 1503435956.000247
         */

        public String text;
        public String username;
        public String bot_id;
        public String type;
        public String subtype;
        public String ts;
        public List<AttachmentsEntity> attachments;

        public static class AttachmentsEntity {
            /**
             * text : This is an attachment
             * id : 1
             * fallback : This is an attachment's fallback
             */

            public String text;
            public int id;
            public String fallback;
        }
    }
}
