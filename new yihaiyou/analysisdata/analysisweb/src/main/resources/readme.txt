billid,traderid,paymethodid,opdate,totalamt
create table sale (  billid         bigint,
  traderid       bigint,
  paymethodid bigint,
  opdate TIMESTAMP,
  totalamt DOUBLE
)ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t' partitioned by (date TIMESTAMP)



./sqoop import --append --connect "jdbc:sqlserver://192.168.1.131:1433;DatabaseName=SEMPR3_ESE_XMzuipin2014" --username esbb --password esbb --target-dir /user/hive/warehouse/sale  --m 1 --table sale --columns billid,traderid,paymethodid,opdate,totalamt --fields-terminated-by '\t' --incremental append  --check-column billid --last-value 0

./sqoop import --append --connect "jdbc:sqlserver://192.168.1.131:1433;DatabaseName=SEMPR3_ESE_XMzuipin2014" --username esbb --password esbb --target-dir /user/hive/warehouse/sale  --m 1 --table sale   --hive-import -m 5 --hive-table sale  --columns billid,traderid,paymethodid,opdate,totalamt --fields-terminated-by '\t' --incremental append  --check-column billid --last-value 0 

hive
select traderid,sum(totalamt) from sale where opdate > unix_timestamp('2014-05-03 10:10:10') group by traderid;
select count(traderid),sum(totalamt) from sale  where traderid in (56173,54107,57230,53879,55125,4495) group by traderid;

select datediff("2014-06-03",to_date(cast(opdate as string))) from sale limit 10;


select count(traderid),sum(totalamt) from sale s where s.traderid in (select traderid from (select traderid,max(opdate) opdate from sale group by traderid) a where datediff("2014-06-03",to_date(cast(opdate as string))) < 30 ) group by s.traderid;


select s.traderid,count(s.traderid),sum(totalamt),avg(totalamt) from sale s
join 
(select a.traderid from (select n.traderid,max(opdate) opdate from sale n group by n.traderid) a where datediff("2014-06-03",to_date(cast(opdate as string))) between 31 and 45 ) b
on s.traderid = b.traderid
group by s.traderid;

create table sale_daydiff_cached as
select n.traderid,max(opdate) opdate,datediff("2014-06-04",to_date(cast(max(opdate) as string))) daydiff from sale n group by n.traderid

select s.traderid,count(s.traderid),sum(totalamt),avg(totalamt) from sale_cached s
join 
(select a.traderid from sale_daydiff_cached a where daydiff between 31 and 45 ) b
on s.traderid = b.traderid
group by s.traderid;


select s.traderid,count(s.traderid),sum(totalamt),avg(totalamt) from 
(select * from sale_cached c where c.opdate between '2014-06-01' and '2014-06-04') s 
join 
sale_daydiff_cached d on s.traderid = d.traderid  where daydiff between 31 and 45 group by s.traderid;

select s.traderid,count(s.traderid),sum(totalamt),avg(totalamt) from sale_cached s
join 
sale_daydiff_cached d on s.traderid = d.traderid  
where daydiff between 0 and 500 and s.opdate > 1401552000000 and s.opdate <1401811200000 group by s.traderid;

select s.traderid,max(s.opdate),max(daydiff),count(s.traderid),sum(totalamt),avg(totalamt) from sale_cached s
join 
sale_daydiff_cached d on s.traderid = d.traderid  
where daydiff between 0 and 500 and s.opdate between UNIX_TIMESTAMP('2013-06-01 00:00:00') and UNIX_TIMESTAMP('2014-06-05 00:00:00') group by s.traderid;

select s.traderid,max(s.opdate),max(daydiff),count(s.traderid),sum(totalamt),avg(totalamt) from sale_cached s
join 
sale_daydiff_cached d on s.traderid = d.traderid  
where daydiff between 0 and 500  group by s.traderid;


select s.traderid,max(s.opdate),max(daydiff),count(s.traderid),sum(totalamt),avg(totalamt) from sale_cached s
join 
sale_daydiff_cached d on s.traderid = d.traderid  
where s.opdate between UNIX_TIMESTAMP('2013-06-01 00:00:00') and UNIX_TIMESTAMP('2014-06-05 00:00:00') group by s.traderid;
fewfewfewe
fewfewfeddddddd fewfe




