/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package changetracker.wrappers;

import changetracker.util.ChangeTracker;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vicky
 */
public class PatientEnrollmentTracker extends TimerTask{
    public static void trackPatientEnrollment() throws Exception
    {
        try{
            List results = ChangeTracker.getChangesFromTable(ChangeTracker.getLastVersion("mst_Patient"), "Mst_Patient", "Ptn_Pk");
            
            if(!results.isEmpty())
            {
            for(Object object:results)
            {
               HashMap record =  (HashMap)object;
               String change_operation = (String)record.get("SYS_CHANGE_OPERATION");
               int primary_key = (int)record.get("Ptn_Pk");
               
               /*Read the bytes from the blob using a ByteArrayOutputStream 
                and then create a String from the resulting byte array.*/
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];

                InputStream in;
                in = ((Blob)record.get("FirstName")).getBinaryStream();
                

                int n = 0;
                while ((n=in.read(buf))>=0)
                {
                   baos.write(buf, 0, n);
                }

                in.close();
                byte[] bytes = baos.toByteArray();
                String blob_first_name_string = new String(bytes); 
               
               
                
               String gender;
               String marital_status;
               
               switch((int)record.get("Sex")){
                   case 16:
                       gender = "M";
                       break;
                   case 17:
                       gender = "F";
                       break;
                   default:
                       gender = "O";
                       break;
                }
               
               switch((int)record.get("MaritalStatus")){
                   case 42:
                       marital_status = "Single";
                       break;
                   case 43:
                       marital_status = "Married";
                       break;
                   case 44:
                       marital_status = "Divorced";
                       break;
                   case 189:
                       marital_status = "Widowed";
                       break;
                   case 45:
                       marital_status = "Other";
                       break;
                   case 290:
                       marital_status = "Married Polygamous";
                       break;
                   case 291:
                       marital_status = "Married Monogamous";
                       break;
                   case 292:
                       marital_status = "Cohabitating";
                       break;
                   default:
                       marital_status = "Undefined";
                       break;
                }
                       
                
               
               
                Timestamp dob =   (Timestamp) record.get("DOB");
                Timestamp created_date =   (Timestamp) record.get("CreateDate");
                Timestamp update_date =   (Timestamp) record.get("UpdateDate");
                Timestamp ART_start_date =   (Timestamp) record.get("ARTStartDate");
                
                
                
                //System.out.println("DOB "+dob);
                System.out.println("change_operation "+change_operation);
                
                System.out.println("first_name "+record.get("firstname_decrypted"));
                System.out.println("middle_name "+record.get("middlename_decrypted"));
                System.out.println("last_name "+record.get("lastname_decrypted"));
                //System.out.println("middle_name "+middle_name);
                //System.out.println("last_name "+last_name);
                System.out.println("sex "+gender);
                System.out.println("marital_status "+marital_status);
                System.out.println("dob "+dob);
                System.out.println("created date "+created_date);
                System.out.println("update date "+update_date);
                System.out.println("ART Start Date "+ART_start_date);
          //System.out.println(object);
            }
            }
            else
            {System.out.println("No changes To Report!");}
        }catch(SQLException ex){}
        
        }
//            boolean hasNext = rs.next();
//             
//                while (hasNext) {
//
//                    if (!hasNext) {
//                       // set current version to the change version of the last row
//                        setCurrentVersion("mst_Patient", String.valueOf(rs.getInt("SYS_CHANGE_VERSION")));
//                    }
//                    
//                    String change_operation = rs.getString("SYS_CHANGE_OPERATION");
//                    
//                    //demographic details
//                    int id = rs.getInt("Ptn_Pk");
//                    String first_name = rs.getString("FirstName");
//                    String middle_name = rs.getString("MiddleName");
//                    String lst_name = rs.getString("LastName");
//                    int sex = rs.getInt("Sex");
//                    int marital_staus = rs.getInt("MaritalStatus");
//                    Date dob = rs.getDate("DOB");
//                    Date date_created = rs.getDate("CreateDate");
//                    Date date_modified = rs.getDate("UpdateDate");
//                    Date ART_start_date = rs.getDate("ARTStartDate");
//                    
//                    //location details
//                    int village = rs.getInt("VillageName");
//                    int district = rs.getInt("DistrictName");
//                    int province = rs.getInt("Province");
//                    int location = rs.getInt("LocationID");
//                    String landmark = rs.getString("Landmark");
//                    String sublocation = rs.getString("Sublocation");
//                    
//                    int change_version = rs.getInt("SYS_CHANGE_VERSION");
//                    
//                    System.out.println("id: "+id+"\tFirst Name: "+first_name+"\tMiddle Name: "+middle_name);
//                    System.out.println("sex: "+sex+"\tMarital Status: "+marital_staus+"\tdob: "+dob);
//                    System.out.println("ART Start: "+ART_start_date+"\tvillage: "+village+"\tdistrict: "+district);
//                    System.out.println("province: "+province+"\tlocation: "+location+"\tlandmark: "+landmark);
//                    System.out.println("sublocation: "+sublocation+"\tchange version: "+change_version+"\tchange operation: "+change_operation);
//                }
//        }catch(SQLException ex){
//            System.out.println(ex.getMessage());
//        }

    @Override
    public void run() {
        try {
            trackPatientEnrollment();
        } catch (Exception ex) {
            Logger.getLogger(PatientEnrollmentTracker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    }

   

