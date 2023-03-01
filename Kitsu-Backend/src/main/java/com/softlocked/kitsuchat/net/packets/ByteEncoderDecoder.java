package com.softlocked.kitsuchat.net.packets;

/**
 * The {@code ByteEncoderDecoder} class is used to encode and decode bytes.
 *
 * <p>
 * It supports multiple types such as integers, strings, booleans, etc.
 *
 */
public class ByteEncoderDecoder {
    /**
     * Encodes an integer into a byte array.
     * @param value The integer to encode
     * @return The encoded byte array
     */
    public static byte[] encodeInt(int value) {
        return new byte[] {
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value
        };
    }

    /**
     * Decodes an integer from a byte array.
     * @param bytes The byte array to decode
     * @return The decoded integer
     */
    public static int decodeInt(byte[] bytes) {
        return (bytes[0] << 24) + ((bytes[1] & 0xFF) << 16) + ((bytes[2] & 0xFF) << 8) + (bytes[3] & 0xFF);
    }

    /**
     * Encodes a long into a byte array.
     * @param value The long to encode
     * @return The encoded byte array
     */
    public static byte[] encodeLong(long value) {
        return new byte[] {
                (byte) (value >> 56),
                (byte) (value >> 48),
                (byte) (value >> 40),
                (byte) (value >> 32),
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value
        };
    }

    /**
     * Decodes a long from a byte array.
     * @param bytes The byte array to decode
     * @return The decoded long
     */
    public static long decodeLong(byte[] bytes) {
        return ((long) bytes[0] << 56) + ((long) (bytes[1] & 0xFF) << 48) + ((long) (bytes[2] & 0xFF) << 40) + ((long) (bytes[3] & 0xFF) << 32) + ((long) (bytes[4] & 0xFF) << 24) + ((bytes[5] & 0xFF) << 16) + ((bytes[6] & 0xFF) << 8) + (bytes[7] & 0xFF);
    }

    /**
     * Encodes a short into a byte array.
     * @param value The short to encode
     * @return The encoded byte array
     */
    public static byte[] encodeShort(short value) {
        return new byte[] {
                (byte) (value >> 8),
                (byte) value
        };
    }

    /**
     * Decodes a short from a byte array.
     * @param bytes The byte array to decode
     * @return The decoded short
     */
    public static short decodeShort(byte[] bytes) {
        return (short) ((bytes[0] << 8) + (bytes[1] & 0xFF));
    }

    /**
     * Encodes a string into a byte array. The string is encoded using UTF-8.
     * @param value The string to encode
     * @return The encoded byte array
     */
    public static byte[] encodeString(String value) {
        return value.getBytes();
    }

    /**
     * Decodes a string from a byte array. The string is decoded using UTF-8.
     * @param bytes The byte array to decode
     * @return The decoded string
     */
    public static String decodeString(byte[] bytes) {
        return new String(bytes);
    }

    /**
     * Encodes a boolean into a byte array.
     * @param value The boolean to encode
     * @return The encoded byte array
     */
    public static byte[] encodeBoolean(boolean value) {
        return new byte[] {
                (byte) (value ? 1 : 0)
        };
    }

    /**
     * Decodes a boolean from a byte array.
     * @param bytes The byte array to decode
     * @return The decoded boolean
     */
    public static boolean decodeBoolean(byte[] bytes) {
        return bytes[0] == 1;
    }
}
