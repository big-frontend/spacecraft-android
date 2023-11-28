import sys,os,dataclasses
def convertTs(ts):
    if ts >= 1000:
        return f"{ts/1000}s"
    else:
        return f"{ts}ms"
class ThreadInfo:
    t:str
    utm:int
    stm:int
    cpu_duration:int
    def __str__(self) -> str:
        return f"cpu_duration:{convertTs(self.cpu_duration)} utm/stm:{convertTs(self.utm)}/{convertTs(self.stm)} thread:{self.t}"

from collections import OrderedDict
import collections

def main():
    l = []
    with open(sys.argv[1],"r",encoding = 'utf-8') as f:
        while True:
            line = f.readline()
            if not line:break

            if "----- pid" in line:
                if l:
                    # sorted(l,key=lambda t: t.cpu_duration)
                    l = sorted(l,key=lambda t: t.cpu_duration,reverse=True)
                    l = filter(lambda t: t.cpu_duration !=0, l)    
                    print(*l,sep='\n')
                    l = []
                print(line.strip(),sep='\n')
                continue
            #解析一个线程信息
            if "prio=" in line and "tid=" in line:
                t = ThreadInfo()
                t.t=line.strip()
                while True:
                    line = f.readline()
                    if line in ['\n','\r\n']:
                        break
                    if "utm=" in line:
                        utm = 0
                        for e in line.split(" "):
                            if "utm" in e:
                                utm = int(e.split("=")[1])
                                t.utm = utm * 10
                                break
                    if "stm=" in line:
                        stm = 0
                        for e in line.split(" "):
                            if "stm" in e:
                                stm = int(e.split("=")[1])
                                t.stm = stm * 10
                                break
                t.cpu_duration = (t.utm + t.stm) 
                l.append(t)
    
    if l:
        # sorted(l,key=lambda t: t.cpu_duration)
        l = sorted(l,key=lambda t: t.cpu_duration,reverse=True)
        l = filter(lambda t: t.cpu_duration !=0, l)    
        print(*l,sep='\n')

if __name__=="__main__":
    main()