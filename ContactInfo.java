package prodigy_infotech;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactInfo {  
	private final String insert="insert into contact_info values (?,?,?) ";
	private final String delete="delete from contact_info where name=?";
	private final String update="update contact_info set  phone_number=?, email=? where name=?";
	private final String display="select row_number() over (order by phone_number) "
			                     + "as row_num, name, phone_number, email from contact_info";
		
	ContactInfo(){		   
		Scanner sc=null;
		Connection con=null;
		PreparedStatement ps=null;		
		try{ 
			int op,n,i,result=0,count=0;
			String name,phone_number,email;		
			Class.forName("oracle.jdbc.driver.OracleDriver");
			if(sc==null && con==null) {
				sc= new Scanner(System.in);
				con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","c##gopi","7730");				
			}   
			 System.out.println("1. Insert");
			 System.out.println("2. Delete");
			 System.out.println("3. Update");
			 System.out.println("4. Display");			
			 System.out.println("5. Exit");
			 do { 
				
				 System.out.println("Choose your Option: ");
				 op=sc.nextInt();
				 if(op>=1 && op<=5) {
				 switch(op) {
				   
				 case 1: try { 
					     
					 		 ps=con.prepareStatement(insert);
					 	 
					 		 System.out.println("How many contacts you want to insert: ");
					 		 n=sc.nextInt();
					 		 for(i=1;i<=n;i++) {
					   		 System.out.println("Enter "+i+" contact details.");					       				   		
					 		 System.out.println("Enter name: ");
					 		 name=sc.next();
					 		 System.out.println("Enter Phone number: ");
					 		 phone_number=sc.next();
					 		 System.out.println("Enter Email: ");
					 		 email=sc.next();
					 		 ps.setString(1, name); ps.setString(2, phone_number); ps.setString(3, email);
					 		 result=ps.executeUpdate();
					 		 if(result==0) {
					 			 System.out.println(i +" Contact details not inserted.");					 			 
					 		 }
					 		 else 
					 			 System.out.println(i +" Contact details inserted.");
					 		
					 	   }	    	 
		 	            	 }
				 			catch(InputMismatchException ie) {
			 			 System.out.println("Input Mismatch Exception.");
				 		 }
						 catch(Exception e) {
			        	System.out.println(e);
					  } 
				 		System.out.println("---------------------------------------------");
				         break;
				 case 2: 
					    try {
					 	ps=con.prepareStatement(delete);
					    System.out.println("Enter name you want to delete: ");
					    name=sc.next();					 	
					 	ps.setString(1, name);
					 	result=ps.executeUpdate();
					 	if(result==0)
					 		 System.out.println("Person "+name+ " details not found in the table.");
					 	else
					 		 System.out.println("person "+name+" details deleted.");	
					 
					    }
					    catch(InputMismatchException ie) {
				 		System.out.println("Input Mismatch Exception.");
					 	}
					    catch(Exception e) {
		        		System.out.println(e);
						}
					    System.out.println("---------------------------------------------");
					    break;
				 case 3:
					    try {
					        ps = con.prepareStatement(update);
					        System.out.println("Enter phone_number: ");
					        phone_number = sc.next();
					        System.out.println("Enter Email_Id: ");
					        email = sc.next();
					        System.out.println("Enter which name you want to update (where): ");
					        name = sc.next();
					        ps.setString(1, phone_number);
					        ps.setString(2, email);
					        ps.setString(3, name);// Update to use 'email' instead of 'mail'
					       
					        result = ps.executeUpdate();
					        if (result == 0)
					            System.out.println("Person " + name + " details not Updated.");
					        else
					            System.out.println("Person " + name + " details Updated.");
					    } catch (InputMismatchException ie) {
					        System.out.println("Input Mismatch Exception.");
					    } catch (SQLException e) {
					    	System.out.println(e);
					    } catch (Exception e) {
					    	System.out.println(e);
					    } finally {
					        if (ps != null) {
					            try {
					                ps.close();
					            } catch (SQLException e) {
					                System.out.println("Error closing PreparedStatement: " + e.getMessage());
					            }
					        }
					    }
					    System.out.println("---------------------------------------------");
					    break;

		 case 4:   
					 	try {
					 		ps=con.prepareStatement(display);
				 		    ResultSet rs=ps.executeQuery();
				 		    while(rs.next()) {
				 			System.out.print( rs.getInt(1)+"   ");
				 			System.out.print( rs.getString(2)+"   ");
				 			System.out.print( rs.getString(3)+"   ");
				 			System.out.print( rs.getString(4)+"   ");
				 			System.out.println();
				 			count=1;
				 		    } 
				 		    if(count==0)
				 		    	System.out.println("No data in the \"CONTACT_INFO\" table. ");
					 	   }
					 		catch(InputMismatchException ie) {
					 		System.out.println("Input Mismatch Exception.");
						 	}
						    catch(Exception e) {
			        		System.out.println(e);
							}
					 	    System.out.println("---------------------------------------------");
			    		 break;
	
				 case 5:	  
					     System.out.println("                 Exit.");
					     break;	 
				 }
				} 
				 else 
					 System.out.println("Sorry you entered wrong input.");
			 }while(op!=5);	
		}
		catch(InputMismatchException ie) {
			
			System.out.println("InputMismatchException");
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	public static void main(String[] args) throws SQLException{		 
		 new ContactInfo();
	}
}
