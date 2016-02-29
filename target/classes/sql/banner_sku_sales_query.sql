banner详情:

select 
if(views.source_id is null,sales.source_id,views.source_id) as source_id,
views.pv,
views.uv,
sales.sales_volume as paid_count,
sales.sales_amount as paid_amount,
sales.user_count as paid_user,
favor.favor_numbers as favor_user,
cart.cart_numbers as cart_user
from (
    select 
    source_id,
    count(distinct gi) as uv,
    count(*) as pv 
    from mxyc04 where eventname="sku_c" 
    and banner_id = cast({banner_id} as string)
    and cdate >= {start_date}
    and cdate < from_unixtime(unix_timestamp(date_add({end_date}, 1)), 'yyyy-MM-dd')
    and source != "1"
    and source_id is not null
    group by source_id 
) as views
full join (
    select sum(order_sources.goods_number) as sales_volume,
    order_sources.source_id,
    sum(order_sources.goods_paid_price) as sales_amount,
    count(distinct order_info.user_id) as user_count
    from ecs_mysql_order_sources  as order_sources
    inner join ecs_order_info as order_info using(order_id)
    inner join ecs_order_payment as payment using(pay_order_id) 
    where payment.pay_order_status = 1
        and order_sources.ts >= {start_date}
        and order_sources.ts < from_unixtime(unix_timestamp(date_add({end_date}, 1)), 'yyyy-MM-dd')
        and order_sources.banner_id = cast({banner_id} as string)
    group by order_sources.source_id
) as sales
using(source_id)
left join 
(
select source_id, 
    count(distinct gi) as favor_numbers
from mxyc04
where eventname = 'favor'
    and banner_id = cast({banner_id} as String)
    and cdate >= {start_date}
    and cdate < from_unixtime(unix_timestamp(date_add({end_date}, 1)), 'yyyy-MM-dd')
    and source != "1"
group by source_id
) as favor on views.source_id = favor.source_id
left join 
(
select source_id, 
    count(distinct gi) as cart_numbers
from mxyc04
where eventname = 'addcart_c'
    and banner_id = cast({banner_id} as String)
    and cdate >= {start_date}
    and cdate < from_unixtime(unix_timestamp(date_add({end_date}, 1)), 'yyyy-MM-dd')
    and source != "1"
group by source_id
) as cart on views.source_id = cart.source_id
where views.uv > 100 
    or sales.sales_volume >0 
    or favor.favor_numbers >0 
    or cart.cart_numbers >0