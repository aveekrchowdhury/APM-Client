package com.ge.apm.fieldsetmapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.ge.apm.dto.AssetDataRecord;

public class AssetFieldSetMapper implements FieldSetMapper<AssetDataRecord> {

	@Override
	public AssetDataRecord mapFieldSet(FieldSet fieldSet) throws BindException {
		AssetDataRecord assetData = new AssetDataRecord();
		
		//assetData.setReadTime(fieldSet.getValue("readTime"));
		
		return null;
	}

}
