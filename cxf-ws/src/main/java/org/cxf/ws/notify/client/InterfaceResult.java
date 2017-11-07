
package org.cxf.ws.notify.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * interfaceResult complex type的 Java 类。
 *
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="interfaceResult"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isSuccess" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "interfaceResult", propOrder = {
    "isSuccess",
    "message"
})
public class InterfaceResult {

    protected Boolean isSuccess;
    protected String message;

    /**
     * 获取isSuccess属性的值。
     *
     * @return
     *         possible object is
     *         {@link Boolean }
     *
     */
    public Boolean isIsSuccess() {
        return isSuccess;
    }

    /**
     * 设置isSuccess属性的值。
     *
     * @param value
     *            allowed object is
     *            {@link Boolean }
     *
     */
    public void setIsSuccess(Boolean value) {
        this.isSuccess = value;
    }

    /**
     * 获取message属性的值。
     *
     * @return
     *         possible object is
     *         {@link String }
     *
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置message属性的值。
     *
     * @param value
     *            allowed object is
     *            {@link String }
     *
     */
    public void setMessage(String value) {
        this.message = value;
    }

    @Override
    public String toString() {
        return "InterfaceResult [isSuccess=" + isSuccess + ", message=" + message + "]";
    }

}
