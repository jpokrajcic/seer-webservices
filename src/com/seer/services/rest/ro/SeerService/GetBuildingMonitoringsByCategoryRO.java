package com.seer.services.rest.ro.SeerService;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GetBuildingMonitoringsByCategoryRO {
    public String sessionToken;
    public Long buildingId;
    public Long categoryId;
}
