/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.williandrade.report;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import me.williandrade.annotation.XlsxField;
import me.williandrade.annotation.XlsxSheet;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author williandrade.me
 */
public class XlsxReport<T> extends BasicReport {

    private CellStyle titleCellStyle = null;
    private List<T> listDTO;
    private String sheetName;
    private XSSFWorkbook wb;

    public XlsxReport() {
        wb = new XSSFWorkbook();
    }

    public XlsxReport(List<T> listDTO, String sheetName) {
        this();
        this.listDTO = listDTO;
        this.sheetName = sheetName;
    }

    public XlsxReport(List<T> listDTO) {
        this(listDTO, "First");
    }

    @Override
    public ByteArrayOutputStream generate() throws Exception {
        ByteArrayOutputStream result = new ByteArrayOutputStream();

        if (this.listDTO == null || this.listDTO.isEmpty()) {
            throw new Exception("List is null or empty");
        }
        T obj = this.listDTO.get(0);
        XlsxSheet xmlSheet = obj.getClass().getAnnotation(XlsxSheet.class);

        if (xmlSheet == null) {
            throw new Exception("Dont have [" + XlsxSheet.class.getCanonicalName() + "] annotation on class");
        }

        XSSFSheet singleSheet = wb.createSheet(this.sheetName);

        List<Method> getters = this.findAllGetters(obj);

        int totalOfColumns = 0;

        int i = 0;
        XSSFRow headRow = singleSheet.createRow(0);
        for (Method getter : getters) {
            String title = "";
            String methodName = getter.getName();

            Field field = this.getFieldFromGetter(obj, methodName);
            XlsxField xlsxField = field.getAnnotation(XlsxField.class);

            //From Annotation
            if (xlsxField != null) {
                if (!xlsxField.title().trim().isEmpty()) {
                    title = xlsxField.title();
                }
            }

            //If null get using the hardcore way
            if (title == null || title.trim().isEmpty()) {
                title = this.convertDefaultCaseToNormalString(methodName.substring(3, methodName.length()));
            }

            XSSFCell titleCell = headRow.createCell(i);

            if (titleCellStyle != null) {
                titleCell.setCellStyle(titleCellStyle);
            }

            titleCell.setCellValue(title);
            i++;
            totalOfColumns++;
        }

        i = 1;
        for (T dto : this.listDTO) {
            XSSFRow row = singleSheet.createRow(i);
            int cellCount = -1;

            for (Method method : getters) {
                XlsxField.CellType choosedType = null;

                Field field = this.getFieldFromGetter(dto, method.getName());

                XlsxField xlsxField = field.getAnnotation(XlsxField.class);

                if (xlsxField != null) {
                    if (xlsxField.cellType() != XlsxField.CellType.NONE) {
                        choosedType = xlsxField.cellType();
                    }
                }

                Object resultGetter = this.invokeGetter(method, dto);

                if (resultGetter == null) {
                    row.createCell(++cellCount).setCellValue("");
                } else if (resultGetter instanceof String) {
                    row.createCell(++cellCount).setCellValue((String) resultGetter);
                } else if (resultGetter instanceof Date) {
                    XSSFCell customCell = row.createCell(++cellCount);
                    customCell.setCellStyle(this.getCellStyle(wb, xmlSheet, choosedType != null ? choosedType : XlsxField.CellType.DATE));
                    customCell.setCellValue((Date) resultGetter);
                } else if (resultGetter instanceof Integer) {
                    XSSFCell customCell = row.createCell(++cellCount);
                    customCell.setCellStyle(this.getCellStyle(wb, xmlSheet, choosedType != null ? choosedType : XlsxField.CellType.NUMBER));
                    customCell.setCellValue((Integer) resultGetter);
                } else if (resultGetter instanceof Double) {
                    XSSFCell customCell = row.createCell(++cellCount);
                    customCell.setCellStyle(this.getCellStyle(wb, xmlSheet, choosedType != null ? choosedType : XlsxField.CellType.NUMBER));
                    customCell.setCellValue((Double) resultGetter);
                } else if (resultGetter instanceof Float) {
                    XSSFCell customCell = row.createCell(++cellCount);
                    customCell.setCellStyle(this.getCellStyle(wb, xmlSheet, choosedType != null ? choosedType : XlsxField.CellType.NUMBER));
                    customCell.setCellValue((Float) resultGetter);
                }

            }

        }
        
        for (int columnI = 0; columnI <= totalOfColumns + 1; columnI++) {
            singleSheet.autoSizeColumn(columnI);
        }

        wb.write(result);
        return result;
    }

    private CellStyle getCellStyle(Workbook wb, XlsxSheet xmlSheet, XlsxField.CellType cellType) {
        CellStyle dateStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        String format = "";

        switch (cellType) {
            case NONE:
                break;
            case CURRENCY:
                format = xmlSheet.currencyFormat();
                break;
            case DATE:
                format = xmlSheet.dateFormat();
                break;
            case NUMBER:
                format = xmlSheet.numberFormat();
                break;
            case TIME:
                format = xmlSheet.timeFormat();
                break;
            default:
                break;
        }

        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat(format));
        return dateStyle;
    }

    public XlsxReport setTitleCellStyle(CellStyle titleCellStyle) {
        this.titleCellStyle = titleCellStyle;
        return this;
    }

    public XSSFWorkbook getWorkbook() {
        return wb;
    }

}
