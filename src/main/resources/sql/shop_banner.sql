banner:

select
    concat("http://m2.pimg.cn/images/images",base.image_url_app) as img_url,
    views.banner_id as banner_id,
    views.pv as pv,
    views.uv as uv,
    base.title as title,
    base.description,
    base.create_time,
    base.start_time,
    base.end_time,
    base.position_id,
    price.sale_count as paid_count,
    price.sale_amount as paid_amount,
    price.pay_uv,
    favor.favor_numbers as favor_user,
    cart.cart_numbers as cart_user,
    1 as conversion_rate
from
    (
    select 
        cast(banner_id as INT) as banner_id,
        count(gi) as pv,
        count(distinct gi) as uv
    from  mxyc04 
    where eventname in ('banner_c', 'shop_banner_c') 
        and cdate >= {start_date}
        and cdate < from_unixtime(unix_timestamp(date_add({end_date}, 1)), 'yyyy-MM-dd')
    group by banner_id
    )   as  views
inner   join
    (
    select 
        cast(con.id as INT) as banner_id,
        title,
        concat(tab.name,"/", pos.description) as description,
        con.create_time,
        con.start_time,
        if(support_endtime = 1, con.end_time,con.start_time) as end_time,
        position_id,
        image_url_app
    from promotion_content as con
    inner join promotion_position as pos on pos.id = con.position_id
    inner join promotion_tab as tab on pos.tab_id = tab.id
    where con.review = 1
          and image_url_app != ""
          and con.start_time <= con.end_time
    )   as  base    on views.banner_id = base.banner_id
left   join
    (
    select  
        cast(banner_id as INT) as banner_id,
        sum(osources.goods_number) as sale_count,
        sum(osources.goods_paid_price) as sale_amount,
        count(distinct opay.user_id) as pay_uv
    from ecs_mysql_order_sources as osources
    inner join ecs_order_info as oinfo using(order_id)
    inner join ecs_order_payment as opay on oinfo.pay_order_id = opay.pay_order_id
    where opay.pay_order_status = 1
        and banner_id is not null
        and osources.ts >= {start_date}
        and osources.ts < from_unixtime(unix_timestamp(date_add({end_date}, 1)), 'yyyy-MM-dd')
    group by banner_id
    )   as  price on views.banner_id = price.banner_id
left join 
    (
    select cast(banner_id as int) as banner_id, 
        count(distinct gi) as favor_numbers
    from mxyc04
    where eventname = 'favor'
        and cdate >= {start_date}
        and cdate < from_unixtime(unix_timestamp(date_add({end_date}, 1)), 'yyyy-MM-dd')
    group by banner_id
    ) as favor on views.banner_id = favor.banner_id
left join 
    (
    select cast(banner_id as int) as banner_id, 
        count(distinct gi) as cart_numbers
    from mxyc04
    where eventname = 'addcart_c'
        and cdate >= {start_date}
        and cdate < from_unixtime(unix_timestamp(date_add({end_date}, 1)), 'yyyy-MM-dd')
    group by banner_id
) as cart on views.banner_id = cart.banner_id
order by views.uv desc