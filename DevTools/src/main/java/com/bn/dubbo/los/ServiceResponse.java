// package com.bn.dubbo.los;
//
// import java.io.Serializable;
// import java.util.Locale;
//
// import org.springframework.context.MessageSource;
//
//
/// **
// *
// * <p>
// * Title: ServiceResponse
// * </p>
// * <p>
// * Description: LOS服务响应
// * </p>
// * <p>
// * Copyright: openlo.cn Copyright (C) 2016
// * </p>
// *
// * @author wangzq
// *
// * @version
// * @since 2016年4月13日
// */
// public class ServiceResponse implements Serializable {
//
// private static final long serialVersionUID = -6403723080385559390L;
//
// public static final String FIELD_MODEL = "model";
// public static final String FIELD_RESPONSE_CODE = "responseCode";
// public static final String FIELD_RESPONSE_MSG = "responseMsg";
// public static final String FIELD_FAIL_CAUSE = "failCause";
//
// private Object model;
//
// private String responseCode;
//
// private String responseMsg;
//
// private Exception failCause;
//
// /**
// * 构造一个ServiceResponse对象
// *
// * @param code 6位数编码，非空
// * @param messageSource MessageSource，非空
// * @param locale Locale
// * @param args 构造Message的参数列表
// * @return
// */
// public static ServiceResponse getServiceResponse(String code, MessageSource messageSource, Locale locale, Object... args) {
// ServiceResponse resp = new ServiceResponse();
// resp.setResponseCode(code);
// // 如果locale为空，则MessageSource会取JVM默认的Locale
// if (messageSource != null) {
// // if (locale == null) {
// // locale = LocaleHolder.get();
// // }
// String responseMsg = messageSource.getMessage(code, args, locale);
// resp.setResponseMsg(responseMsg);
// }
// return resp;
// }
//
// public Object getModel() {
// return model;
// }
//
// public void setModel(Object model) {
// this.model = model;
// }
//
// public String getResponseCode() {
// return responseCode;
// }
//
// public void setResponseCode(String responseCode) {
// this.responseCode = responseCode;
// }
//
// public String getResponseMsg() {
// return responseMsg;
// }
//
// public void setResponseMsg(String responseMsg) {
// this.responseMsg = responseMsg;
// }
//
// public Exception getFailCause() {
// return failCause;
// }
//
// public void setFailCause(Exception failCause) {
// this.failCause = failCause;
// }
//
// }
