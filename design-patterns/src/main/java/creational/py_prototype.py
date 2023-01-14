import copy


class Myself():
    def __init__(self, width=10, height=10):
        self.width = width
        self.height = height

    def __deepcopy__(self, mem={}):
        w = copy.deepcopy(self.width, mem)
        h = copy.deepcopy(self.height, mem)
        shadow = self.__class__(w, h)
        shadow.__dict__ = copy.deepcopy(self.__dict__, mem)
        return shadow

    def __copy__(self):
        w = copy.copy(self.width)
        h = copy.copy(self.height)
        shadow = self.__class__(w, h)
        shadow.__dict__.update(self.__dict__)
        return shadow


class MyselfShadow():
    pass
