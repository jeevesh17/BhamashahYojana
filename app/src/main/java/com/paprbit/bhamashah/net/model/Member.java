package com.paprbit.bhamashah.net.model;

import java.io.Serializable;

/**
 * Created by ankush38u on 3/19/2017.
 */

public class Member implements Serializable{
    private String CATEGORY_DESC_ENG;

    private String AADHAR_ID;

    private String HOUSE_NUMBER_ENG;

    private String MOTHER_NAME_ENG;
    private String MOTHER_NAME_HND;

    private String STATE;

    private String RELATION_ENG;

    private String BUILDING_NO_ENG;

    private String ECONOMIC_GROUP;

    private String DOB;

    private String STREET_NAME_ENG;

    private String BHAMASHAH_ID;

    private String IFSC_CODE;

    private String M_ID;

    private String FAMILYIDNO;

    private String PIN_CODE;

    private String LANDLINE_NO;

    private String VILLAGE_NAME;

    private String GP_WARD;

    private String EMAIL;

    private String SPOUCE_NAME_ENG;
    private String SPOUCE_NAME_HND;

    private String IS_RURAL;

    private String DISTRICT;

    private String EDUCATION_DESC_ENG;

    private String ACC_NO;

    private String ADDRESS;

    private String INCOME_DESC_ENG;

    private String BANK_NAME;

    private String LAND_MARK_ENG;

    private String RATION_CARD_NO;

    private String MOBILE_NO;

    private String NAME_ENG;
    private String NAME_HND;

    private String FATHER_NAME_ENG;
    private String FATHER_NAME_HND;

    private String GENDER;

    private String FAX_NO;

    private String BLOCK_CITY;

    private String NFSA_BPL_NUMBER;

    public String getNFSA_BPL_NUMBER() {
        return NFSA_BPL_NUMBER;
    }

    public String getCATEGORY_DESC_ENG() {
        return CATEGORY_DESC_ENG;
    }

    public String getAADHAR_ID() {
        return AADHAR_ID;
    }

    public String getHOUSE_NUMBER_ENG() {
        return HOUSE_NUMBER_ENG;
    }

    public String getMOTHER_NAME(String lang) {
        if (lang.equals("en")) {
            return MOTHER_NAME_ENG;
        } else {
            return MOTHER_NAME_HND;
        }
    }

    public String getSTATE() {
        return STATE;
    }

    public String getRELATION_ENG() {
        return RELATION_ENG;
    }

    public String getBUILDING_NO_ENG() {
        return BUILDING_NO_ENG;
    }

    public String getECONOMIC_GROUP() {
        return ECONOMIC_GROUP;
    }

    public String getDOB() {
        return DOB;
    }

    public String getSTREET_NAME_ENG() {
        return STREET_NAME_ENG;
    }

    public String getBHAMASHAH_ID() {
        return BHAMASHAH_ID;
    }

    public String getIFSC_CODE() {
        return IFSC_CODE;
    }

    public String getM_ID() {
        return M_ID;
    }

    public String getFAMILYIDNO() {
        return FAMILYIDNO;
    }

    public String getPIN_CODE() {
        return PIN_CODE;
    }

    public String getLANDLINE_NO() {
        return LANDLINE_NO;
    }

    public String getVILLAGE_NAME() {
        return VILLAGE_NAME;
    }

    public String getGP_WARD() {
        return GP_WARD;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getSPOUCE_NAME(String lang) {
        if (lang.equals("en")) {
            return SPOUCE_NAME_ENG;
        } else {
            return SPOUCE_NAME_HND;
        }
    }

    public String getIS_RURAL() {
        return IS_RURAL;
    }

    public String getDISTRICT() {
        return DISTRICT;
    }

    public String getEDUCATION_DESC_ENG() {
        return EDUCATION_DESC_ENG;
    }

    public String getACC_NO() {
        return ACC_NO;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public String getINCOME_DESC_ENG() {
        return INCOME_DESC_ENG;
    }

    public String getBANK_NAME() {
        return BANK_NAME;
    }

    public String getLAND_MARK_ENG() {
        return LAND_MARK_ENG;
    }

    public String getRATION_CARD_NO() {
        return RATION_CARD_NO;
    }

    public String getMOBILE_NO() {
        return MOBILE_NO;
    }

    public String getNAME(String lang) {
        if (lang.equals("en"))
            return NAME_ENG;
        else return NAME_HND;
    }

    public String getFATHER_NAME(String lang) {
        if (lang.equals("en"))
            return FATHER_NAME_ENG;
        else return FATHER_NAME_HND;
    }

    public String getGENDER(String lang) {
        if (lang.equals("en"))
            return GENDER;
        else {
            if (GENDER.equalsIgnoreCase("Male")) {
                return "पुरुष";
            } else {
                return "स्त्री";
            }
        }
    }

    public String getFAX_NO() {
        return FAX_NO;
    }

    public String getBLOCK_CITY() {
        return BLOCK_CITY;
    }

}
