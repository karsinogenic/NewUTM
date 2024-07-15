package com.mega.project.utm.Models.AMLA;

import lombok.Data;

@Data
public class CombinedAmlaMerchant {

    private AmlaMerchantRuleResult amlaMerchantRuleResult;
    private MerchMenyimpangA merchMenyimpangA;
    private MerchMenyimpangB merchMenyimpangB;

    public CombinedAmlaMerchant(AmlaMerchantRuleResult amlaMerchantRuleResult, MerchMenyimpangA merchMenyimpangA,
            MerchMenyimpangB merchMenyimpangB) {
        this.amlaMerchantRuleResult = amlaMerchantRuleResult;
        this.merchMenyimpangA = merchMenyimpangA;
        this.merchMenyimpangB = merchMenyimpangB;
    }

}
