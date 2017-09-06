/* Add the HEADER 
* Collin Gardner
* 8/31/2017
* CSE 383 Client Server Programming
* ServerMain.java, ClientMain.java
* Description: This is the Server program that works with the given Client 
* Client will connect, Server sends greeting via UTF string to client. Client sends
* value list.  Server reads in values, sums them up, and sends affirmation and sum to client.
* If error occurs, Server notifies client and sends error message.  Paremeters taken in by program
* are port, and localhost
* I certify this is my own work 
*/
package gardnecr;

import java.net.*;
import java.io.*;
import java.util.logging.*;

public class ServerMain {
	int port; // these 5 lines are setting up ports, sockets, strings, and loggers
	// this is a file handler
	String fqdn;
	ServerSocket servesock = null;
	Socket sock = null;
	DataInputStream dis = null;
	DataOutputStream dos = null;
	private static Logger LOGGER = Logger.getLogger("info");
	FileHandler fh = null;

	// Main method - DO NO WORK IN PSVM, just invoke other classes
	public static void main(String[] args) {
		int port = -1;
		try {
			port = Integer.parseInt(args[0]);
		} catch (Exception err) {
			System.err.println("Invalid usage - first arg must be port which is an integer");
			System.exit(-1);
		}
		 if (args.length < 2)
		 {
		 System.err.println("Invalid usage - port FQDN VALUES (at least 2 values required)");
		 }
		String fqdn = args[1];

		ServerMain server = new ServerMain(port, fqdn); // creates the client object
		server.Main(); // client object calls main functions with values, values is an array
	}

	// Constructor for Server, uses the port, LocalHost IP address
	public ServerMain(int port, String fqdn) {
		try {
			fh = new FileHandler("server.log"); 
			LOGGER.addHandler(fh);
			LOGGER.setUseParentHandlers(false);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

		} catch (IOException err) {
			System.err.println("Error - can't open log file");
		}
		LOGGER.info("ServerClientMAIN - Port = " + port + " fqdn=" + fqdn);
		this.fqdn = fqdn;
		this.port = port;
	}

	// main - takes an arraylist of arguments -> each argument is type, number.
	public void Main() {
		boolean result = false;
		String greeting = "";
		double response = 0.0;

		for (int retry = 5; retry > 0; retry--) {
			try {
				LOGGER.info("Connecting");
				System.out.println("going to connect");
				connect();
				result = true;
				System.out.println("Connect()");
				// so client and server connect, the greeting sent is uniqueID
				sendGreeting("gardnecr");
				System.out.println("Sent Greeting");
				receiveValues();
				dos.close();
				disconnect();
				result = true;
			} catch (IOException err) {
				System.err.println("Error during protocol " + err.toString());
				LOGGER.log(Level.SEVERE, "error during connection", err);
			}
		}
		if (result) {
			System.out.println("Success");
			System.out.println("Greeting = " + greeting);
			System.out.println("Response => " + response);
		} else {
			System.out.println("Failed");
		}
	}
	// This connects to the server, creates DataInputStream and DataOutputStream
	public void connect() throws IOException {
		//add try - cache
		try{
		servesock = new ServerSocket(port);
		sock = servesock.accept();
		dis = new DataInputStream(sock.getInputStream());
		dos = new DataOutputStream(sock.getOutputStream());
		sock.setSoTimeout(10000);
		}
		catch(IOException ex){
			System.err.println("Error during connection " + ex.toString());
			LOGGER.log(Level.SEVERE, "error during connection", ex);
		}
	}
	// Disconnects the sockets
	public void disconnect() throws IOException {
		try{
		sock.close();
		servesock.close();
		}
		catch(IOException ex){
			System.err.println("Error during disconection "+ ex.toString());
			LOGGER.log(Level.SEVERE, "error during disconnect", ex);
		}
	}
	//Sends the greeting to the client, greeting is uniqueID
	public void sendGreeting(String greeting) throws IOException {
		try{
		dos.writeUTF(greeting);
		dos.flush();
		} 
		catch(IOException ex){
			System.err.println("Error during sendGreeting " +ex.toString());
			LOGGER.log(Level.SEVERE, "error during sendGreeting ", ex);
		}
	}

	// This method reads in the values from client, adds those values, and sends sum and affirmation to client
	// If there is an errors, client is informed
	public void receiveValues() throws IOException {
		double ret = 0.0;
		double dub = 0.0;
		int num = 0;
		int num2 = 0;
		try {
			while (true) {
				num = dis.readInt();
				if (num == 1) {
					num2 = dis.readInt();
				} else if (num == 2) {
					dub = dis.readDouble();
				} else if (num == 0) {
					break;
				}
				ret = num2 + dub + ret;
				num2 = 0;
				dub = 0;
			}
		} catch (IOException ex) {
			System.out.println("Error during receiving values " + ex.toString());
			LOGGER.log(Level.SEVERE, "error during receiving values", ex);
			dos.writeUTF("Error" + ex.toString());
			dos.flush();
		}
		dos.writeUTF("OK");
		dos.flush();
		dos.writeDouble(ret);
		dos.flush();
	}
}