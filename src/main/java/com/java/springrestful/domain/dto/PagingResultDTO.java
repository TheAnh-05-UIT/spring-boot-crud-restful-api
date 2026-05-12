package com.java.springrestful.domain.dto;

public class PagingResultDTO {

    private MetaData metaData;

    private Object result;

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
