package com.ge.apm.dto;

public class AssetDataRecord {

	private String siteName;
	private String blockId;
	private String position;
	private String tagId;
	private String unitId="";
	private String inverterType;
	private String skid;
	private String inverterNum;
	public String getInverterType() {
		return inverterType;
	}
	public void setInverterType(String inverterType) {
		this.inverterType = inverterType;
	}
	public String getSkid() {
		return skid;
	}
	public void setSkid(String skid) {
		this.skid = skid;
	}
	public String getInverterNum() {
		return inverterNum;
	}
	public void setInverterNum(String inverterNum) {
		this.inverterNum = inverterNum;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	private String readTime;
	private String value;
	private String assetID;
	public String getAssetID() {
		assetID = siteName+"_"+blockId+"_"+position;
		return assetID;
	}
	public void setAssetID(String assetID) {
		this.assetID = assetID;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getBlockId() {
		return blockId;
	}
	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	
	public String getReadTime() {
		return readTime;
	}
	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "AssetDataRecord [siteName=" + siteName + ", blockId=" + blockId + ", position=" + position + ", tagId="
				+ tagId + ", readTime=" + readTime + ", value=" + value + "]";
	}
	
	
	
}