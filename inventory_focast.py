from flask import Flask, request, jsonify
from flask_cors import CORS
from sklearn.linear_model import LinearRegression
import numpy as np

app = Flask(__name__)
CORS(app)

class InventoryForecastingModel:
    def __init__(self):
        self.model = LinearRegression()
        self.model.fit([[1], [2], [3]], [10, 20, 30])

    def forecast(self, current_inventory):
        return self.model.predict([[current_inventory]])[0]

model = InventoryForecastingModel()

@app.route('/api/forecast', methods=['POST'])
def forecast_inventory():
    try:
        data = request.get_json()
        print("Received data:", data)  # log để debug
        current_inventory = data.get('current_inventory', 0)
        forecast = model.forecast(current_inventory)
        return jsonify({
            'forecasted_demand': forecast,
            'suggestions': f'Replenish {forecast - current_inventory} units of stock.'
        })
    except Exception as e:
        print("Error occurred:", str(e))  # log lỗi
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True, port=5000)
