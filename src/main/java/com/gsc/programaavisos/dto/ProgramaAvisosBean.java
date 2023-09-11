package com.gsc.programaavisos.dto;

import com.gsc.ecare.core.ECareNotification;
import com.gsc.programaavisos.constants.PAInfo;
import com.gsc.ws.core.*;
import com.gsc.ws.core.maintenancecontract.MaintenanceContract;
import com.rg.dealer.Dealer;
import com.sc.commons.utils.DateTimerTasks;
import com.sc.commons.utils.StringTasks;
import com.sc.commons.validations.Validate;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class ProgramaAvisosBean {

	//------------------------ Info PA_DATA
	protected int ivId;
	protected int ivIdSource;
	protected String ivIdDocument;
	protected int ivIdChannel;
	protected int ivIdContactType;
	protected int ivIdClientType;
	protected int ivYear;
	protected int ivMonth;
	protected int ivDay;
	protected String ivOidDealer;
	protected String ivLicensePlate;
	protected String ivBrand;
	protected String ivModel;
	protected String ivNif;
	protected String ivName;
	protected String ivAddress;
	protected String ivCp4;
	protected String ivCp3;
	protected String ivCpExt;
	protected String ivContactPhone;
	protected String ivEmail;
	protected String ivDataIsCorrect;
	protected String ivNewNif;
	protected String ivNewName;
	protected String ivNewAddress;
	protected String ivNewAddressNumber;
	protected String ivNewFloor;
	protected String ivNewCp4;
	protected String ivNewCp3;
	protected String ivNewCpExt;
	protected String ivNewContactPhone;
	protected String ivNewEmail;
	protected String ivSuccessContact;
	protected String ivSuccessMotive;
	protected Date ivDtScheduleContact;
	protected Time ivHrScheduleContact;
	protected String ivRevisionSchedule;
	protected String ivRevisionScheduleMotive;
	protected String ivRevisionScheduleMotive2;
//	protected String ivKm;
	protected String ivObservations;
	protected String ivRemovedObs;
	protected String ivDelegatedTo;
	protected String ivClient;
	protected String ivBlockedBy;
	protected int ivNrMissedCalls;
//	protected String ivVisible;
//	protected String ivCreatedBy;
//	protected Timestamp ivDtCreated;
	protected String ivChangedBy;
	protected Timestamp ivDtChanged;
	protected int ivNrCalls;
	protected Integer ivIdClaim;
	protected int ivIdClientChannelPreference;
	protected String ivReceiveInformation;
	protected String ivOidNewsletter;
	protected String ivNewsletterPersonalData;
	protected String ivOidDealerSchedule;
	protected Date ivDtSchedule;
	protected Time ivHrSchedule;
	protected String ivOwner;
	protected String ivRecoveryAndShipping;

	//------------------------ Info MRS
	protected String ivContactChannel;
	protected String ivPostalType;
	protected String ivLastRevision;
	protected String ivLastRevisionKm;
	protected Date	 ivDtLastRevision;
	protected String ivNextRevision;
	protected int	 ivYearNextRevision;
	protected int	 ivMonthNextRevision;
	protected Date	 ivDtNextRevision;
	protected Date	 ivDtItv;
	protected double ivMaintenancePrice;
	protected String ivEurocare;
	protected String ivFlag5Plus;
	protected String ivFlagSend;
	protected String ivSkinDoPostal;
	protected String ivContactReason;
	protected String ivFlagMaintenanceContract;
	protected boolean ivFlagHybrid;
	protected String ivAcessoryCode1;
	protected String ivAcessory1;
	protected String ivAcessoryCode2;
	protected String ivAcessory2;
	protected String ivServiceCode1;
	protected String ivService1;
	protected String ivServiceCode2;
	protected String ivService2;
	protected String ivServiceCode3;
	protected String ivService3;
	protected String ivCmkDmv1;
	protected Date	 ivCmkDmv1DtEnd;
	protected String ivCmkDmv1Image;
	protected String ivCmkDav2;
	protected Date	 ivCmkDav2DtEnd;
	protected String ivCmkDav2Image;
	protected String ivMaintenancePlan;
	protected String ivSendType;
//	protected boolean ivIsToSendNewsletter;

	//Info ExtraCare
	protected double ivExtraCarePlusCostPrice;
	private Date ivExtraCarePlusDateLimit;

	//Info Listagem
	protected String ivDescription;
	protected String ivExpectedDate;

	//Info Gen�rica
	protected Dealer ivDealer;
	protected List<Campaign> ivTechnicalCampaigns;
	protected List<Revision> ivRevisions;
	protected List<Warranty> ivWarranties;
	protected List<ECareNotification> ivECareNotifications;
	protected List<ECareNotification> ivECareAllNotifications;
	protected int ivIDOrigin;
	protected int ivExtIDInOrigin;
	protected List<Claim> ivClaims;
	protected List<Rpt> ivRpts;
	protected String ivExtracare;
	protected String ivDtNextIUC;
	protected String ivDtStartNextITV;
	protected String ivDtNextItv;
	protected MaintenanceContract ivMaintenanceContract;
	protected String ivTecnicalModel;
	protected Integer ivIndiceCSToyota;

//	protected String ivWarrantyExtensionCode;
//	protected String ivWarrantyExtensionDesig;
//	protected String ivDtStartWarrantyExtension;
//	protected String ivDtEndWarrantyExtension;
//	protected String ivKmEndWarrantyExtension;
	protected String ivHHCCode;
	protected String ivHHCDesig;
	protected String ivHHCDisplayName;
	protected String ivHHCDtStart;
	protected String ivHHCDtEnd;
	protected String ivHHCKmEnd;

	//Info Maintenance Contract
//	protected String ivContractCode;
//	protected String ivContractName;
//	protected String ivOidDealerSell;
//	protected Date ivDtSell;
//	protected double ivDealerPrice;
//	protected double ivPvpPrice;
//	protected double ivClientPrice;
//	protected Date ivDtStartContract;
//	protected Date ivDtEndContract;
	protected Date ivDtFinishContract;

	//Info Campanhas T�cnicas
	protected String ivTechicalCampaignCode;
	protected Date ivTechicalCampaignSendDate1;
//	protected int ivAge;
//	protected String ivGamma;
//	protected String ivTechnicalModel;
//	protected String ivSalesDate;
//	protected String ivContactSource;
//	protected String ivPhone1;
//	protected String ivPhone2;
//	protected String ivPhone3;
//	protected String ivExlude;
	protected Date ivGenerationDate;
	protected Date ivTechicalCampaignSendDate2;
	protected Date ivDtVisible;
	protected int ivWarningPriority;

	public ProgramaAvisosBean() {
	}

	public ProgramaAvisosBean(Map<String,Object> rs, boolean withHHC) throws Exception {
		//------------------------ Info PA_DATA
		setId((Integer) rs.get("PA_ID"));
		setIdContactType((Integer) rs.get("PA_ID"));
		setLicensePlate(			 StringTasks.cleanString((String) rs.get("PA_LICENSE_PLATE").toString(), ""));
		setBrand(					 StringTasks.cleanString((String) rs.get("PA_BRAND"), ""));
		setOidNewsletter((String) rs.get("PA_OID_NEWSLETTER"));
		setNewsletterPersonalData((String) rs.get("PA_NEWSLETTER_PERSONAL_DATA"));
		setNextRevision(			StringTasks.cleanString((String) rs.get("MRS_NEXT_REVISION"), ""));

	}

	//------------------------ Info PA_DATA
	public int getId() {
		return ivId;
	}
	public void setId(int id) {
		ivId = id;
	}

	public int getIdSource() {
		return ivIdSource;
	}
	public void setIdSource(int idSource) {
		ivIdSource = idSource;
	}
	public String getIdDocument() {
		return ivIdDocument;
	}

	public void setIdDocument(String idDocument) {
		ivIdDocument = idDocument;
	}

	public int getIdChannel() {
		return ivIdChannel;
	}
	public void setIdChannel(int idChannel) {
		ivIdChannel = idChannel;
	}

	public int getIdContactType() {
		return ivIdContactType;
	}
	public void setIdContactType(int idContactType) {
		ivIdContactType = idContactType;
	}

	public int getIdClientType() {
		return ivIdClientType;
	}

	public void setIdClientType(int idClientType) {
		ivIdClientType = idClientType;
	}

	public int getYear() {
		return ivYear;
	}
	public void setYear(int year) {
		ivYear = year;
	}

	public int getMonth() {
		return ivMonth;
	}
	public void setMonth(int month) {
		ivMonth = month;
	}

	public int getDay() {
		return ivDay;
	}
	public void setDay(int day) {
		ivDay = day;
	}

	public String getOidDealer() {
		return ivOidDealer;
	}
	public void setOidDealer(String oidDealer) {
		ivOidDealer = oidDealer;
	}

	public String getLicensePlate() {
		return ivLicensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		ivLicensePlate = licensePlate;
	}

	public String getBrand() {
		return ivBrand;
	}
	public void setBrand(String brand) {
		ivBrand = brand;
	}

	public String getModel() {
		return ivModel;
	}
	public void setModel(String model) {
		ivModel = model;
	}

	public String getNif() {
		return ivNif;
	}
	public void setNif(String nif) {
		ivNif = nif;
	}

	public String getName() {
		return ivName;
	}
	public void setName(String name) {
		ivName = name;
	}

	public String getAddress() {
		return ivAddress;
	}
	public void setAddress(String address) {
		ivAddress = address;
	}

	public String getCp4() {
		return ivCp4;
	}
	public void setCp4(String cp4) {
		ivCp4 = cp4;
	}

	public String getCp3() {
		return ivCp3;
	}
	public void setCp3(String cp3) {
		ivCp3 = cp3;
	}

	public String getCpExt() {
		return ivCpExt;
	}
	public void setCpExt(String cpExt) {
		ivCpExt = cpExt;
	}

	public String getContactPhone() {
		return ivContactPhone;
	}
	public void setContactPhone(String contactPhone) {
		ivContactPhone = contactPhone;
	}

	public String getEmail() {
		return ivEmail;
	}
	public void setEmail(String email) {
		ivEmail = email;
	}

	public String getDataIsCorrect() {
		return ivDataIsCorrect;
	}
	public void setDataIsCorrect(String dataIsCorrect) {
		ivDataIsCorrect = dataIsCorrect;
	}

	public String getNewNif() {
		return ivNewNif;
	}

	public void setNewNif(String newNif) {
		ivNewNif = newNif;
	}

	public String getNewName() {
		return ivNewName;
	}

	public void setNewName(String newName) {
		ivNewName = newName;
	}

	public String getNewAddress() {
		return ivNewAddress;
	}

	public void setNewAddress(String newAddress) {
		ivNewAddress = newAddress;
	}

	public String getNewAddressNumber() {
		return ivNewAddressNumber;
	}

	public void setNewAddressNumber(String newAddressNumber) {
		ivNewAddressNumber = newAddressNumber;
	}

	public String getNewFloor() {
		return ivNewFloor;
	}

	public void setNewFloor(String newFloor) {
		ivNewFloor = newFloor;
	}

	public String getNewCp4() {
		return ivNewCp4;
	}

	public void setNewCp4(String newCp4) {
		ivNewCp4 = newCp4;
	}

	public String getNewCp3() {
		return ivNewCp3;
	}

	public void setNewCp3(String newCp3) {
		ivNewCp3 = newCp3;
	}

	public String getNewCpExt() {
		return ivNewCpExt;
	}

	public void setNewCpExt(String newCpExt) {
		ivNewCpExt = newCpExt;
	}

	public String getNewContactPhone() {
		return ivNewContactPhone;
	}

	public void setNewContactPhone(String newContactPhone) {
		ivNewContactPhone = newContactPhone;
	}

	public String getNewEmail() {
		return ivNewEmail;
	}

	public void setNewEmail(String newEmail) {
		ivNewEmail = newEmail;
	}

	public String getSuccessContact() {
		return ivSuccessContact;
	}

	public void setSuccessContact(String successContact) {
		ivSuccessContact = successContact;
	}

	public String getSuccessMotive() {
		return ivSuccessMotive;
	}

	public void setSuccessMotive(String successMotive) {
		ivSuccessMotive = successMotive;
	}

	public Date getDtScheduleContact() {
		return ivDtScheduleContact;
	}
	public String getDtScheduleContactText() {
		return ivDtScheduleContact==null ? "" : DateTimerTasks.fmtDT.format(ivDtScheduleContact);
	}
	public void setDtScheduleContact(Date dtScheduleContact) {
		ivDtScheduleContact = dtScheduleContact;
	}

	public Time getHrScheduleContact() {
		return ivHrScheduleContact;
	}
	public String getHrScheduleContactText() {
		return ivHrScheduleContact==null ? "" : DateTimerTasks.fmtTime.format(ivHrScheduleContact);
	}
	public void setHrScheduleContact(Time hrScheduleContact) {
		ivHrScheduleContact = hrScheduleContact;
	}

	public String getRevisionSchedule() {
		return ivRevisionSchedule;
	}

	public void setRevisionSchedule(String revisionSchedule) {
		ivRevisionSchedule = revisionSchedule;
	}

	public String getRevisionScheduleMotive() {
		return ivRevisionScheduleMotive;
	}

	public void setRevisionScheduleMotive(String revisionScheduleMotive) {
		ivRevisionScheduleMotive = revisionScheduleMotive;
	}

	public String getRevisionScheduleMotive2() {
		return ivRevisionScheduleMotive2;
	}

	public void setRevisionScheduleMotive2(String revisionScheduleMotive2) {
		ivRevisionScheduleMotive2 = revisionScheduleMotive2;
	}

//	public String getKm() {
//		return ivKm;
//	}
//
//	public void setKm(String km) {
//		ivKm = km;
//	}

	public String getObservations() {
		return ivObservations;
	}

	public void setObservations(String observations) {
		ivObservations = observations;
	}

	public String getRemovedObs() {
		return ivRemovedObs;
	}

	public void setRemovedObs(String removedObs) {
		ivRemovedObs = removedObs;
	}

	public String getDelegatedTo() {
		return ivDelegatedTo;
	}

	public void setDelegatedTo(String delegatedTo) {
		ivDelegatedTo = delegatedTo;
	}

	public String getClient() {
		return ivClient;
	}
	public void setClient(String client) {
		ivClient = client;
	}

	public String getBlockedBy() {
		return ivBlockedBy;
	}
	public void setBlockedBy(String blockedBy) {
		ivBlockedBy = blockedBy;
	}

	public int getNrMissedCalls() {
		return ivNrMissedCalls;
	}
	public void setNrMissedCalls(int nrMissedCalls) {
		ivNrMissedCalls = nrMissedCalls;
	}

	public int getNrCalls() {
		return ivNrCalls;
	}

	public void setNrCalls(int nrCalls) {
		ivNrCalls = nrCalls;
	}

//	public String getVisible() {
//		return ivVisible;
//	}
//
//	public void setVisible(String visible) {
//		ivVisible = visible;
//	}

//	public String getCreatedBy() {
//		return ivCreatedBy;
//	}
//	public void setCreatedBy(String createdBy) {
//		ivCreatedBy = createdBy;
//	}

//	public Timestamp getDtCreated() {
//		return ivDtCreated;
//	}
//	public void setDtCreated(Timestamp dtCreated) {
//		ivDtCreated = dtCreated;
//	}

	public Integer getIdClaim() {
		return ivIdClaim;
	}

	public void setIdClaim(Integer idClaim) {
		ivIdClaim = idClaim;
	}

	public int getIDOrigin() {
		return ivIDOrigin;
	}

	public void setIDOrigin(int iDOrigin) {
		ivIDOrigin = iDOrigin;
	}

	public int getExtIDInOrigin() {
		return ivExtIDInOrigin;
	}

	public void setExtIDInOrigin(int extIDInOrigin) {
		ivExtIDInOrigin = extIDInOrigin;
	}

	public String getChangedBy() {
		return ivChangedBy;
	}

	public void setChangedBy(String changedBy) {
		ivChangedBy = changedBy;
	}

	public Timestamp getDtChanged() {
		return ivDtChanged;
	}
	public String getDtChangedText() {
		return ivDtChanged==null ? "" : DateTimerTasks.fmtDTTime.format(ivDtChanged);
	}
	public void setDtChanged(Timestamp dtChanged) {
		ivDtChanged = dtChanged;
	}

	//------------------------ Info MRS
//	public String getDealerCode() {
//		String dealerCode = "00" + ivDealerCode;
//		return dealerCode.substring(dealerCode.length() - 2, dealerCode.length());
//	}
//	public void setDealerCode(String dealerCode) {
//		ivDealerCode = dealerCode;
//	}
//
//	public String getAfterSalesCode() {
//		String ofic = "000" + ivAfterSalesCode;
//		return ofic.substring(ofic.length() - 3, ofic.length());
//	}
//	public void setAfterSalesCode(int afterSalesCode) {
//		ivAfterSalesCode = afterSalesCode;
//	}

	public int getIdClientChannelPreference() {
		return ivIdClientChannelPreference;
	}
	public void setIdClientChannelPreference(int idClientChannelPreference) {
		ivIdClientChannelPreference = idClientChannelPreference;
	}

	public String getReceiveInformation() {
		return ivReceiveInformation;
	}
	public void setReceiveInformation(String receiveInformation) {
		ivReceiveInformation = receiveInformation;
	}

	public String getOidNewsletter() {
		return StringTasks.cleanString(ivOidNewsletter, "");
	}
	public void setOidNewsletter(String oidNewsletter) {
		ivOidNewsletter = oidNewsletter;
	}

	public String getNewsletterPersonalData() {
		return ivNewsletterPersonalData;
	}
	public void setNewsletterPersonalData(String newsletterPersonalData) {
		ivNewsletterPersonalData = newsletterPersonalData;
	}

	public String getContactChannel() {
		return ivContactChannel;
	}
	public void setContactChannel(String contactChannel) {
		ivContactChannel = contactChannel;
	}

	public String getPostalType() {
		return ivPostalType;
	}
	public void setPostalType(String postalType) {
		ivPostalType = postalType;
	}

	public String getLastRevision() {
		return ivLastRevision;
	}
	public void setLastRevision(String lastRevision) {
		ivLastRevision = lastRevision;
	}

	public String getLastRevisionKm() {
		return ivLastRevisionKm;
	}
	public void setLastRevisionKm(String lastRevisionKm) {
		ivLastRevisionKm = lastRevisionKm;
	}

	public Date getDtLastRevision() {
		return ivDtLastRevision;
	}
	public void setDtLastRevision(Date dtLastRevision) {
		ivDtLastRevision = dtLastRevision;
	}
	public String getNextRevision() {
		return StringTasks.ReplaceStr(ivNextRevision, "is�o Programada dos", ".");
//		return ivNextRevision;
	}
	public int getNextRevisionKm() {
		String nextRevisionKm = StringTasks.ReplaceStr(ivNextRevision, "Revis�o Programada dos ", "");
		nextRevisionKm = StringTasks.ReplaceStr(nextRevisionKm, " kms", "");
		return StringTasks.cleanInteger(nextRevisionKm.trim(), 0);
	}
	public void setNextRevision(String nextRevision) {
		ivNextRevision = nextRevision;
	}

	public int getYearNextRevision() {
		return ivYearNextRevision;
	}
	public void setYearNextRevision(int yearNextRevision) {
		ivYearNextRevision = yearNextRevision;
	}

	public int getMonthNextRevision() {
		return ivMonthNextRevision;
	}
	public void setMonthNextRevision(int monthNextRevision) {
		ivMonthNextRevision = monthNextRevision;
	}

	public Date getDtNextRevision() {
		return ivDtNextRevision;
	}
	public void setDtNextRevision(Date dtNextRevision) {
		ivDtNextRevision = dtNextRevision;
	}

	public Date getDtItv() {
		return ivDtItv;
	}
	public void setDtItv(Date dtItv) {
		ivDtItv = dtItv;
	}

	public double getMaintenancePrice() {
		return ivMaintenancePrice;
	}
	public String getMaintenancePriceText() {
		return ivMaintenancePrice==0 ? "" : PAInfo.CURRENCY_FORMAT.format(ivMaintenancePrice) + "&euro;";
	}
	public String getMaintenancePriceTextSymbol() {
		return ivMaintenancePrice==0 ? "" : PAInfo.CURRENCY_FORMAT.format(ivMaintenancePrice) + "�";
	}
	public void setMaintenancePrice(double maintenancePrice) {
		ivMaintenancePrice = maintenancePrice;
	}

	public String getSendType() {
		return ivSendType;
	}

	public void setSendType(String sendType) {
		ivSendType = sendType;
	}

	public String getEurocare() {
		return ivEurocare;
	}
	public void setEurocare(String eurocare) {
		ivEurocare = eurocare;
	}

	public String getFlag5Plus() {
		return ivFlag5Plus;
	}
	public boolean hasFlag5Plus() {
		return "S".equalsIgnoreCase(getFlag5Plus());
	}
	public void setFlag5Plus(String flag5Plus) {
		ivFlag5Plus = flag5Plus;
	}

	public String getContactReason() {
		return ivContactReason;
	}
	public void setContactReason(String contactReason) {
		ivContactReason = contactReason;
	}

	public String getFlagMaintenanceContract() {
		return ivFlagMaintenanceContract;
	}
	public void setFlagMaintenanceContract(String flagMaintenanceContract) {
		ivFlagMaintenanceContract = flagMaintenanceContract;
	}

	public boolean getFlagHybrid() {
		return ivFlagHybrid;
	}
	public void setFlagHybrid(boolean flagHybrid) {
		ivFlagHybrid = flagHybrid;
	}

	public String getAcessoryCode1() {
		return ivAcessoryCode1;
	}
	public void setAcessoryCode1(String acessoryCode1) {
		ivAcessoryCode1 = acessoryCode1;
	}

	public String getAcessory1() {
		return ivAcessory1;
	}
	public void setAcessory1(String acessory1) {
		ivAcessory1 = acessory1;
	}

	public String getAcessoryCode2() {
		return ivAcessoryCode2;
	}
	public void setAcessoryCode2(String acessoryCode2) {
		ivAcessoryCode2 = acessoryCode2;
	}

	public String getAcessory2() {
		return ivAcessory2;
	}
	public void setAcessory2(String acessory2) {
		ivAcessory2 = acessory2;
	}

	public String getServiceCode1() {
		return ivServiceCode1;
	}
	public void setServiceCode1(String serviceCode1) {
		ivServiceCode1 = serviceCode1;
	}

	public String getService1() {
		return ivService1;
	}
	public void setService1(String service1) {
		ivService1 = service1;
	}

	public String getServiceCode2() {
		return ivServiceCode2;
	}
	public void setServiceCode2(String serviceCode2) {
		ivServiceCode2 = serviceCode2;
	}

	public String getService2() {
		return ivService2;
	}
	public void setService2(String service2) {
		ivService2 = service2;
	}

	public String getServiceCode3() {
		return ivServiceCode3;
	}
	public void setServiceCode3(String serviceCode3) {
		ivServiceCode3 = serviceCode3;
	}

	public String getService3() {
		return ivService3;
	}
	public void setService3(String service3) {
		ivService3 = service3;
	}

	public String getCmkDmv1() {
		return ivCmkDmv1;
	}
	public boolean hasCmkDmv1() {

		if (getCmkDmv1().equals(""))
			return false;

		if (getCmkDmv1DtEnd()==null)
			return true;

		Calendar now = Calendar.getInstance();

		Calendar old = Calendar.getInstance();;
		old.setTimeInMillis(getCmkDmv1DtEnd().getTime());
		old.set(Calendar.HOUR_OF_DAY, 23);
		old.set(Calendar.MINUTE, 59);

		return now.before(old);
	}
	public void setCmkDmv1(String cmkDmv1) {
		ivCmkDmv1 = cmkDmv1;
	}

	public Date getCmkDmv1DtEnd() {
		return ivCmkDmv1DtEnd;
	}
	public void setCmkDmv1DtEnd(Date cmkDmv1DtEnd) {
		ivCmkDmv1DtEnd = cmkDmv1DtEnd;
	}

	public String getCmkDmv1Image() {
		return ivCmkDmv1Image;
	}
	public void setCmkDmv1Image(String cmkDmv1Image) {
		ivCmkDmv1Image = cmkDmv1Image;
	}

	public String getCmkDav2() {
		return ivCmkDav2;
	}
	public boolean hasCmkDav2() {
		if (getCmkDav2().equals(""))
			return false;

		if (getCmkDav2DtEnd()==null)
			return true;

		Calendar now = Calendar.getInstance();

		Calendar old = Calendar.getInstance();;
		old.setTimeInMillis(getCmkDav2DtEnd().getTime());
		old.set(Calendar.HOUR_OF_DAY, 23);
		old.set(Calendar.MINUTE, 59);

		return now.before(old);
	}
	public void setCmkDav2(String cmkDav2) {
		ivCmkDav2 = cmkDav2;
	}

	public Date getCmkDav2DtEnd() {
		return ivCmkDav2DtEnd;
	}
	public void setCmkDav2DtEnd(Date cmkDav2DtEnd) {
		ivCmkDav2DtEnd = cmkDav2DtEnd;
	}

	public String getCmkDav2Image() {
		return ivCmkDav2Image;
	}
	public void setCmkDav2Image(String cmkDav2Image) {
		ivCmkDav2Image = cmkDav2Image;
	}

	public String getMaintenancePlan() {
		if (Validate.isOnlyDigits(ivMaintenancePlan)){
			return "1 ano ou " + PAInfo.KM_FORMAT.format(Long.parseLong(ivMaintenancePlan)) + " Kms";
		}
		return ivMaintenancePlan;
	}
	public void setMaintenancePlan(String maintenancePlan) {
		ivMaintenancePlan = maintenancePlan;
	}

//	public boolean isIsToSendNewsletter() {
//		return ivIsToSendNewsletter;
//	}
//	public void setIsToSendNewsletter(boolean isToSendNewsletter) {
//		ivIsToSendNewsletter = isToSendNewsletter;
//	}

	//------------------------ Info ExtraCare
	public Date getExtraCarePlusDateLimit() {
		return ivExtraCarePlusDateLimit;
	}

	public void setExtraCarePlusDateLimit(Date extraCarePlusDateLimit) {
		ivExtraCarePlusDateLimit =extraCarePlusDateLimit;
	}

	public double getExtraCarePlusCostPrice() {
		return ivExtraCarePlusCostPrice;
	}

	public void setExtraCarePlusCostPrice(double extraCarePlusCostPrice) {
		ivExtraCarePlusCostPrice = extraCarePlusCostPrice;
	}

	public String getExtraCarePlusCostPriceText() {
		return ivExtraCarePlusCostPrice==0 ? "" : PAInfo.CURRENCY_FORMAT.format(ivExtraCarePlusCostPrice);
	}

	//------------------------ Info Listagem
	public String getDescription() {
		return ivDescription;
	}
	public void setDescription(String description) {
		ivDescription = description;
	}

	public String getExpectedDate() {
		return ivExpectedDate;
	}
	public void setExpectedDate(String expectedDate) {
		ivExpectedDate = expectedDate;
	}

	//------------------------ Info Gen�rica




	public void setRevisions(List<Revision> revisions) {
		ivRevisions = revisions;
	}


	public void setWarranties(List<Warranty> warranties) {
		ivWarranties = warranties;
	}



	public void setNotification(List<ECareNotification> notifications) {
		ivECareNotifications = notifications;
	}



	public void setAllNotifications(List<ECareNotification> notifications) {
		ivECareAllNotifications = notifications;
	}

	public void setClaims(List<Claim> claims) {
		ivClaims = claims;
	}

	public void setRpts(List<Rpt> rpts) {
		ivRpts = rpts;
	}

	public String getExtracare() {
		return ivExtracare;
	}
	public void setExtracare(String extracare) {
		ivExtracare = extracare;
	}

	public String getDtNextIUC() {
		return ivDtNextIUC;
	}
	public void setDtNextIUC(String dtNextIUC) {
		ivDtNextIUC = dtNextIUC;
	}

	public String getDtStartNextITV() {
		return ivDtStartNextITV;
	}
	public void setDtStartNextITV(String dtStartNextITV) {
		ivDtStartNextITV = dtStartNextITV;
	}

	public String getDtNextItv() {
		return ivDtNextItv;
	}
	public void setDtNextItv(String dtNextItv) {
		ivDtNextItv = dtNextItv;
	}

	public MaintenanceContract getMaintenanceContract() {
		return ivMaintenanceContract;
	}
	public void setMaintenanceContract(MaintenanceContract maintenanceContract) {
		ivMaintenanceContract = maintenanceContract;
	}

	public String getTecnicalModel() {
		return ivTecnicalModel;
	}
	public void setTecnicalModel(String tecnicalModel) {
		ivTecnicalModel = tecnicalModel;
	}


	public String getOidDealerSchedule() {
		return ivOidDealerSchedule;
	}

	public Date getDtSchedule() {
		return ivDtSchedule;
	}

	public String getDtScheduleText() {
		return ivDtSchedule==null ? "" : DateTimerTasks.fmtDT.format(ivDtSchedule);
	}

	public void setOidDealerSchedule(String oidDealerSchedule) {
		ivOidDealerSchedule = oidDealerSchedule;
	}

	public void setDtSchedule(Date dtSchedule) {
		ivDtSchedule = dtSchedule;
	}

	public String getHrScheduleText() {
		return ivHrSchedule==null ? "" : DateTimerTasks.fmtTime.format(ivHrSchedule);
	}
	public void setHrSchedule(Time hrSchedule) {
		ivHrSchedule = hrSchedule;
	}


	public String getHHCCode() {
		return ivHHCCode;
	}
	public void setHHCCode(String hhcCode) {
		ivHHCCode = hhcCode;
	}

	public String getHHCDesig() {
		return ivHHCDesig;
	}
	public void setHHCDesig(String hhcDesig) {
		ivHHCDesig = hhcDesig;
	}

	public String getHHCDisplayName() {
		return ivHHCDisplayName;
	}
	public void setHHCDisplayName(String hhcDisplayName) {
		ivHHCDisplayName = hhcDisplayName;
	}

	public String getHHCDtStart() {
		return ivHHCDtStart;
	}
	public void setHHCDtStart(String dtStartHHC) {
		ivHHCDtStart = dtStartHHC;
	}

	public String getHHCDtEnd() {
		return ivHHCDtEnd;
	}
	public void setHHCDtEnd(String dtEndHHC) {
		ivHHCDtEnd = dtEndHHC;
	}

	public String getHHCKmEnd() {
		return ivHHCKmEnd;
	}
	public void setHHCKmEnd(String kmEndHHC) {
		ivHHCKmEnd = kmEndHHC;
	}



	public Date getDtFinishContract() {
		return ivDtFinishContract;
	}
	public void setDtFinishContract(Date dtFinishContract) {
		ivDtFinishContract = dtFinishContract;
	}

	public String getFlagSend() {
		return ivFlagSend;
	}
	public void setFlagSend(String flagSend) {
		ivFlagSend = flagSend;
	}

	public String getTechicalCampaignCode() {
		return ivTechicalCampaignCode;
	}
	public void setTechicalCampaignCode(String campaign) {
		ivTechicalCampaignCode = campaign;
	}

	public Date getTechicalCampaignSendDate2() {
		return ivTechicalCampaignSendDate2;
	}
	public void setTechicalCampaignSendDate2(Date sendDate2) {
		ivTechicalCampaignSendDate2 = sendDate2;
	}

	public Date getTechicalCampaignSendDate1() {
		return ivTechicalCampaignSendDate1;
	}
	public void setTechicalCampaignSendDate1(Date sendDate) {
		ivTechicalCampaignSendDate1 = sendDate;
	}

	public Date getGenerationDate() {
		return ivGenerationDate;
	}
	public void setGenerationDate(Date generationDate) {
		ivGenerationDate = generationDate;
	}

	public void getProgramaAvisosByBean() {
	}

	public String getOwner() {
		return ivOwner;
	}
	public void setOwner(String owner) {
		ivOwner = owner;
	}

	public String getRecoveryAndShipping() {
		return ivRecoveryAndShipping;
	}

	public void setRecoveryAndShipping(String recoveryAndShipping) {
		ivRecoveryAndShipping = recoveryAndShipping;
	}

	public Date getDtVisible() {
		return ivDtVisible;
	}

	public void setDtVisible(Date dtVisible) {
		ivDtVisible = dtVisible;
	}

	public int getWarningPriority() {
		return ivWarningPriority;
	}

	public void setWarningPriority(int warningPriority) {
		ivWarningPriority = warningPriority;
	}

	public String colorWarningPriority(int warningPriority) {
		switch (warningPriority) {
			case 1:
				return "#c30605";
			case 2:
				return "#f99144";
			case 3:
				return "#FFC720";
			default:
				return "black";
		}
	}

	public String priorityDescription() {
		switch (getWarningPriority()) {
		case 1:
			return "Cr�tico";
		case 2:
			return "Maior";
		case 3:
			return "Menor";
		default:
			return "Trivial";
		}
	}

	public String titleWarningPriority(int warningPriority) {
		return warningPriority > 0 ? "Prioridade " + warningPriority : "Prioridade n�o dispon�vel";
	}
}
