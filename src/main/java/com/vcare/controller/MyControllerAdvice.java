package com.vcare.controller;

import java.net.BindException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice {
	
	@ExceptionHandler(NonUniqueResultException.class)
public String handleNonUniqueResultException(NonUniqueResultException nonUniqueResultException){
return "exceptionhandle";
}
	
/*
 * @ExceptionHandler(ClassNotFoundException.class) public String
 * handelException(ClassNotFoundException classNotFoundException) {
 * return"exceptionhandle"; }
 * 
 * @ExceptionHandler(IllegalAccessException.class) public String
 * handelIllegalAccessException(IllegalAccessException illegalAccessException) {
 * return"exceptionhandle"; }
 * 
 * @ExceptionHandler(NoSuchFieldException.class) public String
 * handelNoSuchFieldException(NoSuchFieldException noSuchFieldException) {
 * return"exceptionhandle"; }
 * 
 * @ExceptionHandler(NoSuchMethodException.class) public String
 * handelNoSuchMethodException(NoSuchMethodException noSuchFieldException) {
 * return"exceptionhandle"; }
 * 
 * @ExceptionHandler(InterruptedException.class) public String
 * handelInterruptedException(InterruptedException interruptedException) {
 * return"exceptionhandle"; }
 * 
 * @ExceptionHandler(IndexOutOfBoundsException.class) public String
 * handelIndexOutOfBoundsException(Model model,IndexOutOfBoundsException
 * indexOutOfBoundsException) {
 * 
 * model.addAttribute("exception", "No data found"); return"exceptionhandle"; }
 * 
 * @ExceptionHandler(IllegalThreadStateException.class) public String
 * handelIllegalThreadStateException(IllegalThreadStateException
 * illegalThreadStateException) {
 * 
 * return"exceptionhandle"; }
 * 
 * @ExceptionHandler(InputMismatchException.class) public String
 * handelInputMismatchException(InputMismatchException inputMismatchException) {
 * return"exceptionhandle"; }
 * 
 * @ExceptionHandler(NumberFormatException.class) public String
 * handelNumberFormatException(NumberFormatException numberFormatException) {
 * return"exceptionhandle"; }
 * 
 * @ExceptionHandler(ArithmeticException.class) public String
 * handelArithmeticException(ArithmeticException ArithmeticException) {
 * return"exceptionhandle"; }
 * 
 * @ExceptionHandler(NoSuchElementException.class) public String
 * handelNoSuchElementException(NoSuchElementException NoSuchElementException) {
 * return"exceptionhandle"; }
 * 
 * @ExceptionHandler(NullPointerException.class) public String
 * handelNullPointerException(NullPointerException nullPointerException) {
 * return"exceptionhandle"; }
 */
/*
 * @ExceptionHandler(BindException.class) public String
 * handelBindException(BindException bindException) { return"exceptionhandle"; }
 */
	
//	@ExceptionHandler(SQLException.class)
//	    public String handelSQLException(SQLException nullPointerException,Model model,HttpSession session) {
//
//		  if(session.getAttribute("adminuniqexception")=="adminsave") {
//			  session.setAttribute("adminindexmsg","save");
//			  return"redirect:/adminlog";
//		  }
//	        return"indexlogin";
//	    }
	
}
