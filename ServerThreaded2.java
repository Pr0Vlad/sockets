package networks;


import java.io.*;
import java.lang.StringBuilder;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.net.*;
//ServerThreaded2 class
public class ServerThreaded2 implements Runnable{
    private Socket sock;


    public ServerThreaded2(Socket sock) {
        this.sock = sock;
    }

    public static void main(String[] args) throws IOException{
        //making the socket a client can connect to
        ServerSocket socket = new ServerSocket(4146);
        Socket sock = null;
        try {
        	
            while(true) {
            	
            	sock = socket.accept();
                
                ServerThreaded2 clients = new ServerThreaded2(sock);
                Thread client = new Thread(clients);
                client.start();
                   
            }
            
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
    	Runtime runtime = Runtime.getRuntime();
        StringBuilder output = null;
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    	BufferedWriter re = null;
		try {
			re = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
        InputStreamReader in = null;
		try {
			in = new InputStreamReader(sock.getInputStream());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        BufferedReader bf = new BufferedReader(in);
        int ch1=0 ;
		try {
			ch1 = Character.getNumericValue(bf.read());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        switch(ch1) {
            //cases for appropriate inputs
            case 1:
                LocalDateTime now = LocalDateTime.now();
                try {
                    re.write(dtf.format(now) + " \n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    re.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
                long uptime = rb.getUptime();
                try {
                    re.write("uptime is " + uptime/1000 + " seconds " + " \n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    re.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                 Process p = null;
                try {
                    p = Runtime.getRuntime().exec("free");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                output = new StringBuilder();
                // Read the output from the command
                try {
                    p.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String s = null;
                while (true) {
                    try {
                        if (!((s = stdInput.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    output.append(s + " \n");
                }
                output.append("EXIT\n");
                try {
                    re.write(output.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    re.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                //sending a string with netstat to client
                Process p = null;
                try {
                    p = Runtime.getRuntime().exec("netstat");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                output = new StringBuilder();
                // Read the output from the command
                try {
                    p.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String s = null;
                while (true) {
                    try {
                        if (!((s = stdInput.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    output.append("netstat command "+ s + " \n");
                }
                output.append("EXIT\n");
                try {
                    re.write(output.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    re.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 5:
                //not done yet to get connected users but
                //uses linux command who
                Process pr = null;
                try {
                    pr = Runtime.getRuntime().exec("who");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                output = new StringBuilder();

                try {
                    pr.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BufferedReader rs = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                String vs = null;
                while(true)  {
                    try {
                        if (!((vs = rs.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    output.append("process " + vs + " \n");
                }
                output.append("EXIT\n");
                try {
                    re.write(output.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    re.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 6:
                //not sure if this works but should send a string of linux processes to the client need to test on linux
                Process process = null;
                try {
                    process = Runtime.getRuntime().exec("ps -e -o command");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                output = new StringBuilder();

                try {
                    process.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String v = null;
                while(true)  {
                    try {
                        if (!((v = r.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    output.append("process " + v + " \n");
                }
                output.append("EXIT\n");
                try {
                    re.write(output.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    re.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 7:
                try {
                    re.write("chose: " + ch1 + " \n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    re.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    
            try {
				sock.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
    }
}
