package com.kas.clientservice.haiyansmartenforce.Entity;

import java.util.List;

/**
 * 案源
 * @author Administrator
 *
 */
public class CaseDetailsInfo {

	public List<CaseDetailsInfo.KsBean> KS;

	public List<CaseDetailsInfo.KsBean> getKS() {
		return KS;
	}

	public static class KsBean {
		String PunishCas;
		String NameECCas;
		String NumberCas;
		String NameCaF;
		String state;
		String ActCas;
		String CaseID;
		String CaseAddressCas;
		String AcceptTimeCas;
		String CaseEntOrCitiCas;

		public String getPunishCas() {
			return PunishCas;
		}

		public void setPunishCas(String punishCas) {
			PunishCas = punishCas;
		}

		public String getNameECCas() {
			return NameECCas;
		}

		public void setNameECCas(String nameECCas) {
			NameECCas = nameECCas;
		}

		public String getNumberCas() {
			return NumberCas;
		}

		public void setNumberCas(String numberCas) {
			NumberCas = numberCas;
		}

		public String getNameCaF() {
			return NameCaF;
		}

		public void setNameCaF(String nameCaF) {
			NameCaF = nameCaF;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getActCas() {
			return ActCas;
		}

		public void setActCas(String actCas) {
			ActCas = actCas;
		}

		public String getCaseID() {
			return CaseID;
		}

		public void setCaseID(String caseID) {
			CaseID = caseID;
		}

		public String getCaseAddressCas() {
			return CaseAddressCas;
		}

		public void setCaseAddressCas(String caseAddressCas) {
			CaseAddressCas = caseAddressCas;
		}

		public String getAcceptTimeCas() {
			return AcceptTimeCas;
		}

		public void setAcceptTimeCas(String acceptTimeCas) {
			AcceptTimeCas = acceptTimeCas;
		}

		public String getCaseEntOrCitiCas() {
			return CaseEntOrCitiCas;
		}

		public void setCaseEntOrCitiCas(String caseEntOrCitiCas) {
			CaseEntOrCitiCas = caseEntOrCitiCas;
		}
	}
	
}
