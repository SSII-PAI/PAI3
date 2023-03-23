import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class MsgSSLClientSocket {
	private static final String SERVER_HOSTNAME = "localhost";
	private static final int SERVER_PORT = 8443;
	private static final int NUM_THREADS = 300;

	public static void main(String[] args) throws IOException, InterruptedException {
		for (int i = 0; i < NUM_THREADS; i++) {
			new Thread(() -> {
				try {
					SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
					SSLSocket socket = (SSLSocket) socketFactory.createSocket(SERVER_HOSTNAME, SERVER_PORT);
					socket.setEnabledProtocols(new String[] { "TLSv1.3" });
					socket.startHandshake();

					InputStream inputStream = socket.getInputStream();
					OutputStream outputStream = socket.getOutputStream();

					// Write the user, password, and message to the output stream
					outputStream.write("user:password:message".getBytes());

					// Read the response from the input stream
					byte[] buffer = new byte[1024];
					int bytesRead = inputStream.read(buffer);
					String response = new String(buffer, 0, bytesRead);

					System.out.println(response);

					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}).start();
		}

		// Wait for all threads to finish
		Thread.sleep(5000);

	}
}
