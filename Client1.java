package networks;

//Java implementation for a client 
//Save file as Client.java 

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.net.*;

//Client class 
public class Client1 implements Runnable {
	static int choice;

	Client1(int choice) {
		this.choice = choice;
	}

	public static void main(String[] args) throws IOException {

		// scanner for input
		Scanner scan2 = new Scanner(System.in);
		do {

			System.out.println("1: Host current Date and Time \n" + "2: Host Uptime \n" + "3: Host memory use \n"
					+ "4: Host Netstat \n" + "5: Host current users \n" + "6: Host running processes \n"
					+ "7: Quit \n");
			int choice = scan2.nextInt();

			System.out.println("enter the amount of clients to have: ");
			int numClients = scan2.nextInt();

			Thread client[] = new Thread[numClients];
			for (int i = 0; i < numClients; i++) {

				Client1 clients = new Client1(choice);
				client[i] = new Thread(clients);
			}
			for (int j = 0; j < numClients; j++) {
				client[j].start();
			}
		} while (choice != 7);

	}

	@Override
	public void run() {
		try {
			InetAddress ip = InetAddress.getByName("192.168.101.101");
			Socket sock = new Socket(ip, 4144);

			long time = System.currentTimeMillis();
			long end;
			
			System.out.println(time);
			// reader and writer to communicate with server
			BufferedReader re = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			PrintStream send = new PrintStream(sock.getOutputStream());
			// reading from server
			send.println(choice + "\n");
			send.flush();

			System.out.println(re.readLine());
			// loop to keep selecting options as long as 7 is not pressed
			String line = new String();
			// switch for different print outs based on selection cuz some need a loop
			switch (choice) {
			case 1:
				System.out.println(re.readLine());
				
				 end = System.currentTimeMillis();
				System.out.println(end - time + " time ");
				break;
			case 2:
				System.out.println(re.readLine());
				 end = System.currentTimeMillis();
				System.out.println(end - time + " time ");
				break;
			case 3:
				System.out.println(re.readLine());
				 end = System.currentTimeMillis();
				System.out.println(end - time + " time ");
				break;
			case 4:
				while ((line = re.readLine()) != null) {
					if (line.equals("EXIT") == false) {
						System.out.println(line);  
					} else
						break;
				}
				 end = System.currentTimeMillis();
				System.out.println(end - time + " time ");
				break;
			case 5:
				while ((line = re.readLine()) != null) {
					if (line.equals("EXIT") == false) {
						System.out.println(line);  
					} else
						break;
				}
				end = System.currentTimeMillis();
				System.out.println(end - time + " time ");
				break;
			case 6:
				while ((line = re.readLine()) != null) {
					if (line.equals("EXIT") == false) {
						System.out.println(line);  
					} else
						break;
				}
				 end = System.currentTimeMillis();
				System.out.println(end - time + " time ");
				break;
			case 7:
				sock.close();
				 end = System.currentTimeMillis();
				System.out.println(end - time + " time ");
				break;
			// if wrong entry lets u go again
			}
			// re entry to be able to keep choosing options
			// sock.close();
			// send.close();
			// scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
