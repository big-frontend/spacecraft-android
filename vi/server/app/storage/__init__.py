"""
storage包括 sql db / nosql db / 文件存储 等
"""
import os
import sqlite3
from flask import current_app, g, Flask
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy import String, Integer, DateTime, or_, Column,ARRAY,Boolean


def get_db():
    if 'db' not in g:
        pass
        g.db = sqlite3.connect(
            current_app.config['DATABASE'],
            detect_types=sqlite3.PARSE_DECLTYPES
        )
        g.db.row_factory = sqlite3.Row
    return g.db


def close_db(e=None):
    db = g.pop('db', None)
    if db is not None:
        db.close()


alchemy = SQLAlchemy()
DbModel  = alchemy.Model
dbSession = alchemy.session
DbColumn = Column
DbInteger = Integer
DbString = String
DbDateTime = DateTime
db_or = or_
DbArray = ARRAY
DbBoolean = Boolean


def init(app: Flask):
    # log.i('flask env : {}'.format(app.config['SECRET_KEY']))
    app.config.from_mapping(SECRET_KEY='dev' if not app.config['SECRET_KEY'] else app.config['SECRET_KEY'],
                            DATABASE=os.path.join(app.instance_path, 'app.sqlite'))
    # app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:////tmp/app.sqlite'
    app.config['SQLALCHEMY_DATABASE_URI'] = f'sqlite:///{app.instance_path}/app.sqlite'
    # app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = True
    alchemy.init_app(app)
    # tells Flask to call that function
    # when cleaning up after returning the response.
    app.teardown_appcontext(close_db)
    with app.app_context():
        # get_db()  # 提前连接数据库
        alchemy.create_all()
