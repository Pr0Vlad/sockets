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
public class Server2  
{ 
public static void main(String[] args) throws IOException{  	 	 
	 //making the socket a client can connect to
	 ServerSocket socket = new ServerSocket(4145);
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
	  while(true) {
  		 sock = socket.accept(); 
  		 BufferedWriter re = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
  		//writes to the client
  		// re.write("connected to: " + sock + "\n" );
  		//re.flush();
  		//reads from the client
  		 InputStreamReader in = new InputStreamReader(sock.getInputStream());
  		BufferedReader bf = new BufferedReader(in);
  				   		
  		//if recieves empty string goes into loop with menu
  		
  			//gets the choice from client
  			ch1 = Character.getNumericValue(bf.read());
  			//when choice not 7 it stays in loop
  			  				    				
  				
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
  					Process p = Runtime.getRuntime().exec("netstat -natu | grep 'ESTABLISHED");
  					output = new StringBuilder();
  					// Read the output from the command
  					p.waitFor();
  					BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
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
  					Process pr = Runtime.getRuntime().exec("who");
   			       output = new StringBuilder();
   			       
   			       pr.waitFor();
   			       BufferedReader rs = new BufferedReader(new InputStreamReader(pr.getInputStream()));
   			       String vs = null;
   			        while((vs = rs.readLine()) != null)  {
   			            output.append("process " + vs + " \n");
   			        }   
   			        output.append("EXIT\n");
   					re.write(output.toString());    						
   						re.flush();
   					break;		
  				case 6:	
  					//not sure if this works but should send a string of linux processes to the client need to test on linux
  					Process process = Runtime.getRuntime().exec("ps -e -o command");
  			       output2 = new StringBuilder();
  			       
  			       process.waitFor();
  			       BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
  			       String v = null;
  			        while((v = r.readLine()) != null)  {
  			            output2.append("process " + v + " \n");
  			        }   
  			        output2.append("EXIT\n");
  					re.write(output2.toString());    						
  						re.flush();
  					break;				
  				case 7:	re.write("chose: " + ch1 + " \n");    						
  						re.flush();
  					break;    				    					
  			}    				//lets u keep choosing values over and over	
	    				ch1 = Character.getNumericValue(bf.read());	    				   					    			
  				  sock.close(); 		
    		}
     }
       catch (Exception e){    
           e.printStackTrace(); 
       }
  }
}




      
      
    
      
 




