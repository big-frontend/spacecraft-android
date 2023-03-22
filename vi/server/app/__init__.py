import os

from flask import Flask, current_app
from flask_login import LoginManager

from sentry_sdk.integrations.flask import FlaskIntegration
from app import  api, storage
from flask_wtf.csrf import CSRFProtect
from app.config import DevelopmentConfig
from app.storage.user import AnonymousUser, User
from app.util import l
from app.storage import user as user_dao
csrf_protect = CSRFProtect()

def flask_login_init(app):
    csrf_protect.init_app(app)
    login_manager = LoginManager()
    login_manager.session_protection = "strong"
    login_manager.anonymous_user = AnonymousUser
    login_manager.init_app(app)

    @login_manager.user_loader
    def load_user(user_id):
        current_app.logger.info(user_id)
        # return user_dao.get(user_id=user_id)
        return User.query.get(user_id)
    @login_manager.unauthorized_handler
    def unauthorized():
        return "未授权"


def create_app(config_file=None, config_object=DevelopmentConfig()):
    app = Flask(__name__, instance_relative_config=True)
    # 优先从文件区配置，有利于动态改变正在运行的app配置
    app.config['UPLOAD_FOLDER'] = app.instance_path
    if config_file:
        l.i("读取config_file")
        app.config.from_pyfile(config_file, silent=True)
    else:
        # 1.cfg =import_string('config.DevelopmentConfig')
        # app.config.from_object(cfg)
        # 2 app.config.from_object('config.DevelopmentConfig')
        l.i("读取config_object")
        app.config.from_object(config_object)
    # app.config.from_envvar()
    # app.config.from_json()
    # app.config.update()
    try:
        os.makedirs(app.instance_path)
    except OSError:
        pass


    # 注册路由
    api.init(csrf_protect,app)
    # 初始化数据库
    storage.init(app)
    l.init(app)
    flask_login_init(app)
    return app
