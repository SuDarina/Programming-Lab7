package CollectionMannage;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class WriteClient extends RecursiveAction {
    SocketChannel channel;
    byte[] bytes;
    String comand1;
    String comand;

    public WriteClient(SocketChannel channel, byte[] bytes, String comand) {
        this.channel = channel;
        this.bytes = bytes;
        this.comand = comand;
    }

    @Override
    public void compute() {
        ByteBuffer writeBuf = ByteBuffer.allocate(4000);
        writeBuf.flip();
        writeBuf.clear();
        writeBuf.put((bytes));
        writeBuf.flip();
        try {
            channel.write(writeBuf);
            if (Arrays.equals(bytes, "[программа завершена]".getBytes())){
                channel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (writeBuf.hasRemaining()) {
            writeBuf.compact();
        } else {
            writeBuf.clear();
        }
        System.out.println(new String(writeBuf.array()));
        System.out.println("отправлено");

    }
}
