package com.jamesfchen.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: jamesfchen
 * @since: Nov/19/2020  Thu
 */
class Server {
    public static class EchoServer extends Thread {

        private DatagramSocket socket;
        private boolean running;
        private byte[] buf = new byte[256];

        public void run() {
            try {
                socket = new DatagramSocket(4445);
                running = true;

                while (running) {
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    InetAddress address = packet.getAddress();
                    int port = packet.getPort();
                    String received = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("server received:"+port + "" +received);

                    packet = new DatagramPacket(buf, buf.length, address, port);
                    if (received.equals("end")) {
                        running = false;
                        continue;
                    }
                    socket.send(packet);
                    System.out.println("server send:"+port);
                }
                socket.close();
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new EchoServer().start();
    }
}
