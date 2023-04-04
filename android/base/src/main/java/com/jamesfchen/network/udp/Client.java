package com.jamesfchen.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Nov/19/2020  Thu
 */
class Client {

    public static class EchoClient {
        private DatagramSocket socket;
        private InetAddress address;

        private byte[] buf;

        public String sendEcho(String msg) {
            try {
                socket = new DatagramSocket();
                address = InetAddress.getByName("localhost");
                buf = msg.getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
                socket.send(packet);
                System.out.println("client send:"+msg);
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("client receive: "+received);
                return received;
            } catch (SocketException | UnknownHostException e) {
                e.printStackTrace();
                return "";
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }

        public void close() {
            socket.close();
        }
    }

    public static void main(String[] args) {
        new EchoClient().sendEcho("cjf");
    }
}
