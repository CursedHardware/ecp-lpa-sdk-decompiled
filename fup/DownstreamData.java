package com.eastcompeace.lpa.sdk.fup;

import java.util.ArrayList;
import java.util.List;

public class DownstreamData {
    List<EveryCardContent> allCardContents = new ArrayList();

    public List<EveryCardContent> getAllCardContents() {
        return this.allCardContents;
    }

    public void setAllCardContents(List<EveryCardContent> list) {
        this.allCardContents = list;
    }
}
