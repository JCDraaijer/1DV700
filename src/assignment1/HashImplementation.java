package assignment1;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class HashImplementation {
    public static void main(String[] args) {
        ArrayList<Integer> currentHashes = new ArrayList<>();
        int bytes = 0x00000000;
        ByteBuffer b1 = ByteBuffer.allocate(4);
        ByteBuffer b2 = ByteBuffer.allocate(4);
        b1.putInt(256);
        b2.putInt(32);
        int collisionFound = 0;
        do {
            ByteBuffer b = ByteBuffer.allocate(4);
            int hash = hash(b.putInt(bytes).array());
            if (currentHashes.contains(hash)) {
                System.out.println("Found collision! Ints: " + bytes + " and " + currentHashes.indexOf(hash));
                collisionFound++;
            }
            currentHashes.add(hash);
        } while (bytes++ < Integer.MAX_VALUE && collisionFound < 100);
    }

    private static int hash(byte[] msg) {
        int hash = 0xDECAFBAD;
        for (byte aMsg : msg) {
            hash = ((hash << 5) ^ (hash >>> 27)) ^ aMsg;
        }
        return hash & 0x7FFFFFFF;
    }

}
