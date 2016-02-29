
2
banner流量数据明细：
cdate、banner_id,position、pv、uv、paid_count、paid_amount,

SELECT '2016-02-25' as cdate,vs.banner_id,vs.position,vs.pv as pv,vs.uv as uv,if(pay.sale_count is null,0,pay.sale_count) AS paid_count,if(pay.sale_amount is null,0,pay.sale_amount) AS paid_amount FROM (SELECT banner_id,source_id,count(gi) AS pv,count(DISTINCT gi) AS uv FROM mxyc_daily WHERE eventname IN ('banner_c', 'shop_banner_c') AND banner_id <> '' AND cdate = '2016-02-25' GROUP BY banner_id,position) AS vs LEFT JOIN (SELECT banner_id,sum(pay_count) AS sale_count,sum(pay_amount) AS sale_amount FROM pay_daily WHERE cdate='2016-02-25' GROUP BY banner_id,position) AS pay ON pay.banner_id = vs.banner_id and pay.position = vs.position WHERE vs.uv > 0 OR pay.sale_count > 0 