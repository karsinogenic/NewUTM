package com.mega.project.utm.services;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.mega.project.utm.Models.AMLA.AmlaMerchantRuleResult;
import com.mega.project.utm.Models.AMLA.AmlaRuleResult;

public class ExcelWriteRead {

    public void writeXLSAmlaMerchant(List<AmlaMerchantRuleResult> list, String filenamepath) {

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        // hssfWorkbook.writeProtectWorkbook("mega123", "megawatt");
        HSSFSheet hssfSheet = hssfWorkbook.createSheet();
        HSSFRow row = hssfSheet.createRow(0);
        row.createCell(0).setCellValue("Nomer");
        row.createCell(1).setCellValue("MID");
        row.createCell(2).setCellValue("Merchant Name");
        row.createCell(3).setCellValue("Rule");
        row.createCell(4).setCellValue("Total Trx");
        row.createCell(5).setCellValue("Frequency");
        row.createCell(6).setCellValue("MCC");
        row.createCell(7).setCellValue("Owner Flag");
        row.createCell(8).setCellValue("Memo");
        row.createCell(9).setCellValue("Status");
        row.createCell(10).setCellValue("Post Date");
        row.createCell(11).setCellValue("Review By");
        row.createCell(12).setCellValue("Approval By");
        row.createCell(13).setCellValue("Approval Status");

        for (int i = 0; i < list.size(); i++) {
            row = hssfSheet.createRow(i + 1);

            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(list.get(i).getMerchNumber());
            row.createCell(2).setCellValue(list.get(i).getMerchLocalName());
            row.createCell(3).setCellValue(list.get(i).getTriggeredRule());
            row.createCell(4).setCellValue(list.get(i).getTotalTrx());
            row.createCell(5).setCellValue(
                    list.get(i).getFrequency() == null ? "" : list.get(i).getFrequency().toString());
            row.createCell(6)
                    .setCellValue(list.get(i).getMcc() == null ? "" : list.get(i).getMcc().toString());
            row.createCell(7)
                    .setCellValue(list.get(i).getOwnerMemo() == null ? "" : list.get(i).getOwnerMemo());
            row.createCell(8).setCellValue(list.get(i).getMemo() == null ? "" : list.get(i).getMemo());
            row.createCell(9).setCellValue(list.get(i).getStatus() == null ? "" : list.get(i).getStatus());
            row.createCell(10).setCellValue(list.get(i).getPostDate());
            row.createCell(11).setCellValue(list.get(i).getReviewBy() == null ? "" : list.get(i).getReviewBy());
            row.createCell(12).setCellValue(list.get(i).getApprovedBy() == null ? "" : list.get(i).getApprovedBy());
            row.createCell(13).setCellValue(
                    list.get(i).getIsApproved() == null ? list.get(i).getIsSent() ? "Pending" : "No Memo"
                            : list.get(i).getIsApproved() ? "Approved" : "Denied");

        }

        try {
            FileOutputStream out = new FileOutputStream(filenamepath);
            hssfWorkbook.write(out);
            hssfWorkbook.close();
            // System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void writeXLSAmla(List<AmlaRuleResult> list, String filenamepath) {

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        // hssfWorkbook.writeProtectWorkbook("mega123", "megawat");
        HSSFSheet hssfSheet = hssfWorkbook.createSheet();
        HSSFRow row = hssfSheet.createRow(0);
        row.createCell(0).setCellValue("Nomer");
        row.createCell(1).setCellValue("Id");
        row.createCell(2).setCellValue("Account Number");
        row.createCell(3).setCellValue("CH Name");
        row.createCell(4).setCellValue("Company Name");
        row.createCell(5).setCellValue("Post Date");
        row.createCell(6).setCellValue("APUPPT");
        row.createCell(7).setCellValue("Block Acc");
        row.createCell(8).setCellValue("Status");
        row.createCell(9).setCellValue("Memo");
        row.createCell(10).setCellValue("Action Date");
        row.createCell(11).setCellValue("Review By");
        row.createCell(12).setCellValue("Approval By");
        row.createCell(13).setCellValue("Approval Status");

        for (int i = 0; i < list.size(); i++) {
            row = hssfSheet.createRow(i + 1);

            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(list.get(i).getId());
            row.createCell(2).setCellValue(list.get(i).getAccNumber());
            row.createCell(3).setCellValue(list.get(i).getChName());
            row.createCell(4).setCellValue(list.get(i).getCompanyName());
            row.createCell(5).setCellValue(list.get(i).getPostDate().toString());
            row.createCell(6)
                    .setCellValue(list.get(i).getApuppt());
            row.createCell(7)
                    .setCellValue(list.get(i).getBlockAcc());
            row.createCell(8).setCellValue(list.get(i).getStatus());
            row.createCell(9).setCellValue(list.get(i).getMemo());
            row.createCell(10)
                    .setCellValue(list.get(i).getActionDate() == null ? "" : list.get(i).getActionDate().toString());
            row.createCell(11).setCellValue(list.get(i).getReviewBy() == null ? "" : list.get(i).getReviewBy());
            row.createCell(12).setCellValue(list.get(i).getApprovedBy() == null ? "" : list.get(i).getApprovedBy());
            row.createCell(13).setCellValue(
                    list.get(i).getIsApproved() == null ? list.get(i).getIsSent() ? "Pending" : "No Memo"
                            : list.get(i).getIsApproved() ? "Approved" : "Denied");

        }

        try {
            FileOutputStream out = new FileOutputStream(filenamepath);
            hssfWorkbook.write(out);
            hssfWorkbook.close();
            // System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
