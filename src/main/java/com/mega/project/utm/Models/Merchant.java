package com.mega.project.utm.Models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MECH_DATA_PROFILE")
public class Merchant {

    @Id
    @Column(name = "MECH_NBR")
    private String merchNum;

    @Column(name = "MECH_LCL_NAME")
    private String merchName;

    @Column(name = "MECH_CATEGORY_VISA")
    private String categoryVisa;

    @Column(name = "MECH_STATUS")
    private String merchStatus;

    @Column(name = "MECH_CITY")
    private String merchCity;

    @Column(name = "MECH_BASE_CURRENCY")
    private String merchCurrency;

    @Column(name = "MECH_BRANCH")
    private String merchBranch;

    @Column(name = "MECH_MAIL_TEL_IND")
    private String mailTelInd;

    @Column(name = "MECH_IND_CODE")
    private String indCode;

    @Column(name = "MECH_PROG_OFFER1")
    private String progOffer1;

    @Column(name = "MECH_PARENT_MECH")
    private String parentMerch;

    @Column(name = "MECH_PROFILE_ID")
    private String profileId;

    @Column(name = "MECH_OPEN_DATE")
    private Long openDate;

    @Column(name = "MECH_DATE_MAINT")
    private String dateMaint;

    @Column(name = "MECH_TIME_MAINT")
    private String timeMaint;

    @Column(name = "MBPF_PROFILE_ID")
    private String mbpfProfileId;

    @Column(name = "MBPF_OWNER2_NAME2")
    private String mbpfOwner2Name2;

}
