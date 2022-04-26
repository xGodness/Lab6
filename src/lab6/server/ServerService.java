package lab6.server;

import lab6.commands.Command;
import lab6.exceptions.collection_exceptions.CollectionException;
import lab6.exceptions.collection_exceptions.SaveCollectionException;
import lab6.exceptions.file_exceptions.CannotCreateFileException;
import lab6.exceptions.file_exceptions.FileAlreadyExistsException;
import lab6.exceptions.file_exceptions.FilePermissionException;
import lab6.exceptions.file_exceptions.InvalidFileNameException;
import lab6.request_response.*;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.LinkedList;

public class ServerService {
    public static final int PORT = 5432;
    private static final String duck =
            "\n\n\n\n" +
                    "                                    ██████        \n" +
                    "                                  ██      ██      \n" +
                    "                                ██          ██    \n" +
                    "                                ██      ██  ██    \n" +
                    "                                ██        ░░░░██  \n" +
                    "                                  ██      ████    \n" +
                    "                    ██              ██  ██        \n" +
                    "                  ██  ██        ████    ██        \n" +
                    "                  ██    ████████          ██      \n" +
                    "                  ██                        ██    \n" +
                    "                    ██                      ██    \n" +
                    "                    ██    ██      ████      ██    \n" +
                    "                     ██    ████████      ██       \n" +
                    "                      ██                  ██      \n" +
                    "                        ████          ████        \n" +
                    "                            ██████████            \n" +
                    "\n\n";
    private static final String please =
            "\n\n\n\n" +
                    "██████╗░███████╗░░░░░░░░░██████╗░███████╗███╗░░██╗████████╗██╗░░░░░███████╗\n" +
                    "██╔══██╗██╔════╝░░░░░░░░██╔════╝░██╔════╝████╗░██║╚══██╔══╝██║░░░░░██╔════╝\n" +
                    "██████╦╝█████╗░░░░░░░░░░██║░░██╗░█████╗░░██╔██╗██║░░░██║░░░██║░░░░░█████╗░░\n" +
                    "██╔══██╗██╔══╝░░░░░░░░░░██║░░╚██╗██╔══╝░░██║╚████║░░░██║░░░██║░░░░░██╔══╝░░\n" +
                    "██████╦╝███████╗░░░░░░░░╚██████╔╝███████╗██║░╚███║░░░██║░░░███████╗███████╗\n" +
                    "╚═════╝░╚══════╝░░░░░░░░░╚═════╝░╚══════╝╚═╝░░╚══╝░░░╚═╝░░░╚══════╝╚══════╝\n" +
                    "\n" +
                    "██████╗░██╗░░░░░███████╗░█████╗░░██████╗███████╗░░░░░░░░░\n" +
                    "██╔══██╗██║░░░░░██╔════╝██╔══██╗██╔════╝██╔════╝░░░░░░░░░\n" +
                    "██████╔╝██║░░░░░█████╗░░███████║╚█████╗░█████╗░░░░░░░░░░░\n" +
                    "██╔═══╝░██║░░░░░██╔══╝░░██╔══██║░╚═══██╗██╔══╝░░░░░░░░░░░\n" +
                    "██║░░░░░███████╗███████╗██║░░██║██████╔╝███████╗██╗██╗██╗\n" +
                    "╚═╝░░░░░╚══════╝╚══════╝╚═╝░░╚═╝╚═════╝░╚══════╝╚═╝╚═╝╚═╝" +
                    "\n\n\n\n";
    private static final String fatalError =
            "████████████████████████████████████████\n" +
                    "████████████████████████████████████████\n" +
                    "██████▀░░░░░░░░▀████████▀▀░░░░░░░▀██████\n" +
                    "████▀░░░░░░░░░░░░▀████▀░░░░░░░░░░░░▀████\n" +
                    "██▀░░░░░░░░░░░░░░░░▀▀░░░░░░░░░░░░░░░░▀██\n" +
                    "██░░░░░░░░░░░░░░░░░░░▄▄░░░░░░░░░░░░░░░██\n" +
                    "██░░░░░░░░░░░░░░░░░░█░█░░░░░░░░░░░░░░░██\n" +
                    "██░░░░░░░░░░░░░░░░░▄▀░█░░░░░░░░░░░░░░░██\n" +
                    "██░░░░░░░░░░████▄▄▄▀░░▀▀▀▀▄░░░░░░░░░░░██\n" +
                    "██▄░░░░░░░░░████░░░░░░░░░░█░░░░░░░░░░▄██\n" +
                    "████▄░░░░░░░████░░░░░░░░░░█░░░░░░░░▄████\n" +
                    "██████▄░░░░░████▄▄▄░░░░░░░█░░░░░░▄██████\n" +
                    "████████▄░░░▀▀▀▀░░░▀▀▀▀▀▀▀░░░░░▄████████\n" +
                    "██████████▄░░░░░░░░░░░░░░░░░░▄██████████\n" +
                    "████████████▄░░░░░░░░░░░░░░▄████████████\n" +
                    "██████████████▄░░░░░░░░░░▄██████████████\n" +
                    "████████████████▄░░░░░░▄████████████████\n" +
                    "██████████████████▄▄▄▄██████████████████\n" +
                    "████████████████████████████████████████\n" +
                    "████████████████████████████████████████\n" +
                    "Congrats! Server has died.";
    private static final int exitCode = 314159265;
    private static DatagramSocket socket = null;
    private static Application application;

    /*______________________________________________________________________________________________________________*/
    /*                                     Client's requests methods                                                */
    private static int connectedUsersCount = 0;
    private static String currentFileName = "";

    public static void main(String[] callArgs) {
        System.out.println(duck);
        System.out.println(please);
        while (true) {
            runServerMainLoop();
        }

    }

    private static void runServerMainLoop() {
        boolean isRunning = true;
        try {
            startServer();

            application = new Application();

            while (isRunning) {
                DatagramPacket packet = receivePacket();
                InetSocketAddress clientSocketAddress = new InetSocketAddress(packet.getAddress(), packet.getPort());
                Request request = getRequest(packet);
                System.out.println("\n" + request.toString() + "\n");

                if (request.getType() == RequestType.CONNECT) {
                    connectedUsersCount++;
                    confirmConnect(clientSocketAddress);
                }

                if (request.getType() == RequestType.LOAD) {
                    if (connectedUsersCount > 1) {
                        Response response = new Response(ResponseType.SUCCESS,
                                "Collection has already been loaded by another user. Current file: '" + currentFileName + "'");
                        sendResponse(response, clientSocketAddress);
                        continue;
                    }
                    String fileName = (String) request.getArgs()[0];
                    loadFile(fileName, clientSocketAddress);
                    continue;
                }
                if (request.getType() == RequestType.CREATE) {
                    String fileName = (String) request.getArgs()[0];
                    createFile(fileName, clientSocketAddress);
                    continue;
                }
                if (request.getType() == RequestType.EXECUTE_COMMAND) {
                    Command command = request.getCommand();
                    Object[] cmdArgs = request.getArgs();
                    executeCommand(command, cmdArgs, clientSocketAddress);
                    continue;
                }

                if (request.getType() == RequestType.EXIT) {
                    boolean savedSuccessfully = exit(clientSocketAddress);
                    if (savedSuccessfully) {
                        connectedUsersCount--;
                        if (connectedUsersCount < 1) {
                            currentFileName = "";
                        }
                    }
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println(fatalError);
            System.exit(exitCode);
        }
    }

    private static void confirmConnect(InetSocketAddress clientSocketAddress) throws IOException {
        Response response;
        if (connectedUsersCount > 1) {
            response = new Response(ResponseType.SUCCESS_AND_FILE_ALREADY_LOADED,
                    "Connection established. Collection has already been loaded by another user. Current file: '" + currentFileName + "'");
        } else {
            response = new Response(ResponseType.SUCCESS, "Connection established");
        }
        sendResponse(response, clientSocketAddress);
    }

    /*______________________________________________________________________________________________________________*/


    /*______________________________________________________________________________________________________________*/
    /*                                     Initialize server service methods                                        */

    private static boolean exit(InetSocketAddress clientSocketAddress) throws IOException {
        try {
            Response response;
            if (connectedUsersCount > 1) {
                response = new Response(ResponseType.SUCCESS, "Other users are working. Collection will be saved after their disconnection");
            } else {
                LinkedList<String> runtimeMessages = application.saveCollection();
                String exitMessage = runtimeMessages.pollLast();
                response = new Response(ResponseType.SUCCESS, exitMessage, runtimeMessages);
            }
            sendResponse(response, clientSocketAddress);
            return true;
        } catch (FilePermissionException | InvalidFileNameException | SaveCollectionException | FileNotFoundException e) {
            ExceptionType exceptionType;
            if (e instanceof FilePermissionException) exceptionType = ExceptionType.PERMISSION_DENIED;
            else if (e instanceof InvalidFileNameException) exceptionType = ExceptionType.INVALID_FILE_NAME;
            else if (e instanceof SaveCollectionException) exceptionType = ExceptionType.EXECUTE_COMMAND_EXCEPTION;
            else exceptionType = ExceptionType.FILE_NOT_FOUND;
            Response response = new Response(ResponseType.ERROR, e.getMessage(), exceptionType);
            sendResponse(response, clientSocketAddress);
        }
        return false;
    }

//    private static void establishConnection() throws IOException {
//        if (socket == null) throw new SocketException("Socket and/or packet has not been initialised yet");
//        byte[] bytes = new byte[4096];
//        DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
//        socket.receive(packet);
//        clientSocketAddress = new InetSocketAddress(packet.getAddress(), packet.getPort());
//        sendResponse(new Response(ResponseType.SUCCESS, "Connection established"));
//        connectionEstablished = true;
//    }

    /*______________________________________________________________________________________________________________*/

    private static void executeCommand(Command command, Object[] cmdArgs, InetSocketAddress clientSocketAddress) throws IOException {
        try {
            String exitMessage = application.executeCommand(command, cmdArgs);
            Response response = new Response(ResponseType.SUCCESS, exitMessage);
            sendResponse(response, clientSocketAddress);
        } catch (CollectionException e) {
            Response response = new Response(ResponseType.ERROR, e.getMessage(), ExceptionType.EXECUTE_COMMAND_EXCEPTION);
            sendResponse(response, clientSocketAddress);
        }
    }

    private static void loadFile(String fileName, InetSocketAddress clientSocketAddress) throws IOException {
        try {
            LinkedList<String> runtimeMessages = application.loadCollection(fileName);
            String exitMessage = runtimeMessages.pollLast();
            Response response = new Response(ResponseType.SUCCESS, exitMessage, runtimeMessages);
            sendResponse(response, clientSocketAddress);
            currentFileName = fileName;
        } catch (FileNotFoundException | FilePermissionException | InvalidFileNameException e) {
            ExceptionType exceptionType;
            if (e instanceof FileNotFoundException) exceptionType = ExceptionType.FILE_NOT_FOUND;
            else if (e instanceof FilePermissionException) exceptionType = ExceptionType.PERMISSION_DENIED;
            else exceptionType = ExceptionType.INVALID_FILE_NAME;
            Response response = new Response(ResponseType.ERROR, e.getMessage(), exceptionType);
            sendResponse(response, clientSocketAddress);
        }
    }

    private static void createFile(String fileName, InetSocketAddress clientSocketAddress) throws IOException {
        try {
            String exitMessage = application.createBlankFile(fileName);
            Response response = new Response(ResponseType.SUCCESS, exitMessage);
            sendResponse(response, clientSocketAddress);
            currentFileName = fileName;
        } catch (InvalidFileNameException | FileAlreadyExistsException | FilePermissionException | CannotCreateFileException e) {
            ExceptionType exceptionType;
            if (e instanceof InvalidFileNameException) exceptionType = ExceptionType.INVALID_FILE_NAME;
            else if (e instanceof FileAlreadyExistsException) exceptionType = ExceptionType.FILE_ALREADY_EXISTS;
            else if (e instanceof FilePermissionException) exceptionType = ExceptionType.PERMISSION_DENIED;
            else exceptionType = ExceptionType.CANNOT_CREATE_FILE;
            Response response = new Response(ResponseType.ERROR, e.getMessage(), exceptionType);
            sendResponse(response, clientSocketAddress);
        }
    }

    private static void startServer() throws IOException {
        socket = new DatagramSocket(PORT);
    }

    private static DatagramPacket receivePacket() throws IOException {
        if (socket == null) throw new SocketException("Socket and/or packet has not been initialised yet");
        byte[] bytes = new byte[4096];
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
        socket.receive(packet);
        return packet;
    }

    private static Request getRequest(DatagramPacket packet) throws IOException, ClassNotFoundException {
//        if (socket == null) throw new SocketException("Socket and/or packet has not been initialised yet");
//        if (!connectionEstablished) throw new SocketException("Connection has not been established yet");
        ObjectInputStream bytesToRequestStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
        return (Request) bytesToRequestStream.readObject();
    }

    private static void sendResponse(Response response, InetSocketAddress clientSocketAddress) throws IOException {
        System.out.println(response);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream responseToBytes = new ObjectOutputStream(byteArrayOutputStream);
        responseToBytes.writeObject(response);
        DatagramPacket packet = new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.size(), clientSocketAddress);
        socket.send(packet);
    }


}