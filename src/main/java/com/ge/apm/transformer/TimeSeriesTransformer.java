package com.ge.apm.transformer;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;

import com.ge.apm.dto.AssetDataRecord;
import com.ge.apm.ingestor.beans.DataElement;
import com.ge.apm.ingestor.beans.Ingestor;
import com.ge.apm.ingestor.beans.TagElement;



public class TimeSeriesTransformer implements ItemProcessor<AssetDataRecord,TagElement>{

	private final ZoneId pst = ZoneId.of("America/Los_Angeles");

	private Map<String, Integer> mapTag = new HashMap<String, Integer>();
	private Ingestor timeseries;

	

	public TimeSeriesTransformer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TagElement insert(String readTime, String assetId, String siteName, String blockId, String positionId,
			String unitId, String tagId, String value) {

		String apmTagId = "FS_" + assetId + "." + tagId;

		
	    TagElement tagEle = new TagElement();
		DataElement dataEle = new DataElement();
		if (readTime != null) {
			dataEle.setTs(getEpoch(readTime));
			if(!value.isEmpty() && !value.equals(""))
					dataEle.setV(Double.valueOf(value));
			
			//System.out.println("mapTag"+mapTag.keySet());
			if (mapTag.containsKey(apmTagId)) {
				//System.out.println("in here");
				int position = mapTag.get(apmTagId);
				//tagEle = timeseries.getTags().get(position);
				tagEle.getData().add(dataEle);
			} else {
				tagEle = new TagElement();
				tagEle.setTagid(apmTagId);
				tagEle.getData().add(dataEle);
				//timeseries.getTags().add(tagEle);
				//mapTag.put(apmTagId, timeseries.getTags().size() - 1);
			}

		}
		return tagEle;
	}

	private long getEpoch(String readTime) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localtDateAndTime = LocalDateTime.parse(readTime, formatter);
		ZonedDateTime dateAndTimeInPST = ZonedDateTime.of(localtDateAndTime, pst);

		// System.out.println("Current date and time in a particular timezone :
		// " + dateAndTimeInSydney);

		ZonedDateTime utcDate = dateAndTimeInPST.withZoneSameInstant(ZoneOffset.UTC);

		return utcDate.toEpochSecond()*1000;
	}

	/*public boolean transformCSV(List<AssetDataRecord> listAsset) throws IOException{
	    	
		  for (AssetDataRecord assetRec : listAsset) {
	    
	        insert(assetRec.getReadTime(),assetRec.getAssetID(),assetRec.getSiteName(),assetRec.getBlockId(),assetRec.getPosition(),assetRec.getUnitId(),assetRec.getTagId(),assetRec.getValue());
	     }
		return true;
	}*/

	@Override
	public TagElement process(AssetDataRecord assetRec) throws Exception {
		
		return insert(assetRec.getReadTime(),assetRec.getAssetID(),assetRec.getSiteName(),assetRec.getBlockId(),assetRec.getPosition(),assetRec.getUnitId(),assetRec.getTagId(),assetRec.getValue());

	}

}
