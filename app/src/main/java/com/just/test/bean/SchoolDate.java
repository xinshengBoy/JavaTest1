package com.just.test.bean;

import java.util.ArrayList;

public class SchoolDate {

	private ArrayList<SchoolDateList> list;

	public class SchoolDateList {

		private String cmsOpDate;
		private String docURL;
		public String docTitle;

		public String getCmsOpDate() {
			return cmsOpDate;
		}

		public void setCmsOpDate(String cmsOpDate) {
			this.cmsOpDate = cmsOpDate;
		}

		public String getDocURL() {
			return docURL;
		}

		public void setDocURL(String docURL) {
			this.docURL = docURL;
		}

		public String getDocTitle() {
			return docTitle;
		}

		public void setDocTitle(String docTitle) {
			this.docTitle = docTitle;
		}
	}
	public ArrayList<SchoolDateList> getList() {
		return list;
	}

	public void setList(ArrayList<SchoolDateList> list) {
		this.list = list;
	}

	
}
