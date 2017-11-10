package uk.gov.digital.ho.pttg.xlsx;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;
import uk.gov.digital.ho.pttg.dto.FeedbackCsvView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelView extends AbstractXlsView {

@Override
protected void buildExcelDocument(Map<String, Object> model,
                                  Workbook workbook,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {

    // change the file name
    response.setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");

    @SuppressWarnings("unchecked")
    List<FeedbackCsvView> users = (List<FeedbackCsvView>) model.get("feedback");

    // create excel xls sheet
    Sheet sheet = workbook.createSheet("User Detail");
    sheet.setDefaultColumnWidth(30);

    // create style for header cells
    CellStyle style = workbook.createCellStyle();
    Font font = workbook.createFont();
    font.setFontName("Arial");
    style.setFillForegroundColor(HSSFColor.BLUE.index);
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    font.setBold(true);
    font.setColor(HSSFColor.WHITE.index);
    style.setFont(font);


    // create header row
    Row header = sheet.createRow(0);
    header.createCell(0).setCellValue("Firstname");
    header.getCell(0).setCellStyle(style);
    header.createCell(1).setCellValue("LastName");
    header.getCell(1).setCellStyle(style);
    header.createCell(2).setCellValue("Age");
    header.getCell(2).setCellStyle(style);



    int rowCount = 1;

    for(FeedbackCsvView user : users){
        Row userRow =  sheet.createRow(rowCount++);
        userRow.createCell(0).setCellValue(user.getUserId());
        userRow.createCell(1).setCellValue("timestamp here");
        //userRow.createCell(2).setCellValue(user.getDetail());

        }

}

}