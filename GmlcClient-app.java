/**
 *
 */
package org.mk.jss7.gmlc.app.test;

import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;
import org.mobicents.protocols.api.IpChannelType;
import org.mobicents.protocols.sctp.ManagementImpl;
import org.mobicents.protocols.ss7.m3ua.ExchangeType;
import org.mobicents.protocols.ss7.m3ua.Functionality;
import org.mobicents.protocols.ss7.m3ua.IPSPType;
import org.mobicents.protocols.ss7.m3ua.Asp;
import org.mobicents.protocols.ss7.m3ua.impl.M3UAManagementImpl;
import org.mobicents.protocols.ss7.m3ua.parameter.NetworkAppearance;
import org.mobicents.protocols.ss7.m3ua.parameter.RoutingContext;
import org.mobicents.protocols.ss7.m3ua.parameter.TrafficModeType;
import org.mobicents.protocols.ss7.map.MAPStackImpl;
import org.mobicents.protocols.ss7.map.api.MAPApplicationContext;
import org.mobicents.protocols.ss7.map.api.MAPApplicationContextName;
import org.mobicents.protocols.ss7.map.api.MAPApplicationContextVersion;
import org.mobicents.protocols.ss7.map.api.MAPDialog;
import org.mobicents.protocols.ss7.map.api.MAPException;
import org.mobicents.protocols.ss7.map.api.MAPMessage;
import org.mobicents.protocols.ss7.map.api.MAPProvider;
import org.mobicents.protocols.ss7.map.api.dialog.MAPAbortProviderReason;
import org.mobicents.protocols.ss7.map.api.dialog.MAPAbortSource;
import org.mobicents.protocols.ss7.map.api.dialog.MAPNoticeProblemDiagnostic;
import org.mobicents.protocols.ss7.map.api.dialog.MAPProviderError;
import org.mobicents.protocols.ss7.map.api.dialog.MAPRefuseReason;
import org.mobicents.protocols.ss7.map.api.dialog.MAPUserAbortChoice;
import org.mobicents.protocols.ss7.map.api.errors.MAPErrorMessage;
import org.mobicents.protocols.ss7.map.api.primitives.AddressNature;
import org.mobicents.protocols.ss7.map.api.primitives.AddressString;
import org.mobicents.protocols.ss7.map.api.primitives.GSNAddress;
import org.mobicents.protocols.ss7.map.api.primitives.IMEI;
import org.mobicents.protocols.ss7.map.api.primitives.IMSI;
import org.mobicents.protocols.ss7.map.api.primitives.ISDNAddressString;
import org.mobicents.protocols.ss7.map.api.primitives.LMSI;
import org.mobicents.protocols.ss7.map.api.primitives.MAPExtensionContainer;
import org.mobicents.protocols.ss7.map.api.primitives.MAPPrivateExtension;
import org.mobicents.protocols.ss7.map.api.primitives.NumberingPlan;
import org.mobicents.protocols.ss7.map.api.primitives.USSDString;
import org.mobicents.protocols.ss7.map.api.service.lsm.Area;
import org.mobicents.protocols.ss7.map.api.service.lsm.AreaEventInfo;
import org.mobicents.protocols.ss7.map.api.service.lsm.AreaType;
import org.mobicents.protocols.ss7.map.api.service.lsm.LCSClientID;
import org.mobicents.protocols.ss7.map.api.service.lsm.LCSClientInternalID;
import org.mobicents.protocols.ss7.map.api.service.lsm.LCSClientType;
import org.mobicents.protocols.ss7.map.api.service.lsm.LCSCodeword;
import org.mobicents.protocols.ss7.map.api.service.lsm.LCSPriority;
import org.mobicents.protocols.ss7.map.api.service.lsm.LCSPrivacyCheck;
import org.mobicents.protocols.ss7.map.api.service.lsm.LCSQoS;
import org.mobicents.protocols.ss7.map.api.service.lsm.LocationEstimateType;
import org.mobicents.protocols.ss7.map.api.service.lsm.LocationType;
import org.mobicents.protocols.ss7.map.api.service.lsm.MAPDialogLsm;
import org.mobicents.protocols.ss7.map.api.service.lsm.OccurrenceInfo;
import org.mobicents.protocols.ss7.map.api.service.lsm.PeriodicLDRInfo;
import org.mobicents.protocols.ss7.map.api.service.lsm.PrivacyCheckRelatedAction;
import org.mobicents.protocols.ss7.map.api.service.lsm.ProvideSubscriberLocationRequest;
import org.mobicents.protocols.ss7.map.api.service.lsm.ProvideSubscriberLocationResponse;
import org.mobicents.protocols.ss7.map.api.service.lsm.RANTechnology;
import org.mobicents.protocols.ss7.map.api.service.lsm.ReportingPLMN;
import org.mobicents.protocols.ss7.map.api.service.lsm.ReportingPLMNList;
import org.mobicents.protocols.ss7.map.api.service.lsm.SendRoutingInfoForLCSRequest;
import org.mobicents.protocols.ss7.map.api.service.lsm.SendRoutingInfoForLCSResponse;
import org.mobicents.protocols.ss7.map.api.service.lsm.SubscriberLocationReportRequest;
import org.mobicents.protocols.ss7.map.api.service.lsm.SubscriberLocationReportResponse;
import org.mobicents.protocols.ss7.map.api.service.lsm.SupportedGADShapes;
import org.mobicents.protocols.ss7.map.api.service.supplementary.MAPDialogSupplementary;
import org.mobicents.protocols.ss7.map.api.service.supplementary.MAPServiceSupplementaryListener;
import org.mobicents.protocols.ss7.map.api.service.supplementary.ProcessUnstructuredSSRequest;
import org.mobicents.protocols.ss7.map.api.service.supplementary.ProcessUnstructuredSSResponse;
import org.mobicents.protocols.ss7.map.api.service.supplementary.UnstructuredSSNotifyRequest;
import org.mobicents.protocols.ss7.map.api.service.supplementary.UnstructuredSSNotifyResponse;
import org.mobicents.protocols.ss7.map.api.service.supplementary.UnstructuredSSRequest;
import org.mobicents.protocols.ss7.map.api.service.supplementary.UnstructuredSSResponse;
import org.mobicents.protocols.ss7.map.datacoding.CBSDataCodingSchemeImpl;
import org.mobicents.protocols.ss7.map.primitives.MAPPrivateExtensionImpl;
import org.mobicents.protocols.ss7.map.primitives.PlmnIdImpl;
import org.mobicents.protocols.ss7.map.service.lsm.AreaIdentificationImpl;
import org.mobicents.protocols.ss7.map.service.lsm.AreaImpl;
import org.mobicents.protocols.ss7.map.service.lsm.ReportingPLMNImpl;
import org.mobicents.protocols.ss7.sccp.impl.SccpResource;
import org.mobicents.protocols.ss7.sccp.impl.SccpStackImpl;
import org.mobicents.protocols.ss7.tcap.TCAPStackImpl;
import org.mobicents.protocols.ss7.tcap.api.TCAPStack;
import org.mobicents.protocols.ss7.tcap.asn.ApplicationContextName;
import org.mobicents.protocols.ss7.tcap.asn.comp.Problem;

/**
 * @author 
 *
 */
public class SctpClient extends AbstractSctpBase {

      private static Logger logger = Logger.getLogger(SctpClient.class);

      // SCTP
      private ManagementImpl sctpManagement;

      // M3UA
      private M3UAManagementImpl clientM3UAMgmt;

      // SCCP
      private SccpStackImpl sccpStack;
      private SccpResource sccpResource;

      // TCAP
      private TCAPStack tcapStack;

      // MAP
      private MAPStackImpl mapStack;
      private MAPProvider mapProvider;

      public String AS_NAME =  "mk";
      public String ASP_NAME = "mkd";
      protected final int SSN = 145;  
      /**
       *
       */
      public SctpClient() {
            // TODO Auto-generated constructor stub
      }

      protected void initializeStack(IpChannelType ipChannelType) throws Exception {

            this.initSCTP(ipChannelType);

            // Initialize M3UA first
            this.initM3UA();

            // Initialize SCCP
            this.initSCCP();

            // Initialize TCAP
            this.initTCAP();

            // Initialize MAP
            this.initMAP();

            // Set 5: Finally start ASP
            this.clientM3UAMgmt.startAsp(ASP_NAME);
      }

      private void initSCTP(IpChannelType ipChannelType) throws Exception {
            logger.debug("Initializing SCTP Stack ....");
            this.sctpManagement = new ManagementImpl("Client");
            this.sctpManagement.setSingleThread(true);
            this.sctpManagement.setConnectDelay(10000);
            this.sctpManagement.start();
            this.sctpManagement.removeAllResourses();

            // 1. Create SCTP Association
            sctpManagement.addAssociation(CLIENT_IP, CLIENT_PORT, SERVER_IP, SERVER_PORT, CLIENT_ASSOCIATION_NAME);
            logger.debug("Initialized SCTP Stack ....");
      }

      private void initM3UA() throws Exception {
            logger.debug("Initializing M3UA Stack ....");
            this.clientM3UAMgmt = new M3UAManagementImpl("Client");
            this.clientM3UAMgmt.setTransportManagement(this.sctpManagement);
            this.clientM3UAMgmt.start();
            this.clientM3UAMgmt.removeAllResourses();

            // m3ua as create rc <rc> <ras-name>
            RoutingContext rc = factory.createRoutingContext(new long[]{ROUTING_CONTEXT});     
            NetworkAppearance na = factory.createNetworkAppearance(NETWORK_APPEARANCE);    
            TrafficModeType trafficModeType = factory.createTrafficModeType(TrafficModeType.Loadshare);

            this.clientM3UAMgmt.createAs(AS_NAME, Functionality.AS, ExchangeType.SE, IPSPType.CLIENT, rc, trafficModeType, 1, na); 

            // Step 2 : Create ASP     
            this.clientM3UAMgmt.createAspFactory(ASP_NAME, CLIENT_ASSOCIATION_NAME);
            // Step 3 : Assign ASP to AS    
            Asp asp = this.clientM3UAMgmt.assignAspToAs(AS_NAME, ASP_NAME);
            // Step 4 : Add Route. Remote point code is 2    
            clientM3UAMgmt.addRoute(SERVET_SPC, CLIENT_SPC, SERVICE_INIDCATOR, AS_NAME);   // (dpc, opc, si, asName)

            logger.debug("Initialized M3UA Stack ....");
      }

      private void initSCCP() throws Exception {
            logger.debug("Initializing SCCP Stack ....");
            this.sccpStack = new SccpStackImpl("MapLoadClientSccpStack");
            this.sccpStack.setMtp3UserPart(1, this.clientM3UAMgmt);
            this.sccpStack.start();
            this.sccpStack.removeAllResourses();

            this.sccpStack.getSccpResource().addRemoteSpc(0, SERVET_SPC, 0, 0);  
            this.sccpStack.getSccpResource().addRemoteSsn(0, SERVET_SPC, SSN2, 0, false);
            this.sccpStack.getRouter().addMtp3ServiceAccessPoint(1, 1, CLIENT_SPC, NETWORK_INDICATOR);
            //                          (sapId, destId, firstDpc, lastDpc, firstSls, lastSls, slsMask)
            this.sccpStack.getRouter().addMtp3Destination(1, 1, SERVET_SPC, SERVET_SPC, 0, 255, 255);

            logger.debug("Initialized SCCP Stack ....");
      }

      private void initTCAP() {
            logger.debug("Initializing TCAP Stack ....");
            this.tcapStack = new TCAPStackImpl(this.sccpStack.getSccpProvider(), SSN);
            this.tcapStack.setDialogIdleTimeout(60000);
            this.tcapStack.setInvokeTimeout(30000);
            this.tcapStack.setMaxDialogs(2000);
            this.tcapStack.start();
            logger.debug("Initialized TCAP Stack ....");
      }

      private void initMAP() {
            logger.debug("Initializing MAP Stack ....");

            this.mapStack = new MAPStackImpl(this.sccpStack.getSccpProvider(), SSN);
            this.mapStack = new MAPStackImpl(this.tcapStack.getProvider());
            this.mapProvider = this.mapStack.getMAPProvider();

            this.mapProvider.addMAPDialogListener(this);
            this.mapProvider.getMAPServiceLsm().addMAPServiceListener(this);
            this.mapProvider.getMAPServiceLsm().acivate();
            this.mapStack.start();

            logger.debug("Initialized MAP Stack ....");
      }

      
      private void initiateLSM() throws MAPException {
            MAPDialogLsm mapDialog = this.mapProvider.getMAPServiceLsm().createNewDialog(
                    MAPApplicationContext.getInstance(MAPApplicationContextName.locationSvcEnquiryContext,
                            MAPApplicationContextVersion.version3), SCCP_CLIENT_ADDRESS, null, SCCP_SERVER_ADDRESS, null);
             
            LocationType locationType = this.mapProvider.getMAPParameterFactory().
                               createLocationType(LocationEstimateType.currentLocation, null);
          
            ISDNAddressString msisdn = this.mapProvider.getMAPParameterFactory().createISDNAddressString(
                       AddressNature.international_number, NumberingPlan.ISDN, "380630010685");
           
            LCSClientID lcsClientID = this.mapProvider.getMAPParameterFactory().
                       createLCSClientID(LCSClientType.lawfulInterceptServices, null, LCSClientInternalID.oandMHPLMN, null, msisdn, null, null);
           
            IMSI imsi = this.mapProvider.getMAPParameterFactory().createIMSI("255061093724535");    //  imsi
            
            ISDNAddressString msisdn1 = this.mapProvider.getMAPParameterFactory().createISDNAddressString(
                       AddressNature.international_number, NumberingPlan.ISDN, "380631234567");  // msisdn
            
         
            LMSI lmsi = this.mapProvider.getMAPParameterFactory().createLMSI(new byte[]{0, 0, 0, 0});
      
            IMEI imei = this.mapProvider.getMAPParameterFactory().createIMEI("356342043968870");    //  imei
           
            LCSQoS lcsQoS = this.mapProvider.getMAPParameterFactory().createLCSQoS(1, 1, true, null, null);
            
            MAPPrivateExtension extension = new MAPPrivateExtensionImpl(new long[]{0, 0, 0, 0}, new byte[]{1});
            ArrayList<MAPPrivateExtension> extensions = new ArrayList<MAPPrivateExtension>();
            extensions.add(extension);
            MAPExtensionContainer extensionContainer = this.mapProvider.getMAPParameterFactory().
                       createMAPExtensionContainer(extensions, new byte[]{0});
            
            SupportedGADShapes supportedGADShapes = this.mapProvider.getMAPParameterFactory().
                       createSupportedGADShapes(true, true, true, true, true, true, true);
            
            USSDString ussdString = this.mapProvider.getMAPParameterFactory().createUSSDString("1");
            LCSCodeword lcsCodeword = this.mapProvider.getMAPParameterFactory().
                       createLCSCodeword(new CBSDataCodingSchemeImpl(0x0f), ussdString);
            
            LCSPrivacyCheck lcsPrivacyCheck = this.mapProvider.getMAPParameterFactory().
                       createLCSPrivacyCheck(PrivacyCheckRelatedAction.notAllowed, PrivacyCheckRelatedAction.notAllowed);
            
            Area area = new AreaImpl(AreaType.plmnId, new AreaIdentificationImpl(new byte[]{0, 0}));
            ArrayList<Area> areas = new ArrayList<Area>();
            areas.add(area);
            AreaEventInfo areaEventInfo = this.mapProvider.getMAPParameterFactory().
                       createAreaEventInfo(this.mapProvider.getMAPParameterFactory().createAreaDefinition(areas),
                                   OccurrenceInfo.oneTimeEvent, null);
            
            GSNAddress gsna = this.mapProvider.getMAPParameterFactory().createGSNAddress(new byte[]{0, 0, 0, 0, 0});
            
            PeriodicLDRInfo pldri = this.mapProvider.getMAPParameterFactory().createPeriodicLDRInfo(0, 0);
            
                  ArrayList<ReportingPLMN> reportingPLMNs = new ArrayList<ReportingPLMN>();
                  ReportingPLMNImpl reportingPLMNImpl = new ReportingPLMNImpl(new PlmnIdImpl(0, 0), RANTechnology.gsm, true);
                 reportingPLMNs.add(reportingPLMNImpl);
            ReportingPLMNList rplmnl = this.mapProvider.getMAPParameterFactory().createReportingPLMNList(true, reportingPLMNs);
            
            mapDialog.addProvideSubscriberLocationRequest(
                    locationType,
                    msisdn,
                    lcsClientID,
                    true,
                    imsi,
                    msisdn1,
                    lmsi,
                    imei,
                    LCSPriority.normalPriority,
                    lcsQoS,
                    null,   //  extensionContainer,
                    supportedGADShapes,
                    0,
                    0,
                    lcsCodeword,
                    lcsPrivacyCheck,
                    areaEventInfo,
                    gsna,
                    true,
                    pldri,
                    rplmnl);
            mapDialog.send();
      }

      /*
       * (non-Javadoc)
       * 
       */
      
      public static void main(String args[]) {
            System.out.println("*************************************");
            System.out.println("***          SctpClient           ***");
            System.out.println("*************************************");
            
            System.out.println(new Date());
            IpChannelType ipChannelType = IpChannelType.SCTP;
            if (args.length >= 1 && args[0].toLowerCase().equals("tcp")) {
                  ipChannelType = IpChannelType.TCP;
            }

            final SctpClient client = new SctpClient();

            try {
                  client.initializeStack(ipChannelType);

                  // Lets pause for 20 seconds so stacks are initialized properly
                  Thread.sleep(20000);
                  //client.initiateUSSD();

                  client.initiateLSM();

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      public void onDialogAccept(MAPDialog mapDialog, MAPExtensionContainer extensionContainer) {
            if (logger.isDebugEnabled()) {
                  logger.debug(String.format("onDialogAccept for DialogId=%d MAPExtensionContainer=%s",
                          mapDialog.getDialogId(), extensionContainer));
            }
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.mobicents.protocols.ss7.map.api.MAPDialogListener#onDialogClose(org
       * .mobicents.protocols.ss7.map.api.MAPDialog)
       */
      public void onDialogClose(MAPDialog mapDialog) {
            if (logger.isDebugEnabled()) {
                  logger.debug(String.format("DialogClose for Dialog=%d", mapDialog.getDialogId()));
            }
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.mobicents.protocols.ss7.map.api.MAPDialogListener#onDialogDelimiter
       * (org.mobicents.protocols.ss7.map.api.MAPDialog)
       */
      public void onDialogDelimiter(MAPDialog mapDialog) {
            if (logger.isDebugEnabled()) {
                  logger.debug(String.format("onDialogDelimiter for DialogId=%d", mapDialog.getDialogId()));
            }
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.mobicents.protocols.ss7.map.api.MAPDialogListener#onDialogNotice(
       * org.mobicents.protocols.ss7.map.api.MAPDialog,
       * org.mobicents.protocols.ss7.map.api.dialog.MAPNoticeProblemDiagnostic)
       */
      public void onDialogNotice(MAPDialog mapDialog, MAPNoticeProblemDiagnostic noticeProblemDiagnostic) {
            logger.error(String.format("onDialogNotice for DialogId=%d MAPNoticeProblemDiagnostic=%s ",
                    mapDialog.getDialogId(), noticeProblemDiagnostic));
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.mobicents.protocols.ss7.map.api.MAPDialogListener#onDialogProviderAbort
       * (org.mobicents.protocols.ss7.map.api.MAPDialog,
       * org.mobicents.protocols.ss7.map.api.dialog.MAPAbortProviderReason,
       * org.mobicents.protocols.ss7.map.api.dialog.MAPAbortSource,
       * org.mobicents.protocols.ss7.map.api.primitives.MAPExtensionContainer)
       */
      public void onDialogProviderAbort(MAPDialog mapDialog, MAPAbortProviderReason abortProviderReason,
              MAPAbortSource abortSource, MAPExtensionContainer extensionContainer) {
            logger.error(String
                    .format("onDialogProviderAbort for DialogId=%d MAPAbortProviderReason=%s MAPAbortSource=%s MAPExtensionContainer=%s",
                            mapDialog.getDialogId(), abortProviderReason, abortSource, extensionContainer));
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.mobicents.protocols.ss7.map.api.MAPDialogListener#onDialogReject(
       * org.mobicents.protocols.ss7.map.api.MAPDialog,
       * org.mobicents.protocols.ss7.map.api.dialog.MAPRefuseReason,
       * org.mobicents.protocols.ss7.map.api.dialog.MAPProviderError,
       * org.mobicents.protocols.ss7.tcap.asn.ApplicationContextName,
       * org.mobicents.protocols.ss7.map.api.primitives.MAPExtensionContainer)
       */
      public void onDialogReject(MAPDialog mapDialog, MAPRefuseReason refuseReason, MAPProviderError providerError,
              ApplicationContextName alternativeApplicationContext, MAPExtensionContainer extensionContainer) {
            logger.error(String
                  .format("onDialogReject for DialogId=%d MAPRefuseReason=%s MAPProviderError=%s ApplicationContextName=%s MAPExtensionContainer=%s",
                          mapDialog.getDialogId(), refuseReason, providerError, alternativeApplicationContext, extensionContainer));
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.mobicents.protocols.ss7.map.api.MAPDialogListener#onDialogRelease
       * (org.mobicents.protocols.ss7.map.api.MAPDialog)
       */
      public void onDialogRelease(MAPDialog mapDialog) {
            if (logger.isDebugEnabled()) {
                  logger.debug(String.format("onDialogResease for DialogId=%d", mapDialog.getDialogId()));
            }
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.mobicents.protocols.ss7.map.api.MAPDialogListener#onDialogRequest
       * (org.mobicents.protocols.ss7.map.api.MAPDialog,
       * org.mobicents.protocols.ss7.map.api.primitives.AddressString,
       * org.mobicents.protocols.ss7.map.api.primitives.AddressString,
       * org.mobicents.protocols.ss7.map.api.primitives.MAPExtensionContainer)
       */
      public void onDialogRequest(MAPDialog mapDialog, AddressString destReference, AddressString origReference,
              MAPExtensionContainer extensionContainer) {
            if (logger.isDebugEnabled()) {
                  logger.debug(String
                          .format("onDialogRequest for DialogId=%d DestinationReference=%s OriginReference=%s MAPExtensionContainer=%s",
                                  mapDialog.getDialogId(), destReference, origReference, extensionContainer));
            }
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.mobicents.protocols.ss7.map.api.MAPDialogListener#onDialogRequestEricsson
       * (org.mobicents.protocols.ss7.map.api.MAPDialog,
       * org.mobicents.protocols.ss7.map.api.primitives.AddressString,
       * org.mobicents.protocols.ss7.map.api.primitives.AddressString,
       * org.mobicents.protocols.ss7.map.api.primitives.IMSI,
       * org.mobicents.protocols.ss7.map.api.primitives.AddressString)
       */
      public void onDialogRequestEricsson(MAPDialog mapDialog, AddressString destReference, AddressString origReference,
              IMSI arg3, AddressString arg4) {
            if (logger.isDebugEnabled()) {
                  logger.debug(String.format("onDialogRequest for DialogId=%d DestinationReference=%s OriginReference=%s ",
                          mapDialog.getDialogId(), destReference, origReference));
            }
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.mobicents.protocols.ss7.map.api.MAPDialogListener#onDialogTimeout
       * (org.mobicents.protocols.ss7.map.api.MAPDialog)
       */
      public void onDialogTimeout(MAPDialog mapDialog) {
            logger.error(String.format("onDialogTimeout for DialogId=%d", mapDialog.getDialogId()));
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.mobicents.protocols.ss7.map.api.MAPDialogListener#onDialogUserAbort
       * (org.mobicents.protocols.ss7.map.api.MAPDialog,
       * org.mobicents.protocols.ss7.map.api.dialog.MAPUserAbortChoice,
       * org.mobicents.protocols.ss7.map.api.primitives.MAPExtensionContainer)
       */
      public void onDialogUserAbort(MAPDialog mapDialog, MAPUserAbortChoice userReason,
              MAPExtensionContainer extensionContainer) {
            logger.error(String.format("onDialogUserAbort for DialogId=%d MAPUserAbortChoice=%s MAPExtensionContainer=%s",
                    mapDialog.getDialogId(), userReason, extensionContainer));
      }

      /*
       * (non-Javadoc)
       * 
       * @see org.mobicents.protocols.ss7.map.api.service.supplementary.
       * MAPServiceSupplementaryListener
       * #onProcessUnstructuredSSRequest(org.mobicents
       * .protocols.ss7.map.api.service
       * .supplementary.ProcessUnstructuredSSRequest)
       */
      public void onProcessUnstructuredSSRequest(ProcessUnstructuredSSRequest procUnstrReqInd) {
        // This error condition. Client should never receive the
            // ProcessUnstructuredSSRequestIndication
            logger.error(String.format("onProcessUnstructuredSSRequestIndication for Dialog=%d and invokeId=%d",
                    procUnstrReqInd.getMAPDialog().getDialogId(), procUnstrReqInd.getInvokeId()));
      }

      /*
       * (non-Javadoc)
       * 
       * @see org.mobicents.protocols.ss7.map.api.service.supplementary.
       * MAPServiceSupplementaryListener
       * #onProcessUnstructuredSSResponse(org.mobicents
       * .protocols.ss7.map.api.service
       * .supplementary.ProcessUnstructuredSSResponse)
       */
      public void onProcessUnstructuredSSResponse(ProcessUnstructuredSSResponse procUnstrResInd) {
            if (logger.isDebugEnabled()) {
                  try {
                        logger.debug(String.format("Rx ProcessUnstructuredSSResponseIndication.  USSD String=%s",
                                procUnstrResInd.getUSSDString().getString(null)));
                  } catch (MAPException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                  }
            }
      }

      /*
       * (non-Javadoc)
       * 
       * @see org.mobicents.protocols.ss7.map.api.service.supplementary.
       * MAPServiceSupplementaryListener
       * #onUnstructuredSSNotifyRequest(org.mobicents
       * .protocols.ss7.map.api.service.supplementary.UnstructuredSSNotifyRequest)
       */
      public void onUnstructuredSSNotifyRequest(UnstructuredSSNotifyRequest unstrNotifyInd) {
        // This error condition. Client should never receive the
            // UnstructuredSSNotifyRequestIndication
            logger.error(String.format("onUnstructuredSSNotifyRequestIndication for Dialog=%d and invokeId=%d",
                    unstrNotifyInd.getMAPDialog().getDialogId(), unstrNotifyInd.getInvokeId()));
      }

      /*
       * (non-Javadoc)
       * 
       * @see org.mobicents.protocols.ss7.map.api.service.supplementary.
       * MAPServiceSupplementaryListener
       * #onUnstructuredSSNotifyResponse(org.mobicents
       * .protocols.ss7.map.api.service
       * .supplementary.UnstructuredSSNotifyResponse)
       */
      public void onUnstructuredSSNotifyResponse(UnstructuredSSNotifyResponse unstrNotifyInd) {
        // This error condition. Client should never receive the
            // UnstructuredSSNotifyRequestIndication
            logger.error(String.format("onUnstructuredSSNotifyResponseIndication for Dialog=%d and invokeId=%d",
                    unstrNotifyInd.getMAPDialog().getDialogId(), unstrNotifyInd.getInvokeId()));
      }

      /*
       * (non-Javadoc)
       * 
       * @see org.mobicents.protocols.ss7.map.api.service.supplementary.
       * MAPServiceSupplementaryListener
       * #onUnstructuredSSRequest(org.mobicents.protocols
       * .ss7.map.api.service.supplementary.UnstructuredSSRequest)
       */
      public void onUnstructuredSSRequest(UnstructuredSSRequest unstrReqInd) {
            if (logger.isDebugEnabled()) {
                  try {
                        logger.debug(String.format("Rx UnstructuredSSRequestIndication. USSD String=%s ", unstrReqInd
                                .getUSSDString().getString(null)));
                  } catch (MAPException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                  }
            }

            MAPDialogSupplementary mapDialog = unstrReqInd.getMAPDialog();

            try {
                  USSDString ussdString = this.mapProvider.getMAPParameterFactory().createUSSDString("1");

                  AddressString msisdn = this.mapProvider.getMAPParameterFactory().createAddressString(
                          AddressNature.international_number, NumberingPlan.ISDN, "31628838002");

                  mapDialog.addUnstructuredSSResponse(unstrReqInd.getInvokeId(), new CBSDataCodingSchemeImpl(0x0f), ussdString);
                  mapDialog.send();

            } catch (MAPException e) {
                  logger.error(String.format("Error while sending UnstructuredSSResponse for Dialog=%d",
                          mapDialog.getDialogId()));
            }
      }

      /*
       * (non-Javadoc)
       * 
       * @see org.mobicents.protocols.ss7.map.api.service.supplementary.
       * MAPServiceSupplementaryListener
       * #onUnstructuredSSResponse(org.mobicents.protocols
       * .ss7.map.api.service.supplementary.UnstructuredSSResponse)
       */
      public void onUnstructuredSSResponse(UnstructuredSSResponse unstrResInd) {
        // This error condition. Client should never receive the
            // UnstructuredSSResponseIndication
            logger.error(String.format("onUnstructuredSSResponseIndication for Dialog=%d and invokeId=%d", unstrResInd
                    .getMAPDialog().getDialogId(), unstrResInd.getInvokeId()));
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.mobicents.protocols.ss7.map.api.MAPServiceListener#onErrorComponent
       * (org.mobicents.protocols.ss7.map.api.MAPDialog, java.lang.Long,
       * org.mobicents.protocols.ss7.map.api.errors.MAPErrorMessage)
       */
      public void onErrorComponent(MAPDialog mapDialog, Long invokeId, MAPErrorMessage mapErrorMessage) {
            logger.error(String.format("onErrorComponent for Dialog=%d and invokeId=%d MAPErrorMessage=%s",
                    mapDialog.getDialogId(), invokeId, mapErrorMessage));
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.mobicents.protocols.ss7.map.api.MAPServiceListener#onInvokeTimeout
       * (org.mobicents.protocols.ss7.map.api.MAPDialog, java.lang.Long)
       */
      public void onInvokeTimeout(MAPDialog mapDialog, Long invokeId) {
            logger.error(String.format("onInvokeTimeout for Dialog=%d and invokeId=%d", mapDialog.getDialogId(), invokeId));
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.mobicents.protocols.ss7.map.api.MAPServiceListener#onMAPMessage(org
       * .mobicents.protocols.ss7.map.api.MAPMessage)
       */
      public void onMAPMessage(MAPMessage arg0) {
            // TODO Auto-generated method stub
      }

      /*
       * (non-Javadoc)
       * 
       * @see org.mobicents.protocols.ss7.map.api.MAPServiceListener#
       * onProviderErrorComponent(org.mobicents.protocols.ss7.map.api.MAPDialog,
       * java.lang.Long,
       * org.mobicents.protocols.ss7.map.api.dialog.MAPProviderError)
       */
      public void onProviderErrorComponent(MAPDialog mapDialog, Long invokeId, MAPProviderError providerError) {
            logger.error(String.format("onProviderErrorComponent for Dialog=%d and invokeId=%d MAPProviderError=%s",
                    mapDialog.getDialogId(), invokeId, providerError));
      }

      /*
       * (non-Javadoc)
       * 
       * @see
       * org.mobicents.protocols.ss7.map.api.MAPServiceListener#onRejectComponent
       * (org.mobicents.protocols.ss7.map.api.MAPDialog, java.lang.Long,
       * org.mobicents.protocols.ss7.tcap.asn.comp.Problem)
       */
      public void onRejectComponent(MAPDialog mapDialog, Long invokeId, Problem problem) {
            logger.error(String.format("onRejectComponent for Dialog=%d and invokeId=%d Problem=%s",
                    mapDialog.getDialogId(), invokeId, problem));
      }

      private void provideSubscriberLocationRequest() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }

      public void onProvideSubscriberLocationRequest(ProvideSubscriberLocationRequest pslr) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }

      public void onProvideSubscriberLocationResponse(ProvideSubscriberLocationResponse pslr) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }

      public void onSubscriberLocationReportRequest(SubscriberLocationReportRequest slrr) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }

      public void onSubscriberLocationReportResponse(SubscriberLocationReportResponse slrr) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }

      public void onSendRoutingInfoForLCSRequest(SendRoutingInfoForLCSRequest s) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }

      public void onSendRoutingInfoForLCSResponse(SendRoutingInfoForLCSResponse s) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }
}
