package networks;

// Java implementation for a client 
// Save file as Client.java 
  
import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
import java.net.*;
// Client class 
public class Client  
{ 
    public static void main(String[] args) throws IOException  
    { 
    	//scanner for input 
    	 Scanner scan2 = new Scanner(System.in);
    	 //connecting to server
         InetAddress ip = InetAddress.getByName("192.168.101.101"); 
         Socket sock = new Socket(ip, 4143); 
         //trying this after connecting
    	try
        { 
    		//reader and writer to communicate with server
    		BufferedReader re = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    		PrintStream send = new PrintStream(sock.getOutputStream());
            //reading from server
            System.out.println(re.readLine());
            System.out.println(re.readLine());
            //sending empty string to get into server loop
           String choice = "";
           send.println(choice + "\n");
           send.flush();
           //welcome
           //loop to print out menu options
           for(int i = 0; i<9; i++) {
        	   System.out.println(re.readLine());      
           }
           //getting selected option and sending to server
         int option = scan2.nextInt();
         send.println(option + "\n");
         send.flush();
              //loop to keep selecting options as long as 7 is not pressed
          while(re != null) {      	
        	  String line= new String();
        	  //switch for different print outs based on selection cuz some need a loop 
        	  switch(option) {
        	  	case 1: System.out.println(re.readLine());    
        	  		break;
        	  	case 2: System.out.println(re.readLine());    
        	  		break;
        	  	case 3: System.out.println(re.readLine());    
        	  		break;
        	  	case 4:	
        	  		while((line = re.readLine()) != null) {     		  
            		   if(line.equals("EXIT") == false) {
             			  System.out.println(line);    
                		   }
                		   else
                			break;
                	   } 
        	  		break;
        	  	case 5:
        	  		while((line = re.readLine()) != null) {     		  
         		   if(line.equals("EXIT") == false) {
          			  System.out.println(line);    
             		   }
             		   else
             			break;
             	   }  	
        	  		break;
        	  	case 6:
        	  		while((line = re.readLine()) != null) {     		  
              		   if(line.equals("EXIT") == false) {
               			  System.out.println(line);    
                  		   }
                  		   else
                  			break;
         	  		}
        	  		break;
              case 7: 
                sock.close();
                break;
        	  		//if wrong entry lets u go again
        	  	default: System.out.println("Not a valid option chooose between 1 and 7:");    
        	  		break;		
        	  }      
        	  //re entry to be able to keep choosing options
        	  option = scan2.nextInt();
              send.println(option + "\n");
              send.flush();          
          	}           
         // sock.close();
         // send.close();
         // scan.close();
        }catch(Exception e){ 
            e.printStackTrace(); 
        }        
    } 
} 
