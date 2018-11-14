package com.example.swarmaxon;

import lombok.Value;

@Value
public class ServletVisitedEvt {

    String servletPath;
    String remoteAddress;

}
