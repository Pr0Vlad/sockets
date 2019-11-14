
import java.io.*;
import java.net.*;
import java.util.Scanner;
public class client2 implements Runnable {
    static int choice;
    static int ttime = 0;
    static int ttime2 = 0;
    client2(int choice) {
        this.choice = choice;

    }
    client2(int choice, int ttime){
        this.choice = choice;
        this.ttime = ttime;
    }

    public static void main(String[] args) throws IOException {
        // scanner for input
        Scanner scan2 = new Scanner(System.in);
        do {
            ttime = 0;
            ttime2 = 0;
            System.out.println("1: Host current Date and Time \n" + "2: Host Uptime \n" + "3: Host memory use \n"
                    + "4: Host Netstat \n" + "5: Host current users \n" + "6: Host running processes \n"
                    + "7: Quit \n");

            choice = 0;
            String input;

            while(true) {
                try {
                    input = scan2.nextLine();
                    choice = Integer.parseInt(input);
                    while(choice >7 || choice <1) {
                        System.out.println("Wrong input try entering a number between 1 and 7: ");
                        choice = scan2.nextInt();

                    }
                    break;

                } catch (NumberFormatException e) {
                    System.out.println("Please Enter An Integer");
                }
            }

            int numClients = 0;
            String input2;

            if(choice != 7) {
                System.out.println("enter the amount of clients to have: ");
                while(true) {
                    try {
                        input2 = scan2.nextLine();
                        numClients = Integer.parseInt(input2);

                        while(numClients >100 || numClients <1) {
                            System.out.println("Wrong input try entering a number between 0 and 100: ");
                            numClients = scan2.nextInt();

                        }

                        break;

                    } catch (NumberFormatException e) {
                        System.out.println("Please Enter An Integer");
                    }
                }
            }else {
                numClients = 1;
            }


            Thread client[] = new Thread[numClients];
            for (int i = 0; i < numClients; i++) {

                client2 clients = new client2(choice, ttime);
                client[i] = new Thread(clients);
            }
            for (int j = 0; j < numClients; j++) {
                client[j].start();
            }
            for (int k = 0; k < numClients; k++) {
                try{
                    client[k].join();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println(ttime2/numClients + " milli ");
        } while (choice != 7);
    }

    public void setT (int ttime) {
        ttime2 += ttime;
    }

    @Override
    public void run() {
        try {
            InetAddress ip = InetAddress.getByName("192.168.101.101");
            Socket sock = new Socket(ip, 4146);
            long time = System.currentTimeMillis();
            long end;
            BufferedReader re = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            PrintStream send = new PrintStream(sock.getOutputStream());

            send.println(choice + "\n");
            send.flush();
            String line = new String();
            switch (choice) {
                case 1:
                case 3:
                case 2:
                    System.out.println(re.readLine());
                    end = System.currentTimeMillis();
                    ttime += end - time ;
                    this.setT(ttime);
                    break;
                case 4:
                case 5:
                case 6:
                    while ((line = re.readLine()) != null) {
                        if (line.equals("EXIT") == false) {

                            System.out.println(line);
                        } else{
                            end = System.currentTimeMillis();
                            ttime += end - time ;
                            this.setT(ttime);
                            break;
                        }

                    }

                    break;

                case 7:
                    sock.close();
                    end = System.currentTimeMillis();
                    ttime += end - time ;
                    this.setT(ttime);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
