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
//ServerThreaded class
public class ServerThreaded implements Runnable{
    private Socket sock;
    private BufferedWriter re;
    private InputStreamReader in;
    private BufferedReader bf;
    private Runtime runtime;

    public ServerThreaded(Socket sock) {
        this.sock = sock;
    }
    public ServerThreaded(Socket sock, BufferedWriter re) {
        this.sock = sock;
        this.re = re;
    }
    public ServerThreaded(Socket sock, BufferedWriter re, InputStreamReader in) {
        this.sock = sock;
        this.re = re;
        this.in = in;
    }
    public ServerThreaded(Socket sock, BufferedWriter re, InputStreamReader in, BufferedReader bf) {
        this.sock = sock;
        this.re = re;
        this.in = in;
        this.bf=bf;
    }

    public ServerThreaded(Socket sock, BufferedWriter re, InputStreamReader in, BufferedReader bf, Runtime runtime) {
        this.sock = sock;
        this.re = re;
        this.in = in;
        this.bf=bf;
        this.runtime=runtime;
    }

    public static void main(String[] args) throws IOException{
        //making the socket a client can connect to
        ServerSocket socket = new ServerSocket(4146);
        Socket sock = null;
        Runtime runtime = Runtime.getRuntime();

        try {
            //accepts connection
            while(true) {
                sock = socket.accept();
                BufferedWriter re = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
                InputStreamReader in = new InputStreamReader(sock.getInputStream());
                BufferedReader bf = new BufferedReader(in);

                ServerThreaded clients = new ServerThreaded(sock, re, in, bf, runtime);
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
        StringBuilder output = null;
        StringBuilder output2 = null;
        int ch1=0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        try {
            ch1 = Character.getNumericValue(bf.read());
        } catch (IOException e) {
            e.printStackTrace();
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
                long memory = runtime.totalMemory() - runtime.freeMemory();
                try {
                    re.write("memory usage: " + memory/(1024*1024) + " megabytes \n");
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
                output2 = new StringBuilder();

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
                    output2.append("process " + v + " \n");
                }
                output2.append("EXIT\n");
                try {
                    re.write(output2.toString());
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
            e.printStackTrace();
        }
    }
}
