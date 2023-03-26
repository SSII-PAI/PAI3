import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.util.logging.Logger;

public class MsgSSLServerSocket {
	private static final int PORT = 8443;
	private static final Logger log = Logger.getLogger(MsgSSLServerSocket.class.getName());

	public static void main(String[] args) throws IOException {
		SSLServerSocketFactory serverSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		try (SSLServerSocket serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(PORT)) {
			// Activar solo los protocolos que queremos soportar
			serverSocket.setEnabledProtocols(new String[] { "TLSv1.3" });

			// Activar solo los suites de cifrado que queremos soportar
			serverSocket.setEnabledCipherSuites(new String[] { "TLS_AES_128_GCM_SHA256", "TLS_AES_256_GCM_SHA384",
					"TLS_CHACHA20_POLY1305_SHA256" });

			log.info("Server listening on port " + PORT);

			while (true) {
				// Aceptar conexión del cliente
				Socket clientSocket = serverSocket.accept();

				// Crear hilo nuevo
				new Thread(new ClientHandler(clientSocket)).start();
			}
		} catch (IOException e) {
			log.severe(e.getMessage());
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

				// Mensaje de respuesta del servidor
				OutputStream outputStream = clientSocket.getOutputStream();

				// Escribir mensaje de respuesta
				outputStream.write("Message processed successfully".getBytes());

				// Cerrar conexión
				clientSocket.close();
			} catch (IOException e) {
				log.severe(e.getMessage());
			}
		}
	}
}
