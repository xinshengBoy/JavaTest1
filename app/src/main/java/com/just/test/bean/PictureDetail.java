package com.just.test.bean;

import java.util.List;

public class PictureDetail {

	public boolean state;
	public resContent rescontent;

	public static class resContent{
		public List<picture> content;
		public String title;
		public String releaseDate;
		public String docId;
		public String publishdate;
		public String url;
		
		public static class picture{
			public String text;
			public String img;
			public String getText() {
				return text;
			}
			public void setText(String text) {
				this.text = text;
			}
			public String getImg() {
				return img;
			}
			public void setImg(String img) {
				this.img = img;
			}
		}

		public List<picture> getContent() {
			return content;
		}

		public void setContent(List<picture> content) {
			this.content = content;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getReleaseDate() {
			return releaseDate;
		}

		public void setReleaseDate(String releaseDate) {
			this.releaseDate = releaseDate;
		}

		public String getDocId() {
			return docId;
		}

		public void setDocId(String docId) {
			this.docId = docId;
		}

		public String getPublishdate() {
			return publishdate;
		}

		public void setPublishdate(String publishdate) {
			this.publishdate = publishdate;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
		
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public resContent getRescontent() {
		return rescontent;
	}

	public void setRescontent(resContent rescontent) {
		this.rescontent = rescontent;
	}
	
}
