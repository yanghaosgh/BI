首页推荐:

select base.name as title,
       pv_uv.source_id as source_id,
       pv_uv.pv as pv,
       pv_uv.uv as uv,
       if(pay.order_count is null, 0, pay.order_count) as info_count,
       if(pay.sale_count is null, 0, pay.sale_count) as count,
       if(pay.sale_amount is null, 0, pay.sale_amount) as amount
from (
select source_id, 
       count(gi) as pv, 
       count(distinct gi) as uv
from mxyc04
where eventname = 'sku_c'
      and tab_1 = 'hot'
      and tab_2 = 'recommend'
      and cdate >= {start_date}
      and cdate < from_unixtime(unix_timestamp(date_add({end_date}, 1)), 'yyyy-MM-dd')
group by source_id
) as pv_uv
left join 
(
select mosources.source_id as source_id,
       sum(mosources.goods_number) as sale_count,
       count(distinct oinfo.order_id) as order_count,
       sum(mosources.goods_paid_price * mosources.goods_number) as sale_amount
from ecs_mysql_order_sources as mosources
inner join ecs_order_info as oinfo using(order_id)
inner join ecs_order_payment as opay using(pay_order_id)
where opay.pay_order_status = 1
      and tab_1 = 'hot'
      and tab_2 = 'recommend'
      and mosources.ts >= {start_date}
      and mosources.ts < from_unixtime(unix_timestamp(date_add({end_date},1)), 'yyyy-MM-dd')
group by source_id
) as pay using(source_id)
inner join
(
select gsource.id as source_id, 
       goods.goods_name as name   
from  ecs_goods_source as gsource
inner join ecs_goods as goods using(goods_id)
) as base on cast(pv_uv.source_id as BIGINT) = base.source_id
and pv_uv.pv > 20
order by pv_uv.pv desc