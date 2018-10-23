from flask import Flask

app = Flask(__name__)


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/data/2.5/weather', methods=['GET'])
def get_current_weather_data():
    return 'get current weather data'


@app.route('/data/2.5/forecast', methods=['GET'])
def get_five_data():
    return ''


if __name__ == '__main__':
    app.run()
