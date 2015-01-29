/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kemricdc.hapi.oru;

import java.io.IOException;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.Varies;
import ca.uhn.hl7v2.model.v24.datatype.CE;
import ca.uhn.hl7v2.model.v24.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v24.group.ORU_R01_PATIENT;
import ca.uhn.hl7v2.model.v24.message.ORU_R01;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.OBR;
import ca.uhn.hl7v2.model.v24.segment.OBX;
import ca.uhn.hl7v2.model.v24.segment.PID;
import ca.uhn.hl7v2.model.v25.datatype.NM;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kemricdc.entities.Person;
import org.kemricdc.entities.PersonIdentifier;
import org.kemricdc.utils.AppProperties;

/**
 *
 * @author Stanslaus Odhiambo
 */
/**
 *
 * Example code for populating an OBX segment
 *
 * @author <a * href="mailto:soomondi@kemricdc.org">Stanslaus Odhiambo</a>
 *
 * @version $Revision: 1.1 $ updated on $Date: 2014-12-08 13:09:26 $ by $Author:
 * stanslaus $
 */
public class ProcessTransactions {

    List<OruFiller> fillers;
    private final Person person;

    public ProcessTransactions(Person person, List<OruFiller> fillers) {
        this.person = person;
        this.fillers = fillers;

    }

    /**
     *
     *
     * The following oru_r01 snippet is drawn (and modified for simplicity) from
     * section 7.4.2.4 of the HL7 2.5 specification.
     *
     * In an ORU oru_r01, the OBR segment is used as a report header and
     * contains important information about the order being fulfilled (i.e.
     * order number, request date/time, observation date/time, ordering
     * provider, etc.). It is part of a group that can be used more than once
     * for each observation result that is reported in the oru_r01.
     *
     * <code>
     * OBR|1||1234^LAB|88304
     * OBX|1|CE|88304|1|T57000^GALLBLADDER^SNM
     * OBX|2|TX|88304|1|THIS IS A NORMAL GALLBLADDER
     * OBX|3|TX|88304&MDT|1|MICROSCOPIC EXAM SHOWS HISTOLOGICALLY NORMAL GALLBLADDER TISSUE
     * </code>
     *
     * The following code attempts to generate this oru_r01 structure.
     *
     * The HL7 spec defines, the following structure for an ORU^R01 oru_r01,
     * represented in HAPI by the segment group:
     *
     * <code>
     *                     ORDER_OBSERVATION start
     *       {
     *       [ ORC ]
     *       OBR
     *       [ { NTE } ]
     *                     TIMING_QTY start
     *          [{
     *          TQ1
     *          [ { TQ2 } ]
     *          }]
     *                     TIMING_QTY end
     *       [ CTD ]
     *                     OBSERVATION start
     *          [{
     *          OBX
     *          [ { NTE } ]
     *          }]
     *                     OBSERVATION end
     *       [ { FT1 } ]
     *       [ { CTI } ]
     *                     SPECIMEN start
     *          [{
     *          SPM
     *          [ { OBX } ]
     *          }]
     *                     SPECIMEN end
     *       }
     *                     ORDER_OBSERVATION end
     * </code>
     *
     * @return the encoded ORU^R01 oru_r01
     * @throws HL7Exception If any processing problem occurs
     * @throws IOException
     */
    public String generateORU() throws HL7Exception, IOException {
        // First, a oru_r01 object is constructed
        ORU_R01 oru_r01 = new ORU_R01();

        /*
         * The initQuickstart method populates all of the mandatory fields in the
         * MSH segment of the oru_r01, including the oru_r01 type, the timestamp,
         * and the control ID.
         */
        oru_r01.initQuickstart(AppProperties.getProperty("event_message_type"),
                AppProperties.getProperty("event_trigger_event"),
                AppProperties.getProperty("event_processing_id"));

        MSH msh = oru_r01.getMSH();
        msh.getSendingApplication().getNamespaceID().setValue(AppProperties.getProperty("sending_application"));
        msh.getSendingFacility().getNamespaceID().setValue(AppProperties.getProperty("sending_facility"));

        //populate the receiving application details
        msh.getReceivingApplication().getNamespaceID().setValue(AppProperties.getProperty("receiving_station"));
        msh.getReceivingFacility().getNamespaceID().setValue(AppProperties.getProperty("receiving_application"));
        msh.getSequenceNumber().setValue(AppProperties.getProperty("sequence_number"));

        ORU_R01_PATIENT oruPatient = oru_r01.getPATIENT_RESULT().getPATIENT();
        PID pid = oruPatient.getPID();
        pid.getDateTimeOfBirth().getTimeOfAnEvent().setValue(person.getBirthdate());
        pid.getPatientName(0).getFamilyName().getSurname().setValue(person.getLastName());
        pid.getPatientName(0).getGivenName().setValue(person.getFirstName());
        pid.getPatientName(0).getSecondAndFurtherGivenNamesOrInitialsThereof().setValue(person.getMiddleName());
        
        
        pid.getAdministrativeSex().setValue(person.getSex().getValue());
        pid.getMaritalStatus().getCe1_Identifier().setValue(person.getMaritalStatus().getValue());

        //Changed to the below to handle scenario where the patient has more than one identifier
        Set<PersonIdentifier> identifiers = person.getPersonIdentifiers();
        int count = 0;
        for (PersonIdentifier personIdentifier : identifiers) {
            pid.getPatientIdentifierList(count).getID().setValue(personIdentifier.getIdentifier());
            pid.getPatientIdentifierList(count).getIdentifierTypeCode().setValue(personIdentifier.getIdentifierType().getValue());
            count++;
        }

        /*
         * The OBR segment is contained within a group called ORDER_OBSERVATION, 
         * which is itself in a group called PATIENT_RESULT. These groups are
         * reached using named accessors.
         */
        ORU_R01_ORDER_OBSERVATION orderObservation = oru_r01.getPATIENT_RESULT().getORDER_OBSERVATION();

        // Populate the OBR
        OBR obr = orderObservation.getOBR();
        obr.getSetIDOBR().setValue(AppProperties.getProperty("obr_id"));
        obr.getFillerOrderNumber().getEntityIdentifier().setValue(AppProperties.getProperty("facility_mfl_code"));
        obr.getFillerOrderNumber().getNamespaceID().setValue(AppProperties.getProperty("facility_name"));

        obr.getUniversalServiceIdentifier().getIdentifier().setValue(AppProperties.getProperty("facility_mfl_code"));
        OBX obx = null;
        Varies value;

        for (int i = 0; i < fillers.size(); i++) {
            OruFiller oruFiller = fillers.get(i);
            /*
             * The OBX segment is in a repeating group called OBSERVATION. You can 
             * use a named accessor which takes an index to access a specific 
             * repetition. You can ask for an index which is equal to the 
             * current number of repetitions,and a new repetition will be created.
             */
//            ORU_R01_OBSERVATION observation = orderObservation.getOBSERVATION(0);

            // Populate the OBXs
            obx = orderObservation.getOBSERVATION(i).getOBX();
            obx.getSetIDOBX().setValue((i+1)+"");

            //We are working with a fixed value of ST  - pretty much works for us
            obx.getValueType().setValue("ST");

            //Form the Observation Identifier
            obx.getObservationIdentifier().getIdentifier().setValue(oruFiller.getObservationIdentifier());
            obx.getObservationIdentifier().getText().setValue(oruFiller.getObservationIdentifierText());
            obx.getObservationIdentifier().getNameOfCodingSystem().setValue(oruFiller.getCodingSystem());

            //Form the Observation Sub-ID if necessary
            obx.getObservationSubId().setValue(oruFiller.getObservationSubId());

            //Form the Observation Value
            NM nm = new NM(oru_r01);
            nm.setValue(oruFiller.getObservationValue());
            value = obx.getObservationValue(0);
            value.setData(nm);

            //Form the Units
            obx.getUnits().getText().setValue(oruFiller.getUnits());

            //Form References Range
            obx.getReferencesRange().setValue(oruFiller.getReferencesRange());

            //Form the Abnormal Flags
            obx.getAbnormalFlags().setValue(oruFiller.getAbnormalFlags());

            //Form the Probability
            obx.getProbability(0).setValue(oruFiller.getProbability());

            //Form Nature of Abnormal Test
            obx.getNatureOfAbnormalTest().setValue(oruFiller.getNatureOfAbnormalTest());

            //Form Result Status
            obx.getObservationResultStatus().setValue(oruFiller.getResultStatus());

            //Form the Date of Last Normal Values
            obx.getDateLastObservationNormalValue().getTimeOfAnEvent().setValue(oruFiller.getDateOfLastNormalValue());

            //set the User Defined Access Checks if necessary
            obx.getUserDefinedAccessChecks().setValue(oruFiller.getUserDefinedAccessChecks());

            //set Date/Time of the Observation
            obx.getDateTimeOfTheObservation().getTimeOfAnEvent().setValue(oruFiller.getDateTimeOfObservation());

            //set the Producer's ID
            obx.getProducerSID().getText().setValue(oruFiller.getProducerId());

            //form the Responsible Observer
            obx.getResponsibleObserver().getIDNumber().setValue(oruFiller.getResponsibleObserverId());
            obx.getResponsibleObserver().getGivenName().setValue(oruFiller.getResponsibleObserverGivenName());

            //Form the Observation Method
            obx.getObservationMethod(0).getText().setValue(oruFiller.getObservationMethod());

//            
//            
//            ST observationIdentifier = obx.getObservationIdentifier().getIdentifier();
//            observationIdentifier.setValue(properties.getProperty("facility_mfl_code"));
////            obx.getObservationIdentifier().getIdentifier().setValue(properties.getProperty("facility_mfl_code"));
//            obx.getObservationSubId().setValue(properties.getProperty("application_code"));
//            switch (oruFiller.getValueType()) {
//                case CE: {
//                    // The OBX has a value type of CE. So first, we populate OBX-2 with "CE"...
//                    obx.getValueType().setValue("CE");
//                    // ... then we create a CE instance to put in OBX-5.
//                    CE ce = new CE(oru_r01);
//                    ce.getNameOfCodingSystem().setValue(properties.getProperty("coding_system"));
//                    value = obx.getObservationValue(0);
//                    populateCeObx(obx, ce, (i + 1) + "", oruFiller.getIdentifier(), oruFiller.getSubId(), oruFiller.getCeValue());
//                    value.setData(ce);
//                    break;
//                }
//                case TX: {
//                            // The second OBX in the sample oru_r01 has an extra subcomponent at
//                    // OBX-3-1. This component is actually an ST, but the HL7 specification allows
//                    // extra subcomponents to be tacked on to the end of a component. This is
//                    // uncommon, but HAPI nontheless allows it.
////                    Already set the value up above
////                    observationIdentifier.setValue("88304");
//                    ST extraSubcomponent = new ST(oru_r01);
//                    extraSubcomponent.setValue("MDT");
//                    observationIdentifier.getExtraComponents().getComponent(0).setData(extraSubcomponent);
//
//                    // The first OBX has a value type of TX. So first, we populate OBX-2 with "TX"...
//                    obx.getValueType().setValue("TX");
//
//                    // ... then we create a TX instance to put in OBX-5.
//                    TX tx = new TX(oru_r01);
//                    tx.setValue(oruFiller.getTxValue());
//
//                    value = obx.getObservationValue(0);
//                    value.setData(tx);
//
//                    break;
//                }
//            }
        }

        // Print the oru_r01 (remember, the MSH segment was not fully or correctly populated)
        String finalString = oru_r01.encode();
        System.out.println(finalString);

        /*Something close to this would be returned
         *
         * MSH|^~\&|||||20111102082111.435-0500||ORU^R01^ORU_R01|305|T|2.5
         * OBR|1||1234^LAB|88304
         * OBX|1|CE|88304|1|T57000^GALLBLADDER^SNM
         * OBX|2|TX|88304&MDT|1|MICROSCOPIC EXAM SHOWS HISTOLOGICALLY NORMAL GALLBLADDER TISSUE
         */
        return finalString;

    }

}
