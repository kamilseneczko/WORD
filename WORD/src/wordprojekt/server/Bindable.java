package wordprojekt.server;

import java.net.Socket;

public interface Bindable {
	public odbierz zbindujWyslij(Socket socket, wyslij w);
}
