from influxdb_client import InfluxDBClient
from influxdb_client.client.write_api import SYNCHRONOUS
import influxdb_client
import time,datetime,sched,random
url = 'http://10.114.54.124:8086'
token = 'k1BgPCPjDWu8BNqtBgB-pQNx2DRmLI4LfOf_UKMMg4hXSJgFru1y1vt3BqSIC4SD0QoYOqwAtmJ6mTddxZ_gBw=='
org = 'oppo'
bucket = 'appPerf'
          
# with InfluxDBClient(url=url, token=token, org=org) as client:
#     query_api = client.query_api()
          
#     tables = query_api.query('from(bucket: "appPerf") |> range(start: -1d)')
          
#     for table in tables:
#         for record in table.records:
#             print(str(record["_time"]) + " - " + record.get_measurement()
#                             + " " + record.get_field() + "=" + str(record.get_value()))
from threading import Timer
def run():
    now = datetime.datetime.now()
    ts = now.strftime('%Y-%m-%d %H:%M:%S')
    print('do func time :', ts)
    with InfluxDBClient(url=url, token=token, org=org) as client:
        write_api = client.write_api(write_options=SYNCHRONOUS)
        p = influxdb_client.Point("my_measurement").tag("location", "Prague").field("temperature", random.Random().uniform(1,10000))
        write_api.write(bucket=bucket, org=org, record=p)
    # loop_monitor()
    t =  Timer(4.0,run)
    t.start()
run()
def loop_monitor():
    s = sched.scheduler(time.time, time.sleep)  # 生成调度器
    s.enter(5, 1, run, ())
    s.run()
# loop_monitor()




    
    