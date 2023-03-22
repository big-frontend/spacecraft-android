from dataclasses import dataclass, field
from app.storage import DbModel, DbColumn, DbInteger, DbString, DbDateTime, dbSession,DbBoolean
from typing import List, Optional
from sqlalchemy.orm import relationship, registry
from sqlalchemy import ForeignKey,update
import re
from flask  import current_app


@dataclass
class File(DbModel):
    id: int = DbColumn(DbInteger, primary_key=True, autoincrement=True)
    entry_id: int = DbColumn(DbInteger, ForeignKey("entry.id"))
    entry_name: str = DbColumn('entry_name', DbString)
    entry_size: int = DbColumn('entry_size', DbInteger)

@dataclass
class Entry(DbModel):
     id: int = DbColumn(DbInteger, primary_key=True, autoincrement=True)
     task_id: int = DbColumn(DbInteger, ForeignKey("task.id"))
     suffix: str = DbColumn(DbString)
     total_size:int = DbColumn('total_size', DbInteger)
     files: List[File] = relationship("File")

@dataclass
class RClasse(DbModel):
    """
    count R field
    """
    id: int = DbColumn(DbInteger, primary_key=True, autoincrement=True)
    task_id: int = DbColumn(DbInteger, ForeignKey("task.id"))
    name: str = DbColumn(DbString)
    field_count: int = DbColumn('field_count', DbInteger)

@dataclass
class Group(DbModel):
    """
    count methods
    """
    id: int = DbColumn(DbInteger, primary_key=True, autoincrement=True)
    task_id: int = DbColumn(DbInteger, ForeignKey("task.id"))
    name:str= DbColumn(DbString)
    method_count:int = DbColumn(DbInteger)

@dataclass
class ShowFileSize(DbModel):
    id: int = DbColumn(DbInteger, primary_key=True, autoincrement=True)
    task_id: int = DbColumn(DbInteger, ForeignKey("task.id"))
    entry_name:str = DbColumn('entry_name', DbString)
    entry_size: int = DbColumn('entry_size', DbInteger)

@dataclass
class UnusedResource(DbModel):
    id: int = DbColumn(DbInteger, primary_key=True, autoincrement=True)
    task_id: int = DbColumn(DbInteger, ForeignKey("task.id"))
    name:str = DbColumn(DbString)
    max_reduce_size: int = DbColumn(DbInteger)

@dataclass
class UnusedAsset(DbModel):
    id: int = DbColumn(DbInteger, primary_key=True, autoincrement=True)
    task_id: int = DbColumn(DbInteger, ForeignKey("task.id"))
    name:str= DbColumn(DbString)
    max_reduce_size: int = DbColumn(DbInteger)


@dataclass
class NonAlphaPng(DbModel):
    id: int = DbColumn(DbInteger, primary_key=True, autoincrement=True)
    task_id: int = DbColumn(DbInteger, ForeignKey("task.id"))
    entry_name:str = DbColumn(DbString)
    entry_size: int = DbColumn(DbInteger)
    max_reduce_size: int = DbColumn(DbInteger)
@dataclass
class UncompressedFileType(DbModel):
    id: int = DbColumn(DbInteger, primary_key=True, autoincrement=True)
    task_id: int = DbColumn(DbInteger, ForeignKey("task.id"))
    suffix: str = DbColumn(DbString)
    total_size: int = DbColumn(DbInteger)

@dataclass
class DuplicatedFilePath(DbModel):
    id: int = DbColumn(DbInteger, primary_key=True, autoincrement=True)
    duplicated_file_id: int = DbColumn(DbInteger, ForeignKey("duplicated_file.id"))
    name:str = DbColumn(DbString)
@dataclass
class DuplicatedFile(DbModel):
    id: int = DbColumn(DbInteger, primary_key=True, autoincrement=True)
    task_id: int = DbColumn(DbInteger, ForeignKey("task.id"))
    md5: str = DbColumn(DbString)
    size: int = DbColumn(DbInteger)
    duplicated_file_paths:List[DuplicatedFilePath] = relationship("DuplicatedFilePath")

#so
@dataclass
class StlLib(DbModel):
    id: int = DbColumn(DbInteger, primary_key=True, autoincrement=True)
    task_id: int = DbColumn(DbInteger, ForeignKey("task.id"))
    name:str = DbColumn(DbString)

@dataclass
class LibDir(DbModel):
    id: int = DbColumn(DbInteger, primary_key=True, autoincrement=True)
    task_id: int = DbColumn(DbInteger, ForeignKey("task.id"))
    name:str = DbColumn(DbString)

@dataclass
class UnstrippedLib(DbModel):
    id: int = DbColumn(DbInteger, primary_key=True, autoincrement=True)
    task_id: int = DbColumn(DbInteger, ForeignKey("task.id"))
    name:str= DbColumn(DbString)
    max_reduce_size: int = DbColumn(DbInteger)
@dataclass
class Task(DbModel):
    id: int = DbColumn(DbInteger, primary_key=True, autoincrement=True)
    statement_id: int = DbColumn(DbInteger, ForeignKey("statement.id"))
    task_type: int = DbColumn('task_type', DbInteger, nullable=False)
    task_description: str = DbColumn('task_desc', DbString)
    total_size:int = DbColumn('total_size', DbInteger)
    #taskType=1 Unzip the apk file to dest path.
    entries: List[Entry] = relationship("Entry")
    #taskType=2 Check if the apk handled by resguard..
    has_res_proguard:bool = DbColumn(DbBoolean)
    #taskType = 6 Find out the non-alpha png-format files whose size exceed limit size in desc order.
    non_alpha_pngs:List[NonAlphaPng] = relationship("NonAlphaPng")
    #taskType = 8 Show uncompressed file types.
    uncompressed_file_types:List[UncompressedFileType] = relationship("UncompressedFileType")
    #taskType = 10 Find out the duplicated files.
    duplicated_files:List[DuplicatedFile] = relationship("DuplicatedFile")
    #taskType = 12 Find out the unused resources.
    unused_resources:List[UnusedResource] =relationship("UnusedResource")
    #taskType = 13 Find out the unused assets.
    unused_assets:List[UnusedAsset] = relationship("UnusedAsset")
    #taskType = 14 Find out the unstripped shared library files.
    unstripped_lib:List[UnstrippedLib] = relationship("UnstrippedLib")
    #taskType = 3 Show files whose size exceed limit size in order.
    show_file_sizes:List[ShowFileSize] = relationship("ShowFileSize")
    # taskType =4 Count methods in dex file, output results group by class name or package name.
    total_methods:int = DbColumn(DbInteger)
    groups:List[Group] = relationship("Group")
    #taskType = 9 Count the R class.
    r_count:int = DbColumn(DbInteger)
    field_counts:int = DbColumn(DbInteger)
    r_classes:List[RClasse] = relationship("RClasse")
    #taskType =7 Check if there are more than one library dir in the \u0027lib\u0027.
    lib_dirs:List[LibDir] = relationship("LibDir")
    multi_lib:bool = DbColumn(DbBoolean)
    #taskType =11 Check if there are more than one shared library statically linked the STL.
    stl_lib:List[StlLib] = relationship("StlLib")
    multi_stl:bool = DbColumn(DbBoolean)



@dataclass
class Statement(DbModel):
    version_code: int
    version_name: str
    package: str
    min_sdk_version: str
    target_sdk_version: str
    tasks:List[Task]
    id: int = DbColumn(DbInteger, primary_key=True, autoincrement=True)
    version_code = DbColumn(DbInteger)
    version_name = DbColumn(DbString)
    biz:str = DbColumn(DbString)
    package = DbColumn(DbString)
    total_size = DbColumn(DbInteger)
    min_sdk_version = DbColumn(DbString)
    target_sdk_version = DbColumn(DbString)
    tasks = relationship("Task")

# mapper_registry = registry()
# @mapper_registry.mapped
# @dataclass
# class Task:
#      __tablename__ = "task"
#      __sa_dataclass_metadata_key__ = "sa"
#      id: int = field(init=False, metadata={"sa": DbColumn(DbInteger, primary_key=True)})
#      # taskType:int=DbColumn(DbInteger)
#      # taskDescription:str=DbColumn(DbString)
#      statement_id:int = field(init=False, metadata={"sa": DbColumn(ForeignKey("statement.id"))})
#      # statement = relationship("Statement", back_populates="tasks")
# @mapper_registry.mapped
# @dataclass
# class Statement:
#      __tablename__ = "statement"
#      __sa_dataclass_metadata_key__ = "sa"
#      id: int = field(init=False, metadata={"sa": DbColumn(DbInteger, primary_key=True)})
#      tasks:List[Task] = field(default_factory=list, metadata={"sa": relationship("Task")})
def camel_to_snake(name):
    name = re.sub('(.)([A-Z][a-z]+)', r'\1_\2', name)
    return re.sub('([a-z0-9])([A-Z])', r'\1_\2', name).lower()

from PIL import Image
import os
def calcu_strip_so(so_file):
    return 0

def calcu_non_alpha_png(pic_file):
    im = Image.open(pic_file)
    new_pic_file = pic_file[:-3]+'jpg'
    im.convert('RGB').save(new_pic_file) #默认quality=75
    return  os.path.getsize(pic_file) - os.path.getsize(new_pic_file)
def find_abs_path(unzip_package,target):
    visited =  target.split('/')[::-1]
    print(target,visited)
    def find(d):
        if os.path.isfile(d):return d
        for filename in os.listdir(d):
            f = os.path.join(d, filename)
            if visited[-1] == filename:
                visited.pop()
                if not visited:
                    return f            
                else:
                    return find(f)  

    return find(unzip_package)           
def convert(cls, d,unzip_package=None):
#     o = object.__new__(cls)
    o = cls()
    for k, v in d.items():
        if k == 'start-time' or k == 'end-time':continue
        new_k = ''
        if '-' in k:
            a = k.split('-')
            for i in range(0, len(a)):
                if i == len(a) - 1:
                    new_k += a[i].lower()
                else:
                    new_k += a[i].lower()
                    new_k += '_'
        else:
            new_k = camel_to_snake(k)

        if  new_k == 'files' and d.get('taskType') == 6:
            non_alpha_pngs = []
            for e in v:
                pic_file = os.path.join(unzip_package,e['entry-name'])
                if not pic_file:
                        raise RuntimeError(f'{unzip_package} {e} 文件找不到{pic_file}')
                delta = calcu_non_alpha_png(pic_file)
                non_alpha_pngs.append(NonAlphaPng(**{'entry_name':e['entry-name'],'entry_size':e['entry-size'],'max_reduce_size':delta}))
            setattr(o, 'non_alpha_pngs', non_alpha_pngs)
        elif  new_k == 'files' and d.get('taskType') == 8:
            setattr(o, 'uncompressed_file_types', [UncompressedFileType(**{'suffix':e['suffix'],'total_size':e['total-size']}) for e in v])
        elif  new_k == 'files' and d.get('taskType') == 10:
            #**{'name':f}
            setattr(o, 'duplicated_files', [DuplicatedFile(**{'md5':e['md5'],'size':e['size'],'duplicated_file_paths':[DuplicatedFilePath(name=f) for f in e['files']]}) for e in v])
        elif  new_k == 'files' and d.get('taskType') == 3:
            setattr(o, 'show_file_sizes', [ShowFileSize(**{'entry_name':e['entry-name'],'entry_size':e['entry-size']}) for e in v])
        else:
            if not hasattr(o, new_k):
                print(f"{cls} 不存在 {new_k}字段 {k} {d.get('taskType')}")
                continue
            if new_k == 'files':
                setattr(o, 'files', [convert(File, e) for e in v])
            elif new_k == 'entries':
                setattr(o, 'entries', [convert(Entry, e) for e in v])
            elif new_k == 'r_classes':
                setattr(o, 'r_classes', [convert(RClasse, e) for e in v])
            elif new_k == 'unused_resources':
                unused_resources = []
                for e in v:
                    # file = find_abs_path(unzip_package,e.split('.')[-1])
                    unused_resources.append(UnusedResource(**{'name':e,'max_reduce_size':0}))
                setattr(o, 'unused_resources',unused_resources)
            elif new_k == 'unused_assets':
                unused_assets = []
                for e in v:
                    file = os.path.join(unzip_package,f"assets/{e}")
                    if not file:
                        raise RuntimeError(f'{unzip_package} {e} 文件找不到{file}')
                    delta = os.path.getsize(file)
                    unused_assets.append(UnusedAsset(**{'name':e,'max_reduce_size':delta}))
                setattr(o, 'unused_assets', unused_assets)
            elif new_k == 'groups':setattr(o, 'groups', [convert(Group, e) for e in v])
            elif new_k == 'stl_lib':setattr(o, 'stl_lib', [StlLib(**{'name':e}) for e in v])
            elif new_k == 'lib_dirs':setattr(o, 'lib_dirs', [LibDir(**{'name':e}) for e in v])
            elif new_k == 'unstripped_lib':
                unstripped_lib = []
                for e in v:
                    # file = find_abs_path(unzip_package,e)
                    file = os.path.join(unzip_package,f'lib/arm64-v8a/{e}')
                    if not file:
                        raise RuntimeError(f'{unzip_package} {e} 文件找不到{file}')
                    delta = calcu_strip_so(file)
                    unstripped_lib.append(UnstrippedLib(**{'name':e,'max_reduce_size':delta}))
                setattr(o, 'unstripped_lib', unstripped_lib)
            else:
                setattr(o, new_k, v)
    return o

def parse(biz,args):
    d = {}
    ts = []
    for e in args:
        if e['taskType'] == 2:
            d['package']=e['manifest']['package']
            d['min_sdk_version']=e['manifest']['android:minSdkVersion']
            d['target_sdk_version']=e['manifest']['android:targetSdkVersion']
            d['version_code']=int(e['manifest']['android:versionCode'])
            d['version_name']=e['manifest']['android:versionName']
        else:
            unzip = os.path.join(current_app.config['UPLOAD_FOLDER'],biz)
            if e['taskType'] == 1:
                d['total_size'] = e['total-size']
            t = convert(Task, e,unzip)
            ts.append(t)
    d['tasks'] = ts
    d['biz'] = biz
    u = Statement(**d)
    return u

def copy(old,new):
    old.version_code = new.version_code
    old.version_name = new.version_name
    old.package = new.package
    old.biz = new.biz
    old.total_size = new.total_size
    old.min_sdk_version = new.min_sdk_version
    old.target_sdk_version = new.target_sdk_version
    old.tasks = new.tasks

def add(biz,args):
    u = parse(biz,args)
    uu = query(biz = biz,version_code=u.version_code).first()
    if uu:
        copy(uu,u)
        dbSession.commit()
        return uu
    else:
        dbSession.add(u)
        dbSession.commit()
        return u
def update(biz,args):
    u = parse(biz,args)
    uu = query(biz = biz,version_code=u.version_code).first()
    copy(uu,u)
    dbSession.commit()
    return uu

def get(**kwargs):
    return Statement.query.filter_by(**kwargs).all() if kwargs else Statement.query.all()
def query(*where, **kwargs):
    return Statement.query.filter(where) if where else Statement.query.filter_by(**kwargs)

def part_query(*part, **kwargs):
    return dbSession.query(*part).order_by('version_code').filter_by(**kwargs) if kwargs else dbSession.query(*part).order_by('version_code').all()

def delete(biz,version):
    u = query(biz=biz ,version_code=version).delete()
    dbSession.commit()
    return u 