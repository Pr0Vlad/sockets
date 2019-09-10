package networks;

//Java implementation of  Server side 
//It contains two classes : Server and ClientHandler 
//Save file as Server.java 

import java.io.*;
import java.lang.StringBuilder;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*; 
import java.net.*; 

//Server class 
public class Server  
{ 
 public static void main(String[] args) throws IOException{  	 	 
	 //making the socket a client can connect to
	 ServerSocket socket = new ServerSocket(4142);
	 socket.setReuseAddress(true);
     Socket sock = null;
     Runtime runtime = Runtime.getRuntime();
     //string builders for netstat and other choices selected
     StringBuilder output = null;
     StringBuilder output2 = null;
     //to format date
     DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
   //choice selected if needed as referece
     int ch1; 
    try {   	 
    	//accepts connection
    		 sock = socket.accept(); 
    		 BufferedWriter re = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
    		//writes to the client
    		 re.write("connected to: " + sock + "\n" +
    		 		 "Here is the menu: " +  "\n");
    		re.flush();
    		//reads from the client
    		 InputStreamReader in = new InputStreamReader(sock.getInputStream());
    		BufferedReader bf = new BufferedReader(in);
    		String str = bf.readLine(); 		   		
    		//if recieves empty string goes into loop with menu
    		if(str.equalsIgnoreCase("")) {
    			re.write("welcome to the menu \n");
    			re.flush();
    			re.write("1: Host current Date and Time \n"+
    					 "2: Host Uptime \n"+
    					 "3: Host memory use \n"+
    					 "4: Host Netstat \n"+
    					 "5: Host current users \n"+
    					 "6: Host running processes \n"+
    					 "7: Quit \n");
    			re.flush();    			    			
    			re.write("Enter an option to begin \n");
    			re.flush();
    			//gets the choice from client
    			ch1 = Character.getNumericValue(bf.read());
    			//when choice not 7 it stays in loop
    			while(ch1 != 7) {    				    				
    				
    				switch(ch1) {
    				//cases for appropriate inputs
    				case 1:        					    					
    					 LocalDateTime now = LocalDateTime.now();
    						re.write(dtf.format(now) + " \n");
    						re.flush();    						
    					break;
    				case 2:	
    						RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
    						long uptime = rb.getUptime();
    						re.write("uptime is " + uptime/1000 + " seconds " + " \n");    						
    						re.flush();    						
    					break;
    				case 3:	
    					long memory = runtime.totalMemory() - runtime.freeMemory();
    					re.write("memory usage: " + memory/(1024*1024) + " megabytes \n");   						
    						re.flush();
    					break;
    				case 4:	
    					//sending a string with netstat to client
    					Runtime rt = Runtime.getRuntime();
    					String[] command = {"netstat"};
    					Process p = rt.exec(command);
    					output = new StringBuilder();
    					BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
    					// Read the output from the command
    					String s = null;
    					while ((s = stdInput.readLine()) != null) {   				 	
    						output.append("netstat command "+ s + " \n");			   
    					}
    					output.append("EXIT\n");
    					re.write(output.toString());
    					re.flush();  					
    					break;
    				case 5:	
    					//not done yet to get connected users but 
    					//uses linux command who
    					re.write("chose: " + ch1 + " \n");  						
    						re.flush();
    					break;
    				case 6:	
    					//not sure if this works but should send a string of linux processes to the client need to test on linux
    					Process process = Runtime.getRuntime().exec("ps -e -o command");
    			       output2 = new StringBuilder();
    			       BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
    			       String v = null;
    			        while((v = r.readLine()) != null)  {
    			            output2.append("process " + v + " \n");
    			        }   
    			        output.append("EXIT\n");
    					re.write(output2.toString());    						
    						re.flush();
    					break;
    					
    				case 7:	re.write("chose: " + ch1 + " \n");    						
    						ch1 = 7;
    						re.flush();
    					break;    				    					
    			}    				//lets u keep choosing values over and over	
	    				ch1 = Character.getNumericValue(bf.read());	    				   					    			
    				}    		
      		}
    		 //sock.close(); 
             //socket.close();
       }
         catch (Exception e){    
             e.printStackTrace(); 
         }
 }
}
 


        
        
      
        
   
 
 

