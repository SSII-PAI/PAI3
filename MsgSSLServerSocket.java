import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class MsgSSLServerSocket {
	private static final int PORT = 8443;

	public static void main(String[] args) throws IOException {
		SSLServerSocketFactory serverSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		SSLServerSocket serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(PORT);
		serverSocket.setEnabledProtocols(new String[] { "TLSv1.3" });
		serverSocket.setEnabledCipherSuites(
				new String[] { "DHE-RSA", "ECDHE-RSA", "ECDHE-ECDSA" });

		System.out.println("Server listening on port " + PORT);

		while (true) {
			// Aceptar conexión del cliente
			Socket clientSocket = serverSocket.accept();

			// Crear hilo nuevo
			new Thread(new ClientHandler(clientSocket)).start();
		}
	}

	private static class ClientHandler implements Runnable {
		private final Socket clientSocket;

		public ClientHandler(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}

		@Override
		public void run() {
			try {
				// Mensaje del cliente
				InputStream inputStream = clientSocket.getInputStream();

				// Mensaje de respuesta del servidor
				OutputStream outputStream = clientSocket.getOutputStream();

				// Read the user, password, and message from the input stream
				// Authenticate the user and password
				// Process the message

				// Write the response to the output stream
				outputStream.write("Message processed successfully".getBytes());

				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
