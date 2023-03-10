package com.softlocked.kitsuchat.net.packets;

import java.util.*;

public class Packet {
    private final List<Byte> data;
    private final byte id;

    public Packet(byte id) {
        this.id = id;
        this.data = new ArrayList<>();
    }

    public Packet(byte id, byte[] data) {
        this.id = id;
        this.data = new ArrayList<>();
        for (byte b : data) {
            this.data.add(b);
        }
    }

    public Packet(byte id, List<Byte> data) {
        this.id = id;
        this.data = data;
    }

    public byte id() {
        return id;
    }

    public byte[] get() {
        List<Byte> list = new ArrayList<>();
        list.add(id);
        list.addAll(data);

        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            bytes[i] = list.get(i);
        }

        return bytes;
    }

    public void add(byte b) {
        data.add(b);
    }

    public void add(byte[] bytes) {
        for (byte b : bytes) {
            data.add(b);
        }
    }

    public void add(List<Byte> bytes) {
        data.addAll(bytes);
    }

    @Override
    public String toString() {
        return "Packet{" +
                "id=" + id +
                '}';
    }
}
