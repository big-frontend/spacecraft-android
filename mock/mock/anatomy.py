"""
Basic skeleton of a mitmproxy addon.

Run as follows: mitmproxy -s anatomy.py
"""

import logging
from mitmproxy import ctx
from mitmproxy import http
import json
import base64
from urllib.parse import urlparse
from urllib.parse import parse_qs
def base64_decode_url(data):
    value = data.replace('-', '+').replace('_', '/')
    value += '=' * (len(value) % 4)
    return str(base64.urlsafe_b64decode(value), 'utf-8')


def get_path_params(url):
    parsed_url = urlparse(url)
    try:
        captured_value = parse_qs(parsed_url.query)['params'][0]
        return parsed_url.path,base64_decode_url(captured_value)
    except BaseException:
        return parsed_url.path,parsed_url.query
    
class Counter:
    def __init__(self):
        self.num = 0

    def request(self, flow):
        # self.num = self.num + 1
        # logging.info("We've seen %d flows" % self.num)
        pass

    def response(self, flow):
        # statinfo = flow.request.headers.get("statinfo")
        # sv = flow.request.headers.get("sv")
        # logging.error(f"{statinfo} {sv}")
        if "v1/getad" not in flow.request.path: return
        pageid = flow.request.query.get('pageid')
        pos_id = flow.request.query.get('pos_id')
        if not pos_id:
            pos_id = flow.request.query.get("pos_id_arr")
        
        if not pos_id:
            t = flow.request.get_text()
            if not t :
                logging.error(f"{flow.request.url} 没有请求体")
                logging.error(flow.response.get_text())
                return
            r = json.loads(t)
            if r.get("kuaishou_req_info"):
                logging.info(t)
                for k in r.get("kuaishou_req_info").keys():
                    # logging.info(k)
                    pos_id = k
        
        #7600/7601 首页卡片
        if pageid == '1000': 
            logging.info(f"{flow.request.pretty_url}  {flow.request.path} {pageid}")
            # pageid = flow.request.query["pageid"]
            # with open("android_delay_time=-1(bs=150).txt", 'r') as fcc_file:
            with open("./android_delay_time=100(bs=0).txt", 'r') as fcc_file:
            # data = json.loads(flow.response.get_text())
                # logging.info(flow.response.get_text())
                data = json.load(fcc_file)
                flow.response.text = json.dumps(data)

        #1200/1120  直接下载, download/invokeStyle 直接下载 mock数据
        # if pageid=='1200' and pos_id == '1205': 
        #     with open("1200_1205_tencent.json", 'r') as fcc_file:
        #     # data = json.loads(flow.response.get_text())
        #         # logging.info(flow.response.get_text())
        #         data = json.load(fcc_file)
        #         flow.response.text = json.dumps(data)

        #她他圈
        # 1200/1205  直接下载, download/invokeStyle 直接下载 mock数据
        if pageid=='1200' and pos_id == '1205': 
            with open("1200_1205.json", 'r') as fcc_file:
            # data = json.loads(flow.response.get_text())
                # logging.info(flow.response.get_text())
                data = json.load(fcc_file)
                flow.response.text = json.dumps(data)
        # if pageid == '1200' and pos_id == '9301': 
        #     with open("./9301.txt", 'r') as fcc_file:
        #     # data = json.loads(flow.response.get_text())
        #         logging.info(flow.response.get_text())
        #         data = json.load(fcc_file)
        #         flow.response.text = json.dumps(data)

        # 1100/1120 首页流
        # if pageid=='1100' and pos_id == '1120': 
        #         # with open("1100_1120.json", 'r') as fcc_file:
        #         with open("1100_1120_video_style.json", 'r') as fcc_file:
        #         # with open("1100_1120_huawei.json", 'r') as fcc_file:
        #         # data = json.loads(flow.response.get_text())
        #             # logging.info(flow.response.get_text())
        #             data = json.load(fcc_file)
        #             flow.response.text = json.dumps(data)

        # #视频激励广告   
        # if pageid =='9300' and pos_id == '9301':
        #     with open('9300_9301_encourage_video.json', 'r') as fcc_file:
        #         download_scheme_uri ="meetyou.linggan:///web?params=eyJ2ZXJzaW9uQ29kZSI6IjMuOS44Ni4wMDAuMDcxMC4wOTU4IiwidmVyc2lvbk5hbWUiOiIzLjkuODYuMDAwLjA3MTAuMDk1OCIsInBhY2thZ2VOYW1lIjoiY29tLmppZmVuLnF1a2FuIiwibmF2QmFyU3R5bGUiOiJ3aGl0ZSIsImFwcE5hbWUiOiLotqPlpLTmnaEiLCJwZXJtaXNzaW9ucyI6W3sidGl0bGUiOiLmi6XmnInlrozlhajnmoTnvZHnu5zorr_pl67mnYPpmZAiLCJjb250ZW50Ijoi5YWB6K646K-l5bqU55So5Yib5bu6572R57uc5aWX5o6l5a2X5ZKM5L2_55So6Ieq5a6a5LmJ572R57uc5Y2P6K6u44CC5rWP6KeI5Zmo5ZKM5YW25LuW5p-Q5Lqb5bqU55So5o-Q5L6b5LqG5ZCR5LqS6IGU572R5Y-R6YCB5pWw5o2u55qE6YCU5b6E77yM5Zug5q2k5bqU55So5peg6ZyA6K-l5p2D6ZmQ5Y2z5Y-v5ZCR5LqS6IGU572R5Y-R6YCB5pWw5o2u44CCIn0seyJ0aXRsZSI6IuS_ruaUueaIluWIoOmZpOaCqOeahFNE5Y2h5Lit55qE5YaF5a65IiwiY29udGVudCI6IuWFgeiuuOivpeW6lOeUqOWGmeWFpVNE5Y2h44CCIn0seyJ0aXRsZSI6IuWFgeiuuOiuv-mXrue9kee7nCIsImNvbnRlbnQiOiLlhYHorrjor6XlupTnlKjorr_pl67nvZHnu5zov57mjqXvvIzlj6_og73kuqfnlJ9HUFJT5rWB6YeP44CCIn0seyJ0aXRsZSI6Iuivu-WPluaJi-acuueKtuaAgeWSjOi6q-S7vSIsImNvbnRlbnQiOiLlhYHorrjor6XlupTnlKjorr_pl67orr7lpIfnmoTnlLXor53lip_og73jgILmraTmnYPpmZDlj6_orqnor6XlupTnlKjnoa7lrprmnKzmnLrlj7fnoIHlkozorr7lpIdJROOAgeaYr-WQpuato-WkhOS6jumAmuivneeKtuaAgeS7peWPiuaLqOaJk-eahOWPt-eggeOAgiJ9LHsidGl0bGUiOiLmlLnlj5jnvZHnu5znirbmgIEiLCJjb250ZW50Ijoi5YWB6K646K-l5bqU55So5pu05pS5572R57uc6L-e5o6l55qE54q25oCB44CCIn0seyJ0aXRsZSI6Iuivt-axguWuieijheaWh-S7tuWMhSIsImNvbnRlbnQiOiLlhYHorrjor6XlupTnlKjor7fmsYLlronoo4Xmlofku7bljIXjgILnsbvmnI3liqHnoa7lrprmgqjnmoTlpKfmpoLkvY3nva7jgIIifSx7InRpdGxlIjoi6K-75Y-W5a2Y5YKo5Y2h5Lit55qE5YaF5a65IiwiY29udGVudCI6IuWFgeiuuOivpeW6lOeUqOivu-WPluWtmOWCqOWNoeS4reeahOWGheWuueOAguaDheWGteS4i-WIhuS6q-iBlOezu-S6uuaVsOaNruOAgiJ9LHsidGl0bGUiOiLlhbPpl63lhbbku5blupTnlKgiLCJjb250ZW50Ijoi5YWB6K646K-l5bqU55So57uT5p2f5YW25LuW5bqU55So55qE5ZCO5Y-w6L-b56iL44CC5q2k5p2D6ZmQ5Y-v5a-86Ie05YW25LuW5bqU55So5YGc5q2i6L-Q6KGM44CCIn0seyJ0aXRsZSI6IumYsuatouaJi-acuuS8keecoCIsImNvbnRlbnQiOiLlhYHorrjor6XlupTnlKjpmLvmraLmiYvmnLrov5vlhaXkvJHnnKDnirbmgIHjgIIifSx7InRpdGxlIjoi5L-u5pS557O757uf6K6-572uIiwiY29udGVudCI6IuWFgeiuuOivpeW6lOeUqOivu-WGmeezu-e7n-eahOiuvue9ruaVsOaNruOAguaBtuaEj-W6lOeUqOWPr-iDveS8muegtOWdj-aCqOeahOezu-e7n-mFjee9ruOAgiJ9LHsidGl0bGUiOiLorr_pl67lrprkvY3pop3lpJblkb3ku6QiLCJjb250ZW50Ijoi5YWB6K646K-l5bqU55So5L2_55So5YW25LuW55qE5L2N572u5L-h5oGv5o-Q5L6b56iL5bqP5ZG95Luk44CC5q2k5p2D6ZmQ5L2_6K-l5bqU55So5Y-v5Lul5bmy5omwR1BT5oiW5YW25LuW5L2N572u5L-h5oGv5rqQ55qE6L-Q5L2c44CCIn0seyJ0aXRsZSI6IuaLjeaRhOeFp-eJh-WSjOW9leWItuinhumikSIsImNvbnRlbnQiOiLlhYHorrjor6XlupTnlKjkvb_nlKjnm7jmnLrmi43mkYTnhafniYflkozop4bpopHjgILmraTmnYPpmZDlj6_orqnor6XlupTnlKjpmo_ml7bkvb_nlKjnm7jmnLrvvIzogIzml6DpnIDmgqjnmoTnoa7orqTjgIIifSx7InRpdGxlIjoi5YWB6K645o6l5pS2V0xBTuWkmuaSrSIsImNvbnRlbnQiOiLlhYHorrjor6XlupTnlKjkvb_nlKjlpJrmkq3lnLDlnYDmjqXmlLblj5HpgIHliLBXTEFO572R57uc5LiK5omA5pyJ6K6-5aSH77yI6ICM5LiN5LuF5LuF5piv5oKo55qE5omL5py677yJ55qE5pWw5o2u5YyF44CC6K-l5pON5L2c55qE6ICX55S16YeP5q-U6Z2e5aSa5pKt5qih5byP6KaB5aSn44CCIn0seyJ0aXRsZSI6Iuivu-WPluaXpeWOhua0u-WKqOWSjOivpuaDhSIsImNvbnRlbnQiOiLmraTlupTnlKjlj6_or7vlj5bmgqjmiYvmnLrkuIrlrZjlgqjnmoTmiYDmnInml6XljobmtLvliqjvvIzlubbliIbkuqvmgqjnmoTml6XljobmlbDmja7jgIIifSx7InRpdGxlIjoi5re75Yqg5oiW5L-u5pS55pel5Y6G5rS75YqoIiwiY29udGVudCI6IuWFgeiuuOivpeW6lOeUqOa3u-WKoOOAgeWIoOmZpOOAgeabtOaUueaCqOWPr-WcqOaJi-acuuS4iuS_ruaUueeahOa0u-WKqO-8jOWMheaLrOaci-WPi-aIluWQjOS6i-eahOa0u-WKqOOAguatpOadg-mZkOWPr-iuqeivpeW6lOeUqOWGkuWFheaXpeWOhuaJgOacieiAheWPkemAgea2iOaBr--8jOaIluWcqOaJgOacieiAheS4jeefpeaDheeahOaDheWGteS4i-S_ruaUuea0u-WKqOOAgiJ9LHsidGl0bGUiOiLmm7TmlLnmgqjnmoTpn7PpopHorr7nva4iLCJjb250ZW50Ijoi5YWB6K646K-l5bqU55So5L-u5pS55YWo5bGA6Z-z6aKR6K6-572u77yM5L6L5aaC6Z-z6YeP5ZKM55So5LqO6L6T5Ye655qE5oms5aOw5Zmo44CCIn1dLCJwZXJtaXNzaW9uc1VybCI6IiIsInVybCI6Imh0dHA6Ly9iZC1tLnF1dG91dGlhby5uZXQvZG93bmxvYWQvcXV0b3V0aWFvXzEzMTMuYXBrIiwiZGV2ZWxvcE5hbWUiOiLkuIrmtbfln7rliIbmlofljJbkvKDmkq3mnInpmZDlhazlj7giLCJwcml2YXRlUG9saWN5IjoiaHR0cHM6Ly9oNXNzbC4xc2FwcC5jb20vcXVrYW53ZWIvaW5hcHAvdXNlcl9hZ3JlZW1lbnQvcHJpdmFjeS5odG1sIiwiYXBwU3RvcmVEZWVwbGluayI6IiIsImFwcERlc2MiOiLmtYvor5UiLCJod0hvdFpvbmVEb3dubG9hZCI6IiIsImFwcEljb24iOiJodHRwOi8vdGVzdC1zYy5zZWV5b3V5aW1hLmNvbS9laW1nL2FkaW1nLzIwMjMvMi82M2RiN2ZlZDk5MGExXzI1Nl8yNTYuanBnIn0"
        #         data = json.load(fcc_file)
        #         for d in data:
        #             if d.get('download_scheme_uri'):
        #                 # logging.info(d['download_scheme_uri'])    
        #                 d['download_scheme_uri'] = download_scheme_uri
        #         flow.response.text = json.dumps(data)
                # logging.info(flow.response.get_text())


        # if  pageid == '1100': 
        #     with open("./1100_3rd_ad.txt", 'r') as fcc_file:
        #     # data = json.loads(flow.response.get_text())
        #         # logging.info(flow.response.get_text())
        #         data = json.load(fcc_file)
        #         flow.response.text = json.dumps(data)
        

        
        data = json.loads(flow.response.get_text())
        logging.warn(f"{pageid}/{pos_id}")
        i = 0
        for d in data:
            if d.get('download_scheme_uri') or d.get('scheme_uri'):
                path1 , p1 = get_path_params(d.get('download_scheme_uri'))
                path2 , p2 = get_path_params(d.get('scheme_uri'))
                logging.error(f"{i} title:{d.get("title")} call_down_title:{d.get('call_down_title')} new_download_style:{d.get('new_download_style')}")
                if p1:
                    p1j = json.loads(p1)
                    logging.error(f"download_scheme_uri: {path1}\t{p1j.get("appName")} {p1j.get("url")} {p1j.get("appStoreDeeplink")}")
                    logging.error(p1)
                if p2:
                    p2j = json.loads(p2)
                    logging.error(f"scheme_uri:\t{path2} {p2j.get("url")} {p2}")
                        # break
                i +=1
            
addons = [Counter()]