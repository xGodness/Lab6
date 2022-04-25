package lab6.client;

import lab6.IO.IOManager;
import lab6.Serializer;
import lab6.request_response.*;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.time.Duration;
import java.time.Instant;

public class ClientService {
    public static final int PORT = 5432;

    private static ByteBuffer buffer = ByteBuffer.allocate(4096);
    private static byte[] bytes = new byte[4096];
    private static DatagramChannel datagramChannel = null;

    private static InetAddress serverAddress;
    private static InetSocketAddress serverSocketAddress;

    private static IOManager ioManager = new IOManager();
    private static ConsoleManager consoleManager = new ConsoleManager(ioManager);

    public static void main() {
        try {
            startClient();
            establishConnection();
            bytes = extractBuffer(buffer);
            Response response = (Response) Serializer.deserialize(bytes);
            if (response.getType() == ResponseType.SUCCESS)  {
                ioManager.printlnSuccess(response.getExitMessage());
            } else {
                ioManager.printlnErr(response.getExitMessage());
                System.exit(0);
            }
            boolean fileWasFetched = false;
            while (!fileWasFetched) {
                fileWasFetched = fileFetch();
            }

            while (true) {
                Request request = consoleManager.execute();
                if (request != null) {
                    sendRequest(request);
                    receiveResponse();
                    response = (Response) Serializer.deserialize(buffer.array());
                    ResponseType responseType = response.getType();
                    if (responseType == ResponseType.ERROR) {
                        ioManager.printlnErr(response.getExitMessage());
                    } else if (response.getRuntimeMessages() != null) {
                        for (String msg : response.getRuntimeMessages()) {
                            ioManager.printlnOut(msg);
                        }
                        ioManager.printlnOut(response.getExitMessage());
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static boolean fileFetch() throws IOException, ClassNotFoundException {
        String fileName = null;
        while (fileName == null) {
            fileName = ioManager.getNextInput("Specify file name: ");
            if (!ioManager.isStringValid(fileName)) {
                ioManager.printlnErr("File name is invalid");
                fileName = null;
            }
        }
        Request fileRequest = new Request(RequestType.LOAD, new String[] {fileName});
        sendRequest(fileRequest);
        receiveResponse();
        Response response = (Response) Serializer.deserialize(extractBuffer(buffer));
        if (response.getType() == ResponseType.SUCCESS) {
            ioManager.printlnSuccess(response.getExitMessage());
            return true;
        }
        ioManager.printlnErr(response.getExitMessage());
        if (response.getExceptionType() == ExceptionType.FILE_NOT_FOUND) {
            String answer = ioManager.getNextInput("Would you like to create new blank file \"" + fileName + "\"? (y/n): ");
            if (answer.equalsIgnoreCase("y")) {
                fileRequest = new Request(RequestType.CREATE, new String[] {fileName});
                sendRequest(fileRequest);
                receiveResponse();
                response = (Response) Serializer.deserialize(extractBuffer(buffer));
                if (response.getType() == ResponseType.SUCCESS) {
                    ioManager.printlnSuccess(response.getExitMessage());
                    return true;
                }
                ioManager.printlnErr(response.getExitMessage());
            }
        }
        return false;
    }

    /*______________________________________________________________________________________________________________*/
    /*                                     Initialize client service methods                                        */

    private static void startClient() throws IOException {
        datagramChannel = DatagramChannel.open();
        datagramChannel.bind(null);
        datagramChannel.configureBlocking(false);
    }

    private static void establishConnection() throws IOException {
        ioManager.printlnStatus("Connecting to the server...");
        if (datagramChannel == null) throw new SocketException("Datagram channel has not been initialised yet");
        serverAddress = InetAddress.getLocalHost();
        serverSocketAddress = new InetSocketAddress(serverAddress, PORT);
        datagramChannel.send(buffer, serverSocketAddress);
        receiveResponse();
    }

    /*______________________________________________________________________________________________________________*/


    private static void receiveResponse() throws IOException {
        Instant awaitTime = Instant.now();
        buffer.clear();
        while (buffer.position() == 0 && Duration.between(awaitTime, Instant.now()).getSeconds() < 5) {
            datagramChannel.receive(buffer);
        }
        if (buffer.position() == 0) throw new IOException("Unable to reach server");
    }

    private static void sendRequest(Request request) throws IOException {
        System.out.println(request);
        datagramChannel.send(ByteBuffer.wrap((Serializer.serialize(request))), serverSocketAddress);
    }

    private static byte[] extractBuffer(ByteBuffer buffer) {
        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        buffer.flip();
        return bytes;
    }



}
