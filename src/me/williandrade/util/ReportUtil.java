/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.williandrade.util;

import java.util.List;
import me.williandrade.report.XlsxReport;

/**
 *
 * @author williandrade.me
 */
public class ReportUtil {

    public static <T> XlsxReport toXlsx(List<T> listDTO) {
        return new XlsxReport(listDTO);
    }
}
