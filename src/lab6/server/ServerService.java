package lab6.server;

import lab6.Serializer;
import lab6.commands.Command;
import lab6.exceptions.collection_exceptions.CollectionException;
import lab6.exceptions.collection_exceptions.SaveCollectionException;
import lab6.exceptions.file_exceptions.CannotCreateFileException;
import lab6.exceptions.file_exceptions.FileAlreadyExistsException;
import lab6.exceptions.file_exceptions.FilePermissionException;
import lab6.exceptions.file_exceptions.InvalidFileNameException;
import lab6.request_response.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.LinkedList;

public class ServerService {
    public static final int PORT = 5432;

    private static ByteBuffer buffer = ByteBuffer.allocate(1024);
    private static byte[] bytes = new byte[1024];
    private static DatagramSocket socket = null;
    private static DatagramPacket packet = null;
    private static InetAddress clientAddress;
    private static int clientPort;
    private static InetSocketAddress clientSocketAddress;
    private static boolean connectionEstablished = false;

    private static Application application;

    public static void main() {
        while (true) {
            runServerMainLoop();
            resetConnection();
        }

    }

    private static void runServerMainLoop() {
        boolean isRunning = true;
        try {
            startServer();
            establishConnection();

            application = new Application();

            while (isRunning) {
                receiveRequest(); // todo: execute command
                Request request = (Request) Serializer.deserialize(extractBuffer(buffer));
                if (request.getType() == RequestType.LOAD) {
                    String fileName = (String) request.getArgs()[0];
                    loadFile(fileName);
                    continue;
                }
                if (request.getType() == RequestType.CREATE) {
                    String fileName = (String) request.getArgs()[0];
                    createFile(fileName);
                    continue;
                }
                if (request.getType() == RequestType.EXECUTE) {
                    Command command = request.getCommand();
                    Object[] cmdArgs = request.getArgs();
                    executeCommand(command, cmdArgs);
                    continue;
                }

                if (request.getType() == RequestType.EXIT) {
                    boolean savedSuccessfully = exit();
                    if (savedSuccessfully) {
                        isRunning = false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*______________________________________________________________________________________________________________*/
    /*                                     Client's requests methods                                                */

    private static boolean exit() throws IOException {
        try {
            LinkedList<String> runtimeMessages = application.saveCollection();
            String exitMessage = runtimeMessages.pollLast();
            Response response = new Response(ResponseType.SUCCESS, exitMessage, runtimeMessages);
            sendResponse(response);
            return true;
        } catch (FilePermissionException e) {
            Response response = new Response(ResponseType.ERROR, e.getMessage(), ExceptionType.PERMISSION_DENIED);
            sendResponse(response);
        } catch (InvalidFileNameException e) {
            Response response = new Response(ResponseType.ERROR, e.getMessage(), ExceptionType.INVALID_FILE_NAME);
            sendResponse(response);
        } catch (SaveCollectionException e) {
            Response response = new Response(ResponseType.ERROR, e.getMessage(), ExceptionType.EXECUTE_COMMAND_EXCEPTION);
            sendResponse(response);
        } catch (FileNotFoundException e) {
            Response response = new Response(ResponseType.ERROR, e.getMessage(), ExceptionType.FILE_NOT_FOUND);
            sendResponse(response);
        }
        return false;
    }

    private static void executeCommand(Command command, Object[] cmdArgs) throws IOException {
        try {
            String exitMessage = application.executeCommand(command, cmdArgs);
            Response response = new Response(ResponseType.SUCCESS, exitMessage);
            sendResponse(response);
        } catch (CollectionException e) {
            Response response = new Response(ResponseType.ERROR, e.getMessage(), ExceptionType.EXECUTE_COMMAND_EXCEPTION);
            sendResponse(response);
        }
    }

    private static void loadFile(String fileName) throws IOException {
        try {
            LinkedList<String> runtimeMessages = application.loadCollection(fileName);
            String exitMessage = runtimeMessages.pollLast();
            Response response = new Response(ResponseType.SUCCESS, exitMessage, runtimeMessages);
            sendResponse(response);
        } catch (FileNotFoundException e) {
            Response response = new Response(ResponseType.ERROR, e.getMessage(), ExceptionType.FILE_NOT_FOUND);
            sendResponse(response);
        } catch (FilePermissionException e) {
            Response response = new Response(ResponseType.ERROR, e.getMessage(), ExceptionType.PERMISSION_DENIED);
            sendResponse(response);
        } catch (InvalidFileNameException e) {
            Response response = new Response(ResponseType.ERROR, e.getMessage(), ExceptionType.INVALID_FILE_NAME);
            sendResponse(response);
        }
    }

    private static void createFile(String fileName) throws IOException {
        try {
            String exitMessage = application.createBlankFile(fileName);
            Response response = new Response(ResponseType.SUCCESS, exitMessage);
            sendResponse(response);
        } catch (FilePermissionException e) {
            Response response = new Response(ResponseType.ERROR, e.getMessage(), ExceptionType.PERMISSION_DENIED);
            sendResponse(response);
        } catch (FileAlreadyExistsException e) {
            Response response = new Response(ResponseType.ERROR, e.getMessage(), ExceptionType.FILE_ALREADY_EXISTS);
            sendResponse(response);
        } catch (CannotCreateFileException e) {
            Response response = new Response(ResponseType.ERROR, e.getMessage(), ExceptionType.CANNOT_CREATE_FILE);
            sendResponse(response);
        } catch (InvalidFileNameException e) {
            Response response = new Response(ResponseType.ERROR, e.getMessage(), ExceptionType.INVALID_FILE_NAME);
            sendResponse(response);
        }
    }

    /*______________________________________________________________________________________________________________*/


    /*______________________________________________________________________________________________________________*/
    /*                                     Initialize server service methods                                        */

    private static void startServer() throws IOException {
        socket = new DatagramSocket(PORT);
        packet = new DatagramPacket(bytes, bytes.length);
    }

    private static void establishConnection() throws IOException {
        if (socket == null || packet == null) throw new SocketException("Socket and/or packet has not been initialised yet");
        socket.receive(packet);
        clientAddress = packet.getAddress();
        clientPort = packet.getPort();
        clientSocketAddress = new InetSocketAddress(clientAddress, clientPort);
        sendResponse(new Response(ResponseType.SUCCESS, "Connection established"));
        connectionEstablished = true;
    }

    /*______________________________________________________________________________________________________________*/

    private static void receiveRequest() throws IOException {
        if (socket == null || packet == null) throw new SocketException("Socket and/or packet has not been initialised yet");
        if (!connectionEstablished) throw new SocketException("Connection has not been established yet");
        socket.receive(packet);
        buffer.clear();
        buffer.put(packet.getData());
    }

    private static void sendResponse(Response response) throws IOException {
        bytes = Serializer.serialize(response);
        packet = new DatagramPacket(bytes, bytes.length, clientSocketAddress);
        socket.send(packet);
    }

    private static byte[] extractBuffer(ByteBuffer buffer) {
        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        buffer.flip();
        return bytes;
    }

    private static void resetConnection() {
        buffer.clear();
        packet = null;
        clientAddress = null;
        clientPort = -1;
        clientSocketAddress = null;
        connectionEstablished = false;
    }

}