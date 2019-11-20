package com.seer.services.rest.ro.SeerService;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GetBuildingDataRO {
    public String sessionToken;
    public Long buildingId;
}
