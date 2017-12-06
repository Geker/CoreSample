// package com.bn.dubbo.client;
//
// import java.security.PrivateKey;
// import java.security.PublicKey;
// import java.security.Signature;
// import java.util.ArrayList;
// import java.util.Collection;
// import java.util.Collections;
// import java.util.Comparator;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.Map.Entry;
// import java.util.SortedMap;
// import java.util.TreeMap;
//
// import org.apache.commons.codec.binary.Base64;
// import org.apache.commons.logging.Log;
// import org.apache.commons.logging.LogFactory;
// import org.springframework.util.StringUtils;
//
/// **
// * <p> Title: SignUtils </p>
// * <p> Description: </p>
// * <p> Copyright: ufo Copyright (C) 2016 </p>
// *
// * @author GaoZhi
// * @version
// * @since 2016年8月15日
// */
// public class SignUtils {
//
// private static final String SIG_ALG = "SHA1withRSA";
// private static Log logger = LogFactory.getLog(SignUtils.class);
// private static final char QP_SEP_A = '&';
// private static final char QP_SEP_B = '|';
// private static final char QP_SEP_C = '{';
// private static final char QP_SEP_D = '}';
// private static final String NAME_VALUE_SEPARATOR = "=";
// private static final String CHARSET = "UTF-8";
//
// private static String getNvPairsStr(Map<String, Object> nvPairs, String charset, boolean ignoreEmptyValue) throws Exception {
// Map<String, Object> sortedNvPairs = sort(nvPairs);
//
// StringBuilder nvPairsStr = new StringBuilder();
// for (Entry<String, Object> pair : sortedNvPairs.entrySet()) {
// if (ignoreEmptyValue && pair.getValue() != null
// && !StringUtils.hasText(getValueStr(pair.getValue(), charset, ignoreEmptyValue))) {
// continue;
// }
// if (nvPairsStr.length() > 0) {
// nvPairsStr.append(QP_SEP_A);
// }
// nvPairsStr.append(pair.getKey());
// nvPairsStr.append(NAME_VALUE_SEPARATOR);
// nvPairsStr.append(getValueStr(pair.getValue(), charset, ignoreEmptyValue));
// }
// return nvPairsStr.toString();
// }
//
// @SuppressWarnings("unchecked")
// private static String getValueStr(Object value, String charset, boolean ignoreEmptyValue) throws Exception {
// if (value instanceof Collection) {
// List<String> list = new ArrayList<String>(((Collection<Object>) value).size());
// for (Object v : (Collection<Object>) value) {
// list.add(getValueStr(v, charset, ignoreEmptyValue));
// }
// StringBuilder nvPairsStr = new StringBuilder();
// Collections.sort(list, new AscendingComparator());
// nvPairsStr.append(QP_SEP_C);
// for (String v : list) {
// if (nvPairsStr.length() > 1) {
// nvPairsStr.append(QP_SEP_B);
// }
// nvPairsStr.append(v);
// }
// nvPairsStr.append(QP_SEP_D);
// return nvPairsStr.toString();
// }
// else if (value instanceof Map) {
// return getNvPairsStr((Map<String, Object>) value, charset, ignoreEmptyValue);
// }
// else {
// return value.toString();
// }
// }
//
// private static Map<String, Object> sort(Map<String, Object> nvPairs) {
// SortedMap<String, Object> sortedNvPairs;
// sortedNvPairs = new TreeMap<String, Object>(new AscendingComparator());
// sortedNvPairs.putAll(nvPairs);
// return sortedNvPairs;
// }
//
// static class AscendingComparator implements Comparator<String> {
//
// @Override
// public int compare(String o1, String o2) {
// return o1.compareTo(o2);
// }
// }
//
// public static boolean verify(Map<String, Object> data, String signDataKey, PublicKey pubKey) throws Exception {
//
// Map<String, Object> signData = new HashMap<String, Object>(data);
// signData.remove(signDataKey);
// String plainText = getNvPairsStr(signData, CHARSET, true);
// logger.info("$$验签的数据串plainText为:" + plainText);
//
// Signature signature = Signature.getInstance(SIG_ALG);
// signature.initVerify(pubKey);
// signature.update(plainText.getBytes(CHARSET));
// if (data.get(signDataKey) == null) {
// return false;
// }
// return signature.verify(Base64.decodeBase64(data.get(signDataKey).toString()));
// }
//
// public static String sign(Map<String, Object> data, PrivateKey priKey) throws Exception {
//
// String plainText = getNvPairsStr(data, CHARSET, true);
//
// logger.info("签名的数据串plainText为:" + plainText);
// Signature signature = Signature.getInstance(SIG_ALG);
// signature.initSign(priKey);
// signature.update(plainText.getBytes(CHARSET));
// return Base64.encodeBase64String(signature.sign());
// }
// }
