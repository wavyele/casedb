casedb
======

The case based surveillance DB wmb file. Any new updates to the file need to be synchronized with this one
The Repository has the following Modules:

  1.  HapiModuleV1 - A utility module that aids the creation and sending of HL7 messages by other modules
  2.  HIVCaseBasedAccessModule  - Mine events from CPAD EMR and uses the HapiModuleV1 to generate appropriate HL7 Messages which are then sent to the Mirth Server
