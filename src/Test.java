
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import me.williandrade.report.XlsxReport;
import me.williandrade.util.ReportUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author williandrade.me
 */
public class Test {

    public static void main(String[] args) throws Exception {
        List<TestDTO> testList = new ArrayList<>();
        testList.add(new TestDTO("William", 22, "Rua João Bettega, 644", 1000.50));

        /*
        Normal Way 
        
         */
//        ByteArrayOutputStream byteArrayOutputStream = ReportUtil.toXlsx(testList).generate();
        /*
        Custom Header
        
         */
//        XlsxReport xlsxReport = ReportUtil.toXlsx(testList);
//
//        CellStyle cellStyle = xlsxReport.getWorkbook().createCellStyle();
//        XSSFFont font = xlsxReport.getWorkbook().createFont();
//        font.setBold(true);
//        cellStyle.setFont(font);
//
//        ByteArrayOutputStream byteArrayOutputStream = xlsxReport.setTitleCellStyle(cellStyle).generate();
//
//        File file = new File("/Users/william/Documents/texte/teste.xlsx");
//        try (OutputStream outputStream = new FileOutputStream(file)) {
//            byteArrayOutputStream.writeTo(outputStream);
//        }
    }

}
