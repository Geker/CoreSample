package com.shaunabram.springmvc.filedownload;
public class App {
    public static void main(String[] args) {

        String sql = "SELECT\n"
                + "    FORMATDATETIME(ord.order_time,'yyyy-MM-dd HH:mm') AS 订单日期,FORMATDATETIME(ord.date_updated,'yyyy-MM-dd HH:mm')\n"
                + "    AS 订单完成时间,ord.order_no AS 订单号,\n" + "    CASE ord.order_status  \n" + "    WHEN 'SUCCESS'  THEN  '成功'\n"
                + "    WHEN 'CANCEL'  THEN  '已取消(未/已付款)'\n" + "    WHEN 'PAYED'  THEN  '已付款'\n"
                + "    WHEN 'WAIT_RECEIPT'      THEN  '待收货'\n" + "    WHEN 'REFUNDING'  THEN  '退款中'\n"
                + "    WHEN 'RECEIVED' THEN '待确认退货'\n" + "    else  ord.order_status  \n" + "    END\n" + "    AS 订单状态,\n"
                + "    ord.relation_order_no  AS 退货关联的原始订单号,ord.bill_id  AS erp单号,\n"
                + "    goods.goods_code AS  商品代码, goods.goods_num  AS  商品数量,\n" + "    \n" + "    ord.amount  AS 订单支付金额,\n"
                + "    casewhen(ORD.order_op_type='SCAN','扫码购',CASEWHEN(ord.delivery_way='2','自提','配送'))\n"
                + "    AS 订单类型, ord.ORDER_REMARK AS 备注,  ord.consignee_name AS  收货人,ord.consignee_address AS  收货地址, ord.CONSIGNEE_PHONE  AS  电话,\n"
                + "    DELIVERY_TIME_START AS  配送开始时间,DELIVERY_TIME_END AS  配送截止时间,\n" + "    CASE\n"
                + "        WHEN goods.goods_code IN('1571','1570','1572')\n" + "        THEN '预售商品'\n" + "        ELSE '普通商品'\n"
                + "    END  AS 商品类型,IFNULL(goods.goods_price,goods.preferential_price) AS 商品价格, \n"
                + "    dis.discount_amount AS  折扣金额,dis.goods_amount AS  商品折后总价格,dis.yh_discount AS  \"优惠金额分摊[优惠券]\" , goods.goods_bar_code  AS 条码,goods.goods_name AS  商品名称,\n"
                + "    goods.goods_index  AS 订单内商品序号\n" + "FROM\n"
                + "    v_ec_sales_order_detail ord,v_ec_sales_goods_detail goods,discount dis\n" + "WHERE\n"
                + "ord.order_no=goods.order_no\n" + "--AND     ord.order_status  in ('SUCCESS','PAYED')\n"
                + "AND     goods.goods_level_type!='3'"

                + "and ord.order_time>=?  and ord.order_time<? \n" + "and\n"
                + "dis.order_no=ord.order_no and dis.goods_code=goods.goods_code\n" + "--and goods.goods_code in ('1571','1570','1572')\n"
                + "ORDER BY 订单日期,订单号,订单内商品序号 ;";
        System.out.println(sql);
    }
}
