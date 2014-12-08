/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kemricdc.constants;

/**
 *
 * @author Stanslaus Odhiambo
 */
public enum TablesOfInterest {
    
    TBLPATIENT_INFORMATION("tblpatient_information"),
    TBLADDRESS("tbladdress"),
    TBLTREATMENT_SUPPORTER("tbltreatment_supporter"),
    TBLORGANIZATION("tblOrganization"),
    TBLVISIT_INFORMATION("tblvisit_information"),
    TBLUNSATISFACTORYCOTRIMOXAZOLE("tblUnsatisfactorycotrimoxazole"),
    TBLUNSATISFACTORYART("tblUnsatisfactoryart"),
    TBLFPMETHOD("tblfpmethod"),
    TBLARTSIDEEFFECTS("tblARTSideEffects"),
    TBLNEWOI("tblNewOI"),
    TBLFAMILYMEMBERS("tblFamilyMembers"),
    TBL_VALUES("Tbl_Values"),
    TLKSEX("tlkSex"),
    TLKMARITAL("tlkmarital"),
    TLKSUPPORTER_RELATIONSHIPS("tlkSupporter_relationships"),
    TLKREGIMENFIRST("tlkregimenfirst"),
    TLKYESNO("tlkyesno"),
    TLKTBSTATUS("tlktbstatus"),
    TLKADHERENCESTATUS("tlkadherencestatus"),
    TLKADHERENCEUNSATISFACTORY("tlkadherenceunsatisfactory"),
    TLKFPMETHOD("tlkfpmethod"),
    TLKARTSIDEEFFECTS("tlkartsideeffects"),
    TLKOI_CODE("tlkoi_code");
    
    
    private final String value;

    private TablesOfInterest(String value) {
        this.value = value;
    }
    
    public String getValue(){
        return value;
    }
    
    
}
