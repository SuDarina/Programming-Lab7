package CollectionMannage;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.RecursiveAction;

public class ReadClient extends RecursiveAction {
    SocketChannel channel;
    ByteBuffer bb;

    public ReadClient(SocketChannel channel, ByteBuffer bb){
        this.channel = channel;
        this.bb = bb;
    }

    @Override
    protected void compute() {
        try {
            channel.read(bb);
            bb.flip();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
//    SocketChannel channel = (SocketChannel) key.channel();
//    ByteBuffer buffer = (ByteBuffer) key.attachment();
//        channel.read(buffer);
//                buffer.flip();
//                System.out.println("получено: "+ new String(buffer.array(), buffer.position(), buffer.remaining()));
//                byte[] bytes = buffer.array();
//                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
//                ois = new ObjectInputStream(bais);
//                comands = (Comands) ois.readObject();;
//                bais.close();
//                ois.close();
