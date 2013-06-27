package org.mifosplatform.billing.media.data;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.LocalDate;
import org.mifosplatform.infrastructure.core.data.EnumOptionData;

public class MediaAssetData {


	private final Long mediaId;
	private final String mediaTitle;
	private final String mediaImage;
	private final BigDecimal mediaRating;
	private final Long eventId;
	private Long noOfPages;
	private Long pageNo;
	private String assetTag;
	private List<EnumOptionData> mediaStatus;
	private List<MediaassetAttribute> mediaAttributes;
	private List<MediaassetAttribute> mediaFormat;
	private String status;
	private LocalDate releaseDate;
	
public MediaAssetData(final Long mediaId,final String mediaTitle,final String image,final BigDecimal rating, Long eventId, String assetTag){
	this.mediaId=mediaId;
	this.mediaTitle=mediaTitle;
	this.mediaImage=image;
	this.mediaRating=rating;
	this.eventId=eventId;
	this.assetTag=assetTag;
}
public MediaAssetData(Long noOfPages, Long pageNo) {
	this.mediaId=null;
	this.mediaTitle=null;
	this.mediaImage=null;
	this.mediaRating=null;
	this.eventId=null;
	this.noOfPages=noOfPages;
	this.pageNo=pageNo;
}
public MediaAssetData(List<EnumOptionData> status,List<MediaassetAttribute> data, List<MediaassetAttribute> mediaFormat) {

	this.mediaStatus=status;
	this.mediaAttributes=data;
	this.mediaFormat=mediaFormat;
	this.mediaId=null;
	this.mediaTitle=null;
	this.mediaImage=null;
	this.mediaRating=null;
	this.eventId=null;
	
}
public MediaAssetData(Long mediaId, String mediaTitle, String status,
		LocalDate releaseDate, BigDecimal rating) {
          this.mediaId=mediaId;
          this.mediaTitle=mediaTitle;
          this.status=status;
          this.releaseDate=releaseDate;
          this.mediaRating=rating;
      	this.mediaImage=null;
      	this.eventId=null;
}
public Long getMediaId() {
	return mediaId;
}
public String getMediaTitle() {
	return mediaTitle;
}
public String getMediaImage() {
	return mediaImage;
}
public BigDecimal getMediaRating() {
	return mediaRating;
}
public Long getEventId() {
	return eventId;
}
	

}
