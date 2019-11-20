package com.seer.services.rest.ro.SeerService;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GetApartmentDataRO {
    public String sessionToken;
    public Long apartmentId;
}
