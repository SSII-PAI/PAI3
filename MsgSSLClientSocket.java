import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class MsgSSLClientSocket {
	private static final String SERVER_HOSTNAME = "localhost";
	private static final int SERVER_PORT = 8443;
	private static final int NUM_THREADS = 300;
	private static final Logger log = Logger.getLogger(MsgSSLClientSocket.class.getName());

	public static void main(String[] args) throws IOException, InterruptedException {
		FileHandler fh;

		try {
			// Este bloque configura el logger con el handler y el formatter
			fh = new FileHandler("./LogFile.log");
			log.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		} catch (IOException e) {
			log.severe(e.getMessage());
		}

		for (int i = 0; i < NUM_THREADS; i++) {
			new Thread(() -> {
				try {
					SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
					SSLSocket socket = (SSLSocket) socketFactory.createSocket(SERVER_HOSTNAME, SERVER_PORT);

					// Activar solo los protocolos que queremos soportar
					socket.setEnabledProtocols(new String[] { "TLSv1.3" });

					// Activar solo los suites de cifrado que queremos soportar
					socket.setEnabledCipherSuites(new String[] { "TLS_AES_128_GCM_SHA256", "TLS_AES_256_GCM_SHA384",
							"TLS_CHACHA20_POLY1305_SHA256" });

					// Iniciar handshake
					socket.startHandshake();

					InputStream inputStream = socket.getInputStream();
					OutputStream outputStream = socket.getOutputStream();

					// Escribe el usuario, la contraseña y el mensaje en el stream de salida
					outputStream.write("user:password:message".getBytes());

					// Lee la respuesta del stream de salida
					byte[] buffer = new byte[1024];
					int bytesRead = inputStream.read(buffer);
					String response = new String(buffer, 0, bytesRead);

					log.info(response);

					socket.close();
				} catch (IOException e) {
					log.severe(e.getMessage());
				}
			}).start();
		}

		// Wait for all threads to finish
		Thread.sleep(5000);

	}

}
