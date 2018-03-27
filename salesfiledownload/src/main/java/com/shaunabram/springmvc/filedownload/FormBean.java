package com.shaunabram.springmvc.filedownload;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class FormBean {




    private Date startDate;


    private Date endDate;




	private String inquiryDetails;

	private boolean subscribeNewsletter;

	private Map<String, String> additionalInfo;




	public String getInquiryDetails() {
		return inquiryDetails;
	}

	public void setInquiryDetails(String inquiryDetails) {
		this.inquiryDetails = inquiryDetails;
	}

	public boolean isSubscribeNewsletter() {
		return subscribeNewsletter;
	}

	public void setSubscribeNewsletter(boolean subscribeNewsletter) {
		this.subscribeNewsletter = subscribeNewsletter;
	}

	public Map<String, String> getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(Map<String, String> additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "FormBean [startDate=" + startDate + ", endDate=" + endDate + ", inquiryDetails=" + inquiryDetails + ", subscribeNewsletter="
                + subscribeNewsletter + ", additionalInfo=" + additionalInfo + "]";
    }

}
