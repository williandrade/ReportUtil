/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.williandrade.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author williandrade.me
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface XlsxSheet {

    String numberFormat() default "#";

    String currencyFormat() default "R$#.##0_);(R$#.##0)";

    String dateFormat() default "dd/MM/yyyy hh:mm";

    String timeFormat() default "hh:mm:ss";

}
