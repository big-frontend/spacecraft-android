from flask import (
    Blueprint, flash, g, redirect, render_template, request, session, url_for,
    template_rendered
)

from contextlib import contextmanager
import os

def find_abs_path(unzip_package,target):
    visited =  target.split('/')[::-1]
    def find(d):
        if os.path.isfile(d):return d
        for filename in os.listdir(d):
            f = os.path.join(d, filename)
            print(visited,filename,f)
            if visited[-1] == filename:
                visited.pop()
                if not visited:
                    return f            
                else:
                    return find(f)  

    return find(unzip_package)            
# a = find_abs_path('tests','res/drawable-xxhdpi-v4/quit_dialog_no_games_bg.png')
# a = find_abs_path('tests','a/b/b.py')
# print(a)
import os

def findfile(start, name):
    for relpath, dirs, files in os.walk(start):
        if name in files:
            full_path = os.path.join(start, relpath, name)
            print(os.path.normpath(os.path.abspath(full_path)))

print(os.path.dirname(__file__))
print(findfile(os.path.dirname(__file__),'b.py'))

from PIL import Image
import os
def calcu_non_alpha_png(pic_file):
    im = Image.open(pic_file)
    new_pic_file = pic_file[:-3]+'jpg'
    im.convert('RGB').save(new_pic_file) #默认quality=75
    return  os.path.getsize(pic_file) - os.path.getsize(new_pic_file)

print(calcu_non_alpha_png('quit_dialog_no_games_bg.png'))