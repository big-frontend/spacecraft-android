from logging.config import dictConfig
from logging.handlers import SMTPHandler
import logging

from flask import Flask

logger_dict = {
    'version': 1,
    'formatters': {
        'default_formatter': {
            'format': '[%(asctime)s] %(levelname)s in %(module)s: %(message)s',
        },
        'error_formatter': {
            'format': '%(asctime)s [%(levelname)s] :%(levelno)s: %(message)s',
        }

    },
    'handlers': {
        'wsgi_handler': {
            'class': 'logging.StreamHandler',
            'stream': 'ext://flask.logging.wsgi_errors_stream',
            'formatter': 'default_formatter'
        },
        # 'error_file_handler': {
        #     'class': 'logging.handlers.RotatingFileHandler',
        #     'formatter': 'default_formatter',
        #     'level': 'ERROR',
        #     'filename': './logs/error.log',
        # 'maxBytes': '10485760',
        # 'backupCount': '20',
        # 'encoding': 'utf8',
        # }
    },
    'root': {
        'level': 'INFO',
        'handlers': ['wsgi_handler'],
    }
}
# load logging configuration
dictConfig(logger_dict)

# load mail configuration
mail_handler = SMTPHandler(
    mailhost='127.0.0.1',
    fromaddr='server-error@example.com',
    toaddrs=['hawksjamesf@gmail.com'],
    subject='Application Error'
)
mail_handler.setLevel(logging.ERROR)
mail_handler.setFormatter(logging.Formatter(
    '[%(asctime)s] %(levelname)s in %(module)s: %(message)s'))


def init(app: Flask):
    """
    logging的配置有两种：dictConfig配置表 与 代码配置
    """
    if not app.debug:
        app.logger.addHandler(mail_handler)


def d(msg, *args, **kwargs): logging.debug(msg, *args, **kwargs)


def i(msg, *args, **kwargs): logging.info(msg, *args, **kwargs)


def w(msg, *args, **kwargs): logging.warning(msg, *args, **kwargs)


def e(msg, *args, **kwargs): logging.error(msg, *args, **kwargs)


def c(msg, *args, **kwargs): logging.critical(msg, *args, **kwargs)
