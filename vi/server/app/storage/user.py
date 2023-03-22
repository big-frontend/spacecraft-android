from dataclasses import dataclass
from datetime import datetime

from flask import current_app
from flask_login import UserMixin, AnonymousUserMixin

from app.storage import DbModel, DbColumn, DbInteger, DbString, DbDateTime, dbSession


@dataclass
class AnonymousUser(AnonymousUserMixin):
    @property
    def role(self):
        return None


@dataclass
class User(UserMixin, DbModel):
    # flask 的jsonify 函数 对于 current_time这样的非数据库字段。如果声明类型则在进行jsonify时就会报错，为了过滤应该不声明
    current_time = datetime.now
    # 表的结构:
    id: int = DbColumn(DbInteger, primary_key=True, autoincrement=True)
    username: str = DbColumn(DbString(50))
    password: str = DbColumn(DbString(50), nullable=False)
    is_email_verified: int = DbColumn(DbInteger, default=0)
    email: str = DbColumn(DbString(50), unique=True, nullable=False)
    avatar: str = DbColumn(DbString(100))
    role: str = DbColumn(DbString(10))
    status: int = DbColumn(DbInteger, default=1)
    last_space: int = DbColumn(DbInteger, default=0)
    # role_info = relationship("walle.model.user.RoleModel", back_populates="users")
    created_at: str = DbColumn(DbDateTime, default=current_time)
    updated_at: str = DbColumn(DbDateTime, default=current_time, onupdate=current_time)

    status_active = 1
    status_blocked = 2
    status_mapping = {
        -1: '删除',
        0: '新建',
        1: '正常',
        2: '冻结',
    }
    @classmethod
    def avatar_url(cls, avatar):
        avatar = avatar if avatar else 'default.jpg'
        return current_app.config['AVATAR_PATH'] + avatar

    def to_json(self):
        item = {
            'id': int(self.id),
            'user_id': int(self.id),
            'username': self.username,
            'is_email_verified': self.is_email_verified,
            'email': self.email,
            'avatar': self.avatar_url(self.avatar),
            'status': self.status_mapping[self.status],
            'last_space': self.last_space,
            # 'status': self.status,
            # 'role_name': self.role_id,
            'created_at': self.created_at.strftime('%Y-%m-%d %H:%M:%S'),
            'updated_at': self.updated_at.strftime('%Y-%m-%d %H:%M:%S'),
        }
        item.update(self.enable())
        return item

    def enable(self):
        return {
            'enable_view': True,
            # 'enable_update': permission.role_upper_master(),
            # 'enable_delete': permission.role_upper_master(),
            'enable_create': False,
            'enable_online': False,
            'enable_audit': False,
            'enable_block': False,
        }
    # def __repr__(self):
    #     """
    #     打印User数组 会调用
    #     :return:
    #     """
    #     return json.dumps({
    #         'id': self.id,
    #         'username': self.username,
    #         'email': self.email
    #     })
    #
    # def __str__(self):
    #     return json.dumps({
    #         'id': self.id,
    #         'username': self.username,
    #         'email': self.email
    #     })


def add(**kwargs):
    u = User(**kwargs)
    dbSession.add(u)
    dbSession.commit()
    return u


def remove(**kwargs):
    ret = User.query.filter_by(**kwargs).delete() if kwargs else User.query.delete()
    dbSession.commit()
    return ret


def update(**kwargs):
    users = User.query.filter_by(**kwargs).all()
    for u in users:
        for attr, value in list(kwargs.items()):
            setattr(u, attr, value)
        dbSession.add(u)
        dbSession.commit()


def get(**kwargs):
    return User.query.filter_by(**kwargs).all() if kwargs else User.query.all()


def query(*where, **kwargs):
    return User.query.filter(where) if where else User.query.filter_by(**kwargs)
